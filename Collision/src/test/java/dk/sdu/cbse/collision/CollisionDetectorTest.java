package dk.sdu.cbse.collision;

import dk.sdu.cbse.common.data.Entity;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CollisionDetectorTest {

    private final CollisionDetector detector = new CollisionDetector();

    @Test
    void overlapping_circles_collide() {
        Entity a = entity(100, 100, 10);
        Entity b = entity(105, 100, 10);
        assertTrue(detector.collides(a, b));
    }

    @Test
    void touching_circles_do_not_collide() {
        // Distance equals sum of radii: edge case treated as no collision.
        Entity a = entity(0, 0, 10);
        Entity b = entity(20, 0, 10);
        assertFalse(detector.collides(a, b));
    }

    @Test
    void distant_circles_do_not_collide() {
        Entity a = entity(0, 0, 5);
        Entity b = entity(100, 0, 5);
        assertFalse(detector.collides(a, b));
    }

    private static Entity entity(double x, double y, float radius) {
        Entity e = new Entity();
        e.setX(x);
        e.setY(y);
        e.setRadius(radius);
        return e;
    }
}
