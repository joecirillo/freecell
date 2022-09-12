import cs3500.freecell.model.PileType;
import cs3500.freecell.model.hw02.Card;
import cs3500.freecell.model.hw02.ICard;
import cs3500.freecell.model.hw02.SimpleFreecellModel;

import cs3500.freecell.model.hw02.Suit;
import cs3500.freecell.model.hw02.Value;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Testing class for SimpleFreecellModelTest.
 */
public class SimpleFreecellModelTest {

  private SimpleFreecellModel simp;
  private ICard aceOfDiamonds;
  private ICard threeOfSpades;
  private List<ICard> generatedDeck;

  // initializes the data
  @Before
  public void initData() {
    simp = new SimpleFreecellModel();
    aceOfDiamonds = new Card(Value.ACE, Suit.DIAMOND);
    threeOfSpades = new Card(Value.THREE, Suit.SPADE);
    generatedDeck = simp.getDeck();
  }


  // test for getDeck.
  @Test
  public void testGetDeck() {
    initData();
    assertEquals(52, simp.getDeck().size());
    assertNotEquals(51, simp.getDeck().size());
  }

  // tests to throw an exception for an invalid deck size (not 52).
  @Test(expected = IllegalArgumentException.class)
  public void testDeckSize() {
    initData();
    generatedDeck.add(aceOfDiamonds);
    simp.startGame(generatedDeck, 4, 2, false);
  }

  // tests for no shuffle
  @Test
  public void testShuffleFalse() {
    initData();
    simp.startGame(generatedDeck, 8, 4, false);
    assertEquals(4, simp.getNumOpenPiles());
    assertEquals(8, simp.getNumCascadePiles());
    simp.startGame(generatedDeck, 8, 4, true);
    assertEquals(4, simp.getNumOpenPiles());
    assertEquals(8, simp.getNumCascadePiles());
  }

  // tests for no shuffle
  @Test
  public void testShuffleTrue() {
    initData();
    simp.startGame(generatedDeck, 10, 5, true);
    assertEquals(5, simp.getNumOpenPiles());
    assertEquals(10, simp.getNumCascadePiles());
  }

  // tests to throw an exception for an invalid cascade pile number.
  // (must be be at least 4.)
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidCascadePileNumber() {
    initData();
    simp.startGame(generatedDeck, 3, 2, true);
  }

  // tests to throw an exception for an invalid open pile number.
  // (must be at least one.)
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidOpenPileNumber() {
    initData();
    simp.startGame(generatedDeck, 8, 0, true);
  }

  // tests for shuffled twice to make sure piles are cleared
  @Test
  public void testDoubleShuffle() {
    initData();
    simp.startGame(generatedDeck, 10, 5, false);
    assertEquals(5, simp.getNumOpenPiles());
    assertEquals(10, simp.getNumCascadePiles());
    simp.startGame(generatedDeck, 10, 4, true);
    assertEquals(4, simp.getNumOpenPiles());
    assertEquals(10, simp.getNumCascadePiles());
  }

  // tests to throw an exception for a deck with duplicate cards.
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidDuplicateDeck() {
    initData();
    generatedDeck.remove(threeOfSpades);
    generatedDeck.add(aceOfDiamonds);
    simp.startGame(generatedDeck, 8, 4, true);
  }

  // tests to ensure non-shuffled decks are  the same as original deck.
  @Test
  public void testNonShuffledDecks() {
    initData();
    simp.startGame(generatedDeck, 8, 4, false);
    simp.startGame(generatedDeck, 8, 4, false);
    assertEquals(simp, simp);

  }

  // test for out of bounds index in cascade
  @Test(expected = IllegalArgumentException.class)
  public void testGetNumCardsInCascadePile() {
    initData();
    simp.startGame(generatedDeck, 6, 4, false);
    simp.getNumCardsInCascadePile(7);
  }

