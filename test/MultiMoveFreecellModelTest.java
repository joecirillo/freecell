import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import cs3500.freecell.model.FreecellModelCreator;
import cs3500.freecell.model.PileType;
import cs3500.freecell.model.hw02.Card;
import cs3500.freecell.model.hw02.ICard;
import cs3500.freecell.model.hw02.SimpleFreecellModel;
import cs3500.freecell.model.hw02.Suit;
import cs3500.freecell.model.hw02.Value;
import cs3500.freecell.model.hw04.MultiMoveFreecellModel;
import java.util.Collections;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

/**
 * Testing class for MultiMoveFreecellModel. Also tests the static factory method create.
 */
public class MultiMoveFreecellModelTest {

  // model for multimove
  private MultiMoveFreecellModel multi;
  // a valid deck of 52 cards
  private List<ICard> generatedDeck;


  // initializes the data
  @Before
  public void initData() {
    multi = new MultiMoveFreecellModel();
    generatedDeck = multi.getDeck();
  }

  // tests to ensure single and multimoves can be instantiated
  @Test
  public void testCreate() {
    FreecellModelCreator creator = new FreecellModelCreator();
    assertEquals(true, creator.create(FreecellModelCreator.GameType.MULTIMOVE)
        instanceof MultiMoveFreecellModel);
    assertEquals(true, creator.create(FreecellModelCreator.GameType.SINGLEMOVE)
        instanceof SimpleFreecellModel);
  }

  // test for null model
  @Test(expected = IllegalArgumentException.class)
  public void testCreateIAE() {
    FreecellModelCreator creator = new FreecellModelCreator();
    creator.create(null);
  }


  // test for getDeck in multi.
  @Test
  public void testGetDeckMulti() {
    initData();
    assertEquals(52, multi.getDeck().size());
    assertNotEquals(53, multi.getDeck().size());
  }

  // tests to throw an exception for an invalid deck size (not 52) in multi.
  @Test(expected = IllegalArgumentException.class)
  public void testDeckSize() {
    initData();
    ICard aceOfDiamonds = new Card(Value.ACE, Suit.DIAMOND);
    generatedDeck.add(aceOfDiamonds);
    multi.startGame(generatedDeck, 6, 4, false);
  }

  // tests to throw an exception for an invalid cascade pile number.
  // (must be be at least 4.)
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidCascadePileNumber() {
    initData();
    multi.startGame(generatedDeck, 2, 2, true);
  }

  // tests to throw an exception for an invalid open pile number.
  // (must be at least one.)
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidOpenPileNumber() {
    initData();
    multi.startGame(generatedDeck, 8, -1, true);
  }

  // test for shuffling a pile
  @Test
  public void testShuffleTrueCorrectPiles() {
    initData();
    multi.startGame(generatedDeck, 12, 4, true);
    assertEquals(4, multi.getNumOpenPiles());
    assertEquals(12, multi.getNumCascadePiles());
  }

  // tests to throw an exception for an invalid cascade pile number.
  // (must be be at least 4.)
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidCascadePileNumberMulti() {
    initData();
    multi.startGame(generatedDeck, 2, 2, true);
  }

  // tests to throw an exception for an invalid open pile number.
  // (must be at least one.)
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidOpenPileNumberMulti() {
    initData();
    multi.startGame(generatedDeck, 6, 0, true);
  }

  // tests for double shuffling and to make sure cards aren't in the same order
  @Test
  public void testDoubleShuffle() {
    initData();
    multi.startGame(generatedDeck, 10, 5, false);
    assertEquals(5, multi.getNumOpenPiles());
    assertEquals(10, multi.getNumCascadePiles());
    multi.getCascadeCardAt(0, 0);
    MultiMoveFreecellModel multi2 = new MultiMoveFreecellModel();
    multi2.startGame(generatedDeck, 10, 5, true);
    // makes sure the first card in twice shuffled deck is not equal to first card in once shuffled
    // deck
    assertNotEquals(multi2.getCascadeCardAt(0, 0),
        multi.getCascadeCardAt(0, 0));
    // makes sure the second card in twice shuffled deck is not equal to second card in
    // once shuffled deck
    assertNotEquals(multi2.getCascadeCardAt(0, 1),
        multi.getCascadeCardAt(0, 1));
  }

  // tests for throwing illegal argument exception for invalid cascade to cascade move
  @Test(expected = IllegalArgumentException.class)
  public void testIllegalMoveCascadeToCascade() {
    multi.startGame(generatedDeck, 8, 4, false);
    multi.move(PileType.CASCADE, -1, 2, PileType.CASCADE, 3);
  }

