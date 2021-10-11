package ca.pragmaticcoding.boids;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class BoidsView extends StackPane {
    private final Pane boidsPane = new Pane();

    private final BoidModel boidModel;

    public BoidsView(BoidModel model, int height, int width) {
        this.boidModel = model;
        boidsPane.setMinHeight(height + 100);
        boidsPane.setMinWidth(width + 100);
        VBox slidersBox = buildSlidersBox();
        StackPane.setAlignment(slidersBox, Pos.TOP_RIGHT);
        getChildren().addAll(boidsPane, slidersBox);
        setBackground(new Background(new BackgroundFill(Color.web("#282B34"), CornerRadii.EMPTY, Insets.EMPTY)));
    }

    public VBox buildSlidersBox() {
        VBox vBox = new VBox();
        vBox.setMaxWidth(200);
        Slider centeringFactorSlider = createDoubleSlider(boidModel.centeringFactorProperty());
        Slider minDistanceSlider = createIntegerSlider(boidModel.minDistanceProperty(), 20);
        Slider matchingFactorSlider = createDoubleSlider(boidModel.matchingFactorProperty());
        Slider speedLimitSlider = createIntegerSlider(boidModel.speedLimitProperty(), 25);
        Slider avoidFactorSlider = createDoubleSlider(boidModel.avoidFactorProperty());
        Button resetButton = new Button("Reset Settings");
        CheckBox drawTracesCheckBox = new CheckBox();
        boidModel.drawTracesProperty().bindBidirectional(drawTracesCheckBox.selectedProperty());

        resetButton.setOnAction(e -> {
            centeringFactorSlider.setValue(0.005);
            minDistanceSlider.setValue(15);
            matchingFactorSlider.setValue(0.05);
            speedLimitSlider.setValue(15);
            avoidFactorSlider.setValue(0.05);
        });

        vBox.getChildren().addAll(sliderLabel("Centering Factor"), centeringFactorSlider,
                sliderLabel("Minimum Distance"), minDistanceSlider,
                sliderLabel("Matching Factor"), matchingFactorSlider,
                sliderLabel("Speed Limit"), speedLimitSlider,
                sliderLabel("Avoid Factor"), avoidFactorSlider,
                resetButton,
                sliderLabel("Draw Traces"), drawTracesCheckBox);
        return vBox;
    }

    private Slider createIntegerSlider(IntegerProperty boundProperty, int maxValue) {
        Slider minDistanceSlider = new Slider(10, maxValue, 15);
        boundProperty.bind(Bindings.createIntegerBinding(() -> minDistanceSlider.valueProperty().intValue(), minDistanceSlider.valueProperty()));
        return minDistanceSlider;
    }

    private Slider createDoubleSlider(DoubleProperty boundProperty) {
        Slider centeringFactorSlider = new Slider(0.003, 0.007, 0.005);
        boundProperty.bind(centeringFactorSlider.valueProperty());
        return centeringFactorSlider;
    }

    private Label sliderLabel(String label) {
        Label centeringFactorLabel = new Label(label);
        centeringFactorLabel.setTextFill(Color.web("#fff"));
        return centeringFactorLabel;
    }

    public Pane getBoidsPane() {
        return boidsPane;
    }
}
