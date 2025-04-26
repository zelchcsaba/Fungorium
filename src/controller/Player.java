package controller;

public  class Player {
    private int points;

    public void setPoints(int points){
        this.points = points;
    }

    public int getPoints(){
        return points;
    }

    public void addPoint(){
        points++;
    }
}
