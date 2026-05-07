package dk.sdu.cbse.common.services;

import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.World;

/** Updates per-frame. One provider per component. */
public interface IEntityProcessingService {

    void process(GameData gameData, World world);
}
