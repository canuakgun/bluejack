import java.util.Scanner;

import javax.smartcardio.Card;

public class Main {
    public static void main(String[] args) {
        Game game = new Game();
        Scanner scanner = new Scanner(System.in);

        boolean playerTurn = true;
        boolean gameState = game.getGameState();
        System.out.println(gameState);

        while (playerTurn) {
            displayPlayerHand(game);
            System.out.println("Choose an action: 'hit' or 'stand'");
            String action = scanner.nextLine();

            // will be implemented hit or stand
        }

        scanner.close();
    }

    private static void displayPlayerHand(Game game) {
        System.out.println("Your hand:");
        Card[] hand = game.getPlayerHand();
        int handSize = game.getPlayerHandSize();
        for (int i = 0; i < handSize; i++) {
            System.out.println(hand[i]);
        }
    }
}
