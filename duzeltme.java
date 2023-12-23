import java.util.Random;
import java.util.Scanner;

public class duzeltme {
    private static int TOTAL_CARDS = 40;
    private static final int TOTAL_RANKS = 10;
    private static final int TOTAL_COLOURS = 4;
    private static final int TOTAL_POINTS_TO_WIN = 20;
    private static int PLAYER_TOTAL_SCORE = 0;
    private static int COMPUTER_TOTAL_SCORE = 0;

    public static void main(String[] args) {
        System.out.println("Welcome to Bluejack!");
        int PLAYER_TOTAL_SCORE = 0;
        int COMPUTER_TOTAL_SCORE = 0;
        //oyunun 3'te bitmesini yapamadım
        // Initialize the game
        Card[] deck = initializeDeck();
        shuffleDeck(deck);

        Card[] playerHand = new Card[9];
        Card[] computerHand = new Card[9];
        int playerHandSize = 0;
        int computerHandSize = 0;

        // Deal 5 cards to each player(-6 , +6 arası kartlar ve signed kartları kaldı) bide biri destenin altı biri üstünden çekecek en altta bunun kodunu yaptım ama sorun çıkıyo başka yerde bunu kullanınca
        for(int i=0;i<5;i++){
        playerHandSize = drawCard(deck, playerHand, playerHandSize);

        computerHandSize = drawCard(deck, computerHand, computerHandSize);
        }
       
        // Player's turn
        playerHandSize = playerTurn(deck, playerHand, playerHandSize);

        // Computer's turn
        computerHandSize = computerTurn(deck, computerHand, computerHandSize);

        // Determine the winner
        determineWinner(playerHand, playerHandSize, computerHand, computerHandSize);
    }

    private static Card[] initializeDeck() {
        Card[] deck = new Card[TOTAL_CARDS];
        String[] ranks = {"1","2", "3", "4", "5", "6", "7", "8", "9", "10"};
        String[] colours = {"Blue", "Yellow", "Green", "Red"};

        int index = 0;
        for (String colour : colours) {
            for (String rank : ranks) {
                deck[index++] = new Card(rank, colour);
            }
        }

        return deck;
    }

    private static void shuffleDeck(Card[] deck) {
        Random rand = new Random();
        for (int i = TOTAL_CARDS - 1; i > 0; i--) {
            int j = rand.nextInt(i + 1);
            // Swap deck[i] and deck[j]
            Card temp = deck[i];
            deck[i] = deck[j];
            deck[j] = temp;
        }
    }
    //drawcard 
    private static int drawCard(Card[] deck, Card[] hand, int handSize) {
        if (handSize < hand.length) {
            hand[handSize++] = deck[--TOTAL_CARDS];
        }
        return handSize;
    }

    private static String handToString(Card[] hand, int handSize) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < handSize; i++) {
            result.append(hand[i]).append(" ");
        }
        return result.toString().trim();
    }

    private static int playerTurn(Card[] deck, Card[] playerHand, int playerHandSize) {
        Scanner sc = new Scanner(System.in);
        while (getHandValue(playerHand, playerHandSize) < TOTAL_POINTS_TO_WIN) {
            System.out.println("Your current hand value: " + getHandValue(playerHand, playerHandSize));
            System.out.println("Do you want to hit (h) or stand (s)?");
            char choice = sc.next().charAt(0);

            if (choice == 'h') {
                playerHandSize = drawCard(deck, playerHand, playerHandSize);
                System.out.println("You drew: " + playerHand[playerHandSize - 1]);
            } else if (choice == 's') {
                break;
            } else {
                System.out.println("Invalid choice. Please enter 'h' or 's'.");
            }
        }

        System.out.println("Your final hand: " + handToString(playerHand, playerHandSize));
        return playerHandSize;
    }

    private static int computerTurn(Card[] deck, Card[] computerHand, int computerHandSize) {
        System.out.println("Computer's turn:");
        //computer will draw untill computer's score hits 18
        while (getHandValue(computerHand, computerHandSize) < 19) {
            computerHandSize = drawCard(deck, computerHand, computerHandSize);
            System.out.println("Computer drew: " + computerHand[computerHandSize - 1]);
        }

        System.out.println("Computer's final hand: " + handToString(computerHand, computerHandSize));
        return computerHandSize;
    }

    public static void determineWinner(Card[] playerHand, int playerHandSize, Card[] computerHand, int computerHandSize) {
        int playerValue = getHandValue(playerHand, playerHandSize);
        int computerValue = getHandValue(computerHand, computerHandSize);

        System.out.println("Your hand value: " + playerValue);
        System.out.println("computer's hand value: " + computerValue);

        if (playerValue > TOTAL_POINTS_TO_WIN || (computerValue <= TOTAL_POINTS_TO_WIN && computerValue >= playerValue)) {
            System.out.println("computer wins!");
            COMPUTER_TOTAL_SCORE++;
        } else {
            System.out.println("You win!");
            PLAYER_TOTAL_SCORE++;
        }
    }

    private static int getHandValue(Card[] hand, int handSize) {
        int value = 0;
        

    }
}

class Card {
    private String rank;
    private String colour;

    public Card(String r, String c) {
        rank = r;
        colour = c;
    }

    public int getValue() {
        switch (rank) {
            case "1": case "2": case "3": case "4": case "5": case "6": case "7": case "8": case "9": case "10":
                return Integer.parseInt(rank);
        }
    }

    public String getRank() {
        return rank;
    }

    @Override
    public String toString() {
        return rank + " of " + colour;
    }
}


private static int drawCard(Card[] deck, Card[] playerHand, int playerHandSize, Card[] computerHand, int computerHandSize) {
    if (playerHandSize < playerHand.length && computerHandSize < computerHand.length) {
        // Draw card for the player from the top of the deck
        playerHand[playerHandSize++] = deck[--TOTAL_CARDS];

        // Draw card for the computer from the bottom of the deck
        computerHand[computerHandSize++] = deck[TOTAL_CARDS++];
    }
    return playerHandSize;
}