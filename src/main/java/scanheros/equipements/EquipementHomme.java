package scanheros.equipements;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class EquipementHomme implements IEquipement {

    private HashMap<Color, String> casque;
    private HashMap<Color, String> bdroit;
    private HashMap<Color, String> bgauche;
    private HashMap<Color, String> armure;
    private HashMap<Color, String> bottes;
    private HashMap<Color, Boolean> cheval;

    public EquipementHomme() {
        super();
        casque = new HashMap<>();
        bdroit = new HashMap<>();
        bgauche = new HashMap<>();
        armure = new HashMap<>();
        bottes = new HashMap<>();
        cheval = new HashMap<>();

        // BRAS DROIT
        // ------------------------------------------------------------------------------------------------------
        bdroit.put(new Color(66, 50, 40), "Épée du légionnaire");
        bdroit.put(new Color(138, 72, 39), "Épée du prétorien");
        bdroit.put(new Color(42, 23, 20), "Épée de l'imperian");
        bdroit.put(new Color(133, 97, 73), "Épée de l'imperatoris");
        bdroit.put(new Color(197, 198, 190), "Lance du caesaris");
        bdroit.put(new Color(172, 137, 105), "Pique de phalange");
        bdroit.put(new Color(124, 111, 101), "Épée du combattant");
        bdroit.put(new Color(177, 159, 138), "Arc du toutatis");
        bdroit.put(new Color(110, 58, 38), "Crosse de druide");
        bdroit.put(new Color(173, 99, 60), "Lance de léhédouin");
        bdroit.put(new Color(119, 93, 75), "Masse du combattant");
        bdroit.put(new Color(172, 164, 152), "Pique du lancier");
        bdroit.put(new Color(130, 81, 56), "Hache du combattant");
        bdroit.put(new Color(239, 228, 197), "Marteau du Paladin");
        bdroit.put(new Color(129, 130, 130), "Épée du cavalier teuton");

        // BRAS GAUCHE
        // ------------------------------------------------------------------------------------------------------
        bgauche.put(new Color(125, 36, 23), "Bouclier");
        bgauche.put(new Color(166, 125, 89), "Cor Natar");
        bgauche.put(new Color(190, 172, 130), "Sachet du voleur");
        bgauche.put(new Color(192, 171, 141), "Carte");
        bgauche.put(new Color(152, 59, 27), "Fanion");
        bgauche.put(new Color(120, 38, 16), "Banniére");

        // CASQUE
        // ------------------------------------------------------------------------------------------------------
        casque.put(new Color(192, 178, 165), "C. Sensibilisation");
        casque.put(new Color(153, 140, 130), "C. Lumiére");
        casque.put(new Color(152, 142, 134), "C. Sagesse");
        casque.put(new Color(184, 193, 190), "C. Régénération");
        casque.put(new Color(187, 194, 189), "C. Santé");
        casque.put(new Color(0, 0, 0), "C. Guérison");
        casque.put(new Color(134, 103, 68), "C. Gladiateur");
        casque.put(new Color(134, 102, 67), "C. Gladiateur");
        casque.put(new Color(134, 104, 68), "C. Gladiateur");
        casque.put(new Color(133, 102, 67), "C. Gladiateur");
        casque.put(new Color(130, 98, 63), "C. Tribun");
        casque.put(new Color(0, 0, 0), "C. Consul");
        casque.put(new Color(165, 143, 108), "C. Cavalier");
        casque.put(new Color(163, 141, 106), "C. Cavalerie");
        casque.put(new Color(0, 0, 0), "C. Cavalerie lourde");
        casque.put(new Color(153, 138, 118), "C. Mercenaire");
        casque.put(new Color(111, 97, 80), "C. Guerrier");
        casque.put(new Color(0, 0, 0), "C. Archer");

        // ARMURE
        // ------------------------------------------------------------------------------------------------------
        armure.put(new Color(230, 178, 89), "A. régénération");
        armure.put(new Color(228, 180, 90), "A. santé");
        armure.put(new Color(0, 0, 0), "A. guérison");
        armure.put(new Color(104, 122, 133), "A. légére d'écaille");
        armure.put(new Color(0, 0, 0), "A. d'écaille");
        armure.put(new Color(0, 0, 0), "A. lourde d'écaille");
        armure.put(new Color(89, 28, 7), "Cuirasse légére");
        armure.put(new Color(71, 49, 35), "Cuirasse");
        armure.put(new Color(75, 55, 43), "Cuirasse lourde");
        armure.put(new Color(162, 65, 41), "A. segmentée légére");
        armure.put(new Color(87, 64, 48), "A. segmentée");
        armure.put(new Color(41, 31, 28), "A. segmentée lourde");

        // BOTTES
        // ------------------------------------------------------------------------------------------------------
        bottes.put(new Color(46, 35, 27), "Bottes de Régénération");
        bottes.put(new Color(51, 35, 25), "Bottes de Santé");
        bottes.put(new Color(0, 0, 0), "Bottes de Guérison");
        bottes.put(new Color(114, 108, 95), "Bottes du mercenaire");
        bottes.put(new Color(86, 77, 66), "Bottes du guerrier");
        bottes.put(new Color(96, 89, 79), "Bottes de l'archer");
        bottes.put(new Color(186, 149, 117), "Petits éperons");
        bottes.put(new Color(113, 48, 26), "éperons");
        bottes.put(new Color(145, 122, 103), "éperons maléfiques");

        // CHEVAL-----------------------------------------------------------------------------------------------------
        cheval.put(new Color(255, 255, 255), false);
        cheval.put(new Color(125, 86, 43), true);

    }

    public String getCasque(BufferedImage image) {
        String res = "";
        if (casque.containsKey(new Color(image.getRGB(195, 56)))) {
            res = casque.get(new Color(image.getRGB(195, 56)));
        }
        return res;
    }

    public String getBdroit(BufferedImage image) {
        String res = "";
        if (bdroit.containsKey(new Color(image.getRGB(130, 231)))) {
            res = bdroit.get(new Color(image.getRGB(130, 231)));
        } else if (bdroit.containsKey(new Color(image.getRGB(117, 89)))) {
            res = bdroit.get(new Color(image.getRGB(117, 89)));
        } else if (bdroit.containsKey(new Color(image.getRGB(99, 233)))) {
            res = bdroit.get(new Color(image.getRGB(99, 233)));
        }
        return res;
    }

    public String getBgauche(BufferedImage image) {
        String res = "";
        if (bgauche.containsKey(new Color(image.getRGB(270, 156)))) {
            res = bgauche.get(new Color(image.getRGB(270, 156)));
        } else if (bgauche.containsKey(new Color(image.getRGB(265, 296)))) {
            res = bgauche.get(new Color(image.getRGB(265, 296)));
        } else if (bgauche.containsKey(new Color(image.getRGB(280, 156)))) {
            res = bgauche.get(new Color(image.getRGB(280, 156)));
        }
        return res;
    }

    public String getArmure(BufferedImage image) {
        String res = "";
        if (armure.containsKey(new Color(image.getRGB(205, 201)))) {
            res = armure.get(new Color(image.getRGB(205, 201)));
        } else if (armure.containsKey(new Color(image.getRGB(195, 166)))) {
            res = armure.get(new Color(image.getRGB(195, 166)));
        }
        return res;
    }

    public String getBottes(BufferedImage image) {
        String res = "";
        if (bottes.containsKey(new Color(image.getRGB(175, 356)))) {
            res = bottes.get(new Color(image.getRGB(175, 356)));
        } else if (bottes.containsKey(new Color(image.getRGB(171, 389)))) {
            res = bottes.get(new Color(image.getRGB(171, 389)));
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
