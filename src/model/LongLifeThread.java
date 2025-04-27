package model;

/**
 * A LongLifeThread osztály a FungalThread osztály leszármazottja.
 * Felelősségei azon megszakított fonalak élettartamának figyelése, amelyek a vágás után
 * még két körig aktívak maradhatnak, majd automatikusan megszűnnek.
 */
public class LongLifeThread extends FungalThread {

    public LongLifeThread() {
        super();
    }

    /**
     * Hozzáadja a megadott Tecton objektumot egy belső időzített megszüntetési listához.
     *
     * @param t A Tecton objektum, amelyet hozzá kell adni a megszüntetési listához.
     * @return Igaz értéket ad vissza, ha a művelet sikeres volt.
     */
    public boolean sendToDie(Tecton t) {
        timeToDie ttd = new timeToDie();
        ttd.setTecton(t);
        ttd.setTime(2);
        life.add(ttd);
        return true;
    }
}
