package view;
import controller.Controller;
import java.awt.CardLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainWindow extends JFrame{
    
    
    Controller controller;

    private CardLayout cardLayout;
    private JPanel cardPanel;

    private StartScreenPanel startScreen;
    private NameEntryPanel nameEntry;
    private GamePanel gamePanel;
    private WinPanel winPanel;

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

        setTitle("Gombász vs Rovarász");
        this.setContentPane(cardPanel);
        this.setSize(800, 600);
        setLocationRelativeTo(null); // Képernyő közepére
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximalizálás
    }

    public void showStartScreen() {
        cardLayout.show(cardPanel, "start");

    }

    public void showNameEntryPanel() {
        nameEntry.updatePlayerFields();
        cardLayout.show(cardPanel, "nameEntry");
    }

    public void showGamePanel() {
        controller.loadTecton("ok.txt");
        gamePanel.tectontTranslateTransform();
        gamePanel.refreshTopPanel();
        controller.act();
        cardLayout.show(cardPanel, "game");
    }

    public void showWinPanel() {
        cardLayout.show(winPanel, "winPanel");
    }
}