  // test for out of bounds index in open
  @Test(expected = IllegalArgumentException.class)
  public void testGetNumCardsInOpenPile() {
    initData();
    simp.startGame(generatedDeck, 6, 4, false);
    simp.getNumCardsInOpenPile(-1);
  }

  // test for out of bounds index in foundation
  @Test(expected = IllegalArgumentException.class)
  public void testGetNumCardsInFoundationPile() {
    initData();
    simp.startGame(generatedDeck, 6, 4, false);
    simp.getNumCardsInFoundationPile(4);
  }

  // test for game not started in cascade
  @Test(expected = IllegalStateException.class)
  public void testGetNumCardsInCascadePileBGS() {
    initData();
    simp.getNumCardsInCascadePile(7);
  }

  // test for game not started in open
  @Test(expected = IllegalStateException.class)
  public void testGetNumCardsInOpenPileBGS() {
    initData();
    simp.getNumCardsInOpenPile(-1);
  }

  // test for game not started in foundation
  @Test(expected = IllegalStateException.class)
  public void testGetNumCardsInFoundationPileBGS() {
    initData();
    simp.getNumCardsInFoundationPile(4);
  }

  // test for index in cascade
  @Test
  public void testGetNumCardsInCascadePileAGS() {
    initData();
    simp.startGame(generatedDeck, 6, 4, false);
    assertNotEquals(0, simp.getNumCardsInCascadePile(5));
    assertEquals(9, simp.getNumCardsInCascadePile(3));
  }

  // test for index in open
  @Test
  public void testGetNumCardsInOpenPileAGS() {
    initData();
    simp.startGame(generatedDeck, 6, 4, false);
    assertEquals(0, simp.getNumCardsInOpenPile(0));
  }

  // test for index in foundation
  @Test
  public void testGetNumCardsInFoundationPileAGS() {
    initData();
    simp.startGame(generatedDeck, 6, 4, false);
    assertEquals(0, simp.getNumCardsInFoundationPile(3));
  }

  // tests for getNumOpenPiles
  @Test
  public void testGetNumOpenPiles() {
    initData();
    assertEquals(-1, simp.getNumOpenPiles());
    simp.startGame(generatedDeck, 6, 4, true);
    assertEquals(4, simp.getNumOpenPiles());
  }

  // tests for getNumCascadePiles
  @Test
  public void testGetNumCascadePiles() {
    initData();
    assertEquals(-1, simp.getNumCascadePiles());
    simp.startGame(generatedDeck, 6, 4, false);
    assertEquals(6, simp.getNumCascadePiles());
  }

  // tests for getNumCascadePiles
  @Test(expected = IllegalArgumentException.class)
  public void testGetNumCascadePilesAfterInvalidGame() {
    initData();
    assertEquals(-1, simp.getNumCascadePiles());
    simp.startGame(generatedDeck, 0, 5, true);
    assertEquals(2, simp.getNumCascadePiles());
  }


  // test for getOpenCardAt
  @Test
  public void testGetOpenCardAt() {
    simp.startGame(generatedDeck, 4, 4, false);
    simp.move(PileType.CASCADE, 1, 12, PileType.OPEN, 1);
    assertNotEquals(new Card(Value.ACE, Suit.DIAMOND), simp.getOpenCardAt(1));
  }

  // test for getCascadeCardAt
  @Test
  public void testGetCascadeCardAt() {
    simp.startGame(generatedDeck, 4, 4, false);
    assertNotEquals(new Card(Value.ACE, Suit.DIAMOND), simp.getCascadeCardAt(3, 5));
  }

  // test for getFoundationCardAt
  @Test(expected = IllegalArgumentException.class)
  public void testGetFoundationCardAtAE() {
    simp.startGame(generatedDeck, 4, 4, false);
    simp.getFoundationCardAt(-1, 0);
    simp.getFoundationCardAt(0, -1);
    simp.getFoundationCardAt(3, 13);
    simp.getFoundationCardAt(4, 12);
    simp.getFoundationCardAt(1, 0);
  }

