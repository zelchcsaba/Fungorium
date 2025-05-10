package model;

/**
 * A ParalysingSpore osztály egy konkrét megvalósítása a Spore absztrakt osztálynak.
 * Ez az osztály reprezentál egy speciális spórát, amely alkalmazásakor
 * megbénítja a célul választott rovar állapotát.
 */
public class ParalysingSpore extends Spore {

    /**
     * Konstruktor, amely létrehozza a ParalysingSpore objektumot.
     */
    public ParalysingSpore() {
        super();
    }


    /**
     * Alkalmazza a spóra hatását egy adott rovaron, mely ebben az esetben
     * megbénítja a rovar mozgását az állapotának megfelelő módosításával.
     *
     * @param i Az a rovar (Insect objektum), amelyre a spóra hatását alkalmazni kell.
     */
    public void applyEffect(Insect i) {
        i.setState(InsectState.PARALYZED);
    }

}
