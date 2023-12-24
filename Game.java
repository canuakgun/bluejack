import java.text.SimpleDateFormat;  
import java.util.Date;  
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
    private int playerScore;
    private int computerScore;
    private boolean isOver;
    private boolean playerWonTurn;
    private boolean computerWonTurn;
    private boolean playerWonGame;
    private boolean computerWonGame;
    private History gameHistory; // if value -1 => game not played yet, value 0 => computer won, if value => player won

    public Game() {
        gameDeck = new Deck();
        gameDeck.shuffle();
        tempPlayerHand = new Card[10]; // Assuming max 10 cards in hand
        tempComputerHand = new Card[10]; // Assuming max 10 cards in hand
        finalPlayerHand = new Card[4];
        finalComputerHand = new Card[4];
        playerBoardSize = 0;
        computerBoardSize = 0;
        playerBoard = new Card[9];
        computerBoard = new Card[9];
        tempPlayerHandSize = 0;
        tempComputerHandSize = 0;
        finalPlayerHandSize = 0;
        finalComputerHandSize = 0;
        playerScore = 0;
        computerScore = 0;
        playerWonTurn = false;
        computerWonTurn = false;
        playerWonGame = false;
        computerWonGame = false;
        isOver = false;
        gameHistory = new History();
        dealInitialCards();
    }

    private void dealInitialCards() { // creates first player cards of size 10
        for (int i = 0; i < 5; i++) { // 5 basic cards
            tempComputerHand[tempComputerHandSize++] = gameDeck.dealTopCard();
            tempPlayerHand[tempPlayerHandSize++] = gameDeck.dealBottomCard();
        }
        generateAdditionalCards(); // rest of the 5 cards
        randomizeFinalHands(); // pick random 4 cards out of then and put it to hand
    }

    private void generateAdditionalCards() { // generate final 5 cards
        for (int i = 0; i < 3; i++) {
            addRandomCardToHand(tempPlayerHand, tempPlayerHandSize++); // add 3 normal cards to player's hand
            addRandomCardToHand(tempComputerHand, tempComputerHandSize++); // add 3 normal cards to computer's hand
        }
        for (int i = 0; i < 2; i++) {
            addSpecialCardToHand(tempPlayerHand, tempPlayerHandSize++); // add 2 normal cards to player's hand
            addSpecialCardToHand(tempComputerHand, tempComputerHandSize++); // add 2 normal cards to computer's hand
        }
    }

    private void randomizeFinalHands(){
        for(int i = 0; i < 4; i++){ // randomly select 4 cards out of 10 for both player and computer
            finalPlayerHand[i] = tempPlayerHand[(int)(Math.random() * 10)];
            finalComputerHand[i] = tempComputerHand[(int)(Math.random() * 10)];
        }
        finalComputerHandSize = 4;
        finalPlayerHandSize = 4;
    }

    private void addRandomCardToHand(Card[] hand, int index) { // add rando card to hand
        int value = (int)(Math.random() * 6) + 1; // Random value between 1 and 6
        String color = getRandomColor();
        String sign = Math.random() < 0.5 ? "+" : "-"; // 50% chance of +, and 50% chance of -
        hand[index] = new Card(value, color, sign, "normal"); // create new card and add it to hand
    }

    private void addSpecialCardToHand(Card[] hand, int index) {
        if ((int)Math.random() < 0.8) { // 80% a normal signed card is generated
            // 80% chance to be a signed card
            addRandomCardToHand(hand, index);
        } else {
            // 20% chance to be a flip or double card
            String specialType = Math.random() < 0.5 ? "flip" : "double";
            hand[index] = new Card(0, "", "", specialType);
        }
    }

    public int getPlayerBoardValue(){ // get the sum of current cards in the player's board
        int sum = 0;
        for(int i = 0; i < playerBoardSize; i++){
            if(playerBoard[i].getSign().equals("-")){
                sum -= playerBoard[i].getValue();
            }
            else if(playerBoard[i].getSign().equals("+")){
                sum += playerBoard[i].getValue();
            }
        }
        return sum;
    }

    public int getComputerBoardValue(){ // get the sum of current cards in the computer's board
        int sum = 0;
        for(int i = 0; i < computerBoardSize; i++){
            if(computerBoard[i].getSign().equals("-")){
                sum -= computerBoard[i].getValue();
            }
            else if(computerBoard[i].getSign().equals("+")){
                sum += computerBoard[i].getValue();
            }
        }
        return sum;
    }

    public boolean checkAllBlue(Card[] deck){
        if(deck.length == 0){
            return false;
        }
        for (int i = 0; i < deck.length; i++) {
            if(!deck[i].getColor().equals("deck")){
                return false;
            }
        }
        return true;
    }

    public void evaluateResults(){ // if both computer and player stands, and none of them got 20, check who got the bigger value and update scoreboard & end turn
        if (getPlayerBoardValue() == 20 && checkAllBlue(playerBoard)) {  // if player has 20 and all blue
            playerWonGame = true;
        }
        else if(getComputerBoardValue() == 20 && checkAllBlue(computerBoard)){ // if computer has 20 and all blue
            computerWonGame = true;
        }
        else if(getComputerBoardValue() > 20 && getPlayerBoardValue() > 20){
            System.out.println("Both players busted");
        }
        else if(getComputerBoardValue() > 20){
            playerWonTurn = true;
        }
        else if(getPlayerBoardValue() > 20){
            computerWonTurn = true;
        }
        else if(getComputerBoardValue() == 20 && getPlayerBoardValue() == 20){
            // do nothing, updateScores() will see that both players didnt win and print tie
        }
        else if(getComputerBoardValue() == 20){
            computerWonTurn = true;
        }
        else if(getPlayerBoardValue() == 20){
            playerWonTurn = true;
        }
        else if(getComputerBoardValue() > getPlayerBoardValue()){
            computerWonTurn = true;
        }
        else if (getComputerBoardValue() < getPlayerBoardValue()){
            playerWonTurn = true;
        }
        updateScores();
    }

    public void displayScores(){ // display scores
        System.out.println("Player Score: " + playerScore);
        System.out.println("Computer Score: " + computerScore);
    }

    public void updateScores(){ // update scores depending on the situation. This function is called in various places
        if(playerWonGame){
            playerScore = 3;
        }
        else if(computerWonGame){
            computerScore = 3;
        }
        else if(playerWonTurn){
            System.out.println("Player won this turn.");
            playerScore++;
        }
        else if (computerWonTurn){
            System.out.println("Computer won this turn.");
            computerScore++;
        }
        else{
            System.out.println("It is a tie.");
        }
        if (playerScore == 3 || computerScore == 3) {
            isOver = true;
        }
        playerWonTurn = false;
        computerWonTurn = false;
        resetGame();
    }

    public void resetGame(){ // after a turn ends, this function resets the boards
        playerBoardSize = 0;
        computerBoardSize = 0;
        for (int i = 0; i < computerBoard.length; i++) {
            playerBoard[i] = null;
            computerBoard[i] = null;
        }
    }

    public void computersTurn(){ // MAIN METHOD COMPLEX METHOD I AM DYING MY BROTHER IN CHRIST WHAT IS THIS 
        // TO BE FURTHER IMPLEMENTED, COMPUTER SHOULD BE ABLE TO END TURN, NOT ALWAYS STAND
        // NOT COMPLETELY IMPLEMENTED
        boolean done = false;
        int maxSub = 0;
        int flipAmount = 0;
        int x2Amount = 0;

        for (int i = 0; i < finalComputerHandSize; i++) {
            if (finalComputerHand[i] != null) {
                if(finalComputerHand[i].getType().equals("normal") && finalComputerHand[i].getSign().equals("-")){
                    maxSub += finalComputerHand[i].getValue();
                }
                else if(finalComputerHand[i].getType().equals("flip")){
                    flipAmount++;
                }
                else if(finalComputerHand[i].getType().equals("flip")){
                    x2Amount++;
                }
            }
        }
        while(!done){
            for (int i = 0; i < finalComputerHandSize; i++) {
                if(finalComputerHand[i] != null && finalComputerHand[i].getSign().equals("+")){ // first it checks if he can reach 20 with using any of its cards
                    if(getComputerBoardValue() + finalComputerHand[i].getValue() == 20){
                        System.out.println("Computer played the card: " + finalComputerHand[i]);
                        playCardComputer(finalComputerHand[i]);
                    }
                }
            }
            if(getComputerBoardValue() <= 15){ // if computer has less than 15, it will hit
                hitComputer();
            }
            else if(getComputerBoardValue() >= 16 && getComputerBoardValue() <= 20){ // OPTIMAL INTERVAL, WILL STAY BETWEEN VALUES
                done = true;
            }
            else if(getComputerBoardValue() > 20 && getComputerBoardValue() - maxSub < 20){ // IF WE ARE ABOVE 20, AND WE CAN REACH BELOW 20, WE WILL PLAY CARDS UNTIL WE REACH
                for (int i = 0; i < finalComputerHandSize; i++) {
                    if(finalComputerHand[i] != null && finalComputerHand[i].getSign().equals("-")){
                        System.out.println("Computer played the card: " + finalComputerHand[i]);
                        playCardComputer(finalComputerHand[i]);
                    }
                    if(getComputerBoardValue() < 20){
                        break;
                    }
                }
            }
            else if(getComputerBoardValue() - maxSub > 20){ 
                done = true;
            }
            else if(flipAmount > 0){ // IF WE HAVE FLIP
                if(computerBoard[computerBoardSize].getSign().equals("-") && getComputerBoardValue() < 20){ // IF FLIP IS LOGICAL TO BE USED
                    if(computerBoard[computerBoardSize].getValue() * 2 + getComputerBoardValue() <= 20 && computerBoard[computerBoardSize].getValue() * 2 + getComputerBoardValue() >= 16){ // IF USING FLIP PUTS US IN THE OPTIMAL RANGE
                        for (int i = 0; i < finalComputerHandSize; i++) {
                            if(finalComputerHand[i] != null && finalComputerHand[i].getType().equals("flip")){
                                flipAmount--;
                                System.out.println("Computer played the card: " + finalComputerHand[i]);
                                playCardComputer(finalComputerHand[i]);
                            }
                        }
                    }
                }
                else if(computerBoard[computerBoardSize].getSign().equals("+") && getComputerBoardValue() > 20){ // IF FLIP IS LOGICAL TO BE USED
                    if( getComputerBoardValue() - computerBoard[computerBoardSize].getValue() * 2 <= 20 && getComputerBoardValue() - computerBoard[computerBoardSize].getValue() * 2 >= 16){ // IF USING FLIP PUTS US IN THE OPTIMAL RANGE
                        for (int i = 0; i < finalComputerHandSize; i++) {
                            if(finalComputerHand[i] != null && finalComputerHand[i].getType().equals("flip")){
                                flipAmount--;
                                System.out.println("Computer played the card: " + finalComputerHand[i]);
                                playCardComputer(finalComputerHand[i]);
                            }
                        }
                    }
                }
            }
            else if(x2Amount > 0){ // IF WE HAVE DOUBLE
                if(computerBoard[computerBoardSize].getSign().equals("+") && getComputerBoardValue() < 20){ // IF DOUBLE IS LOGICAL TO BE USED
                    if(computerBoard[computerBoardSize].getValue() + getComputerBoardValue() <= 20 && computerBoard[computerBoardSize].getValue() + getComputerBoardValue() >= 16){ // IF USING DOUBLE PUTS US IN THE OPTIMAL RANGE
                        for (int i = 0; i < finalComputerHandSize; i++) {
                            if(finalComputerHand[i] != null && finalComputerHand[i].getType().equals("double")){
                                x2Amount--;
                                System.out.println("Computer played the card: " + finalComputerHand[i]);
                                playCardComputer(finalComputerHand[i]);
                            }
                        }
                    }
                }
                else if(computerBoard[computerBoardSize].getSign().equals("-") && getComputerBoardValue() > 20){ // IF DOUBLE IS LOGICAL TO BE USED
                    if(getComputerBoardValue() - computerBoard[computerBoardSize].getValue() <= 20 && getComputerBoardValue() - computerBoard[computerBoardSize].getValue() >= 16){ // IF USING DOUBLE PUTS US IN THE OPTIMAL RANGE
                        for (int i = 0; i < finalComputerHandSize; i++) {
                            if(finalComputerHand[i] != null && finalComputerHand[i].getType().equals("double")){
                                x2Amount--;
                                System.out.println("Computer played the card: " + finalComputerHand[i]);
                                playCardComputer(finalComputerHand[i]);
                            }
                        }
                    }
                }
            }
        }
    }

    public void playCardComputer(Card card){ // computer plays card
        int index = -1;
        for (int i = 0; i < finalComputerHandSize; i++) {
            if(finalComputerHand[i] != null && finalComputerHand[i].equals(card)){
                index = i;
                break;
            }
        }
        if (finalComputerHand[index] != null) {
            if(card.getType().equals("flip")){ // IF CARD IS FLIP, FLIP THE SIGN OF THE PREVIOUS CARD
                String previousCardSign = computerBoard[computerBoardSize].getSign() == "-" ? "+" :"-";
                computerBoard[computerBoardSize].setSign(previousCardSign);
            }
            else if(card.getType().equals("double")){ // IF CARD IS DOUBLE, DOUBLE THE VALUE OF THE PREVIOUS CARD
                computerBoard[computerBoardSize].setValue(computerBoard[computerBoardSize].getValue() * 2);
            }
            finalComputerHand[index] = null;
            computerBoard[computerBoardSize++] = card;
            // finalComputerHand = removeCard(finalComputerHand, index);
            // finalComputerHandSize--;
        }
    }

    public void playCardPlayer(int index){ // player plays card
        if (finalPlayerHand[index] == null) {
            System.out.println("You have already played that card.");
        }
        else{
            Card card = finalPlayerHand[index];
            if(card.getType().equals("flip")){ // IF CARD IS FLIP, FLIP THE SIGN OF THE PREVIOUS CARD
                String previousCardSign = playerBoard[playerBoardSize].getSign() == "-" ? "+" :"-";
                playerBoard[playerBoardSize].setSign(previousCardSign);
            }
            else if(card.getType().equals("double")){ // IF CARD IS DOUBLE, DOUBLE THE VALUE OF THE PREVIOUS CARD
                playerBoard[playerBoardSize].setValue(playerBoard[playerBoardSize].getValue() * 2);
            }
            playerBoard[playerBoardSize++] = card;
            finalPlayerHand = removeCard(finalPlayerHand, index);
            finalPlayerHandSize--;
        }
    }

    public Card[] removeCard(Card[] deck, int index){
        Card[] newArray = new Card[deck.length-1];
        for (int i = 0, k = 0; i < deck.length; i++) { 
  
            // if the index is 
            // the removal element index 
            if (i == index) { 
                continue; 
            } 
  
            // if the index is not 
            // the removal element index 
            newArray[k++] = deck[i]; 
        }
        return newArray;
    }

    public void checkComputerBoard(){ // check if at the end of a turn if computer got a value of 20 and won immediately.
        if(getComputerBoardValue() == 20){
            System.out.println("Computer won the turn.");
            computerWonTurn = true;
            updateScores();
            displayScores();
        }
        
    }

    public void checkPlayerBoard(){ // check if at the end of a turn if player got a value of 20 and won immediately.
        if(getPlayerBoardValue() == 20){
            System.out.println("Player won the turn.");
            playerWonTurn = true;
            updateScores();
            displayScores();
        }
    }

    public Turn turnResult(){
        Turn turn = new Turn(new Date(), "" + computerScore, "" + playerScore);
        return turn;
    }

    private String getRandomColor() {
        String[] colors = {"blue", "yellow", "red", "green"};
        return colors[(int)(Math.random() * colors.length)];
    }

    public void hitPlayer() {
        if(playerBoardSize <= 9){
            playerBoard[playerBoardSize++] = gameDeck.dealTopCard();
        }
        else{
            System.out.println("Your board is full, cannot hit anymore.");
        }
    }

    public void hitComputer() {
        if (computerBoardSize <= 9) {
            computerBoard[computerBoardSize++] = gameDeck.dealTopCard();
        }
    }

    public Card[] getPlayerHand() {
        return finalPlayerHand;
    }

    public Card[] getComputerHand() {
        return finalComputerHand;
    }

    public Card[] getPlayerBoard(){
        return playerBoard;
    }

    public Card[] getComputerBoard(){
        return computerBoard;
    }

    public int getPlayerHandSize() {
        return finalPlayerHandSize;
    }

    public int getComputerBoardSize() {
        return computerBoardSize;
    }

    public int getComputerHandSize() {
        return finalComputerHandSize;
    }

    public int getPlayerBoardSize(){
        return playerBoardSize;
    }

    public boolean getGameState(){
        return isOver;
    }

    public void setGameState(Boolean bool){
        isOver = bool;
    }
}