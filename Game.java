import java.util.Random;

public class Game {
    private Deck gameDeck;
    private Card[] tempPlayerHand;
    private Card[] tempComputerHand;
    private Card[] finalPlayerHand;
    private Card[] finalComputerHand;
    private Card[] playerBoard;
    private Card[] computerBoard;
    private int playerBoardSize;
    private int computerBoardSize;
    private int tempPlayerHandSize;
    private int tempComputerHandSize;
    private int finalPlayerHandSize;
    private int finalComputerHandSize;
    private boolean isOver;

    public Game() {
        gameDeck = new Deck();
        gameDeck.shuffle();
        tempPlayerHand = new Card[10]; // Assuming max 10 cards in hand
        tempComputerHand = new Card[10]; // Assuming max 10 cards in hand
        finalPlayerHand = new Card[4];
        finalComputerHand = new Card[4];
        playerBoard = new Card[20];
        computerBoard = new Card[20];
        tempPlayerHandSize = 0;
        tempComputerHandSize = 0;
        finalPlayerHandSize = 0;
        finalComputerHandSize = 0;
        isOver = false;
        dealInitialCards();
    }

    private void dealInitialCards() {
        for (int i = 0; i < 5; i++) {
            tempComputerHand[tempComputerHandSize++] = gameDeck.dealTopCard();
            tempPlayerHand[tempPlayerHandSize++] = gameDeck.dealBottomCard();
        }
        generateAdditionalCards();
        randomizeFinalHands();
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

    private void randomizeFinalHands(){
        for(int i = 0; i < 4; i++){
            finalPlayerHand[i] = tempPlayerHand[(int)(Math.random() * 10)];
            finalComputerHand[i] = tempComputerHand[(int)(Math.random() * 10)];
        }
        finalComputerHandSize = 4;
        finalPlayerHandSize = 4;
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

    public void hitPlayer() {
        playerBoard[playerBoardSize++] = gameDeck.dealTopCard();
    }

    public void hitComputer() {
        computerBoard[computerBoardSize++] = gameDeck.dealTopCard();
    }

    public Card[] getPlayerHand() {
        return finalPlayerHand;
    }

    public Card[] getPlayerBoard(){
        return playerBoard;
    }

    public int getPlayerHandSize() {
        return finalPlayerHandSize;
    }

    public int getPlayerBoardSize(){
        return playerBoardSize;
    }

    public boolean getGameState(){
        return isOver;
    }
}