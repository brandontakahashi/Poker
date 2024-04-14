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
            raise(calculateRaiseAmount());
        } else if (shouldAllIn()) {
            allIn();
        }
    }

    @Override
    protected boolean shouldFold() {
        int handValue = evaluatePlayerHand().getValue();
        int tableBet = getGameState().getTableBet();
        int bank = getBank();

        //Fold if the hand is weak (worse than two pairs) and the bet is too high compared to the bank
        return handValue < HandRanks.TWO_PAIR.getValue() && tableBet > bank * 0.3;
    }

    @Override
    protected boolean shouldCheck() {
        int handValue = evaluatePlayerHand().getValue();

        //Check if there is no active bet or if the hand is weak (worse than a pair)
        return !getGameState().isActiveBet() || handValue < HandRanks.PAIR.getValue();
    }

    @Override
    protected boolean shouldCall() {
        int handValue = evaluatePlayerHand().getValue();
        int tableBet = getGameState().getTableBet();
        int bank = getBank();

        //Call if the bet is not too high (less than 30% of the bank) and the hand is decent (at least a pair)
        return tableBet <= bank * 0.3 && handValue >= HandRanks.PAIR.getValue();
    }

    @Override
    protected boolean shouldRaise() {
        int handValue = evaluatePlayerHand().getValue();
        int tableBet = getGameState().getTableBet();
        int bank = getBank();

        //Raise if the hand is strong (better than two pairs) and the bet is not too high (less than 50% of the bank)
        return handValue >= HandRanks.TWO_PAIR.getValue() && tableBet <= bank * 0.5;
    }

    @Override
    protected boolean shouldAllIn() {
        int handValue = evaluatePlayerHand().getValue();
        int bank = getBank();
        int tableBet = getGameState().getTableBet();

        //Go all-in if the hand is very strong (three of a kind or better) and the bank is double the table bet
        return handValue >= HandRanks.THREE_OF_A_KIND.getValue() && bank >= tableBet * 2;
    }

    //Calculate raise amount based on hand strength and game state
    private int calculateRaiseAmount() {
        int handValue = evaluatePlayerHand().getValue();
        int bank = getBank();

        if (handValue >= HandRanks.FULL_HOUSE.getValue()) {
            return bank; //Go all-in with a full house or better
        } else if (handValue >= HandRanks.THREE_OF_A_KIND.getValue()) {
            return bank * 3 / 4; //Raise 75% of the bank with three of a kind or better
        } else if (handValue >= HandRanks.TWO_PAIR.getValue()) {
            return bank / 2; //Raise half of the bank with two pairs or better
        } else {
            //Otherwise, raise by a significant portion of the bank (30%)
            return (int) (bank * 0.3);
        }
    }

}




