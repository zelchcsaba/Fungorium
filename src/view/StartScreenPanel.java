package view;

import controller.Controller;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import static view.FieldCreator.*;


/**
 * A StartScreenPanel osztály a játék kezdőképernyőjéért felelős.
 * Egy JPanel kiterjesztés, amely lehetővé teszi a játékosok számának
 * és a maximális körök számának megadását.
 */
public class StartScreenPanel extends JPanel implements ActionListener {
    private MainWindow parent;
    Controller controller;
    JTextField fPlayerCount;
    JTextField iPlayerCount;
    JTextField maxRounds;
    JButton nextButton;


    /**
     * A StartScreenPanel konstruktor létrehozza a játék kezdőképernyőjét tartalmazó panelt.
     * A panel tartalmaz címfeliratot, mezőket az adatok megadására, valamint egy gombot a
     * továbblépéshez. A GroupLayout elrendezést használja.
     *
     * @param parent     A MainWindow osztály példánya, amely a panel szülője,
     *                   és lehetővé teszi a különböző panelek közötti váltást.
     * @param controller A Controller osztály példánya, amely a játék logikáját
     *                   és működését kezeli.
     */
    public StartScreenPanel(MainWindow parent, Controller controller) {
        this.parent = parent;
        this.controller = controller;

        setLayout(new BorderLayout());
        setBackground(Color.BLACK);

        // Cím hozzáadása (felül)
        JLabel titleLabel = createTitle("Welcome to Fungorium");
        add(titleLabel, BorderLayout.NORTH);

        // Fő panel létrehozása
        JPanel mainP = new JPanel(new GridBagLayout());
        mainP.setBackground(Color.BLACK);
        add(mainP, BorderLayout.CENTER);

        // Gombok létrehozása
        JLabel fLabel = createLabel("Number of fungus-players:");
        JLabel iLabel = createLabel("Number of insect-players:");
        JLabel rLabel = createLabel("Number of max rounds:");

        fPlayerCount = createTextField();
        iPlayerCount = createTextField();
        maxRounds = createTextField();

        // GridBagConstraints konfigurálása
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 0, 0, 00); // Margók a komponensek körül
        gbc.anchor = GridBagConstraints.CENTER;

        // Gombok elhelyezése
        mainP.add(fLabel, gbc);
        gbc.gridy++;
        mainP.add(iLabel, gbc);
        gbc.gridy++;
        mainP.add(rLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        mainP.add(fPlayerCount, gbc);
        gbc.gridy++;
        mainP.add(iPlayerCount, gbc);
        gbc.gridy++;
        mainP.add(maxRounds, gbc);
        add(mainP, BorderLayout.CENTER);

        // Next gomb létrehozása
        JPanel nextPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        nextPanel.setBackground(Color.BLACK);
        add(nextPanel, BorderLayout.SOUTH);

        nextButton = createButton("Next");
        nextButton.addActionListener(this);
        nextPanel.add(nextButton);
    }


    /**
     * Kezeli a felhasználói interakciókat, különösen a nextButton gomb lenyomását,
     * a bevitt adatok érvényességének ellenőrzésével és az adatok vezérlőn keresztül való továbbításával.
     *
     * @param e Az ActionEvent objektum, amely az eseményt reprezentálja.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == nextButton) {
            try {
                int fCount = Integer.parseInt(fPlayerCount.getText());
                int iCount = Integer.parseInt(iPlayerCount.getText());
                int maxRound = Integer.parseInt(maxRounds.getText());

                if (fCount < 0 || fCount > 4) {
                    throw new IllegalArgumentException();
                }

                if (iCount < 0 || iCount > 4) {
                    throw new IllegalArgumentException();
                }

                if (maxRound < 0) {
                    throw new IllegalArgumentException();
                }

                controller.setFungusPlayerCount(fCount);
                controller.setInsectPlayerCount(iCount);
                controller.setMaxRound(maxRound);
                parent.showNameEntryPanel();

            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid numbers!", "Input Error",
                        JOptionPane.ERROR_MESSAGE);
            }

        }
    }
}
