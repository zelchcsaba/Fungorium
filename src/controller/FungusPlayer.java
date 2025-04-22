package controller;
import java.util.ArrayList;
import java.util.List;
import model.IFungalThreadController;
import model.IMushroomController;

public class FungusPlayer extends Player {
    private IFungalThreadController fungalThread;
    private boolean branchThread;
    private List<mushroomAssotiation> mushrooms;

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

    public List<mushroomAssotiation> getMushrooms(){
        return mushrooms;
    }

    public void addMushroom(IMushroomController m){
        mushroomAssotiation mAssoc = new mushroomAssotiation();
        mAssoc.setMushroom(m);
        mushrooms.add(mAssoc);
    }

    public mushroomAssotiation getMushroomAt(int i){
        return mushrooms.get(i);
    }

    public void removeMushroomAt(int i){
        mushrooms.remove(i);
    }

    public void removeMushroom(mushroomAssotiation m){
        mushrooms.remove(m);
    }

    public int getMushroomSize(){
        return mushrooms.size();
    }
}
