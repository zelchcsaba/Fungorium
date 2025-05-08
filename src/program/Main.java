package program;

import controller.*;
import view.MainWindow;

import java.util.Scanner;

import javax.swing.SwingUtilities;


public class Main {
    public static void main(String[] args) {
       
        SwingUtilities.invokeLater(() -> {
            MainWindow frame = new MainWindow(); 
            frame.setVisible(true);           
        });

    }
}
