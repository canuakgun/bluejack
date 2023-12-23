import java.util.Random;
import java.util.Scanner;

class Card {
    private final String color;
    private final int value;
    private final String sign;

    public Card(String color, int value, String sign) {
        this.color = color;
        this.value = value;
        this.sign = sign;
    }

    public String getColor() {
        return color;
    }

    public int getValue() {
        return value;
    }

    public String getSign() {
        return sign;
    }

    public String toString() {
        return color + " " + value + " " + sign;
    }
}

class Player {
    private final String name;
    private final Card[] hand;
    private final Card[] board;
    private int handSize;
    private int boardSize;

    public Player(String name) {
        this.name = name;
        this.hand = new Card[4];
        this.board = new Card[9];
        this.handSize = 0;
        this.boardSize = 0;
    }

    public String getName() {
        return name;
    }

    public void drawCard(Card[] gameDeck) {
        hand[handSize++] = gameDeck[--Game.TOTAL_CARDS];
    }

    public void playCard(int index) {
        board[boardSize++] = hand[index];
        hand[index] = null;
    }

    public void showHand() {
        System.out.print(name + "'s hand: ");
        for (int i = 0; i < handSize; i++) {
            System.out.print(hand[i] + " ");
        }
        System.out.println();
    }

    public void showBoard() {
        System.out.print(name + "'s board: ");
        for (int i = 0; i < boardSize; i++) {
            System.out.print(board[i] + " ");
        }
        System.out.println();
    }

    public boolean hasCardsToPlay() {
        return handSize > 0;
    }

    public boolean isBoardFull() {
        return boardSize == 9;
    }
}

class Game {
    static int TOTAL_CARDS = 40;
    static final int CARDS_IN_HAND = 4;
    static final int TARGET_SCORE = 20;
    static final int MAX_CARDS_ON_BOARD = 9;
    static final Random random = new Random();

    public static void main(String[] args) {
        Card[] gameDeck = initializeGameDeck();
        Player[] players = initializePlayers();

        for (int i = 0; i < 5; i++) {
            players[0].drawCard(gameDeck); // Computer
            players[1].drawCard(gameDeck); // Human
        }

        addRandomCards(gameDeck, players[1]);

        for (int i = 0; i < CARDS_IN_HAND; i++) {
            players[1].playCard(i);
        }

        playGame(players, gameDeck);
    }

    private static Card[] initializeGameDeck() {
        Card[] gameDeck = new Card[TOTAL_CARDS];
        int index = 0;

        for (int i = 1; i <= 10; i++) {
            gameDeck[index++] = new Card("Blue", i, "");
            gameDeck[index++] = new Card("Yellow", i, "");
            gameDeck[index++] = new Card("Red", i, "");
            gameDeck[index++] = new Card("Green", i, "");
        }

        shuffleDeck(gameDeck);
        return gameDeck;
    }

    private static Player[] initializePlayers() {
        Player[] players = new Player[2];
        players[0] = new Player("Computer");
        players[1] = new Player("Human");
        return players;
    }

    private static void shuffleDeck(Card[] deck) {
        for (int i = TOTAL_CARDS - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            // Swap deck[i] and deck[j]
            Card temp = deck[i];
            deck[i] = deck[j];
            deck[j] = temp;
        }
    }

    private static void addRandomCards(Card[] gameDeck, Player player) {
        for (int i = 0; i < 3; i++) {
            player.drawCard(gameDeck);
        }

        if (random.nextDouble() < 0.2) {
            player.drawCard(gameDeck);
        }

        if (random.nextDouble() < 0.2) {
            player.drawCard(gameDeck);
        }
    }

    

    private static String randomSign() {
        return random.nextBoolean() ? "+" : "-";
    }

    private static void playGame(Player[] players, Card[] gameDeck) {
        int currentPlayerIndex = 1; // Human player starts
        boolean gameWon = false;

        while (!gameWon) {
            Player currentPlayer = players[currentPlayerIndex];
            Player opponent = players[1 - currentPlayerIndex];

            currentPlayer.showHand();
            currentPlayer.showBoard();
            drawOrPlay(currentPlayer, gameDeck);

            if (isBust(currentPlayer)) {
                System.out.println(currentPlayer.getName() + " busts! " + opponent.getName() + " wins the set.");
                break;
            }

            if (currentPlayer.isBoardFull()) {
                if (getScore(currentPlayer) <= TARGET_SCORE) {
                    System.out.println(currentPlayer.getName() + " wins the set!");
                    break;
                }
            }

            currentPlayerIndex = 1 - currentPlayerIndex; // Switch turn to the opponent
        }
    }

    private static void drawOrPlay(Player player, Card[] gameDeck) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println(player.getName() + ", do you want to draw a card (d) or play a card (p)?");
            char choice = scanner.next().charAt(0);

            if (choice == 'd') {
                player.drawCard(gameDeck);
                player.showHand();
            } else if (choice == 'p' && player.hasCardsToPlay()) {
                player.showHand();
                System.out.println("Which card do you want to play? Enter the index:");
                int index = scanner.nextInt();
                player.playCard(index);
                break; // Playing a card ends the turn
            } else {
                System.out.println("Invalid choice. Please enter 'd' or 'p'.");
            }
        }
    }

    private static boolean isBust(Player player) {
        return getScore(player) > TARGET_SCORE;
    }

    private static int getScore(Player player) {
        int score = 0;

        for (Card card : player.getBoard()) {
            if ("+".equals(card.getSign())) {
                score += card.getValue();
            } else if ("-".equals(card.getSign())) {
                score -= card.getValue();
            } else {
                score += card.getValue();
            }
        }

        return score;
    }
}
