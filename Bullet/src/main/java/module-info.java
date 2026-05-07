import dk.sdu.cbse.common.bullet.BulletSPI;
import dk.sdu.cbse.common.services.IEntityProcessingService;

module Bullet {
    requires Common;
    requires CommonBullet;

    provides BulletSPI with dk.sdu.cbse.bullet.BulletControlSystem;
    provides IEntityProcessingService with dk.sdu.cbse.bullet.BulletControlSystem;
}
