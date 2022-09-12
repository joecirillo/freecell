
import cs3500.freecell.controller.SimpleFreecellController;
import cs3500.freecell.model.PileType;
import cs3500.freecell.model.hw02.ICard;
import cs3500.freecell.model.hw02.SimpleFreecellModel;
import cs3500.freecell.model.hw04.MultiMoveFreecellModel;
import cs3500.freecell.view.FreecellTextView;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.StringReader;
import java.util.Collections;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

/**
 * Testing class for FreecellView, which is the view portion of the MCV.
 */
public class FreecellTextViewTest {

  private Appendable ap;
  private SimpleFreecellModel simp;
  private FreecellTextView free;
  private List<ICard> generatedDeck;

  // initializes the data
  @Before
  public void initData() {
    ap = new StringBuilder();
    simp = new SimpleFreecellModel();
    free = new FreecellTextView(simp, ap);
    generatedDeck = simp.getDeck();
  }


  // tests for null arguments to throw exceptions
  @Test(expected = IllegalArgumentException.class)
  public void testNullArguments() {
    FreecellTextView badFreecellAP = new FreecellTextView(null, ap);
    FreecellTextView badFreecellRD = new FreecellTextView(simp, null);
  }

  // test for toString
  @Test
  public void testToString() {
    Appendable ap = new StringBuilder();
    SimpleFreecellModel simp = new SimpleFreecellModel();
    FreecellTextView free = new FreecellTextView(simp);
    assertEquals("", free.toString());
  }


  // test for toString
  @Test
  public void testToStringGameStarted() {
    initData();
    simp.startGame(generatedDeck, 6, 4, false);
    assertEquals("F1:\n"
        + "F2:\n"
        + "F3:\n"
        + "F4:\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: A♦, 2♣, 4♦, 5♣, 7♦, 8♣, 10♦, J♣, K♦\n"
        + "C2: A♥, 2♠, 4♥, 5♠, 7♥, 8♠, 10♥, J♠, K♥\n"
        + "C3: A♣, 3♦, 4♣, 6♦, 7♣, 9♦, 10♣, Q♦, K♣\n"
        + "C4: A♠, 3♥, 4♠, 6♥, 7♠, 9♥, 10♠, Q♥, K♠\n"
        + "C5: 2♦, 3♣, 5♦, 6♣, 8♦, 9♣, J♦, Q♣\n"
        + "C6: 2♥, 3♠, 5♥, 6♠, 8♥, 9♠, J♥, Q♠", free.toString());
  }

  // test for toString with a multimovefreecell model
  @Test
  public void testToStringMultiMove() {
    MultiMoveFreecellModel multi = new MultiMoveFreecellModel();
    Collections.reverse(generatedDeck);
    multi.startGame(generatedDeck, 26, 6, false);
    multi.move(PileType.CASCADE, 8, 1, PileType.CASCADE, 2);
    multi.move(PileType.CASCADE, 11, 1, PileType.CASCADE, 2);
    multi.move(PileType.CASCADE, 17, 1, PileType.CASCADE, 2);
    multi.move(PileType.CASCADE, 2, 1, PileType.CASCADE, 0);
    FreecellTextView free = new FreecellTextView(multi);
    assertEquals("F1:\n"
        + "F2:\n"
        + "F3:\n"
        + "F4:\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "O5:\n"
        + "O6:\n"
        + "C1: K♠, 7♥, 6♠, 5♥, 4♣, 3♦\n"
        + "C2: K♣, 7♦\n"
        + "C3: K♥\n"
        + "C4: K♦, 6♣\n"
        + "C5: Q♠, 6♥\n"
        + "C6: Q♣, 6♦\n"
        + "C7: Q♥, 5♠\n"
        + "C8: Q♦, 5♣\n"
        + "C9: J♠\n"
        + "C10: J♣, 5♦\n"
        + "C11: J♥, 4♠\n"
        + "C12: J♦\n"
        + "C13: 10♠, 4♥\n"
        + "C14: 10♣, 4♦\n"
        + "C15: 10♥, 3♠\n"
        + "C16: 10♦, 3♣\n"
        + "C17: 9♠, 3♥\n"
        + "C18: 9♣\n"
        + "C19: 9♥, 2♠\n"
        + "C20: 9♦, 2♣\n"
        + "C21: 8♠, 2♥\n"
        + "C22: 8♣, 2♦\n"
        + "C23: 8♥, A♠\n"
        + "C24: 8♦, A♣\n"
        + "C25: 7♠, A♥\n"
        + "C26: 7♣, A♦", free.toString());
  }


  // test for toString with a simplefreecell model
  @Test
  public void testToStringMoveMade() {
    initData();
    simp.startGame(generatedDeck, 4, 4, false);
    simp.move(PileType.CASCADE, 1, 11, PileType.CASCADE, 3);
    FreecellTextView free = new FreecellTextView(simp);
    assertEquals("F1:\n"
        + "F2:\n"
        + "F3:\n"
        + "F4:\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦, Q♦, K♦\n"
        + "C2: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, K♥\n"
        + "C3: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣, 9♣, 10♣, J♣, Q♣, K♣\n"
        + "C4: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠, Q♥", free.toString());
  }

  // test for toString for open move
  @Test
  public void testToStringOpenMoveMade() {
    initData();
    simp.startGame(generatedDeck, 4, 4, false);
    simp.move(PileType.CASCADE, 1, 12, PileType.OPEN, 1);
    FreecellTextView free = new FreecellTextView(simp);
    assertEquals("F1:\n"
        + "F2:\n"
        + "F3:\n"
        + "F4:\n"
        + "O1:\n"
        + "O2: K♥\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦, Q♦, K♦\n"
        + "C2: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥\n"
        + "C3: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣, 9♣, 10♣, J♣, Q♣, K♣\n"
        + "C4: A♠, 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠", free.toString());
  }

