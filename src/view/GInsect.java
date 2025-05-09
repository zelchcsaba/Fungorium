package view;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import controller.Controller;
import model.Insect;

public class GInsect {
    private Insect i;
    private DrawingPanel drawingPanel;
    public GInsect(){}

    public void setDrawingPanel(DrawingPanel drawingPanel){
        this.drawingPanel = drawingPanel;
    }

    public void setInsect(Insect i){
        this.i = i;
    }

    public Insect getInsect(){
        return i;
    }

    public void draw(Graphics g, Controller controller) {
        Graphics2D g2 = (Graphics2D) g;
        Point center = drawingPanel.getGTecton(i.getPosition()).getCenter();
        
        BufferedImage img = null;
        try{
            img = ImageIO.read(new File("bug1.png"));
        }catch(IOException e){
            e.printStackTrace();
        }
        g2.drawImage(img, center.x+3, center.y-28, 23, 23, null);
    }
}
