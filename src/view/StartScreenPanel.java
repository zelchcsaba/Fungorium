package view;
import javax.swing.JButton;
import javax.swing.JPanel;

public class StartScreenPanel extends JPanel{
    private MainWindow parent;

    public StartScreenPanel(MainWindow parent) {
        this.parent = parent;
        JButton startButton = new JButton("Tovább");
        startButton.addActionListener(e -> parent.showNameEntryPanel());
        this.add(startButton);
    }
}
