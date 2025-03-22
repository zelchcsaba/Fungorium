import java.util.ArrayList;
import java.util.List;

public class FungalThread {

    Tester t;
    private List<Tecton> tectons;

    public FungalThread(Tester t){
        this.t=t;
        tectons=new ArrayList<>();
    }

    public void setTectons(List<Tecton> tectons){
        this.tectons=tectons;
    }
    
    public void deleteUnnecessaryThreads() {}
    public boolean branchThread(Tecton t) {
        this.t.toCall("branchThread"); // És itt iratjuk a testerrel.
        //Nincs megállás
        this.t.caller = this;
        this.t.called = t;
        this.t.parameters.clear();
        this.t.parameters.add(this);
        t.putThread(this);

        return true;
    }
    public boolean addTecton(Tecton t) {
        tectons.add(t);
        return true;
    }
    public boolean removeTecton(Tecton t) {
        tectons.remove(t);
        return true;
    }
    public boolean growMushroom(Tecton t) {
        return true;
    }

}
