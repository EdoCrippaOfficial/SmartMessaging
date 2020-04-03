import java.util.HashMap;

public class smartmessageCustomListener extends smartmessageBaseListener {

    private HashMap<String, String> variabili = new HashMap<>();

    @Override
    public void exitPriorita(smartmessageParser.PrioritaContext ctx) {
        variabili.put("priorita", ctx.NUM().getText());
        System.out.println("PRIORITA' " + ctx.NUM().getText());
    }

    @Override
    public void exitInvia(smartmessageParser.InviaContext ctx) {
        System.out.println("INVIA");
    }

    @Override
    public void exitTitolo(smartmessageParser.TitoloContext ctx) {
        variabili.put("titolo", ctx.TESTO().getText());
        System.out.println("TITOLO " + ctx.TESTO().getText());
    }

    @Override
    public void exitCorpo(smartmessageParser.CorpoContext ctx) {
        variabili.put("corpo", ctx.TESTO().getText());
        System.out.println("CORPO " + ctx.TESTO().getText());
    }

    @Override
    public void exitOpzioni(smartmessageParser.OpzioniContext ctx) {
        if (ctx.cc() != null){
            variabili.put("cc", "true");
            System.out.println("CC TRUE");
        }else{
            variabili.put("cc", "false");
            System.out.println("CC FALSE");
        }
    }

    @Override
    public void exitImg(smartmessageParser.ImgContext ctx) {
        if (ctx.TESTO() != null){
            variabili.put("img", ctx.TESTO().getText());
            System.out.println("IMG " + ctx.TESTO().getText());
        }
    }



}
