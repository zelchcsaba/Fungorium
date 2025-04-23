package controller;
import model.IInsectController;

public class InsectAssociation {
    private IInsectController insect;
    private boolean moved;
    private boolean cut;

    public InsectAssociation(){
        insect = null;
        moved=false;
        cut = false;
    }

    public void setInsect(IInsectController i){
        insect = i;
    }
    public IInsectController getInsect(){
        return insect;
    }
    public void setMoved(boolean m){
        moved = m;
    }
    public boolean getMoved(){
        return moved;
    }
    public void setCut(boolean c){
        cut = c;
    }
    public boolean getCut(){
        return cut;
    }
}
