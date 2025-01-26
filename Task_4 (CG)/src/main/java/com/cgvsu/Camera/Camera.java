package com.cgvsu.Camera;


import com.cgvsu.math.typesMatrix.Matrix4D;
import com.cgvsu.math.typesVectors.Vector3C;
import com.cgvsu.render_engine.GraphicConveyor;

public class Camera {

    private final double fov;
    private final double nearPlane;
    private final double farPlane;
    private Vector3C position;
    private Vector3C target;
    private double aspectRatio;

    public Camera(Vector3C position,
                  Vector3C target,
                  double fov,
                  double aspectRatio,
                  double nearPlane,
                  double farPlane
    ) {
        this.position = position;
        this.target = target;
        this.fov = fov;
        this.aspectRatio = aspectRatio;
        this.nearPlane = nearPlane;
        this.farPlane = farPlane;
    }

    public void setAspectRatio(final double aspectRatio) {
        this.aspectRatio = aspectRatio;
    }

    public Vector3C getPosition() {
        return position;
    }

    public void setPosition(final Vector3C position) {
        this.position = position;
    }

    public Vector3C getTarget() {
        return target;
    }

    public void setTarget(final Vector3C target) {
        this.target = target;
    }

    public void movePosition(final Vector3C translation) {
        this.position.add(translation);
    }

    public void moveTarget(final Vector3C translation) {
        this.target.add(translation);
    }

    public Matrix4D getViewMatrix() {
        return GraphicConveyor.lookAt(position, target);
    }

    public Matrix4D getProjectionMatrix() {
        return GraphicConveyor.perspective(fov, aspectRatio, nearPlane, farPlane);
    }

    public double getRadius() {
        return target.subtracted(position).getLength();
    }
}
