package scanheros.equipements;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class EquipementFemme implements IEquipement {

    private HashMap<Color, String> casque;
    private HashMap<Color, String> bdroit;
    private HashMap<Color, String> bgauche;
    private HashMap<Color, String> armure;
    private HashMap<Color, String> bottes;
    private HashMap<Color, Boolean> cheval;

    public EquipementFemme() {
        super();
        casque = new HashMap<>();
        bdroit = new HashMap<>();
        bgauche = new HashMap<>();
        armure = new HashMap<>();
        bottes = new HashMap<>();
        cheval = new HashMap<>();

        // BRAS DROIT
        // ------------------------------------------------------------------------------------------------------
        bdroit.put(new Color(0, 0, 0), "Épée du légionnaire");
        bdroit.put(new Color(0, 0, 0), "Épée du prétorien");
        bdroit.put(new Color(162, 115, 66), "Épée de l'imperian");
        bdroit.put(new Color(93, 50, 33), "Épée de l'imperatoris");
        bdroit.put(new Color(0, 0, 0), "Lance du caesaris");
        bdroit.put(new Color(168, 131, 89), "Pique de phalange");
        bdroit.put(new Color(0, 0, 0), "Épée du combattant");
        bdroit.put(new Color(0, 0, 0), "Arc du toutatis");
        bdroit.put(new Color(163, 114, 80), "Crosse de druide");
        bdroit.put(new Color(211, 112, 53), "Lance de l’hédouin");
        bdroit.put(new Color(131, 107, 86), "Masse du combattant");
        bdroit.put(new Color(0, 0, 0), "Pique du lancier");
        bdroit.put(new Color(0, 0, 0), "Hache du combattant");
        bdroit.put(new Color(0, 0, 0), "Marteau du Paladin");
        bdroit.put(new Color(137, 106, 82), "Épée du cavalier teuton");

        // BRAS GAUCHE
        // ------------------------------------------------------------------------------------------------------
        bgauche.put(new Color(93, 44, 18), "Bouclier");
        bgauche.put(new Color(236, 225, 217), "Cor Natar");
        bgauche.put(new Color(0, 0, 0), "Sachet du voleur");
        bgauche.put(new Color(179, 169, 143), "Carte");
        bgauche.put(new Color(152, 58, 26), "Fanion");
        bgauche.put(new Color(123, 37, 15), "Bannière");

        // CASQUE
        // ------------------------------------------------------------------------------------------------------
        casque.put(new Color(155, 141, 134), "Casque de Sensibilisation");
        casque.put(new Color(150, 138, 133), "Casque de Lumière");
        casque.put(new Color(0, 0, 0), "Casque de Sagesse");
        casque.put(new Color(175, 161, 125), "Casque de Régénération");
        casque.put(new Color(0, 0, 0), "Casque de Santé");
        casque.put(new Color(0, 0, 0), "Casque de Guérison");
        casque.put(new Color(68, 51, 38), "Casque du Gladiateur");
        casque.put(new Color(66, 49, 37), "Casque du Gladiateur");
        casque.put(new Color(68, 52, 39), "Casque du Gladiateur");
        casque.put(new Color(70, 53, 39), "Casque du Gladiateur");
        casque.put(new Color(64, 49, 37), "Casque du Gladiateur");
        casque.put(new Color(62, 47, 36), "Casque du Tribun");
        casque.put(new Color(63, 47, 37), "Casque du Tribun");
        casque.put(new Color(0, 0, 0), "Casque du Consul");
        casque.put(new Color(80, 60, 44), "Casque du Cavalier");
        casque.put(new Color(83, 65, 49), "Casque de la Cavalerie");
        casque.put(new Color(0, 0, 0), "Casque de la Cavalerie lourde");
        casque.put(new Color(147, 154, 156), "Casque du Mercenaire");
        casque.put(new Color(0, 0, 0), "Casque du Guerrier");
        casque.put(new Color(0, 0, 0), "Casque de l'Archer");

        // ARMURE
        // ------------------------------------------------------------------------------------------------------
        armure.put(new Color(241, 200, 106), "Armure de régénération");
        armure.put(new Color(0, 0, 0), "Armure de santé");
        armure.put(new Color(0, 0, 0), "Armure de guérison");
        armure.put(new Color(89, 20, 20), "Armure légère d'écaille");
        armure.put(new Color(194, 205, 207), "Armure d'écaille");
        armure.put(new Color(0, 0, 0), "Armure lourde d'écaille");
        armure.put(new Color(109, 78, 57), "Cuirasse légère");
        armure.put(new Color(0, 0, 0), "Cuirasse");
        armure.put(new Color(0, 0, 0), "Cuirasse lourde");
        armure.put(new Color(154, 64, 51), "Armure segmentée légère");
        armure.put(new Color(0, 0, 0), "Armure segmentée");
        armure.put(new Color(0, 0, 0), "Armure segmentée lourde");

        // BOTTES
        // ------------------------------------------------------------------------------------------------------
        bottes.put(new Color(43, 36, 28), "Bottes de Régénération");
        bottes.put(new Color(115, 93, 70), "Bottes de Santé");
        bottes.put(new Color(0, 0, 0), "Bottes de Guérison");
        bottes.put(new Color(86, 81, 77), "Bottes du mercenaire");
        bottes.put(new Color(0, 0, 0), "Bottes du guerrier");
        bottes.put(new Color(0, 0, 0), "Bottes de l'archer");
        bottes.put(new Color(75, 47, 35), "Petits éperons");
        bottes.put(new Color(59, 28, 21), "Éperons");
        bottes.put(new Color(0, 0, 0), "Éperons maléfiques");

        // CHEVAL-----------------------------------------------------------------------------------------------------
        cheval.put(new Color(255, 255, 255), false);
        cheval.put(new Color(125, 86, 43), true);

    }

    public String getCasque(BufferedImage image) {
        String res = "";
        if (casque.containsKey(new Color(image.getRGB(205, 76)))) {
            res = casque.get(new Color(image.getRGB(205, 76)));
        }
        return res;
    }

    public String getBdroit(BufferedImage image) {
        String res = "";
        if (bdroit.containsKey(new Color(image.getRGB(135, 243)))) {
            res = bdroit.get(new Color(image.getRGB(135, 243)));
        } else if (bdroit.containsKey(new Color(image.getRGB(124, 90)))) {
            res = bdroit.get(new Color(image.getRGB(124, 90)));
        }
        return res;
    }

    public String getBgauche(BufferedImage image) {
        String res = "";
        if (bgauche.containsKey(new Color(image.getRGB(280, 176)))) {
            res = bgauche.get(new Color(image.getRGB(280, 176)));
        } else if (bgauche.containsKey(new Color(image.getRGB(265, 296)))) {
            res = bgauche.get(new Color(image.getRGB(265, 296)));
        }
        return res;
    }

    public String getArmure(BufferedImage image) {
        String res = "";
        if (armure.containsKey(new Color(image.getRGB(195, 226)))) {
            res = armure.get(new Color(image.getRGB(195, 226)));
        }
        return res;
    }

    public String getBottes(BufferedImage image) {
        String res = "";
        if (bottes.containsKey(new Color(image.getRGB(165, 336)))) {
            res = bottes.get(new Color(image.getRGB(165, 336)));
        } else if (bottes.containsKey(new Color(image.getRGB(154, 362)))) {
            res = bottes.get(new Color(image.getRGB(154, 362)));
        }
        return res;
    }

    public Boolean getCheval(BufferedImage image) {
        if (cheval.containsKey(new Color(image.getRGB(105, 146)))) {
            return cheval.get(new Color(image.getRGB(105, 146)));
        }
        return false;
    }

}
