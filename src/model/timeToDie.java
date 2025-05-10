package model;

/**
 * A timeToDie osztály egy egyszerű adatstruktúra, amely egy Tecton típusú objektumot
 * és egy időértéket tárol. Ezen kívül getter és setter metódusokat biztosít ezen adatok
 * eléréséhez és frissítéséhez.
 */
public class timeToDie {
    Tecton t;
    int time;
    
    public timeToDie(){}

    public void setTecton(Tecton t){
        this.t = t;
    }

    public Tecton getTecton(){
        return t;
    }

    public void setTime(int time){
        this.time = time;
    }

    public int getTime(){
        return time;
    }

}
