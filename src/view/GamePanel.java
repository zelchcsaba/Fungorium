package view;
import controller.Controller;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import model.Tecton;


public class GamePanel extends JPanel{
    private MainWindow parent;
    DrawingPanel drawingPanel;
    Controller controller;

    private java.util.List<GTecton> polygons = new ArrayList<>();

    public GamePanel(MainWindow parent, Controller controller) {
        this.parent = parent;
        this.controller = controller;

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

        drawingPanel = new DrawingPanel();
        drawingPanel.setBackground(Color.WHITE); // vagy amit szeretnél
        add(drawingPanel, BorderLayout.CENTER);

        // akció gombok
        JPanel actionsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));    
        actionsPanel.setBackground(Color.BLACK);    
        add(actionsPanel, BorderLayout.SOUTH);

        JButton startButton = new JButton("Anyád");
        startButton.addActionListener(e -> parent.showStartScreen());
        actionsPanel.add(startButton);

        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                Point click = e.getPoint();
                for (GTecton poly : polygons) {
                    if (poly.contains(click)) {
                        poly.toggleSelected();
                        drawingPanel.repaint();
                        break;
                    }
                }
            }
        });
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
        polygons.add(gtect);
        drawingPanel.repaint();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (GTecton poly : polygons) {
            poly.draw(g);
        }
    }

    public class DrawingPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            for (GTecton poly : polygons) {
                poly.draw(g);
            }
        }
    }
}


