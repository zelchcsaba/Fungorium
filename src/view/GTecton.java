package view;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import model.ITectonView;
import model.Tecton;

public class GTecton extends Polygon {
    private boolean selected = false;
    private ITectonView t;
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
        this.t = t;
    }

    public ITectonView getTecton(){
        return t;
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

    public void draw(Graphics g) {

        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(selected ? Color.RED : color);
        g2.fillPolygon(this);
        g2.setColor(Color.BLACK);
        g2.drawPolygon(this);
    }
}
