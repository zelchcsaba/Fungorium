package view;
import controller.Controller;
import javax.swing.JButton;
import javax.swing.JPanel;

public class NameEntryPanel extends JPanel{
    private MainWindow parent;
    Controller controller;

    public NameEntryPanel(MainWindow parent, Controller controller) {
        this.parent = parent;
        this.controller = controller;

        JButton startButton = new JButton("Apád");
        startButton.addActionListener(e -> parent.showGamePanel());
        this.add(startButton);
    }
}
