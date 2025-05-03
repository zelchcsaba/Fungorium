package model;

import java.util.List;

/**
 * Az IFungalThreadController interfész a gombafonalak működéséhez szükséges alapvető metódusok
 * deklarációját tartalmazza. Olyan mechanizmusokat biztosít, amelyek egy gombafonal részlegei,
 * műveletei és eseményei kezeléséhez kapcsolódnak, például az ágazások létrehozása,
 * gombatermesztés, felesleges szálak eltávolítása, valamint időalapú folyamatok kezelése.
 */
public interface IFungalThreadController {

    void deleteUnnecessaryThreads();

    boolean branchThread(Tecton t);

    boolean growMushroom(Tecton t, Mushroom m);

    void timeCheck();

    boolean eatInsect(Insect i);

    void setTectons(List<Tecton> tlist);

}
