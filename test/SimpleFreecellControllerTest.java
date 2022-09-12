import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import cs3500.freecell.controller.FreecellController;
import cs3500.freecell.controller.SimpleFreecellController;
import cs3500.freecell.model.PileType;
import cs3500.freecell.model.hw02.ICard;
import cs3500.freecell.model.hw02.SimpleFreecellModel;
import java.io.StringReader;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

/**
 * Class that contains the controller for the Freecell game and controls and evaluates the input
 * that the user gives.
 */
public class SimpleFreecellControllerTest {

  private Appendable ap;
  private Readable rd;
  private SimpleFreecellModel simp;
  private SimpleFreecellController controller;
  private SimpleFreecellController controller2;
  private List<ICard> generatedDeck;

  // initializes the data
  @Before
  public void initData() {
    ap = new StringBuilder();
    rd = new StringReader("hi");
    simp = new SimpleFreecellModel();
    controller = new SimpleFreecellController(simp, rd, ap);
    controller2 = new SimpleFreecellController(simp, rd, ap);
    generatedDeck = simp.getDeck();
  }

  // tests for playGame deck validity and shuffle
  @Test
  public void testDeckAndShuffle() {
    initData();
    controller.playGame(generatedDeck, 10, 5, false);
    assertEquals(52, generatedDeck.size());
    assertNotEquals(51, generatedDeck.size());
    assertEquals(5, simp.getNumOpenPiles());
    assertEquals(10, simp.getNumCascadePiles());
  }

  // test to throw exception for readable running out
  @Test(expected = IllegalStateException.class)
  public void testReadableRanOut() {
    SimpleFreecellController controllerNotReadable = new SimpleFreecellController(simp,
        new StringReader(""), ap);
    controllerNotReadable.playGame(generatedDeck, 8, 4, true);
  }

  // test to throw exception for readable running out
  @Test(expected = IllegalStateException.class)
  public void testReadableRanOut2() {
    controller.playGame(generatedDeck, 8, 4, true);
    controller.playGame(generatedDeck, 8, 4, true);
  }


  // tests for bad controllers
  @Test(expected = IllegalArgumentException.class)
  public void testWrongControllers() {
    SimpleFreecellController badControllerModel = new SimpleFreecellController(null, rd, ap);
    SimpleFreecellController badControllerRD = new SimpleFreecellController(simp, null, ap);
    SimpleFreecellController badControllerAP = new SimpleFreecellController(simp, rd, null);
    SimpleFreecellController badControllerAll =
        new SimpleFreecellController(null, null, null);
  }

  // tests for bad play game
  @Test(expected = IllegalArgumentException.class)
  public void testBadPlayGame() {
    SimpleFreecellController badController = new SimpleFreecellController(null, rd, ap);
    controller2.playGame(null, 4, 4, false);
  }

  // tests for not being able to start game because of invalid cascades
  @Test
  public void testCouldNotStartGameCascades() {
    SimpleFreecellController badController = new SimpleFreecellController(simp, rd, ap);
    badController.playGame(generatedDeck, -1, 8, true);
    assertEquals("Could not start game.", ap.toString());
  }


  // tests for not being able to start game because of invalid opens
  @Test
  public void testCouldNotStartGameOpens() {
    SimpleFreecellController badController = new SimpleFreecellController(simp, rd, ap);
    badController.playGame(generatedDeck, 8, -20, true);
    assertEquals("Could not start game.", ap.toString());
  }

  // It should re-prompt if the source pile has a bad index given
  @Test
  public void testPlayGameInvalidSourcePileNumber() {
    Readable rd1 = new StringReader("jc 3 jc");
    SimpleFreecellController badInput = new SimpleFreecellController(simp, rd1, ap);
    badInput.playGame(simp.getDeck(), 8, 4, false);
    assertEquals(true, ap.toString().contains("Re-enter source and pile number."));
  }

