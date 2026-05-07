package dk.sdu.cbse.asteroids;

import dk.sdu.cbse.common.asteroid.Asteroid;
import dk.sdu.cbse.common.asteroid.IAsteroidSplitter;
import dk.sdu.cbse.common.data.Entity;
import dk.sdu.cbse.common.data.World;

import java.util.Random;

public class AsteroidSplitter implements IAsteroidSplitter {

    @Override
    public void splitAsteroid(Entity asteroid, World world) {
        world.removeEntity(asteroid);
        float size = asteroid.getRadius() / 2;
        if (size < 8) return;

        Random rnd = new Random();
        for (int i = 0; i < 2; i++) {
            Entity child = new Asteroid();
            child.setPolygonCoordinates(size, -size, -size, -size, -size, size, size, size);
            child.setX(asteroid.getX());
            child.setY(asteroid.getY());
            child.setRotation(rnd.nextInt(360));
            child.setRadius(size);
            world.addEntity(child);
        }
    }
}
