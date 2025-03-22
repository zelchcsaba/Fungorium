package fungorium;

public class ParalysingSpore extends Spore{

    public ParalysingSpore(Tester t){
        super(t);
    }
    
    public void applyEffect(Insect i) {
        i.setState(SporeEffect.PARALYZED);
    }

}
