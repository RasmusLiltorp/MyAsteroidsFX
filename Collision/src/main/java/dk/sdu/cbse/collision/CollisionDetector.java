package dk.sdu.cbse.collision;

import dk.sdu.cbse.common.asteroid.Asteroid;
import dk.sdu.cbse.common.asteroid.IAsteroidSplitter;
import dk.sdu.cbse.common.bullet.Bullet;
import dk.sdu.cbse.common.data.Entity;
import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.World;
import dk.sdu.cbse.common.services.IPostEntityProcessingService;
import dk.sdu.cbse.common.services.IScoreService;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

public class CollisionDetector implements IPostEntityProcessingService {

    private IAsteroidSplitter splitter;
    private IScoreService scoreService;

    public CollisionDetector() {
        // Look up once instead of every frame
        for (IAsteroidSplitter s : ServiceLoader.load(IAsteroidSplitter.class)) {
            splitter = s;
            break;
        }
        for (IScoreService s : ServiceLoader.load(IScoreService.class)) {
            scoreService = s;
            break;
        }
    }

    @Override
    public void process(GameData gameData, World world) {
        List<Entity> entities = new ArrayList<>(world.getEntities());

        // Two for loops for all entities in the world.
        for (int i = 0; i < entities.size(); i++) {
            Entity entity1 = entities.get(i);
            for (int j = i + 1; j < entities.size(); j++) {
                Entity entity2 = entities.get(j);
                if (collides(entity1, entity2)) {
                    handle(entity1, entity2, world, gameData);
                }
            }
        }

        for (Entity e : entities) {
            if (e.getHitPoints() <= 0) world.removeEntity(e);
        }
    }

    public boolean collides(Entity entity1, Entity entity2) {
        double dx = entity1.getX() - entity2.getX();
        double dy = entity1.getY() - entity2.getY();
        double distance = Math.sqrt(dx * dx + dy * dy);
        return distance < entity1.getRadius() + entity2.getRadius();
    }

    private void handle(Entity entity1, Entity entity2, World world, GameData gameData) {
        if (entity1 instanceof Bullet && entity2 instanceof Asteroid) { split(entity2, world); world.removeEntity(entity1); incrementScore(gameData); return; }
        if (entity2 instanceof Bullet && entity1 instanceof Asteroid) { split(entity1, world); world.removeEntity(entity2); incrementScore(gameData); return; }
        if (entity1 instanceof Bullet) { entity2.damage(1); world.removeEntity(entity1); return; }
        if (entity2 instanceof Bullet) { entity1.damage(1); world.removeEntity(entity2); return; }
        if (entity1 instanceof Asteroid) entity2.setHitPoints(0);
        if (entity2 instanceof Asteroid) entity1.setHitPoints(0);
    }

    private void split(Entity asteroid, World world) {
        if (splitter != null) splitter.splitAsteroid(asteroid, world);
    }

    private void incrementScore(GameData gameData) {
        Integer total = scoreService != null ? scoreService.addPoint() : null;
        if (total != null) {
            gameData.setScore(total);
        } else {
            // Microservice down, fall back to local count
            gameData.addScore(1);
        }
    }
}
