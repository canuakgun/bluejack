import java.util.Date;

public class Turn {
    private Date date;
    private String computerScore;
    private String playerScore;

    public Turn(Date date, String computerScore, String playerScore) {
        this.date = date;
        this.computerScore = computerScore;
        this.playerScore = playerScore;
    }

    public Date getDate(){
        return date;
    }
    public String getComputerScore(){
        return computerScore;
    }
    public String getPlayerScore(){
        return playerScore;
    }
}
