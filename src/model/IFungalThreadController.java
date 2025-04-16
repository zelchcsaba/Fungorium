package model;

import java.util.List;

public interface IFungalThreadController {
    public abstract void deleteUnnecessaryThreads();
    public abstract boolean branchThread(Tecton t);
    public abstract boolean growMushroom(Tecton t, Mushroom m);
    public abstract void timeCheck();
    public abstract boolean eatInsect(Insect i);
    public abstract void setTectons(List<Tecton> tlist);

}
