package view;

import controller.Controller;
import controller.FungusPlayer;
import controller.Player;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Stroke;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import javax.imageio.ImageIO;

import model.FungalThread;
import model.ITectonView;
import model.Spore;
import model.Tecton;


/**
 * A GTecton osztály a tektonokat reprezentálja a játékban.
 * Ez az osztály öröklődik a Polygon osztályból, és tartalmazza a tektonokkal kapcsolatos
 * információkat és műveleteket.
 */
public class GTecton extends Polygon {
    private boolean selected = false;
    private ITectonView tecton;
    private int lineCount;
    private DrawingPanel drawingPanel;
    private Color color;
    private boolean done = false;


    /**
     * A GTecton konstruktora.
     */
    public GTecton() {
        super();
    }


    /**
     * Visszaadja, hogy a "done" állapot igaz vagy hamis értéket tartalmaz,
     * amely azt jelzi, hogy a tektonon kirajzolásra került-e gombafonal.
     *
     * @return true, ha rajzoltunk gombafonalat, ellenkező esetben false
     */
    public boolean getDone() {
        return done;
    }


    /**
     * Beállítja az objektum "done" állapotát.
     *
     * @param done Egy logikai érték, ami jelzi, hogy rajzoltunk már gombafonalat.
     */
    public void setDone(boolean done) {
        this.done = done;
    }


    /**
     * Beállítja a tekton színét.
     *
     * @param color A kívánt szín, amelyet a tektonhoz kell rendelni.
     */
    public void setColor(Color color) {
        this.color = color;
    }


    /**
     * Visszaadja a GTecton objektumhoz társított színt.
     *
     * @return A GTecton színeként definiált Color objektum.
     */
    public Color getColor() {
        return color;
    }


    /**
     * Beállítja a megadott DrawingPanel objektumot a példányhoz.
     *
     * @param drawingPanel A DrawingPanel típusú objektum, amelyet be kell állítani.
     */
    public void setDrawingPanel(DrawingPanel drawingPanel) {
        this.drawingPanel = drawingPanel;
    }


    /**
     * Beállítja a tekton objektumot az aktuális példányhoz.
     *
     * @param t A beállítandó Tecton objektum.
     */
    public void setTecton(Tecton t) {
        tecton = t;
    }


    /**
     * Visszaadja a tektonhoz tartozó nézetet reprezentáló objektumot.
     *
     * @return Az ITectonView interfész implementációját adja vissza, amely a tekton nézetével kapcsolatos
     * információkat és elemeket biztosít.
     */
    public ITectonView getTecton() {
        return tecton;
    }


    /**
     * Beállítja az osztályhoz tartozó sorok számát.
     *
     * @param lineCount Az új sorok száma, amelyet be kell állítani.
     */
    public void setLineCount(int lineCount) {
        this.lineCount = lineCount;
    }


    /**
     * Visszaadja az oldalak számát, amelyeket a "GTecton" objektum tartalmaz.
     *
     * @return Az oldalak száma, mint egész szám.
     */
    public int getLineCount() {
        return lineCount;
    }


    /**
     * Létrehoz egy új GTecton objektumot a megadott pontok alapján.
     *
     * @param xpoints Az x koordináták tömbje, amelyek meghatározzák a sokszög csúcsait.
     * @param ypoints Az y koordináták tömbje, amelyek meghatározzák a sokszög csúcsait.
     * @param npoints A csúcspontok száma, amelyek a sokszöget alkotják.
     */
    public GTecton(int[] xpoints, int[] ypoints, int npoints) {
        super(xpoints, ypoints, npoints);
    }


    /**
     * Ellenőrzi, hogy az aktuális objektum ki van-e választva.
     *
     * @return true, ha az objektum ki van választva, különben false
     */
    public boolean isSelected() {
        return selected;
    }


    /**
     * Beállítja az objektum kijelölt állapotát.
     *
     * @param b Igaz, ha az objektum kijelölése aktív legyen, hamis, ha nem.
     */
    public void setSelected(boolean b) {
        selected = b;
    }


    /**
     * Ez a metódus megváltoztatja a selected attribútum értékét.
     * Ha a selected jelenleg igaz, akkor hamisra állítja, és fordítva.
     */
    public void switchSelected() {
        selected = !selected;
    }


