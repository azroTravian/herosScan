package scanheros.gestion;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javax.imageio.ImageIO;
import scanheros.controller.Ligne;
import scanheros.controller.ScanController;
import scanheros.equipements.EquipementFemme;
import scanheros.equipements.EquipementHomme;
import scanheros.equipements.IEquipement;
import scanheros.exception.GenderNotFindException;
import scanheros.popup.PopupError;

public class TimerScanTask extends TimerTask {

    private static EquipementHomme equipH = null;
    private static IEquipement equipF = null;
    private ObservableList<Ligne> listeHero;
    private ScanController scan;

    public TimerScanTask(ObservableList<Ligne> listeHero, ScanController scanController) {
        this.listeHero = listeHero;
        scan = scanController;
        equipH = new EquipementHomme();
        equipF = new EquipementFemme();
    }

    private static void getImageFromWeb(String imageName, int idHero) {
        try {
            URL site = new URL(ScanController.getServeur() + "/hero_body.php?uid=" + idHero);
            File file = new File(imageName);
            file.createNewFile();
            InputStream in = site.openStream();
            OutputStream out = new FileOutputStream(file);
            byte[] b = new byte[2048];
            int length;
            while ((length = in.read(b)) != -1) {
                out.write(b, 0, length);
            }
            in.close();
            out.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void run() {
        long time = 0;
        long toWait = 0;
        try {
            for (Ligne ligne : listeHero) {
                time = System.nanoTime();
                String imageNom = "img" + ligne.getId() + ".png";

                /* On récupère l'image du héros*/
                getImageFromWeb(imageNom, ligne.getId());

                /* On ouvre l'image dans un BufferedImage*/
                BufferedImage image = ImageIO.read(new File(imageNom));

                /* On crée l'Enregistrement et on le rempli de ses infos visuelles*/
                Enregistrement enre = new Enregistrement(ligne.getId(), ligne.getPseudo(), image,
                    equipH, equipF);

                /* On supprime l'image, pour pas stocker inutilement des images*/
                boolean delete = new File(imageNom).delete();

                /* On termine par l'exp*/
                enre.setExperience(ScanController.getExpe().getExp(enre.getPseudo()));

                /* Ajout de l'enregistrement dans le tableau*/
                scan.getData().add(new LigneEnre(enre));

                /* Sauvegarde dans la BDD*/
                System.out.println("-----> " + enre.getPseudo());
                ScanController.getConnexion().addEnregistrement(enre);

                /* Attendre pour que l'enregistrement dure 1sec, pour pas spam TG*/
                toWait = (System.nanoTime() - time) / (1000 * 1000);
                if (toWait < 1000) {
                    Thread.sleep(1000 - toWait);
                }
                System.out.println(toWait + " " + (System.nanoTime() - time));
            }

        } catch (IOException | InterruptedException | GenderNotFindException e) {
            Logger.getLogger(getClass().getName()).log(Level.INFO, null, e);
            new PopupError(e).get().show();
        }
    }
}
