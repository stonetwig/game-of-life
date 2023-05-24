import okhttp3.*;
import okio.ByteString;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class GameOfLifeTest {
    private final GameOfLife ctx = mock(GameOfLife.class);

    @Test
    @DisplayName("Test websocket connection")
    public void TestWebSocketConnection() {
        var webSocketCb = new WebSocketListener() {
            @Override
            public void onMessage(@NotNull WebSocket webSocket, @NotNull String text) {
                System.out.println(text);
                super.onMessage(webSocket, text);
            }

            @Override
            public void onMessage(@NotNull WebSocket webSocket, @NotNull ByteString bytes) {
                System.out.println("On message!");
                super.onMessage(webSocket, bytes);
            }

            @Override
            public void onOpen(@NotNull WebSocket webSocket, @NotNull Response response) {
                System.out.println("Opened");
                super.onOpen(webSocket, response);
            }
        };
        var okHttpClient = new OkHttpClient.Builder()
                .readTimeout(3, TimeUnit.SECONDS)
                .build();
        var websocketUrl = "ws://localhost:7070/gol";
        var websocketRequest = new Request.Builder().url(websocketUrl).build();
        var socket = okHttpClient.newWebSocket(websocketRequest, webSocketCb);
        socket.send("Test");
    }

    @Test
    @DisplayName("Test create html message from sender")
    public void TestCreateHtmlMessageFromSender() {
        var timestamp = new SimpleDateFormat("HH:mm:ss").format(new Date());
        assertEquals(GameOfLife.createHtmlMessageFromSender("Test"), "<p><strong>Test, last updated: </strong><span class=\"timestamp\">" + timestamp + "</span></p>");
    }
}
