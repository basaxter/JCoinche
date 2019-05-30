import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import java.io.BufferedReader;

import com.esotericsoftware.minlog.Log;
import lib.Game;
import lib.Network;

import java.io.IOException;
import java.io.InputStreamReader;

public class GameClient {
    public static void main(String[] args) {
        Log.set(Log.LEVEL_NONE);
        if (args.length != 2) {
            System.out.println("Usage: java -jar ./jcoinche-client.jar hostname port");
            System.exit(84);
        }
        try {
            GameClient gameClient = new GameClient(args[0], Integer.parseInt(args[1]));
            while (true) {
                Thread.sleep(500);
            }
        } catch (Exception e) {
            System.out.println("Client was interrupted.");
        }
    }

    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    Client client;
    String name;

    public GameClient(final String host, final int port) {
        client = new Client();
        client.start();
        Network.register(client);

        client.addListener(new Listener() {
            public void connected(Connection conn) {
                Network.RegisterName playerName = new Network.RegisterName();
                playerName.name = name;
                client.sendTCP(playerName);
            }

            public void received(Connection conn, Object obj) {
                if (obj instanceof Network.ChatMessage) {
                    Network.ChatMessage chatMessage = (Network.ChatMessage) obj;
                    System.out.println(chatMessage.text);
                    return;
                }
                if (obj instanceof Network.Deck) {
                    Network.Deck currentDeck = (Network.Deck) obj;
                    prompThread prompter = new prompThread();
                    prompter.updateDeck(currentDeck);
                    prompter.start();
                    return;
                }
            }

            public void disconnected(Connection connection) {
                System.out.println("Client disconnected.");
                client.close();
                System.exit(0);
            }
        });

        try {
            while (name == null || name.length() == 0) {
                System.out.print("Choose your nickname : ");
                name = br.readLine();
                name = name.trim();
            }
            client.connect(10000, host, port);
        } catch (IOException e) {
            System.out.println("Timed out.");
        }
    }

    public void prompt (Network.Deck currentDeck){
        int i = -1;
        String choice = "";
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        Network.GameResponse selectedCard = new Network.GameResponse();
        Game trads = new Game();

        System.out.println("Your deck is : ");
        for (Game.coincheCard card : currentDeck.cards) {
            ++i;
            System.out.println(i + " : " + trads.getTradsValues().get(card.getValue()) + " " + trads.getTradsColor().get(card.getColor()));
        }
        try {
            while (choice.isEmpty()) {
                System.out.println("Choose a card : ");
                choice = br.readLine();
                choice = choice.trim();
                try {
                    selectedCard.card = currentDeck.cards.get(Integer.parseInt(choice));
                    selectedCard.cardName = trads.getTradsValues().get(selectedCard.card.getValue()) + " " + trads.getTradsColor().get(selectedCard.card.getColor());
                    break;
                } catch (Exception e) {
                    choice = "";
                    System.out.println("ERROR: This card is not in the deck.");
                }
            }
        } catch (IOException e) {
            System.out.println("Bad Input");
        }
        client.sendTCP(selectedCard);
    }

    public void finalize() {
        client.close();
        System.exit(0);
    }

    public class prompThread extends Thread {
        Network.Deck    deck = null;

        public void run(){
            prompt(deck);
        }

        public void updateDeck(Network.Deck currentDeck) {
            deck = currentDeck;
        }
    }
}
