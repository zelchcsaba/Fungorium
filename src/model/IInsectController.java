package model;

public interface IInsectController {
    public abstract boolean move(Tecton t);
    public abstract boolean cut(Tecton t);
    public abstract Insect divide();
    public abstract void setPosition(Tecton t);
    public abstract void setState(InsectState iState);
    public abstract InsectState getState();
}
