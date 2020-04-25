package server;

import java.util.ArrayList;
import java.util.List;

public class Messaggio {

    int id;
    int priorita;
    public List<String> destinatari;
    public String titolo;
    public String corpo;
    public String img;
    public boolean cc;
    public Format formattazione;

    public Messaggio(int priorita, int id) {
        this.id = id;
        this.priorita = priorita;
        destinatari = new ArrayList<>();
    }
}
