package cleanCatClient;

import cleanCatClient.event.EventTarget;
import cleanCatClient.event.impl.ClientTickEvent;

public class Test {
    @EventTarget
    public void onTick(ClientTickEvent event) {
        System.out.println("Tick");
    }


}
