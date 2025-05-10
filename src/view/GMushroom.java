package view;

import controller.Controller;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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
        
        BufferedImage img = null;
        try{
            img = ImageIO.read(new File("mush1.png"));
        }catch(IOException e){
            e.printStackTrace();
        }
        g2.drawImage(img, center.x-28, center.y-28, 23, 23, null);
    }

}
