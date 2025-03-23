package fungorium;

public class SpeedSpore extends Spore {

    public SpeedSpore(Tester t) {
        super(t);
    }

    public void applyEffect(Insect i) {
        this.t.toCall("applyEffect");

        this.t.list.add(this);
        this.t.list.add(i);
        i.setState(SporeEffect.SPEEDBOOST);

        this.t.returnValue.clear();
        this.t.toReturn();
    }


}
