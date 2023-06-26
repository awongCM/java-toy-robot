package com.andywong.components;

public class Location {
    private Integer x;
    private Integer y;

    public Location(Integer x, Integer y){
        this.x = x;
        this.y = y;
    }

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    @Override
    protected Location clone() throws CloneNotSupportedException {
        return new Location(x, y);
    }

    @Override
    public boolean equals(Object object) {
        if(!(object instanceof Location)){
            return false;
        }

        Location comparedTo = (Location) object;
        return x == comparedTo.x && y == comparedTo.y;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

}
