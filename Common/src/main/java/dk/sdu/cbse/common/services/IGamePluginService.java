package dk.sdu.cbse.common.services;

import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.World;

/** Spawn entities through start, stop removes them. Makes it so we can add and remove components. */
public interface IGamePluginService {

    /** Spawn entities */
    void start(GameData gameData, World world);

    /** Tear down everything */
    void stop(GameData gameData, World world);
}
