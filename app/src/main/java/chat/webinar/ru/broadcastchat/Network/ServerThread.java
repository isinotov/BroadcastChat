package chat.webinar.ru.broadcastchat.Network;

import android.content.Context;
import android.content.Intent;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import chat.webinar.ru.broadcastchat.MainActivity;
import chat.webinar.ru.broadcastchat.ChatMessage;

/**
 * Created by isinotov on 14/03/2016.
 */
public class ServerThread extends Thread {

    Context context;

    public ServerThread(Context context) {
        this.context = context;
    }


    @Override
    public void run() {
        try {
            DatagramSocket socket = new DatagramSocket(9995);
            while (true) {
                new ChatMessage(receiveMessage(socket)).save();
                Intent intent = new Intent(MainActivity.ACTION_MESSAGE);
                context.sendBroadcast(intent);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static String receiveMessage(DatagramSocket s) throws IOException {
        DatagramPacket dp;
        byte buf[] = new byte[2048];
        dp = new DatagramPacket(buf, 2048);
        s.receive(dp);
        return new String(buf, 0, dp.getLength());
    }
}
