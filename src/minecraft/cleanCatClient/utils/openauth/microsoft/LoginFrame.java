package cleanCatClient.utils.openauth.microsoft;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebView;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.CompletableFuture;

public class LoginFrame extends JFrame {
    private CompletableFuture<String> future;

    public LoginFrame() {
        this.setTitle("Sign in to Minecraft");
        this.setSize(750, 750);
        this.setLocationRelativeTo(null);

        this.setContentPane(new JFXPanel());
    }

    public CompletableFuture<String> start(String url) {
        if (this.future != null) {
            return this.future;
        }

        this.future = new CompletableFuture<>();
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                future.completeExceptionally(new MicrosoftAuthenticationException("User closed the authentication window"));
            }
        });

        Platform.runLater(() -> this.init(url));
        return this.future;
    }

    protected void init(String url) {
        WebView webView = new WebView();
        JFXPanel content = (JFXPanel) this.getContentPane();

        content.setScene(new Scene(webView, this.getWidth(), this.getHeight()));

        webView.getEngine().locationProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.contains("access_token")) {
                this.setVisible(false);
                this.future.complete(newValue);
            }
        });
        webView.getEngine().load(url);

        this.setVisible(true);
    }
}