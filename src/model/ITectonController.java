package model;

import java.util.List;

/**
 * Az ITectonController egy interfész, amely meghatározza a különböző műveleteket,
 * amelyek egy Tecton elem kezelésére és manipulálására irányulnak.
 */
public interface ITectonController {

    List<Tecton> breakTecton();

    boolean putFirstMushroom(FungalThread f, Mushroom m);

    boolean putFirstInsect(Insect i);

    void absorb();

    boolean setMushroom(Mushroom m);

    void setInsect(Insect i);

    boolean putThread(FungalThread f);

    void setSpores(List<Spore> slist);

    List<Spore> getSpores();

    boolean removeSpores(List<Spore> slist);

    void setNeighbors(List<Tecton> neighbors);

    void addThread(FungalThread f);

    void addSpore(Spore sp);
}
