import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        History history = new History();
        boolean playerTurn = true;
        boolean end = false;

        //@TODO
        //BETTER DISPLAY OF BOARDS (NEED TIDINESS)
        //PLAY CARD FUNCTION FOR PLAYER (AND FOR THE COMPUTER)
        while(!end){
            System.out.println("1: To start a new game, type 'start'");
            System.out.println("2: To display the game history, type 'history'");
            System.out.println("3: To end the game, type 'end'");
            String start = scanner.nextLine(); // READ user input
            if(start.equals("start")){
                Game game = new Game();
                while(!game.getGameState()){ // while game is not ended yet
                    while (playerTurn) { // while the player didn't press stand yet
                        createGameTable(game);
                        System.out.println("\n1: Choose an action by typing it: 'hit' or 'stand' or 'end'");
                        System.out.println("2: To play your cards: type the index of the card (either '1', '2', '3', or '4')");
                        
                        String action = scanner.nextLine(); // READ user input
                        if (action.equals("hit")) {
                            game.hitPlayer(); // player said hit, add a card to his board
                        } else if (action.equals("stand")) {
                            playerTurn = false; // player said stand, end his turn, wait for computer's response
                        } else if (action.equals("end")){
                            break; //  player said end, computer will respond accordingly and will not say stand, turn will come back to player
                        } else if (action.equals("1") || action.equals("2") || action.equals("3") || action.equals("4")){
                            int choice = Integer.parseInt(action);
                            game.playCardPlayer(choice - 1);
                        } 
                        else{
                            System.out.println("Invalid action"); 
                        }
                    }
                    //game.checkPlayerBoard(); // check if player won the turn by getting 20 or won the game by getting blue 20
                    game.computersTurn(); // COMPUTER'S ALGORITHM, MOST COMPLEX FUNCTION SO FAR, NOT YET FULLY IMPLEMENTED
                    createGameTable(game);
                    //game.checkComputerBoard(); // check if computer won the turn by getting 20 or won the game by getting blue 20
                    if(!playerTurn){ // IF BOTH PLAYER AND COMPUTER COULDNT REACH 20, AND NOW WE EVALUATE WHO GOT HIGHER
                        game.evaluateResults(); // CHECK WHO GOT HIGHER AND UPDATE SCOREBOARD, RESET THE TURN
                        playerTurn = true;
                        game.displayScores();
                    }
                }
                Turn turn = game.turnResult();
                history.addTurn(turn);
            }
            else if (start.equals("history")) {
                history.showHistory();
            }
            else if(start.equals("end")){
                end = true;
            }
            else{
                System.out.println("Invalid action");
            }
        }
        scanner.close();
    }

    // DISPLAY FUNCTIONS
    //@TODO => WILL BE TRANSFERRED TO THE GAME CLASS

    private static void displayPlayerHand(Game game) {
        System.out.print("Your hand: ");
        Card[] hand = game.getPlayerHand();
        int handSize = game.getPlayerHandSize();
        if(handSize == 0){
            System.out.println("Empty!");
            return;
        }
        for (int i = 0; i < handSize; i++) {
            System.out.print(hand[i]);
            if(i != handSize-1){
                System.out.print(" ");
            }
        }
        System.out.println();
    }

    private static void displayPlayerBoard(Game game) {
        System.out.print("Your board: ");
        Card[] board = game.getPlayerBoard();
        int boardSize = game.getPlayerBoardSize();
        if(boardSize == 0){
            System.out.println("Empty!");
            System.out.println("Total Value in the Player's Board: " + game.getPlayerBoardValue());
            return;
        }
        for (int i = 0; i < boardSize; i++) {
            System.out.print(board[i]);
            if(i != boardSize-1){
                System.out.print(" ");
            }
        }
        System.out.println();
        System.out.println("Total Value in the Player's Board: " + game.getPlayerBoardValue());
    }

    private static void displayComputerHand(Game game) {
        System.out.print("Computer hand: ");
        Card[] hand = game.getComputerHand();
        int handSize = game.getComputerHandSize();
        for (int i = 0; i < handSize; i++) {
            if(hand[i] == null){
                System.out.print("O");
            }
            else{
                System.out.print("X");
            }
            if(i != handSize-1){
                System.out.print(" ");
            }
        }
        System.out.println();
    }

    private static void displayComputerBoard(Game game) {
        System.out.print("Computer board: ");
        Card[] board = game.getComputerBoard();
        int boardSize = game.getComputerBoardSize();
        if(boardSize == 0){
            System.out.println("Empty!");
            System.out.println("Total Value in the Computer's Board: " + game.getComputerBoardValue());
            return;
        }
        for (int i = 0; i < boardSize; i++) {
            System.out.print(board[i]);
            if(i != boardSize-1){
                System.out.print(" ");
            }
        }
        System.out.println();
        System.out.println("Total Value in the Computer's Board: " + game.getComputerBoardValue());
    }

    private static void createGameTable(Game game){
        System.out.println();
        displayComputerHand(game);
        displayComputerBoard(game);
        System.out.println();
        displayPlayerHand(game);
        displayPlayerBoard(game);
    }
}
