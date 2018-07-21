package scanheros.gestion;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.sql.Timestamp;
import scanheros.Exception.GenderNotFindException;
import scanheros.equipements.EquipementHomme;
import scanheros.equipements.IEquipement;

public class Enregistrement {

    private int id;
    private String pseudo;
    private String casque;
    private String bdroit;
    private String bgauche;
    private String armure;
    private String bottes;
    private boolean cheval;
    private int experience;
    private Timestamp date;

    Enregistrement(int id) {
        super();
        this.id = id;
        pseudo = "";
        casque = "";
        bdroit = "";
        bgauche = "";
        armure = "";
        bottes = "";
        cheval = false;
        experience = 0;
        date = new Timestamp(System.currentTimeMillis());
    }

    Enregistrement(int id, String pseudo, BufferedImage image, EquipementHomme equipH,
        IEquipement equipF) throws GenderNotFindException {
        super();
        Color couleur = new Color(image.getRGB(173, 308), false);
        IEquipement equip = null;
        if (couleur.equals(new Color(241, 219, 188))) {
            equip = equipH;
        } else if (couleur.equals(new Color(208, 173, 142))) {
            equip = equipF;
        } else {
            throw new GenderNotFindException();
        }

        this.id = id;
        this.pseudo = pseudo;
        casque = equip.getCasque(image);
        bdroit = equip.getBdroit(image);
        bgauche = equip.getBgauche(image);
        armure = equip.getArmure(image);
        bottes = equip.getBottes(image);
        cheval = equip.getCheval(image);
        experience = 0;
        date = new Timestamp(System.currentTimeMillis());

    }

    public Enregistrement(int id, String pseudo, String casque, String bdroit, String bgauche,
        String armure, String bottes, boolean cheval, int experience) {
        super();
        this.id = id;
        this.pseudo = pseudo;
        this.casque = casque;
        this.bdroit = bdroit;
        this.bgauche = bgauche;
        this.armure = armure;
        this.bottes = bottes;
        this.cheval = cheval;
        this.experience = experience;
        date = new Timestamp(System.currentTimeMillis());
    }


    public Enregistrement(int id, String pseudo, String casque, String bdroit, String bgauche,
        String armure,
        String bottes, boolean cheval, int experience, Timestamp date) {
        super();
        this.id = id;
        this.pseudo = pseudo;
        this.casque = casque;
        this.bdroit = bdroit;
        this.bgauche = bgauche;
        this.armure = armure;
        this.bottes = bottes;
        this.cheval = cheval;
        this.experience = experience;
        this.date = date;
    }

    @Override
    public String toString() {
        return "Enregistrement [id=" + id + ", pseudo=" + pseudo + ", casque=" + casque
            + ", bdroit=" + bdroit
            + ", bgauche=" + bgauche + ", armure=" + armure + ", bottes=" + bottes + ", cheval="
            + cheval
            + ", experience=" + experience + ", date=" + date + "]";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getCasque() {
        return casque;
    }

    public void setCasque(String casque) {
        this.casque = casque;
    }

    public String getBdroit() {
        return bdroit;
    }

    public void setBdroit(String bdroit) {
        this.bdroit = bdroit;
    }

    public String getBgauche() {
        return bgauche;
    }

    public void setBgauche(String bgauche) {
        this.bgauche = bgauche;
    }

    public String getArmure() {
        return armure;
    }

    public void setArmure(String armure) {
        this.armure = armure;
    }

    public String getBottes() {
        return bottes;
    }

    public void setBottes(String bottes) {
        this.bottes = bottes;
    }

    public boolean isCheval() {
        return cheval;
    }

    public void setCheval(boolean cheval) {
        this.cheval = cheval;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public void setDateNow() {
        date = new Timestamp(System.currentTimeMillis());
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }


}
