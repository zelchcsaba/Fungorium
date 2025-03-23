package fungorium;

import java.util.ArrayList;
import java.util.List;

public class SingleThreadTecton extends Tecton {

    private Mushroom mushroom;
    private FungalThread thread;

    public SingleThreadTecton(Tester t) {
        super(t);
        mushroom = null;
        thread = null;
    }

    public void setThreads(List<FungalThread> list){
        t.toCall("setThreads");
        t.list.add(this);
        t.list.add(list);
        t.parameters.clear();
        t.parameters.add(this);

        thread = list.getFirst();

        t.returnValue.clear();
        t.returnValue.add(Boolean.TRUE);
        t.toReturn();
    }

    // visszaadja azokat a gombafonalakat, amelyek rajta vannak
    public List<FungalThread> getThreads() {

        t.toCall("getThreads");
        // berakom egy listába a fonalat
        ArrayList<FungalThread> list = new ArrayList<>();
        if (thread == null) {
            t.returnValue.clear();
            t.toReturn();
            return null;
        } else {
            list.add(thread);
        }

        t.returnValue.clear();
        t.returnValue.addAll(list);
        t.toReturn();
        return list;
    }

    public void setMushroom(Mushroom mushroom) {
        this.mushroom = mushroom;
    }

    // visszaadja a tektonon található gombatestet
    public Mushroom getMushroom() {
        // meghívja a tester kiíró függvényét
        this.t.toCall("getMushroom");

        this.t.returnValue.clear();
        this.t.returnValue.add(mushroom);
        this.t.toReturn();

        return mushroom;
    }

    // ha nincs meg mushroom rarakjuk
    public boolean putMushroom(Mushroom m) {
        t.toCall("putMushroom");
        t.list.add(this);
        t.list.add(m);
        t.parameters.clear();
        t.parameters.add(this);

        if(mushroom == null) {
            mushroom = m;
            t.returnValue.clear();
            t.returnValue.add(Boolean.TRUE);
            t.toReturn();
            return true;
        }
        return false;
    }

    // ha van rajta mushroom toroljuk
    public boolean removeMushroom() {

        t.toCall("removeMushroom");

        if (mushroom != null) {
            mushroom = null;

            t.returnValue.clear();
            t.returnValue.add(Boolean.TRUE);
            t.toReturn();

            return true;
        } else {

            t.returnValue.clear();
            t.returnValue.add(Boolean.FALSE);
            t.toReturn();

            return false;
        }
    }

    // ha nincs egy fonal se rajta, akkor lehet fonalat helyezni ra
    public boolean putThread(FungalThread f) {
        t.toCall("putThread");
        if (thread == null && !neighbors.isEmpty()) {
            for (Tecton tecton : neighbors) {
                List<FungalThread> threads = tecton.getThreadsWithoutCout();
                if (threads != null) { // Csak akkor iterálj, ha nem null
                    for (FungalThread fungals : threads) {
                        if (fungals != null && fungals.equals(f)) {
                            thread = f;
                            t.returnValue.clear();
                            t.returnValue.add(Boolean.TRUE);
                            t.toReturn();
                            return true;
                        }
                    }
                }
            }
            thread = f;
            t.returnValue.clear();
            t.returnValue.add(Boolean.FALSE);
            t.toReturn();
            return false;
        } else {
            t.returnValue.clear();
            t.returnValue.add(Boolean.FALSE);
            t.toReturn();
            return false;
        }

    }

    // ha a kapott thread megegyezik az eltarolt threaddel, akkor töröljük
    public boolean removeThread(FungalThread f) {
        // meghívja a tester kiíró függvényét
        this.t.toCall("removeThread");

        this.t.returnValue.clear();
        this.t.returnValue.add(Boolean.TRUE);
        this.t.toReturn();

        thread = null;
        return true;
    }

    // To do
    public boolean putFirstMushroom() {
        if (mushroom == null) {
            return true;
        } else {
            return false;
        }
    }

    // getter, csak nem írat ki
    public List<FungalThread> getThreadsWithoutCout() {
        ArrayList<FungalThread> list = new ArrayList<>();
        if (thread == null) {
            return null;
        } else {
            list.add(thread);
        }
        return list;
    }

    // ketté törik a tekton
    public boolean breakTecton() {
        // meghívja a tester kiíró függvényét
        this.t.toCall("breakTecton");

        if (mushroom == null) {

            // létrejön a két új tekton
            Tecton t6 = new SingleThreadTecton(t);
            Tecton t7 = new SingleThreadTecton(t);

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
            thread.removeTecton(this);

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
