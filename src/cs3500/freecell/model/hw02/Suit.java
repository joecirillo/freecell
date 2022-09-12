package cs3500.freecell.model.hw02;

/**
 * This is an enumeration for the suit of a card. A card can either be a diamond, a heart, a club,
 * or a spade. Diamonds and hearts are red, while clubs and spades are black. The four suits have
 * symbols attached to them as well.
 */
public enum Suit {
  DIAMOND("♦"), HEART("♥"), CLUB("♣"), SPADE("♠");

  private final String symbolValue;

  /**
   * One of the four symbols from a deck of cards.
   * @param symbolValue Visual symbol of the suit of the card.
   */
  Suit(String symbolValue) {
    this.symbolValue = symbolValue;
  }

  @Override
  public String toString() {
    return this.symbolValue;
  }

  /**
   * Is the suit black?.
   * @return whether the suit is black.
   */
  public boolean isBlack() {
    return this.symbolValue == CLUB.symbolValue || this.symbolValue == SPADE.symbolValue;
  }
}
