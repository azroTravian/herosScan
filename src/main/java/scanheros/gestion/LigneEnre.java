package scanheros.gestion;

import java.sql.Timestamp;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;


public class LigneEnre {

    private SimpleIntegerProperty id;
    private SimpleStringProperty pseudo;
    private SimpleStringProperty casque;
    private SimpleStringProperty bdroit;
    private SimpleStringProperty bgauche;
    private SimpleStringProperty armure;
    private SimpleStringProperty bottes;
    private SimpleBooleanProperty cheval;
    private SimpleIntegerProperty experience;
    private ObjectProperty<Timestamp> date;

    public LigneEnre(Enregistrement enregistrement) {
        super();
        id = new SimpleIntegerProperty(enregistrement.getId());
        pseudo = new SimpleStringProperty(enregistrement.getPseudo());
        casque = new SimpleStringProperty(enregistrement.getCasque());
        bdroit = new SimpleStringProperty(enregistrement.getBdroit());
        bgauche = new SimpleStringProperty(enregistrement.getBgauche());
        armure = new SimpleStringProperty(enregistrement.getArmure());
        bottes = new SimpleStringProperty(enregistrement.getBottes());
        cheval = new SimpleBooleanProperty(enregistrement.isCheval());
        experience = new SimpleIntegerProperty(enregistrement.getExperience());
        date = new SimpleObjectProperty<>(enregistrement.getDate());
    }

    @Override
    public String toString() {
        return "LigneEnre [id=" + id.get() + ", pseudo=" + pseudo.get() + ", casque=" + casque.get()
            + ", bdroit=" + bdroit.get() + ", bgauche="
            + bgauche.get() + ", armure=" + armure.get() + ", bottes=" + bottes.get() + ", cheval="
            + cheval.get() + ", experience="
            + experience.get() + ", date=" + date.get() + "]";
    }

    public int getId() {
        return id.get();
    }

    public void setId(SimpleIntegerProperty id) {
        this.id = id;
    }

    public String getPseudo() {
        return pseudo.get();
    }

    public void setPseudo(SimpleStringProperty pseudo) {
        this.pseudo = pseudo;
    }

    public String getCasque() {
        return casque.get();
    }

    public void setCasque(SimpleStringProperty casque) {
        this.casque = casque;
    }

    public String getBdroit() {
        return bdroit.get();
    }

    public void setBdroit(SimpleStringProperty bdroit) {
        this.bdroit = bdroit;
    }

    public String getBgauche() {
        return bgauche.get();
    }

    public void setBgauche(SimpleStringProperty bgauche) {
        this.bgauche = bgauche;
    }

    public String getArmure() {
        return armure.get();
    }

    public void setArmure(SimpleStringProperty armure) {
        this.armure = armure;
    }

    public String getBottes() {
        return bottes.get();
    }

    public void setBottes(SimpleStringProperty bottes) {
        this.bottes = bottes;
    }

    public boolean getCheval() {
        return cheval.get();
    }

    public void setCheval(SimpleBooleanProperty cheval) {
        this.cheval = cheval;
    }

    public int getExperience() {
        return experience.get();
    }

    public void setExperience(SimpleIntegerProperty experience) {
        this.experience = experience;
    }

    public Timestamp getDate() {
        return date.get();
    }

    public void setDate(ObjectProperty<Timestamp> date) {
        this.date = date;
    }

}
