package view;
import controller.Controller;

import java.util.ArrayList;
import java.util.List;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class NameEntryPanel extends JPanel implements ActionListener{
    private MainWindow parent;
    private Controller controller;
    private List<JTextField> fPlayerNameFields;
    private List<JTextField> iPlayerNameFields;
    private JButton backButton;
    private JButton startButton;
    private JPanel namesPanel;
    private int fungusCount;
    private int insectCount;

    public NameEntryPanel(MainWindow parent, Controller controller) {
        this.parent = parent;
        this.controller = controller;

        this.fPlayerNameFields = new ArrayList<>();
        this.iPlayerNameFields = new ArrayList<>();

        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Enter your names", JLabel.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);

        namesPanel = new JPanel();
        add(namesPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new BorderLayout());
        backButton = new JButton("Back");
        startButton = new JButton("Start");

        backButton.addActionListener(this);
        startButton.addActionListener(this);

        buttonPanel.add(backButton, BorderLayout.WEST);
        buttonPanel.add(startButton, BorderLayout.EAST);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void updatePlayerFields() {
        fPlayerNameFields.clear();
        iPlayerNameFields.clear();
        namesPanel.removeAll(); // vagy amelyik panel tárolja a mezőket
        fungusCount = controller.getFungusPlayerCount();
        insectCount = controller.getInsectPlayerCount();

        namesPanel.setLayout(new GridLayout(Math.max(fungusCount, insectCount) + 1, 2, 10, 10));
        namesPanel.add(new JLabel("Fungus-players", JLabel.CENTER));
        namesPanel.add(new JLabel("Insect-players", JLabel.CENTER));

        for (int i = 0; i < Math.max(fungusCount, insectCount); i++) {
            if (i < fungusCount) {
                JTextField fField = new JTextField(10);
                fPlayerNameFields.add(fField);
                namesPanel.add(fField);
            } else {
                namesPanel.add(new JLabel());
            }

            if (i < insectCount) {
                JTextField iField = new JTextField(10);
                iPlayerNameFields.add(iField);
                namesPanel.add(iField);
            } else {
                namesPanel.add(new JLabel());
            }
        }

        namesPanel.revalidate(); // újrarajzolás
        namesPanel.repaint();    // újrarajzolás
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startButton) {
            List<String> fungusNames = new ArrayList<>();
            List<String> insectNames = new ArrayList<>();

            try {
                for (JTextField fField : fPlayerNameFields) {
                    String name = fField.getText().trim(); //A trim() nem fix hogy kell!!!
                    if (name.isEmpty()) throw new IllegalArgumentException("All fungus player names must be filled in.");
                    fungusNames.add(name);
                }
                for (JTextField iField : iPlayerNameFields) {
                    String name = iField.getText().trim();
                    if (name.isEmpty()) throw new IllegalArgumentException("All insect player names must be filled in.");
                    insectNames.add(name);
                }
                
                controller.createFungusPlayers(fungusNames);
                controller.createInsectPlayers(insectNames);

                parent.showGamePanel();
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Input Error", JOptionPane.ERROR_MESSAGE);
            }

        } else if (e.getSource() == backButton) {
            parent.showStartScreen();
        }
    }
}