  // It should re-prompt if the source pile has a bad index given
  @Test
  public void testPlayGameInvalidSourcePileNumber2() {
    Readable rd1 = new StringReader("00000 3 jc");
    SimpleFreecellController badInput = new SimpleFreecellController(simp, rd1, ap);
    badInput.playGame(simp.getDeck(), 8, 4, false);
    assertEquals(true, ap.toString().contains("Re-enter source and pile number."));
  }

  // It should re-prompt if the source pile has a bad index given
  @Test
  public void testPlayGameInvalidSourcePileNumber3() {
    Readable rd1 = new StringReader("ci sono molte pizze");
    SimpleFreecellController badInput = new SimpleFreecellController(simp, rd1, ap);
    badInput.playGame(simp.getDeck(), 8, 4, false);
    assertEquals(true, ap.toString().contains("Re-enter source and pile number."));
  }

  // It should re-prompt if the source pile has a bad index given
  @Test
  public void testInvalidCardIndex() {
    Readable rd1 = new StringReader("O1 jj o4");
    SimpleFreecellController badInput = new SimpleFreecellController(simp, rd1, ap);
    badInput.playGame(simp.getDeck(), 8, 4, false);
    assertEquals(true, ap.toString().contains("Re-enter the card index."));
  }

  // It should re-prompt if the source pile has a bad index given
  @Test
  public void testInvalidCardIndex2() {
    Readable rd1 = new StringReader("c1 .01 o4");
    SimpleFreecellController badInput = new SimpleFreecellController(simp, rd1, ap);
    badInput.playGame(simp.getDeck(), 8, 4, false);
    assertEquals(true, ap.toString().contains("Re-enter the card index."));
  }

  // test for invalid destination
  @Test
  public void testInvalidDestination() {
    Readable rd1 = new StringReader("c1 13 j4");
    SimpleFreecellController badInput = new SimpleFreecellController(simp, rd1, ap);
    badInput.playGame(simp.getDeck(), 8, 4, false);
    assertEquals(true, ap.toString().contains("Re-enter destination and pile number."));
  }

  // test for invalid destination
  @Test
  public void testInvalidDestination2() {
    Readable rd1 = new StringReader("c1 13 1j");
    SimpleFreecellController badInput = new SimpleFreecellController(simp, rd1, ap);
    badInput.playGame(simp.getDeck(), 8, 4, false);
    assertEquals(true, ap.toString().contains("Re-enter destination and pile number."));
  }

  // test for invalid destination
  @Test
  public void testInvalidDestination3() {
    Readable rd1 = new StringReader("o1 13 oooo");
    SimpleFreecellController badInput = new SimpleFreecellController(simp, rd1, ap);
    badInput.playGame(simp.getDeck(), 8, 4, false);
    assertEquals(true, ap.toString().contains("Re-enter destination and pile number."));
  }

  // test for invalid destination
  @Test
  public void testInvalidDestination4() {
    Readable rd1 = new StringReader("C1 13 oj");
    SimpleFreecellController badInput = new SimpleFreecellController(simp, rd1, ap);
    badInput.playGame(simp.getDeck(), 8, 4, false);
    assertEquals(true, ap.toString().contains("Re-enter destination and pile number."));
  }

  // test to ensure game quit if only q given
  @Test
  public void testQuitGamePrematurely() {
    Readable rd1 = new StringReader("q");
    SimpleFreecellController controller = new SimpleFreecellController(simp, rd1, ap);
    controller.playGame(simp.getDeck(), 8, 4, false);
    assertEquals(true, ap.toString().contains("Game quit prematurely."));
  }

  // test to ensure game quit if only q given
  @Test
  public void testQuitGamePrematurelyUppercase() {
    Readable rd1 = new StringReader("Q");
    SimpleFreecellController controller = new SimpleFreecellController(simp, rd1, ap);
    controller.playGame(simp.getDeck(), 8, 4, false);
    assertEquals(true, ap.toString().contains("Game quit prematurely."));
  }

