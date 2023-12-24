public class Card {
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

    // Card representation
    @Override
    public String toString() {
        if (type == "flip"){
            return "+/-";
        }
        else if(type == "double"){
            return "x2";
        }
        return color + " " + value + " " + sign;
    }
}
