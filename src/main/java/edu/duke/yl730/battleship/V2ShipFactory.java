package edu.duke.yl730.battleship;

public class V2ShipFactory implements AbstractShipFactory<Character>{

    @Override
    public Ship<Character> makeSubmarine(Placement where) {
        return createShip(where, 1, 2, 's', "Submarine");
    }

    @Override
    public Ship<Character> makeDestroyer(Placement where) {
        return createShip(where, 1, 3, 'd', "Destroyer");
    }

    @Override
    public Ship<Character> makeBattleship(Placement where) {
        Coordinate c = where.getWhere();
        char o = where.getOrientation();
        BasicShip<Character> battleShip = new V2_Battleship<Character>("Battleship",c,o,'b','*');
        return battleShip;
    }

    @Override
    public Ship<Character> makeCarrier(Placement where) {
        Coordinate c = where.getWhere();
        char o = where.getOrientation();
        BasicShip<Character> carrier = new V2_Carrier<Character>("Carrier",c,o,'c','*');
        return carrier;
    }

    public Ship<Character> moveShip(Ship<Character> ship, Placement where){
        if(ship.getName().equals("Submarine") ){return moveSubmarine(ship,where);}
        else if(ship.getName().equals("Destroyer")){
            return moveDestroyer(ship,where);
        } else if(ship.getName().equals("Battleship")){
            return moveBattleship(ship,where);
        } else if(ship.getName().equals("Carrier")){
            return moveCarrier(ship,where);
        }
        return null;
    }

    public Ship<Character> moveBattleship(Ship<Character> ship, Placement where){
        V2ShipFactory v = new V2ShipFactory();
        V2_Battleship<Character> new_Ship = (V2_Battleship<Character>) v.makeBattleship(where);
        V2_Battleship<Character> old_ship = (V2_Battleship<Character>) ship;
        for(int i = 1; i<5;i++){
            Coordinate old_c = old_ship.piecesIndex.get(i);
            Coordinate new_c = new_Ship.piecesIndex.get(i);
            new_Ship.myPieces.put(new_c,old_ship.myPieces.get(old_c));
        }
        return new_Ship;
    }

    public Ship<Character> moveCarrier(Ship<Character> ship, Placement where){
        V2ShipFactory v = new V2ShipFactory();
        V2_Carrier<Character> new_Ship = (V2_Carrier<Character>) v.makeCarrier(where);
        V2_Carrier<Character> old_ship = (V2_Carrier<Character>) ship;
        for(int i = 1; i<7;i++){
            Coordinate old_c = old_ship.piecesIndex.get(i);
            Coordinate new_c = new_Ship.piecesIndex.get(i);
            new_Ship.myPieces.put(new_c,old_ship.myPieces.get(old_c));
        }
        return new_Ship;
    }

    public Ship<Character> moveSubmarine(Ship<Character> ship, Placement where){
        V2ShipFactory v = new V2ShipFactory();
        Coordinate new_upperleft = where.getWhere();
        char new_ori = where.getOrientation();
        BasicShip<Character> new_Ship = (BasicShip<Character>) v.makeSubmarine(where);
        BasicShip<Character> old_ship = (BasicShip<Character>) ship;
        //don't need to change
        if(new_ori == 'H' && ship.getOrientation() == 'H'){
            for(int i = 0;i<2;i++){
                //track current coordinate of new ship and old ship
                Coordinate old_c = new Coordinate(old_ship.upperLeft.getRow(),old_ship.upperLeft.getColumn()+i);
                Coordinate new_c = new Coordinate(new_upperleft.getRow(),new_upperleft.getColumn()+i);
                //copy coordinate status from old ship to new ship
                new_Ship.myPieces.put(new_c,old_ship.myPieces.get(old_c));
            }
        } else if(new_ori == 'V' && ship.getOrientation() == 'V'){
            for(int i = 0;i<2;i++){
                Coordinate old_c = new Coordinate(old_ship.upperLeft.getRow()+i,old_ship.upperLeft.getColumn());
                Coordinate new_c = new Coordinate(new_upperleft.getRow()+i,new_upperleft.getColumn());
                new_Ship.myPieces.put(new_c,old_ship.myPieces.get(old_c));
            }
        } else if(new_ori == 'V' && ship.getOrientation() == 'H'){
            for(int i = 0;i<2;i++){
                Coordinate old_c = new Coordinate(old_ship.upperLeft.getRow(),old_ship.upperLeft.getColumn()+i);
                Coordinate new_c = new Coordinate(new_upperleft.getRow()+i,new_upperleft.getColumn());
                new_Ship.myPieces.put(new_c,old_ship.myPieces.get(old_c));
            }
        } else if(new_ori == 'H' && ship.getOrientation() == 'V'){
            for(int i = 0;i<2;i++){
                Coordinate old_c = new Coordinate(old_ship.upperLeft.getRow()+i,old_ship.upperLeft.getColumn());
                Coordinate new_c = new Coordinate(new_upperleft.getRow(),new_upperleft.getColumn()+i);
                new_Ship.myPieces.put(new_c,old_ship.myPieces.get(old_c));
            }
        }
        return new_Ship;
    }

    public Ship<Character> moveDestroyer(Ship<Character> ship, Placement where){
        V2ShipFactory v = new V2ShipFactory();
        Coordinate new_upperleft = where.getWhere();
        char new_ori = where.getOrientation();
        BasicShip<Character> new_Ship = (BasicShip<Character>) v.makeDestroyer(where);
        BasicShip<Character> old_ship = (BasicShip<Character>) ship;
        if(new_ori == 'H' && ship.getOrientation() == 'H'){
            for(int i = 0;i<3;i++){
                Coordinate old_c = new Coordinate(old_ship.upperLeft.getRow(),old_ship.upperLeft.getColumn()+i);
                Coordinate new_c = new Coordinate(new_upperleft.getRow(),new_upperleft.getColumn()+i);
                new_Ship.myPieces.put(new_c,old_ship.myPieces.get(old_c));
            }
        } else if(new_ori == 'V' && ship.getOrientation() == 'V'){
            for(int i = 0;i<3;i++){
                Coordinate old_c = new Coordinate(old_ship.upperLeft.getRow()+i,old_ship.upperLeft.getColumn());
                Coordinate new_c = new Coordinate(new_upperleft.getRow()+i,new_upperleft.getColumn());
                new_Ship.myPieces.put(new_c,old_ship.myPieces.get(old_c));
            }
        } else if(new_ori == 'V' && ship.getOrientation() == 'H'){
            for(int i = 0;i<3;i++){
                Coordinate old_c = new Coordinate(old_ship.upperLeft.getRow(),old_ship.upperLeft.getColumn()+i);
                Coordinate new_c = new Coordinate(new_upperleft.getRow()+i,new_upperleft.getColumn());
                new_Ship.myPieces.put(new_c,old_ship.myPieces.get(old_c));
            }
        } else if(new_ori == 'H' && ship.getOrientation() == 'V'){
            for(int i = 0;i<3;i++){
                Coordinate old_c = new Coordinate(old_ship.upperLeft.getRow()+i,old_ship.upperLeft.getColumn());
                Coordinate new_c = new Coordinate(new_upperleft.getRow(),new_upperleft.getColumn()+i);
                new_Ship.myPieces.put(new_c,old_ship.myPieces.get(old_c));
            }
        }
        return new_Ship;
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
