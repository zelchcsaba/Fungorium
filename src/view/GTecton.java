package view;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;

import model.Tecton;

public class GTecton extends Polygon {
    private boolean selected = false;
    private Tecton t;
    private int lineCount;

    public GTecton() {
        super();
    }

    public void setTecton(Tecton t){
        this.t = t;
    }

    public Tecton getTecton(){
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

    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(selected ? Color.RED : Color.BLUE);
        g2.fillPolygon(this);
        g2.setColor(Color.BLACK);
        g2.drawPolygon(this);
    }
}