  // test for rendering a message
  @Test
  public void testRenderEmptyMessage() throws IOException {
    simp.toString();
    free.renderMessage("");
    assertEquals("", ap.toString());
  }

  // test for rendering a message
  @Test
  public void testRenderGameQuitPrematurely() throws IOException {
    simp.toString();
    free.renderMessage("Game quit prematurely.");
    assertEquals("Game quit prematurely.", ap.toString());
  }

  // test for rendering a message
  @Test
  public void testGameOver() throws IOException {
    simp.toString();
    free.renderMessage("Game over.");
    assertEquals("Game over.", ap.toString());
  }

  // test for rendering a message
  @Test
  public void testReEnterCardIndex() throws IOException {
    simp.toString();
    free.renderMessage("Re-enter card index.");
    assertEquals("Re-enter card index.", ap.toString());
  }

  // test for rendering board before a game is being played
  @Test
  public void testRenderBoardBeforePlayGame() throws IOException {
    simp.toString();
    free.renderBoard();
    assertEquals("\n", ap.toString());
  }

  @Test
  public void testRenderBoardReEnterSource() throws IOException {
    Readable rd = new StringReader("hi");
    SimpleFreecellController controller = new SimpleFreecellController(simp, rd, ap);
    controller.playGame(generatedDeck, 4, 4, false);
    // tests an invalid input for the source pile
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
        + "Re-enter source and pile number.\n", ap.toString());
  }

  // test for a valid move from a cascade pile to an open pile
  @Test
  public void testRenderBoardMoveCard() throws IOException {
    Readable correctInput = new StringReader("c4 13 o1");
    SimpleFreecellController controller3 = new SimpleFreecellController(simp, correctInput, ap);
    controller3.playGame(generatedDeck, 4, 4, false);
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
        + "F4:\n"
        + "O1: A♠\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: A♦, 2♦, 3♦, 4♦, 5♦, 6♦, 7♦, 8♦, 9♦, 10♦, J♦, Q♦, K♦\n"
        + "C2: A♥, 2♥, 3♥, 4♥, 5♥, 6♥, 7♥, 8♥, 9♥, 10♥, J♥, Q♥, K♥\n"
        + "C3: A♣, 2♣, 3♣, 4♣, 5♣, 6♣, 7♣, 8♣, 9♣, 10♣, J♣, Q♣, K♣\n"
        + "C4: 2♠, 3♠, 4♠, 5♠, 6♠, 7♠, 8♠, 9♠, 10♠, J♠, Q♠, K♠\n", ap.toString());
  }

  // test for a valid move from a cascade pile to an open pile
  @Test
  public void testRenderBoardInvalidMove() throws IOException {
    Readable partialInput = new StringReader("c1 3 c3");
    SimpleFreecellController controller2 = new SimpleFreecellController(simp, partialInput, ap);
    controller2.playGame(generatedDeck, 4, 4, false);
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
        + "Invalid move. Try again.\n", ap.toString());
  }

  // test for a valid move from a cascade pile to an open pile
  @Test
  public void testRenderBoardMoveCascades() throws IOException {
    Readable moveCascadeToCascade = new StringReader("c5 6 c4");
    SimpleFreecellController controller4 = new SimpleFreecellController(simp, moveCascadeToCascade,
        ap);
    controller4.playGame(generatedDeck, 8, 4, false);
    assertEquals("F1:\n"
        + "F2:\n"
        + "F3:\n"
        + "F4:\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: A♦, 3♦, 5♦, 7♦, 9♦, J♦, K♦\n"
        + "C2: A♥, 3♥, 5♥, 7♥, 9♥, J♥, K♥\n"
        + "C3: A♣, 3♣, 5♣, 7♣, 9♣, J♣, K♣\n"
        + "C4: A♠, 3♠, 5♠, 7♠, 9♠, J♠, K♠\n"
        + "C5: 2♦, 4♦, 6♦, 8♦, 10♦, Q♦\n"
        + "C6: 2♥, 4♥, 6♥, 8♥, 10♥, Q♥\n"
        + "C7: 2♣, 4♣, 6♣, 8♣, 10♣, Q♣\n"
        + "C8: 2♠, 4♠, 6♠, 8♠, 10♠, Q♠\n"
        + "F1:\n"
        + "F2:\n"
        + "F3:\n"
        + "F4:\n"
        + "O1:\n"
        + "O2:\n"
        + "O3:\n"
        + "O4:\n"
        + "C1: A♦, 3♦, 5♦, 7♦, 9♦, J♦, K♦\n"
        + "C2: A♥, 3♥, 5♥, 7♥, 9♥, J♥, K♥\n"
        + "C3: A♣, 3♣, 5♣, 7♣, 9♣, J♣, K♣\n"
        + "C4: A♠, 3♠, 5♠, 7♠, 9♠, J♠, K♠, Q♦\n"
        + "C5: 2♦, 4♦, 6♦, 8♦, 10♦\n"
        + "C6: 2♥, 4♥, 6♥, 8♥, 10♥, Q♥\n"
        + "C7: 2♣, 4♣, 6♣, 8♣, 10♣, Q♣\n"
        + "C8: 2♠, 4♠, 6♠, 8♠, 10♠, Q♠\n", ap.toString());
  }

  //


}