    /**
     * Ez a metódus megváltoztatja a selected attribútum értékét.
     * Ha a selected jelenleg igaz, akkor hamisra állítja, és fordítva.
     */
    public void toggleSelected() {
        selected = !selected;
    }


    /**
     * Kiszámítja és visszaadja a sokszög középpontját.
     * A középpontot az x és y koordináták átlagának meghatározásával számolja ki.
     *
     * @return A számított középpontot reprezentáló Point objektum.
     */
    public Point getCenter() {
        int sumX = 0;
        int sumY = 0;

        for (int i = 0; i < npoints; i++) {
            sumX += xpoints[i];
            sumY += ypoints[i];
        }

        int centerX = sumX / npoints;
        int centerY = sumY / npoints;

        return new Point(centerX, centerY);
    }


    /**
     * Megrajzolja a fonalakat a megfelelő tektonokra a megadott vezérlő alapján.
     *
     * @param g          A rajzoláshoz használt Graphics objektum.
     * @param controller A megjelenítéshez szükséges vezérlő, amely tartalmazza a játékosok és szálak állapotát.
     */
    public void drawThreads(Graphics g, Controller controller) {

        Graphics2D g2 = (Graphics2D) g;

        for (int i = 0; i < tecton.getNeighbors().size(); i++) {
            List<FungalThread> fList1 = tecton.getThreads();
            List<FungalThread> fList2 = new ArrayList<>();
            if (tecton.getNeighbors().get(i) != null) {
                if (drawingPanel.getGTecton(tecton.getNeighbors().get(i)).getDone() == false) {
                    fList2 = tecton.getNeighbors().get(i).getThreads();

                    List<FungalThread> f1 = new ArrayList<>(fList1);
                    f1.retainAll(fList2);
                    if (!f1.isEmpty()) {
                        for (FungusPlayer fPlayer : controller.getFungusPlayers()) { // játékosokon végig
                            for (int k = 0; k < f1.size(); k++) {
                                if (f1.get(k) == fPlayer.getThread()) {
                                    Color c = drawingPanel.getGPanel().returnColor(fPlayer);

                                    Point p1 = new Point(xpoints[i], ypoints[i]);
                                    Point p2;
                                    if (i + 1 == lineCount) {
                                        p2 = new Point(xpoints[0], ypoints[0]);
                                    } else {
                                        p2 = new Point(xpoints[i + 1], ypoints[i + 1]);
                                    }

                                    Point vec = new Point(p2.x - p1.x, p2.y - p1.y);

                                    double dx = (double) vec.y;
                                    double dy = (double) -vec.x;
                                    double length = Math.sqrt(dx * dx + dy * dy);
                                    dx = dx / length;
                                    dy = dy / length;

                                    if (c.equals(Color.RED)) {
                                        Color originalColor = g2.getColor();
                                        Stroke originalStroke = g2.getStroke();

                                        // Új beállítások
                                        g2.setColor(Color.RED);
                                        g2.setStroke(new BasicStroke(5f));

                                        int startx = (int) (p1.x + vec.x * 0.2 - dx * 15);
                                        int starty = (int) (p1.y + vec.y * 0.2 - dy * 15);
                                        int endx = (int) (startx + dx * 60);
                                        int endy = (int) (starty + dy * 60);
                                        g2.drawLine(startx, starty, endx, endy);
                                        // Eredeti beállítások visszaállítása
                                        g2.setColor(originalColor);
                                        g2.setStroke(originalStroke);

                                    } else if (c.equals(Color.GREEN)) {
                                        Color originalColor = g2.getColor();
                                        Stroke originalStroke = g2.getStroke();

                                        // Új beállítások
                                        g2.setColor(Color.GREEN);
                                        g2.setStroke(new BasicStroke(5f));

                                        int startx = (int) (p1.x + vec.x * 0.4 - dx * 15);
                                        int starty = (int) (p1.y + vec.y * 0.4 - dy * 15);
                                        int endx = (int) (startx + dx * 60);
                                        int endy = (int) (starty + dy * 60);
                                        g2.drawLine(startx, starty, endx, endy);
                                        // Eredeti beállítások visszaállítása
                                        g2.setColor(originalColor);
                                        g2.setStroke(originalStroke);

                                    } else if (c.equals(Color.BLUE)) {
                                        Color originalColor = g2.getColor();
                                        Stroke originalStroke = g2.getStroke();

                                        // Új beállítások
                                        g2.setColor(Color.BLUE);
                                        g2.setStroke(new BasicStroke(5f));

                                        int startx = (int) (p1.x + vec.x * 0.6 - dx * 15);
                                        int starty = (int) (p1.y + vec.y * 0.6 - dy * 15);
                                        int endx = (int) (startx + dx * 60);
                                        int endy = (int) (starty + dy * 60);
                                        g2.drawLine(startx, starty, endx, endy);
                                        // Eredeti beállítások visszaállítása
                                        g2.setColor(originalColor);
                                        g2.setStroke(originalStroke);

                                    } else if (c.equals(color.YELLOW)) {
                                        Color originalColor = g2.getColor();
                                        Stroke originalStroke = g2.getStroke();

                                        // Új beállítások
                                        g2.setColor(Color.YELLOW);
                                        g2.setStroke(new BasicStroke(5f));

                                        int startx = (int) (p1.x + vec.x * 0.8 - dx * 15);
                                        int starty = (int) (p1.y + vec.y * 0.8 - dy * 15);
                                        int endx = (int) (startx + dx * 60);
                                        int endy = (int) (starty + dy * 60);
                                        g2.drawLine(startx, starty, endx, endy);
                                        // Eredeti beállítások visszaállítása
                                        g2.setColor(originalColor);
                                        g2.setStroke(originalStroke);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        done = true;
    }


    /**
     * A tekton kirajzolásáért felelős eljárás. Meghatározza a színezést,
     * a kirajzolást és a spórák hozzáadott képeit a játékosokhoz társított színek alapján.
     *
     * @param g          A grafikus objektum, amelyre a rajzolási műveletek végrehajtásra kerülnek.
     * @param controller A vezérlő, amely tartalmazza a játékosokat és azokkal kapcsolatos információkat,
     *                   amelyeket a rajzolási folyamat során használunk.
     */
    public void draw(Graphics g, Controller controller) {

        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(selected ? new Color(173, 216, 230) : color);
        g2.fillPolygon(this);
        g2.setColor(Color.BLACK);
        g2.drawPolygon(this);

        List<Spore> spores = tecton.getSpores(); // ezek a spórák vannak rajta
        if (spores.isEmpty()) {
            return;
        }

        for (FungusPlayer fPlayer : controller.getFungusPlayers()) { // játékosokon végig
            for (Spore s : tecton.getSpores()) { // spórákon végig
                if (s.getThread() == fPlayer.getThread()) { // ha ez a spóra rajta van a tektonon
                    Color playerColor = null;
                    for (Entry<Player, Color> entry : drawingPanel.getGPanel().players.entrySet()) { // kiszedjük a
                        // színét
                        if (entry.getKey() == fPlayer) {
                            Point center = getCenter();
                            playerColor = entry.getValue();
                            BufferedImage img = null;

                            switch (playerColor.getRGB()) {
                                case 0xFFFF0000: // Color.RED
                                    try {
                                        img = ImageIO.read(new File("spore1.png"));
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    g2.drawImage(img, center.x - 23, center.y + 3, 10, 10, null);
                                    break;

                                case 0xFF00FF00: // Color.GREEN
                                    try {
                                        img = ImageIO.read(new File("spore3.png"));
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    g2.drawImage(img, center.x - 11, center.y + 3, 10, 10, null);
                                    break;

                                case 0xFF0000FF: // Color.BLUE
                                    try {
                                        img = ImageIO.read(new File("spore2.png"));
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    g2.drawImage(img, center.x + 1, center.y + 3, 10, 10, null);
                                    break;

                                case 0xFFFFFF00: // Color.YELLOW
                                    try {
                                        img = ImageIO.read(new File("spore4.png"));
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    g2.drawImage(img, center.x + 13, center.y + 3, 10, 10, null);
                                    break;
                            }
                        }
                    }
                }
            }
        }
    }

}