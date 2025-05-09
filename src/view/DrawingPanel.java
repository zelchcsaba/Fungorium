package view;

import controller.Controller;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JPanel;
import model.Insect;
import model.Mushroom;
import model.Tecton;

public class DrawingPanel extends JPanel{
    private HashMap<Tecton, GTecton> tectCombo;
    private HashMap<Mushroom, GMushroom> mushCombo;
    private HashMap<Insect, GInsect> insCombo;
    private Controller controller;
    private GamePanel gPanel;

    public DrawingPanel(Controller controller, GamePanel gPanel){
        tectCombo = new HashMap();
        mushCombo = new HashMap();
        insCombo = new HashMap();
        this.controller = controller;
        this.gPanel = gPanel;

        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                handleClickEvent(e);
            }       
        });
    }

    public void addTecton(Tecton t, GTecton gtect){
        tectCombo.put(t,gtect);
    }

    public void addMushroom(Mushroom m, GMushroom gmush){
        mushCombo.put(m, gmush);
    }

    public void addInsect(Insect i, GInsect gins){
        insCombo.put(i, gins);
    }

    public void removeMushroom(Mushroom m){
        mushCombo.remove(m);
    }

    public void removeInsect(Insect i){
        insCombo.remove(i);
    }

    public GTecton getGTecton(Tecton t){
        return tectCombo.get(t);
    }

    public GMushroom getGMushroom(Mushroom m){
        return mushCombo.get(m);
    }

    public GInsect getGInsect(Insect i){
        return insCombo.get(i);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        for(Map.Entry<Tecton, GTecton> entry : tectCombo.entrySet()){
                GTecton val = entry.getValue();
                val.draw(g);
            }

            for(Map.Entry<Mushroom, GMushroom> entry : mushCombo.entrySet()){
                GMushroom val = entry.getValue();
                val.draw(g, controller);
            }

            for(Map.Entry<Insect, GInsect> entry : insCombo.entrySet()){
                GInsect val = entry.getValue();
                val.draw(g, controller);
            }
    }

    private void handleClickEvent(MouseEvent e) {
    Point click = e.getPoint();

    for (Map.Entry<Tecton, GTecton> entry : tectCombo.entrySet()) {
        GTecton val = entry.getValue();
        if (val.contains(click)) {
            val.toggleSelected();
            repaint();
            break;
        }
    }
}

}
