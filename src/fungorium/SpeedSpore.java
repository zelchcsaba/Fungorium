package fungorium;

/**
 * A SpeedSpore osztály a Spore absztrakt osztály leszármazottja.
 * Ez az osztály egy hatást alkalmaz egy rovarra, amely sebességnövekedést idéz elő.
 */
public class SpeedSpore extends Spore {

    /**
     * A SpeedSpore konstruktor egy új SpeedSpore objektumot hoz létre,
     * amely egy adott Tester példányhoz van csatolva.
     *
     * @param t Az a Tester objektum, amelyet a SpeedSpore fog használni.
     */
    public SpeedSpore(Tester t) {
        super(t);
    }

    /**
     * A megadott rovarra (Insect) egy sebességnövelő (SPEEDBOOST) hatást alkalmaz.
     * A hatás a rovar állapotát (state) módosítja a SporeEffect enum SPEEDBOOST értékére.
     *
     * @param i A rovar (Insect), amelyre a hatást alkalmazni kell.
     */
    public void applyEffect(Insect i) {
        this.t.toCall("applyEffect");

        this.t.list.add(this);
        this.t.list.add(i);
        i.setState(SporeEffect.SPEEDBOOST);

        this.t.returnValue.clear();
        this.t.toReturn();
    }


}
