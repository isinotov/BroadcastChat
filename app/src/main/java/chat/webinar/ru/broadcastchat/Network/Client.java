package chat.webinar.ru.broadcastchat.Network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.List;

/**
 * Created by isinotov on 14/03/2016.
 */
public class Client {
    public static void sendMessage(String str) throws IOException {
        DatagramSocket socket = new DatagramSocket();
        byte[] buf = str.getBytes();
        InetAddress broadcastAddress = getBroadcastAddress();
        if (broadcastAddress == null)
            return;
        DatagramPacket datagramPacket = new DatagramPacket(buf, buf.length,
                broadcastAddress, 9995);
        socket.send(datagramPacket);
    }

    public static InetAddress getBroadcastAddress() throws IOException {
        for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
            NetworkInterface intf = en.nextElement();
            if (!intf.isLoopback()) {
                List<InterfaceAddress> intfaddrs = intf.getInterfaceAddresses();
                for (InterfaceAddress addr : intfaddrs) {
                    if (addr.getBroadcast() != null)
                        return addr.getBroadcast();
                }
            }
        }
        return null;
    }
}
