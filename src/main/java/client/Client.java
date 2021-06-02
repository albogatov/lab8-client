package client;

import commons.app.Command;
import commons.app.CommandCenter;
import commons.app.User;
import commons.commands.Login;
import commons.commands.Register;
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

import static client.PasswordEncoder.getHexString;

public class Client implements Runnable {
    private SocketAddress socketAddress;
    private final DatagramChannel datagramChannel;
    private final Selector selector;
    private final UserInterface userInterface = new UserInterface(new InputStreamReader(System.in),
            new OutputStreamWriter(System.out), true);
    private User user = null;

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
            String host = userInterface.readUnlimitedArgument("Введите адрес подключения:", false);
            Integer port = null;
            while (port == null) {
                try {
                    port = Integer.parseInt(userInterface.readLimitedArgument("Введите порт:", 1025, 65535, false));
                } catch (NumberFormatException e) {
                    userInterface.displayMessage("Порт должен быть числом");
                }
            }
            Client client = new Client();
            client.connect(host, port);
            boolean entrance = false;
            try {
                while (!entrance)
                    entrance = client.authorisation();
            } catch (PortUnreachableException e) {
                userInterface.displayMessage("Указан неверный адрес соединения, невозможно завершить авторизацию");
                System.exit(-1);
            }
            client.run();
            while (true) {
                String confirmation = userInterface.readUnlimitedArgument("Сервер временно недоступен, хотите повторить подключение? (да/нет)", false);
                if (confirmation.equals("да")) {
                    client.run();
                } else
                    System.exit(0);
            }
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

    public void connect(String host, int port) throws IOException {
        socketAddress = new InetSocketAddress(host, port);
        datagramChannel.connect(socketAddress);
        userInterface.displayMessage("Осуществляется подключение по адресу " + host + " по порту " + port);
    }

    public void sendCommand(Command cmd) throws IOException {
        if (SerializationTool.serializeObject(cmd) == null) {
            userInterface.displayMessage("Произошла ошибка сериализации");
            System.exit(-1);
        }
        ByteBuffer send = ByteBuffer.wrap(Objects.requireNonNull(SerializationTool.serializeObject(cmd)));
        datagramChannel.send(send, socketAddress);
    }

    private byte[] receiveAnswer() throws PortUnreachableException, IOException {
        byte[] buffer = new byte[65536];
        ByteBuffer bufferAnswer = ByteBuffer.wrap(buffer);
        socketAddress = datagramChannel.receive(bufferAnswer);
        return bufferAnswer.array();
    }

    @Override
    public void run() {
        try {
            Set keys = selector.selectedKeys();
            keys.clear();
            Scanner scanner = new Scanner(System.in);
            datagramChannel.register(selector, SelectionKey.OP_WRITE);
//            sendCommand(new Help());
            while (true) {
                int count = selector.select();
                if (count == 0) {
                    System.exit(0);
                }
                keys = selector.selectedKeys();
                Iterator iterator = keys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = (SelectionKey) iterator.next();
                    iterator.remove();
                    if (selectionKey.isReadable()) {
                        byte[] toBeReceived = receiveAnswer();
                        String answer = (String) new SerializationTool().deserializeObject(toBeReceived);
                        if (!answer.contains("Awaiting further client instructions.")) {
                            userInterface.displayMessage(answer);
                            if (answer.equals("Коллекция сохранена в файл")) {
                                userInterface.displayMessage("Goodbye, friend");
                                System.exit(0);
                            }
                        } else datagramChannel.register(selector, SelectionKey.OP_WRITE);
                    }
                    if (selectionKey.isWritable()) {
                        datagramChannel.register(selector, SelectionKey.OP_READ);
                        String input = scanner.nextLine().trim();
                        String[] args = input.split("\\s+");
                        if (args[0].equals("save")) {
                            userInterface.displayMessage("Данная команда недоступна пользователю");
                            datagramChannel.register(selector, SelectionKey.OP_WRITE);
                        } else {
                            Command cmd = CommandCenter.getInstance().getCmd(args[0]);
                            if (!(cmd == null)) {
                                cmd.setUser(user);
                                if (cmd.getArgumentAmount() == 0) {
                                    sendCommand(cmd);
                                }
                                if (cmd.getArgumentAmount() == 1 && cmd.getNeedsObject()) {
                                    Worker worker = userInterface.readWorker(userInterface);
                                    cmd.setObject(worker);
                                    sendCommand(cmd);
                                }
                                if (cmd.getArgumentAmount() == 1 && !cmd.getNeedsObject()) {
                                    cmd.setArgument(args[1]);
                                    sendCommand(cmd);
                                }
                                if (cmd.getArgumentAmount() == 2 && cmd.getNeedsObject()) {
                                    try {
                                        cmd.setArgument(args[1]);
                                        Worker worker = userInterface.readWorker(userInterface);
                                        cmd.setObject(worker);
                                        sendCommand(cmd);
                                    } catch (ArrayIndexOutOfBoundsException e) {
                                        userInterface.displayMessage("Неверно указан аргумент команды");
                                        datagramChannel.register(selector, SelectionKey.OP_WRITE);
                                    }
                                }
                            } else {
                                userInterface.displayMessage("Введена несуществующая команда, используйте команду help, " +
                                        "чтобы получить список возможных команд");
                                datagramChannel.register(selector, SelectionKey.OP_WRITE);
                            }
                        }
                    }
                }
            }
        } catch (PortUnreachableException e) {
            userInterface.displayMessage("Порт недоступен");
        } catch (BindException e) {
            userInterface.displayMessage("Подключение по данному порту невозможно или у вас нет на него прав");
        } catch (UnresolvedAddressException e) {
            userInterface.displayMessage("Подключение по данному адресу не удалось");
        } catch (IOException e) {
            userInterface.displayMessage("Произошла неизвестная ошибка ввода-вывода");
        }
    }

    public boolean authorisation() throws IOException {
        String action = userInterface.readUnlimitedArgument("Здравствуйте! Введите login, если вы уже зарегистрированы. В ином случае, введите register.", false);
        if (action.equals("login")) {
            return login();
        } else {
            if (action.equals("register")) {
                return register();
            } else return false;
        }
    }

    public boolean login() throws IOException {
        Set keys = selector.selectedKeys();
        keys.clear();
        datagramChannel.register(selector, SelectionKey.OP_WRITE);
        String login = userInterface.readUnlimitedArgument("Введите ваш логин:", false);
        String password = getHexString(userInterface.readUnlimitedArgument("Введите пароль", false));
        Command cmd = new Login();
        User user = new User(login, password);
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
            userInterface.displayMessage("Вход успешен!");
            return true;
        }
    }

    public boolean register() {
        try {
            datagramChannel.register(selector, SelectionKey.OP_WRITE);
            Set keys = selector.selectedKeys();
            String login = userInterface.readUnlimitedArgument("Придумайте логин:", false);
            String password = "";
            do {
                password = getHexString(userInterface.readUnlimitedArgument("Введите пароль", false));
            } while (password.isEmpty());
            Command cmd = new Register();
            User user = new User(login, password);
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
                userInterface.displayMessage("Вход успешен!");
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
