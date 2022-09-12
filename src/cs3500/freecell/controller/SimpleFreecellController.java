package cs3500.freecell.controller;

import cs3500.freecell.model.FreecellModel;
import cs3500.freecell.model.PileType;
import cs3500.freecell.model.hw02.ICard;

import cs3500.freecell.view.FreecellTextView;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

/**
 * SimpleFreecellController is the controller portion of the MCV. The class contains the play game
 * method that can be called to start a game. It also works in conjunction with the UserCommand
 * class to take in inputs, pass them off to the model to check for validity, and, if valid, execute
 * them.
 */
public class SimpleFreecellController implements FreecellController<ICard> {


  private final FreecellModel<ICard> model;
  private Readable in;
  private Appendable out;
  private Scanner scanner;
  private boolean gameQuit;
  private boolean gameStarted;
  private boolean gameOver;
  private UserCommand userMove;
  private FreecellTextView freecell;

  /**
   * Constructor for the controller for the MCV.
   *
   * @param model is the SimpleFreecellModel.
   * @param rd    is the Readable portion of the Readable/Appendable.
   * @param ap    is the Appendable portion of the Readable/Appendable.
   * @throws IllegalArgumentException if null inputs are given.
   */
  public SimpleFreecellController(FreecellModel<ICard> model, Readable rd, Appendable ap)
      throws IllegalArgumentException {
    if (rd == null || ap == null) {
      throw new IllegalArgumentException("Null input given!");
    } else {
      this.model = model;
      this.in = rd;
      this.out = ap;
      this.scanner = new Scanner(rd);
      this.gameQuit = false;
      this.gameStarted = false;
      this.gameOver = false;
      this.userMove = new UserCommand();
      this.freecell = new FreecellTextView(model, out);
    }

  }

  // Writes a message to the console in a similar fashion to renderMessage, but it throws an
  // IllegalArgumentException instead of an IOException.
  private void write(String message) throws IllegalArgumentException {
    try {
      this.out.append(message);
    } catch (IOException e) {
      throw new IllegalArgumentException("Could not write to appendable.");
    }
  }


  @Override
  public void playGame(List<ICard> deck, int numCascades, int numOpens, boolean shuffle)
      throws IllegalStateException, IllegalArgumentException {
    try {
      Objects.requireNonNull(deck);
      Objects.requireNonNull(model);
    } catch (NullPointerException e) {
      throw new IllegalArgumentException("Neither deck nor argument can be null.");
    }
    try {
      this.model.startGame(deck, numCascades, numOpens, shuffle);
      this.gameStarted = true;
    } catch (IllegalArgumentException e) {
      write("Could not start game.");
      return;
    }

    try {
      if (gameStarted) {
        run(model);
      }
    } catch (IllegalArgumentException | IOException e) {
      throw new IllegalStateException("Illegal input received!");
    }
  }


  // Helper method for play game that handles the input scenarios and delegates further.
  private void run(FreecellModel model) throws IOException {
    freecell.renderBoard();
    if (!model.isGameOver()) {
      Scanner scanner = new Scanner(in);
      if (!scanner.hasNext()) {
        throw new IllegalStateException("Readable ran out.");
      }
      // will run as long as there's a next input to be evaluated
      while (scanner.hasNext()) {
        String userInput = scanner.next().toLowerCase();

        if (userInput.equals("q")) {
          freecell.renderMessage("Game quit prematurely.");
          return;
        } else {
          convertUserCommand(userInput);
        }
        // if every part of the required move has been filled out isValidCommand
        // will evaluate if it is a valid move
        if (userMove.isValidCommand()) {
          PileType source = userMove.getSourceType();
          int sourcePileNum = userMove.getSourcePileNum();
          int cardIndex = userMove.getCardIndex();
          PileType dest = userMove.getDestType();
          int destPileNum = userMove.getDestPileNum();
          {
            try {
              model.move(source, sourcePileNum, cardIndex, dest, destPileNum);
              freecell.renderBoard();
            } catch (IllegalArgumentException e) {
              freecell.renderMessage("Invalid move. Try again." + "\n");
            }
            // a new user move will be created no matter the result
            userMove = new UserCommand();
          }
        }
      }
    }
    if (model.isGameOver()) {
      freecell.renderMessage("\nGame over.");
      return;
    }
  }

  // Attempts to convert given user commands from the console into a valid format.
  private void convertUserCommand(String userInput) throws IOException {
    switch (userMove.numValidCommands()) {
      case 0:
        if (isValidPile(userInput.substring(0, 1))
            && isValidInt(userInput.substring(1))) {
          userMove.convertSource(convertPile(userInput.substring(0, 1)));
          userMove.convertSourcePileNum(getNumber(userInput.substring(1)));
        } else {
          freecell.renderMessage("Re-enter source and pile number." + "\n");
        }
        break;
      case 2:
        if (isValidInt(userInput.substring(0, 1))) {
          userMove.convertCardIndex(getNumber(userInput.substring(0, 1)));
        } else {
          freecell.renderMessage("Re-enter the card index." + "\n");
        }
        break;
      case 3:
        if (isValidPile(userInput.substring(0, 1))
            && isValidInt(userInput.substring(1))) {
          userMove.convertDest(convertPile(userInput.substring(0, 1)));
          userMove.convertDestPileNum(getNumber(userInput.substring(1)));
        } else {
          freecell.renderMessage("Re-enter destination and pile number." + "\n");
        }
        break;
      default:
        break;
    }
  }

  // Is a Pile valid?
  private boolean isValidPile(String s) {
    String pile = s.toLowerCase();
    return pile.equals("c") || pile.equals("o")
        || pile.equals("f");
  }

  // Converts a string to one of the three Pile types.
  private PileType convertPile(String s) {
    String pile = s.toLowerCase();
    if (pile.equals("c")) {
      return PileType.CASCADE;
    } else if (pile.equals("o")) {
      return PileType.OPEN;
    } else {
      return PileType.FOUNDATION;
    }
  }

  // Is an integer valid?
  private boolean isValidInt(String s) {
    int index;
    try {
      index = Integer.parseInt(s);
    } catch (NumberFormatException e) {
      return false;
    }
    return index > 0;
  }


  // converts a String number to an integer
  private int getNumber(String s) {
    try {
      return Integer.parseInt(s);
    } catch (NumberFormatException e) {
      return -13;
    }
  }
}