  // test to ensure game not quit if more than q given
  @Test
  public void testQuitGamePrematurely2() {
    Readable rd1 = new StringReader("qt");
    SimpleFreecellController controller = new SimpleFreecellController(simp, rd1, ap);
    controller.playGame(simp.getDeck(), 8, 4, false);
    assertEquals(false, ap.toString().contains("Game quit prematurely."));
  }

  // test to ensure game not quit if more than q given
  @Test
  public void testQuitGamePrematurely3() {
    Readable rd1 = new StringReader("fs si q");
    SimpleFreecellController controller = new SimpleFreecellController(simp, rd1, ap);
    controller.playGame(simp.getDeck(), 8, 4, false);
    assertEquals(true, ap.toString().contains("Game quit prematurely."));
  }

  // test to ensure game quit
  @Test
  public void testQuitGamePrematurely4() {
    Readable rd1 = new StringReader("hello q");
    SimpleFreecellController controller = new SimpleFreecellController(simp, rd1, ap);
    controller.playGame(simp.getDeck(), 8, 4, false);
    assertEquals(true, ap.toString().contains("Game quit prematurely."));
  }

  // test for valid move
  @Test
  public void testValidMove() {
    Readable rd1 = new StringReader("c1 13 o1");
    SimpleFreecellController controller = new SimpleFreecellController(simp, rd1, ap);
    controller.playGame(simp.getDeck(), 4, 4, false);
    assertEquals(true, ap.toString().contains("O1: A♦"));
  }

  // test for two valid moves in a row
  @Test
  public void testValidMove2() {
    Readable rd1 = new StringReader("c4 1 f4 c1 13 o4");
    SimpleFreecellController controller = new SimpleFreecellController(simp, rd1, ap);
    controller.playGame(simp.getDeck(), 4, 4, false);
    assertEquals(true, ap.toString().contains("F4: A♠"));
    assertEquals("F1:\n"
        + "F2:\n"
        + "F3:\n"
        + "F4:\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦, Q♦, K♦\n"
        + "C2: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n"
        + "C3: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣, 9♣, 10♣, J♣, Q♣, K♣\n"
        + "C4: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n"
        + "F1:\n"
        + "F2:\n"
        + "F3:\n"
        + "F4: A♠, \n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦, Q♦, K♦\n"
        + "C2: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n"
        + "C3: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣, 9♣, 10♣, J♣, Q♣, K♣\n"
        + "C4: 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n"
        + "F1:\n"
        + "F2:\n"
        + "F3:\n"
        + "F4: A♠, \n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4: A♦\n"
        + "C1: 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦, Q♦, K♦\n"
        + "C2: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n"
        + "C3: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣, 9♣, 10♣, J♣, Q♣, K♣\n"
        + "C4: 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n", ap.toString());
    assertEquals(true, ap.toString().contains("O4: A♦"));
  }

  // test to card moves properly
  @Test
  public void testValidMove3() {
    Readable rd1 = new StringReader("C8 6 C2");
    SimpleFreecellController controller = new SimpleFreecellController(simp, rd1, ap);
    controller.playGame(simp.getDeck(), 8, 4, false);
    assertEquals(true, ap.toString().contains("C2: A♥, 3♥, 5♥, 7♥, 9♥, J♥, K♥, Q♠"));
  }

  // tests for move to foundation pile
  @Test
  public void testValidMove4() {
    Readable rd1 = new StringReader("C1 1 F1");
    SimpleFreecellController badInput = new SimpleFreecellController(simp, rd1, ap);
    badInput.playGame(simp.getDeck(), 52, 4, false);
    assertEquals(true, ap.toString().contains("F1: A♦"));
  }

