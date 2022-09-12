package cs3500.freecell.model.hw02;

import cs3500.freecell.model.FreecellModel;
import cs3500.freecell.model.PileType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


/**
 * SimpleFreecellModel is an implementation of a Freecell game. ICard is used to implement it.
 * Freecell has a deck of 52 unique cards with three different piles: Cascade, Open, and Foundation.
 * The three piles are represented in the PileType enum. There is an option to shuffle the deck
 * before the game starts. SimpleFreecellModel is the class that contains the methods to create a
 * deck and start a game.
 */
public class SimpleFreecellModel implements FreecellModel<ICard> {

  // has the game started?
  protected boolean hasGameStarted;
  // list of all the cascade piles
  private List<Pile> allCascadePiles;
  // list of all the foundation piles
  private List<Pile> allFoundationPiles;
  // list of the four open piles
  private List<Pile> allOpenPiles;


  /**
   * Constructed FreecellModel object.
   */
  public SimpleFreecellModel() {
    this.hasGameStarted = false;
    this.allCascadePiles = new ArrayList<Pile>();
    this.allFoundationPiles = new ArrayList<Pile>();
    this.allOpenPiles = new ArrayList<Pile>();
  }

  @Override
  public List<ICard> getDeck() {
    Value[] cardValues = Value.values();
    Suit[] suitValues = Suit.values();
    List<ICard> iteratedDeck = new ArrayList<>();
    // takes all the enumerated values for suit and value enumerations and iterates over them.
    // creates a full deck of 52 cards
    for (Value value : cardValues) {
      for (Suit suit : suitValues) {
        iteratedDeck.add(new Card(value, suit));
      }
    }
    return iteratedDeck;
  }


  @Override
  public void startGame(List<ICard> deck, int numCascadePiles, int numOpenPiles, boolean shuffle)
      throws IllegalArgumentException {
    allCascadePiles.clear();
    allOpenPiles.clear();
    allFoundationPiles.clear();
    if ((numOpenPiles < 1) || (numCascadePiles < 4)) {
      throw new IllegalArgumentException("Number of chosen piles is out of range!");
    } else if (deck == null || deck.size() != 52) {
      throw new IllegalArgumentException("Invalid deck size!");
    } else if (!isValidDeck(deck)) {
      throw new IllegalArgumentException("Invalid deck!");
    } else {
      // foundation piles are for the four suits, so there will always be 4 piles.
      for (int i = 0; i < 4; i++) {
        allFoundationPiles.add(new Pile(PileType.FOUNDATION, new ArrayList<ICard>()));
      }
      // open piles can vary between 1 and 4.
      for (int i = 0; i < numOpenPiles; i++) {
        allOpenPiles.add(new Pile(PileType.OPEN, new ArrayList<ICard>()));
      }
      // cascade piles can vary between 4 and 8. The cards will be shuffled out into these piles
      // initially.
      for (int i = 0; i < numCascadePiles; i++) {
        allCascadePiles.add(new Pile(PileType.CASCADE, new ArrayList<ICard>()));
      }
      if (shuffle) {
        Collections.shuffle(deck);
      }
      // for-each loop will help shuffle "round robin" by resetting at 0 (back to the first pile)
      // every time the end of the list of piles has been reached.
      int i = 0;
      for (ICard card : (ArrayList<ICard>) deck) {
        if (i == allCascadePiles.size()) {
          i = 0;
        }
        allCascadePiles.get(i).addCardToPile(card);
        i++;
      }
      this.hasGameStarted = true;
    }

  }

