package cs3500.freecell.model.hw02;

import cs3500.freecell.model.PileType;
import java.util.ArrayList;
import java.util.Objects;

/**
 * Pile implements IPile, which is a pile of a card in all three types: Cascade, Foundation, and
 * Open. The three types are enumerated in the PileType enum.
 */
public class Pile implements IPile {

  private final PileType pileType;
  private final ArrayList<ICard> cardsInPile;

  /**
   * Constructor for a pile, which represents a singular type that has a type, along with the list
   * of cards in it.
   *
   * @param pileType    one of the three types of piles
   * @param cardsInPile the cards that have been placed in the pile
   */
  public Pile(PileType pileType, ArrayList<ICard> cardsInPile) {
    this.pileType = pileType;
    this.cardsInPile = Objects.requireNonNull(cardsInPile);

  }

  @Override
  public ArrayList<ICard> getCardsFromPile() {
    return this.cardsInPile;
  }

  @Override
  public void addCardToPile(ICard card) {
    this.cardsInPile.add(card);
  }

  @Override
  public void removeCardFromPile(ICard card) {
    this.cardsInPile.remove(card);
  }

  @Override
  public PileType getPileType() {
    return this.pileType;
  }

  @Override
  public boolean isMoveLegal(ICard movingCard) {
    boolean emptyDestination = this.cardsInPile.isEmpty();
    if (this.pileType == PileType.FOUNDATION) {
      return isFoundationMoveLegal(movingCard);
    }
    // can't add a card to an open pile if it's already occupied
    else if (this.pileType == PileType.OPEN && !emptyDestination) {
      return false;
    } else if (this.pileType == PileType.CASCADE && !emptyDestination) {
      return isCascadeMoveLegal(movingCard);
    } else {
      return true;
    }
  }

  @Override
  public boolean isFoundationMoveLegal(ICard movingCard) {
    boolean emptyDestination = this.cardsInPile.isEmpty();
    if (!emptyDestination) {
      ICard destinationCard = this.cardsInPile.get(this.cardsInPile.size() - 1);
      boolean isSameSuit = destinationCard.getSuit() == movingCard.getSuit();
      boolean isOneMore =
          destinationCard.getValue().returnInt() + 1 == movingCard.getValue().returnInt();
      return isSameSuit && isOneMore;
    } else {
      boolean isAce = movingCard.getValue().returnInt() == Value.ACE.returnInt();
      return isAce;
    }
  }

  @Override
  public boolean isCascadeMoveLegal(ICard movingCard) {
    ICard destCard = this.cardsInPile.get(this.cardsInPile.size() - 1);
    boolean differentColor = (!destCard.getSuit().isBlack() && movingCard.getSuit().isBlack()) ||
        destCard.getSuit().isBlack() && !movingCard.getSuit().isBlack();
    boolean smallerVal = destCard.getValue().returnInt() == movingCard.getValue().returnInt() + 1;
    return differentColor && smallerVal;
  }


}
