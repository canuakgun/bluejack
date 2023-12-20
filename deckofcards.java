public class DeckOfCards {
    private Card theCard;
    private int remainingCards = 40; 
    
    DeckOfCards() {
    theCard = new Card();   
    }
    
    public class Deck {
    private Card[] cards;

    // Constructor
    public Deck() {
        initializeDeck();
    }

    // Method to initialize the deck with all possible cards
    private void initializeDeck() {
        String[] ranks = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        String[] colours = {"blue", "yellow", "red", "green"};

        int numberOfCards = ranks.length * colours.length;
        cards = new Card[numberOfCards];

        int index = 0;
        for (String colour : colour) {
            for (String rank : ranks) {
                cards[index] = new Card(rank, colour);
                index++;
            }
        }
    }
    
    public Card[] getCards() {
        return cards;
    }
    
}
public class Card {
    private String rank;
    private String colour;

    // Constructor with parameters to initialize instance variables
    public Card(String r, String c) {
        rank = r;
        colour = c;
    }

    // Getters and setters

    public String getRank() {
        return rank;
    }

    public String getColour() {
        return colour;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }




}
