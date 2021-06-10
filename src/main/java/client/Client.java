package client;

import client.utils.AlertDisplay;
import client.utils.CollectionRefresher;
import client.utils.ScriptExecutor;
import commons.app.*;
import commons.commands.Login;
import commons.commands.Register;
import commons.commands.Show;
import commons.elements.Worker;
import commons.network.Request;
import commons.network.Response;
import commons.network.ResponseCode;
import commons.utils.UserInterface;
import commons.utils.SerializationTool;
import javafx.application.Platform;

import java.io.*;
import java.net.BindException;
import java.net.InetSocketAddress;
import java.net.PortUnreachableException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.UnresolvedAddressException;
import java.util.*;

import static client.utils.PasswordEncoder.getHexString;

public class Client {
    private InetSocketAddress socketAddress;
    private final DatagramChannel datagramChannel;
    private final Selector selector;
    private final UserInterface userInterface = new UserInterface(new InputStreamReader(System.in),
            new OutputStreamWriter(System.out), true);
    private User user = null;
    private CollectionRefresher collectionRefresher;
    private HashSet<Worker> collection;
    private String host;
    private int port;
    private AlertDisplay alertDisplay;

    public Client() throws IOException {
        selector = Selector.open();
        datagramChannel = DatagramChannel.open();
        datagramChannel.configureBlocking(false);
        userInterface.displayMessage("Осуществляется подключение в неблокирующем режиме ввода-вывода");
    }

    public boolean connect(String host, int port) throws IOException {
        socketAddress = new InetSocketAddress(host, port);
        datagramChannel.connect(socketAddress);
        userInterface.displayMessage("Осуществляется подключение по адресу " + host + " по порту " + port);
        return true;
    }

    public void sendRequest(Request request) throws IOException {
        ByteBuffer send = ByteBuffer.wrap(Objects.requireNonNull(SerializationTool.serializeObject(request)));
        datagramChannel.send(send, socketAddress);
    }

    public byte[] receiveAnswer() throws InterruptedException, IOException {
        byte[] buffer = new byte[65536];
        ByteBuffer bufferAnswer = ByteBuffer.wrap(buffer);
//        Thread.sleep(300);
        socketAddress = (InetSocketAddress) datagramChannel.receive(bufferAnswer);
        return bufferAnswer.array();
    }

    public HashSet<Worker> processRequestFromUser(String cmd, String argument, Worker object) {
        Request requestToServer = null;
        Response serverResponse = null;
        try {
            Set keys = selector.selectedKeys();
            keys.clear();
            datagramChannel.register(selector, SelectionKey.OP_WRITE);
            requestToServer = new Request(cmd, argument, object, user);
            sendRequest(requestToServer);
            Thread.sleep(300);
            datagramChannel.register(selector, SelectionKey.OP_READ);
            selector.select();
            byte[] answer = receiveAnswer();
            serverResponse = (Response) new SerializationTool().deserializeObject(answer);
            if (!serverResponse.getResponseBody().equals("NotNeeded") && (!serverResponse.getResponseBody().equals("Empty") || serverResponse.getResponseBody().equals(""))) {
                if (serverResponse.getResponseCode().equals(ResponseCode.ERROR))
                    AlertDisplay.showError(serverResponse.getResponseBody(), serverResponse.getResponseBodyArgs());
                else AlertDisplay.showInfo(serverResponse.getResponseBody(), serverResponse.getResponseBodyArgs());
            }
            if (serverResponse.getResponseBody().equals("NotNeeded")) {
                return null;
            }
            return serverResponse.getCollection();
        } catch (PortUnreachableException e) {
            AlertDisplay.showError("PortUnavailableError");
            try {
                datagramChannel.disconnect();
                connect(host, port);
            } catch (PortUnreachableException ex) {
                AlertDisplay.showError("TryLater");
            } catch (IOException exc) {
                AlertDisplay.showError("FatalConnectionError");
                Platform.exit();
            }
            return null;
        } catch (BindException e) {
            AlertDisplay.showError("ConnectionError");
            return null;
        } catch (UnresolvedAddressException e) {
            AlertDisplay.showError("AddressError");
            return null;
        } catch (IOException e) {
            AlertDisplay.showError("FatalConnectionError");
            return null;
        } catch (InterruptedException e) {
            AlertDisplay.showError("FatalConnectionError");
            return null;
        }
    }

