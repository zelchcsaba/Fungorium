package model;

/**
 * A ShortLifeThread osztály a FungalThread osztály leszármazottja.
 * Felelősségei azon megszakított fonalak élettartamának figyelése, amelyek a vágás után
 * még egy körig aktívak maradhatnak, majd automatikusan megszűnnek.
 */
public class ShortLifeThread extends FungalThread {

    /**
     * Konstruktor, amely inicializálja a ShortLifeThread objektumot.
     */
    public ShortLifeThread() {
        super();
    }


    /**
     * A megadott Tecton objektumot egy rövid élettartamra jelöli ki, amely után automatikusan
     * megszűnik.
     *
     * @param t A Tecton objektum, amelyet rövid élettartamra kell állítani.
     * @return Igaz értéket ad vissza, ha a művelet sikeresen végrehajtásra került.
     */
    public boolean sendToDie(Tecton t) {
        timeToDie ttd = new timeToDie();
        ttd.setTecton(t);
        ttd.setTime(1);
        life.add(ttd);
        return true;
    }

}
