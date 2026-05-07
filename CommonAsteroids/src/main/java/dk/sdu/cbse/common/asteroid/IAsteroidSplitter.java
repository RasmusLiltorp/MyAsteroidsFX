package dk.sdu.cbse.common.asteroid;

import dk.sdu.cbse.common.data.Entity;
import dk.sdu.cbse.common.data.World;

public interface IAsteroidSplitter {

    /** Splits asteroid into smaller pieces, or removes if too small */
    void splitAsteroid(Entity asteroid, World world);
}
