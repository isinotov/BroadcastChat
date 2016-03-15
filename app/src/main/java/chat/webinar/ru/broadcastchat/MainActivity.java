package chat.webinar.ru.broadcastchat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import chat.webinar.ru.broadcastchat.Network.Client;

public class MainActivity extends AppCompatActivity {


    ListView listView;
    ArrayAdapter<String> adapter;
    EditText messageText;
    Button sendButton;

    public static final String DATA_MESSAGE = "message";
    public static final String ACTION_MESSAGE = "chat.webinar.ru.broadcastchat.message";

    IntentFilter filter = new IntentFilter(ACTION_MESSAGE);
    Executor executor = Executors.newSingleThreadExecutor();


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.list);
        messageText = (EditText) findViewById(R.id.etMessage);
        sendButton = (Button) findViewById(R.id.sendButton);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        listView.setAdapter(adapter);
        loadFromBase();
        registerReceiver(broadcastReceiver, filter);
        sendButton.setOnClickListener(sendButtonListener);
    }

    private View.OnClickListener sendButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!messageText.getText().toString().trim().isEmpty()) {
                sendButton.setEnabled(false);
                executor.execute(sendMessageRunnable);
            }
        }
    };

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            sendButton.setEnabled(true);
            return false;
        }
    });
    private Runnable sendMessageRunnable = new Runnable() {
        @Override
        public void run() {
            try {
                Client.sendMessage(messageText.getText().toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            handler.sendEmptyMessage(0);
        }
    };


    public void loadFromBase() {
        List<ChatMessage> messages = ChatMessage.listAll(ChatMessage.class);
        LinkedList<String> adapterData = new LinkedList<>();
        for (ChatMessage message : messages) {
            adapterData.add(message.getTextMessage());
        }
        adapter.clear();
        adapter.addAll(adapterData);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            loadFromBase();
            listView.smoothScrollToPosition(adapter.getCount() - 1);
        }
    };
}
