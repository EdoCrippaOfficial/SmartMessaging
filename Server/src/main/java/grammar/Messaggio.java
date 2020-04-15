package grammar;

import java.util.ArrayList;
import java.util.List;

public class Messaggio {

    int priorita;
    List<String> destinatari;
    String titolo;
    String corpo;
    String img;
    boolean cc;
    Format formattazione;

    public Messaggio(int priorita){
        this.priorita = priorita;
        destinatari = new ArrayList<>();
    }
}
