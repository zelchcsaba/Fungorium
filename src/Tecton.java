public class Tecton {

    private List<Spore> spores;
    private List<Tecton> neighbors;
    private Insect i;

    public boolean putSpore(Spore sp, Tecton t) {}
    public boolean putMushroom(Mushroom m) {}
    public boolean putThread(FungalThread f) {}
    public boolean removeMushroom() {}
    public boolean removeThread(FungalThread f) {}
    public List<Tecton> getThreadSection(FungalThread f) {}
    public boolean breakTecton() {}
    public boolean putFirstMushroom() {}
    public boolean putFirstInsect() {}
    public boolean putInsect(Insect i, Tecton t) {}
    public boolean removeInsect() {}
    public boolean isNeighbor(Tecton t) {}
    public boolean addNeighbor(List<Tecton> tlist) {}
    public boolean putEvolvedSpore(Spore sp, Tecton t) {}
    public boolean removeSpores(List<Spore> slist) {}

}
