package model;

import java.util.List;

/**
 * Ez az interfész a gomba (Mushroom) vezérléséért felelős meghatározott műveletek
 * és viselkedésmodellek definícióját tartalmazza. Az ebben szereplő metódusok
 * végrehajtásra kerülő funkciókat biztosítanak a spórákkal kapcsolatos műveletekre,
 * a gomba állapotváltozására és a megfelelő pozíciók beállítására.
 */
public interface IMushroomController {

    boolean shootSpore(Tecton t);

    boolean evolve();

    boolean generateSpore(Spore sp);

    void setPosition(Tecton t);

    void setSpores(List<Spore> slist);

    List<Spore> getSpores();

    void setThread(FungalThread f);

    void setState(MushroomState mState);

    void setShootedSporesCount(int count);

    int getShootedSporesCount();

    FungalThread getThread();

}
