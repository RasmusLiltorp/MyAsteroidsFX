package dk.sdu.cbse.player;

import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.GameKeys;
import dk.sdu.cbse.common.data.World;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class PlayerControlSystemTest {

    private PlayerControlSystem system;
    private GameData gameData;
    private World world;
    private Player player;

    @BeforeEach
    void setUp() {
        system = new PlayerControlSystem();
        gameData = new GameData();
        world = new World();
        player = new Player();
        player.setX(400);
        player.setY(400);
        world.addEntity(player);
    }

    @Test
    void left_key_rotates_player() {
        gameData.getKeys().setKey(GameKeys.LEFT, true);
        system.process(gameData, world);
        assertEquals(-4, player.getRotation());
    }

    @Test
    void right_key_rotates_player() {
        gameData.getKeys().setKey(GameKeys.RIGHT, true);
        system.process(gameData, world);
        assertEquals(4, player.getRotation());
    }

    @Test
    void up_key_moves_player() {
        gameData.getKeys().setKey(GameKeys.UP, true);
        system.process(gameData, world);
        assertEquals(401.5, player.getX(), 0.01);
    }

    @Test
    void no_input_no_movement() {
        system.process(gameData, world);
        assertEquals(400, player.getX());
        assertEquals(400, player.getY());
    }

    @Test
    void player_wraps_at_screen_edge() {
        player.setX(-1);
        system.process(gameData, world);
        assertNotEquals(-1, player.getX());
        assertEquals(gameData.getDisplayWidth(), player.getX());
    }
}
