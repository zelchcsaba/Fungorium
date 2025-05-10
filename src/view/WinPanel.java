package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import controller.Controller;


public class WinPanel extends JPanel{
    private MainWindow parent;
    private Controller controller;

    private Map<String, Integer> fungalScores = new LinkedHashMap<String, Integer>();
    private Map<String, Integer> insectScores = new LinkedHashMap<String, Integer>();

    private int maxFungalScore;
    private int maxInsectScore;

    public JLabel createLabel(String text){
        JLabel field = new JLabel(text);
        field.setPreferredSize(new Dimension(120, 60));
        field.setFont(new Font("Arial", Font.BOLD, 13));
        field.setBackground(Color.BLACK);
        field.setForeground(Color.WHITE);
        field.setOpaque(true);
        field.setHorizontalAlignment(SwingConstants.CENTER); // Szöveg középre igazítása vízszintesen
        Border border = BorderFactory.createLineBorder(Color.WHITE, 1); // Fekete vonalas keret, 2 pixel vastagság
        field.setBorder(border);
        return field;
    }

    public void sortFungal() {
        List<Map.Entry<String, Integer>> entryList = new ArrayList<>(fungalScores.entrySet());
        entryList.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue())); // Érték szerint csökkenő sorrend
    
        maxFungalScore = entryList.get(0).getValue(); // mellékesen kiszedjük a max score-t

        // Töröljük az eredeti térképet és hozzáadjuk a rendezett elemeket
        fungalScores.clear();
        for (Map.Entry<String, Integer> entry : entryList) {
            fungalScores.put(entry.getKey(), entry.getValue());
        }

    }

    public void sortInsect() {
        List<Map.Entry<String, Integer>> entryList = new ArrayList<>(insectScores.entrySet());
        entryList.sort((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue())); // Érték szerint csökkenő sorrend
    
        maxInsectScore = entryList.get(0).getValue();

        // Töröljük az eredeti térképet és hozzáadjuk a rendezett elemeket
        insectScores.clear();
        for (Map.Entry<String, Integer> entry : entryList) {
            insectScores.put(entry.getKey(), entry.getValue());
        }
    }

    public void loadPlayers(){
        // fungalScores.put("huszi", 100);
        // fungalScores.put("goldi", 1010);
        // fungalScores.put("huszii", 1010);

        // insectScores.put("ruszi", 100);
        // insectScores.put("puli", 100);
        // insectScores.put("rudi", 10);
        // insectScores.put("asa", 80);

        insectScores = controller.getInsectScores();
        fungalScores = controller.getFungalScores();

        if(insectScores.size()>0)sortInsect();
        if(fungalScores.size()>0)sortFungal();
    }

    public WinPanel(MainWindow parent, Controller controller) {
        this.parent = parent;
        this.controller = controller;
        setLayout(new BorderLayout());
    }

    public void refresh(){
        loadPlayers();

        // Top panel: Cím és háttérkép
        JPanel topPanel = new JPanel(new BorderLayout());

        // Cím hozzáadása
        JLabel titleL = new JLabel("Leaderboard", SwingConstants.CENTER);
        titleL.setFont(new Font("Arial", Font.BOLD, 50));
        titleL.setForeground(Color.WHITE);
        titleL.setOpaque(true);
        titleL.setBackground(Color.BLACK);

        titleL.setHorizontalTextPosition(SwingConstants.CENTER);
        titleL.setVerticalTextPosition(SwingConstants.CENTER);
        topPanel.add(titleL, BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);
        
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.BLACK);
        // GridBagConstraints konfigurálása
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0,0, 0, 0); // Margók a komponensek körül
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.BOTH;

        JLabel fungusPlayers = createLabel("fungus players");
        fungusPlayers.setBackground(Color.BLACK);
        fungusPlayers.setForeground(Color.white);
        gbc.gridwidth = 2;
        mainPanel.add(fungusPlayers, gbc);
        gbc.gridx++;
        gbc.gridx++;
        JLabel insectPlayers = createLabel("insect players");
        insectPlayers.setBackground(Color.BLACK);
        insectPlayers.setForeground(Color.white);
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
            if(entry.getValue()==maxFungalScore){
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
            if(entry.getValue()==maxInsectScore){
                player.setForeground(Color.YELLOW);
                point.setForeground(Color.YELLOW);
            } 
            mainPanel.add(point, gbc);
            gbc.gridx++;
        }
        add(mainPanel, BorderLayout.CENTER);

    }

    
}
