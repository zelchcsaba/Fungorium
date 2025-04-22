package controller;
import java.util.ArrayList;
import java.util.List;

import model.IInsectController;

public class InsectPlayer extends Player {
    private List<insectAssotiation> insects;

    public InsectPlayer(){
        insects = new ArrayList<>();
    }

    public List<insectAssotiation> getInsects(){
        return insects;
    }

    public void addInsect(IInsectController i){
        insectAssotiation iAssoc = new insectAssotiation();
        iAssoc.setInsect(i);
        insects.add(iAssoc);
    }

    public insectAssotiation getInsectAt(int i){
        return insects.get(i);
    }

    public void removeInsectAt(int i){
        insects.remove(i);
    }

    public void removeInsect(insectAssotiation i){
        insects.remove(i);
    }

    public int getInsectSize(){
        return insects.size();
    }
}
