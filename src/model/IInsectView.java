package model;


/** * Az IInsectView interfész egy rovar nézetét reprezentálja, amely
 * tartalmazza a rovar pozícióját és állapotát lekérdező metódusokat.
 */
public interface IInsectView {
    Tecton getPosition();

    InsectState getState();
}
