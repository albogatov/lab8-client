package client;

import client.utils.AlertDisplay;
import client.utils.CollectionRefresher;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import commons.app.*;
import commons.commands.Login;
import commons.commands.Register;
import commons.commands.Show;
import commons.elements.Worker;
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

    public static void main(String[] args) throws FileNotFoundException {
        UserInterface userInterface = new UserInterface(new InputStreamReader(System.in),
                new OutputStreamWriter(System.out), true);
        try {
//            String host = userInterface.readUnlimitedArgument("Введите адрес подключения:", false);
            String host = "localhost";
            Integer port = 7855;
//            while (port == null) {
//                try {
//                    port = Integer.parseInt(userInterface.readLimitedArgument("Введите порт:", 1025, 65535, false));
//                } catch (NumberFormatException e) {
//                    userInterface.displayMessage("Порт должен быть числом");
//                }
//            }
            Client client = new Client();
            client.setCollectionRefresher(client);
            client.connect(host, port);
//            boolean entrance = false;
//            try {
//                while (!entrance)
//                    entrance = client.authorisation();
//            } catch (PortUnreachableException e) {
//                userInterface.displayMessage("Указан неверный адрес соединения, невозможно завершить авторизацию");
//                System.exit(-1);
//            }
//            client.run();
//            while (true) {
//                String confirmation = userInterface.readUnlimitedArgument("Сервер временно недоступен, хотите повторить подключение? (да/нет)", false);
//                if (confirmation.equals("да")) {
//                    client.run();
//                } else
//                    System.exit(0);
//            }
        } catch (BindException e) {
            userInterface.displayMessage("Подключение по данному порту невозможно или у вас нет на него прав");
            PrintWriter pw = new PrintWriter("errorLog.txt");
            e.printStackTrace(pw);
            pw.close();
            System.exit(-1);
        } catch (IOException | UnresolvedAddressException e) {
            userInterface.displayMessage("Подключение по данному адресу не удалось");
            PrintWriter pw = new PrintWriter("errorLog.txt");
            e.printStackTrace(pw);
            pw.close();
            System.exit(-1);
        } catch (NoSuchElementException e) {
            userInterface.displayMessage("Ввод недоступен");
            PrintWriter pw = new PrintWriter("errorLog.txt");
            e.printStackTrace(pw);
            pw.close();
            System.exit(-1);
        }
    }

    public boolean connect(String host, int port) throws IOException {
        socketAddress = new InetSocketAddress(host, port);
        datagramChannel.connect(socketAddress);
        userInterface.displayMessage("Осуществляется подключение по адресу " + host + " по порту " + port);
        return true;
    }

    public void sendCommand(Command cmd) throws IOException {
        if (SerializationTool.serializeObject(cmd) == null) {
            userInterface.displayMessage("Произошла ошибка сериализации");
            System.exit(-1);
        }
        ByteBuffer send = ByteBuffer.wrap(Objects.requireNonNull(SerializationTool.serializeObject(cmd)));
        datagramChannel.send(send, socketAddress);
    }

    public void sendRequest(Request request) throws IOException {
        if (SerializationTool.serializeObject(request) == null) {

        }
        ByteBuffer send = ByteBuffer.wrap(Objects.requireNonNull(SerializationTool.serializeObject(request)));
        datagramChannel.send(send, socketAddress);
    }

    public byte[] receiveAnswer() throws PortUnreachableException, IOException {
        byte[] buffer = new byte[65536];
        ByteBuffer bufferAnswer = ByteBuffer.wrap(buffer);
        socketAddress = (InetSocketAddress) datagramChannel.receive(bufferAnswer);
        return bufferAnswer.array();
    }

