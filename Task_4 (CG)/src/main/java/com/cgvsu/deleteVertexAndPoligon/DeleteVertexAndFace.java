package com.cgvsu.deleteVertexAndPoligon;

import com.cgvsu.math.typesVectors.Vector3C;
import com.cgvsu.model.Model;
import com.cgvsu.model.Polygon;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class DeleteVertexAndFace {
    public static Model changeModel(Model model, List<Integer> vertexIndices) {
        // Список вершин на удаление отсортированный по возрастанию
        List<Integer> vertexIndicesToDelete = new ArrayList<>(vertexIndices).stream().sorted(Comparator.reverseOrder()).toList();

        // Удаление вершин
        deleteVertices(model.vertices, vertexIndicesToDelete);

        // Удаление полигонов, часть вершин которых исчезла
        deleteDanglingPolygons(model.polygons, vertexIndices);

        // Смещение вершинных индексов внутри полигона
        shiftIndicesInPolygons(model.polygons, vertexIndices);


        return model;
    }

    private static void deleteVertices(List<Vector3C> modelVertices, List<Integer> vertexIndicesToDelete) {
        for (Integer i : vertexIndicesToDelete) {
            modelVertices.remove(i.intValue());
        }
    }

    private static void deleteDanglingPolygons(List<Polygon> modelPolygons, List<Integer> vertexIndicesToDelete) {
        for (int i = modelPolygons.size() - 1; i >= 0; i--) {
            Polygon polygon = modelPolygons.get(i);
            boolean areVertexIndicesToDeletePresentInPolygon = polygon.getVertexIndices().stream()
                    .anyMatch(vertexIndicesToDelete::contains);
            if (areVertexIndicesToDeletePresentInPolygon) {
                modelPolygons.remove(i);
            }
        }
    }


    private static void shiftIndicesInPolygons(List<Polygon> modelPolygons, List<Integer> vertexIndicesToDelete) {
        List<Integer> sortedVertexIndicesToDelete = new ArrayList<>(vertexIndicesToDelete);
        sortedVertexIndicesToDelete.sort(Comparator.reverseOrder());
        for (Polygon polygon : modelPolygons) {
            ArrayList<Integer> newVertexIndices = new ArrayList<>();
            for (int polygonVertexIndex : polygon.getVertexIndices()) {
                // Смещение в отрицательную сторону = число вершинных индексов на удаление, больше которых вершинный индекс полигона.
                int offset = countLessThan(polygonVertexIndex, sortedVertexIndicesToDelete);
                // Добавляем вершину с отрицательным смещением
                newVertexIndices.add(polygonVertexIndex - offset);
            }
            // Устанавливаем у модели список новых вершинных индексов
            polygon.setVertexIndices(newVertexIndices);
        }
    }

    private static int countLessThan(int polygonVertexIndex, List<Integer> sortedVertexIndicesToDelete) {
        int result = 0;
        while (result < sortedVertexIndicesToDelete.size()) {
            if (polygonVertexIndex >= sortedVertexIndicesToDelete.get(result)) {
                result++;
            } else {
                break;
            }
        }
        return result;
    }

    public static List<Integer> parseVerticesInput(String input) {
        List<Integer> indices = new ArrayList<>();
        try {
            String[] parts = input.split(",");
            for (String part : parts) {
                indices.add(Integer.parseInt(part.trim()));
            }
        } catch (NumberFormatException ignored) {
        }
        return indices;
    }
}



