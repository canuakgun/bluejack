import java.util.Random;

import javax.smartcardio.Card;

public class Game {
    private Deck gameDeck;
    private Card[] tempPlayerHand;
    private Card[] tempComputerHand;
    private int tempPlayerHandSize;
    private int tempComputerHandSize;
    private boolean isOver;

    public Game() {
        gameDeck = new Deck();
        gameDeck.shuffle();
        tempPlayerHand = new Card[10]; // Assuming max 10 cards in hand
        tempComputerHand = new Card[10]; // Assuming max 10 cards in hand
        tempPlayerHandSize = 0;
        tempComputerHandSize = 0;
        isOver = false;
        dealInitialCards();
    }

    private void dealInitialCards() {
        for (int i = 0; i < 5; i++) {
            tempComputerHand[tempComputerHandSize++] = gameDeck.dealTopCard();
            tempPlayerHand[tempPlayerHandSize++] = gameDeck.dealBottomCard();
        }
    }
}