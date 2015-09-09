package broodjes;
import static org.junit.Assert.*;

import org.junit.Test;


public class OrderTest {

    @Test
    public void testParseCurrency() {
        assertEquals(new Currency(380), Order.getPrice("Jurgen 1 x Komkommersla groot, wit, smos ( €3,80)."));
        assertEquals(new Currency(460), Order.getPrice("1 x Beenham&jonge kaas groot, wit, smos ( €4,60 - Bart )."));
        assertEquals(new Currency(-162), Order.getPrice("1 x Korting ( €-1,62)."));
        assertEquals(new Currency(2000), Order.getPrice("Jurgen terugbetaling 2015-07-01 ( €20,00)."));
    }

    @Test
    public void testParseName() {
        assertEquals("Jurgen", Order.getName("Jurgen 1 x Komkommersla groot, wit, smos ( €3,80)."));
        assertEquals("Bart", Order.getName("1 x Beenham&jonge kaas groot, wit, smos ( €4,60 - Bart )."));
        assertEquals("Jurgen", Order.getName("Jurgen terugbetaling 2015-07-01 ( €20,00)."));
    }

    @Test
    public void testParseDescription() {
        assertEquals("Komkommersla groot, wit, smos", Order.getDescription("Jurgen 1 x Komkommersla groot, wit, smos ( €3,80)."));
        assertEquals("Beenham&jonge kaas groot, wit, smos", Order.getDescription("1 x Beenham&jonge kaas groot, wit, smos ( €4,60 - Bart )."));
        assertEquals("terugbetaling", Order.getDescription("Jurgen terugbetaling 2015-07-01 ( €20,00)."));
    }

    
}
