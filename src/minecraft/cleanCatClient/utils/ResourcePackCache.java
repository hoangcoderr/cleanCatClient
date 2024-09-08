package cleanCatClient.utils;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.resources.IResourcePack;
public class ResourcePackCache {
    private final Map<String, IResourcePack> cache = new HashMap<>();

    public void addToCache(String key, IResourcePack resourcePack) {
        cache.put(key, resourcePack);
    }

    public IResourcePack getFromCache(String key) {
        return cache.get(key);
    }

    public void clearCache() {
        cache.clear();
    }
}