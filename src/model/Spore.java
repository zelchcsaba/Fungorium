package model;

/**
 * A Spore osztály egy absztrakt osztály, amely egy általános spóra mechanizmust
 * definiál. Ez az osztály alapvető funkcionalitást és interfészt biztosít
 * különféle specifikus spóra implementációk számára, amelyek különféle hatásokat
 * alkalmazhatnak rovarokra.
 */
public abstract class Spore implements ISporeController{

    private FungalThread thread;

    /**
     * A Spore konstruktora, amely inicializálja a Spore egy példányát a megadott tesztelővel.
     *
     * @param t A Tester példány, amely a spóra különböző tesztelési funkcióit biztosítja.
     */
    public Spore() {
        thread = null;
    }


    /**
     * Beállítja a Spore példányhoz tartozó FungalThread objektumot.
     *
     * @param thread A beállítandó FungalThread példány.
     */
    public void setThread(FungalThread thread) {
        this.thread = thread;
    }


    /**
     * Visszaadja a jelenlegi szálat, amely a Spore-hoz tartozik.
     *
     * @return A Spore-hoz tartozó FungalThread objektum, amely reprezentálja a működési szálat, vagy null, ha nincs szál inicializálva.
     */
    public FungalThread getThread() {
        return thread;
    }


    /**
     * Absztrakt metódus, amely egy adott hatást alkalmaz egy rovarra (Insect).
     *
     * @param i A rovar (Insect), amelyre a hatást alkalmazni kell.
     */
    public abstract void applyEffect(Insect i);

}
