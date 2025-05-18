package view;

import controller.Controller;
import controller.FungusPlayer;
import controller.InsectPlayer;
import controller.Player;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import model.Insect;
import model.Mushroom;
import model.Tecton;

import static view.FieldCreator.*;


/**
 * A GamePanel osztály a játék fő paneljét reprezentálja, amely tartalmazza a
 * játékosok nevét, a körszámot, a DrawingPanel-t és a különböző gombokat.
 * Az osztály felelős a játék állapotának kezeléséért és a felhasználói
 * interakciók feldolgozásáért.
 */
public class GamePanel extends JPanel {
    private MainWindow parent;
    private DrawingPanel drawingPanel;
    private Controller controller;
    private GameState state;

    JLabel currentPlayer;
    JLabel player;
    JLabel roundNumber;
    JLabel round;

    private JButton moveButton;
    private JButton cutButton;
    private JButton infoButton;
    private JButton branchButton;
    private JButton growButton;
    private JButton shootButton;
    private JButton eatButton;
    private JButton closeButton;

    Map<Player, Color> players;
    Random rand;


    /**
     * Konstruktor, amely létrehoz egy új GamePanel objektumot. A panel különféle interfész
     * elemeket jelenít meg, ideértve a játék állapotát, a jelenlegi játékos adatait és az
     * akciógombokat. A GamePanel a főablak (MainWindow) részeként működik, és Controller-t
     * használ a játék funkcióinak kezelésére.
     *
     * @param parent     A szülő MainWindow objektum, amelyhez ez a panel tartozik.
     * @param controller A Controller objektum, amelyet a játék interaktív vezérléséhez
     *                   használnak az események kezelésére és a játékmenet logikájának kezelésére.
     */
    public GamePanel(MainWindow parent, Controller controller) {
        this.parent = parent;
        this.controller = controller;
        state = GameState.PUTFIRSTMUSHROOM;
        players = new HashMap<>();
        rand = new Random();

        setLayout(new BorderLayout());

        // játékos, körszám
        int labelWidth = getWidth() / 5;

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.BLACK);
        add(topPanel, BorderLayout.NORTH);

        // játékos
        JPanel playerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        playerPanel.setBackground(Color.BLACK);
        topPanel.add(playerPanel, BorderLayout.WEST);

        currentPlayer = createLabel("Current Player:");
        currentPlayer.setPreferredSize(new Dimension(labelWidth, 60));
        playerPanel.add(currentPlayer);

        player = createLabel("");
        player.setPreferredSize(new Dimension(labelWidth, 60));
        playerPanel.add(player);

        // kör
        JPanel roundPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        roundPanel.setBackground(Color.BLACK);
        topPanel.add(roundPanel, BorderLayout.EAST);

        roundNumber = createLabel("Round:");
        roundNumber.setPreferredSize(new Dimension(labelWidth, 60));
        roundPanel.add(roundNumber);

        round = createLabel("");
        round.setPreferredSize(new Dimension(labelWidth, 60));
        roundPanel.add(round);

        //info
        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        infoPanel.setBackground(Color.BLACK);
        topPanel.add(infoPanel, BorderLayout.CENTER);

