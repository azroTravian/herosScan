package scanheros.equipements;

import java.awt.image.BufferedImage;

public interface IEquipement {

    String getCasque(BufferedImage image);

    String getBdroit(BufferedImage image);

    String getBgauche(BufferedImage image);

    String getArmure(BufferedImage image);

    String getBottes(BufferedImage image);

    Boolean getCheval(BufferedImage image);
}
