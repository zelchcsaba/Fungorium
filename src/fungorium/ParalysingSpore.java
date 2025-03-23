package fungorium;

/**
 * A ParalysingSpore osztály egy konkrét megvalósítása a Spore absztrakt osztálynak.
 * Ez az osztály reprezentál egy speciális spórát, amely alkalmazásakor
 * megbénítja a célul választott rovar állapotát.
 */
public class ParalysingSpore extends Spore {

    /**
     * A ParalysingSpore osztály konstruktora, amely inicializálja a spóra objektumot
     * egy tesztelővel.
     *
     * @param t A Tester példány, amely biztosítja a Spore osztály megfelelő működését
     */
    public ParalysingSpore(Tester t) {
        super(t);
    }

    /**
     * Alkalmazza a spóra hatását egy adott rovaron, mely ebben az esetben
     * megbénítja a rovar mozgását az állapotának megfelelő módosításával.
     *
     * @param i Az a rovar (Insect objektum), amelyre a spóra hatását alkalmazni kell.
     */
    public void applyEffect(Insect i) {
        i.setState(SporeEffect.PARALYZED);
    }

}
