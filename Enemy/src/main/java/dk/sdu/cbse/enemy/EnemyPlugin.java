package dk.sdu.cbse.enemy;

import dk.sdu.cbse.common.data.Entity;
import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.World;
import dk.sdu.cbse.common.services.IGamePluginService;

import java.util.concurrent.ThreadLocalRandom;

public class EnemyPlugin implements IGamePluginService {

    private Entity enemy;

    @Override
    public void start(GameData gameData, World world) {
        enemy = new Enemy();
        enemy.setPolygonCoordinates(-7, -7, 9, 0, -7, 7, -3, 0);
        enemy.setX(ThreadLocalRandom.current().nextInt(gameData.getDisplayWidth()));
        enemy.setY(ThreadLocalRandom.current().nextInt(gameData.getDisplayHeight()));
        enemy.setRotation(ThreadLocalRandom.current().nextInt(360));
        enemy.setRadius(8);
        enemy.setHitPoints(2);
        world.addEntity(enemy);
    }

    @Override
    public void stop(GameData gameData, World world) {
        world.removeEntity(enemy);
    }
}