  // test for getOpenCardAt
  @Test(expected = IllegalArgumentException.class)
  public void testGetOpenCardAtAE() {
    simp.startGame(generatedDeck, 4, 4, false);
    simp.getOpenCardAt(-1);
    simp.getOpenCardAt(-1);
  }

  // test for getCascadeCardAt
  @Test(expected = IllegalArgumentException.class)
  public void testGetCascadeCardAtAE() {
    simp.startGame(generatedDeck, 4, 4, false);
    simp.getCascadeCardAt(-1, 0);
    simp.getCascadeCardAt(0, -1);
  }

  // test for getFoundationCardAt
  @Test(expected = IllegalStateException.class)
  public void testGetFoundationCardAtSE() {
    simp.getFoundationCardAt(1, 2);
  }

  // test for getOpenCardAt
  @Test(expected = IllegalStateException.class)
  public void testGetOpenCardAtSE() {
    simp.getOpenCardAt(1);
  }

  // test for getCascadeCardAt
  @Test(expected = IllegalStateException.class)
  public void testGetCascadeCardAtSE() {
    simp.getCascadeCardAt(0, 3);
  }


  @Test
  public void testIsGameOver() {
    initData();
    simp.startGame(generatedDeck, 4, 4, false);
    simp.move(PileType.CASCADE, 0, 12, PileType.OPEN, 0);
    simp.move(PileType.CASCADE, 1, 12, PileType.OPEN, 1);
    simp.move(PileType.CASCADE, 2, 12, PileType.OPEN, 2);
    simp.move(PileType.CASCADE, 3, 12, PileType.OPEN, 3);
    assertNotEquals(true, simp.isGameOver());
    simp.startGame(generatedDeck, 8, 4, true);
    assertNotEquals(true, simp.isGameOver());
  }


  // tests for successful move
  @Test
  public void testSuccessfulMoves() {
    initData();
    simp.startGame(generatedDeck, 4, 4, false);
    simp.move(PileType.CASCADE, 1, 12, PileType.OPEN, 1);
    simp.move(PileType.CASCADE, 1, 11, PileType.CASCADE, 3);
    simp.move(PileType.CASCADE, 0, 0, PileType.OPEN, 3);
    simp.move(PileType.OPEN, 3, 0, PileType.OPEN, 2);
    assertEquals(0, simp.getNumCardsInFoundationPile(1));
    assertEquals(14, simp.getNumCardsInCascadePile(3));
    assertEquals(1, simp.getNumCardsInOpenPile(2));

  }

  @Test
  public void testGameOver() {
    simp.startGame(generatedDeck, 52, 4, false);
    for (int i = 0; i < 4; i++) {
      simp.move(PileType.CASCADE, i, 0, PileType.FOUNDATION, i);
      simp.move(PileType.CASCADE, i + 4, 0, PileType.FOUNDATION, i);
      simp.move(PileType.CASCADE, i + 8, 0, PileType.FOUNDATION, i);
      simp.move(PileType.CASCADE, i + 12, 0, PileType.FOUNDATION, i);
      simp.move(PileType.CASCADE, i + 16, 0, PileType.FOUNDATION, i);
      simp.move(PileType.CASCADE, i + 20, 0, PileType.FOUNDATION, i);
      simp.move(PileType.CASCADE, i + 24, 0, PileType.FOUNDATION, i);
      simp.move(PileType.CASCADE, i + 28, 0, PileType.FOUNDATION, i);
      simp.move(PileType.CASCADE, i + 32, 0, PileType.FOUNDATION, i);
      simp.move(PileType.CASCADE, i + 36, 0, PileType.FOUNDATION, i);
      simp.move(PileType.CASCADE, i + 40, 0, PileType.FOUNDATION, i);
      simp.move(PileType.CASCADE, i + 44, 0, PileType.FOUNDATION, i);
      simp.move(PileType.CASCADE, i + 48, 0, PileType.FOUNDATION, i);
    }
    assertEquals(true, simp.isGameOver());
  }


}
