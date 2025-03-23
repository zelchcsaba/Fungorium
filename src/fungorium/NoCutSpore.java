package fungorium;

/**
 * A NoCutSpore osztály a Spore absztrakt osztályból származik, és egy specifikus
 * spóra effektust valósít meg, amely megakadályozza, hogy a célzott rovar
 * vágási képességet használhasson.
 */
public class NoCutSpore extends Spore {

    /**
     * A NoCutSpore konstruktor, amely egy adott teszt objektumot fogad, és
     * inicializálja a spóra alapobjektumát ezzel a teszt paraméterrel.
     *
     * @param t A Tester objektum, amely a spóra működésének meghatározásához szükséges.
     */
    public NoCutSpore(Tester t) {
        super(t);
    }

    /**
     * A megadott rovar (Insect) állapotát (state) a NOCUT értékre állítja,
     * hogy a vágási képességét deaktiválja a spóra effektus hatására.
     *
     * @param i az a rovar, amelyre az effektust alkalmazni kell
     */
    public void applyEffect(Insect i) {
        i.setState(SporeEffect.NOCUT);
    }

}
