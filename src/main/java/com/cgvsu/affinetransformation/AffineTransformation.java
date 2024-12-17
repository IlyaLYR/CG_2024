//
//public class AffineTransformation {
//
//    /**
//     * Метод для масштабирования вектора(вершины)
//     *
//     * @param x       коэффициент масштабирования по оси X.
//     * @param y       коэффициент масштабирования по оси Y.
//     * @param z       коэффициент масштабирования по оси Z.
//     * @param vector3D вектор(вершины), который необходимо масштабировать.
//     * @return Новый вектор после масштабирования.
//     */
//    public static Vector3D scale(float x, float y, float z, Vector3D vector3D) {
//        Vector4D vector4D = vector3D.translationToVector4D();
//        float[][] scale = new float[][]{
//                {x, 0, 0, 0},
//                {0, y, 0, 0},
//                {0, 0, z, 0},
//                {0, 0, 0, 1}};
//        Matrix4D ScaleMatrix = new Matrix4D(scale);
//
//        return ScaleMatrix.multiplyVector(vector4D).translationToVector3D();
//    }
//    /**
//     * Метод для получения матрицы масштабирования
//     *
//     * @param x       коэффициент масштабирования по оси X.
//     * @param y       коэффициент масштабирования по оси Y.
//     * @param z       коэффициент масштабирования по оси Z.
//     * @return Новая матрица масштабирования.
//     */
//    public static Matrix4D getScaleMatrix(float x, float y, float z) {
//        float[][] scale = new float[][]{
//                {x, 0, 0, 0},
//                {0, y, 0, 0},
//                {0, 0, z, 0},
//                {0, 0, 0, 1}};
//        return new Matrix4D(scale);
//    }
//
//    /**
//     * Метод для поворота вектора(вершины)
//     *
//     * @param a        угол поворота в градусах для матрицы поворота по оси X.
//     * @param b        угол поворота в градусах для матрицы поворота по оси Y.
//     * @param c        угол поворота в градусах для матрицы поворота по оси Z.
//     * @param vector3D вектор, который необходимо повернуть.
//     * @return Новый вектор после поворота.
//     */
//    public static Vector3D rotate(int a, int b, int c, Vector3D vector3D) {
//        Vector4D vector4D = vector3D.translationToVector4D();
//        float[][] rotateX = new float[][]{
//                {1, 0, 0, 0},
//                {0, (float) Math.cos(Math.toRadians(a)), (float) Math.sin(Math.toRadians(a)), 0},
//                {0, (float) -Math.sin(Math.toRadians(a)), (float) Math.cos(Math.toRadians(a)), 0},
//                {0, 0, 0, 1}};
//        float[][] rotateY = new float[][]{
//                {(float) Math.cos(Math.toRadians(b)), 0, (float) Math.sin(Math.toRadians(b)), 0},
//                {0, 1, 0, 0},
//                {(float) -Math.sin(Math.toRadians(b)), 0, (float) Math.cos(Math.toRadians(b)), 0},
//                {0, 0, 0, 1}};
//
//        float[][] rotateZ = new float[][]{
//                {(float) Math.cos(Math.toRadians(c)), (float) Math.sin(Math.toRadians(c)), 0, 0},
//                {(float) -Math.sin(Math.toRadians(c)), (float) Math.cos(Math.toRadians(c)), 0, 0},
//                {0, 0, 1, 0},
//                {0, 0, 0, 1}};
//
//        Matrix4D RotateXMatrix = new Matrix4D(rotateX);
//        Matrix4D RotateYMatrix = new Matrix4D(rotateY);
//        Matrix4D RotateZMatrix = new Matrix4D(rotateZ);
//
//        Matrix4D RotateXYZ = RotateXMatrix.multiplyMatrix(RotateYMatrix).multiplyMatrix(RotateZMatrix);
//
//        return RotateXYZ.multiplyVector(vector4D).translationToVector3D();
//    }
//
//    /**
//     * Метод для получения матрицы поворота
//     *
//     * @param a        угол поворота в градусах для матрицы поворота по оси X.
//     * @param b        угол поворота в градусах для матрицы поворота по оси Y.
//     * @param c        угол поворота в градусах для матрицы поворота по оси Z.
//     * @return Новая матрица поворота.
//     */
//    public static Matrix4D getRotateMatrix(int a, int b, int c) {
//        float[][] rotateX = new float[][]{
//                {1, 0, 0, 0},
//                {0, (float) Math.cos(Math.toRadians(a)), (float) Math.sin(Math.toRadians(a)), 0},
//                {0, (float) -Math.sin(Math.toRadians(a)), (float) Math.cos(Math.toRadians(a)), 0},
//                {0, 0, 0, 1}};
//        float[][] rotateY = new float[][]{
//                {(float) Math.cos(Math.toRadians(b)), 0, (float) Math.sin(Math.toRadians(b)), 0},
//                {0, 1, 0, 0},
//                {(float) -Math.sin(Math.toRadians(b)), 0, (float) Math.cos(Math.toRadians(b)), 0},
//                {0, 0, 0, 1}};
//
//        float[][] rotateZ = new float[][]{
//                {(float) Math.cos(Math.toRadians(c)), (float) Math.sin(Math.toRadians(c)), 0, 0},
//                {(float) -Math.sin(Math.toRadians(c)), (float) Math.cos(Math.toRadians(c)), 0, 0},
//                {0, 0, 1, 0},
//                {0, 0, 0, 1}};
//
//        Matrix4D RotateXMatrix = new Matrix4D(rotateX);
//        Matrix4D RotateYMatrix = new Matrix4D(rotateY);
//        Matrix4D RotateZMatrix = new Matrix4D(rotateZ);
//
//        Matrix4D RotateXYZ = RotateXMatrix.multiplyMatrix(RotateYMatrix).multiplyMatrix(RotateZMatrix);
//
//        return RotateXYZ;
//    }
//
//    /**
//     * Метод для переноса вектора(вершины)
//     *
//     * @param tx       значение на которое смещается x.
//     * @param ty       значение на которое смещается y.
//     * @param tz       значение на которое смещается z.
//     * @param vector3D вектор, который необходимо сместить.
//     * @return Новый вектор после смещения.
//     */
//    public static Vector3D parallelTranslation(float tx, float ty, float tz, Vector3D vector3D) {
//        Vector4D vector4D = vector3D.translationToVector4D();
//        float[][] translation = new float[][]{
//                {1, 0, 0, tx},
//                {0, 1, 0, ty},
//                {0, 0, 1, tz},
//                {0, 0, 0, 1}};
//        Matrix4D Translation = new Matrix4D(translation);
//        return Translation.multiplyVector(vector4D).translationToVector3D();
//    }
//
//    /**
//     * Метод для получения матрицы переноса
//     *
//     * @param tx       значение на которое смещается x.
//     * @param ty       значение на которое смещается y.
//     * @param tz       значение на которое смещается z.
//     * @return Новая матрица смещения.
//     */
//    public static Matrix4D getTranslationMatrix(float tx, float ty, float tz) {
//        float[][] translation = new float[][]{
//                {1, 0, 0, tx},
//                {0, 1, 0, ty},
//                {0, 0, 1, tz},
//                {0, 0, 0, 1}};
//        return new Matrix4D(translation);
//    }
//
//}
