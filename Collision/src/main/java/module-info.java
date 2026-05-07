import dk.sdu.cbse.common.services.IPostEntityProcessingService;

module Collision {
    requires Common;
    requires CommonBullet;
    requires CommonAsteroids;

    uses dk.sdu.cbse.common.asteroid.IAsteroidSplitter;
    uses dk.sdu.cbse.common.services.IScoreService;

    provides IPostEntityProcessingService with dk.sdu.cbse.collision.CollisionDetector;
}
