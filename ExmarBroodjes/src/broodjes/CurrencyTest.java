package broodjes;
import static org.junit.Assert.*;

import org.junit.Test;


public class CurrencyTest {

    @Test
    public void testToString() {
        assertEquals("€0,05", new Currency(5).toString());
        assertEquals("€0,15", new Currency(15).toString());
        assertEquals("€2,15", new Currency(215).toString());
        assertEquals("€32,15", new Currency(3215).toString());
        
        assertEquals("€-0,05", new Currency(-5).toString());
        assertEquals("€-0,15", new Currency(-15).toString());
        assertEquals("€-2,15", new Currency(-215).toString());
        assertEquals("€-32,15", new Currency(-3215).toString());
    }
    
    @Test
    public void testOperations() {
        assertEquals(new Currency(30), new Currency(10).multiply(new Currency(3)));
        assertEquals(new Currency(13), new Currency(10).add(new Currency(3)));
        assertEquals(new Currency(33), new Currency(100).divide(new Currency(3)));
        assertEquals(new Currency(97), new Currency(100).substract(new Currency(3)));
        assertEquals(new Currency(-33), new Currency(33).negate());
        assertEquals(new Currency(33), new Currency(-33).negate());
    }
    
}
