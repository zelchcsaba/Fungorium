package view;

import controller.Controller;
import controller.FungusPlayer;
import controller.Player;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import javax.imageio.ImageIO;
import model.IMushroomView;
import model.Mushroom;

public class GMushroom {
    private IMushroomView m;
    private DrawingPanel drawingPanel;
    public GMushroom(){}

    public void setDrawingPanel(DrawingPanel drawingPanel){
        this.drawingPanel = drawingPanel;
    }

    public void setMushroom(Mushroom m){
        this.m = m;
    }

    public IMushroomView getMushroom(){
        return m;
    }

    public void draw(Graphics g, Controller controller) {
        Graphics2D g2 = (Graphics2D) g;
        Point center = drawingPanel.getGTecton(m.getPosition()).getCenter();
        FungusPlayer fPlayer = null;
        for(int i = 0; i < controller.getFungusPlayers().size(); i++){
            for(int j = 0; j < controller.getFungusPlayers().get(i).getMushrooms().size(); j++){
                if(controller.getFungusPlayers().get(i).getMushrooms().get(j).getMushroom() == m){
                    fPlayer = controller.getFungusPlayers().get(i);
                    break;
                }
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

        if(playerColor == null){
            return;
        }
        
        String pathName = null;
        switch (playerColor.getRGB()) {
            case 0xFFFF0000: // Color.RED
                pathName = "mush1.png";
                break;
            case 0xFF00FF00: // Color.GREEN
                pathName = "mush3.png";
                break;
            case 0xFF0000FF: // Color.BLUE
                pathName = "mush2.png";
                break;
            case 0xFFFFFF00: // Color.YELLOW
                pathName = "mush4.png";
                break;
        }

        BufferedImage img = null;
        try{
            img = ImageIO.read(new File(pathName));
        }catch(IOException e){
            e.printStackTrace();
        }
        g2.drawImage(img, center.x-28, center.y-28, 23, 23, null);
    }

}
