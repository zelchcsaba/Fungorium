package view;
import javax.swing.JButton;
import javax.swing.JPanel;

import controller.Controller;

public class StartScreenPanel extends JPanel{
    private MainWindow parent;
    Controller controller;

    public StartScreenPanel(MainWindow parent, Controller controller) {
        this.parent = parent;
        this.controller = controller;

        JButton startButton = new JButton("Tovább");
        startButton.addActionListener(e -> parent.showNameEntryPanel());
        this.add(startButton);
    }
}
