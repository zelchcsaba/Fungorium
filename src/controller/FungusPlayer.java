package controller;
import java.util.ArrayList;
import java.util.List;
import model.IFungalThreadController;
import model.IMushroomController;

public class FungusPlayer extends Player {
    private IFungalThreadController fungalThread;
    private boolean branchThread;
    private List<mushroomAssociation> mushrooms;

    public FungusPlayer(){
        fungalThread = null;
        branchThread = false;
        mushrooms = new ArrayList<>();
    }

    public IFungalThreadController getThread(){
        return fungalThread;
    }

    public void setThread(IFungalThreadController f){
        fungalThread = f;
    }

    public boolean getBranchThread(){
        return branchThread;
    }

    public void setBranchThread(boolean b){
        branchThread = b;
    }

    public List<mushroomAssociation> getMushrooms(){
        return mushrooms;
    }

    public void addMushroom(IMushroomController m){
        mushroomAssociation mAssoc = new mushroomAssociation();
        mAssoc.setMushroom(m);
        mushrooms.add(mAssoc);
    }

    public void addMushroomAssociation(mushroomAssociation ma){
        mushrooms.add(ma);
    }

    public mushroomAssociation getMushroomAt(int i){
        return mushrooms.get(i);
    }

    public void removeMushroomAt(int i){
        mushrooms.remove(i);
    }

    public void removeMushroom(mushroomAssociation m){
        mushrooms.remove(m);
    }

    public int getMushroomSize(){
        return mushrooms.size();
    }
}
