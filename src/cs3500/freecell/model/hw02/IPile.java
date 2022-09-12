package cs3500.freecell.model.hw02;

import cs3500.freecell.model.PileType;
import java.util.ArrayList;

/**
 * Represents a pile of a card in all three types: Cascade, Foundation, and Open, which are
 * enumerated in the PileType enum.
 */
public interface IPile {

  /**
   * Retrieves all the cards from a pile.
   * @return the cards in a pile.
   */
  public ArrayList<ICard> getCardsFromPile();

  /**
   * Adds a card to a pile.
   * @param card that is added to pile.
   */
  public void addCardToPile(ICard card);

  /**
   * Removes a card from a pile.
   * @param card that is removed from pile.
   */
  public void removeCardFromPile(ICard card);

  /**
   * Retrieves the type of pile from a pile.
   * @return type of pile.
   */
  public PileType getPileType();

  /**
   * Is attempted move allowed in freecell?.
   * @param movingCard is the card that would move.
   * @return
   */
  public boolean isMoveLegal(ICard movingCard);

  /**
   * Is attempted move allowed in freecell foundation pile?.
   * @param movingCard is the card that would move.
   * @return
   */
  public boolean isFoundationMoveLegal(ICard movingCard);


  /**
   * Is attempted move allowed in freecell cascade pile?.
   * @param movingCard is the card that would move.
   * @return
   */
  public boolean isCascadeMoveLegal(ICard movingCard);

}
