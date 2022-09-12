import cs3500.freecell.model.hw02.Suit;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Testing class for Suit.
 */
public class SuitTest {

  // test for toString
  @Test
  public void testToString() {
    assertEquals("♦", Suit.DIAMOND.toString());
    assertEquals("♥", Suit.HEART.toString());
    assertEquals("♣", Suit.CLUB.toString());
    assertEquals("♠", Suit.SPADE.toString());
  }

  // test for isBlack
  @Test
  public void testIsBlack() {
    assertEquals(false, Suit.DIAMOND.isBlack());
    assertEquals(false, Suit.HEART.isBlack());
    assertEquals(true, Suit.CLUB.isBlack());
    assertEquals(true, Suit.SPADE.isBlack());
  }


}
