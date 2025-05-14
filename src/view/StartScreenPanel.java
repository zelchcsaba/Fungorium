package view;

import controller.Controller;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;


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

        JLabel titleLabel = new JLabel("Fungorium");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 32));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel fLabel = new JLabel("Number of fungus-players:");
        JLabel iLabel = new JLabel("Number of insect-players:");
        JLabel rLabel = new JLabel("Number of maximum rounds:");

        fPlayerCount = new JTextField(10);
        iPlayerCount = new JTextField(10);
        maxRounds = new JTextField(10);

        nextButton = new JButton("Next");
        nextButton.addActionListener(this);
        this.add(nextButton);

        setLayout(new BorderLayout());

        JPanel centerPanel = new JPanel();
        GroupLayout layout = new GroupLayout(centerPanel);
        centerPanel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(titleLabel)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                        .addComponent(fLabel)
                                        .addComponent(iLabel)
                                        .addComponent(rLabel))
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(fPlayerCount)
                                        .addComponent(iPlayerCount)
                                        .addComponent(maxRounds)))
                        .addGroup(layout.createSequentialGroup()
                                .addGap(200)
                                .addComponent(nextButton)));

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addComponent(titleLabel)
                        .addGap(20)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(fLabel)
                                .addComponent(fPlayerCount))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(iLabel)
                                .addComponent(iPlayerCount))
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(rLabel)
                                .addComponent(maxRounds))
                        .addGap(20)
                        .addComponent(nextButton));

        add(centerPanel, BorderLayout.CENTER);
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
