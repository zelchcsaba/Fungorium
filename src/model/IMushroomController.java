package model;

import java.util.List;

public interface IMushroomController {
    public abstract boolean shootSpore(Tecton t);
    public abstract boolean evolve();
    public abstract boolean generateSpore(Spore sp);
    public abstract void setPosition(Tecton t);
    public abstract void setSpores(List<Spore> slist);
    public abstract List<Spore> getSpores();
    public abstract void setThread(FungalThread f);
    public abstract void setState(MushroomState mState);
    public abstract void setShootedSporesCount(int count);
    public abstract int getShootedSporesCount();
}
