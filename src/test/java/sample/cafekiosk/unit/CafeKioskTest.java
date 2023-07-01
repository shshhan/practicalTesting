package sample.cafekiosk.unit;

import org.junit.jupiter.api.Test;
import sample.cafekiosk.unit.beverage.Americano;

class CafeKioskTest {

    @Test
    void add() {
        CafeKiosk cafeKiosk = new CafeKiosk();
        cafeKiosk.add(new Americano());

        System.out.println(">> amount of beverages : " + cafeKiosk.getBeverages().size());
        System.out.println(">> elements of beverages : " + cafeKiosk.getBeverages().get(0).getName());
    }
}