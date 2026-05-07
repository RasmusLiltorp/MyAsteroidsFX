package dk.sdu.cbse.common.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class World {

    private final Map<String, Entity> entities = new ConcurrentHashMap<>();

    public String addEntity(Entity entity) {
        entities.put(entity.getID(), entity);
        return entity.getID();
    }

    public void removeEntity(Entity entity) {
        entities.remove(entity.getID());
    }

    public Collection<Entity> getEntities() {
        return entities.values();
    }

    @SafeVarargs
    public final <E extends Entity> List<Entity> getEntities(Class<E>... types) {
        List<Entity> result = new ArrayList<>();
        for (Entity e : getEntities()) {
            for (Class<E> t : types) {
                if (t.isInstance(e)) result.add(e);
            }
        }
        return result;
    }
}
