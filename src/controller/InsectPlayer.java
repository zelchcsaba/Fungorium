package controller;
import java.util.ArrayList;
import java.util.List;
import model.IInsectController;
import model.Insect;

public class InsectPlayer extends Player {
    private List<InsectAssociation> insects;

    public InsectPlayer(){
        insects = new ArrayList<>();
    }

    public List<InsectAssociation> getInsects(){
        return insects;
    }

    public void addInsect(IInsectController i){
        InsectAssociation iAssoc = new InsectAssociation();
        iAssoc.setInsect(i);
        insects.add(iAssoc);
    }

    public InsectAssociation getInsectAt(int i){
        return insects.get(i);
    }

    public void removeInsectAt(int i){
        insects.remove(i);
    }

    public void removeInsect(InsectAssociation i){
        insects.remove(i);
    }

    public void rm(Insect i){
        InsectAssociation removable=null;
        for(InsectAssociation ins : insects){
            if(ins.getInsect()==i){
                removable = ins;
            }
        }
        removeInsect(removable);
    }

    public int getInsectSize(){
        return insects.size();
    }

    public InsectAssociation getInsectAssociation(IInsectController insect){
        for (InsectAssociation insectAssociation : insects) {
            if(insectAssociation.getInsect().equals(insect)) return insectAssociation;
        }
        return null;
    }
}
