package view;
import controller.Controller;
import controller.FungusPlayer;
import controller.Player;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map.Entry;
import javax.imageio.ImageIO;
import model.ITectonView;
import model.Spore;
import model.Tecton;

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

    public void setSelected(boolean b) {
        selected = b;
    }

    public void switchSelected(){
        selected = !selected;
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