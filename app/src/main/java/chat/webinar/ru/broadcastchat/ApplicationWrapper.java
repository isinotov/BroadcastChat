package chat.webinar.ru.broadcastchat;

import android.app.Application;
import android.content.Intent;

import com.orm.SugarContext;

import chat.webinar.ru.broadcastchat.Network.ServerService;

/**
 * Created by isinotov on 15/03/2016.
 */
public class ApplicationWrapper extends Application {

    private Intent service;

    @Override
    public void onCreate() {
        super.onCreate();
        service = new Intent(this, ServerService.class);
        startService(service);
        SugarContext.init(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        stopService(service);
    }
}
