package chat.webinar.ru.broadcastchat;

import com.orm.SugarRecord;

/**
 * Created by isinotov on 15/03/2016.
 */
public class ChatMessage extends SugarRecord {
    private String textMessage;

    public ChatMessage(String s) {
        textMessage = s;
    }

    public ChatMessage(){
        textMessage = "";
    }

    public String getTextMessage() {
        return textMessage;
    }

    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }
}
