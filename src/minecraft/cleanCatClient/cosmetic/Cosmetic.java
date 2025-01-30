package cleanCatClient.cosmetic;

public class Cosmetic {
    private int type;
    private int id;
    private String name;
    private boolean enabled;

    public Cosmetic(int type, int id, String name, boolean enabled) {
        this.type = type;
        this.id = id;
        this.name = name;
        this.enabled = enabled;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}