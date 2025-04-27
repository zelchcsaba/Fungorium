package model;

/**
 * A NoCutSpore osztály a Spore absztrakt osztályból származik, és egy specifikus
 * spóra effektust valósít meg, amely megakadályozza, hogy a célzott rovar
 * vágási képességet használhasson.
 */
public class NoCutSpore extends Spore {

    /**
     * Alapértelmezett konstruktor, amely létrehozza a NoCutSpore objektumot.
     */
    public NoCutSpore() {
        super();
    }


    /**
     * A megadott rovar (Insect) állapotát (state) a NOCUT értékre állítja,
     * hogy a vágási képességét deaktiválja a spóra effektus hatására.
     *
     * @param i az a rovar, amelyre az effektust alkalmazni kell
     */
    public void applyEffect(Insect i) {
        i.setState(InsectState.NOCUT);
    }

}
