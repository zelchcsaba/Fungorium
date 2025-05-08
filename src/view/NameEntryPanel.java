import javax.swing.JButton;
import javax.swing.JPanel;

public class NameEntryPanel extends JPanel{
    private MainWindow parent;

    public NameEntryPanel(MainWindow parent) {
        this.parent = parent;
        JButton startButton = new JButton("Apád");
        startButton.addActionListener(e -> parent.showGamePanel());
        this.add(startButton);
    }
}
