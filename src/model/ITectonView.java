package model;

import java.util.List;

/**
 * Az ITectonView interfész a Tecton adott nézetének lekérdezésére szolgál.
 * A nézetet érintő elemekkel kapcsolatos információkat biztosít.
 */
public interface ITectonView {

    Insect getInsect();

    Mushroom getMushroom();

    List<FungalThread> getThreads();

    List<Spore> getSpores();
    
    List<Tecton> getNeighbors();
}