  // invalid move test where move does not follow rules
  @Test
  public void testInvalidMoveIllegalMove() {
    Readable rd1 = new StringReader("C8 6 C10");
    SimpleFreecellController badInput = new SimpleFreecellController(simp, rd1, ap);
    badInput.playGame(simp.getDeck(), 8, 4, false);
    assertEquals(true, ap.toString().contains("Invalid move. Try again."));
  }

  // invalid move test where the source pile is not valid
  @Test
  public void testInvalidMoveBadSourcePile() {
    Readable rd1 = new StringReader("C322 6 C10");
    SimpleFreecellController badInput = new SimpleFreecellController(simp, rd1, ap);
    badInput.playGame(simp.getDeck(), 8, 4, false);
    assertEquals(true, ap.toString().contains("Invalid move. Try again."));
  }

  // invalid move test for out of bounds card index
  @Test
  public void testInvalidMoveCardIndexOutOfBounds() {
    Readable rd1 = new StringReader("C322 9 C10");
    SimpleFreecellController badInput = new SimpleFreecellController(simp, rd1, ap);
    badInput.playGame(simp.getDeck(), 8, 4, false);
    assertEquals(true, ap.toString().contains("Invalid move. Try again."));
  }


  // tests relating to game over
  @Test
  public void testGameOver() {
    initData();
    controller.playGame(generatedDeck, 52, 5, false);
    simp.move(PileType.CASCADE, 0, 0, PileType.FOUNDATION, 0);
    simp.move(PileType.CASCADE, 1, 0, PileType.FOUNDATION, 1);
    simp.move(PileType.CASCADE, 2, 0, PileType.FOUNDATION, 2);
    simp.move(PileType.CASCADE, 3, 0, PileType.FOUNDATION, 3);
    simp.move(PileType.CASCADE, 4, 0, PileType.FOUNDATION, 0);
    simp.move(PileType.CASCADE, 5, 0, PileType.FOUNDATION, 1);
    simp.move(PileType.CASCADE, 6, 0, PileType.FOUNDATION, 2);
    simp.move(PileType.CASCADE, 7, 0, PileType.FOUNDATION, 3);
    simp.move(PileType.CASCADE, 8, 0, PileType.FOUNDATION, 0);
    simp.move(PileType.CASCADE, 9, 0, PileType.FOUNDATION, 1);
    simp.move(PileType.CASCADE, 10, 0, PileType.FOUNDATION, 2);
    simp.move(PileType.CASCADE, 11, 0, PileType.FOUNDATION, 3);
    simp.move(PileType.CASCADE, 12, 0, PileType.FOUNDATION, 0);

    simp.move(PileType.CASCADE, 13, 0, PileType.FOUNDATION, 1);
    simp.move(PileType.CASCADE, 14, 0, PileType.FOUNDATION, 2);
    simp.move(PileType.CASCADE, 15, 0, PileType.FOUNDATION, 3);
    simp.move(PileType.CASCADE, 16, 0, PileType.FOUNDATION, 0);
    simp.move(PileType.CASCADE, 17, 0, PileType.FOUNDATION, 1);
    simp.move(PileType.CASCADE, 18, 0, PileType.FOUNDATION, 2);
    simp.move(PileType.CASCADE, 19, 0, PileType.FOUNDATION, 3);
    simp.move(PileType.CASCADE, 20, 0, PileType.FOUNDATION, 0);
    simp.move(PileType.CASCADE, 21, 0, PileType.FOUNDATION, 1);
    simp.move(PileType.CASCADE, 22, 0, PileType.FOUNDATION, 2);
    simp.move(PileType.CASCADE, 23, 0, PileType.FOUNDATION, 3);
    simp.move(PileType.CASCADE, 24, 0, PileType.FOUNDATION, 0);
    simp.move(PileType.CASCADE, 25, 0, PileType.FOUNDATION, 1);

    simp.move(PileType.CASCADE, 26, 0, PileType.FOUNDATION, 2);
    simp.move(PileType.CASCADE, 27, 0, PileType.FOUNDATION, 3);
    simp.move(PileType.CASCADE, 28, 0, PileType.FOUNDATION, 0);
    simp.move(PileType.CASCADE, 29, 0, PileType.FOUNDATION, 1);
    simp.move(PileType.CASCADE, 30, 0, PileType.FOUNDATION, 2);
    simp.move(PileType.CASCADE, 31, 0, PileType.FOUNDATION, 3);
    simp.move(PileType.CASCADE, 32, 0, PileType.FOUNDATION, 0);
    simp.move(PileType.CASCADE, 33, 0, PileType.FOUNDATION, 1);
    simp.move(PileType.CASCADE, 34, 0, PileType.FOUNDATION, 2);
    simp.move(PileType.CASCADE, 35, 0, PileType.FOUNDATION, 3);
    simp.move(PileType.CASCADE, 36, 0, PileType.FOUNDATION, 0);
    simp.move(PileType.CASCADE, 37, 0, PileType.FOUNDATION, 1);
    simp.move(PileType.CASCADE, 38, 0, PileType.FOUNDATION, 2);

    simp.move(PileType.CASCADE, 39, 0, PileType.FOUNDATION, 3);
    simp.move(PileType.CASCADE, 40, 0, PileType.FOUNDATION, 0);
    simp.move(PileType.CASCADE, 41, 0, PileType.FOUNDATION, 1);
    simp.move(PileType.CASCADE, 42, 0, PileType.FOUNDATION, 2);
    simp.move(PileType.CASCADE, 43, 0, PileType.FOUNDATION, 3);
    simp.move(PileType.CASCADE, 44, 0, PileType.FOUNDATION, 0);
    simp.move(PileType.CASCADE, 45, 0, PileType.FOUNDATION, 1);
    simp.move(PileType.CASCADE, 46, 0, PileType.FOUNDATION, 2);
    simp.move(PileType.CASCADE, 47, 0, PileType.FOUNDATION, 3);
    simp.move(PileType.CASCADE, 48, 0, PileType.FOUNDATION, 0);
    simp.move(PileType.CASCADE, 49, 0, PileType.FOUNDATION, 1);
    simp.move(PileType.CASCADE, 50, 0, PileType.FOUNDATION, 2);
    assertEquals(false, simp.isGameOver());
    assertEquals(false, ap.toString().contains("Game over."));
    simp.move(PileType.CASCADE, 51, 0, PileType.FOUNDATION, 3);

    assertEquals(true, simp.isGameOver());
    assertEquals(true, ap.toString().contains("Game over."));
  }

