package lib;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class jhGame {
    static Map<cardValue, Integer> points = new HashMap<cardValue, Integer>();
    static Map<cardValue, String> tradsValues = new HashMap<cardValue, String>();
    static Map<cardColor, String> tradsColor = new HashMap<cardColor, String>();

    public Game() {
        points.put(cardValue.CARD_A, 18);
        points.put(cardValue.CARD_10, 10);
        points.put(cardValue.CARD_R, 4);
        points.put(cardValue.CARD_D, 3);
        points.put(cardValue.CARD_V, 2);
        points.put(cardValue.CARD_9, 0);
        points.put(cardValue.CARD_8, 0);
        points.put(cardValue.CARD_7, 0);

        tradsValues.put(cardValue.CARD_A, "Ace");
        tradsValues.put(cardValue.CARD_R, "King");
        tradsValues.put(cardValue.CARD_D, "Queen");
        tradsValues.put(cardValue.CARD_V, "valet");
        tradsValues.put(cardValue.CARD_10, "10");
        tradsValues.put(cardValue.CARD_9, "9");
        tradsValues.put(cardValue.CARD_8, "8");
        tradsValues.put(cardValue.CARD_7, "7");

        tradsColor.put(cardColor.CARREAUX, "Diamonds");
        tradsColor.put(cardColor.PIC, "Spades");
        tradsColor.put(cardColor.TREFLE, "Clubs");
        tradsColor.put(cardColor.COEUR, "Hearts");
    }

    static public class    coincheCard {
        private cardColor   color;
        private cardValue   value;

        public cardColor getColor() {
            return color;
        }

        public cardValue getValue() {
            return value;
        }

        public void setColor(cardColor color) {
            this.color = color;
        }

        public void setValue(cardValue value) {
            this.value = value;
        }
    }

    public enum cardColor {
        CARREAUX,
        PIC,
        TREFLE,
        COEUR
    }

    public enum cardValue {
        CARD_A,
        CARD_10,
        CARD_R,
        CARD_D,
        CARD_V,
        CARD_9,
        CARD_8,
        CARD_7
    }

    public static Map<cardValue, Integer> getPoints() {
        return points;
    }

    public static Map<cardValue, String> getTradsValues() {
        return tradsValues;
    }

    public static Map<cardColor, String> getTradsColor() {
        return tradsColor;
    }
}
