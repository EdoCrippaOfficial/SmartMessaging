package elevati.inc.parser;

import java.util.ArrayList;

// Classe che tiene tutti i dati del messaggio
public class Message {

    private int priority;
    private String title, body, img, format;
    private ArrayList<String> receivers = new ArrayList<>();
    private boolean cc;

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public ArrayList<String> getReceivers() {
        return receivers;
    }

    public void addReceiver(String d) {
        receivers.add(d);
    }

    public boolean isCc() {
        return cc;
    }

    public void setCc(boolean cc) {
        this.cc = cc;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}
