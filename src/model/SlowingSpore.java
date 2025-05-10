package model;

/**
 * A SlowingSpore osztály egy konkrét megvalósítása a Spore absztrakt osztálynak.
 * Ez az osztály egy lassító hatást alkalmaz az érintett rovarokra.
 */
public class SlowingSpore extends Spore {

    /**
     * Létrehoz egy új SlowingSpore példányt, amely lassító hatást fejt ki rovarokra.
     */
    public SlowingSpore() {
        super();
    }


    /**
     * Alkalmaz egy lassító hatást a megadott rovarra.
     *
     * @param i Az a rovar, amelynek az állapotát a SporeEffect.SLOWED értékre állítja.
     */
    public void applyEffect(Insect i) {
        i.setState(InsectState.SLOWED);
    }

}