    public boolean executeScript(File script) {
        Request requestToServer = null;
        Response serverResponse = null;
        ScriptExecutor scriptExecutor = new ScriptExecutor(script);
        try {
            while (true) {
                requestToServer = serverResponse != null ? scriptExecutor.process(serverResponse.getResponseCode(),
                        user) : scriptExecutor.process(null, user);
                if (requestToServer == null)
                    return false;
                if (requestToServer.isEmpty())
                    continue;
                Set keys = selector.selectedKeys();
                keys.clear();
                datagramChannel.register(selector, SelectionKey.OP_WRITE);
                if (requestToServer.getCommandName().equals("end"))
                    break;
                sendRequest(requestToServer);
                Thread.sleep(300);
                datagramChannel.register(selector, SelectionKey.OP_READ);
                selector.select();
                byte[] answer = receiveAnswer();
                serverResponse = (Response) new SerializationTool().deserializeObject(answer);
                if (!serverResponse.getResponseBody().isEmpty()) {
                    if (serverResponse.getResponseCode().equals(ResponseCode.ERROR))
                        return false;
                }
            }
        } catch (InterruptedException e) {
            AlertDisplay.showError("FatalConnectionError");
            return false;
        } catch (IOException e) {
            AlertDisplay.showError("FatalConnectionError");
            return false;
        }
        return true;
    }

    public boolean login(String login, String pwd) {
        Request requestToServer = null;
        Response serverResponse = null;
        try {
            Set keys = selector.selectedKeys();
            keys.clear();
            datagramChannel.register(selector, SelectionKey.OP_WRITE);
            User user = new User(login, getHexString(pwd));
            requestToServer = new Request("login", "", null, user);
            sendRequest(requestToServer);
            Thread.sleep(300);
            datagramChannel.register(selector, SelectionKey.OP_READ);
            selector.select();
            serverResponse = (Response) new SerializationTool().deserializeObject(receiveAnswer());
            if (serverResponse.getResponseCode().equals(ResponseCode.ERROR)) {
                if (!serverResponse.getResponseBody().equals("Empty") || serverResponse.getResponseBody().equals(""))
                    AlertDisplay.showError(serverResponse.getResponseBody(), serverResponse.getResponseBodyArgs());
                return false;
            } else {
                if (!serverResponse.getResponseBody().equals("Empty") || serverResponse.getResponseBody().equals(""))
                    AlertDisplay.showInfo(serverResponse.getResponseBody(), serverResponse.getResponseBodyArgs());
                this.user = user;
//            userInterface.displayMessage("Вход успешен!");
                return true;
            }
        } catch (PortUnreachableException e) {
            AlertDisplay.showError("PortUnavailableError");
            return false;
        } catch (IOException e) {
            AlertDisplay.showError("FatalConnectionError");
            return false;
        } catch (InterruptedException e) {
            return false;
        }
    }

    public boolean register(String login, String pwd) {
        Request requestToServer = null;
        Response serverResponse = null;
        try {
            Set keys = selector.selectedKeys();
            keys.clear();
            datagramChannel.register(selector, SelectionKey.OP_WRITE);
            User user = new User(login, getHexString(pwd));
            requestToServer = new Request("register", "", null, user);
            sendRequest(requestToServer);
            Thread.sleep(300);
            datagramChannel.register(selector, SelectionKey.OP_READ);
            selector.select();
            serverResponse = (Response) new SerializationTool().deserializeObject(receiveAnswer());
            if (serverResponse.getResponseCode().equals(ResponseCode.ERROR)) {
                if (!serverResponse.getResponseBody().equals("Empty") || serverResponse.getResponseBody().equals(""))
                    AlertDisplay.showError(serverResponse.getResponseBody(), serverResponse.getResponseBodyArgs());
                return false;
            } else {
                if (!serverResponse.getResponseBody().equals("Empty") || serverResponse.getResponseBody().equals(""))
                    AlertDisplay.showInfo(serverResponse.getResponseBody(), serverResponse.getResponseBodyArgs());
                this.user = user;
//            userInterface.displayMessage("Вход успешен!");
                return true;
            }
        } catch (PortUnreachableException e) {
            AlertDisplay.showError("PortUnavailableError");
            return false;
        } catch (IOException e) {
            AlertDisplay.showError("FatalConnectionError");
            return false;
        } catch (InterruptedException e) {
            return false;
        }
    }

    public void initCollection() {
        collection = processRequestFromUser("show", "", null);
    }

    public HashSet<Worker> getCollection() {
        return collection;
    }

    public DatagramChannel getDatagramChannel() {
        return this.datagramChannel;
    }

    public Selector getSelector() {
        return this.selector;
    }

    public User getUser() {
        return this.user;
    }

    public void setCollectionRefresher(Client client) {
        collectionRefresher = new CollectionRefresher(client);
    }

    public CollectionRefresher getCollectionRefresher() {
        return this.collectionRefresher;
    }

    public void setAlertDisplay() {
        this.alertDisplay = new AlertDisplay();
    }

    public int getPort() {
        return socketAddress.getPort();
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
