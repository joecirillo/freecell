package cs3500.freecell.controller;

import cs3500.freecell.model.hw02.ICard;
import cs3500.freecell.model.hw02.SimpleFreecellModel;

import cs3500.freecell.model.hw04.MultiMoveFreecellModel;
import java.io.InputStreamReader;

import java.util.Collections;
import java.util.List;

/**
 * Play contains the main method to be able to run the Freecell game in the console. It uses the
 * controller class with a Readable, Appendable, SimpleFreecellModel, and SimpleFreecellController
 * object to be able to run the game.
 */
public class Play {

  /**
   * Runs the game of freecell.
   *
   * @param args is part of the main method
   */
  public static void main(String[] args) {
    SimpleFreecellModel simp = new SimpleFreecellModel();
    MultiMoveFreecellModel multi = new MultiMoveFreecellModel();
    Readable rd = new InputStreamReader(System.in);
    Appendable ap = System.out;
    SimpleFreecellController controller = new SimpleFreecellController(multi, rd, ap);
    List<ICard> generatedDeck = simp.getDeck();
    Collections.reverse(generatedDeck);

    controller.playGame(generatedDeck, 26, 6, false);
  }

}
