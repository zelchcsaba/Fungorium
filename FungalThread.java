import java.util.ArrayList;

public class FungalThread {

    private ArrayList<Tecton> tectons;

    public FungalThread(){
        tectons = new ArrayList<>();
    }

    public void deleteUnnecessaryThreads() {

        for(int i=0; i<tectons.size();i++){

            ArrayList<Tecton> FungalSection = new ArrayList<>();

            if(tectons.get(i).getMushroom() != null){

                FungalSection.add(tectons.get(i));
                ArrayList<Tecton> NextCheck = new ArrayList<>();
                NextCheck.add(tectons.get(i));

                boolean changed = true;
                while (changed == true){
                    int sectionSize = FungalSection.size();
                    while (!NextCheck.isEmpty()){
                        Tecton checkTecton = NextCheck.get(0);
                        NextCheck.remove(0);
                        FungalSection.addAll(checkTecton.getThreadSection(this));
                    }
                }
            }
        }
    }
    public boolean branchThread(Tecton t) {}
    public boolean addTecton(Tecton t) {}
    public boolean removeTecton(Tecton t) {}
    public boolean growMushroom(Tecton t) {}

}