  // tests for throwing illegal argument exception for invalid cascade to open move
  @Test(expected = IllegalArgumentException.class)
  public void testIllegalMoveCascadeToOpen() {
    multi.startGame(generatedDeck, 8, 4, false);
    multi.move(PileType.CASCADE, 4, -1, PileType.OPEN, 3);
  }

  // tests for throwing illegal argument exception for invalid cascade to foundation move
  @Test(expected = IllegalArgumentException.class)
  public void testIllegalMoveCascadeToFoundation() {
    multi.startGame(generatedDeck, 8, 4, false);
    multi.move(PileType.CASCADE, 4, -1, PileType.FOUNDATION, -1);
  }

  // test for state exception for not starting game and trying to make a move
  @Test(expected = IllegalStateException.class)
  public void testGameNotStartedSE() {
    multi.move(PileType.CASCADE, 1,
        4, PileType.FOUNDATION, 1);
  }

  // test for out of bounds index in cascade
  @Test(expected = IllegalArgumentException.class)
  public void testGetNumCardsInCascadePile() {
    initData();
    multi.startGame(generatedDeck, 9, 5, false);
    multi.getNumCardsInCascadePile(10);
  }

  // test for out of bounds index in open
  @Test(expected = IllegalArgumentException.class)
  public void testGetNumCardsInOpenPile() {
    initData();
    multi.startGame(generatedDeck, 6, 4, false);
    multi.getNumCardsInOpenPile(-1);
  }

  // test for out of bounds index in foundation
  @Test(expected = IllegalArgumentException.class)
  public void testGetNumCardsInFoundationPile() {
    initData();
    multi.startGame(generatedDeck, 6, 4, false);
    multi.getNumCardsInFoundationPile(4);
  }

  // test for game not started in cascade
  @Test(expected = IllegalStateException.class)
  public void testGetNumCardsInCascadePileBGS() {
    initData();
    multi.getNumCardsInCascadePile(7);
  }

  // test for game not started in open
  @Test(expected = IllegalStateException.class)
  public void testGetNumCardsInOpenPileBGS() {
    initData();
    multi.getNumCardsInOpenPile(-1);
  }

  // test for exception for game not started in foundation
  @Test(expected = IllegalStateException.class)
  public void testGetNumCardsInFoundationPileBGS() {
    initData();
    multi.getNumCardsInFoundationPile(4);
  }

  // test for index in cascade in a chosen pile
  @Test
  public void testGetNumCardsInCascadePileAGS() {
    initData();
    multi.startGame(generatedDeck, 6, 4, false);
    assertNotEquals(0, multi.getNumCardsInCascadePile(5));
    assertEquals(9, multi.getNumCardsInCascadePile(3));
  }

  // test for index in open in a chosen pile
  @Test
  public void testGetNumCardsInOpenPileAGS() {
    initData();
    multi.startGame(generatedDeck, 6, 4, false);
    assertEquals(0, multi.getNumCardsInOpenPile(0));
  }

  // test for index in foundation for number of cards in a chosen pile
  @Test
  public void testGetNumCardsInFoundationPileAGS() {
    initData();
    multi.startGame(generatedDeck, 6, 4, false);
    assertEquals(0, multi.getNumCardsInFoundationPile(3));
  }

  // tests for getNumOpenPiles before and after game has started
  @Test
  public void testGetNumOpenPilesBeforeAndAfterSG() {
    initData();
    assertEquals(-1, multi.getNumOpenPiles());
    multi.startGame(generatedDeck, 6, 4, true);
    assertEquals(4, multi.getNumOpenPiles());
  }

  // tests for getNumCascadePiles before and after game has started
  @Test
  public void testGetNumCascadePilesBeforeAndAfterSG() {
    initData();
    assertEquals(-1, multi.getNumCascadePiles());
    multi.startGame(generatedDeck, 6, 4, false);
    assertEquals(6, multi.getNumCascadePiles());
  }

  // tests for getNumCascadePiles if game call is invalid
  @Test(expected = IllegalArgumentException.class)
  public void testGetNumCascadePilesAfterInvalidGame() {
    initData();
    assertEquals(-1, multi.getNumCascadePiles());
    multi.startGame(generatedDeck, 0, 5, true);
    assertEquals(2, multi.getNumCascadePiles());
  }


  // test for getOpenCardAt for valid piles and indices
  @Test
  public void testGetOpenCardAt() {
    multi.startGame(generatedDeck, 4, 4, false);
    multi.move(PileType.CASCADE, 1, 12, PileType.OPEN, 1);
    assertNotEquals(new Card(Value.ACE, Suit.DIAMOND), multi.getOpenCardAt(1));
  }

