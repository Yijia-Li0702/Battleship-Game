package edu.duke.yl730.battleship;

public class V1ShipFactory implements AbstractShipFactory<Character>{

    @Override
    public Ship<Character> makeSubmarine(Placement where) {
        // TODO Auto-generated method stub
        return createShip(where, 1, 2, 's', "Submarine");

    }

    @Override
    public Ship<Character> makeBattleship(Placement where) {
        // TODO Auto-generated method stub
        return createShip(where, 1, 4, 'b', "Battleship");

    }

    @Override
    public Ship<Character> makeCarrier(Placement where) {
        // TODO Auto-generated method stub
        return createShip(where, 1, 6, 'c', "Carrier");
    }

    @Override
    public Ship<Character> makeDestroyer(Placement where) {
        // TODO Auto-generated method stub
        return createShip(where, 1, 3, 'd', "Destroyer");
    }

    protected Ship<Character> createShip(Placement where, int w, int h, char letter, String name){
        Coordinate c = where.getWhere();
        char ori = where.getOrientation();
        if(ori != 'H' && ori != 'V'){
            throw new IllegalArgumentException("orientation of this placement is illegal");
        }
        if(ori == 'V') {
            Ship<Character> rec = new RectangleShip<Character>(name, c, w, h, letter, '*');
            return rec;
        } else {
            Ship<Character> rec = new RectangleShip<Character>(name, c, h, w, letter, '*');
            return rec;
        }
    }


}
