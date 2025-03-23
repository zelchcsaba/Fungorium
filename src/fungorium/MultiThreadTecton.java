package fungorium;

import java.util.ArrayList;
import java.util.List;

public class MultiThreadTecton extends Tecton {

    private Mushroom mushroom;
    private List<FungalThread> threads;

    // konstruktor
    public MultiThreadTecton(Tester t) {
        super(t);
        mushroom = null;
        threads = new ArrayList<>();
    }

    // getter setter
    public void setMushroom(Mushroom mushroom) {
        this.mushroom = mushroom;
    }

    // visszaadja a tektonon található gombatestet
    public Mushroom getMushroom() {
        // meghívja a tester kiíró függvényét
        this.t.toCall("getMushroom"); // És itt iratjuk a testerrel.

        this.t.returnValue.clear();
        this.t.returnValue.add(mushroom);
        this.t.toReturn();

        return mushroom;
    }

    public void setThreads(List<FungalThread> threads) {
        this.threads = threads;
    }

    // visszaadja azokat a gombafonalakat, amelyek rajta vannak
    public List<FungalThread> getThreads() {
        t.toCall("getThreads");

        t.returnValue.clear();
        t.returnValue.addAll(threads);

        t.toReturn();

        return threads;
    }

    public boolean putThread(FungalThread f) {
        t.toCall("putThread");
        for (Tecton tecton : neighbors) {
            t.list.add(this);
            t.list.add(tecton);
            t.parameters.clear();
            if(tecton.getThreads().contains(f)){
                threads.add(f);
                t.returnValue.clear();
                t.returnValue.add(Boolean.TRUE);
                t.toReturn();
                return true;
            }
        }
        t.returnValue.clear();
        t.returnValue.add(Boolean.FALSE);
        t.toReturn();
        return false;
    }

    public void addThread(FungalThread f){
        threads.add(f);
    } 


    // lerak gomba ha meg nincs
    public boolean putMushroom(Mushroom m) {
        if (mushroom == null) {
            mushroom = m;
            return true;
        }
        return false;
    }

    //ha van rajta gombatest, akkor ezt töröljük
    public boolean removeMushroom() {
        this.t.toCall("removeMushroom");

        if (mushroom != null) {
            mushroom = null;

            this.t.returnValue.clear();
            this.t.returnValue.add(Boolean.TRUE);
            this.t.toReturn();

            return true;
        } else {

            this.t.returnValue.clear();
            this.t.returnValue.add(Boolean.TRUE);
            this.t.toReturn();
            
            return false;
        }
    }

    // a kapott fonalat kitörli a listájából
    public boolean removeThread(FungalThread f) {
        // meghívja a tester kiíró függvényét
        this.t.toCall("removeThread");

        this.t.returnValue.clear();
        this.t.returnValue.add(Boolean.TRUE);
        this.t.toReturn();

        threads.remove(f);
        return true;
    }

    public boolean putFirstMushroom() {
        this.t.toCall("putFirstMushroom");
        if (mushroom == null) {
            FungalThread ft = new FungalThread(t);
            this.t.toCreate(this, ft, "ft");
            Mushroom mush = new Mushroom(t);
            t.toCreate(this, mush, "mush");

            t.list.add(this);
            t.list.add(ft);
            t.parameters.clear();
            t.parameters.add(this);
            ft.addTecton(this);

            t.list.add(this);
            t.list.add(mush);
            t.parameters.clear();
            t.parameters.add(this);
            mush.setPosition(this);

            t.list.add(this);
            t.list.add(mush);
            t.parameters.clear();
            t.parameters.add(ft);
            mush.setThread(ft);

            this.t.returnValue.clear();
            this.t.returnValue.add(Boolean.TRUE);
            this.t.toReturn();
            return true;
        }
        this.t.returnValue.clear();
        this.t.returnValue.add(Boolean.FALSE);
        this.t.toReturn();
        return false;
    }

    public List<FungalThread> getThreadsWithoutCout() {
        return threads;
    }

