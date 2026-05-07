package dk.sdu.cbse.common.services;

import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.World;

/**
 * <p>Runs after every {@code IEntityProcessingService} each frame. For collisions and other
 * cross-entity stuff.</p>
 */
public interface IPostEntityProcessingService {

    /**
     * <p>Cross-entity logic, after all entities are updated.</p>
     *
     * <p>Pre-conditions:</p>
     * <ul>
     *     <li>{@code gameData} not null</li>
     *     <li>{@code world} not null</li>
     *     <li>every entity processor has run this frame</li>
     * </ul>
     *
     * <p>Post-conditions:</p>
     * <ul>
     *     <li>collisions applied</li>
     *     <li>dead entities removed</li>
     * </ul>
     *
     * @param gameData game state
     * @param world entity store
     */
    void process(GameData gameData, World world);
}
