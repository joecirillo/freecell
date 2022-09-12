package cs3500.freecell.model;

import cs3500.freecell.model.hw02.SimpleFreecellModel;
import cs3500.freecell.model.hw04.MultiMoveFreecellModel;

/**
 * Factory class for the two game types of this freecell game: single move and multimove. Before a
 * game starts, it will be passed one of the two game types.
 */
public class FreecellModelCreator {

  /**
   * The two game types, single move and multimove. A single move is one single card move to or from
   * the three card piles: cascade, foundation, and open. An open move involves more than one card
   * move to a cascade pile.
   */
  public enum GameType {
    SINGLEMOVE, MULTIMOVE
  }

  /**
   * Static factory method that returns a simplefreecell or multimovefreecell model depending
   * on the input.
   * @param type is the game type, with the two valid types being singlemove and multimove.
   * @return a new object or an exception if the input is invalid.
   * @throws IllegalArgumentException if the input is invalid.
   */
  public static FreecellModel create(GameType type) throws IllegalArgumentException {
    if (type == null) {
      throw new IllegalArgumentException("Valid type entered not valid.");
    } else if (type == GameType.SINGLEMOVE) {
      return new SimpleFreecellModel();
    } else if (type == GameType.MULTIMOVE) {
      return new MultiMoveFreecellModel();
    } else {
      throw new IllegalArgumentException("Invalid game type entered.");
    }
  }

}
