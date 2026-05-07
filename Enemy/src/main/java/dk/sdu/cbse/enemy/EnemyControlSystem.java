package dk.sdu.cbse.enemy;

import dk.sdu.cbse.common.bullet.BulletSPI;
import dk.sdu.cbse.common.data.Entity;
import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.World;
import dk.sdu.cbse.common.services.IEntityProcessingService;

import java.util.Random;
import java.util.ServiceLoader;

public class EnemyControlSystem implements IEntityProcessingService {

    private final Random rnd = new Random();
    private BulletSPI bulletSPI;

    public EnemyControlSystem() {
        for (BulletSPI spi : ServiceLoader.load(BulletSPI.class)) {
            bulletSPI = spi;
            break;
        }
    }

    @Override
    public void process(GameData gameData, World world) {
        for (Entity enemy : world.getEntities(Enemy.class)) {
            if (rnd.nextDouble() < 0.04) {
                enemy.setRotation(enemy.getRotation() + (rnd.nextDouble() * 30 - 15));
            }

            double changeX = Math.cos(Math.toRadians(enemy.getRotation()));
            double changeY = Math.sin(Math.toRadians(enemy.getRotation()));
            enemy.setX(enemy.getX() + changeX);
            enemy.setY(enemy.getY() + changeY);

            if (enemy.getX() < 0) enemy.setX(gameData.getDisplayWidth());
            if (enemy.getX() > gameData.getDisplayWidth()) enemy.setX(0);
            if (enemy.getY() < 0) enemy.setY(gameData.getDisplayHeight());
            if (enemy.getY() > gameData.getDisplayHeight()) enemy.setY(0);

            if (rnd.nextDouble() < 0.01 && bulletSPI != null) {
                world.addEntity(bulletSPI.createBullet(enemy, gameData));
            }
        }
    }
}
