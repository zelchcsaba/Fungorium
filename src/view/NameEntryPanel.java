package view;

import controller.Controller;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

import static view.FieldCreator.*;


/**
 * A NameEntryPanel osztály egy JPanel leszármazott, amely a játékosok neveinek beviteléért
 * felelős grafikus komponens. Ebben a panelben lehetőség van a felhasználóknak
 * a Gombász és Rovarász játékosok neveinek megadására, valamint
 * a névbeviteli mezők dinamikus kezelésére a játékosok számának megfelelően..
 */
public class NameEntryPanel extends JPanel implements ActionListener {
    private MainWindow parent;
    private Controller controller;
    private List<JTextField> fPlayerNameFields;
    private List<JTextField> iPlayerNameFields;

    private JButton backButton;
    private JButton startButton;
    private JPanel namesPanel;
    private int fungusCount;
    private int insectCount;


    /**
     * NameEntryPanel konstruktor, amely lehetővé teszi a játékosok számára,
     * hogy megadják a nevüket a játék elindítása előtt. A panel tartalmaz egy címet,
     * névbeviteli mezőket és navigációs gombokat.
     *
     * @param parent     A szülő ablak, amely a fő keretet képviseli, és amelyhez ez a panel tartozik.
     * @param controller A játék folyamatait és logikai részeit vezérlő Controller objektum,
     *                   amely együttműködik a névbeviteli panellel.
     */
    public NameEntryPanel(MainWindow parent, Controller controller) {
        this.parent = parent;
        this.controller = controller;

        this.fPlayerNameFields = new ArrayList<>();
        this.iPlayerNameFields = new ArrayList<>();

        setLayout(new BorderLayout());

        JLabel titleLabel = createTitle("Enter your names");
        add(titleLabel, BorderLayout.NORTH);

        namesPanel = new JPanel(new GridBagLayout());
        namesPanel.setBackground(Color.BLACK);
        add(namesPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setBackground(Color.BLACK);
        backButton = createButton("Back");
        startButton = createButton("Start");

        backButton.addActionListener(this);
        startButton.addActionListener(this);

        buttonPanel.add(backButton, BorderLayout.WEST);
        buttonPanel.add(startButton, BorderLayout.EAST);
        add(buttonPanel, BorderLayout.SOUTH);
    }


    /**
     * Frissíti a játékos névmezőket és a mezőket tároló panel kinézetét a jelenlegi játékosok számának
     * megfelelően. Beállítja a gombászokhoz és rovarászokhoz tartozó játékosok számát, majd ennek alapján
     * újrarajzolja a névpaneleket.
     */
    public void updatePlayerFields() {
        fPlayerNameFields.clear();
        iPlayerNameFields.clear();
        namesPanel.removeAll();
        fungusCount = controller.getFungusPlayerCount();
        insectCount = controller.getInsectPlayerCount();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 10, 0, 0); // Margók a komponensek körül
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.BOTH;

        JLabel fungusPlayers = createLabel("fungus players");
        namesPanel.add(fungusPlayers, gbc);
        gbc.gridx++;
        JLabel insectPlayers = createLabel("insect players");
        namesPanel.add(insectPlayers, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        while (gbc.gridy < fungusCount + 1) {
            gbc.gridy++;
            JTextField fField = createTextField();
            fPlayerNameFields.add(fField);
            namesPanel.add(fField, gbc);
        }

        gbc.gridx = 1;
        gbc.gridy = 1;
        while (gbc.gridy < insectCount + 1) {
            gbc.gridy++;
            JTextField iField = createTextField();
            iPlayerNameFields.add(iField);
            namesPanel.add(iField, gbc);
        }

        namesPanel.revalidate(); // újrarajzolás
        namesPanel.repaint(); // újrarajzolás
    }


    /**
     * Kezeli a felhasználói interakciókat az akciógombokra kattintva.
     * Az eseménytípus alapján végrehajtja a megfelelő logikát, például:
     * - A játékosnevek összegyűjtése és az új játék elkezdése.
     * - A kezdőképernyőre való visszalépés.
     *
     * @param e Az akcióeseményt reprezentáló objektum, amely tartalmazza az eseményforrást
     *          és más vonatkozó információkat.
     * @throws IllegalArgumentException Ha a felhasználó által megadott játékosnevek nem érvényesek.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startButton) {
            List<String> fungusNames = new ArrayList<>();
            List<String> insectNames = new ArrayList<>();

            try {
                for (JTextField fField : fPlayerNameFields) {
                    String name = fField.getText().trim(); // A trim() nem fix hogy kell!!!
                    if (name.isEmpty())
                        throw new IllegalArgumentException("All fungus player names must be filled in.");
                    fungusNames.add(name);
                }
                for (JTextField iField : iPlayerNameFields) {
                    String name = iField.getText().trim();
                    if (name.isEmpty())
                        throw new IllegalArgumentException("All insect player names must be filled in.");
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
