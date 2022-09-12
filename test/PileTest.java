import static org.junit.Assert.assertEquals;

import cs3500.freecell.model.PileType;
import cs3500.freecell.model.hw02.Card;
import cs3500.freecell.model.hw02.ICard;
import cs3500.freecell.model.hw02.Pile;
import cs3500.freecell.model.hw02.SimpleFreecellModel;
import cs3500.freecell.model.hw02.Suit;
import cs3500.freecell.model.hw02.Value;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

/**
 * Class to test Pile class.
 */
public class PileTest {

  private SimpleFreecellModel simp;
  private ICard aceOfDiamonds;
  private ICard threeOfSpades;
  private ICard fiveOfHearts;
  private ICard sixOfHearts;
  private ICard sevenOfClubs;
  private ICard eightOfClubs;
  private List<ICard> generatedDeck;
  private ArrayList<ICard> samplePile;
  private ArrayList<ICard> samplePile2;
  private ArrayList<ICard> samplePile3;
  private ArrayList<ICard> samplePile4;
  private ArrayList<ICard> samplePile5;
  private Pile fPile;
  private Pile oPile;
  private Pile cPile;
  private Pile cPile2;

  // initializes the data
  @Before
  public void initData() {
    simp = new SimpleFreecellModel();
    aceOfDiamonds = new Card(Value.ACE, Suit.DIAMOND);
    threeOfSpades = new Card(Value.THREE, Suit.SPADE);
    fiveOfHearts = new Card(Value.FIVE, Suit.HEART);
    sixOfHearts = new Card(Value.SIX, Suit.HEART);
    sevenOfClubs = new Card(Value.SEVEN, Suit.CLUB);
    eightOfClubs = new Card(Value.EIGHT, Suit.CLUB);
    generatedDeck = simp.getDeck();
    samplePile = new ArrayList<>();
    samplePile2 = new ArrayList<>();
    samplePile3 = new ArrayList<>();
    samplePile4 = new ArrayList<>();
    samplePile5 = new ArrayList<>();
    samplePile2.add(aceOfDiamonds);
    samplePile3.add(aceOfDiamonds);
    samplePile3.add(threeOfSpades);
    samplePile3.add(fiveOfHearts);
    samplePile3.add(sevenOfClubs);
    samplePile4.add(sixOfHearts);
    samplePile5.add(eightOfClubs);
    fPile = new Pile(PileType.FOUNDATION, samplePile);
    oPile = new Pile(PileType.OPEN, samplePile5);
    cPile = new Pile(PileType.CASCADE, samplePile3);
    cPile2 = new Pile(PileType.CASCADE, samplePile4);

  }

  // tests for getPileType
  @Test
  public void testGetPileType() {
    initData();
    simp.startGame(generatedDeck, 8, 4, true);
    assertEquals(PileType.FOUNDATION, fPile.getPileType());
    assertEquals(PileType.OPEN, oPile.getPileType());
    assertEquals(PileType.CASCADE, cPile.getPileType());
    assertEquals(PileType.CASCADE, cPile2.getPileType());
  }

  // tests for getCardFromPile
  @Test
  public void testGetCardFromPile() {
    initData();
    simp.startGame(generatedDeck, 8, 4, true);
    assertEquals(new ArrayList<>(), fPile.getCardsFromPile());
    assertEquals(samplePile5, oPile.getCardsFromPile());
    assertEquals(samplePile3, cPile.getCardsFromPile());
    assertEquals(samplePile4, cPile2.getCardsFromPile());
  }

  // tests for removeCardFromPile
  @Test
  public void testRemoveCardFromPile() {
    initData();
    simp.startGame(generatedDeck, 8, 4, true);
    fPile.addCardToPile(aceOfDiamonds);
    fPile.removeCardFromPile(aceOfDiamonds);
    assertEquals(new ArrayList<>(), fPile.getCardsFromPile());
    oPile.removeCardFromPile(eightOfClubs);
    assertEquals(new ArrayList<>(), oPile.getCardsFromPile());
    cPile.removeCardFromPile(threeOfSpades);
    samplePile.add(aceOfDiamonds);
    samplePile.add(fiveOfHearts);
    samplePile.add(sevenOfClubs);
    assertEquals(samplePile, samplePile3);
  }

  // tests for addCardToPile
  @Test
  public void testAddCardToPile() {
    initData();
    simp.startGame(generatedDeck, 8, 4, true);
    samplePile.add(sixOfHearts);
    assertEquals(samplePile, samplePile4);
    samplePile.clear();
    samplePile.add(eightOfClubs);
    assertEquals(samplePile, samplePile5);
    samplePile.clear();
    samplePile.add(aceOfDiamonds);
    assertEquals(samplePile, samplePile2);
  }

  // test for isMoveLegal
  @Test
  public void testIsMoveLegal() {
    initData();
    simp.startGame(generatedDeck, 8, 4, true);
    assertEquals(true, fPile.isMoveLegal(aceOfDiamonds));
    assertEquals(true, cPile.isMoveLegal(sixOfHearts));
    assertEquals(false, oPile.isMoveLegal(threeOfSpades));
    samplePile5.remove(eightOfClubs);
    assertEquals(true, oPile.isMoveLegal(threeOfSpades));

  }

  // test for isFoundationMoveLegal
  @Test
  public void testIsFoundationMoveLegal() {
    initData();
    simp.startGame(generatedDeck, 8, 4, true);
    assertEquals(true, fPile.isFoundationMoveLegal(aceOfDiamonds));
    samplePile.add(aceOfDiamonds);
    assertEquals(false, fPile.isFoundationMoveLegal(aceOfDiamonds));
    assertEquals(false, fPile.isFoundationMoveLegal(threeOfSpades));
  }

  // test for isCascadeMoveLegal
  @Test
  public void testIsCascadeMoveLegal() {
    initData();
    simp.startGame(generatedDeck, 8, 4, true);
    assertEquals(true, cPile.isCascadeMoveLegal(sixOfHearts));
    assertEquals(false, cPile.isCascadeMoveLegal(eightOfClubs));
    assertEquals(false, cPile2.isCascadeMoveLegal(sevenOfClubs));
  }

}
