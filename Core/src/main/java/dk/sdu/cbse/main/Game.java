package dk.sdu.cbse.main;

import dk.sdu.cbse.common.data.Entity;
import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.GameKeys;
import dk.sdu.cbse.common.data.World;
import dk.sdu.cbse.common.services.IEntityProcessingService;
import dk.sdu.cbse.common.services.IGamePluginService;
import dk.sdu.cbse.common.services.IPostEntityProcessingService;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Game {

    private final GameData gameData = new GameData();
    private final World world = new World();
    private final Pane gameWindow = new Pane();
    private final Map<Entity, Polygon> polygons = new ConcurrentHashMap<>();
    private final Text scoreText = new Text(10, 20, "Score: 0");

    private final List<IGamePluginService> gamePlugins;
    private final List<IEntityProcessingService> entityProcessors;
    private final List<IPostEntityProcessingService> postProcessors;

    public Game(List<IGamePluginService> gamePlugins,
                List<IEntityProcessingService> entityProcessors,
                List<IPostEntityProcessingService> postProcessors) {
        this.gamePlugins = gamePlugins;
        this.entityProcessors = entityProcessors;
        this.postProcessors = postProcessors;
    }

    public void start(Stage window) {
        gameWindow.setPrefSize(gameData.getDisplayWidth(), gameData.getDisplayHeight());
        gameWindow.getChildren().add(scoreText);
        Scene scene = new Scene(gameWindow);
        scene.setOnKeyPressed(e -> setKey(e.getCode(), true));
        scene.setOnKeyReleased(e -> setKey(e.getCode(), false));

        for (IGamePluginService plugin : gamePlugins) {
            plugin.start(gameData, world);
        }

        window.setScene(scene);
        window.setTitle("Asteroids");
        window.show();
    }

    public void render() {
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
                draw();
                gameData.getKeys().update();
            }
        }.start();
    }

    private void update() {
        for (IEntityProcessingService s : entityProcessors) s.process(gameData, world);
        // Post-processors run after entity processors so collision sees the new positions
        for (IPostEntityProcessingService s : postProcessors) s.process(gameData, world);
    }

    private void draw() {
        scoreText.setText("Score: " + gameData.getScore());

        for (Entity polygonEntity : polygons.keySet()) {
            if (!world.getEntities().contains(polygonEntity)) {
                Polygon removed = polygons.get(polygonEntity);
                polygons.remove(polygonEntity);
                gameWindow.getChildren().remove(removed);
            }
        }

        for (Entity entity : world.getEntities()) {
            Polygon polygon = polygons.get(entity);
            if (polygon == null) {
                polygon = new Polygon(entity.getPolygonCoordinates());
                polygons.put(entity, polygon);
                gameWindow.getChildren().add(polygon);
            }
            polygon.setTranslateX(entity.getX());
            polygon.setTranslateY(entity.getY());
            polygon.setRotate(entity.getRotation());
        }
    }

    private void setKey(KeyCode code, boolean pressed) {
        if (code == KeyCode.UP)    gameData.getKeys().setKey(GameKeys.UP, pressed);
        if (code == KeyCode.LEFT)  gameData.getKeys().setKey(GameKeys.LEFT, pressed);
        if (code == KeyCode.RIGHT) gameData.getKeys().setKey(GameKeys.RIGHT, pressed);
        if (code == KeyCode.SPACE) gameData.getKeys().setKey(GameKeys.SPACE, pressed);
    }
}
