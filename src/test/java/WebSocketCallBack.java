import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import org.jetbrains.annotations.NotNull;

public class WebSocketCallBack extends WebSocketListener {

    @Override
    public void onMessage(@NotNull WebSocket webSocket, @NotNull String text) {
        System.out.println(text);
        System.out.println("Websocket opened");
        System.out.println("Websocket opened");
        System.out.println("Websocket opened");
        System.out.println("Websocket opened");
        super.onMessage(webSocket, text);
    }

    @Override
    public void onClosed(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
        super.onClosed(webSocket, code, reason);
    }

    @Override
    public void onOpen(@NotNull WebSocket webSocket, @NotNull Response response) {
        System.out.println("Websocket opened");
        super.onOpen(webSocket, response);
    }
}
