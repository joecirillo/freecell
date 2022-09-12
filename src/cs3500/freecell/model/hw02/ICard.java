package cs3500.freecell.model.hw02;

/**
 ICard is the interface representation of a card in a traditional 52 card deck. A card can go in
 * one of three piles: Cascade, Open, or Foundation. A card has a suit type and a value type,
 * with suits being hearts, diamonds, clubs, and spades; values are two through ten and ace, jack,
 * queen, and king.
 */
public interface ICard {

  /**
   * Returns the value of the suit.
   * @return the suit.
   */
  public Suit getSuit();

  /**
   * Returns the value of the card.
   * @return the value.
   */
  public Value getValue();

  /**
   * Turns the card value into value suit format. Ex: (A♣).
   * @return string value of card.
   */
  public String toString();

}
