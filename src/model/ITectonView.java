package model;

import java.util.List;

/**
 * Az ITectonView interfész a Tecton adott nézetének lekérdezésére szolgál.
 * A nézetet érintő elemekkel kapcsolatos információkat biztosít.
 */
public interface ITectonView {

    public abstract Insect getInsect();

    public abstract Mushroom getMushroom();

    public abstract List<FungalThread> getThreads();

    public abstract List<Spore> getSpores();
}
