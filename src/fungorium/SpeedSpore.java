package fungorium;

public class SpeedSpore extends Spore {

    public SpeedSpore(Tester t) {
        super(t);
    }

    public void applyEffect(Insect i) {
        i.setState(SporeEffect.SPEEDBOOST);
    }

}
