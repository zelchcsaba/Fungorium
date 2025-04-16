package model;

import java.util.List;

public interface ITectonView {
    public abstract Insect getInsect();
    public abstract Mushroom getMushroom();
    public abstract List<FungalThread> getThreads();
    public abstract List<Spore> getSpores();

}
