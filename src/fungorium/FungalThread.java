package fungorium;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class FungalThread {

    Tester t;
    private List<Tecton> tectons;

    public FungalThread(Tester t) {
        this.t = t;
        tectons = new ArrayList<>();
    }

    public void setTectons(List<Tecton> tectons) {
        this.tectons = tectons;
    }

    public boolean branchThread(Tecton t) {
        this.t.toCall("branchThread"); // És itt iratjuk a testerrel.
        // Nincs megállás
        this.t.list.add(this);
        this.t.list.add(t);
        this.t.parameters.clear();
        this.t.parameters.add(this);

        if (!t.putThread(this)) {
            this.t.returnValue.clear();
            this.t.returnValue.add(Boolean.FALSE);
            this.t.toReturn();
            return false;
        } else {
            tectons.add(t);
            this.t.returnValue.clear();
            this.t.returnValue.add(Boolean.TRUE);
            this.t.toReturn();
            return true;
        }
    }

    public boolean addTecton(Tecton t) {
        this.t.toCall("addTecton");
        this.t.returnValue.clear();
        this.t.returnValue.add(Boolean.TRUE);
        this.t.toReturn();
        return tectons.add(t);
    }

    // keveszi a tectons többől a kapott tektont
    public boolean removeTecton(Tecton t) {
        this.t.toCall("removeTecton");

        this.t.returnValue.clear();
        this.t.returnValue.add(Boolean.TRUE);
        this.t.toReturn();

        tectons.remove(t);
        return true;
    }


    public boolean growMushroom(Tecton t) {
        this.t.toCall("growMushroom");

        this.t.list.add(this);
        this.t.list.add(t);
        this.t.parameters.clear();

        t.getSpores();

        boolean allSporesUseThisThread = true;
        for (Spore s : t.spores) {
            if(s.getThread() != this) {
                allSporesUseThisThread = false;
                break;
            }
        }



        if (!allSporesUseThisThread) {
            this.t.returnValue.clear();
            this.t.returnValue.add(Boolean.FALSE);
            this.t.toReturn();
            return false;
        }

        Mushroom m = (Mushroom) this.t.getObjectByValue("m");

        this.t.list.add(this);
        this.t.list.add(m);
        this.t.parameters.clear();
        this.t.parameters.add(t);

        m.setPosition(t);
        m.setThread(this);

        List<FungalThread> thisThread = new ArrayList<>();
        thisThread.add(this);
        t.setThreads(thisThread);

        t.putMushroom(m);

        this.t.returnValue.clear();
        this.t.returnValue.add(Boolean.TRUE);
        this.t.toReturn();

        return true;
    }

    public void deleteUnnecessaryThreads() {}
    // TO DO majd, most nem kell
    public boolean growMushroom(Tecton t) {
        return true;
    }

    // olyan fonálrészek eltávolítása, amelyek nem kapcsolódnak ugyanolyan fajból
    // származó gombatesthez
    public void deleteUnnecessaryThreads() {
        // meghívja a tester kiíró függvényét
        this.t.toCall("deleteUnnecessaryThreads"); // És itt iratjuk a testerrel.

        // létrehozok két segéd listát
        List<Tecton> fungalList = new ArrayList<>();
        List<Tecton> connectedTectons = new ArrayList<>();

        // végigmegyek a tectons listán, megnézem melyik tektonon van gomba, ezt
        // elmentem a fungalList listába
        for (int i = 0; i < tectons.size(); i++) {

            this.t.list.add(this);
            this.t.list.add(tectons.get(i));
            this.t.parameters.clear();

            // megnézem, ha van-e rajta gombatest
            if (tectons.get(i).getMushroom() != null) {
                fungalList.add(tectons.get(i));
            }
        }

        // megkeresem azokat a fonálrészeket, amelyek kapcsolatban vannak ugyanolyan
        // fajból származó gombatesttel
        while (!fungalList.isEmpty()) {
            connectedTectons.add(fungalList.get(0));

            this.t.list.add(this);
            this.t.list.add(fungalList.get(0));
            this.t.parameters.clear();
            this.t.parameters.add(this);

            // megnézem, hogy tectons.get(0) szomszédai közül melyeken van elágazva a fonál
            List<Tecton> list = fungalList.get(0).getThreadSection(this);
            for (int i = 0; i < list.size(); i++) {
                if (!connectedTectons.contains(list.get(i))) {
                    fungalList.add(list.get(i));
                }
            }
            fungalList.remove(0);
        }

        // Végigmegyek a tectons listán, majd azokról a tectonokról, amelyeken
        // olyanfonálrészek vannak,
        // amelyek nincsenek kapcsolatban ugyanolyan fajból származó gombatesttel
        // leszedjük a fonalat
        for (int i = 0; i < tectons.size(); i++) {
            if (!connectedTectons.contains(tectons.get(i))) {

                this.t.list.add(this);
                this.t.list.add(tectons.get(i));
                this.t.parameters.clear();
                this.t.parameters.add(this);
                // leveszem a tektonokról a fonalat
                tectons.get(i).removeThread(this);
            }
        }

        // visszatér a függvény
        this.t.returnValue.clear();
        this.t.toReturn();

    }

    public List<Tecton> getTectons() {
        return tectons;
    }

}
