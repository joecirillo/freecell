package cs3500.freecell.controller;

import cs3500.freecell.model.PileType;

/**
 * Class to represent a command that is inputted from the user. Comprised of the five required
 * fields to make a move: the source pile, the index number for the source, the card index within
 * the pile, the destination pile, and the index number for the destination.
 */
public class UserCommand {

  private PileType source;
  private int sourcePileNum;
  private int cardIndex;
  private PileType dest;
  private int destPileNum;

  /**
   * Builds a default user command. Defaults are used until they are replaced with actual valid user
   * inputs.
   */
  public UserCommand() {
    // all of these are set  to null or some integer less than one until a user enters a command
    // that enables them to be validated into a move command
    source = null;
    sourcePileNum = -13;
    cardIndex = -13;
    dest = null;
    destPileNum = -13;
  }

  /**
   * User Command contains all the inputs required to make a move in the Freecell game.
   *
   * @param source        which is the type of pile from which the card is being moved
   * @param sourcePileNum is the pile in a pile type from which the card is being moved
   * @param cardIndex     which is the card index of the moving card
   * @param dest          which is the type of pile to which the card is being moved
   * @param destPileNum   is the pile in a pile type to which the card is being moved
   */
  public UserCommand(PileType source, int sourcePileNum, int cardIndex, PileType dest,
      int destPileNum) {
    if (sourcePileNum <= 0 || cardIndex <= 0 || destPileNum <= 0 || source == null
        || dest == null) {
      throw new IllegalArgumentException("Indices must be greater than 0!");
    }
    this.source = source;
    this.sourcePileNum = sourcePileNum;
    this.cardIndex = cardIndex;
    this.dest = dest;
    this.destPileNum = destPileNum;
  }

  /**
   * Gets the pile type of the pile from which the card is being moved.
   *
   * @return the pile type.
   */
  public PileType getSourceType() {
    return this.source;
  }

  /**
   * Gets the pile number of the pile from which the card is being moved.
   *
   * @return the pile number of the source.
   */
  public int getSourcePileNum() {
    return this.sourcePileNum;
  }

  /**
   * Gets the card index of the card that is being moved.
   *
   * @return the index of a card
   */
  public int getCardIndex() {
    return this.cardIndex;
  }


  /**
   * Gets the pile type of the pile to which the card is being moved.
   *
   * @return the pile type.
   */
  public PileType getDestType() {
    return this.dest;
  }

  /**
   * Gets the pile number of the pile to which the card is being moved.
   *
   * @return the pile number of the destination.
   */
  public int getDestPileNum() {
    return this.destPileNum;
  }



  /**
   * Converts the source pile number from the user friendly index starting at one to the program
   * friendly index that starts at zero.
   *
   * @param sourcePileNum which is the source pile number of the user commanded move
   * @return the move that is meant for the program to interpret
   */
  public UserCommand convertSourcePileNum(int sourcePileNum) {
    this.sourcePileNum = sourcePileNum - 1;
    return this;
  }

  /**
   * Converts the destination pile number rom the user friendly index starting at one to the program
   * friendly index that starts at zero.
   *
   * @param destPileNum which is the destination pile number of the user commanded move
   * @return the move that is meant for the program to interpret
   */
  public UserCommand convertDestPileNum(int destPileNum) {
    this.destPileNum = destPileNum - 1;
    return this;
  }

  /**
   * Converts the card index from the user friendly index starting at one to the program friendly
   * index that starts at zero.
   *
   * @param cardIndex which is the card index number of the user commanded move
   * @return the move that is meant for the program to interpret
   */
  public UserCommand convertCardIndex(int cardIndex) {
    this.cardIndex = cardIndex - 1;
    return this;
  }

  /**
   * Converts the source from null to the actual source pile the user chooses.
   *
   * @param source is the pile type of the user's chosen pile
   * @return the declared source
   */
  public UserCommand convertSource(PileType source) {
    this.source = source;
    return this;
  }

  /**
   * Converts the destination from null to the actual destination pile the user chooses.
   *
   * @param dest is the pile type of the user's chosen pile
   * @return the declared destination
   */
  public UserCommand convertDest(PileType dest) {
    this.dest = dest;
    return this;
  }

  /**
   * Is a user command a valid command?.
   *
   * @return whether a command is valid.
   */
  public boolean isValidCommand() {
    return source != null
        && sourcePileNum >= 0
        && cardIndex >= 0
        && dest != null
        && destPileNum >= 0;
  }

  /**
   * Determines the size of the command.
   *
   * @return the size of the command by looking at how many fields have been set.
   */
  public int numValidCommands() {
    int validCommands = 0;
    if (source != null) {
      validCommands++;
    }
    if (sourcePileNum >= 0) {
      validCommands++;
    }
    if (cardIndex >= 0) {
      validCommands++;
    }
    if (dest != null) {
      validCommands++;
    }
    if (destPileNum >= 0) {
      validCommands++;
    }
    return validCommands;
  }
}
