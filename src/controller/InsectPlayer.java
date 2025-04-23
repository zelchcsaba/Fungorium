package controller;
import java.util.ArrayList;
import java.util.List;
import model.IInsectController;

public class InsectPlayer extends Player {
    private List<insectAssociation> insects;

    public InsectPlayer(){
        insects = new ArrayList<>();
    }

    public List<insectAssociation> getInsects(){
        return insects;
    }

    public void addInsect(IInsectController i){
        insectAssociation iAssoc = new insectAssociation();
        iAssoc.setInsect(i);
        insects.add(iAssoc);
    }

    public insectAssociation getInsectAt(int i){
        return insects.get(i);
    }

    public void removeInsectAt(int i){
        insects.remove(i);
    }

    public void removeInsect(insectAssociation i){
        insects.remove(i);
    }

    public int getInsectSize(){
        return insects.size();
    }
}
