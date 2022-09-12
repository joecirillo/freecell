package cs3500.freecell.model.hw04;

import cs3500.freecell.model.PileType;
import cs3500.freecell.model.hw02.ICard;
import cs3500.freecell.model.hw02.Pile;
import cs3500.freecell.model.hw02.SimpleFreecellModel;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to perform a multi move if said move is desired. A valid multimove is more than one move
 * with a valid build. In this implementation, only multimoves to cascade piles are allowed.
 * MultiMoveFreecellModel will override any methods in SimpleFreecellModel that are written only for
 * a single move.
 */
public class MultiMoveFreecellModel extends SimpleFreecellModel {

  @Override
  public void move(PileType source, int pileNumber, int cardIndex, PileType destination,
      int destPileNumber) throws IllegalArgumentException, IllegalStateException {
    // local variables that are also used in SimpleFreecellModel
    Pile sourcePile;
    Pile destinationPile;
    Pile destinationPileCopy;
    // passes off to helper method to check that game has started. Calls protected boolean
    // from SimpleFreecellModel
    if (!hasGameStarted) {
      throw new IllegalStateException("Game has not started!");
    }
    // helper method from SimpleFreecellModel that checks for out of bounds indices and such
    if (!super.isPossibleMove(source, pileNumber, cardIndex, destination, destPileNumber)) {
      throw new IllegalArgumentException("Invalid multimove attempted!");
    }
    sourcePile = super.getPileType(source).get(pileNumber);
    destinationPile = super.getPileType(destination).get(destPileNumber);
    // will be used to evaluate legality of multi moves
    destinationPileCopy = new Pile(destination,
        (ArrayList<ICard>) super.getPileType(destination).get(destPileNumber).getCardsFromPile()
            .clone());
    List<ICard> movingCards = new ArrayList<>((sourcePile.getCardsFromPile()
        .subList(cardIndex, sourcePile.getCardsFromPile().size())));
    // maxPossMoves is compared to the size of the cards to be moved to ensure there is enough
    // space for the move to be made
    if (movingCards.size() > numPossMoves()) {
      throw new IllegalArgumentException("Not enough space to make move!");
    } else {
      isMultiMoveLegal(destinationPileCopy, movingCards);
    }
    for (ICard card : movingCards) {
      sourcePile.removeCardFromPile(card);
      destinationPile.addCardToPile(card);
    }
  }

  // formula for the maximum number of moves, which is given in the assignment description
  // for n open open piles, k open cascade piles: (n+1) * 2^k
  private int numPossMoves() {
    // int n = number of open piles
    int k = getNumAvailablePiles(PileType.CASCADE);
    // because it is raised to a power, if k is too large there could be an overflow issue,
    // so k will be set to an arbitrary number that will always be large enough to make the move
    if (k > 9) {
      return 1024;
    } else {
      return (getNumAvailablePiles(PileType.OPEN) + 1) * (int) Math.pow(2, k);
    }
  }

  // get the number of piles when given a pile type with no cards in them
  private int getNumAvailablePiles(PileType pType) {
    int numEmptyPiles = 0;
    List<Pile> lopType;
    lopType = super.getPileType(pType);
    for (Pile pile : lopType) {
      if (pile.getCardsFromPile().size() == 0) {
        numEmptyPiles++;
      }
    }
    return numEmptyPiles;
  }


  // is a multimove legal? Passes off to helper in the pile class
  private void isMultiMoveLegal(Pile destination, List<ICard> movingCards)
      throws IllegalArgumentException {
    if (destination.getPileType() == PileType.OPEN && movingCards.size() > 1) {
      throw new IllegalArgumentException("Open multi move not supported!");
    } else if (destination.getPileType() == PileType.FOUNDATION && movingCards.size() > 1) {
      throw new IllegalArgumentException("Foundation multi move not supported!");
    } else {
      for (ICard card : movingCards) {
        if (destination.isMoveLegal(card)) {
          destination.addCardToPile(card);
        } else {
          throw new IllegalArgumentException("Illegal move! Card " + card + " could not be moved.");
        }
      }
    }
  }
}

