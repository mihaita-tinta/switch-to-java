package com.ing.switchtojava.carpoolingapi.rest.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CarPosition implements Iterator<Position> {

    private List<Position> positions;
    private int index;

    public CarPosition() {
        this.positions = new ArrayList<>();
        this.index = 0;
    }

    public List<Position> getPositions() {
        return positions;
    }

    public void setPositions(List<Position> positions) {
        this.positions = positions;
    }

    @Override
    public boolean hasNext() {
        if (index < positions.size()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Position next() {
        Position position = positions.get(index);
        index++;
        return position;
    }
}
