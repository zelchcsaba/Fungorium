package view;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;

import javax.swing.JPanel;

import controller.Controller;
import model.Tecton;


import java.awt.*;
import java.awt.event.*;


public class GamePanel extends JPanel{
    private MainWindow parent;

    Controller controller;

    private java.util.List<GTecton> polygons = new ArrayList<>();

    public GamePanel(MainWindow parent, Controller controller) {
        this.parent = parent;
        this.controller = controller;
        JButton startButton = new JButton("Anyád");
        startButton.addActionListener(e -> parent.showStartScreen());
        this.add(startButton);

        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                Point click = e.getPoint();
                for (GTecton poly : polygons) {
                    if (poly.contains(click)) {
                        poly.toggleSelected();
                        repaint();
                        break;
                    }
                }
            }
        });
    }

    public void addTecton(List<Integer> points, Tecton t){
        GTecton gtect = new GTecton();
        int lineCount = -1;
        for(int i=0; i<points.size()-1;i=i+2){
            int x = points.get(i);
            int y = points.get(i);
            gtect.addPoint(x,y);
            lineCount++;
        }
        gtect.setLineCount(lineCount);
        gtect.setTecton(t);
        polygons.add(gtect);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (GTecton poly : polygons) {
            poly.draw(g);
        }
    }
}
