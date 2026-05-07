package dk.sdu.cbse.player;

import dk.sdu.cbse.common.bullet.BulletSPI;
import dk.sdu.cbse.common.data.Entity;
import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.GameKeys;
import dk.sdu.cbse.common.data.World;
import dk.sdu.cbse.common.services.IEntityProcessingService;

import java.util.ServiceLoader;

public class PlayerControlSystem implements IEntityProcessingService {

    private BulletSPI bulletSPI;

    public PlayerControlSystem() {
        for (BulletSPI spi : ServiceLoader.load(BulletSPI.class)) {
            bulletSPI = spi;
            break;
        }
    }

    @Override
    public void process(GameData gameData, World world) {
        for (Entity player : world.getEntities(Player.class)) {
            if (gameData.getKeys().isDown(GameKeys.LEFT))  player.setRotation(player.getRotation() - 4);
            if (gameData.getKeys().isDown(GameKeys.RIGHT)) player.setRotation(player.getRotation() + 4);
            if (gameData.getKeys().isDown(GameKeys.UP)) {
                double changeX = Math.cos(Math.toRadians(player.getRotation()));
                double changeY = Math.sin(Math.toRadians(player.getRotation()));
                player.setX(player.getX() + changeX * 1.5);
                player.setY(player.getY() + changeY * 1.5);
            }
            if (gameData.getKeys().isPressed(GameKeys.SPACE) && bulletSPI != null) {
                world.addEntity(bulletSPI.createBullet(player, gameData));
            }

            if (player.getX() < 0) player.setX(gameData.getDisplayWidth());
            if (player.getX() > gameData.getDisplayWidth()) player.setX(0);
            if (player.getY() < 0) player.setY(gameData.getDisplayHeight());
            if (player.getY() > gameData.getDisplayHeight()) player.setY(0);
        }
    }
}
