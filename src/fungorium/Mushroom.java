package fungorium;

import java.util.ArrayList;
import java.util.List;

public class Mushroom {

    Tester t;
    private Tecton position;
    private List<Spore> spores;
    private FungalThread thread;
    private MushroomState state;
    private int shootedSporesCount;

    public Mushroom(Tester t){
        this.t=t;
        position = null;
        spores=new ArrayList<>();
        thread = null;
        state = null;
        shootedSporesCount = 0;
    }

    public void setPosition(Tecton position){
        this.position=position;
    }

    public void setThread(FungalThread thread){
        this.thread=thread;
    }

    public void setSpores(List<Spore> spores){
        this.spores=spores;
    }

    public void setShootedSporesCount(int shootedSporesCount){
        this.shootedSporesCount=shootedSporesCount;
    }

    public boolean shootSpore(Tecton t) {
        return true;
    }
    public boolean evolve() {
        return true;
    }
    public boolean generateSpore(Spore sp) {
        return true;
    }

}
