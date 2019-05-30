package lib;

import com.esotericsoftware.kryonet.Server;

import java.util.*;

public class gameThread extends Thread {
    private Game                    game = new Game();
    private boolean                 gameIsOn;
    private Network.GameResponse    response;
    private Server                  server;
    List<cardPlayer>                players;

    public gameThread(Server srv) {
        this.server = srv;
    }

    public void run() {
        Network.ChatMessage messageStruct = new Network.ChatMessage();
        messageStruct.text = "==============\n== NEW GAME ==\n==============";
        server.sendToAllTCP(messageStruct);
        playGame();
    }

    public void playGame()
    {
        List<cardPlayer> playing = new ArrayList<cardPlayer>();
        List<Game.coincheCard>  played = new ArrayList<Game.coincheCard>();

        gameIsOn = true;
        try {
            for (cardPlayer player : players) {
                player.setInGame(true);
                playing.add(player);
            }
            distributeCards(playing);
            for (int i = 0; i <= 8; i++) {
                for (cardPlayer player : playing) {
                    boolean found = false;
                    response = null;
                    player.sendTCP(player.getDeck());
                    while (response == null)
                        Thread.sleep(200);
                    for (Game.coincheCard card : player.getDeck().cards) {
                        if (card.getColor() == response.card.getColor()
                                && card.getValue() == response.card.getValue()) {
                            played.add(card);
                            player.getDeck().cards.remove(card);
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        endGame(playing, "Someone has tried to cheat !");
                        gameIsOn = false;
                        return;
                    }
                    Network.ChatMessage messageStruct = new Network.ChatMessage();
                    messageStruct.text = player.getName() + " --> " + response.cardName;
                    server.sendToAllTCP(messageStruct);
                }
                int max = 0;
                int ibis = 0;
                int imax = 0;
                for (Game.coincheCard card : played) {
                    if (game.getPoints(card.getValue()) > max) {
                        max = game.getPoints(card.getValue());
                        imax = ibis;
                    }
                    ibis += 1;
                }
                for (Game.coincheCard playedCard : played)
                    players.get(imax).collected.cards.add(playedCard);
                Network.ChatMessage messageStruct = new Network.ChatMessage();
                messageStruct.text = players.get(imax).getName() + " collects the cards.\n------------------------";
                server.sendToAllTCP(messageStruct);
                played.clear();
            }
            Network.ChatMessage message = new Network.ChatMessage();
            message.text = getWinner(playing) + " wins.";
            server.sendToAllTCP(message);
            gameIsOn = false;
        }
        catch (InterruptedException e) {
            System.out.println("Some weires exception just showed up.\nError : " + e.getMessage());
        }
    }

    void distributeCards(List<cardPlayer> players) {
        Random rand = new Random();
        final List<Game.cardColor> colors =
                Collections.unmodifiableList(Arrays.asList(Game.cardColor.values()));
        final List<Game.cardValue> values =
                Collections.unmodifiableList(Arrays.asList(Game.cardValue.values()));

        for (cardPlayer player : players) {
            for (int i=0; i<=8; i++) {
                Game.coincheCard card = new Game.coincheCard();

                card.setValue(values.get(rand.nextInt(values.size())));
                card.setColor(colors.get(rand.nextInt(colors.size())));
                player.getDeck().cards.add(card);
            }
        }
    }

    void endGame(List<cardPlayer> players, String message) {
        Network.ChatMessage messageStruct = new Network.ChatMessage();
        messageStruct.text = message;
        server.sendToAllTCP(messageStruct);
        for (cardPlayer player : players) {
            player.setPoints(0);
            player.collected.cards.clear();
            player.setInGame(false);
        }
    }

    String getWinner(List<cardPlayer> players) {
        int imax = 0;
        int max = 0;
        int i = 0;

        for (cardPlayer player : players) {
            for (Game.coincheCard card : player.collected.cards)
                player.setPoints(player.getPoints() + game.getPoints(card.getValue()));
            if (player.getPoints() > max) {
                max = player.getPoints();
                imax = i;
            }
            i += 1;
        }
        return (players.get(imax).getName());
    }

    public void updatePlayers(List<cardPlayer> players) {
        this.players = players;
    }

    public void setResponse(Network.GameResponse resp) {
        this.response = resp;
    }

    public boolean isGameOn() {
        return gameIsOn;
    }
    public void setGameStatus(boolean status) {
        gameIsOn = status;
    }
}