  /**
   * Is the deck valid?.
   *
   * @param deck is the given deck for a new game.
   * @return whether the deck is a legal 52 card deck with no duplicates.
   */
  private boolean isValidDeck(List deck) {
    if (deck == null) {
      throw new IllegalArgumentException("Invalid deck!");
    }
    ArrayList<String> correctDeck = new ArrayList<>(Arrays.asList(
        "A♦", "2♦", "3♦", "4♦", "5♦", "6♦", "7♦", "8♦", "9♦", "10♦", "J♦", "Q♦", "K♦",
        "A♥", "2♥", "3♥", "4♥", "5♥", "6♥", "7♥", "8♥", "9♥", "10♥", "J♥", "Q♥", "K♥",
        "A♠", "2♠", "3♠", "4♠", "5♠", "6♠", "7♠", "8♠", "9♠", "10♠", "J♠", "Q♠", "K♠",
        "A♣", "2♣", "3♣", "4♣", "5♣", "6♣", "7♣", "8♣", "9♣", "10♣", "J♣", "Q♣", "K♣"));
    List<ICard> cardList = deck;
    ArrayList<String> generatedStringList = new ArrayList<>();
    for (ICard card : cardList) {
      if (card == null) {
        throw new IllegalArgumentException("Invalid card!");
      }
      generatedStringList.add(card.toString());
    }
    return generatedStringList.containsAll(correctDeck) && generatedStringList.size() == correctDeck
        .size();
  }

  @Override
  public void move(PileType source, int pileNumber, int cardIndex, PileType destination,
      int destPileNumber) throws IllegalArgumentException, IllegalStateException {
    // local variables
    Pile sourcePile;
    Pile destinationPile;

    // passes off to helper method
    if (!isPossibleMove(source, pileNumber, cardIndex, destination, destPileNumber)) {
      throw new IllegalArgumentException("Invalid move attempted!");
    }

    sourcePile = getPileType(source).get(pileNumber);
    destinationPile = getPileType(destination).get(destPileNumber);
    ICard movingCard = sourcePile.getCardsFromPile().get(cardIndex);
    if (!destinationPile.isMoveLegal(movingCard)) {
      throw new IllegalArgumentException("Illegal move attempted!");
    } else {
      sourcePile.removeCardFromPile(movingCard);
      destinationPile.addCardToPile(movingCard);
    }

  }

  protected List<Pile> getPileType(PileType pType) {
    if (pType == PileType.FOUNDATION) {
      return allFoundationPiles;
    } else if (pType == PileType.CASCADE) {
      return allCascadePiles;
    } else {
      return allOpenPiles;
    }
  }

  /**
   * Is selected move a legal move in the game?.
   *
   * @param source         the type of the source pile see @link{PileType}
   * @param pileNumber     the pile number of the given type, starting at 0
   * @param cardIndex      the index of the card to be moved from the source pile, starting at 0
   * @param destination    the type of the destination pile (see
   * @param destPileNumber the pile number of the given type, starting at 0
   */
  protected boolean isPossibleMove(PileType source, int pileNumber, int cardIndex,
      PileType destination,
      int destPileNumber) {
    if (source == PileType.FOUNDATION) {
      return false;
    } else {
      switch (source) {
        case OPEN:
          if (pileNumber >= getNumOpenPiles() || pileNumber < 0) {
            return false;
          }
          getNumCardsInOpenPile(pileNumber);
          break;
        case FOUNDATION:
          if (pileNumber >= 4 || pileNumber < 0) {
            return false;
          }
          getNumCardsInFoundationPile(pileNumber);
          getFoundationCardAt(pileNumber, cardIndex);
          break;
        default:
          if (pileNumber >= getNumCascadePiles() || pileNumber < 0) {
            return false;
          }
          getNumCardsInCascadePile(pileNumber);
          getCascadeCardAt(pileNumber, cardIndex);
      }
      switch (destination) {
        case OPEN:
          if (destPileNumber >= getNumOpenPiles() || pileNumber < 0) {
            return false;
          }
          break;
        case FOUNDATION:
          if (destPileNumber >= 4 || pileNumber < 0) {
            return false;
          }
          break;
        default:
          if (destPileNumber >= getNumCascadePiles() || pileNumber < 0) {
            return false;
          }
      }
    }

    return true;
  }


