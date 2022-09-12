package cs3500.freecell.view;

import cs3500.freecell.model.FreecellModel;
import java.io.IOException;

/**
 * Class for the view portion of the MCV. Contains methods that can print out a String
 * representation of the board for the Freecell game and messages relating to moves.
 */
public class FreecellTextView implements FreecellView {

  private final FreecellModel<?> model;
  private Appendable out;


  /**
   * Constructor for FreecellTextView that takes in an Appendable to be able in addition
   * to a model to be able to interact with the controller.
   * @param model is a SimpleFreecell model.
   * @param ap is an Appendable that cannot be null.
   */
  public FreecellTextView(FreecellModel<?> model, Appendable ap) {
    if (model == null || ap == null) {
      throw new IllegalArgumentException("Model cannot be null!");
    }
    this.model = model;
    this.out = ap;
  }

  /**
   * Constructor from the first homework that can be used to call toString. Only takes in a model.
   * @param model is a SimpleFreecellModel.
   */
  public FreecellTextView(FreecellModel<?> model) {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null!");
    }
    this.model = model;
  }

  @Override
  public String toString() {
    if (this.model.getNumCascadePiles() == -1) {
      return "";
    }
    String generatedString = "";
    for (int i = 0; i < 4; ++i) {
      if (i != 0) {
        generatedString += "\n";
      }
      generatedString += "F" + (i + 1) + ":";
      if (model.getNumCardsInFoundationPile(i) != 0) {
        generatedString += " ";
      }
      for (int j = 0; j < model.getNumCardsInFoundationPile(i); ++j) {
        generatedString += model.getFoundationCardAt(i, j).toString();
        if (j < model.getNumCardsInCascadePile(i) - 1) {
          generatedString += ", ";
        }
      }
    }
    generatedString += "\n";
    for (int i = 0; i < model.getNumOpenPiles(); ++i) {
      if (i != 0) {
        generatedString += "\n";
      }
      generatedString += "O" + (i + 1) + ":";
      if (model.getNumCardsInOpenPile(i) != 0) {
        generatedString += " ";
      }
      for (int j = 0; j < model.getNumCardsInOpenPile(i); ++j) {
        generatedString += model.getOpenCardAt(i).toString();
        if (j < model.getNumCardsInOpenPile(i) - 1) {
          generatedString += ", ";
        }
      }
    }
    generatedString += "\n";
    for (int i = 0; i < model.getNumCascadePiles(); ++i) {
      if (i != 0) {
        generatedString += "\n";
      }
      generatedString += "C" + (i + 1) + ":";
      if (model.getNumCardsInCascadePile(i) != 0) {
        generatedString += " ";
      }
      for (int j = 0; j < model.getNumCardsInCascadePile(i); ++j) {
        generatedString += model.getCascadeCardAt(i, j).toString();
        if (j < model.getNumCardsInCascadePile(i) - 1) {
          generatedString += ", ";
        }
      }
    }
    return generatedString;
  }

  @Override
  public void renderBoard() throws IOException {
    try {
      out.append(this.toString());
      out.append("\n");
    } catch (IOException e) {
      throw new IOException("Illegal input received");
    }

  }

  @Override
  public void renderMessage(String message) throws IOException {
    try {
      this.out.append(message);
    } catch (IOException e) {
      throw new IOException("Could not write to appendable.");
    }

  }

}
