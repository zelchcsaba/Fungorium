package fungorium;

import java.awt.*;
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
    
    public boolean branchThread(Tecton t) {
        this.t.toCall("branchThread"); // És itt iratjuk a testerrel.
        //Nincs megállás
        this.t.list.add(this);
        this.t.list.add(t);
        this.t.parameters.clear();
        this.t.parameters.add(this);

        t.putThread(this);

        this.t.returnValue.clear();
        this.t.returnValue.add(Boolean.TRUE);
        this.t.toReturn();
        
        return true;
    }

    public boolean addTecton(Tecton t) {
        this.t.toCall("addTecton");
        this.t.returnValue.clear();
        this.t.returnValue.add(Boolean.TRUE);
        this.t.toReturn();
        return tectons.add(t);
    }
    
    public boolean removeTecton(Tecton t) {
        return tectons.remove(t);
    }

    //TO DO majd, most nem kell
    public boolean growMushroom(Tecton t) {return true;}
    
    public void deleteUnnecessaryThreads() {
        
        this.t.toCall("deleteUnnecessaryThreads"); // És itt iratjuk a testerrel.


        List<Tecton> fungalList = new ArrayList<>();
        List<Tecton> connectedTectons = new ArrayList<>();

        for(int i=0; i<tectons.size(); i++){

            this.t.list.add(this);
            this.t.list.add(tectons.get(i));
            this.t.parameters.clear();

            if(tectons.get(i).getMushroom() != null){
                fungalList.add(tectons.get(i));
            }
        }

            while(!fungalList.isEmpty()){
                connectedTectons.add(fungalList.get(0));

                this.t.list.add(this);
                this.t.list.add(tectons.get(0));
                this.t.parameters.clear();
                this.t.parameters.add(this);

                fungalList.addAll(tectons.get(0).getThreadSection(this));
                fungalList.remove(0);
            }

            for(int i=0; i<tectons.size(); i++){
                if(!tectons.contains(tectons.get(i))){

                    this.t.list.add(this);
                    this.t.list.add(tectons.get(i));
                    this.t.parameters.clear();
                    this.t.parameters.add(this);

                    tectons.get(i).removeThread(this);
                }
            }

        this.t.returnValue.clear();
        this.t.toReturn();

    }

}
