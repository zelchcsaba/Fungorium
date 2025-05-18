package view;

import controller.Controller;
import controller.FungusPlayer;
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

import model.IMushroomView;
import model.Mushroom;
import model.MushroomState;


/**
 * A GMushroom osztály felelős a Gombát megjelenítő view objektum kezeléséért, valamint
 * a gomba rajzolásának implementálásáért a játék felületén.
 */
public class GMushroom {
    private IMushroomView m;
    private DrawingPanel drawingPanel;


    /**
     * A GMushroom osztály paraméter nélküli konstruktora.
     */
    public GMushroom() { }


    /**
     * Beállítja a DrawingPanel-t, amely a grafikus megjelenítésekért felelős.
     *
     * @param drawingPanel a használni kívánt rajzoló panel
     */
    public void setDrawingPanel(DrawingPanel drawingPanel) {
        this.drawingPanel = drawingPanel;
    }


    /**
     * Beállítja a gombát, amelyet a GMushroom osztály képvisel.
     *
     * @param m A Mushroom típusú objektum, amelyet a view-hoz társítani kívánunk.
     */
    public void setMushroom(Mushroom m) {
        this.m = m;
    }


    /**
     * Visszaadja a gombát megjelenítő IMushroomView típusú példányt, amely a gomba grafikus
     * megjelenítéséért és állapotainak lekérdezéséért felelős.
     *
     * @return az aktuális gombát megjelenítő IMushroomView példány
     */
    public IMushroomView getMushroom() {
        return m;
    }


    /**
     * A gomba rajzolását végzi az adott grafikus kontextuson a vezérlő adatok alapján.
     *
     * @param g          A rajzoláshoz szükséges Graphics objektum.
     * @param controller A játék vezérlő objektuma, amely tartalmazza a játékosok és gombák adatait.
     */
    public void draw(Graphics g, Controller controller) {
        Graphics2D g2 = (Graphics2D) g;
        Point center = drawingPanel.getGTecton(m.getPosition()).getCenter();
        FungusPlayer fPlayer = null;
        for (int i = 0; i < controller.getFungusPlayers().size(); i++) {
            for (int j = 0; j < controller.getFungusPlayers().get(i).getMushrooms().size(); j++) {
                if (controller.getFungusPlayers().get(i).getMushrooms().get(j).getMushroom() == m) {
                    fPlayer = controller.getFungusPlayers().get(i);
                    break;
                }
            }
        }

        if (fPlayer == null) {
            return;
        }

        Color playerColor = null;
        for (Entry<Player, Color> entry : drawingPanel.getGPanel().players.entrySet()) {
            if (entry.getKey() == fPlayer) {
                playerColor = entry.getValue();
                break;
            }
        }

        if (playerColor == null) {
            return;
        }


        String pathName = null;
        if (m.getState() == MushroomState.UNEVOLVED) {
            switch (playerColor.getRGB()) {
                case 0xFFFF0000: // Color.RED
                    pathName = "mush1.png";
                    break;

                case 0xFF00FF00: // Color.GREEN
                    pathName = "mush3.png";
                    break;

                case 0xFF0000FF: // Color.BLUE
                    pathName = "mush2.png";
                    break;

                case 0xFFFFFF00: // Color.YELLOW
                    pathName = "mush4.png";
                    break;
            }
        } else {
            switch (playerColor.getRGB()) {
                case 0xFFFF0000: // Color.RED
                    pathName = "mush1old.png";
                    break;

                case 0xFF00FF00: // Color.GREEN
                    pathName = "mush3old.png";
                    break;

                case 0xFF0000FF: // Color.BLUE
                    pathName = "mush2old.png";
                    break;

                case 0xFFFFFF00: // Color.YELLOW
                    pathName = "mush4old.png";
                    break;
            }
        }


        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(pathName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        g2.drawImage(img, center.x - 28, center.y - 28, 23, 23, null);
    }

}
