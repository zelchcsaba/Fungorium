package view;
import javax.swing.JButton;
import javax.swing.JPanel;

import controller.Controller;

public class NameEntryPanel extends JPanel{
    private MainWindow parent;
    private Controller controller;
    private int fPlayerCount;
    private int iPlayerCount;


    public NameEntryPanel(MainWindow parent, Controller controller) {
        this.parent = parent;
        this.controller = controller;

        JButton startButton = new JButton("Apád");
        startButton.addActionListener(e -> parent.showGamePanel());
        this.add(startButton);
    }

    public void setFPlayerCount(int fPlayerCount){
        this.fPlayerCount = fPlayerCount;
    }

    public void setIPlayerCount(int iPlayerCount){
        this.iPlayerCount = iPlayerCount;
    }
}
