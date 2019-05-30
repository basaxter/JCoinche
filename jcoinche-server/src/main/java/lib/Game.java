package lib;

import com.esotericsoftware.kryonet.Connection;

import javax.smartcardio.Card;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Game {
    static Map<cardValue, Integer> points = new HashMap<cardValue, Integer>();

    public Game() {
        points.put(cardValue.CARD_A, 18);
        points.put(cardValue.CARD_10, 10);
        points.put(cardValue.CARD_R, 4);
        points.put(cardValue.CARD_D, 3);
        points.put(cardValue.CARD_V, 2);
        points.put(cardValue.CARD_9, 0);
        points.put(cardValue.CARD_8, 0);
        points.put(cardValue.CARD_7, 0);
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

    static Integer getPoints(cardValue value) {
        return points.get(value);
    }
}
