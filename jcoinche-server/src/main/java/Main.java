import com.esotericsoftware.minlog.Log;

public class Main {
    public static void main(String[] args) {
        Log.set(Log.LEVEL_NONE);
        if (args.length != 1) {
            System.out.println("Usage: java -jar jcoinche-server.jar port");
            System.exit(84);
        }
        GameServer server = new GameServer(Integer.parseInt(args[0]));
        server.loop();
    }
}
