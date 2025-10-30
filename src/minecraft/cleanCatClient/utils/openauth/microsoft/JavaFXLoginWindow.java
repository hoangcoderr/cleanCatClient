package cleanCatClient.utils.openauth.microsoft;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import javax.swing.*;
import java.util.concurrent.CompletableFuture;

public class JavaFXLoginWindow {
    public CompletableFuture<String> start(String url) {
        CompletableFuture<String> future = new CompletableFuture<>();

        // Đảm bảo JavaFX Platform đã khởi động (bắt buộc nếu app gốc KO phải JavaFX)
        new JFXPanel(); // Force init JavaFX

        Platform.runLater(() -> {
            Stage stage = new Stage();
            stage.setTitle("Microsoft Login");

            WebView webView = new WebView();
            WebEngine engine = webView.getEngine();
            engine.load(url);

            // Lắng nghe thay đổi URL (chuyển hướng, token, v.v)
            engine.locationProperty().addListener((obs, oldLoc, newLoc) -> {
                if (newLoc != null && newLoc.startsWith(MicrosoftAuthenticator.MICROSOFT_REDIRECTION_ENDPOINT)) {
                    // Hoàn thành khi redirect, trả full URL (có hash access_token)
                    if (!future.isDone()) future.complete(newLoc);
                    stage.close();
                }
            });

            // Đóng cửa sổ: cancel future nếu chưa xong
            stage.setOnCloseRequest((WindowEvent e) -> {
                if (!future.isDone()) future.completeExceptionally(new RuntimeException("Login window closed"));
            });

            Scene scene = new Scene(webView, 520, 700);
            stage.setScene(scene);
            stage.show();
            // auto-focus cửa sổ
            stage.requestFocus();
        });
        return future;
    }
}
