package com.cgvsu.objreader;
import com.cgvsu.math.typesVectors.Vector2C;
import com.cgvsu.math.typesVectors.Vector3C;
import com.cgvsu.model.Model;
import com.cgvsu.model.Polygon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class ObjReader {
    private static final String NAME_OF_MODEL_TOKEN = "o";
    private static final String OBJ_VERTEX_TOKEN = "v";
    private static final String OBJ_TEXTURE_TOKEN = "vt";
    private static final String OBJ_NORMAL_TOKEN = "vn";
    private static final String OBJ_FACE_TOKEN = "f";
    private static Model result = new Model();

    public static Model read(String fileContent) throws ObjReaderException {
        result = new Model();

        int lineInd = 0;
        Scanner scanner = new Scanner(fileContent);
        while (scanner.hasNextLine()) {
            final String line = scanner.nextLine();
            ArrayList<String> wordsInLine = new ArrayList<>(Arrays.asList(line.split("\\s+")));
            if (line.isEmpty() || line.startsWith("#")) {
                lineInd++;
                continue;
            }


            final String token = wordsInLine.get(0);
            wordsInLine.remove(0);

            ++lineInd;
            switch (token) {
                case NAME_OF_MODEL_TOKEN -> result.setNameOfModel(parseNameOfModel(wordsInLine, lineInd));
                case OBJ_VERTEX_TOKEN -> result.vertices.add(parseVertex(wordsInLine, lineInd));
                case OBJ_TEXTURE_TOKEN -> result.textureVertices.add(parseTextureVertex(wordsInLine, lineInd));
                case OBJ_NORMAL_TOKEN -> result.normals.add(parseNormal(wordsInLine, lineInd));
                case OBJ_FACE_TOKEN -> result.polygons.add(parseFace(wordsInLine, lineInd));
                default -> {  }
            }
        }

        return result;
    }

    // throw new ObjReaderException("The token is incorrect: " + token, lineInd);
    protected static String parseNameOfModel(final ArrayList<String> wordsInLineWithoutToken, int lineInd) throws ObjReaderException {
        if (wordsInLineWithoutToken.isEmpty()) {
            return "";
        } else if (wordsInLineWithoutToken.size() > 1) {
            throw new ObjReaderException("Incorrect name of model", lineInd);
        }
        return wordsInLineWithoutToken.get(0);
    }

    // Всем методам кроме основного я поставил модификатор доступа protected, чтобы обращаться к ним в тестах
    protected static Vector3C parseVertex(final ArrayList<String> wordsInLineWithoutToken, int lineInd) throws ObjReaderException {
        if (wordsInLineWithoutToken.size() > 3) {
            throw new ObjReaderException("Too many vertex arguments.", lineInd);
        }
        try {
            return new Vector3C(
                    Float.parseFloat(wordsInLineWithoutToken.get(0)),
                    Float.parseFloat(wordsInLineWithoutToken.get(1)),
                    Float.parseFloat(wordsInLineWithoutToken.get(2)));

        } catch(NumberFormatException e) {
            throw new ObjReaderException("Failed to parse float value.", lineInd);

        } catch(IndexOutOfBoundsException e) {
            throw new ObjReaderException("Too few vertex arguments.", lineInd);
        }
    }

    protected static Vector2C parseTextureVertex(final ArrayList<String> wordsInLineWithoutToken, int lineInd) throws ObjReaderException {
        if (wordsInLineWithoutToken.size() > 3) {
            throw new ObjReaderException("Too many texture vertex arguments.", lineInd);
        }
        try {
            return new Vector2C(
                    Float.parseFloat(wordsInLineWithoutToken.get(0)),
                    Float.parseFloat(wordsInLineWithoutToken.get(1)));

        } catch (NumberFormatException e) {
            throw new ObjReaderException("Failed to parse float value.", lineInd);

        } catch (IndexOutOfBoundsException e) {
            try {
                return new Vector2C(
                        Float.parseFloat(wordsInLineWithoutToken.get(0)),
                        Float.parseFloat(wordsInLineWithoutToken.get(1)));

            } catch (IndexOutOfBoundsException e1) {
                throw new ObjReaderException("Too few texture vertex arguments.", lineInd);
            }
        }
    }


    protected static Vector3C parseNormal(final ArrayList<String> wordsInLineWithoutToken, int lineInd) throws ObjReaderException {
        if (wordsInLineWithoutToken.size() > 3) {
            throw new ObjReaderException("Too many normal arguments.", lineInd);
        }
        try {
            return new Vector3C(
                    Float.parseFloat(wordsInLineWithoutToken.get(0)),
                    Float.parseFloat(wordsInLineWithoutToken.get(1)),
                    Float.parseFloat(wordsInLineWithoutToken.get(2)));

        } catch(NumberFormatException e) {
            throw new ObjReaderException("Failed to parse float value.", lineInd);

        } catch(IndexOutOfBoundsException e) {
            throw new ObjReaderException("Too few normal arguments.", lineInd);
        }
    }

    protected static Polygon parseFace(final ArrayList<String> wordsInLineWithoutToken, int lineInd) throws ObjReaderException {
        ArrayList<Integer> onePolygonVertexIndices = new ArrayList<>();
        ArrayList<Integer> onePolygonTextureVertexIndices = new ArrayList<>();
        ArrayList<Integer> onePolygonNormalIndices = new ArrayList<>();

        for (String s : wordsInLineWithoutToken) {
            parseFaceWord(s, onePolygonVertexIndices, onePolygonTextureVertexIndices, onePolygonNormalIndices, lineInd);
        }

        Polygon result = new Polygon();

        if (equalsCorrectFaceFormat(onePolygonVertexIndices, onePolygonTextureVertexIndices, onePolygonNormalIndices)) {
            result.setVertexIndices(onePolygonVertexIndices);
            result.setTextureVertexIndices(onePolygonTextureVertexIndices);
            result.setNormalIndices(onePolygonNormalIndices);
        } else throw new ObjReaderException("Incorrect face format.", lineInd);

        return result;
    }

    protected static void parseFaceWord(
            String wordInLine,
            ArrayList<Integer> onePolygonVertexIndices,
            ArrayList<Integer> onePolygonTextureVertexIndices,
            ArrayList<Integer> onePolygonNormalIndices,
            int lineInd) throws ObjReaderException {
        try {
            String[] wordIndices = wordInLine.split("/");
            switch (wordIndices.length) {
                case 1 -> {

                    if (Integer.parseInt(wordIndices[0]) - 1 > result.getVertices().size()) {
                        throw new ObjReaderException("Vertex index is too much", lineInd);
                    }

                    onePolygonVertexIndices.add(Integer.parseInt(wordIndices[0]) - 1);
                }
                case 2 -> {

                    if (Integer.parseInt(wordIndices[0]) - 1 > result.getVertices().size()
                            || Integer.parseInt(wordIndices[1]) - 1 > result.getTextureVertices().size()) {
                        throw new ObjReaderException("Index is too much.", lineInd);

                    } else if (onePolygonVertexIndices.size() != onePolygonTextureVertexIndices.size()) {
                        throw new ObjReaderException("Incorrect face format.", lineInd);
                    }

                    onePolygonVertexIndices.add(Integer.parseInt(wordIndices[0]) - 1);
                    onePolygonTextureVertexIndices.add(Integer.parseInt(wordIndices[1]) - 1);
                }
                case 3 -> {

                    if (isIndexOutOfBoundInFace(Integer.parseInt(wordIndices[0]), result.getVertices().size())
                            || isIndexOutOfBoundInFace(Integer.parseInt(wordIndices[2]), result.getNormals().size())) {
                        throw new ObjReaderException("Index is too much.", lineInd);

                    } else if (onePolygonVertexIndices.size() != onePolygonNormalIndices.size()) {
                        throw new ObjReaderException("Incorrect face format.", lineInd);
                    }

                    onePolygonVertexIndices.add(Integer.parseInt(wordIndices[0]) - 1);
                    onePolygonNormalIndices.add(Integer.parseInt(wordIndices[2]) - 1);

                    if (!wordIndices[1].isEmpty() && !isIndexOutOfBoundInFace(Integer.parseInt(wordIndices[1]), result.getTextureVertices().size())) {
                        onePolygonTextureVertexIndices.add(Integer.parseInt(wordIndices[1]) - 1);

                        if (onePolygonVertexIndices.size() != onePolygonTextureVertexIndices.size()) {
                            throw new ObjReaderException("Incorrect face format.", lineInd);
                        }

                    } else if (!wordIndices[1].isEmpty() && isIndexOutOfBoundInFace(Integer.parseInt(wordIndices[1]), result.getTextureVertices().size()))
                        throw new ObjReaderException("Index of normal is too much.", lineInd);
                }

                default -> throw new ObjReaderException("Invalid element size.", lineInd);
            }

        } catch(NumberFormatException e) {
            throw new ObjReaderException("Failed to parse int value.", lineInd);

        } catch(IndexOutOfBoundsException e) {
            throw new ObjReaderException("Too few arguments.", lineInd);
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
}
