package cs3500.freecell.model.hw02;

import java.util.Objects;

/**
 * Card implements ICard, a representation of a card in a traditional 52 card deck. A card can go in
 * one of three piles: Cascade, Open, or Foundation. A card has a suit type and a value type,
 * with suits being hearts, diamonds, clubs, and spades; values are two through ten and ace, jack,
 * queen, and king.
 */
public class Card implements ICard {

  // suit is the suit of card, represented by the Suit enum
  private final Suit suit;
  // value is the card value, represented by the Value enum
  private final Value value;

  /**
   * Constructor for Card. Ex: "3 of spades."
   *
   * @param value 2 through 10 plus Ace, Jack, Queen, and King.
   * @param suit  Diamonds, Hearts, Spades, and Clubs.
   * @throws NullPointerException value and suit cannot be null.
   */
  public Card(Value value, Suit suit) throws NullPointerException {
    this.value = Objects.requireNonNull(value);
    this.suit = Objects.requireNonNull(suit);
  }

  @Override
  public Value getValue() {
    return this.value;
  }

  @Override
  public Suit getSuit() {
    return this.suit;
  }

  @Override
  public String toString() {
    String generatedString = "";
    Suit givenSuit = this.getSuit();
    Value givenValue = this.getValue();
    switch (givenValue) {
      case ACE:
        generatedString = "A" + this.getSuit().toString();
        break;
      case TWO:
        generatedString = "2" + this.getSuit().toString();
        break;
      case THREE:
        generatedString = "3" + this.getSuit().toString();
        break;
      case FOUR:
        generatedString = "4" + this.getSuit().toString();
        break;
      case FIVE:
        generatedString = "5" + this.getSuit().toString();
        break;
      case SIX:
        generatedString = "6" + this.getSuit().toString();
        break;
      case SEVEN:
        generatedString = "7" + this.getSuit().toString();
        break;
      case EIGHT:
        generatedString = "8" + this.getSuit().toString();
        break;
      case NINE:
        generatedString = "9" + this.getSuit().toString();
        break;

      case TEN:
        generatedString = "10" + this.getSuit().toString();
        break;
      case JACK:
        generatedString = "J" + this.getSuit().toString();
        break;
      case QUEEN:
        generatedString = "Q" + this.getSuit().toString();
        break;
      default:
        generatedString = "K" + this.getSuit().toString();
        break;
    }
    return generatedString;
  }

}
