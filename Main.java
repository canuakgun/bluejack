import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Game game = new Game();
        Scanner scanner = new Scanner(System.in);

        boolean playerTurn = true;
        while(!game.getGameState()){
            while (playerTurn) {
                displayPlayerHand(game);
                displayPlayerBoard(game);
                System.out.println("Choose an action: 'hit' or 'stand' or 'end'");
                String action = scanner.nextLine();
                if (action.equals("hit")) {
                    game.hitPlayer();
                } else if (action.equals("stand")) {
                    playerTurn = false;
                } else if (action.equals("end")){
                    System.out.println("Turn ended.");
                } else {
                    System.out.println("Invalid action, please type 'hit' or 'stand'");
                }
            }
            game.checkPlayerBoard(); // check if player won the turn by getting 20 or won the game by getting blue 20
            game.computersTurn();
            displayComputerHand(game);
            displayComputerBoard(game);
            game.checkComputerBoard(); // check if computer won the turn by getting 20 or won the game by getting blue 20
            if(!playerTurn && game.getPlayerBoardValue() != 20){
                game.evaluateResults();
                playerTurn = true;
                game.displayScores();
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

    private static void displayPlayerBoard(Game game) {
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
        System.out.println(game.getPlayerBoardValue());
    }

    private static void displayComputerHand(Game game) {
        System.out.println("Computer hand:");
        Card[] hand = game.getComputerHand();
        int handSize = game.getComputerHandSize();
        for (int i = 0; i < handSize; i++) {
            if(hand[i] == null){
                System.out.print("O");
            }
            else{
                System.out.print("X");
            }
            System.out.println();
        }
    }

    private static void displayComputerBoard(Game game) {
        System.out.println("Computer board:");
        Card[] board = game.getComputerBoard();
        int boardSize = game.getComputerBoardSize();
        if(boardSize == 0){
            System.out.println("Empty!");
            return;
        }
        for (int i = 0; i < boardSize; i++) {
            System.out.println(board[i]);
        }
        System.out.println(game.getComputerBoardValue());
    }
}
