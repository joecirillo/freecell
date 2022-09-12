package cs3500.freecell.model.hw02;

/**
 * This is an enumeration for the value of a card. There are 13 values of cards in a standard 52
 * card deck. In this game, Ace is the lowest value. Each has a value attached as it
 * would appear on a physical, real life card.
 */
public enum Value {
  ACE(1), TWO(2), THREE(3), FOUR(4),
  FIVE(5), SIX(6), SEVEN(7), EIGHT(8),
  NINE(9), TEN(10), JACK(11), QUEEN(12),
  KING(13);

  private final int cardValue;

  /**
   * The 13 different values of cards as they would appear on a card.
   * @param cardValue Game value of the card (A, 2-10, JQK).
   */
  Value(int cardValue) {
    this.cardValue = cardValue;
  }

  @Override
  public String toString() {
    return Integer.toString(this.cardValue);
  }

  public int returnInt() {
    return this.cardValue;
  }

}

