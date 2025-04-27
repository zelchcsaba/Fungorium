package controller;

import java.util.ArrayList;
import java.util.List;

import model.IFungalThreadController;
import model.IMushroomController;
import model.Mushroom;


/**
 * A FungusPlayer osztály a Player osztály leszármazottja, ami egy gombász
 * játékos megvalósítását biztosítja. A különböző feladatokkal kapcsolatos,
 * például gombák kezelésével, gombafonál vezérléssel és asszociációk kezelésével
 * kapcsolatos funkciókat határoz meg.
 */
public class FungusPlayer extends Player {

    private IFungalThreadController fungalThread;
    private boolean branchThread;
    private List<MushroomAssociation> mushrooms;


    /**
     * A FungusPlayer osztály konstruktora.
     * Inicializálja az alapértelmezett értékeket a példányosított objektumhoz.
     */
    public FungusPlayer() {
        fungalThread = null;
        branchThread = false;
        mushrooms = new ArrayList<>();
    }


    /**
     * Visszaadja a gombafonál-vezérléshez használt IFungalThreadController
     * típusú példányt, amely a gombafonál működésének kezelésére szolgál.
     *
     * @return az IFungalThreadController típusú példány, amely a gombafonalak kezelését végzi
     */
    public IFungalThreadController getThread() {
        return fungalThread;
    }


    /**
     * Beállítja a gombafonál vezérlőjét egy adott IFungalThreadController típusú példányra.
     *
     * @param f A gombafonál vezérlőt reprezentáló IFungalThreadController típusú objektum,
     * amely a gombafonal működését és eseményeit kezeli.
     */
    public void setThread(IFungalThreadController f) {
        fungalThread = f;
    }


    /**
     * Visszaadja a branchThread állapotát.
     *
     * @return true, ha a branchThread állapota igaz; különben false.
     */
    public boolean getBranchThread() {
        return branchThread;
    }


    /**
     * Beállítja a branchThread változót a megadott értékre.
     *
     * @param b A branchThread új értéke.
     */
    public void setBranchThread(boolean b) {
        branchThread = b;
    }


    /**
     * A játékoshoz társított gomba-asszociációk listáját adja vissza.
     *
     * @return A gombák asszociációinak listája.
     */
    public List<MushroomAssociation> getMushrooms() {
        return mushrooms;
    }


    /**
     * Egy új gombát (IMushroomController típusú példányt) hozzáad a gombák listájához
     * úgy, hogy egy gombaasszociációs objektumot (MushroomAssociation) hoz létre
     * és azt társítja a megadott gombával. Az így létrehozott asszociációt a
     * gombák listájához adja.
     *
     * @param m A gombát vezérlő objektum, amelyet a gombák listájához kell hozzáadni.
     */
    public void addMushroom(IMushroomController m) {
        MushroomAssociation mAssoc = new MushroomAssociation();
        mAssoc.setMushroom(m);
        mushrooms.add(mAssoc);
    }


    /**
     * Hozzáad egy MushroomAssociation objektumot a gombák listájához.
     *
     * @param ma A hozzáadandó MushroomAssociation objektum
     */
    public void addMushroomAssociation(MushroomAssociation ma) {
        mushrooms.add(ma);
    }


    /**
     * Visszatér a megadott indexű MushroomAssociation objektummal a gombák listájából.
     *
     * @param i Az index, amely a keresett MushroomAssociation helyzetét jelöli a listában.
     * @return A megadott indexű MushroomAssociation objektum a gombák listájából.
     */
    public MushroomAssociation getMushroomAt(int i) {
        return mushrooms.get(i);
    }


    /**
     * Egy adott index alapján eltávolít egy gomba-asszociációt a gombák listájából.
     *
     * @param i A lista indexe, ahol az eltávolítandó gomba-asszociáció található.
     */
    public void removeMushroomAt(int i) {
        mushrooms.remove(i);
    }


    /**
     * Eltávolít egy megadott gombát a gombák listájából.
     *
     * @param m A MushroomAssociation típusú objektum, amelyet el kell távolítani a listából.
     */
    public void removeMushroom(MushroomAssociation m) {
        mushrooms.remove(m);
    }


    /**
     * Eltávolítja a megadott gombát a jelenlegi asszociációk közül.
     *
     * @param m A gomba, amelyet el kívánunk távolítani.
     */
    public void rm(Mushroom m) {
        MushroomAssociation removable = null;
        for (MushroomAssociation mush : mushrooms) {
            if (mush.getMushroom() == m) {
                removable = mush;
            }
        }
        removeMushroom(removable);
    }


    /**
     * Visszaadja a jelenlegi gombák számát, amelyeket a gombász játékos kezel.
     *
     * @return a gombák száma
     */
    public int getMushroomSize() {
        return mushrooms.size();
    }

}
