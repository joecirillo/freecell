import cs3500.freecell.model.hw02.Suit;
import cs3500.freecell.model.hw02.Value;
import cs3500.freecell.model.hw02.Card;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Testing class for the class card.
 */
public class CardTest {

  // tests for getValue
  @Test
  public void testGetValue() {
    assertEquals(Value.ACE, new Card(Value.ACE, Suit.DIAMOND).getValue());
    assertEquals(Value.FIVE, new Card(Value.FIVE, Suit.SPADE).getValue());
    assertEquals(Value.NINE, new Card(Value.NINE, Suit.CLUB).getValue());
  }

  // tests for getValue
  @Test
  public void testGetSuit() {
    assertEquals(Suit.DIAMOND, new Card(Value.ACE, Suit.DIAMOND).getSuit());
    assertEquals(Suit.SPADE, new Card(Value.FIVE, Suit.SPADE).getSuit());
    assertEquals(Suit.CLUB, new Card(Value.NINE, Suit.CLUB).getSuit());
  }

  // tests for getDeck
  @Test
  public void testGetDeck() {
    assertEquals("A♦", new Card(Value.ACE, Suit.DIAMOND).toString());
    assertEquals("J♠", new Card(Value.JACK, Suit.SPADE).toString());
    assertEquals("3♥", new Card(Value.THREE, Suit.HEART).toString());
    assertEquals("K♣", new Card(Value.KING, Suit.CLUB).toString());
  }
}
