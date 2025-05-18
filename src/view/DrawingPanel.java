package view;

import controller.Controller;
import controller.FungusPlayer;
import controller.InsectPlayer;

import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;

import model.FungalThread;
import model.Insect;
import model.Mushroom;
import model.Spore;
import model.Tecton;


/**
 * A DrawingPanel osztály a játék grafikus felhasználói felületének megjelenítésére szolgál.
 * Ez az osztály kezeli a Tecton, Mushroom és Insect objektumok vizuális reprezentációját,
 * valamint az egérkattintási események kezelését.
 */
public class DrawingPanel extends JPanel {
    private HashMap<Tecton, GTecton> tectCombo;
    private HashMap<Mushroom, GMushroom> mushCombo;
    private HashMap<Insect, GInsect> insCombo;
    private Controller controller;
    private GamePanel gPanel;

    private GTecton lastClicked;
    private GTecton selectedSource;
    private GTecton targetSource;

    private double currentWidth;
    private double currentHeight;

    private double xTranslate;
    private double yTranslate;
    private boolean firstpaint;


    /**
     * A DrawingPanel osztály konstruktora. Feladata az osztály attribútumainak inicializálása,
     * az egérkattintási események figyelése, és az alapértelmezett szélesség és magasság beállítása.
     *
     * @param controller a logikát vezérlő Controller objektum
     * @param gPanel     a GamePanel objektum, amely a rajzelemeket és interakciókat kezeli a panelen belül
     */
    public DrawingPanel(Controller controller, GamePanel gPanel) {
        tectCombo = new HashMap<>();
        mushCombo = new HashMap<>();
        insCombo = new HashMap<>();
        this.controller = controller;
        this.gPanel = gPanel;
        currentWidth = 1920;
        currentHeight = 1080;

        selectedSource = null;
        targetSource = null;

        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                handleClickEvent(e);
            }
        });
    }


    /**
     * Ez a metódus felelős a DrawingPanel méreteihez viszonyított
     * transzformáció végrehajtásáért a Tecton és GTecton objektumok koordinátáin.
     * A transzformáció során az aktuális szélesség és magasság arányában skálázza
     * az egyes objektumok pontjait.
     */
    public void tectontTranslateTransform() {
        double width = getWidth();
        double height = getHeight();
        xTranslate = width / currentWidth;
        yTranslate = height / currentHeight;
        for (Map.Entry<Tecton, GTecton> entry : tectCombo.entrySet()) {
            GTecton val = entry.getValue();

            for (int i = 0; i < val.npoints; i++) {
                val.xpoints[i] = (int) (val.xpoints[i] * xTranslate);
                val.ypoints[i] = (int) (val.ypoints[i] * yTranslate);

            }
        }
    }


    /**
     * Visszaadja a Tecton és GTecton objektumok közötti megfeleltetéseket.
     *
     * @return HashMap amely tartalmazza a Tecton típusú kulcsokat és
     * a hozzájuk tartozó GTecton típusú értékeket.
     */
    public HashMap<Tecton, GTecton> getTectCombo() {
        return tectCombo;
    }


    /**
     * Visszaadja a DrawingPanel osztályhoz tartozó GamePanel objektumot,
     * amely felelős a játékbeli interakciók megvalósításáért.
     *
     * @return a GamePanel típusú objektum, amely a DrawingPanelhez tartozik
     */
    public GamePanel getGPanel() {
        return gPanel;
    }


    /**
     * Ez a metódus hozzáad egy Tecton objektumot és a hozzárendelt GTecton elemet
     * a tectCombo attribútumhoz.
     *
     * @param t     a Tecton objektum, amelyet hozzá szeretnénk adni
     * @param gtect a GTecton objektum, amely a megadott Tecton objektumhoz tartozik
     */
    public void addTecton(Tecton t, GTecton gtect) {
        tectCombo.put(t, gtect);
    }


    /**
     * Hozzáad egy Mushroom és GMushroom párost a tárolóhoz, amely a gombák
     * és azok vizuális reprezentációjának hozzárendelésére szolgál.
     *
     * @param m     a hozzáadni kívánt Mushroom objektum
     * @param gmush a Mushroom objektumhoz tartozó GMushroom objektum
     */
    public void addMushroom(Mushroom m, GMushroom gmush) {
        mushCombo.put(m, gmush);
    }


    /**
     * Egy Insect és GInsect pár hozzáadása az insCombo attribútumhoz.
     *
     * @param i    az Insect típusú objektum, amelyet hozzáadunk
     * @param gins a gins a hozzátartozó GInsect típusú objektum
     */
    public void addInsect(Insect i, GInsect gins) {
        insCombo.put(i, gins);
    }


    /**
     * Eltávolít egy adott gombát a panelen tárolt gombák közül.
     *
     * @param m az eltávolítandó Mushroom típusú objektum
     */
    public void removeMushroom(Mushroom m) {
        mushCombo.remove(m);
    }


    /**
     * Eltávolít egy rovart az aktuális insCombo listából.
     *
     * @param i az Insect objektum, amelyet el akarunk távolítani
     */
    public void removeInsect(Insect i) {
        insCombo.remove(i);
    }


    /**
     * Visszaadja a megadott Tecton objektumhoz tartozó GTecton példányt.
     *
     * @param t a lekérdezendő Tecton objektum
     * @return a Tecton objektumhoz tartozó GTecton példány, vagy null, ha nem található ilyen
     */
    public GTecton getGTecton(Tecton t) {
        return tectCombo.get(t);
    }


    /**
     * Visszaadja az adott Mushroom példányhoz tartozó GMushroom objektumot.
     *
     * @param m A Mushroom objektum, amelyhez a GMushroom példányt keressük.
     * @return A megadott Mushroom objektumhoz tartozó GMushroom példány.
     */
    public GMushroom getGMushroom(Mushroom m) {
        return mushCombo.get(m);
    }


    /**
     * Visszaadja a megadott rovarhoz tartozó GInsect példányt.
     *
     * @param i A rovar objektum, amelyhez tartozó GInsect-et vissza kell adni.
     * @return A megadott rovarhoz tartozó GInsect példány, vagy null, ha nincs ilyen kapcsolat.
     */
    public GInsect getGInsect(Insect i) {
        return insCombo.get(i);
    }


    /**
     * Ezt a metódust egy kezdő pontnak egy másik ponthoz viszonyított eltolására használjuk.
     * Az eltolás egy 15 egységnyi távolságot jelent a megadott irányban,
     * amely az első és a második pont közötti vektor alapján számítódik.
     *
     * @param from az eltolás kiindulópontja
     * @param to   a célpont, amely meghatározza az eltolás irányát
     * @return egy új Point objektum, amely az eltolás eredményeként létrejövő koordinátákat tartalmazza
     */
    public Point shiftPoint(Point from, Point to) {
        double dx = to.x - from.x;
        double dy = to.y - from.y;
        double length = Math.sqrt(dx * dx + dy * dy);
        dx = dx / length;
        dy = dy / length;
        int newX = (int) (from.x + dx * 15);
        int newY = (int) (from.y + dy * 15);
        return new Point(newX, newY);
    }


    /**
     * Ez a metódus egy meglévő Tecton objektumot két új Tecton objektumra bont.
     *
     * @param source   Az eredeti Tecton objektum, amelyet fel kell bontani.
     * @param created1 Az első létrehozott Tecton objektum a bontás után.
     * @param created2 A második létrehozott Tecton objektum a bontás után.
     */
    public void breakTecton(Tecton source, Tecton created1, Tecton created2) {
        GTecton g1 = new GTecton();
        GTecton g2 = new GTecton();

        GTecton src = tectCombo.get(source);

        int centre = src.npoints / 2;
        int lineCount = 0;
        Point p1 = new Point(src.xpoints[0], src.ypoints[0]);
        Point p2 = new Point(src.xpoints[1], src.ypoints[1]);
        g1.addPoint(shiftPoint(p1, p2).x, shiftPoint(p1, p2).y);
        lineCount++;

        for (int i = 1; i < centre; i++) {
            int x = src.xpoints[i];
            int y = src.ypoints[i];
            g1.addPoint(x, y);
            lineCount++;
        }

        p1 = new Point(src.xpoints[centre], src.ypoints[centre]);
        p2 = new Point(src.xpoints[centre - 1], src.ypoints[centre - 1]);
        g1.addPoint(shiftPoint(p1, p2).x, shiftPoint(p1, p2).y);
        lineCount++;
        g1.setLineCount(lineCount);
        g1.setTecton(created1);
        g1.setColor(src.getColor());
        g1.setDrawingPanel(this);

        lineCount = 0;
        p1 = new Point(src.xpoints[0], src.ypoints[0]);
        p2 = new Point(src.xpoints[src.npoints - 1], src.ypoints[src.npoints - 1]);

        g2.addPoint(shiftPoint(p1, p2).x, shiftPoint(p1, p2).y);
        lineCount++;

        p1 = new Point(src.xpoints[centre], src.ypoints[centre]);
        p2 = new Point(src.xpoints[centre + 1], src.ypoints[centre + 1]);
        g2.addPoint(shiftPoint(p1, p2).x, shiftPoint(p1, p2).y);
        lineCount++;

        for (int i = centre + 1; i < src.npoints; i++) {
            int x = src.xpoints[i];
            int y = src.ypoints[i];
            g2.addPoint(x, y);
            lineCount++;
        }

        g2.setLineCount(lineCount);
        g2.setTecton(created2);
        g2.setColor(src.getColor());
        g2.setDrawingPanel(this);

        tectCombo.put(created1, g1);
        tectCombo.put(created2, g2);
        tectCombo.remove(source);

    }


    /**
     * Feladata a DrawingPanel eleminek megrajzolása, beleértve a Tecton-ok, Mushroom-ok és
     * Insect-ek vizuális reprezentációit. A metódus külön-külön meghívja a draw és
     * drawThreads metódusokat, valamint alaphelyzetbe állít bizonyos állapotokat.
     *
     * @param g A Graphics objektum, amely a komponensek megrajzolásához használt eszközöket biztosítja
     */
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (Map.Entry<Tecton, GTecton> entry : tectCombo.entrySet()) {
            GTecton val = entry.getValue();
            val.draw(g, controller);
        }

        for (Map.Entry<Tecton, GTecton> entry : tectCombo.entrySet()) {
            GTecton val = entry.getValue();
            val.drawThreads(g, controller);
        }

        for (Map.Entry<Tecton, GTecton> entry : tectCombo.entrySet()) {
            GTecton val = entry.getValue();
            val.setDone(false);
        }

        for (Map.Entry<Mushroom, GMushroom> entry : mushCombo.entrySet()) {
            GMushroom val = entry.getValue();
            val.draw(g, controller);
        }

        for (Map.Entry<Insect, GInsect> entry : insCombo.entrySet()) {
            GInsect val = entry.getValue();
            val.draw(g, controller);
        }

    }


    /**
     * Kezeli az egérkattintás eseményeket és a játékmenet állapotának megfelelő műveletet hajt végre.
     *
     * @param e Az egérkattintás eseményt reprezentáló MouseEvent objektum, amely
     *          tartalmazza a kattintás helyét.
     */
    private void handleClickEvent(MouseEvent e) {
        Point click = e.getPoint();
        GTecton selected = null;
        for (GTecton gt : tectCombo.values()) {
            if (gt.contains(click)) {
                selected = gt;
                break;
            }
        }

        if (selected != null) {
            GameState state = gPanel.getState();

            switch (state) {
                case SELECTINSECTFORMOVE:
                    selectedSource = selected;
                    selected.setSelected(true);
                    gPanel.setState(GameState.MOVEINSECT);
                    break;

                case MOVEINSECT:
                    clearSelection();
                    targetSource = selected;
                    controller.move(selectedSource.getTecton().getInsect(), (Tecton) targetSource.getTecton());
                    gPanel.setState(GameState.WAITINSECTCOMMAND);
                    break;

                case SELECTINSECTFORCUT:
                    selectedSource = selected;
                    selected.setSelected(true);
                    gPanel.setState(GameState.CUTTHREAD);
                    break;

                case CUTTHREAD:
                    clearSelection();
                    targetSource = selected;
                    controller.cut(selectedSource.getTecton().getInsect(), (Tecton) targetSource.getTecton());
                    gPanel.setState(GameState.WAITINSECTCOMMAND);
                    break;

                case BRANCHTHREAD:
                    controller.branchThread((Tecton) selected.getTecton());
                    gPanel.setState(GameState.WAITFUNGALCOMMAND);
                    break;

                case EATINSECT:
                    controller.eatInsect(selected.getTecton().getInsect());
                    gPanel.setState(GameState.WAITFUNGALCOMMAND);
                    break;

                case SELECTMUSHROOMFORSHOOT:
                    selectedSource = selected;
                    selected.setSelected(true);
                    gPanel.setState(GameState.SHOOTSPORE);
                    break;

                case SHOOTSPORE:
                    clearSelection();
                    targetSource = selected;
                    controller.shootSpore(selectedSource.getTecton().getMushroom(), (Tecton) targetSource.getTecton());
                    gPanel.setState(GameState.WAITFUNGALCOMMAND);
                    break;

                case GROWMUSHROOM:
                    controller.growMushroom((Tecton) selected.getTecton());
                    gPanel.setState(GameState.WAITFUNGALCOMMAND);
                    break;

                case PUTFIRSTINSECT:
                    controller.putFirstInsect((Tecton) selected.getTecton());
                    break;

                case PUTFIRSTMUSHROOM:
                    int rand = gPanel.randomize() % 2;
                    if (rand == 0) {
                        if (controller.putFirstMushroom("ShortLifeThread", (Tecton) selected.getTecton())) {
                            gPanel.showInformation("ShortLifeThread típusú gombafonál jött létre");
                        }
                    } else {
                        if (controller.putFirstMushroom("LongLifeThread", (Tecton) selected.getTecton())) {
                            gPanel.showInformation("LongLifeThread típusú gombafonál jött létre");
                        }
                    }
                    break;

                case TECTON_INFO:
                    Tecton tecton = (Tecton) selected.getTecton();
                    String tectonType = tecton.getClass().getSimpleName();

                    Mushroom mushroom = tecton.getMushroom();
                    String mushroomInfo = "-";
                    if (mushroom != null) {
                        FungusPlayer mushroomPlayer = controller.getMushroomPlayer(mushroom);
                        if (mushroomPlayer != null) {
                            Color playerColor = gPanel.returnColor(mushroomPlayer);
                            mushroomInfo = getColorName(playerColor);
                        }
                    }

                    Insect insect = tecton.getInsect();
                    String insectInfo = "-";
                    if (insect != null) {
                        InsectPlayer insectPlayer = controller.getInsectPlayer(insect);
                        if (insectPlayer != null) {
                            Color playerColor = gPanel.returnColor(insectPlayer);
                            insectInfo = getColorName(playerColor);
                        }
                    }

                    List<Spore> spores = tecton.getSpores();
                    StringBuilder sporesInfo = new StringBuilder();
                    if (spores != null && !spores.isEmpty()) {
                        for (Spore spore : spores) {
                            FungalThread thread = spore.getThread();
                            if (thread != null) {
                                FungusPlayer sporePlayer = controller.getThreadPlayer(thread);
                                if (sporePlayer != null) {
                                    Color playerColor = gPanel.returnColor(sporePlayer);
                                    String colorName = getColorName(playerColor);
                                    if (sporesInfo.length() > 0) {
                                        sporesInfo.append(", ");
                                        sporesInfo.append(colorName);
                                    } else if (sporesInfo.length() == 0) {
                                        sporesInfo.append(colorName);
                                    }
                                }
                            }
                        }
                    }

                    String sporesInfoStr = sporesInfo.length() > 0 ? sporesInfo.toString() : "-";

                    List<FungalThread> threads = tecton.getThreads();
                    StringBuilder threadsInfo = new StringBuilder();
                    if (threads != null && !threads.isEmpty()) {
                        for (FungalThread thread : threads) {
                            FungusPlayer threadPlayer = controller.getThreadPlayer(thread);
                            if (threadPlayer != null) {
                                Color playerColor = gPanel.returnColor(threadPlayer);
                                String colorName = getColorName(playerColor);
                                if (threadsInfo.length() > 0) {
                                    threadsInfo.append(", ");
                                    threadsInfo.append(colorName);
                                } else if (threadsInfo.length() == 0) {
                                    threadsInfo.append(colorName);
                                }
                            }
                        }
                    }

                    String threadsInfoStr = threadsInfo.length() > 0 ? threadsInfo.toString() : "-";

                    String info = "Tecton type: " + tectonType + "\n\n" +
                            "Mushroom: " + mushroomInfo + "\n" +
                            "Insect: " + insectInfo + "\n" +
                            "Spores: " + sporesInfoStr + "\n" +
                            "Fungal Threads: " + threadsInfoStr;

                    gPanel.showInformation(info);

                    if (controller.getFungalScores().containsKey(controller.getCurrentPlayerName()))
                        gPanel.setState(GameState.WAITFUNGALCOMMAND);
                    else
                        gPanel.setState(GameState.WAITINSECTCOMMAND);
                    break;

                default:
                    break;
            }

            repaint();
        }
    }


    /**
     * Törli az összes GTecton objektum kijelölését a tectCombo attribútumban.
     */
    public void clearSelection() {
        for (GTecton gt : tectCombo.values()) {
            gt.setSelected(false);
        }
    }


    /**
     * Visszaadja a szín nevét az adott Color objektum alapján.
     * Amennyiben a szín nem ismert, vagy a bemeneti paraméter null,
     * a visszatérési érték "unknown".
     *
     * @param color Az a Color objektum, amelynek nevét le szeretnénk kérdezni.
     * @return A szín neve szövegként.
     */
    private String getColorName(Color color) {
        if (color == null) return "unknown";

        if (color.equals(Color.RED)) {
            return "red";
        } else if (color.equals(Color.GREEN)) {
            return "green";
        } else if (color.equals(Color.BLUE)) {
            return "blue";
        } else if (color.equals(Color.YELLOW)) {
            return "yellow";
        } else {
            return "unknown";
        }
    }

}
