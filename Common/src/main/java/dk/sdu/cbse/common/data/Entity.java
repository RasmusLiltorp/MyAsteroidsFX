package dk.sdu.cbse.common.data;

import java.util.UUID;

public class Entity {

    private final String id = UUID.randomUUID().toString();
    private double[] polygonCoordinates;
    private double x;
    private double y;
    private double rotation;
    private float radius;
    private int hitPoints = 1;

    public String getID() {
        return id;
    }

    public void setPolygonCoordinates(double... coordinates) {
        this.polygonCoordinates = coordinates;
    }

    public double[] getPolygonCoordinates() {
        return polygonCoordinates;
    }

    public void setX(double x) { this.x = x; }
    public double getX() { return x; }

    public void setY(double y) { this.y = y; }
    public double getY() { return y; }

    public void setRotation(double rotation) { this.rotation = rotation; }
    public double getRotation() { return rotation; }

    public void setRadius(float radius) { this.radius = radius; }
    public float getRadius() { return radius; }

    public void setHitPoints(int hitPoints) { this.hitPoints = hitPoints; }
    public int getHitPoints() { return hitPoints; }
    public void damage(int amount) { this.hitPoints -= amount; }
}