//    @Override
//    public void run() {
//        Thread updating = new Thread(() -> {
//            try {
//                Thread.sleep(10000);
//                collection = collectionRefresher.getCollection();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        });
//        updating.start();
//        try {
//            Set keys = selector.selectedKeys();
//            keys.clear();
//            Scanner scanner = new Scanner(System.in);
//            datagramChannel.register(selector, SelectionKey.OP_WRITE);
////            sendCommand(new Show());
//            while (true) {
//                int count = selector.select();
//                if (count == 0) {
//                    System.exit(0);
//                }
//                keys = selector.selectedKeys();
//                Iterator iterator = keys.iterator();
//                while (iterator.hasNext()) {
//                    SelectionKey selectionKey = (SelectionKey) iterator.next();
//                    iterator.remove();
//                    if (selectionKey.isReadable()) {
//                        byte[] toBeReceived = receiveAnswer();
//                        String answer = "";
//                        HashSet<Worker> collection;
//                        try {
//                            answer = (String) new SerializationTool().deserializeObject(toBeReceived);
//                        } catch (ClassCastException e) {
//                            collection = (HashSet<Worker>) new SerializationTool().deserializeObject(toBeReceived);
//                        }
//                        if (!answer.contains("Awaiting further client instructions.")) {
//                            userInterface.displayMessage(answer);
//                            if (answer.equals("Коллекция сохранена в файл")) {
//                                userInterface.displayMessage("Goodbye, friend");
//                                System.exit(0);
//                            }
//                        } else datagramChannel.register(selector, SelectionKey.OP_WRITE);
//                    }
//                    if (selectionKey.isWritable()) {
//                        datagramChannel.register(selector, SelectionKey.OP_READ);
//                        String input = scanner.nextLine().trim();
//                        String[] args = input.split("\\s+");
//                        if (args[0].equals("save")) {
//                            userInterface.displayMessage("Данная команда недоступна пользователю");
//                            datagramChannel.register(selector, SelectionKey.OP_WRITE);
//                        } else {
//                            Command cmd = CommandCenter.getInstance().getCmd(args[0]);
//                            if (!(cmd == null)) {
//                                cmd.setUser(user);
//                                if (cmd.getArgumentAmount() == 0) {
//                                    sendCommand(cmd);
//                                }
//                                if (cmd.getArgumentAmount() == 1 && cmd.getNeedsObject()) {
//                                    Worker worker = userInterface.readWorker(userInterface);
//                                    cmd.setObject(worker);
//                                    sendCommand(cmd);
//                                }
//                                if (cmd.getArgumentAmount() == 1 && !cmd.getNeedsObject()) {
//                                    cmd.setArgument(args[1]);
//                                    sendCommand(cmd);
//                                }
//                                if (cmd.getArgumentAmount() == 2 && cmd.getNeedsObject()) {
//                                    try {
//                                        cmd.setArgument(args[1]);
//                                        Worker worker = userInterface.readWorker(userInterface);
//                                        cmd.setObject(worker);
//                                        sendCommand(cmd);
//                                    } catch (ArrayIndexOutOfBoundsException e) {
//                                        userInterface.displayMessage("Неверно указан аргумент команды");
//                                        datagramChannel.register(selector, SelectionKey.OP_WRITE);
//                                    }
//                                }
//                            } else {
//                                userInterface.displayMessage("Введена несуществующая команда, используйте команду help, " +
//                                        "чтобы получить список возможных команд");
//                                datagramChannel.register(selector, SelectionKey.OP_WRITE);
//                            }
//                        }
//                    }
//                }
//            }
//        } catch (PortUnreachableException e) {
//            userInterface.displayMessage("Порт недоступен");
//        } catch (BindException e) {
//            userInterface.displayMessage("Подключение по данному порту невозможно или у вас нет на него прав");
//        } catch (UnresolvedAddressException e) {
//            userInterface.displayMessage("Подключение по данному адресу не удалось");
//        } catch (IOException e) {
//            userInterface.displayMessage("Произошла неизвестная ошибка ввода-вывода");
//        }
//    }

