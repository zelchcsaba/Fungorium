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

    public List<Spore> getSpores();

    public boolean removeSpores(List<Spore> slist);

    public void setNeighbors(List<Tecton> neighbors);

    public void addThread(FungalThread f);

    public void addSpore(Spore sp);
}
