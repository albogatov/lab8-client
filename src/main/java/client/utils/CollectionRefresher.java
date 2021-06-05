package client.utils;

import client.Client;
import commons.app.Command;
import commons.app.User;
import commons.commands.Show;
import commons.elements.Worker;
import commons.utils.SerializationTool;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.HashSet;
import java.util.Set;

public class CollectionRefresher {

    private Client client;

    public CollectionRefresher(Client client) {
        this.client = client;
    }

    public HashSet<Worker> getCollection() {
        try {
            DatagramChannel datagramChannel = DatagramChannel.open();
            SocketAddress socketAddress = new InetSocketAddress(InetAddress.getLocalHost(), client.getPort());
            datagramChannel.connect(socketAddress);
            datagramChannel.configureBlocking(false);
            Selector selector = client.getSelector();
            User user = client.getUser();
            datagramChannel.register(selector, SelectionKey.OP_WRITE);
            Set keys = selector.selectedKeys();
            Command cmd = new Show();
            cmd.setUser(user);
            client.sendCommand(cmd);
            datagramChannel.register(selector, SelectionKey.OP_READ);
            selector.select();
            byte[] toBeReceived = client.receiveAnswer();
            HashSet<Worker> answer;
            String test;
            try {
                answer = (HashSet<Worker>) new SerializationTool().deserializeObject(toBeReceived);
                keys.clear();
                answer.stream().forEach(worker -> System.out.println(worker.displayWorker()));
                return answer;
            } catch (ClassCastException e) {
                test = (String) new SerializationTool().deserializeObject(toBeReceived);
                System.out.println(test);
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
