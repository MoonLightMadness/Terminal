package app.host;

import app.config.Configer;
import app.utils.GeneralUtil;
import app.utils.SimpleUtils;
import app.utils.net.Sender;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

/**
 * @ClassName : app.host.Host
 * @Description :
 * @Date 2021-09-09 15:07:59
 * @Author ZhangHL
 */
public class Host {

    private SocketChannel socketChannel;

    private Selector selector;

    public void init() {
        try {
            socketChannel = SocketChannel.open();
//            selector = Selector.open();
//            socketChannel.register(selector, SelectionKey.OP_READ);
            //socketChannel.configureBlocking(false);
        } catch (IOException e) {
            GeneralUtil.error("套接字开启失败，原因：{}", e);
            e.printStackTrace();
        }
    }

    public void connectRemote() {
        String ip = GeneralUtil.readConfig("server.ip");
        String port = GeneralUtil.readConfig("server.port");
        System.out.println(ip+" "+port);
        try {
            socketChannel.connect(new InetSocketAddress(ip, Integer.parseInt(port)));
        } catch (IOException e) {
            GeneralUtil.error("连接到服务器失败，原因:{}", e);
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            socketChannel.close();
        } catch (IOException e) {
            GeneralUtil.error("关闭套接字失败，原因:{}", e);
            e.printStackTrace();
        }
    }

    public void send(String msg) {
        connectRemote();
        Sender.send(socketChannel, msg.getBytes(StandardCharsets.UTF_8));
    }

    public byte[] read() {
        byte[] res = SimpleUtils.receiveDataInNIO(socketChannel);
        close();
        return res;
    }

    //    public byte[] read() {
//        while (true){
//            try {
//                selector.select();
//                Iterator<SelectionKey> selectionKeys = selector.selectedKeys().iterator();
//                while (selectionKeys.hasNext()){
//                    SelectionKey key = selectionKeys.next();
//                    byte[] res = SimpleUtils.receiveDataInNIO((SocketChannel) key.channel());
//                    close();
//                    return res;
//                }
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            return null;
//        }
//    }

}
