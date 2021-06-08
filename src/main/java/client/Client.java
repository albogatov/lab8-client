package client;

import client.utils.AlertDisplay;
import client.utils.CollectionRefresher;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
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

//    public void sendCommand(Command cmd) throws IOException {
//        if (SerializationTool.serializeObject(cmd) == null) {
//            userInterface.displayMessage("Произошла ошибка сериализации");
//            System.exit(-1);
//        }
//        ByteBuffer send = ByteBuffer.wrap(Objects.requireNonNull(SerializationTool.serializeObject(cmd)));
//        datagramChannel.send(send, socketAddress);
//    }

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
            System.out.println(serverResponse.getResponseBody() + " response body");
            if (serverResponse.getResponseCode().equals(ResponseCode.ERROR))
                AlertDisplay.showError(serverResponse.getResponseBody());
            else AlertDisplay.showInfo(serverResponse.getResponseBody());
            return serverResponse.getCollection();
        } catch (PortUnreachableException e) {
            AlertDisplay.showError("PortUnavailableError");
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
            return null;
        }
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
            System.out.println(serverResponse.getResponseBody() + "response body " + serverResponse.getResponseCode().toString());
            if (serverResponse.getResponseCode().equals(ResponseCode.ERROR)) {
//                if (!serverResponse.getResponseBody().equals("Empty"))
                AlertDisplay.showError(serverResponse.getResponseBody());
                return false;
            } else {
//                if (!serverResponse.getResponseBody().equals("Empty"))
                AlertDisplay.showInfo(serverResponse.getResponseBody());
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
        try {
            datagramChannel.register(selector, SelectionKey.OP_WRITE);
            Set keys = selector.selectedKeys();
//            String login = userInterface.readUnlimitedArgument("Придумайте логин:", false);
//            do {
            pwd = getHexString(pwd);
//            } while (password.isEmpty());
            Command cmd = new Register();
            User user = new User(login, pwd);
            cmd.setUser(user);
            sendRequest(new Request(cmd.getCommand(), "", user));
//            sendCommand(cmd);
//            sendRequest(new Request("login", ));
            datagramChannel.register(selector, SelectionKey.OP_READ);
            selector.select();
            byte[] toBeReceived = receiveAnswer();
            Response response = (Response) new SerializationTool().deserializeObject(toBeReceived);
            keys.clear();
            if (response.getResponseCode().equals(ResponseCode.ERROR))
                return false;
            else {
                this.user = user;
//                userInterface.displayMessage("Вход успешен!");
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
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
}
