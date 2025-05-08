package view;

import java.util.Random;


/**
 * A View osztály az IView interfészt valósítja meg, és a felhasználói felület
 * megjelenítésének alapvető feladatival foglalkozik.
 */
public class View implements IView {

    Random rand = new Random();


    /**
     * Egy véletlenszerű számot generál 0 és 999 közötti tartományban.
     *
     * @return A generált véletlenszerű egész szám.
     */
    public int randomize() {
        return rand.nextInt(1000);
    }
}
