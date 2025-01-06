package cleanCatClient.mods;

public class ModData {
    public String name;
    public boolean state;
    public int keyBind;

    public ModData(boolean state, int keyBind) {
        this.state = state;
        this.keyBind = keyBind;
    }
}

