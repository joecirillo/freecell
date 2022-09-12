import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import cs3500.freecell.controller.UserCommand;
import cs3500.freecell.model.PileType;
import org.junit.Before;
import org.junit.Test;

/**
 * Testing class for UserCommand.
 */
public class UserCommandTest {

  private PileType source;
  private int sourcePileNum;
  private int cardIndex;
  private PileType dest;
  private int destPileNum;
  private PileType pType;
  private PileType pType2;
  private PileType pType3;
  private UserCommand userCommand;
  private UserCommand userCommand2;
  private UserCommand userCommand3;


  // initializes the data
  @Before
  public void initData() {
    source = null;
    sourcePileNum = -13;
    cardIndex = -13;
    dest = null;
    destPileNum = -13;
    pType = PileType.CASCADE;
    pType2 = PileType.OPEN;
    pType3 = PileType.FOUNDATION;
    userCommand = new UserCommand(pType, 1, 2, pType, 3);
    userCommand2 = new UserCommand(pType2, 2, 4, pType3, 1);
    userCommand3 = new UserCommand(pType2, 4, 4, pType3, 4);
  }

  // tests for throwing exceptions for invalid constructors
  @Test(expected = IllegalArgumentException.class)
  public void testUserCommandConstructor() {
    new UserCommand(source, sourcePileNum, cardIndex, dest, destPileNum);
    new UserCommand(source, 3, 5, dest, destPileNum);
  }

  // tests for getting the type of source pile
  @Test
  public void testGetSourceType() {
    assertEquals(pType, userCommand.getSourceType());
    assertEquals(pType2, userCommand2.getSourceType());
    assertNotEquals(pType3, userCommand.getSourceType());
  }

  // tests for getting the source pile number
  @Test
  public void testGetSourcePileNumber() {
    assertEquals(1, userCommand.getSourcePileNum());
    assertEquals(2, userCommand2.getSourcePileNum());
    assertEquals(4, userCommand3.getSourcePileNum());
  }

  // tests for getting the type of destination pile
  @Test
  public void testGetDestType() {
    assertEquals(PileType.CASCADE, userCommand.getDestType());
    assertEquals(PileType.FOUNDATION, userCommand2.getDestType());
    assertEquals(PileType.FOUNDATION, userCommand3.getDestType());
  }

  // tests for getting the destination pile number
  @Test
  public void testDestCardNum() {
    assertEquals(3, userCommand.getDestPileNum());
    assertEquals(1, userCommand2.getDestPileNum());
    assertEquals(4, userCommand3.getDestPileNum());
  }

  // tests for getting the card index in a pile
  @Test
  public void testGetCardIndex() {
    assertEquals(2, userCommand.getCardIndex());
    assertEquals(4, userCommand2.getCardIndex());
    assertEquals(4, userCommand3.getCardIndex());
  }

  // tests for converting the type of source pile
  @Test
  public void testConvertSource() {
    assertEquals(userCommand, userCommand.convertSource(PileType.CASCADE));
    assertNotEquals(new UserCommand(PileType.OPEN, 2, 4, pType3, 1),
        userCommand2.convertSource(PileType.FOUNDATION));
    assertNotEquals(new UserCommand(PileType.CASCADE, 4, 4, pType3, 4),
        userCommand3.convertSource(PileType.CASCADE));
  }

  // tests for converting the destination pile to a zero based index for the program to read
  @Test
  public void testConvertDest() {
    assertEquals(userCommand, userCommand.convertSource(PileType.CASCADE));
    assertNotEquals(new UserCommand(PileType.FOUNDATION, 2, 4, pType3, 1),
        userCommand2.convertSource(PileType.FOUNDATION));
    assertNotEquals(new UserCommand(PileType.OPEN, 4, 4, pType3, 4),
        userCommand3.convertSource(PileType.CASCADE));
  }

  // tests for converting the source pile to a zero based index for the program to read
  @Test
  public void testConvertSourcePileNum() {
    userCommand.convertSourcePileNum(5);
    userCommand2.convertSourcePileNum(4);
    userCommand3.convertSourcePileNum(3);
    assertEquals(4, userCommand.getSourcePileNum());
    assertEquals(3, userCommand2.getSourcePileNum());
    assertEquals(2, userCommand3.getSourcePileNum());
  }

  // tests for converting the destination pile card number to a zero based index
  // for the program to read
  @Test
  public void testConvertDestPileNum() {
    userCommand.convertDestPileNum(7);
    userCommand2.convertDestPileNum(8);
    userCommand3.convertDestPileNum(9);
    assertEquals(6, userCommand.getDestPileNum());
    assertEquals(7, userCommand2.getDestPileNum());
    assertEquals(8, userCommand3.getDestPileNum());
  }

  // tests for converting the card index to a zero based index for the program to read
  @Test
  public void testConvertCardIndex() {
    userCommand.convertCardIndex(1);
    userCommand2.convertCardIndex(2);
    userCommand3.convertCardIndex(3);
    assertEquals(0, userCommand.getCardIndex());
    assertEquals(1, userCommand2.getCardIndex());
    assertEquals(2, userCommand3.getCardIndex());
  }


  // tests for whether a converted user command is valid
  @Test
  public void testIsValidCommand() {
    assertEquals(true, userCommand.isValidCommand());
    userCommand.convertCardIndex(0);
    assertEquals(false, userCommand.isValidCommand());
    userCommand.convertCardIndex(1);
    assertEquals(true, userCommand.isValidCommand());
  }

  // tests for how many converted user commands there are
  @Test
  public void testNumValidCommands() {
    assertEquals(5, userCommand.numValidCommands());
    userCommand.convertCardIndex(0);
    userCommand.convertDestPileNum(-13);
    assertEquals(3, userCommand.numValidCommands());
    userCommand.convertDest(null);
    userCommand.convertSource(null);
    userCommand.convertSourcePileNum(0);
    assertEquals(0, userCommand.numValidCommands());
  }

}
