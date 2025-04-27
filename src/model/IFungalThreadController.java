package model;

import java.util.List;

/**
 * Az IFungalThreadController interfész a gombafonalak működéséhez szükséges alapvető metódusok
 * deklarációját tartalmazza. Olyan mechanizmusokat biztosít, amelyek egy gombafonal részlegei,
 * műveletei és eseményei kezeléséhez kapcsolódnak, például az ágazások létrehozása,
 * gombatermesztés, felesleges szálak eltávolítása, valamint időalapú folyamatok kezelése.
 */
public interface IFungalThreadController {

    public abstract void deleteUnnecessaryThreads();

    public abstract boolean branchThread(Tecton t);

    public abstract boolean growMushroom(Tecton t, Mushroom m);

    public abstract void timeCheck();

    public abstract boolean eatInsect(Insect i);

    public abstract void setTectons(List<Tecton> tlist);

}
