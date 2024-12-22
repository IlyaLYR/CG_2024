package com.cgvsu.render_engine;

import com.cgvsu.affinetransformation.ATransformation;
import com.cgvsu.affinetransformation.STransformation;
import com.cgvsu.math.typesVectors.Vector3C;
import com.cgvsu.model.Model;

import static com.cgvsu.affinetransformation.STransformation.rotateTwoAngles;

public class TransferManagerModel {
    private Model model;

    public TransferManagerModel(Model model) {
        this.model = model;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;

    }

    public void rotate(double x, double y, double z) {

        ATransformation.ATBuilder builder = new ATransformation.ATBuilder();
        ATransformation transformation = builder
                .rotateByX(Math.toRadians(x % 360))
                .rotateByY(Math.toRadians(y % 360))
                .rotateByZ(Math.toRadians(z % 360))
                .build();
        model = transformation.applyTransformationToModel(model);
    }

    public void translate(double x, double y, double z) {
        ATransformation.ATBuilder builder = new ATransformation.ATBuilder();
        ATransformation transformation = builder
                .translateByX(x)
                .translateByY(y)
                .translateByZ(z).build();

        model = transformation.applyTransformationToModel(model);
    }

    public void scale(double x, double y, double z) {
        ATransformation.ATBuilder builder = new ATransformation.ATBuilder();
        ATransformation transformation = builder
                .scaleByX(x)
                .scaleByY(y)
                .scaleByZ(z).build();

        model = transformation.applyTransformationToModel(model);
    }
}
