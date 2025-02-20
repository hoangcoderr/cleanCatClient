package cleanCatClient.mods.manager;

public interface IConfigManager<T> {
    void loadAll();
    void saveAll();
    T getConfig(String name);
    void setConfig(String name, T config);
}