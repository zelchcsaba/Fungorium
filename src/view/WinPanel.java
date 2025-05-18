package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JPanel;

import controller.Controller;

import static view.FieldCreator.*; // createLabel


/**
 * A WinPanel osztály a játék eredménylistájának (leaderboard) megjelenítésére szolgál.
 * A JPanel osztályból öröklődik, és keretet biztosít a játékosok pontszámainak kezeléséhez és vizualizálásához.
 * A panel két külön leaderboardot jelenít meg: az egyiket gombász, a másikat rovarász játékosok szerint.
 */
public class WinPanel extends JPanel {
    private MainWindow parent;
    private Controller controller;

    private Map<String, Integer> fungalScores = new LinkedHashMap<String, Integer>();
    private Map<String, Integer> insectScores = new LinkedHashMap<String, Integer>();

    private int maxFungalScore;
    private int maxInsectScore;


    /**
     * A metódus a fungalScores nevű Map elemeit érték szerint
     * csökkenő sorrendbe rendezi. A rendezési folyamat során az eredeti Map
     * elemei eltávolításra kerülnek, majd a rendezett elemek visszaírásra
     * kerülnek ugyanabba a Map-be.
     */
    public void sortFungal() {
        List<Map.Entry<String, Integer>> entryList = new ArrayList<>(fungalScores.entrySet());
        entryList.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue())); // Érték szerint csökkenő
        // sorrend

        maxFungalScore = entryList.get(0).getValue(); // mellékesen kiszedjük a max score-t

        // Töröljük az eredeti térképet és hozzáadjuk a rendezett elemeket
        fungalScores.clear();
        for (Map.Entry<String, Integer> entry : entryList) {
            fungalScores.put(entry.getKey(), entry.getValue());
        }

    }


    /**
     * A rovarokhoz tartozó pontszámokat tartalmazó Map-et
     * rendezi csökkenő sorrendbe az értékek alapján, majd
     * frissíti az eredeti térképet a rendezett sorrenddel.
     */
    public void sortInsect() {
        List<Map.Entry<String, Integer>> entryList = new ArrayList<>(insectScores.entrySet());
        entryList.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue())); // Érték szerint csökkenő
        // sorrend

        maxInsectScore = entryList.get(0).getValue();

        // Töröljük az eredeti térképet és hozzáadjuk a rendezett elemeket
        insectScores.clear();
        for (Map.Entry<String, Integer> entry : entryList) {
            insectScores.put(entry.getKey(), entry.getValue());
        }
    }


    /**
     * A metódus betölti a rovarász és gombász játékosok pontszámait
     * a controller objektumtól, és átrendezi azokat, ha nem üres adatokat tartalmaznak.
     */
    public void loadPlayers() {

        insectScores = controller.getInsectScores();
        fungalScores = controller.getFungalScores();

        if (insectScores.size() > 0)
            sortInsect();
        if (fungalScores.size() > 0)
            sortFungal();
    }

    /**
     * A WinPanel osztály konstruktora, amely inicializálja a győzelmi panelt.
     * Beállítja az elrendezést, és kapcsolja a főablakhoz, valamint a vezérlőhöz.
     *
     * @param parent     A főablak, amelyhez ez a panel tartozik.
     * @param controller A játékmenetet irányító vezérlő, amely biztosítja az adatokat és a logikát.
     */
    public WinPanel(MainWindow parent, Controller controller) {
        this.parent = parent;
        this.controller = controller;
        setLayout(new BorderLayout());
    }

    /**
     * A metódus frissíti a nyertes panel tartalmát, új adatokat töltve be és újrarajzolva
     * a felhasználói felületet.
     */
    public void refresh() {
        loadPlayers();

        // Top panel: Cím 
        JPanel topPanel = new JPanel(new BorderLayout());

        // Cím hozzáadása
        JLabel titleL = createTitle("Leaderboard");
        topPanel.add(titleL, BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.BLACK);
        // GridBagConstraints konfigurálása
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 0, 0); // Margók a komponensek körül
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.BOTH;

        JLabel fungusPlayers = createLabel("fungus players");
        gbc.gridwidth = 2;
        mainPanel.add(fungusPlayers, gbc);
        gbc.gridx++;
        gbc.gridx++;
        JLabel insectPlayers = createLabel("insect players");
        mainPanel.add(insectPlayers, gbc);

        gbc.gridwidth = 1;

        // Ciklus ami kirajzolja a leaderboard tartalmát táblázat formájában
        for (Map.Entry<String, Integer> entry : fungalScores.entrySet()) {
            gbc.gridx = 0;
            gbc.gridy++;
            JLabel player = createLabel(entry.getKey());

            mainPanel.add(player, gbc);
            gbc.gridx++;
            JLabel point = createLabel(Integer.toString(entry.getValue()));
            if (entry.getValue() == maxFungalScore) {
                player.setForeground(Color.YELLOW);
                point.setForeground(Color.YELLOW);
            }
            mainPanel.add(point, gbc);
            gbc.gridx++;
        }

        gbc.gridy = 0;

        for (Map.Entry<String, Integer> entry : insectScores.entrySet()) {
            gbc.gridx = 2;
            gbc.gridy++;
            JLabel player = createLabel(entry.getKey());

            mainPanel.add(player, gbc);
            gbc.gridx++;
            JLabel point = createLabel(Integer.toString(entry.getValue()));
            if (entry.getValue() == maxInsectScore) {
                player.setForeground(Color.YELLOW);
                point.setForeground(Color.YELLOW);
            }
            mainPanel.add(point, gbc);
            gbc.gridx++;
        }

        add(mainPanel, BorderLayout.CENTER);
    }

}