        ActionListener buttonListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleButtonClick(e);
            }
        };

        infoButton = createButton("Info");
        infoButton.setPreferredSize(new Dimension(labelWidth, 60));
        infoButton.addActionListener(buttonListener);
        infoPanel.add(infoButton);


        drawingPanel = new DrawingPanel(controller, this);
        drawingPanel.setBackground(Color.BLACK);
        add(drawingPanel, BorderLayout.CENTER);


        // akció gombok
        JPanel actionsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        actionsPanel.setBackground(Color.BLACK);
        add(actionsPanel, BorderLayout.SOUTH);

        moveButton = createButton("Move");
        moveButton.addActionListener(buttonListener);
        actionsPanel.add(moveButton);

        cutButton = createButton("Cut");
        cutButton.addActionListener(buttonListener);
        actionsPanel.add(cutButton);

        branchButton = createButton("Branch Thread");
        branchButton.addActionListener(buttonListener);
        actionsPanel.add(branchButton);

        growButton = createButton("Grow Mushroom");
        growButton.addActionListener(buttonListener);
        actionsPanel.add(growButton);

        shootButton = createButton("Shoot Spore");
        shootButton.addActionListener(buttonListener);
        actionsPanel.add(shootButton);

        eatButton = createButton("Eat Insect");
        eatButton.addActionListener(buttonListener);
        actionsPanel.add(eatButton);

        closeButton = createButton("Close Step");
        closeButton.addActionListener(buttonListener);
        actionsPanel.add(closeButton);
    }


    /**
     * Hibaüzenetet jelenít meg egy felugró ablakban.
     *
     * @param str A megjelenítendő hibaüzenet szövegét tartalmazó karakterlánc.
     */
    public void showError(String str) {
        JOptionPane.showMessageDialog(this, str, "Valami nem jó!", JOptionPane.ERROR_MESSAGE);
    }


    /**
     * Információs üzenetet jelenít meg egy felugró ablakban.
     *
     * @param str A megjelenítendő üzenet szövege.
     */
    public void showInformation(String str) {
        JOptionPane.showMessageDialog(this, str);
    }


    /**
     * Véletlenszerű számot generál.
     *
     * @return Egy véletlenszerű egész szám a [0, 1000) intervallumban.
     */
    public int randomize() {
        return rand.nextInt(1000);
    }


    /**
     * Visszaadja a megadott játékoshoz tartozó színt.
     *
     * @param p A játékos, akinek a színét szeretnénk lekérdezni.
     * @return A játékoshoz rendelt szín.
     */
    public Color returnColor(Player p) {
        return players.get(p);
    }


    /**
     * A játék befejezését végrehajtó metódus.
     * Meghívja a szülőkomponens (MainWindow) showWinPanel metódusát,
     * amely a végeredményt megjelenítő panelt mutatja meg a felhasználónak.
     */
    public void endGame() {
        parent.showWinPanel();
    }


    /**
     * Ellenőrzi, hogy egy adott tekton kettétörhető-e az aktuális feltételek alapján.
     * A metódus a tektonhoz tartozó rajzolt vonalak (oldalak) számát vizsgálja,
     * és ha az meghaladja a 3-at, akkor megtörhetőnek számít.
     *
     * @param t A vizsgált tekton objektum.
     * @return Igaz, ha a tekton oldalainak száma több, mint 3, egyébként hamis.
     */
    public boolean canBreakTecton(Tecton t) {
        if (drawingPanel.getGTecton(t).getLineCount() > 3) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * Frissíti a felső panel megjelenítését a jelenlegi játékos nevével és az aktuális kör számával.
     * <p>
     * A metódus használatával a GUI friss információkat jelenít meg, amelyek tükrözik
     * a játék aktuális állapotát. A játékos nevét és a kör számát a Controller által rendelkezésre
     * bocsátott információk alapján állítja be.
     */
    public void refreshTopPanel() {
        player.setText(controller.getCurrentPlayerName());
        round.setText(Integer.toString(controller.getRound()));
        int labelWidth = getWidth() / 5;
        currentPlayer.setPreferredSize(new Dimension(labelWidth, 60));
        player.setPreferredSize(new Dimension(labelWidth, 60));
        roundNumber.setPreferredSize(new Dimension(labelWidth, 60));
        round.setPreferredSize(new Dimension(labelWidth, 60));
        infoButton.setPreferredSize(new Dimension(labelWidth, 60));
        revalidate();
    }


    /**
     * Meghívja a DrawingPanel osztály tectontTranslateTransform metódusát.
     * Ez a művelet a DrawingPanel osztályban hajt végre egy transzformációt,
     * amely a tektonok (Tecton és GTecton objektumok) koordinátáira vonatkozik.
     * A transzformáció során a méretek és az arányok aktualizálásra kerülnek
     * a megjelenítési környezet változásainak megfelelően.
     */
    public void tectontTranslateTransform() {
        drawingPanel.tectontTranslateTransform();
    }


    /**
     * Beállítja a játékpanel aktuális állapotát.
     *
     * @param state A játék új állapota, amelyet a GameState enum határoz meg.
     */
    public void setState(GameState state) {
        this.state = state;
    }


    /**
     * Visszaadja az aktuális játékállapotot (GameState).
     *
     * @return Az aktuális játékállapot, amely a GameState enum értékei közül valamelyik.
     */
    public GameState getState() {
        return state;
    }


    /**
     * Beállítja a játékosok színeit a játékban.
     * <p>
     * A metódus előre definiált színeket rendel az InsectPlayer és FungusPlayer típusú
     * játékosokhoz. A színek a játékosok típusai szerint kerülnek kiosztásra a
     * players attribútumon keresztül.
     */
    public void setPlayerColors() {
        Color[] colors = {Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW};

        List<InsectPlayer> iPlayers = controller.getInsectPlayers();
        List<FungusPlayer> fPlayers = controller.getFungusPlayers();

        int i = 0;
        for (InsectPlayer iPlayer : iPlayers) {
            players.put(iPlayer, colors[i++]);
        }

        i = 0;
        for (FungusPlayer fPlayer : fPlayers) {
            players.put(fPlayer, colors[i++]);
        }
    }


    /**
     * Új tekton hozzáadása a játékfelülethez.
     * A metódus egy grafikai reprezentációt hoz létre a tektonhoz a megadott pontok alapján, beállítja
     * annak típusát és színét, majd hozzáadja a játékfelülethez.
     *
     * @param points A tektonhoz tartozó pontok listája, ahol a páros indexek az x koordináták, a páratlanok pedig az y koordináták.
     * @param t      A hozzáadandó tekton objektum.
     * @param type   A tekton típusa, amely befolyásolja annak megjelenítését (például "MultiThreadTecton", "SingleThreadTecton").
     */
    public void addTecton(List<Integer> points, Tecton t, String type) {

        GTecton gtect = new GTecton();
        int lineCount = 0;
        for (int i = 0; i < points.size() - 1; i = i + 2) {
            int x = points.get(i);
            int y = points.get(i + 1);
            gtect.addPoint(x, y);
            lineCount++;
        }
        gtect.setLineCount(lineCount);
        gtect.setTecton(t);

        switch (type) {
            case ("MultiThreadTecton"):
                gtect.setColor(Color.LIGHT_GRAY);
                break;

            case ("SingleThreadTecton"):
                gtect.setColor(new Color(23, 45, 62));
                break;

            case ("AbsorbingTecton"):
                gtect.setColor(new Color(40, 20, 50));
                break;

            case ("KeepThreadTecton"):
                gtect.setColor(Color.DARK_GRAY);
                break;
        }

        gtect.setDrawingPanel(drawingPanel);

        drawingPanel.addTecton(t, gtect);
        drawingPanel.repaint();
    }


    /**
     * Egy meglévő tekton felosztása két új tektonra.
     * A metódus a megadott forrás tektonból két új tekton objektumot hoz létre,
     * és frissíti a grafikus felületet a változásnak megfelelően.
     *
     * @param source   A forrás tekton, amelyet szét kell osztani.
     * @param created1 Az első létrehozott új tekton.
     * @param created2 A második létrehozott új tekton.
     */
    public void breakTecton(Tecton source, Tecton created1, Tecton created2) {
        drawingPanel.breakTecton(source, created1, created2);
    }


    /**
     * Új gomba hozzáadása a játékfelülethez.
     * A metódus létrehoz egy új grafikai reprezentációt a gombához,
     * amelyet hozzáad a panelhez, majd újrarajzolja a felületet.
     *
     * @param m A hozzáadandó gomba objektum.
     */
    public void addMushroom(Mushroom m) {
        GMushroom gmush = new GMushroom();
        gmush.setMushroom(m);
        gmush.setDrawingPanel(drawingPanel);

        drawingPanel.addMushroom(m, gmush);
        drawingPanel.repaint();
    }


    /**
     * Eltávolítja a megadott gombát a DrawingPanel-ből.
     * A gomba eltávolítása után frissíti a panel megjelenítését.
     *
     * @param m Az eltávolítandó gomba objektum.
     */
    public void removeMushroom(Mushroom m) {
        drawingPanel.removeMushroom(m);
        drawingPanel.repaint();
    }


    /**
     * Új rovar hozzáadása a játékfelülethez.
     * A metódus egy új grafikai reprezentációt is létrehoz a rovarhoz, amelyet
     * hozzáad a panelhez, majd újrarajzolja a felületet.
     *
     * @param i A hozzáadandó rovar objektum.
     */
    public void addInsect(Insect i) {
        GInsect gins = new GInsect();
        gins.setInsect(i);
        gins.setDrawingPanel(drawingPanel);

        drawingPanel.addInsect(i, gins);
        drawingPanel.repaint();
    }


    /**
     * Eltávolítja a megadott rovart a DrawingPanel-ből.
     * A rovar eltávolítása után frissíti a panel megjelenítését.
     *
     * @param i Az eltávolítandó rovar objektum.
     */
    public void removeInsect(Insect i) {
        drawingPanel.removeInsect(i);
        drawingPanel.repaint();
    }


    /**
     * A GamePanel felületének kirajzolását végző metódus.
     * Frissíti a felső panelt és beállítja a gombok láthatóságát a játékállapot alapján,
     * majd meghívja az ősosztály paintComponent metódusát a további rajzolási műveletekhez.
     *
     * @param g A grafikus objektum, amely a rajzoláshoz szükséges.
     */
    protected void paintComponent(Graphics g) {
        refreshTopPanel();
        setVisibility();
        super.paintComponent(g);

    }


    /**
     * A játék kezelőfelület (GamePanel) gombjainak láthatóságát beállító metódus.
     * A láthatóságot az aktuális játékállapot (GameState) alapján konfigurálja.
     * Használatával az interfész dinamikusan alkalmazkodik az éppen aktuális
     * játékállapothoz, biztosítva a felhasználói interakció szabályszerűségét és folyamatosságát.
     */
    private void setVisibility() {

        if (state == GameState.TECTON_INFO) {

            if (controller.getFungalScores().containsKey(controller.getCurrentPlayerName())) {

                infoButton.setVisible(true);
                moveButton.setVisible(false);
                cutButton.setVisible(false);
                branchButton.setVisible(true);
                growButton.setVisible(true);
                shootButton.setVisible(true);
                eatButton.setVisible(true);
                closeButton.setVisible(true);

            } else {

                infoButton.setVisible(true);
                moveButton.setVisible(true);
                cutButton.setVisible(true);
                branchButton.setVisible(false);
                growButton.setVisible(false);
                shootButton.setVisible(false);
                eatButton.setVisible(false);
                closeButton.setVisible(true);

            }

        } else if (state == GameState.BRANCHTHREAD ||
                state == GameState.GROWMUSHROOM ||
                state == GameState.SELECTMUSHROOMFORSHOOT ||
                state == GameState.CUTTHREAD ||
                state == GameState.SHOOTSPORE ||
                state == GameState.EATINSECT ||
                state == GameState.WAITFUNGALCOMMAND) {

            infoButton.setVisible(true);
            closeButton.setVisible(true);
            moveButton.setVisible(false);
            cutButton.setVisible(false);
            branchButton.setVisible(true);
            growButton.setVisible(true);
            shootButton.setVisible(true);
            eatButton.setVisible(true);

        } else if (state == GameState.SELECTINSECTFORMOVE ||
                state == GameState.SELECTINSECTFORCUT ||
                state == GameState.WAITINSECTCOMMAND ||
                state == GameState.MOVEINSECT ||
                state == GameState.CUTTHREAD) {

            infoButton.setVisible(true);
            closeButton.setVisible(true);
            moveButton.setVisible(true);
            cutButton.setVisible(true);
            branchButton.setVisible(false);
            growButton.setVisible(false);
            shootButton.setVisible(false);
            eatButton.setVisible(false);

        } else {

            infoButton.setVisible(false);
            closeButton.setVisible(false);
            moveButton.setVisible(false);
            cutButton.setVisible(false);
            branchButton.setVisible(false);
            growButton.setVisible(false);
            shootButton.setVisible(false);
            eatButton.setVisible(false);

        }
    }


    /**
     * Feldolgozza a gombnyomás eseményét, és ennek megfelelően frissíti az állapotot,
     * valamint elvégzi a szükséges műveleteket, például a kijelölések törlését,
     * vagy a vezérlő adott metódusának meghívását.
     *
     * @param e Az esemény, amely tartalmazza a nyomott gombhoz tartozó információkat.
     */
    public void handleButtonClick(ActionEvent e) {
        if (e.getSource() == infoButton) {
            state = GameState.TECTON_INFO;
            drawingPanel.clearSelection();
        } else if (e.getSource() == closeButton) {
            controller.closestep();
            drawingPanel.clearSelection();
        } else if (e.getSource() == moveButton) {
            state = GameState.SELECTINSECTFORMOVE;
            drawingPanel.clearSelection();
        } else if (e.getSource() == cutButton) {
            state = GameState.SELECTINSECTFORCUT;
            drawingPanel.clearSelection();
        } else if (e.getSource() == branchButton) {
            state = GameState.BRANCHTHREAD;
            drawingPanel.clearSelection();
        } else if (e.getSource() == growButton) {
            state = GameState.GROWMUSHROOM;
            drawingPanel.clearSelection();
        } else if (e.getSource() == shootButton) {
            state = GameState.SELECTMUSHROOMFORSHOOT;
            drawingPanel.clearSelection();
        } else if (e.getSource() == eatButton) {
            state = GameState.EATINSECT;
            drawingPanel.clearSelection();
        }

        repaint();
    }

}
