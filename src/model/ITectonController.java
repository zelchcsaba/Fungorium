package model;

import java.util.List;

public interface ITectonController {
    public abstract List<Tecton> breakTecton();
    public abstract boolean putFirstMushroom(FungalThread f, Mushroom m);
    public abstract boolean putFirstInsect(Insect i);
    public abstract void absorb();
    public abstract void setNeighbors(List<Tecton> tlist);
    public abstract void setMushroom(Mushroom m);
    public abstract void setInsect(Insect i);
    public abstract boolean putThread(FungalThread f);
    public abstract void setSpores(List<Spore> slist);
}
