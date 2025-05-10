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

public class GamePanel extends JPanel {
    private MainWindow parent;
    private DrawingPanel drawingPanel;
    private Controller controller;
    private GameState state;

    JLabel player;
    JLabel round;

    private JButton moveButton;
    private JButton cutButton;
    private JButton branchButton;
    private JButton growButton;
    private JButton shootButton;
    private JButton eatButton;
    private JButton closeButton;

    Map<Player, Color> players;

    Random rand;

    public GamePanel(MainWindow parent, Controller controller) {
        this.parent = parent;
        this.controller = controller;
        state = GameState.PUTFIRSTMUSHROOM;
        players = new HashMap<>();
        rand =new Random();

        setLayout(new BorderLayout());

        // játékos, körszám
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.CYAN);
        add(topPanel, BorderLayout.NORTH);

        // játékos
        JPanel playerPanel = new JPanel(new FlowLayout());
        playerPanel.setBackground(Color.CYAN);
        topPanel.add(playerPanel, BorderLayout.WEST);

        JLabel currentPlayer = new JLabel("Current Player:");
        currentPlayer.setForeground(Color.BLACK);
        playerPanel.add(currentPlayer);

        player = new JLabel();
        player.setForeground(Color.BLACK);
        playerPanel.add(player);

        // kör
        JPanel roundPanel = new JPanel(new FlowLayout());
        roundPanel.setBackground(Color.CYAN);
        topPanel.add(roundPanel, BorderLayout.EAST);

        JLabel roundNumber = new JLabel("Round:");
        roundNumber.setForeground(Color.BLACK);
        roundPanel.add(roundNumber);

        round = new JLabel();
        round.setForeground(Color.BLACK);
        roundPanel.add(round);

        drawingPanel = new DrawingPanel(controller, this);
        drawingPanel.setBackground(Color.WHITE); // vagy amit szeretnél
        add(drawingPanel, BorderLayout.CENTER);

        ActionListener buttonListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleButtonClick(e);
            }
        };

        // akció gombok
        JPanel actionsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        actionsPanel.setBackground(Color.BLACK);
        add(actionsPanel, BorderLayout.SOUTH);

        moveButton = new JButton("Move");
        moveButton.addActionListener(buttonListener);
        actionsPanel.add(moveButton);

        cutButton = new JButton("Cut");
        cutButton.addActionListener(buttonListener);
        actionsPanel.add(cutButton);

        branchButton = new JButton("BranchThread");
        branchButton.addActionListener(buttonListener);
        actionsPanel.add(branchButton);

        growButton = new JButton("GrowMushroom");
        growButton.addActionListener(buttonListener);
        actionsPanel.add(growButton);

        shootButton = new JButton("ShootSpore");
        shootButton.addActionListener(buttonListener);
        actionsPanel.add(shootButton);

        eatButton = new JButton("EatInsect");
        eatButton.addActionListener(buttonListener);
        actionsPanel.add(eatButton);

        closeButton = new JButton("Closestep");
        closeButton.addActionListener(buttonListener);
        actionsPanel.add(closeButton);

    }

    public void showError(String str) {
        JOptionPane.showMessageDialog(this, str, "Valami nem jó!", JOptionPane.ERROR_MESSAGE);
    }

    public void showInformation(String str) {
        JOptionPane.showMessageDialog(this, str);
    }

    public int randomize() {
        return rand.nextInt(1000);
    }

    public Color returnColor(Player p) {
        return players.get(p);
    }

    public void endGame() {
        parent.showWinPanel();
    }

    public boolean canBreakTecton(Tecton t) {
        if (drawingPanel.getGTecton(t).getLineCount() > 3) {
            return true;
        } else {
            return false;
        }
    }

    public void refreshTopPanel() {
        player.setText(controller.getCurrentPlayerName());
        round.setText(Integer.toString(controller.getRound()));
    }

    public void tectontTranslateTransform() {
        drawingPanel.tectontTranslateTransform();
    }

    public void setState(GameState state) {
        this.state = state;
        System.out.println(this.state);
    }

    public GameState getState() {
        return state;
    }

    public void setPlayerColors() {
        Color[] colors = { Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW };

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
                gtect.setColor(Color.BLACK);
                break;
            case ("KeepThreadTecton"):
                gtect.setColor(Color.DARK_GRAY);
                break;
        }

        gtect.setDrawingPanel(drawingPanel);

        drawingPanel.addTecton(t, gtect);
        drawingPanel.repaint();
    }

    public void breakTecton(Tecton source, Tecton created1, Tecton created2) {
        drawingPanel.breakTecton(source, created1, created2);
    }

    public void addMushroom(Mushroom m) {
        GMushroom gmush = new GMushroom();
        gmush.setMushroom(m);
        gmush.setDrawingPanel(drawingPanel);

        drawingPanel.addMushroom(m, gmush);
        drawingPanel.repaint();
    }

    public void removeMushroom(Mushroom m) {
        drawingPanel.removeMushroom(m);
        drawingPanel.repaint();
    }

    public void addInsect(Insect i) {
        GInsect gins = new GInsect();
        gins.setInsect(i);
        gins.setDrawingPanel(drawingPanel);

        drawingPanel.addInsect(i, gins);
        drawingPanel.repaint();
    }

    public void removeInsect(Insect i) {
        drawingPanel.removeInsect(i);
        drawingPanel.repaint();
    }

    protected void paintComponent(Graphics g) {
        refreshTopPanel();
        setVisibility();
        super.paintComponent(g);
    }

    private void setVisibility() {
        if (state == GameState.BRANCHTHREAD ||
                state == GameState.GROWMUSHROOM ||
                state == GameState.SELECTMUSHROOMFORSHOOT ||
                state == GameState.CUTTHREAD ||
                state == GameState.SHOOTSPORE ||
                state == GameState.EATINSECT ||
                state == GameState.WAITFUNGALCOMMAND) {

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

            closeButton.setVisible(true);
            moveButton.setVisible(true);
            cutButton.setVisible(true);
            branchButton.setVisible(false);
            growButton.setVisible(false);
            shootButton.setVisible(false);
            eatButton.setVisible(false);

        } else {

            closeButton.setVisible(false);
            moveButton.setVisible(false);
            cutButton.setVisible(false);
            branchButton.setVisible(false);
            growButton.setVisible(false);
            shootButton.setVisible(false);
            eatButton.setVisible(false);

        }
    }

    public void handleButtonClick(ActionEvent e) {

        if (e.getSource() == closeButton) {
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