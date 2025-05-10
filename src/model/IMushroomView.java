package model;

public interface IMushroomView {
    public Tecton getPosition();

    public FungalThread getThread();

    //public List<Spore> getSpores();

    //public int getShootedSporesCount();

    public MushroomState getState();
}
