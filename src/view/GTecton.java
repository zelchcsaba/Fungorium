package view;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.List;
import java.util.Map.Entry;

import javax.imageio.ImageIO;

import controller.Controller;
import controller.FungusPlayer;
import controller.Player;

import java.awt.Point;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import model.ITectonView;
import model.Tecton;
import model.Spore;

public class GTecton extends Polygon {
    private boolean selected = false;
    private ITectonView tecton;
    private int lineCount;
    private DrawingPanel drawingPanel;
    private Color color;

    public GTecton() {
        super();
    }

    public void setColor(Color color){
        this.color = color;
    }

    public Color getColor(){
        return color;
    }

    public void setDrawingPanel(DrawingPanel drawingPanel){
        this.drawingPanel = drawingPanel;
    }

    public void setTecton(Tecton t){
        tecton = t;
    }

    public ITectonView getTecton(){
        return tecton;
    }

    public void setLineCount(int lineCount){
        this.lineCount = lineCount;
    }

    public int getLineCount(){
        return lineCount;
    }

    public GTecton(int[] xpoints, int[] ypoints, int npoints) {
        super(xpoints, ypoints, npoints);
    }

    public boolean isSelected() {
        return selected;
    }

    public void toggleSelected() {
        selected = !selected;
    }

    public Point getCenter(){
        int sumX = 0;
        int sumY = 0;

        for (int i = 0; i < npoints; i++) {
            sumX += xpoints[i];
            sumY += ypoints[i];
        }

        int centerX = sumX / npoints;
        int centerY = sumY / npoints;

        return new Point(centerX, centerY);
    }

    public void draw(Graphics g /*,Controller controller*/) {

        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(selected ? Color.RED : color);
        g2.fillPolygon(this);
        g2.setColor(Color.BLACK);
        g2.drawPolygon(this);

        /*List<Spore> spores = tecton.getSpores();
        if(spores.isEmpty()){
            return;
        }

        for(Spore spore : spores){
            FungusPlayer fPlayer = null;
            for(FungusPlayer player : controller.getFungusPlayers()){
                if(player.getThread() == spore.getThread()){
                    fPlayer = player;
                }
            }

            if(fPlayer == null){
                return;
            }
            
            Color playerColor = null;
            for(Entry<Player, Color> entry : drawingPanel.getGPanel().players.entrySet()){
                if(entry.getKey() == fPlayer){
                    playerColor = entry.getValue();
                    break;
                }
            }

            
            String pathName = null;
            switch (playerColor.getRGB()) {
                case 0xFFFF0000: // Color.RED
                    pathName = "spore1.png";
                    break;
                case 0xFF00FF00: // Color.GREEN
                    pathName = "spore3.png";
                    break;
                case 0xFF0000FF: // Color.BLUE
                    pathName = "spore2.png";
                    break;
                case 0xFFFFFF00: // Color.YELLOW
                    pathName = "spore4.png";
                    break;
            }

           //Kirajz spóra
        }*/


    }
}
