package dk.sdu.cbse.common.bullet;

import dk.sdu.cbse.common.data.Entity;
import dk.sdu.cbse.common.data.GameData;

public interface BulletSPI {

    /** Returns bullet at the shooter, ready to add to world */
    Entity createBullet(Entity shooter, GameData gameData);
}
