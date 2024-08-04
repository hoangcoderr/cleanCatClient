package cleanCatClient.gui.hud;

public interface IRenderConfig {
    public void save(ScreenPosition pos);

    public ScreenPosition load();
}
