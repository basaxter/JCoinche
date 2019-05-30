import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import lib.cardPlayer;
import lib.gameThread;
import lib.Network;
import lib.Game;

import java.io.IOException;
import java.util.*;

public class GameServer {
    private Game                    game;
    private Server                  server;
    private List<cardPlayer>        players = new ArrayList<cardPlayer>();
    private gameThread              coinche;
    private Network.GameResponse    response;

    public void loop() {
        try {
            boolean gameWasOn = false;

            while (true) {
                gameWasOn = coinche.isGameOn();
                if (!coinche.isGameOn() && this.countPlayers() >= 4) {
                    System.out.println("Waiting for 10 seconds...");
                    Network.ChatMessage messageStruct = new Network.ChatMessage();
                    messageStruct.text = "10 seconds until next game...";
                    server.sendToAllTCP(messageStruct);
                    Thread.sleep(10000);
                    coinche.updatePlayers(players);
                    coinche.start();
                    System.out.println("Launched Game.");
                }
                Thread.sleep(500);
                if (gameWasOn && !coinche.isGameOn()) {
                    coinche = new gameThread(server);
                    System.out.println("Ended Game.");
                }
            }
        }
        catch (InterruptedException e) {
            System.out.println("The server was interrupted.");
        }
    }

    public GameServer(int port) {
        game = new Game();
        server = new Server() {
            protected Connection newConnection() {
                return (new cardPlayer());
            }
        };
        coinche = new gameThread(server);

        Network.register(server);

        server.addListener(new Listener() {
            public void received(Connection conn, Object obj) {
                cardPlayer client = (cardPlayer)conn;

                if (obj instanceof Network.RegisterName) {
                    if (client.getName() != null) {
                        return;
                    }
                    String name = ((Network.RegisterName) obj).name;
                    if (name == null) {
                        return;
                    }
                    name = name.trim();
                    if (name.length() == 0) {
                        return;
                    }
                    client.setPlayerName(name);
                    addPlayer(client);
                }
                if (obj instanceof Network.ChatMessage) {
                    if (client.getName() == null) return;
                    Network.ChatMessage chatMessage = (Network.ChatMessage)obj;
                    String message = chatMessage.text;
                    if (message == null || message.isEmpty()) return;
                    message = message.trim();
                    if (message.length() == 0) return;
                    chatMessage.text = client.getName() + ": " + message;
                    server.sendToAllTCP(chatMessage);
                    return;
                }
                if (obj instanceof Network.GameResponse) {
                    response = (Network.GameResponse)obj;
                    if (coinche.isGameOn())
                        coinche.setResponse(response);
                }
            }

            public void diconnected (Connection conn) {
                cardPlayer  client = (cardPlayer)conn;
                if (client.getName() != null)
                    return;
                System.out.println(((cardPlayer)conn).getName() + " has quit.");
            }
        });

        try {
            server.bind(port);
            server.start();
        }
        catch (IOException e) {
            System.out.println("Error: Couldn't bind port.");
        }
    }

    void addPlayer(cardPlayer player) {
        if (player.getName() == null)
            return;
        players.add(player);
        Network.ChatMessage messageStruct = new Network.ChatMessage();
        if (coinche.isGameOn())
            messageStruct.text = "Welcome !\nWAITING UNTIL NEXT GAME...";
        else
            messageStruct.text = "Welcome !\nWaiting for minimal players to connect...";
        player.sendTCP(messageStruct);
        System.out.println(player.getName() + " has joined.");
    }

    public int countPlayers() {
        return (players.size());
    }

    public void finalize() {
        System.out.println("Quitting server...");
        server.close();
    }
}