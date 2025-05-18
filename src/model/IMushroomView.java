package model;


/**
 * Az IMushroomView interfész a gomba nézeti reprezentációjára szolgál,
 * amely lehetővé teszi a gomba állapotának és pozíciójának lekérdezését,
 * valamint a hozzá társított fonal és állapot kezelését.
 */
public interface IMushroomView {
    Tecton getPosition();

    FungalThread getThread();

    MushroomState getState();
}