    // ketté törik a tekton
    public boolean breakTecton() {
        // meghívja a tester kiíró függvényét
        this.t.toCall("breakTecton");

        if (mushroom == null) {
            // létrejön a két új tekton
            Tecton t6 = new MultiThreadTecton(t);
            Tecton t7 = new MultiThreadTecton(t);

            t.toCreate(this, t6, "t6");
            t.toCreate(this, t7, "t7");

            // ez lesz a töréspont a tektonon
            int centre = neighbors.size() / 2;

            // létrehozok egy listát, amelyben a t6 tekton szomszédai lesznek
            List<Tecton> neighborList = new ArrayList<>();
            for (int i = 0; i < centre; i++) {
                neighborList.add(neighbors.get(i));
            }
            neighborList.add(t7);

            this.t.list.add(this);
            this.t.list.add(t6);
            this.t.parameters.clear();
            this.t.parameters.addAll(neighborList);
            // beállítom a t6 szomszédait
            t6.addNeighbor(neighborList);
            neighborList.clear();

            // létrehozok egy listát, amelyben a t7 szomszédai lesznek
            neighborList.add(t6);
            for (int i = centre; i < neighbors.size(); i++) {
                neighborList.add(neighbors.get(i));
            }

            this.t.list.add(this);
            this.t.list.add(t7);
            this.t.parameters.clear();
            this.t.parameters.addAll(neighborList);
            // beállítom a t7 szomszédait
            t7.addNeighbor(neighborList);
            neighborList.clear();

            // a jelenlegi tekton szomszédait beállítom, hozzáadva szomszédsági listájukhoz
            // a megfelelő létrejött tektont
            // valamint kivéve a kettétötött tektont
            neighborList.add(t6);
            for (int i = 0; i < centre; i++) {

                this.t.list.add(this);
                this.t.list.add(neighbors.get(i));
                this.t.parameters.clear();
                this.t.parameters.addAll(neighborList);

                neighbors.get(i).addNeighbor(neighborList);

                this.t.list.add(this);
                this.t.list.add(neighbors.get(i));
                this.t.parameters.clear();
                this.t.parameters.add(this);

                neighbors.get(i).removeNeighbor(this);
            }

            neighborList.clear();

            neighborList.add(t7);
            for (int i = centre; i < neighbors.size(); i++) {

                this.t.list.add(this);
                this.t.list.add(neighbors.get(i));
                this.t.parameters.clear();
                this.t.parameters.addAll(neighborList);

                neighbors.get(i).addNeighbor(neighborList);

                this.t.list.add(this);
                this.t.list.add(neighbors.get(i));
                this.t.parameters.clear();
                this.t.parameters.add(this);

                neighbors.get(i).removeNeighbor(this);
            }

            this.t.list.add(this);
            this.t.list.add(t6);
            this.t.parameters.clear();
            this.t.parameters.add(i);
            // a tektonon levő bogarat ráhelyezem a t6-ra
            t6.setInsect(i);

            this.t.list.add(this);
            this.t.list.add(i);
            this.t.parameters.clear();
            this.t.parameters.add(t6);
            // beállítom a bogár pozícióját
            i.setPosition(t6);

            this.t.list.add(this);
            this.t.list.add(i);
            this.t.parameters.clear();
            this.t.parameters.add(t6);
            // kitörlöm a tektont a fonálról
            for (int i = 0; i < threads.size(); i++) {
                threads.get(i).removeTecton(this);
            }

            // ha keletkezett olyan fonálrész, amely a kettétörés során már nem kapcsolódik
            // gombatesthez ezt eltávolítom
            System.out.println("deleteUnnecessaryThreads");

            this.t.returnValue.clear();
            this.t.returnValue.add(Boolean.TRUE);
            this.t.toReturn();

            return true;
        } else {
            this.t.returnValue.clear();
            this.t.returnValue.add(Boolean.FALSE);
            this.t.toReturn();
            return false;
        }
    }

}
