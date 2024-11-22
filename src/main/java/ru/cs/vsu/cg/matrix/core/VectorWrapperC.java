package ru.cs.vsu.cg.matrix.core;

import ru.cs.vsu.cg.matrix.types.VectorC;

/**
 * Класс-обертка для вектора-столбца заданного размера
 *
 * @param <T> класс вектор-столбец
 */
public abstract class VectorWrapperC<T extends VectorWrapperC<T>> {
    /**
     * Вектор-столбец
     */
    VectorC vector;

    /**
     * Конструктор нулевого вектора-столбца
     *
     * @param n количество элементов
     */
    public VectorWrapperC(int n) {
        vector = new VectorC(n);
    }

    /**
     * Конструктор вектор-столбца
     *
     * @param n    количество элементов
     * @param base элементы вектора
     */
    public VectorWrapperC(int n, double[] base) {
        vector = new VectorC(n, base);
    }


    //Геттеры сеттеры

    /**
     * Получить значения вектора
     *
     * @return массив
     */
    public double[] getBase() {
        return vector.getBase();
    }

    /**
     * Получить количество строк
     *
     * @return значение
     */
    public int getRows() {
        return vector.getRows();
    }

    /**
     * Получить количество столбцов
     *
     * @return значение
     */
    public int getCols() {
        return vector.getCols();
    }

    /**
     * Получит тело матрицы
     *
     * @return матрица - базовый объект
     */
    protected VectorC getVector() {
        return vector;
    }

    /**
     * Изменить тело матрицы
     */
    protected void setVector(VectorC vector) {
        this.vector = vector;
    }

    /**
     * Получить элементы по индексу
     *
     * @param row строка
     * @param col столбец
     * @return значение
     */
    public double get(int row, int col) {
        return vector.get(row, col);
    }


    /**
     * Изменить значение по индексу
     *
     * @param row   строка
     * @param col   столбец
     * @param value значение
     */
    public void set(int row, int col, double value) {
        vector.set(row, col, value);
    }

    /**
     * Вывод матрицы
     */
    public void print() {
        vector.print();
    }


    /**
     * Вспомогательный метод
     *
     * @param vector вектор-столбец
     * @return конкретный тип вектора
     */
    public abstract T newMatrix(VectorC vector);

    /**
     * Сложение матриц
     *
     * @param other слагаемое
     */
    public void add(T other) {
        setVector(vector.added(other.getVector()));
    }

    /**
     * Сложение матриц
     *
     * @param other слагаемое
     * @return новая матрица - результат
     */
    public T added(T other) {
        return newMatrix(vector.added(other.getVector()));
    }

    /**
     * Вычитание матриц
     *
     * @param other вычитаемое
     */
    public void subtract(T other) {
        setVector(vector.subtracted(other.getVector()));
    }

    /**
     * Вычитание матриц
     *
     * @param other вычитаемое
     * @return новая матрица - результат
     */
    public T subtracted(T other) {
        return newMatrix(vector.subtracted(other.getVector()));
    }

    /**
     * Умножение матрицы на число
     *
     * @param number множитель
     */
    public void multiply(double number) {
        setVector(vector.multiplied(number));
    }

    /**
     * Умножение матрицы на число
     *
     * @param number множитель
     * @return новая матрица - результат
     */
    public T multiplied(double number) {
        return newMatrix(vector.multiplied(number));
    }

    /**
     * Деление матрицы на число
     *
     * @param number делитель
     */
    public void divide(double number) {
        setVector(vector.divided(number));
    }

    /**
     * Деление матрицы на число
     *
     * @param number делитель
     * @return новая матрица - результат
     */
    public T divided(double number) {
        return newMatrix(vector.divided(number));
    }

    /**
     * Вывод в консоль объекта
     *
     * @return текст
     */
    @Override
    public String toString() {
        return vector.toString();
    }

    /**
     * Длинна вектора
     *
     * @return длинна вектора
     */
    public double getLength() {
        return getVector().getLength();
    }


    /**
     * Нормализация вектора
     *
     * @return вектор-столбец
     */
    public T normalize() {
        return newMatrix(vector.normalize());
    }

    public T crossProduct(T other) {
        return newMatrix(vector.crossProduct(other.getVector()));
    }
}
