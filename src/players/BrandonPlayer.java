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
        return false;
    }

    @Override
    protected boolean shouldCheck() {
        return evaluatePlayerHand().getValue() >= HandRanks.HIGH_CARD.getValue();

    }

    @Override
    protected boolean shouldCall() {
        return false;
    }

    @Override
    protected boolean shouldRaise() {
        boolean hand = evaluatePlayerHand().getValue() >= HandRanks.PAIR.getValue();
        boolean bank = getGameState().getTableBet() < getBank() * 0.1;
        return hand && bank;
    }

    @Override
    protected boolean shouldAllIn() {
        return evaluatePlayerHand().getValue() >= HandRanks.FOUR_OF_A_KIND.getValue();
    }
}