  // test to print out whole string of game quit
  @Test
  public void testQuitGame() {
    Appendable out = new StringBuilder();
    Readable in = new StringReader("q");
    FreecellController controllerQ = new SimpleFreecellController(simp, in, out);
    controllerQ.playGame(generatedDeck, 10, 5, false);
    assertEquals("F1:\n"
        + "F2:\n"
        + "F3:\n"
        + "F4:\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "O5:\n"
        + "C1: A♦, 3♣, 6♦, 8♣, J♦, K♣\n"
        + "C2: A♥, 3♠, 6♥, 8♠, J♥, K♠\n"
        + "C3: A♣, 4♦, 6♣, 9♦, J♣\n"
        + "C4: A♠, 4♥, 6♠, 9♥, J♠\n"
        + "C5: 2♦, 4♣, 7♦, 9♣, Q♦\n"
        + "C6: 2♥, 4♠, 7♥, 9♠, Q♥\n"
        + "C7: 2♣, 5♦, 7♣, 10♦, Q♣\n"
        + "C8: 2♠, 5♥, 7♠, 10♥, Q♠\n"
        + "C9: 3♦, 5♣, 8♦, 10♣, K♦\n"
        + "C10: 3♥, 5♠, 8♥, 10♠, K♥\n"
        + "Game quit prematurely.", out.toString());
  }


}
