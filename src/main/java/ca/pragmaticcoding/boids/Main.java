package ca.pragmaticcoding.boids;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;


public class Main extends Application {

    private int width = 1000;
    private int height = 550;

    private final int numBoids = 100;
    private final int visualRange = 80;

    private Boid[] boids = new Boid[numBoids];
    private Pane boidPane = new Pane();
    private BoidModel boidModel;

    @Override
    public void start(Stage primaryStage) {
        boidModel = new BoidModel();
        initBoids();
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                animationLoop(l);
            }
        };
        timer.start();
        BoidsView boidsView = new BoidsView(boidModel, height, width);
        primaryStage.setTitle("Boids");
        primaryStage.setScene(new Scene(boidsView));
        primaryStage.show();
    }


    public void initBoids() {
        for (int i = 0; i < numBoids; i += 1) {
            boids[i] = new Boid(
                    Math.random() * width,
                    Math.random() * height,
                    Math.random() * 10 - 5,
                    Math.random() * 10 - 5,
                    new Point2D[0]
            );
        }
    }

    public double distance(Boid boid1, Boid boid2) {
        return Math.sqrt((boid1.x - boid2.x) * (boid1.x - boid2.x) + (boid1.y - boid2.y) * (boid1.y - boid2.y));
    }

    public void flyTowardsCenter(Boid boid) {
        long numNeighbors = streamNeighbors(boid).count();
        double centerX = streamNeighbors(boid).map(Boid::getX).reduce(0.0, Double::sum);
        double centerY = streamNeighbors(boid).map(Boid::getY).reduce(0.0, Double::sum);
        boid.dx += ((centerX / numNeighbors) - boid.x) * boidModel.getCenteringFactor();
        boid.dy += ((centerY / numNeighbors) - boid.y) * boidModel.getCenteringFactor();
    }

    public void avoidOthers(Boid boid) {
        double moveX = getAvoidanceAmount(boid, Boid::getX);
        double moveY = getAvoidanceAmount(boid, Boid::getY);
        boid.dx += moveX * boidModel.getAvoidFactor();
        boid.dy += moveY * boidModel.getAvoidFactor();
    }

    private double getAvoidanceAmount(Boid boid, Function<Boid, Double> getPos) {
        return Arrays.stream(boids)
                .filter(otherBoid -> otherBoid != boid)
                .filter(otherBoid -> distance(boid, otherBoid) < boidModel.getMinDistance())
                .map(otherBoid -> getPos.apply(boid) - getPos.apply(otherBoid)).reduce(0.0, Double::sum);
    }

    public void mathVelocity(Boid boid) {
        Predicate<Boid> filter = otherBoid -> distance(boid, otherBoid) < visualRange;
        long numNeighbors = streamNeighbors(boid).count();
        double avgDX = streamNeighbors(boid).map(Boid::getDx).reduce(0.0, Double::sum) / numNeighbors;
        double avgDY = streamNeighbors(boid).map(Boid::getDy).reduce(0.0, Double::sum) / numNeighbors;
        boid.dx += (avgDX - boid.dx) * boidModel.getMatchingFactor();
        boid.dy += (avgDY - boid.dy) * boidModel.getMatchingFactor();
    }

    private Stream<Boid> streamNeighbors(Boid boid) {
        Predicate<Boid> filter = otherBoid -> distance(boid, otherBoid) < visualRange;
        return Arrays.stream(boids).filter(filter);
    }

    public void drawGraphics(Boid boid) {
        double angle = Math.atan2(boid.dy, boid.dx);
        Color fillColor = Color.web("#558cf4");
        Color strokeColour = Color.web("#558cf466");
        Polygon triangle = new Polygon(boid.x, boid.y, boid.x - 15, boid.y + 5, boid.x - 15, boid.y - 15);
        triangle.setStroke(strokeColour);
        triangle.setFill(fillColor);
        boidPane.getChildren().add(triangle);
    }


    public void animationLoop(long l) {
        System.out.println(l + " -> " + boidModel.getSpeedLimit());
        for (Boid boid : boids) {
            flyTowardsCenter(boid);
            avoidOthers(boid);
            mathVelocity(boid);
            boid.limitSpeed(boidModel.getSpeedLimit());
            boid.keepWithinBounds(width, height);

            boid.x += boid.dx;
            boid.y += boid.dy;

            boid.history = push(boid.history, boid.x, boid.y);
            boid.history = slice(boid.history, boid.history.length - 50, 0);
        }

        boidPane.getChildren().clear();
        for (Boid boid : boids) {
            drawGraphics(boid);
        }

    }

    public Point2D[] push(Point2D[] arr, double x, double y) {
        Point2D[] longer = new Point2D[arr.length + 1];
        System.arraycopy(arr, 0, longer, 0, arr.length);
        longer[arr.length] = new Point2D(x, y);
        return longer;
    }

    public Point2D[] slice(Point2D[] arr, int start, int end) {
        if (arr.length > 50) {
            Point2D[] slice = new Point2D[start - end];
            System.arraycopy(arr, start, slice, 0, slice.length);
            return slice;
        }
        return arr;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
