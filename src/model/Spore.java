package model;

/**
 * A Spore osztály egy absztrakt osztály, amely egy általános spóra mechanizmust
 * definiál. Ez az osztály alapvető funkcionalitást és interfészt biztosít
 * különféle specifikus spóra implementációk számára, amelyek különféle hatásokat
 * alkalmazhatnak rovarokra.
 */
public abstract class Spore implements ISporeController {

    private FungalThread thread;


    /**
     * A Spore konstruktora, amely inicializálja a Spore egy példányát,
     * és beállítja a thread mezőt null értékre.
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
     * Visszaadja a jelenlegi fonalat, amely a Spore-hoz tartozik.
     *
     * @return A Spore-hoz tartozó FungalThread objektum.
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
