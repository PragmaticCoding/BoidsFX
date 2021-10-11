package ca.pragmaticcoding.boids;

import javafx.beans.property.*;

public class BoidModel {
    private final DoubleProperty centeringFactor = new SimpleDoubleProperty(0.005);
    private final DoubleProperty avoidFactor = new SimpleDoubleProperty(0.05);
    private final DoubleProperty matchingFactor = new SimpleDoubleProperty(0.05);
    private final IntegerProperty speedLimit = new SimpleIntegerProperty(15);
    private final IntegerProperty minDistance = new SimpleIntegerProperty(15);
    private final BooleanProperty drawTraces = new SimpleBooleanProperty(false);

    public double getCenteringFactor() {
        return centeringFactor.get();
    }

    public DoubleProperty centeringFactorProperty() {
        return centeringFactor;
    }

    public void setCenteringFactor(double centeringFactor) {
        this.centeringFactor.set(centeringFactor);
    }

    public double getAvoidFactor() {
        return avoidFactor.get();
    }

    public DoubleProperty avoidFactorProperty() {
        return avoidFactor;
    }

    public void setAvoidFactor(double avoidFactor) {
        this.avoidFactor.set(avoidFactor);
    }

    public double getMatchingFactor() {
        return matchingFactor.get();
    }

    public DoubleProperty matchingFactorProperty() {
        return matchingFactor;
    }

    public void setMatchingFactor(double matchingFactor) {
        this.matchingFactor.set(matchingFactor);
    }

    public int getSpeedLimit() {
        return speedLimit.get();
    }

    public IntegerProperty speedLimitProperty() {
        return speedLimit;
    }

    public void setSpeedLimit(int speedLimit) {
        this.speedLimit.set(speedLimit);
    }

    public int getMinDistance() {
        return minDistance.get();
    }

    public IntegerProperty minDistanceProperty() {
        return minDistance;
    }

    public void setMinDistance(int minDistance) {
        this.minDistance.set(minDistance);
    }

    public boolean isDrawTraces() {
        return drawTraces.get();
    }

    public BooleanProperty drawTracesProperty() {
        return drawTraces;
    }

    public void setDrawTraces(boolean drawTraces) {
        this.drawTraces.set(drawTraces);
    }
}
