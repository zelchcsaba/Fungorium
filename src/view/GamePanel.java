package view;
import controller.Controller;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;


import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import model.Insect;
import model.Mushroom;
import model.Tecton;


public class GamePanel extends JPanel{
    private MainWindow parent;
    private DrawingPanel drawingPanel;
    private Controller controller;
    private GameState state;
    private JButton moveButton;
    private JButton cutButton;
    private JButton branchButton;
    private JButton growButton;
    private JButton shootButton;
    private JButton eatButton;
    private JButton closeButton;


    public GamePanel(MainWindow parent, Controller controller) {
        this.parent = parent;
        this.controller = controller;
        state = GameState.PUTFIRSTINSECT;

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
        
        JLabel player = new JLabel("Huszi");
        player.setForeground(Color.BLACK); 
        playerPanel.add(player);

        // kör
        JPanel roundPanel = new JPanel(new FlowLayout());
        roundPanel.setBackground(Color.CYAN);
        topPanel.add(roundPanel, BorderLayout.EAST);

        JLabel roundNumber = new JLabel("Round:");
        roundNumber.setForeground(Color.BLACK);
        roundPanel.add(roundNumber);

        JLabel number = new JLabel("1");
        number.setForeground(Color.BLACK);
        roundPanel.add(number);


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

        setVisibility();

    }

    public void setState(GameState state){
        this.state = state;
    }

    public GameState getState(){
        return state;
    }

    public void addTecton(List<Integer> points, Tecton t){

        GTecton gtect = new GTecton();
        int lineCount = 0;
        for(int i=0; i<points.size()-1; i=i+2){
            int x = points.get(i);
            int y = points.get(i+1);
            gtect.addPoint(x,y);
            lineCount++;
        }
        gtect.setLineCount(lineCount);
        gtect.setTecton(t);
        gtect.setDrawingPanel(drawingPanel);

        drawingPanel.addTecton(t, gtect);
        drawingPanel.repaint();
    }

    public void addMushroom(Mushroom m){
        GMushroom gmush = new GMushroom();
        gmush.setMushroom(m);
        gmush.setDrawingPanel(drawingPanel);

        drawingPanel.addMushroom(m, gmush);
        drawingPanel.repaint();
    }

    public void removeMushroom(Mushroom m){
        drawingPanel.removeMushroom(m);
        drawingPanel.repaint();
    }

    public void addInsect(Insect i){
        GInsect gins = new GInsect();
        gins.setInsect(i);
        gins.setDrawingPanel(drawingPanel);

        drawingPanel.addInsect(i, gins);
        drawingPanel.repaint();
    }

    public void removeInsect(Insect i){
        drawingPanel.removeInsect(i);
        drawingPanel.repaint();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }

    private void setVisibility(){
        if(state == GameState.BRANCHTHREAD || 
        state == GameState.GROWMUSHROOM ||
        state == GameState.SELECTMUSHROOMFORSHOOT ||
        state == GameState.CUTTHREAD ||
        state == GameState.SHOOTSPORE ||
        state == GameState.EATINSECT ||
        state == GameState.WAITFUNGALCOMMAND){
            closeButton.setVisible(true);
            moveButton.setVisible(true);
            cutButton.setVisible(true);
            branchButton.setVisible(false);
            growButton.setVisible(false);
            shootButton.setVisible(false);
            eatButton.setVisible(false);
        }else if(state == GameState.SELECTINSECTFORMOVE ||
        state == GameState.SELECTINSECTFORCUT ||
        state == GameState.WAITINSECTCOMMAND){
            closeButton.setVisible(true);
            moveButton.setVisible(false);
            cutButton.setVisible(false);
            branchButton.setVisible(true);
            growButton.setVisible(true);
            shootButton.setVisible(true);
            eatButton.setVisible(true);
        }else{
            closeButton.setVisible(false);
            moveButton.setVisible(false);
            cutButton.setVisible(false);
            branchButton.setVisible(false);
            growButton.setVisible(false);
            shootButton.setVisible(false);
            eatButton.setVisible(false);
        }
        revalidate();
        repaint();
    }

    public void handleButtonClick(ActionEvent e) {

            if (e.getSource() == closeButton) {
                parent.showStartScreen();
            } else if (e.getSource() == moveButton) {
                state = GameState.SELECTINSECTFORMOVE;
            } else if (e.getSource() == cutButton) {
                 state = GameState.SELECTINSECTFORCUT;
            }  else if (e.getSource() == branchButton) {
                 state = GameState.BRANCHTHREAD;
            } else if (e.getSource() == growButton) {
                 state = GameState.GROWMUSHROOM;
            } else if (e.getSource() == shootButton) {
                 state = GameState.SELECTMUSHROOMFORSHOOT;
            } else if (e.getSource() == eatButton) {
                 state = GameState.EATINSECT;
            }
            setVisibility(); 
    }

}


