package view;

import controller.Controller;
import controller.InsectPlayer;
import controller.Player;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map.Entry;

import javax.imageio.ImageIO;

import model.IInsectView;
import model.Insect;


/**
 * A GInsect osztály az Insect entitások grafikus megjelenítéséért felelős.
 * Lehetővé teszi egy rovar megjelenítését és ahhoz kapcsolódó rajzolási
 * műveletek végrehajtását adott felületen.
 */
public class GInsect {
    private IInsectView insect;
    private DrawingPanel drawingPanel;


    /**
     * A GInsect osztály paraméter nélküli konstruktora.
     */
    public GInsect() { }


    /**
     * Beállítja azt a rajzoló panelt, amin a GInsect osztály elemei megjelenítésre kerülnek.
     *
     * @param drawingPanel A rajzoló panel objektuma, amely ezt az osztályt kiszolgálja
     */
    public void setDrawingPanel(DrawingPanel drawingPanel) {
        this.drawingPanel = drawingPanel;
    }


    /**
     * Beállítja a GInsect példányhoz tartozó rovar objektumot.
     *
     * @param i a beállítandó Insect objektum
     */
    public void setInsect(Insect i) {
        this.insect = i;
    }


    /**
     * Visszaadja az aktuálisan használt rovart, amely az objektum megjelenítéséért felelős.
     *
     * @return az aktuális rovar megjelenítésére szolgáló IInsectView objektum
     */
    public IInsectView getInsect() {
        return insect;
    }


    /**
     * Rajzolási művelet végrehajtása egy rovarhoz tartozóan, amely a megadott grafikus kontextusra
     * és vezérlő objektumra épül.
     *
     * @param g          A rajzolási műveletek végrehajtásához használt grafikus kontextus.
     * @param controller A vezérlő objektum, amely tartalmazza a rovarok és az azokhoz tartozó játékosok információit.
     */
    public void draw(Graphics g, Controller controller) {
        Graphics2D g2 = (Graphics2D) g;
        Point center = drawingPanel.getGTecton(insect.getPosition()).getCenter();

        InsectPlayer iPlayer = null;
        for (int i = 0; i < controller.getInsectPlayers().size(); i++) {
            for (int j = 0; j < controller.getInsectPlayers().get(i).getInsects().size(); j++) {
                if (controller.getInsectPlayers().get(i).getInsects().get(j).getInsect() == insect) {
                    iPlayer = controller.getInsectPlayers().get(i);
                    break;
                }
            }
        }

        if (iPlayer == null) {
            return;
        }

        Color playerColor = null;
        for (Entry<Player, Color> entry : drawingPanel.getGPanel().players.entrySet()) {
            if (entry.getKey() == iPlayer) {
                playerColor = entry.getValue();
                break;
            }
        }

        if (playerColor == null) {
            return;
        }

        String pathName = null;
        switch (playerColor.getRGB()) {
            case 0xFFFF0000: // Color.RED
                pathName = "bug1.png";
                break;

            case 0xFF00FF00: // Color.GREEN
                pathName = "bug3.png";
                break;

            case 0xFF0000FF: // Color.BLUE
                pathName = "bug2.png";
                break;

            case 0xFFFFFF00: // Color.YELLOW
                pathName = "bug4.png";
                break;
        }

        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(pathName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        g2.drawImage(img, center.x + 3, center.y - 28, 23, 23, null);
    }

}
