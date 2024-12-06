import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

// Custom class for generating random image icons for program testing
public class CustomImageIcon extends ImageIcon {

    public CustomImageIcon() {
        super(createImage());
    }

    private static Image createImage() {
        Color randomColor = new Color((int) (Math.random() * 0x1000000)); // Select a random color
        BufferedImage image = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = image.createGraphics();
        g.setColor(randomColor);
        g.fillRect(0, 0, 100, 100);
        g.dispose();
        return image;
    }
}