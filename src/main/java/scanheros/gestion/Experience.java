package scanheros.gestion;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import scanheros.controller.ScanController;
import scanheros.popup.PopupError;

public class Experience {

    private String id;
    private String mdp;

    public Experience(String id, String mdp) {
        super();
        this.id = id;
        this.mdp = mdp;
    }

    int getExp(String pseudo) {
        int expF = 0;
        try {
            String url = ScanController.getServeur() + "/statistiken.php?id=3&name=" + pseudo;
            System.out.println("url exp " + url);
            Connection.Response loginForm = Jsoup.connect(url).method(Connection.Method.GET)
                .execute();

            Document document = Jsoup.connect(ScanController.getServeur() + "/dorf1.php")
                .data("cookieexists", "false")
                .data("name", id).data("password", mdp).data("login", "Login")
                .cookies(loginForm.cookies()).post();

            Elements expFind = document.select(".hl .xp");
            String ligne = expFind.toString().replaceFirst("<td.*?>", "").replaceFirst("</td>", "");
            if (!ligne.isEmpty()) {
                expF = Integer.valueOf(ligne);
            }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.INFO, null, e);
            new PopupError(e).get().show();
        }
        return expF;
    }
}
