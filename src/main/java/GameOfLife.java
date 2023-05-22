import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import io.javalin.websocket.WsContext;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static j2html.TagCreator.*;

public class GameOfLife {

    private static final Map<WsContext, String> users = new ConcurrentHashMap<>();
    private static int nextUserNumber = 1; // Assign to username for next connecting user

    public static void main(String[] args) {
        Javalin app = Javalin.create(config -> {
            config.staticFiles.add("/public", Location.CLASSPATH);
        }).start(7070);

        app.ws("/gol", ws -> {
            ws.onConnect(ctx -> {
                String username = "User" + nextUserNumber++;
                users.put(ctx, username);
                broadcastMessage("Current watchers " + users.size());
            });
            ws.onClose(ctx -> {
                String username = users.get(ctx);
                users.remove(ctx);
                broadcastMessage("Current watchers " + users.size());
            });
        });

        GameServer gameServer = new GameServer();
        gameServer.start();
    }

    // Broadcasts the number of watchers
    private static void broadcastMessage(String message) {
        users.keySet().stream().filter(ctx -> ctx.session.isOpen()).forEach(session -> {
            session.send(Map.of(
                "watcherMessage", createHtmlMessageFromSender(message)
            ));
        });
    }

    public static void updateBoard(int[][] generation, boolean restart) {
        System.out.println("Updating board...");
        users.keySet().stream().filter(ctx -> ctx.session.isOpen()).forEach(session -> {
            session.send(Map.of(
                "board", generation,
                "restart", restart
            ));
        });
    }

    public static void restartGameMessage() {
        users.keySet().stream().filter(ctx -> ctx.session.isOpen()).forEach(session -> {
            session.send(Map.of(
                    "restart", true
            ));
        });
    }

    // Builds a HTML element with the number of watchers
    private static String createHtmlMessageFromSender(String message) {
        return article(
                b(message + ", last updated: "),
                span(
                    attrs(".timestamp"), new SimpleDateFormat("HH:mm:ss").format(new Date())
                )
        ).render();
    }

}