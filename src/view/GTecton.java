package view;
import controller.Controller;
import controller.FungusPlayer;
import controller.Player;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Stroke;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.imageio.ImageIO;

import model.FungalThread;
import model.ITectonView;
import model.Spore;
import model.Tecton;

import java.util.ArrayList;
import java.util.HashMap;

public class GTecton extends Polygon {
    private boolean selected = false;
    private ITectonView tecton;
    private int lineCount;
    private DrawingPanel drawingPanel;
    private Color color;
    private boolean done = false;

    public GTecton() {
        super();
    }

    public boolean getDone(){
        return done;
    }

    public void setDone(boolean done){
        this.done = done;
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

    public void drawThreads(Graphics g ,Controller controller){

        Graphics2D g2 = (Graphics2D) g;

        for(int i = 0; i< tecton.getNeighbors().size();i++){
            List<FungalThread> fList1 = tecton.getThreads();
            List<FungalThread> fList2 = new ArrayList<>();
            if(tecton.getNeighbors().get(i)!=null){
                if(drawingPanel.getGTecton(tecton.getNeighbors().get(i)).getDone() == false){
                fList2 = tecton.getNeighbors().get(i).getThreads();


            List<FungalThread> f1 = new ArrayList<>(fList1);
            f1.retainAll(fList2);
            if(!f1.isEmpty()){
                System.out.println("anyad");
                for(FungusPlayer fPlayer : controller.getFungusPlayers()){ // játékosokon végig
                            for(int k=0; k<f1.size();k++){
                                if(f1.get(k) == fPlayer.getThread()){
                                    Color c = drawingPanel.getGPanel().returnColor(fPlayer);
                                    
                                    Point p1 = new Point(xpoints[i],ypoints[i]);
                                    Point p2;
                                    if(i+1 == lineCount){
                                        p2 = new Point(xpoints[0],ypoints[0]);
                                    }else{
                                        p2 = new Point(xpoints[i+1],ypoints[i+1]);
                                    }

                                    Point vec = new Point(p2.x-p1.x, p2.y-p1.y);
                                    
                                    double dx = (double)vec.y;
                                    double dy = (double)-vec.x;
                                    double length = Math.sqrt(dx * dx + dy * dy);
                                    dx = dx/length;
                                    dy = dy/length;

                                    if(c.equals(Color.RED)){
                                        Color originalColor = g2.getColor();
                                        Stroke originalStroke = g2.getStroke();

                                                    // Új beállítások
                                        g2.setColor(Color.RED);
                                        g2.setStroke(new BasicStroke(5f));


                                        int startx = (int)(p1.x+vec.x*0.2-dx*20);
                                        int starty = (int)(p1.y+vec.y*0.2-dy*20);
                                        int endx = (int)(startx+dx*80);
                                        int endy = (int)(starty+dy*80);
                                        g2.drawLine(startx, starty, endx, endy);
                                        // Eredeti beállítások visszaállítása
                                        g2.setColor(originalColor);
                                        g2.setStroke(originalStroke);

                                    }else if(c.equals(Color.GREEN)){
                                        Color originalColor = g2.getColor();
                                        Stroke originalStroke = g2.getStroke();

                                                    // Új beállítások
                                        g2.setColor(Color.GREEN);
                                        g2.setStroke(new BasicStroke(5f));


                                        int startx = (int)(p1.x+vec.x*0.4-dx*20);
                                        int starty = (int)(p1.y+vec.y*0.4-dy*20);
                                        int endx = (int)(startx+dx*80);
                                        int endy = (int)(starty+dy*80);
                                        g2.drawLine(startx, starty, endx, endy);
                                        // Eredeti beállítások visszaállítása
                                        g2.setColor(originalColor);
                                        g2.setStroke(originalStroke);

                                    }else if(c.equals(Color.BLUE)){
                                        Color originalColor = g2.getColor();
                                        Stroke originalStroke = g2.getStroke();

                                                    // Új beállítások
                                        g2.setColor(Color.BLUE);
                                        g2.setStroke(new BasicStroke(5f));


                                        int startx = (int)(p1.x+vec.x*0.6-dx*20);
                                        int starty = (int)(p1.y+vec.y*0.6-dy*20);
                                        int endx = (int)(startx+dx*80);
                                        int endy = (int)(starty+dy*80);
                                        g2.drawLine(startx, starty, endx, endy);
                                        // Eredeti beállítások visszaállítása
                                        g2.setColor(originalColor);
                                        g2.setStroke(originalStroke);

                                    }else if(c.equals(color.YELLOW)){
                                        Color originalColor = g2.getColor();
                                        Stroke originalStroke = g2.getStroke();

                                                    // Új beállítások
                                        g2.setColor(Color.YELLOW);
                                        g2.setStroke(new BasicStroke(5f));


                                        int startx = (int)(p1.x+vec.x*0.8-dx*20);
                                        int starty = (int)(p1.y+vec.y*0.8-dy*20);
                                        int endx = (int)(startx+dx*80);
                                        int endy = (int)(starty+dy*80);
                                        g2.drawLine(startx, starty, endx, endy);
                                        // Eredeti beállítások visszaállítása
                                        g2.setColor(originalColor);
                                        g2.setStroke(originalStroke);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        done = true;
    }

    public void draw(Graphics g ,Controller controller) {

        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(selected ? Color.RED : color);
        g2.fillPolygon(this);
        g2.setColor(Color.BLACK);
        g2.drawPolygon(this);


        List<Spore> spores = tecton.getSpores(); // ezek a spórák vannak rajta
        if(spores.isEmpty()){
            return;
        }

        for(FungusPlayer fPlayer : controller.getFungusPlayers()){ // játékosokon végig
            for(Spore s : tecton.getSpores()){ // spórákon végig
                if(s.getThread() == fPlayer.getThread()){ // ha ez a spóra rajta van a tektonon
                    Color playerColor = null;
                    for(Entry<Player, Color> entry : drawingPanel.getGPanel().players.entrySet()){ // kiszedjük a színét
                        if(entry.getKey() == fPlayer){
                            Point center = getCenter();
                            playerColor = entry.getValue();
                            BufferedImage img = null;
                            switch(playerColor.getRGB()) {
                                case 0xFFFF0000: // Color.RED
                                    try{
                                        img = ImageIO.read(new File("spore1.png"));
                                    }catch(IOException e){
                                        e.printStackTrace();
                                    }
                                    g2.drawImage(img, center.x-23, center.y+3, 10, 10, null);
                                break;
                                case 0xFF00FF00: // Color.GREEN
                                    try{
                                        img = ImageIO.read(new File("spore3.png"));
                                    }catch(IOException e){
                                        e.printStackTrace();
                                    }
                                    g2.drawImage(img, center.x-11, center.y+3, 10, 10, null);
                                break;
                                case 0xFF0000FF: // Color.BLUE
                                    try{
                                        img = ImageIO.read(new File("spore2.png"));
                                    }catch(IOException e){
                                        e.printStackTrace();
                                    }
                                    g2.drawImage(img, center.x+1, center.y+3, 10, 10, null);
                                break;
                                case 0xFFFFFF00: // Color.YELLOW
                                    try{
                                        img = ImageIO.read(new File("spore4.png"));
                                    }catch(IOException e){
                                        e.printStackTrace();
                                    }
                                    g2.drawImage(img, center.x+13, center.y+3, 10, 10, null);
                                break;
                            }
                        }
                    }
                }
            }
        }
    }
}