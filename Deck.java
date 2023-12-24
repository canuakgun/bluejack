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
                cards[size++] = new Card(i, color, "", "normal");
            }
        }
    }

    public void shuffle() {
        for (int i = 0; i < size; i++) {
            int index = (int) (Math.random() * size);
            Card temp = cards[i];
            cards[i] = cards[index];
            cards[index] = temp;
        }
    }

    public Card dealTopCard() {
        if (size == 0) return null;
        return cards[--size];
    }

    public Card dealBottomCard() {
        if (size == 0) return null;
        Card card = cards[0];
        System.arraycopy(cards, 1, cards, 0, --size);
        return card;
    }
}
