package lib;

import com.esotericsoftware.kryonet.Connection;

import java.util.ArrayList;

public class cardPlayer extends Connection {
    private String          name;
    private int             points = 0;
    private boolean         inGame = false;
    private Network.Deck    deck = new Network.Deck();
    public Network.Deck     collected = new Network.Deck();

    void cardPlayer() {
        deck = new Network.Deck();
        deck.cards = new ArrayList<Game.coincheCard>();
    }

    public void setPlayerName(String name) {
        this.name = name;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void setInGame(boolean inGame) {
        this.inGame = inGame;
    }

    public void setDeck(Network.Deck deck) {
        this.deck = deck;
    }

    public String getName() {
        return name;
    }

    public int getPoints() {
        return points;
    }

    public boolean isInGame() {
        return inGame;
    }

    public Network.Deck getDeck() {
        return deck;
    }
}
