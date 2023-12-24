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
    private boolean playerWon;
    private boolean computerWon;

    public Game() {
        gameDeck = new Deck();
        gameDeck.shuffle();
        tempPlayerHand = new Card[10]; // Assuming max 10 cards in hand
        tempComputerHand = new Card[10]; // Assuming max 10 cards in hand
        finalPlayerHand = new Card[4];
        finalComputerHand = new Card[4];
        playerBoardSize = 0;
        computerBoardSize = 0;
        playerBoard = new Card[20];
        computerBoard = new Card[20];
        tempPlayerHandSize = 0;
        tempComputerHandSize = 0;
        finalPlayerHandSize = 0;
        finalComputerHandSize = 0;
        playerScore = 0;
        computerScore = 0;
        playerWon = false;
        computerWon = false;
        isOver = false;
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

    public void evaluateResults(){ // if both computer and player stands, and none of them got 20, check who got the bigger value and update scoreboard & end turn
        if(getComputerBoardValue() > 20 && getPlayerBoardValue() > 20){
            System.out.println("Both players busted");
            resetGame();
        }
        if(getComputerBoardValue() > getPlayerBoardValue()){
            computerWon = true;
        }
        else if (getComputerBoardValue() < getPlayerBoardValue()){
            playerWon = true;
        }
        updateScores();
    }

    public void displayScores(){ // display scores
        System.out.println("Player Score: " + playerScore);
        System.out.println("Computer Score: " + computerScore);
    }

    public void updateScores(){ // update scores depending on the situation. This function is called in various places
        if(playerWon){
            System.out.println("Player won this turn.");
            playerScore++;
        }
        else if (computerWon){
            System.out.println("Computer won this turn.");
            computerScore++;
        }
        else{
            System.out.println("It is a tie.");
            playerScore++;
            computerScore++;
        }
        if (playerScore == 3 || computerScore == 3) {
            isOver = true;
        }
        playerWon = false;
        computerWon = false;
        resetGame();
    }

    public void resetGame(){ // after a turn ends, this function resets the boards
        playerBoardSize = 0;
        computerBoardSize = 0;
        playerBoard = new Card[20];
        computerBoard = new Card[20];
    }

    public void computersTurn(){ // MAIN METHOD COMPLEX METHOD I AM DYING MY BROTHER IN CHRIST WHAT IS THIS 
        // TO BE FURTHER IMPLEMENTED, COMPUTER SHOULD BE ABLE TO END TURN, NOT ALWAYS STAND
        // NOT COMPLETELY IMPLEMENTED
        boolean done = false;
        int maxAdd = 0;
        int maxSub = 0;
        int flipAmount = 0;
        int x2Amount = 0;

        Card[] add = new Card[4];
        Card[] sub = new Card[4];
        Card[] flip = new Card[4];
        Card[] x2 = new Card[4];
        for (int i = 0; i < finalComputerHandSize; i++) {
            if(finalComputerHand[i].getType().equals("normal") && finalComputerHand[i].getSign().equals("+")){
                maxAdd += finalComputerHand[i].getValue();
                add[i] = finalComputerHand[i];
            }
            else if(finalComputerHand[i].getType().equals("normal") && finalComputerHand[i].getSign().equals("-")){
                maxSub += finalComputerHand[i].getValue();
                sub[i] = finalComputerHand[i];
            }
            else if(finalComputerHand[i].getType().equals("flip")){
                flipAmount++;
                flip[i] = finalComputerHand[i];
            }
            else if(finalComputerHand[i].getType().equals("flip")){
                x2Amount++;
                x2[i] = finalComputerHand[i];
            }
        }
        while(!done){
            if(getComputerBoardValue() <= 15){
                hitComputer();
            }
            else if(getComputerBoardValue() >= 16 && getComputerBoardValue() <= 20){
                done = true;
            }
            else if(getComputerBoardValue() - maxSub > 20){
                for (int i = 0; i < finalComputerHandSize; i++) {
                    if(finalComputerHand[i].getType().equals("normal") && finalComputerHand[i].getSign().equals("-")){
                        playCardComputer(finalComputerHand[i]);
                    }
                }
                done = true;
            }
            else{
                done = true;
            }
        }
    }

    public void playCardComputer(Card card){ // computer plays card
        // @TODO
        // NOT FULLY IMPLEMENTED
        for (int i = 0; i < finalComputerHandSize; i++) {
            if(finalComputerHand[i].equals(card)){
                finalComputerHand[i] = null;
                finalComputerHandSize--;
                break;
            }
        }
        computerBoard[computerBoardSize++] = card;
    }

    public void playCardPlayer(int index){ // player plays card
        if (finalPlayerHand[index] == null) {
            System.out.println("You have already played that card.");
        }
        else{
            Card card = finalPlayerHand[index];
            if(card.getType().equals("flip")){
                String previousCardSign = playerBoard[playerBoardSize].getSign() == "-" ? "+" :"-";
                playerBoard[playerBoardSize].setSign(previousCardSign);
            }
            else if(card.getType().equals("double")){
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
            computerWon = true;
            updateScores();
            displayScores();
        }
        
    }

    public void checkPlayerBoard(){ // check if at the end of a turn if player got a value of 20 and won immediately.
        if(getPlayerBoardValue() == 20){
            System.out.println("Player won the turn.");
            playerWon = true;
            updateScores();
            displayScores();
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
}