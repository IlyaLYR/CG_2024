package com.cgvsu.affinetransformation;

import com.cgvsu.math.typesMatrix.Matrix4D;
import com.cgvsu.math.typesVectors.Vector3C;
import com.cgvsu.math.typesVectors.Vector4C;
import com.cgvsu.model.Model;

import java.util.ArrayList;

public class ATransformation {

    private Matrix4D transformationMatrix;

    private ATransformation(Matrix4D transformationMatrix) {
        this.transformationMatrix = new Matrix4D(transformationMatrix.getBase());
    }

    public Vector3C applyTransformationToVector(Vector3C vector) {
        Vector4C fourVector = new Vector4C(new double[]{vector.getX(), vector.getY(), vector.getZ(), 1});
        Vector4C transformedVector = transformationMatrix.multiplied(fourVector);
        if (transformedVector.getW() == 0) {
            return new Vector3C(new double[]{transformedVector.getX(), transformedVector.getY(), transformedVector.getZ()});
        } else {
            return new Vector3C(new double[]{transformedVector.getX() / transformedVector.getW(), transformedVector.getY() / transformedVector.getW(), transformedVector.getZ() / transformedVector.getW()});
        }
    }

    public ArrayList<Vector3C> applyTransformationToVectorList(ArrayList<Vector3C> vectorList) {
        ArrayList<Vector3C> transformedVectorList = new ArrayList<>();
        for (Vector3C vector : vectorList) {
            transformedVectorList.add(applyTransformationToVector(vector));
        }
        return transformedVectorList;
    }

    public Model applyTransformationToModel(Model originalModel) {
        Model transformedModel = new Model();
        for (Vector3C vertex : originalModel.vertices) {
            transformedModel.vertices.add(applyTransformationToVector(vertex));
        }

        transformedModel.nameOfModel = originalModel.nameOfModel;
        transformedModel.textureVertices.addAll(originalModel.textureVertices);
        transformedModel.normals.addAll(originalModel.normals);
        transformedModel.polygons.addAll(originalModel.polygons);
        transformedModel.setActivePolyGrid(originalModel.isActivePolyGrid());
        transformedModel.setActiveLighting(originalModel.isActiveLighting());
        transformedModel.setActiveTexture(originalModel.isActiveTexture());

        return transformedModel;
    }

    public Matrix4D getTransformationMatrix() {
        return transformationMatrix;
    }

    @Override
    public String toString() {
        return transformationMatrix.toString();
    }

    public void clean() {
        this.transformationMatrix = new Matrix4D(true);
    }

    public static class ATBuilder {
        private Matrix4D currentMatrix;

        public ATBuilder() {
            this.currentMatrix = new Matrix4D(true);
        }

        private ATBuilder scale(double sX, double sY, double sZ) {
            Matrix4D scaleMatrix = new Matrix4D(new double[][]{
                    {sX, 0, 0, 0},
                    {0, sY, 0, 0},
                    {0, 0, sZ, 0},
                    {0, 0, 0, 1}
            });
            this.currentMatrix = this.currentMatrix.multiplied(scaleMatrix);
            return this;
        }

        private ATBuilder rotateX(double rX) {
            double cosX = Math.cos(rX);
            double sinX = Math.sin(rX);

            Matrix4D rotationMatrix = new Matrix4D(new double[][]{
                    {1, 0, 0, 0},
                    {0, cosX, sinX, 0},
                    {0, -sinX, cosX, 0},
                    {0, 0, 0, 1}
            });
            this.currentMatrix = this.currentMatrix.multiplied(rotationMatrix);
            return this;
        }

        private ATBuilder rotateY(double rY) {
            double cosY = Math.cos(rY);
            double sinY = Math.sin(rY);

            Matrix4D rotationMatrix = new Matrix4D(new double[][]{
                    {cosY, 0, sinY, 0},
                    {0, 1, 0, 0},
                    {-sinY, 0, cosY, 0},
                    {0, 0, 0, 1}
            });
            this.currentMatrix = this.currentMatrix.multiplied(rotationMatrix);
            return this;
        }

        private ATBuilder rotateZ(double rZ) {
            double cosZ = Math.cos(rZ);
            double sinZ = Math.sin(rZ);

            Matrix4D rotationMatrix = new Matrix4D(new double[][]{
                    {cosZ, sinZ, 0, 0},
                    {-sinZ, cosZ, 0, 0},
                    {0, 0, 1, 0},
                    {0, 0, 0, 1}
            });
            this.currentMatrix = this.currentMatrix.multiplied(rotationMatrix);
            return this;
        }

        private ATBuilder translate(double tX, double tY, double tZ) {
            Matrix4D translationMatrix = new Matrix4D(new double[][]{
                    {1, 0, 0, tX},
                    {0, 1, 0, tY},
                    {0, 0, 1, tZ},
                    {0, 0, 0, 1}
            });

            this.currentMatrix = this.currentMatrix.multiplied(translationMatrix);
            return this;
        }

        public ATBuilder scaleByX(double sX) {
            return scale(sX, 1, 1);
        }

        public ATBuilder scaleByY(double sY) {
            return scale(1, sY, 1);
        }

        public ATBuilder scaleByZ(double sZ) {
            return scale(1, 1, sZ);
        }

        public ATBuilder scaleByVector(Vector3C vector) {
            return scale(vector.getX(), vector.getY(), vector.getZ());
        }

        public ATBuilder scaleByCoordinates(double sX, double sY, double sZ) {
            return scale(sX, sY, sZ);
        }

        public ATBuilder translateByX(double tX) {
            return translate(tX, 0, 0);
        }

        public ATBuilder translateByY(double tY) {
            return translate(0, tY, 0);
        }

        public ATBuilder translateByZ(double tZ) {
            return translate(0, 0, tZ);
        }

        public ATBuilder translateByVector(Vector3C vector) {
            return translate(vector.getX(), vector.getY(), vector.getZ());
        }

        public ATBuilder translateByCoordinates(double tX, double tY, double tZ) {
            return translate(tX, tY, tZ);
        }

        public ATBuilder rotateByX(double rX) {
            return rotateX(rX);
        }

        public ATBuilder rotateByY(double rY) {
            return rotateY(rY);
        }

        public ATBuilder rotateByZ(double rZ) {
            return rotateZ(rZ);
        }

        public ATransformation build() {
            return new ATransformation(currentMatrix);
        }
    }
}