  // test for getCascadeCardAt
  @Test
  public void testGetCascadeCardAt() {
    multi.startGame(generatedDeck, 4, 4, false);
    assertNotEquals(new Card(Value.ACE, Suit.DIAMOND), multi.getCascadeCardAt(3, 5));
  }

  // test for getFoundationCardAt for invalid pile and card indices
  @Test(expected = IllegalArgumentException.class)
  public void testGetFoundationCardAtAE() {
    multi.startGame(generatedDeck, 4, 4, false);
    multi.getFoundationCardAt(-1, 0);
    multi.getFoundationCardAt(0, -1);
    multi.getFoundationCardAt(3, 13);
    multi.getFoundationCardAt(4, 12);
    multi.getFoundationCardAt(1, 0);
  }

  // test for getOpenCardAt for invalid pile and card indices
  @Test(expected = IllegalArgumentException.class)
  public void testGetOpenCardAtAE() {
    multi.startGame(generatedDeck, 4, 4, false);
    multi.getOpenCardAt(-1);
    multi.getOpenCardAt(-1);
  }

  // test for getCascadeCardAt for invalid pile and card indices
  @Test(expected = IllegalArgumentException.class)
  public void testGetCascadeCardAtAE() {
    multi.startGame(generatedDeck, 4, 4, false);
    multi.getCascadeCardAt(-1, 0);
    multi.getCascadeCardAt(0, -1);
  }

  // test for getFoundationCardAt for state exception condition
  @Test(expected = IllegalStateException.class)
  public void testGetFoundationCardAtSE() {
    multi.getFoundationCardAt(1, 2);
  }

  // test for getOpenCardAt for state exception condition
  @Test(expected = IllegalStateException.class)
  public void testGetOpenCardAtSE() {
    multi.getOpenCardAt(1);
  }

  // test for getCascadeCardAt for state exception condition
  @Test(expected = IllegalStateException.class)
  public void testGetCascadeCardAtSE() {
    multi.getCascadeCardAt(0, 3);
  }

  // test for single moves and making sure they still follow SimpleFreecell rules
  @Test
  public void testSingleMovesInMulti() {
    Collections.reverse(generatedDeck);
    multi.startGame(generatedDeck, 6, 6, false);
    // tests to move from cascade to open
    multi.move(PileType.CASCADE, 3, 8, PileType.OPEN, 0);
    assertEquals(1, multi.getNumCardsInOpenPile(0));
    assertEquals(8, multi.getNumCardsInCascadePile(3));
    multi.move(PileType.CASCADE, 3, 7, PileType.OPEN, 1);
    assertEquals(1, multi.getNumCardsInOpenPile(1));
    assertEquals(7, multi.getNumCardsInCascadePile(3));
    // tests to move from cascade to foundation
    multi.move(PileType.CASCADE, 0, 8, PileType.FOUNDATION, 0);
    assertEquals(8, multi.getNumCardsInCascadePile(0));
    assertEquals(1, multi.getNumCardsInFoundationPile(0));
    multi.move(PileType.CASCADE, 5, 7, PileType.OPEN, 2);
    assertEquals(7, multi.getNumCardsInCascadePile(5));
    assertEquals(1, multi.getNumCardsInOpenPile(2));
    // tests to move from cascade to cascade
    multi.move(PileType.CASCADE, 5, 6, PileType.CASCADE, 3);
    assertEquals(6, multi.getNumCardsInCascadePile(5));
    assertEquals(8, multi.getNumCardsInCascadePile(3));
    // tests to move from open to cascade
    multi.move(PileType.OPEN, 2, 0, PileType.CASCADE, 3);
    assertEquals(0, multi.getNumCardsInOpenPile(2));
    assertEquals(9, multi.getNumCardsInCascadePile(3));
  }

  // test for illegal multimoves from cascade to open
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidToOpenMove() {
    multi.startGame(generatedDeck, 6, 6, false);
    multi.move(PileType.CASCADE, 0, 5, PileType.OPEN, 0);
  }

  // test for illegal multimoves from cascade to foundation
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidToFoundationMove() {
    multi.startGame(generatedDeck, 4, 6, false);
    multi.move(PileType.CASCADE, 0, 0, PileType.OPEN, 0);
  }

