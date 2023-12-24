import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Game game = new Game();
        Scanner scanner = new Scanner(System.in);

        boolean playerTurn = true;
        while(!game.getGameState()){
            while (playerTurn) {
                displayPlayerHand(game);
                displayBoards(game);
                System.out.println("Choose an action: 'hit' or 'stand'");
                String action = scanner.nextLine();
                if (action == "hit") {
                    game.hitPlayer();
                } else if (action == "stand") {
                    playerTurn = false;
                } else {
                    System.out.println("Invalid action, please type 'hit' or 'stand'");
                }
            }
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

    private static void displayBoards(Game game) {
        System.out.println("Your board:");
        Card[] board = game.getPlayerBoard();
        int boardSize = game.getPlayerBoardSize();
        if(boardSize == 0){
            System.out.println("Empty!");
            return;
        }
        for (int i = 0; i < boardSize; i++) {
            System.out.println(board[i]);
        }
    }
}
