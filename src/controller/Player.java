package controller;


/**
 * A Player osztály a játékosokat reprezentáló alapvető objektum.
 * Feladata a játékos által gyűjtött pontok követése és módosítása.
 */
public class Player {

    private int points;


    /**
     * Beállítja a játékos pontjainak értékét.
     *
     * @param points Az új pontszám, amelyet be kell állítani.
     */
    public void setPoints(int points) {
        this.points = points;
    }


    /**
     * Visszaadja a játékos által gyűjtött pontok számát.
     *
     * @return a játékos által gyűjtött pontok száma
     */
    public int getPoints() {
        return points;
    }


    /**
     * Hozzáad egy pontot a játékos jelenlegi pontszámához.
     * A pontszámot eggyel növeli.
     */
    public void addPoint() {
        points++;
    }

}
