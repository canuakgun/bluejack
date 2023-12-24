import java.util.Date;

public class History {
    private Turn[] turns;

    public History() {
        turns = new Turn[10];
    }

    public void showHistory(){
        for (int i = turns.length - 1; i >= 0; i--) {
            if (turns[i] == null) {
                System.out.println((10 - i)+" Game not played yet.");
            }
            else{
                System.out.println((10 - i) + ": Player: " + turns[i].getPlayerScore() + " - " + "Computer: " + turns[i].getComputerScore() + "," + turns[i].getDate());
            }
            
        }
    }

    public int getLength(){
        return turns.length;
    }

    public Turn getTurn(int index){
        return turns[index];
    }

    public void setTurn(Turn turn, int index){
        turns[index] = turn;
    }

    public void addTurn(Turn turn){
        for (int i = 0; i < turns.length - 1; i++) {
            if(turns[i+1] != null){
                turns[i] = turns[i+1];
            }
        }
        turns[turns.length-1] = turn;
    }
}
