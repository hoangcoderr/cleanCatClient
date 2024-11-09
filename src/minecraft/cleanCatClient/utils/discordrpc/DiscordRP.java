package cleanCatClient.utils.discordrpc;

import cleanCatClient.Client;
import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;
import net.arikia.dev.drpc.DiscordUser;
import net.arikia.dev.drpc.callbacks.ReadyCallback;

public class DiscordRP {

    private boolean running = true;
    private long created = 0;

    public void start() {
        this.created = System.currentTimeMillis();
        DiscordEventHandlers handlers = new DiscordEventHandlers.Builder().setReadyEventHandler(new ReadyCallback() {
            @Override
            public void apply(DiscordUser discordUser) {
                System.out.println("Welcome " + discordUser.username + "#" + discordUser.discriminator + "!");
                update(Client.CLIENT_NAME, Client.CLIENT_VERSION);
            }
        }).build();

        DiscordRPC.discordInitialize("1271132858773278800", handlers, true);

        new Thread("Discord RPC Callback"){
            @Override
            public void run() {
                while (running) {
                    DiscordRPC.discordRunCallbacks();
                }
            }
        }.start();
    }

    public void shutdown() {
        running = false;
        DiscordRPC.discordShutdown();
    }

    public void update(String firstLine, String secondLine) {
        DiscordRichPresence.Builder builder = new DiscordRichPresence.Builder(secondLine);
        builder.setBigImage("large", "");
        builder.setDetails(firstLine);
        builder.setStartTimestamps(created);
        DiscordRPC.discordUpdatePresence(builder.build());
    }
}
