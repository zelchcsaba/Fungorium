package fungorium;

public abstract class Spore {

    Tester t;
    private FungalThread thread;

    public Spore(Tester t) {
        this.t = t;
        thread = null;
    }

    public void setThread(FungalThread thread) {
        this.thread = thread;
    }

    public FungalThread getThread() {
        return thread;
    }

    public abstract void applyEffect(Insect i);

}
