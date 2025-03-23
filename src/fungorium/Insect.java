package fungorium;
import java.util.List;
import java.util.ArrayList;

public class Insect {

    Tester t;
    private Tecton position;
    private SporeEffect state;

    // konstruktor
    public Insect(Tester t) {
        this.t = t;
        position = null;
        state = SporeEffect.NORMAL;
    }

    // beállítja a bogárnak a position-ját
    public void setPosition(Tecton t) {
        position = t;
        this.t.toCall("setPosition");
        this.t.returnValue.clear();
        this.t.toReturn();
    }

    public Tecton getPosition() {
        return position;
    }

    public void setState(SporeEffect s) {
        this.t.toCall("setState");
        state = s;
        this.t.returnValue.clear();
        this.t.toReturn();
    }

    public SporeEffect getState() {
        return state;
    }

    // To do
    public boolean move(Tecton t) {
        this.t.toCall("move");

        this.t.list.add(this);
        this.t.list.add(t);
        this.t.parameters.clear();
        this.t.parameters.add(this);
        this.t.parameters.add(position);
        if(t.putInsect(this,position)){
            this.t.list.add(this);
            this.t.list.add(t);
            List<Spore> tSpores = t.getSpores();

            this.t.list.add(this);
            this.t.list.add(tSpores.getFirst());
            tSpores.getFirst().applyEffect(this);

            this.t.returnValue.clear();
            this.t.returnValue.add(Boolean.TRUE);
            this.t.toReturn();
            return true;
        }
        else{
            this.t.returnValue.clear();
            this.t.returnValue.add(Boolean.FALSE);
            this.t.toReturn();
            return false;
        }
    }

    //elvágja a fonalakat a kijelölt tektonon
    public boolean cut(Tecton t) {
        //tester kiíró függvénye
        this.t.toCall("cut");

        //leellenőrizzük, hogy a kapott tekton az szomszédos-e a position tektonnal

        this.t.list.add(this);
        this.t.list.add(t);
        this.t.parameters.clear();
        this.t.parameters.add(position);

        if(t.isNeighbor(position)){
            //létrehozunk két listát, az egyikben a t, a másikban a position tektonon levő fonalakat tároljuk
            List<FungalThread> list1;
            List<FungalThread> list2;

            //lekérjük a fonalakat
            this.t.list.add(this);
            this.t.list.add(t);
            this.t.parameters.clear();

            list1 = t.getThreads();

            this.t.list.add(this);
            this.t.list.add(position);
            this.t.parameters.clear();

            list2 = position.getThreads();

            //megnézzük, hogy a list2-ben mely elemek szerepelnek a list1-ből
            for(int i=0; i<list1.size();i++){

                //ha valamely elem a list1-ből szerepel a list2-ben is
                if(list2.contains(list1.get(i))){
                    //kivesszük a fonál tekton listájából a t tektont
                    this.t.list.add(this);
                    this.t.list.add(list1.get(i));
                    this.t.parameters.clear();
                    this.t.parameters.add(t);

                    list1.get(i).removeTecton(t);
                    //levesszük a t tektonról a fonalat
                    this.t.list.add(this);
                    this.t.list.add(t);
                    this.t.parameters.clear();
                    this.t.parameters.add(list1.get(i));

                    t.removeThread(list1.get(i));
                    //töröljük azon fonálrészeket, amelyek nem kapcsolódnak ugyanolyan fajból származó gombatesthez
                    this.t.list.add(this);
                    this.t.list.add(list1.get(i));
                    this.t.parameters.clear();

                    list1.get(i).deleteUnnecessaryThreads();
                }
            }
            //ha sikerült fonalat vágni true értékkel tér vissza
            this.t.returnValue.clear();
            this.t.returnValue.add(Boolean.TRUE);
            this.t.toReturn();

            return true;
        }else{
            //ha nem akkor false-val
            this.t.returnValue.clear();
            this.t.returnValue.add(Boolean.FALSE);
            this.t.toReturn();
            return false;
        }
              
    }

}