//    public boolean authorisation(String action, String login, String pwd) throws IOException {
////        String action = userInterface.readUnlimitedArgument("Здравствуйте! Введите login, если вы уже зарегистрированы. В ином случае, введите register.", false);
//        if (action.equals("login")) {
//            return login();
//        } else {
//            if (action.equals("register")) {
//                return register();
//            } else return false;
//        }
//    }

    public HashSet<Worker> processRequestFromUser(String cmd, String argument, Worker object) {
        Request requestToServer = null;
        Response serverResponse = null;
        try {
            Set keys = selector.selectedKeys();
            keys.clear();
            datagramChannel.register(selector, SelectionKey.OP_WRITE);
            requestToServer = new Request(cmd, argument, argument, user);
            sendRequest(requestToServer);
            datagramChannel.register(selector, SelectionKey.OP_READ);
            selector.select();
            serverResponse = (Response) new SerializationTool().deserializeObject(receiveAnswer());
            return serverResponse.getCollection();
//            Set keys = selector.selectedKeys();
//            keys.clear();
//            datagramChannel.register(selector, SelectionKey.OP_WRITE);
////            sendCommand(new Show());
////            while (true) {
//            int count = selector.select();
//            if (count == 0) {
//                System.exit(0);
//            }
//            keys = selector.selectedKeys();
//            Iterator iterator = keys.iterator();
//            while (iterator.hasNext()) {
//                SelectionKey selectionKey = (SelectionKey) iterator.next();
//                iterator.remove();
//                if (selectionKey.isReadable()) {
//                    byte[] toBeReceived = receiveAnswer();
//                    String answer = (String) new SerializationTool().deserializeObject(toBeReceived);
//                    System.out.println(answer);
//                    if (answer.contains("Collection Incoming")) {
//                        toBeReceived = receiveAnswer();
//                        collection = (HashSet<Worker>) new SerializationTool().deserializeObject(toBeReceived);
//                        System.out.println(collection);
//                    }
//                    if (!answer.contains("Awaiting further client instructions.")) {
//                        userInterface.displayMessage(answer);
////                        if (answer.equals("Коллекция сохранена в файл")) {
////                            userInterface.displayMessage("Goodbye, friend");
////                            System.exit(0);
////                        }
//                    } else datagramChannel.register(selector, SelectionKey.OP_WRITE);
//                }
//                if (selectionKey.isWritable()) {
//                    datagramChannel.register(selector, SelectionKey.OP_READ);
//                    Command command = CommandCenter.getInstance().getCmd(cmd);
//                    if (!argument.equals(""))
//                        command.setArgument(argument);
//                    if (!(object == null))
//                        command.setObject(object);
//                    command.setUser(user);
//                    sendCommand(command);
//                }
        } catch (PortUnreachableException e) {
            userInterface.displayMessage("Порт недоступен");
            return null;
        } catch (BindException e) {
            userInterface.displayMessage("Подключение по данному порту невозможно или у вас нет на него прав");
            return null;
        } catch (UnresolvedAddressException e) {
            userInterface.displayMessage("Подключение по данному адресу не удалось");
            return null;
        } catch (IOException e) {
            userInterface.displayMessage("Произошла неизвестная ошибка ввода-вывода");
            return null;
        }
    }

    public boolean login(String login, String pwd) {
        try {
            Set keys = selector.selectedKeys();
            keys.clear();
            datagramChannel.register(selector, SelectionKey.OP_WRITE);
            pwd = getHexString(pwd);
            Command cmd = new Login();
            User user = new User(login, pwd);
            cmd.setUser(user);
            sendCommand(cmd);
            datagramChannel.register(selector, SelectionKey.OP_READ);
            selector.select();
            byte[] toBeReceived = receiveAnswer();
            String answer = (String) new SerializationTool().deserializeObject(toBeReceived);
            if (answer.contains(" не "))
                return false;
            else {
                this.user = user;
//            userInterface.displayMessage("Вход успешен!");
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
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
            sendCommand(cmd);
            datagramChannel.register(selector, SelectionKey.OP_READ);
            selector.select();
            byte[] toBeReceived = receiveAnswer();
            String answer = (String) new SerializationTool().deserializeObject(toBeReceived);
            keys.clear();
            if (answer.contains(" не "))
                return false;
            else {
                this.user = user;
//                userInterface.displayMessage("Вход успешен!");
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void initCollection() {
        collection = collectionRefresher.getCollection();
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
