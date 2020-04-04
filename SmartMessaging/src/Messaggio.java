import java.util.ArrayList;
import java.util.List;

public class Messaggio {

    int priorita;
    List<String> destinatari;
    String titolo;
    String corpo;
    String img;
    boolean cc;
    Fromat formattazione;

    public Messaggio(int priorita){
        this.priorita = priorita;
        destinatari = new ArrayList<>();
    }
}
