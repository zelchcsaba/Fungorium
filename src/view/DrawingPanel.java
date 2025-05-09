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
    private GTecton selectedSource;
    private GTecton targetSource;

    public DrawingPanel(Controller controller, GamePanel gPanel){
        tectCombo = new HashMap();
        mushCombo = new HashMap();
        insCombo = new HashMap();
        this.controller = controller;
        this.gPanel = gPanel;

        selectedSource = null;
        targetSource = null;

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
    GTecton selected = null;
    for (Map.Entry<Tecton, GTecton> entry : tectCombo.entrySet()) {
        GTecton val = entry.getValue();
        if (val.contains(click)) {
            val.toggleSelected();
            selected = val;
            repaint();
            break;
        }
    }

    if(selected!=null){
        GameState state= gPanel.getState();

        switch (state){
            case GameState.SELECTINSECTFORMOVE:
                selectedSource = selected;
                gPanel.setState(GameState.MOVEINSECT);
            break;
            case GameState.MOVEINSECT:
                targetSource = selected;
                controller.move(selectedSource.getTecton().getInsect(), targetSource.getTecton());
                gPanel.setState(GameState.WAITINSECTCOMMAND);
            break;
            case GameState.SELECTINSECTFORCUT:
                selectedSource = selected;
                gPanel.setState(GameState.CUTTHREAD);
            break;
            case GameState.CUTTHREAD:
                targetSource = selected;
                controller.cut(selectedSource.getTecton().getInsect(), targetSource.getTecton());
                gPanel.setState(GameState.WAITINSECTCOMMAND);
            break;
            case GameState.BRANCHTHREAD:
                controller.branchThread(selected.getTecton());
                gPanel.setState(GameState.WAITFUNGALCOMMAND);
            break;
            case GameState.EATINSECT:
                controller.eatInsect(selected.getTecton().getInsect());
                gPanel.setState(GameState.WAITFUNGALCOMMAND);
            break;
            case GameState.SELECTMUSHROOMFORSHOOT:
                selectedSource = selected;
                gPanel.setState(GameState.SHOOTSPORE);
            break;
            case GameState.SHOOTSPORE:
                targetSource = selected;
                controller.shootSpore(selectedSource.getTecton().getMushroom(), targetSource.getTecton());
                gPanel.setState(GameState.WAITFUNGALCOMMAND);
            break;
            case GameState.GROWMUSHROOM:
                controller.growMushroom(selected.getTecton());
                gPanel.setState(GameState.WAITFUNGALCOMMAND);
            break;
            case GameState.PUTFIRSTINSECT:
                controller.putFirstInsect(selected.getTecton());
            break;
            case GameState.PUTFIRSTMUSHROOM:
                controller.putFirstMushroom("ShortLifeThread", selected.getTecton());
            break;
            default:
            break;
        }
    }

}

}
