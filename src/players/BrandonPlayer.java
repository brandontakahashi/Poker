package players;

import game.HandRanks;
import game.Player;

public class BrandonPlayer extends Player {
    public BrandonPlayer(String name) {
        super(name);
    }

    @Override
    protected void takePlayerTurn() {
        if (shouldFold()) {
            fold();
        } else if (shouldCheck()) {
            check();
        } else if (shouldCall()) {
            call();
        } else if (shouldRaise()) {
            raise(getGameState().getTableMinBet());
        } else if (shouldAllIn()) {
            allIn();
        }
    }

    @Override
    protected boolean shouldFold() {
        // Fold if the hand is weaker than a pair or if the bet is more than 30% of the bank
        return evaluatePlayerHand().getValue() < HandRanks.PAIR.getValue() || getGameState().getTableBet() > getBank() * 0.3;
    }

    @Override
    protected boolean shouldCheck() {
        //Check if the hand is at least a high card
        return evaluatePlayerHand().getValue() >= HandRanks.HIGH_CARD.getValue();

    }

    @Override
    protected boolean shouldCall() {
        // Call if the bet is not too high compared to the bank and the hand strength is decent
        return evaluatePlayerHand().getValue() >= HandRanks.PAIR.getValue() && getGameState().getTableBet() < getBank() * 0.3; // Call if the bet is less than 30% of the bank
    }

    @Override
    protected boolean shouldRaise() {
        //Raise if the hand is at least a pair and the bet is less than 10% of the bank
        boolean hand = evaluatePlayerHand().getValue() >= HandRanks.PAIR.getValue();
        boolean bank = getGameState().getTableBet()  < getBank() * 0.1;
        return hand && bank;
    }

    @Override
    protected boolean shouldAllIn() {
        //Go all in if the hand is at least a four of a kind
        return evaluatePlayerHand().getValue() >= HandRanks.FOUR_OF_A_KIND.getValue();
    }
}