  // test for valid multimove from cascade to cascade
  @Test
  public void testValidMultiMoveCascadeToCascade() {
    Collections.reverse(generatedDeck);
    multi.startGame(generatedDeck, 6, 6, false);
    // these move cards out of the way to make a valid multimove
    multi.move(PileType.CASCADE, 3, 8, PileType.OPEN, 0);
    assertEquals(1, multi.getNumCardsInOpenPile(0));
    assertEquals(8, multi.getNumCardsInCascadePile(3));
    multi.move(PileType.CASCADE, 3, 7, PileType.OPEN, 1);
    assertEquals(1, multi.getNumCardsInOpenPile(1));
    assertEquals(7, multi.getNumCardsInCascadePile(3));
    multi.move(PileType.CASCADE, 5, 6, PileType.CASCADE, 3);
    assertEquals(6, multi.getNumCardsInCascadePile(5));
    assertEquals(9, multi.getNumCardsInCascadePile(3));
  }

  // test for a more complex multi move from cascade to cascade with four cards
  @Test
  public void testValidMultiMoveCascadeToCascadeFourMoves() {
    Collections.reverse(generatedDeck);
    multi.startGame(generatedDeck, 26, 6, false);
    // these move cards out of the way to make a valid multimove
    multi.move(PileType.CASCADE, 8, 1, PileType.CASCADE, 2);
    multi.move(PileType.CASCADE, 11, 1, PileType.CASCADE, 2);
    multi.move(PileType.CASCADE, 17, 1, PileType.CASCADE, 2);
    multi.move(PileType.CASCADE, 2, 1, PileType.CASCADE, 0);
    assertEquals(6, multi.getNumCardsInCascadePile(0));
  }

  // test for invalid multimove because of lack of opens and cascades
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidMoveNotEnoughSpace() {
    Collections.reverse(generatedDeck);
    multi.startGame(generatedDeck, 6, 2, false);
    // these move cards out of the way to make a valid multimove
    multi.move(PileType.CASCADE, 3, 8, PileType.OPEN, 0);
    assertEquals(1, multi.getNumCardsInOpenPile(0));
    assertEquals(8, multi.getNumCardsInCascadePile(3));
    multi.move(PileType.CASCADE, 3, 7, PileType.OPEN, 1);
    assertEquals(1, multi.getNumCardsInOpenPile(1));
    assertEquals(7, multi.getNumCardsInCascadePile(3));
    // move is attempted for two cards except formula threshold not met
    // (n + 1) * 2^k = (0 opens + 1) * (2^ 0 cascades) = 1 < 2 WILL FAIL!
    multi.move(PileType.CASCADE, 5, 6, PileType.CASCADE, 3);
  }

  // test for invalid multimove from cascade to cascade
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidMultiMove() {
    Collections.reverse(generatedDeck);
    multi.startGame(generatedDeck, 6, 6, false);
    multi.move(PileType.CASCADE, 5, 6, PileType.CASCADE, 3);
  }


  // tests for game being over
  @Test
  public void testGameOver() {
    multi.startGame(generatedDeck, 52, 4, false);
    for (int i = 0; i < 4; i++) {
      multi.move(PileType.CASCADE, i, 0, PileType.FOUNDATION, i);
      multi.move(PileType.CASCADE, i + 4, 0, PileType.FOUNDATION, i);
      multi.move(PileType.CASCADE, i + 8, 0, PileType.FOUNDATION, i);
      multi.move(PileType.CASCADE, i + 12, 0, PileType.FOUNDATION, i);
      multi.move(PileType.CASCADE, i + 16, 0, PileType.FOUNDATION, i);
      multi.move(PileType.CASCADE, i + 20, 0, PileType.FOUNDATION, i);
      multi.move(PileType.CASCADE, i + 24, 0, PileType.FOUNDATION, i);
      multi.move(PileType.CASCADE, i + 28, 0, PileType.FOUNDATION, i);
      multi.move(PileType.CASCADE, i + 32, 0, PileType.FOUNDATION, i);
      multi.move(PileType.CASCADE, i + 36, 0, PileType.FOUNDATION, i);
      multi.move(PileType.CASCADE, i + 40, 0, PileType.FOUNDATION, i);
      multi.move(PileType.CASCADE, i + 44, 0, PileType.FOUNDATION, i);
      assertEquals(false, multi.isGameOver());
      multi.move(PileType.CASCADE, i + 48, 0, PileType.FOUNDATION, i);
    }
    assertEquals(13, multi.getNumCardsInFoundationPile(0));
    assertEquals(13, multi.getNumCardsInFoundationPile(1));
    assertEquals(13, multi.getNumCardsInFoundationPile(2));
    assertEquals(13, multi.getNumCardsInFoundationPile(3));
    assertEquals(true, multi.isGameOver());
  }
}
