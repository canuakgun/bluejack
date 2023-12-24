public class Card {
    String RESET = "\u001B[0m";
    String RED = "\u001B[31m";
    String GREEN = "\u001B[32m";
    String YELLOW = "\u001B[33m";
    String BLUE = "\u001B[34m";
    private int value;
    private String color;
    private String sign; // "+" or "-" or "flip" or "double"
    private String type;

    public Card(int value, String color, String sign, String type) {
        this.value = value;
        this.color = color;
        this.sign = sign;
        this.type = type;
    }

    // Getters
    public String getType(){
        return type;
    }
    
    public int getValue() {
        return value;
    }

    public String getColor() {
        return color;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public void setValue(int value) {
        this.value = value;
    }

    // Card representation
    @Override
    public String toString() {
        if (type == "flip"){
            return "+/-";
        }
        else if(type == "double"){
            return "x2";
        }
        if( color.equals("yellow")){
            return YELLOW + sign + value + RESET; // MAKE TEXT YELLOW, THEN RESET
        }
        else if( color.equals("green")){
            return GREEN + sign + value + RESET; // MAKE TEXT GREEN, THEN RESET
        }
        else if( color.equals("red")){
            return RED + sign + value  + RESET; // MAKE TEXT RED, THEN RESET
        }
        else if(color.equals("blue")){
            return BLUE + sign + value  + RESET; // MAKE TEXT BLUE, THEN RESET
        }
        else{
            return sign + value;
        }
    }
}
