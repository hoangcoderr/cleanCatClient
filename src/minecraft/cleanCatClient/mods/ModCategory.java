package cleanCatClient.mods;

public enum ModCategory {
    PLAYER(0),
    WORLD(1),
    RENDER(2),
    UTIL(3),
    SETTINGS(4);

    private final int id;

    ModCategory(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}