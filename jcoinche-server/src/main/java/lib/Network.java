package lib;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

import java.util.ArrayList;
import java.util.List;

public class Network {
    static public void register (EndPoint endPoint) {
        Kryo kryo = endPoint.getKryo();
        kryo.register(RegisterName.class);
        kryo.register(String[].class);
        kryo.register(ChatMessage.class);
        kryo.register(GameResponse.class);
        kryo.register(java.util.ArrayList.class);
        kryo.register(lib.Game.coincheCard.class);
        kryo.register(lib.Game.cardColor.class);
        kryo.register(lib.Game.cardValue.class);
        kryo.register(lib.Game.coincheCard.class);
        kryo.register(Deck.class);
    }

    static public class RegisterName {
        public String name;
    }

    static public class ChatMessage {
        public String text;
    }

    static public class GameResponse {
        public Game.coincheCard card;
        public String           cardName;
    }

    static public class Deck {
        public List<Game.coincheCard>   cards = new ArrayList<Game.coincheCard>();
    }

}