package com.cgvsu.render_engine;

import com.cgvsu.affinetransformation.ATransformation;
import com.cgvsu.model.Model;

public abstract class TransferManagerModel {

    public static Model rotate(Model model, double x, double y, double z) {
        ATransformation.ATBuilder builder = new ATransformation.ATBuilder();
        ATransformation transformation = builder
                .rotateByX(Math.toRadians(x % 360))
                .rotateByY(Math.toRadians(y % 360))
                .rotateByZ(Math.toRadians(z % 360))
                .build();

        // Применяем трансформацию к текущей модели
        return transformation.applyTransformationToModel(model);
    }

    public static Model translate(Model model, double x, double y, double z) {
        ATransformation.ATBuilder builder = new ATransformation.ATBuilder();
        ATransformation transformation = builder
                .translateByX(x)
                .translateByY(y)
                .translateByZ(z)
                .build();

        // Применяем трансформацию к текущей модели
        return transformation.applyTransformationToModel(model);
    }

    public static Model scale(Model model, double x, double y, double z) {
        ATransformation.ATBuilder builder = new ATransformation.ATBuilder();
        ATransformation transformation = builder
                .scaleByX(x)
                .scaleByY(y)
                .scaleByZ(z)
                .build();

        // Применяем трансформацию к текущей модели
        return transformation.applyTransformationToModel(model);
    }
}
