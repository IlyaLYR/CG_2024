
package com.cgvsu.objreader;

import com.cgvsu.math.typesVectors.Vector2C;
import com.cgvsu.math.typesVectors.Vector3C;
import com.cgvsu.model.Model;
import com.cgvsu.model.Polygon;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class ObjReader {
    private static final Logger logger = Logger.getLogger("ObjReader");

    static {
        try {
            FileHandler fh = new FileHandler("./logs.log");
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
            logger.setUseParentHandlers(false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private static final String NAME_OF_MODEL_TOKEN = "o";
    private static final String OBJ_VERTEX_TOKEN = "v";
    private static final String OBJ_TEXTURE_TOKEN = "vt";
    private static final String OBJ_NORMAL_TOKEN = "vn";
    private static final String OBJ_FACE_TOKEN = "f";
    private static final String OBJ_GRIND_NORMALS_TOKEN = "s";
    private static final String OBJ_GROUP_NAME_TOKEN = "g";
    private static Model result = new Model();

    public static Model read(String fileContent) {
        result = new Model();

        int lineInd = 0;
        Scanner scanner = new Scanner(fileContent);
        while (scanner.hasNextLine()) {
            final String line = scanner.nextLine();
            ArrayList<String> wordsInLine = new ArrayList<>(Arrays.asList(line.split("\\s+")));
            if (line.startsWith("#") || line.isEmpty()) {
                lineInd++;
                continue;
            }

            final String token = wordsInLine.get(0);
            wordsInLine.remove(0);

            ++lineInd;

            try {
                switch (token) {
                    case NAME_OF_MODEL_TOKEN -> result.setNameOfModel(parseNameOfModel(wordsInLine, lineInd));
                    case OBJ_VERTEX_TOKEN -> {
                        Vector3C vertex = parseVertex(wordsInLine, lineInd);
                        if (vertex != null) {
                            result.vertices.add(vertex);
                        }
                    }
                    case OBJ_TEXTURE_TOKEN -> {
                        Vector2C textureVertex = parseTextureVertex(wordsInLine, lineInd);
                        if (textureVertex != null) {
                            result.textureVertices.add(textureVertex);
                        }
                    }
                    case OBJ_NORMAL_TOKEN -> {
                        Vector3C normal = parseNormal(wordsInLine, lineInd);
                        if (normal != null) {
                            result.normals.add(normal);
                        }
                    }
                    case OBJ_FACE_TOKEN -> {
                        Polygon face = parseFace(wordsInLine, lineInd);
                        if (face != null) {
                            result.polygons.add(face);
                        }
                    }
                    case OBJ_GRIND_NORMALS_TOKEN, OBJ_GROUP_NAME_TOKEN -> {
                        // TODO: можно добавить обработку
                    }
                    default ->
                            logger.log(Level.WARNING, "Line {0} [Model: {1}]: The token is incorrect.", new Object[]{lineInd, getModelName()});
                }
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Line {0}: {1}", new Object[]{lineInd, e.getMessage()});
            }
        }
        return result;
    }

    protected static String parseNameOfModel(final ArrayList<String> wordsInLineWithoutToken, int lineInd) {
        if (wordsInLineWithoutToken.isEmpty()) {
            logger.log(Level.WARNING, "Line {0} [Model: {1}]: Model name is empty.", new Object[]{lineInd, getModelName()});
            return "Unknown model";
        } else if (wordsInLineWithoutToken.size() > 1) {
            logger.log(Level.WARNING, "Line {0} [Model: {1}]: Incorrect model name format.", new Object[]{lineInd, getModelName()});
            return "Unknown model";
        }
        return wordsInLineWithoutToken.get(0);
    }

    protected static Vector3C parseVertex(final ArrayList<String> wordsInLineWithoutToken, int lineInd) {
        try {
            if (wordsInLineWithoutToken.size() != 3) {
                logger.log(Level.WARNING, "Line {0} [Model: {1}]: Incorrect number of vertex arguments.", new Object[]{lineInd, getModelName()});
            }
            return new Vector3C(
                    Float.parseFloat(wordsInLineWithoutToken.get(0)),
                    Float.parseFloat(wordsInLineWithoutToken.get(1)),
                    Float.parseFloat(wordsInLineWithoutToken.get(2)));
        } catch (NumberFormatException e) {
            logger.log(Level.WARNING, "Line {0} [Model: {1}]: Failed to parse vertex coordinates.", new Object[]{lineInd, getModelName()});
        }
        return null;
    }

    protected static Vector2C parseTextureVertex(final ArrayList<String> wordsInLineWithoutToken, int lineInd) {
        try {
            if (wordsInLineWithoutToken.size() != 2) {
                logger.log(Level.WARNING, "Line {0} [Model: {1}]: Incorrect number of texture vertex arguments.", new Object[]{lineInd, getModelName()});
            }
            return new Vector2C(
                    Float.parseFloat(wordsInLineWithoutToken.get(0)),
                    Float.parseFloat(wordsInLineWithoutToken.get(1)));
        } catch (NumberFormatException e) {
            logger.log(Level.WARNING, "Line {0} [Model: {1}]: Failed to parse texture vertex coordinates.", new Object[]{lineInd, getModelName()});
        }
        return null;
    }

    protected static Vector3C parseNormal(final ArrayList<String> wordsInLineWithoutToken, int lineInd) {
        try {
            if (wordsInLineWithoutToken.size() != 3) {
                logger.log(Level.WARNING, "Line {0} [Model: {1}]: Incorrect number of normal arguments.", new Object[]{lineInd, getModelName()});
            }
            return new Vector3C(
                    Float.parseFloat(wordsInLineWithoutToken.get(0)),
                    Float.parseFloat(wordsInLineWithoutToken.get(1)),
                    Float.parseFloat(wordsInLineWithoutToken.get(2)));
        } catch (NumberFormatException e) {
            logger.log(Level.WARNING, "Line {0} [Model: {1}]: Failed to parse normal coordinates.", new Object[]{lineInd, getModelName()});
        }
        return null;
    }

    protected static Polygon parseFace(final ArrayList<String> wordsInLineWithoutToken, int lineInd) {
        ArrayList<Integer> onePolygonVertexIndices = new ArrayList<>();
        ArrayList<Integer> onePolygonTextureVertexIndices = new ArrayList<>();
        ArrayList<Integer> onePolygonNormalIndices = new ArrayList<>();

        try {
            for (String s : wordsInLineWithoutToken) {
                parseFaceWord(s, onePolygonVertexIndices, onePolygonTextureVertexIndices, onePolygonNormalIndices, lineInd);
            }

            if (equalsCorrectFaceFormat(onePolygonVertexIndices, onePolygonTextureVertexIndices, onePolygonNormalIndices)) {
                Polygon result = new Polygon();
                result.setVertexIndices(onePolygonVertexIndices);
                result.setTextureVertexIndices(onePolygonTextureVertexIndices);
                result.setNormalIndices(onePolygonNormalIndices);
                return result;
            } else {
                logger.log(Level.WARNING, "Line {0} [Model: {1}]: Incorrect face format.", new Object[]{lineInd, getModelName()});
            }
        } catch (Exception e) {
            logger.log(Level.WARNING, "Line {0} [Model: {1}]: Error parsing face data: {2}", new Object[]{lineInd, getModelName(), e.getMessage()});
        }

        return null;
    }

    protected static void parseFaceWord(
            String wordInLine,
            ArrayList<Integer> onePolygonVertexIndices,
            ArrayList<Integer> onePolygonTextureVertexIndices,
            ArrayList<Integer> onePolygonNormalIndices,
            int lineInd) {
        try {
            String[] wordIndices = wordInLine.split("/");
            switch (wordIndices.length) {
                case 1 -> {
                    if (isIndexOutOfBoundInFace(Integer.parseInt(wordIndices[0]), result.getVertices().size())) {
                        logger.log(Level.WARNING, "Line {0} [Model: {1}]: Vertex index is out of bounds.", new Object[]{lineInd, getModelName()});
                        return;
                    }
                    onePolygonVertexIndices.add(Integer.parseInt(wordIndices[0]) - 1);
                }
                case 2 -> {
                    if (isIndexOutOfBoundInFace(Integer.parseInt(wordIndices[0]), result.getVertices().size()) ||
                            isIndexOutOfBoundInFace(Integer.parseInt(wordIndices[1]), result.getTextureVertices().size())) {
                        logger.log(Level.WARNING, "Line {0} [Model: {1}]: Index is out of bounds.", new Object[]{lineInd, getModelName()});
                        return;
                    }
                    onePolygonVertexIndices.add(Integer.parseInt(wordIndices[0]) - 1);
                    onePolygonTextureVertexIndices.add(Integer.parseInt(wordIndices[1]) - 1);
                }
                case 3 -> {
                    if (isIndexOutOfBoundInFace(Integer.parseInt(wordIndices[0]), result.getVertices().size()) ||
                            isIndexOutOfBoundInFace(Integer.parseInt(wordIndices[2]), result.getNormals().size())) {
                        logger.log(Level.WARNING, "Line {0} [Model: {1}]: Index is out of bounds.", new Object[]{lineInd, getModelName()});
                        return;
                    }
                    onePolygonVertexIndices.add(Integer.parseInt(wordIndices[0]) - 1);
                    onePolygonNormalIndices.add(Integer.parseInt(wordIndices[2]) - 1);

                    if (!wordIndices[1].isEmpty() && !isIndexOutOfBoundInFace(Integer.parseInt(wordIndices[1]), result.getTextureVertices().size())) {
                        onePolygonTextureVertexIndices.add(Integer.parseInt(wordIndices[1]) - 1);
                    }
                }
                default ->
                        logger.log(Level.WARNING, "Line {0} [Model: {1}]: Invalid face element size.", new Object[]{lineInd, getModelName()});
            }
        } catch (NumberFormatException e) {
            logger.log(Level.WARNING, "Line {0} [Model: {1}]: Failed to parse face indices.", new Object[]{lineInd, getModelName()});
        }
    }

    private static boolean isIndexOutOfBoundInFace(int index, int size) {
        return index - 1 >= size;
    }

    public static boolean equalsCorrectFaceFormat(
            ArrayList<Integer> onePolygonVertexIndices,
            ArrayList<Integer> onePolygonTextureVertexIndices,
            ArrayList<Integer> onePolygonNormalIndices) {

        if (onePolygonVertexIndices.size() < 3) {
            return false;
        }

        if (!onePolygonTextureVertexIndices.isEmpty() && onePolygonTextureVertexIndices.size() < 3) {
            return false;
        }

        return onePolygonNormalIndices.isEmpty() || onePolygonNormalIndices.size() >= 3;
    }

    private static String getModelName() {
        if (result.getNameOfModel() != null) {
            return result.getNameOfModel();
        } else return "Unknown model";

    }
}

