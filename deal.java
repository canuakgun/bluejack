public class deal {
    public void deal(){
        for (int i = 0; i < 40; i++) {
           String colour = colours[deck[i] / 10];
           String rank = ranks[deck[i] % 10];
           System.out.println( rank + " of " + colour);
           System.out.println("Remaining cards: " + remainingCards);
         }
        }
    }
    //Dealer class
    public class Dealer {
        public static void main(String[] args){
            DeckOfCards player = new DeckOfCards();
            player.deal();
            DeckOfCards computer = new DeckOfCards();
            computer.deal();
        }
    }










}
