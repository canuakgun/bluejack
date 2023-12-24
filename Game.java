import java.util.Random;

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
    
    private void generateAdditionalCards() {
        for (int i = 0; i < 3; i++) {
            addRandomCardToHand(tempPlayerHand, tempPlayerHandSize++);
            addRandomCardToHand(tempComputerHand, tempComputerHandSize++);
        }
        for (int i = 0; i < 2; i++) {
            addSpecialCardToHand(tempPlayerHand, tempPlayerHandSize++);
            addSpecialCardToHand(tempComputerHand, tempComputerHandSize++);
        }
    }

    private void addRandomCardToHand(Card[] hand, int index) {
        int value = (int)(Math.random() * 6) + 1; // Random value between 1 and 6
        String color = getRandomColor();
        String sign = Math.random() < 0.5 ? "+" : "-";
        hand[index] = new Card(value, color, sign, "normal");
    }

    private void addSpecialCardToHand(Card[] hand, int index) {
        if ((int)Math.random() < 0.8) {
            // 80% chance to be a signed card
            addRandomCardToHand(hand, index);
        } else {
            // 20% chance to be a flip or double card
            String specialType = Math.random() < 0.5 ? "flip" : "double";
            hand[index] = new Card(0, "", "", specialType);
        }
    }

    private String getRandomColor() {
        String[] colors = {"blue", "yellow", "red", "green"};
        return colors[(int)(Math.random() * colors.length)];
    }

    public boolean getGameState(){
        return isOver;
    }
}