  @Override
  public boolean isGameOver() {
    return allFoundationPiles.get(0).getCardsFromPile().size() == 13
        && allFoundationPiles.get(1).getCardsFromPile().size() == 13
        && allFoundationPiles.get(2).getCardsFromPile().size() == 13
        && allFoundationPiles.get(3).getCardsFromPile().size() == 13;
  }

  @Override
  public int getNumCardsInFoundationPile(int index)
      throws IllegalArgumentException, IllegalStateException {
    if (!this.hasGameStarted) {
      throw new IllegalStateException("Game has not started!");
    } else if (index > allFoundationPiles.size() - 1 || index < 0) {
      throw new IllegalArgumentException("Index is invalid!");
    } else {
      return allFoundationPiles.get(index).getCardsFromPile().size();
    }
  }

  @Override
  public int getNumCascadePiles() {
    if (!this.hasGameStarted) {
      return -1;
    } else {
      return allCascadePiles.size();
    }
  }

  @Override
  public int getNumCardsInCascadePile(int index)
      throws IllegalArgumentException, IllegalStateException {
    if (!this.hasGameStarted) {
      throw new IllegalStateException("Game has not started!");
    } else if (index > allCascadePiles.size() - 1 || index < 0) {
      throw new IllegalArgumentException("Index is invalid!");
    } else {
      return allCascadePiles.get(index).getCardsFromPile().size();
    }
  }

  @Override
  public int getNumCardsInOpenPile(int index)
      throws IllegalArgumentException, IllegalStateException {
    if (!this.hasGameStarted) {
      throw new IllegalStateException("Game has not started!");
    } else if (index > allOpenPiles.size() - 1 || index < 0) {
      throw new IllegalArgumentException("Index is invalid!");
    } else {
      return allOpenPiles.get(index).getCardsFromPile().size();
    }
  }

  @Override
  public int getNumOpenPiles() {
    if (!this.hasGameStarted) {
      return -1;
    } else {
      return allOpenPiles.size();
    }
  }

  @Override
  public ICard getFoundationCardAt(int pileIndex, int cardIndex)
      throws IllegalArgumentException, IllegalStateException {
    if (!this.hasGameStarted) {
      throw new IllegalStateException("Game has not started!");
    } else if (pileIndex > allFoundationPiles.size() - 1 || pileIndex < 0) {
      throw new IllegalArgumentException("Pile index is invalid!");
    } else if (cardIndex > this.allFoundationPiles.get(pileIndex).getCardsFromPile().size() - 1
        || cardIndex < 0) {
      throw new IllegalArgumentException("Card index is invalid!");
    } else {
      return allFoundationPiles.get(pileIndex).getCardsFromPile().get(cardIndex);
    }
  }

  @Override
  public ICard getCascadeCardAt(int pileIndex, int cardIndex)
      throws IllegalArgumentException, IllegalStateException {
    if (!this.hasGameStarted) {
      throw new IllegalStateException("Game has not started!");
    } else if (pileIndex > allCascadePiles.size() - 1 || pileIndex < 0) {
      throw new IllegalArgumentException("Pile index is invalid!");
    } else if (cardIndex > allCascadePiles.get(pileIndex).getCardsFromPile().size() - 1
        || cardIndex < 0) {
      throw new IllegalArgumentException("Card index is invalid!");
    } else {
      return allCascadePiles.get(pileIndex).getCardsFromPile().get(cardIndex);
    }
  }

  @Override
  public ICard getOpenCardAt(int pileIndex) throws IllegalArgumentException, IllegalStateException {
    if (!this.hasGameStarted) {
      throw new IllegalStateException("Game has not started!");
    } else if (pileIndex > allOpenPiles.size() - 1 || pileIndex < 0) {
      throw new IllegalArgumentException("Pile index is invalid!");
    } else if (allOpenPiles.get(pileIndex).getCardsFromPile().size() == 0) {
      return null;
    } else {
      return allOpenPiles.get(pileIndex).getCardsFromPile().get(0);
    }
  }


}