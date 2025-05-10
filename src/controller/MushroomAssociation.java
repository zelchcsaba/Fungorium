package controller;

import model.IMushroomController;


/**
 * A MushroomAssociation osztály a gombákhoz kapcsolódó asszociációkat reprezentálja.
 * Ez magában foglalja a gombát (IMushroomController),
 * valamint annak életkorát. A célja, hogy kezelje a gombákhoz tartozó információkat
 * és biztosítsa a gomba egyes viselkedésmodelljeinek nyilvántartását.
 */
public class MushroomAssociation {

    private IMushroomController mushroom;
    private int age;


    /**
     * Alapértelmezett konstruktor a MushroomAssociation osztály számára.
     * Inicializálja a MushroomAssociation objektumot az alapértékekkel:
     * a gombát (mushroom) null értékre állítja és az életkort (age) 0-ra.
     */
    public MushroomAssociation() {
        mushroom = null;
        age = 0;
    }


    /**
     * Visszaadja a gombát vezérlő IMushroomController példányt, amely felelős
     * a gombákhoz kapcsolódó műveletek és viselkedésmodellek kezeléséért.
     *
     * @return az IMushroomController interfészt megvalósító objektum,
     *         amely a gombát vezérli, vagy null, ha nincs inicializálva.
     */
    public IMushroomController getMushroom() {
        return mushroom;
    }


    /**
     * A gomba vezérlő objektum beállítására szolgáló metódus.
     *
     * @param m a beállítandó gomba vezérlő objektum
     */
    public void setMushroom(IMushroomController m) {
        mushroom = m;
    }


    /**
     * Visszaadja a gomba életkorát.
     *
     * @return a gomba életkora
     */
    public int getAge() {
        return age;
    }


    /**
     * Beállítja az életkort.
     *
     * @param a Az új életkor értéke.
     */
    public void setAge(int a) {
        age = a;
    }

}
