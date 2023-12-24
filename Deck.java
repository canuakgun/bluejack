public class Deck {
    private Card[] cards;
    private int size;

    public Deck() {
        cards = new Card[40]; // Assuming 40 cards initially (10 for each color)
        size = 0;
        initializeDeck();
    }

    private void initializeDeck() {
        String[] colors = {"blue", "yellow", "red", "green"};
        for (String color : colors) {
            for (int i = 1; i <= 10; i++) {
                cards[size++] = new Card(i, color, "+", "normal");
            }
        }
    }

    public void addCards(Card[] newCards){
        Card[] temp = new Card[cards.length + newCards.length];
        for (int i = 0; i < cards.length; i++) {
            temp[i] = cards[i];
        }
        for (int i = cards.length; i < newCards.length; i++) {
            temp[i] = newCards[i];
        }
        cards = temp;
    }

    public Card[] getCards(){
        return cards;
    }

    public void shuffle() {
        for (int i = 0; i < size; i++) {
            int index = (int) (Math.random() * size); // GET RANDOM INDEX
            Card temp = cards[i]; // SWAP THE VALUES BETWEEN i AND RANDOM INDEX
            cards[i] = cards[index]; // SWAP THE VALUES BETWEEN i AND RANDOM INDEX
            cards[index] = temp; // SWAP THE VALUES BETWEEN i AND RANDOM INDEX
        }
    }

    public Card dealTopCard() {
        if (size == 0) return null;
        Card[] temp = new Card[cards.length-1];
        Card card = cards[cards.length-1];
        for (int i = temp.length - 1; i >= 0; i--) {
            temp[i] = cards[i];
        }

        cards = temp;
        return card;
    }

    public Card dealBottomCard() {
        if (size == 0) return null;
        Card[] temp = new Card[cards.length-1];
        Card card = cards[0];
        for (int i = 0; i < temp.length; i++) {
            temp[i] = cards[i+1];
        }

        cards = temp;
        return card;
    }

    public int getLength(){
        return cards.length;
    }
}
