package cleanCatClient.gui.hud;

public interface IRenderer extends IRenderConfig{
    int getWidth();
    int getHeight();
    void setWidth(int width);
    void setHeight(int height);
    void render(ScreenPosition pos);

    default void renderDummy(ScreenPosition pos) {
        render(pos);
    }

    default boolean isEnabled(){
        return true;
    }

}
