package view;

import controller.Controller;
import java.awt.CardLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;


/**
 * A MainWindow osztály a játék főablakáért felelős, amely örökli a JFrame funkcióit.
 * Ez az osztály kezeli a különböző képernyőpanelek megjelenítését a játék során.
 * A különböző panelek: Kezdőképernyő, névbeviteli panel, játékpanel, győzelmi panel.
 */
public class MainWindow extends JFrame {
    Controller controller;
    private CardLayout cardLayout;
    private JPanel cardPanel;

    private StartScreenPanel startScreen;
    private NameEntryPanel nameEntry;
    private GamePanel gamePanel;
    private WinPanel winPanel;


    /**
     * A MainWindow konstruktor egy új JFrame ablakot hoz létre és inicializálja a játékhoz szükséges
     * komponenseket, valamint biztosítja a különböző képernyőpanelek kezelését.
     */
    public MainWindow() {
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        controller = new Controller();

        startScreen = new StartScreenPanel(this, controller);
        nameEntry = new NameEntryPanel(this, controller);
        gamePanel = new GamePanel(this, controller);
        winPanel = new WinPanel(this, controller);
        controller.setGPanel(gamePanel);

        cardPanel.add(startScreen, "start");
        cardPanel.add(nameEntry, "nameEntry");
        cardPanel.add(gamePanel, "game");
        cardPanel.add(winPanel, "winPanel");

        setTitle("Fungorium");
        this.setContentPane(cardPanel);
        this.setSize(800, 600);
        setLocationRelativeTo(null); // Képernyő közepére
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximalizálás
    }

    /**
     * Megjeleníti a kezdőképernyőt a főablakban.
     * A "start" azonosítójú panelt jeleníti meg a CardLayout kezelő segítségével.
     */
    public void showStartScreen() {
        cardLayout.show(cardPanel, "start");

    }

    /**
     * A showNameEntryPanel metódus a névbeviteli panel megjelenítéséért felelős.
     *
     * Ez a metódus először frissíti a névbeviteli mezőket a játékosok
     * aktuális számának megfelelően az updatePlayerFields metódus meghívásával,
     * majd megjeleníti a névbeviteli panelt a CardLayout segítségével.
     */
    public void showNameEntryPanel() {
        nameEntry.updatePlayerFields();
        cardLayout.show(cardPanel, "nameEntry");
    }

    /**
     * Megjeleníti a játékpanelt a főablakban, miközben inicializálja a játék
     * elindításához szükséges beállításokat és adatokat.
     */
    public void showGamePanel() {
        controller.loadTecton("tectons.txt");
        gamePanel.tectontTranslateTransform();
        controller.act();
        gamePanel.setPlayerColors();
        cardLayout.show(cardPanel, "game");
    }

    /**
     * Ez a metódus felelős a győzelmi panel megjelenítéséért.
     * A metódus frissíti a winPanel komponens tartalmát a megfelelő adatokkal,
     * majd a CardLayout segítségével a győzelmi panelt jeleníti meg a főablakban.
     */
    public void showWinPanel() {
        winPanel.refresh();
        cardLayout.show(cardPanel, "winPanel");
    }

}
