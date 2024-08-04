package cleanCatClient.event;
import cleanCatClient.event.Event;
import cleanCatClient.event.EventData;
import cleanCatClient.event.EventTarget;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class EventManager {
    private static final Map<Class<? extends Event>, ArrayList<EventData>> REGISTRY_MAP = new HashMap<Class<? extends Event>, ArrayList<EventData>>();

    private static void sortListValue(Class<? extends Event> clazz) {
        final ArrayList<EventData> flexableArray = new ArrayList<EventData>();
        for (byte b = Byte.MIN_VALUE; b < Byte.MAX_VALUE; b++) {
            for (EventData data : REGISTRY_MAP.get(clazz)) {
                if (data.priority == b) {
                    flexableArray.add(data);
                }
            }
        }
        REGISTRY_MAP.put(clazz, flexableArray);
    }

    private static boolean isMethodBad(final Method method) {
        return method.getParameterTypes().length != 1 || !method.isAnnotationPresent(EventTarget.class);
    }

    private static boolean isMethodBad(final Method method, final Class<? extends Event> clazz) {
        return isMethodBad(method) || method.getParameterTypes()[0].equals(clazz);
    }

    public static ArrayList<EventData> get(final Class<? extends Event> clazz) {
        return REGISTRY_MAP.get(clazz);
    }

    public static void cleanMap(boolean removeOnlyEmptyValues) {
        final Iterator<Map.Entry<Class<? extends Event>, ArrayList<EventData>>> iterator = REGISTRY_MAP.entrySet().iterator();

        while (iterator.hasNext()) {
            if (!removeOnlyEmptyValues || iterator.next().getValue().isEmpty()) {
                iterator.remove();
            }
        }
    }

    public static void unregister(final Object o, final Class<? extends Event> clazz) {
        if (REGISTRY_MAP.containsKey(clazz)) {
            Iterator<EventData> iterator = REGISTRY_MAP.get(clazz).iterator();
            while (iterator.hasNext()) {
                EventData methodData = iterator.next();
                if (methodData.source.equals(o)) {
                    iterator.remove();
                }
            }
        }
        cleanMap(true);
    }

    public static void unregister(final Object o) {
        for (ArrayList<EventData> flexableArray : REGISTRY_MAP.values()) {
            Iterator<EventData> iterator = flexableArray.iterator();
            while (iterator.hasNext()) {
                EventData methodData = iterator.next();
                if (methodData.source.equals(o)) {
                    iterator.remove();
                }
            }
        }
        cleanMap(true);
    }

    public static void register(final Method method, final Object o){
        final Class<?> clazz = method.getParameterTypes()[0];
        final EventData methodData = new EventData(o, method, method.getAnnotation(EventTarget.class).value());
        if (!methodData.target.isAccessible()) {
            methodData.target.setAccessible(true);
        }
        if (REGISTRY_MAP.containsKey(clazz)) {
            if (!REGISTRY_MAP.get(clazz).contains(methodData)) {
                REGISTRY_MAP.get(clazz).add(methodData);
                sortListValue((Class<? extends Event>) clazz);
            }
        } else {
            REGISTRY_MAP.put((Class<? extends Event>) clazz, new ArrayList<EventData>() {
                {
                    add(methodData);
                }
            });
        }
    }

    public static void register(final Object o, final Class<? extends Event> clazz) {
        for (final Method method : o.getClass().getMethods()) {
            if (!isMethodBad(method, clazz)) {
                register(method, o);
            }
        }
    }

    public static void register(final Object o) {
        for (final Method method : o.getClass().getMethods()) {
            if (!isMethodBad(method)) {
                register(method, o);
            }
        }
    }
}