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

    // kontruktor
    public Mushroom(Tester t) {
        this.t = t;
        position = null;
        spores = new ArrayList<>();
        thread = null;
        state = MushroomState.UNEVOLVED;
        shootedSporesCount = 0;
    }

    // getter settter
    public void setPosition(Tecton position) {
        this.t.toCall("setPosition");
        this.t.returnValue.clear();
        this.t.toReturn();
        this.position = position;
    }

    public Tecton getPosition() {
        return position;
    }

    public void setThread(FungalThread thread) {
        this.t.toCall("setThread");
        this.t.returnValue.clear();
        this.t.toReturn();
        this.thread = thread;
    }

    public FungalThread getThread() {
        return thread;
    }

    public void setSpores(List<Spore> spores) {
        this.spores = spores;
    }

    public List<Spore> getSpores() {
        return spores;
    }

    public void setShootedSporesCount(int shootedSporesCount) {
        this.shootedSporesCount = shootedSporesCount;
    }

    public int getShootedSporesCount() {
        return shootedSporesCount;
    }

    public void setState(MushroomState s) {
        state = s;
    }

    public MushroomState getState() {
        return state;
    }

    //kilőjük a spórát tektonra
    public boolean shootSpore(Tecton t) {
        //meghívjuk a teszter kiíró függvényt
        this.t.toCall("shootSpore");
        //ha nincs spóránk, amit kilőjünk, akkor nem lőhetünk
        if (spores.isEmpty()) {

            this.t.returnValue.clear();
            this.t.returnValue.add(Boolean.FALSE);
            this.t.toReturn();

            return false;
        }
<<<<<<< HEAD

=======
        boolean returnV = false;
>>>>>>> 7628e1f02e884f419d71e6a95250e7d9b6ef65e5
        this.t.list.add(this);
        this.t.list.add(t);
        this.t.parameters.clear();
        this.t.parameters.add(spores.get(0));
        this.t.parameters.add(position);
<<<<<<< HEAD
        //megnézzük, ha sikerült-e lerakni a spórát
        if (!t.putSpore(spores.get(0), position)) {
            
=======
        if(state == MushroomState.UNEVOLVED){
            returnV = t.putSpore(spores.get(0), position);
        }
        else{
            returnV = t.putEvolvedSpore(spores.get(0), position);
        }
        if (!returnV) {
>>>>>>> 7628e1f02e884f419d71e6a95250e7d9b6ef65e5
            this.t.returnValue.clear();
            this.t.returnValue.add(Boolean.FALSE);
            this.t.toReturn();
            //ha nem, akkor false-val térünk vissza
            return false;

        } else {
            //ha sikerült a spórát lerkani
            //növeljük a shooted spores countot 1-el
            shootedSporesCount+=1;
            //ha a 10. spórát is kilőtte, akkor a gombatestnek meg kell halnia
            if(shootedSporesCount==10){

                this.t.list.add(this);
                this.t.list.add(position);
                this.t.parameters.clear();
                //töröljük a tektonról
                position.removeMushroom();

                this.t.list.add(this);
                this.t.list.add(thread);
                this.t.parameters.clear();
                //töröljük azon gombafonál részeket, amelyekhez nem kapcsolódik gombatest
                thread.deleteUnnecessaryThreads();
            }

            this.t.returnValue.clear();
            this.t.returnValue.add(Boolean.TRUE);
            this.t.toReturn();
            //true értékkel térünk vissza
            return true;
        }
        
    }

    // allapot beallitas
    public boolean evolve() {
        state = MushroomState.EVOLVED;
        return true;
    }

    // spora hozzaadas
    public boolean generateSpore(Spore sp) {
        spores.add(sp);
        return true;
    }

}
