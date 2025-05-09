package model;

/**
 * Az IInsectController interfész a rovarokkal (Insect) kapcsolatos alapvető funkciókat
 * határozza meg, mint például mozgás, állapotkezelés és osztódás.
 */
public interface IInsectController {

    boolean move(Tecton t);

    boolean cut(Tecton t);

    Insect divide();

    void setPosition(Tecton t);

    void setState(InsectState iState);

    InsectState getState();
}
