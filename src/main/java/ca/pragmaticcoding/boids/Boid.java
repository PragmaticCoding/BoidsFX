package ca.pragmaticcoding.boids;

import javafx.geometry.Point2D;

import java.util.Arrays;

public class Boid {
    double x;
    double y;
    double dx;
    double dy;
    Point2D[] history;

    public Boid(double x, double y, double dx, double dy, Point2D[] history) {
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
        this.history = history;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getDx() {
        return dx;
    }

    public void setDx(double dx) {
        this.dx = dx;
    }

    public double getDy() {
        return dy;
    }

    public void setDy(double dy) {
        this.dy = dy;
    }

    public Point2D[] getHistory() {
        return history;
    }

    public void setHistory(Point2D[] history) {
        this.history = history;
    }

    public void keepWithinBounds(int width, int height) {
        int margin = 100;
        int turnFactor = 1;

        if (x < margin) {
            dx += turnFactor;
        }
        if (x > width - margin) {
            dx -= turnFactor;
        }
        if (y < margin) {
            dy += turnFactor;
        }
        if (y > height - margin) {
            dy -= turnFactor;
        }
    }

    public void limitSpeed(double speedLimit) {
        double speed = Math.sqrt(dx * dx + dy * dy);
        if (speed > speedLimit) {
            dx = (dx / speed) * speedLimit;
            dy = (dy / speed) * speedLimit;
        }
    }

    @Override
    public String toString() {
        return "Boid{" +
                "x=" + x +
                ", y=" + y +
                ", dx=" + dx +
                ", dy=" + dy +
                ", history=" + Arrays.toString(history) +
                '}';
    }
}
