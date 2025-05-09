package program;

import javax.swing.SwingUtilities;
import view.MainWindow;


public class Main {
    public static void main(String[] args) {
       
        SwingUtilities.invokeLater(() -> {
            MainWindow frame = new MainWindow(); 
            frame.setVisible(true);           
        });

    }
}
