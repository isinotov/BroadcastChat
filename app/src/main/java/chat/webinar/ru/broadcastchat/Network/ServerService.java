package chat.webinar.ru.broadcastchat.network;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

/**
 * Created by isinotov on 14/03/2016.
 */
public class ServerService extends IntentService {

    private Thread serverThread;

    public ServerService() {
        super("Server");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d("SERVER", "Start Server Service");
        serverThread = new ServerThread(getApplicationContext());
        serverThread.start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        serverThread.interrupt();
    }
}
