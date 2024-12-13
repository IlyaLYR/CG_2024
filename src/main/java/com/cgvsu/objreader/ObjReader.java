package com.cgvsu.objreader;

import com.cgvsu.math.Vector2f;
import com.cgvsu.math.Vector3f;
import com.cgvsu.model.Model;
import com.cgvsu.model.Polygon;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class ObjReader {
	private static final String OBJ_VERTEX_TOKEN = "v";
	private static final String OBJ_TEXTURE_TOKEN = "vt";
	private static final String OBJ_NORMAL_TOKEN = "vn";
	private static final String OBJ_FACE_TOKEN = "f";
	private static Model result = new Model();

	public static Model read(String fileContent) {
		result = new Model();

		int lineInd = 0;
		Scanner scanner = new Scanner(fileContent);
		while (scanner.hasNextLine()) {
			final String line = scanner.nextLine();
			ArrayList<String> wordsInLine = new ArrayList<String>(Arrays.asList(line.split("\\s+")));
			if (wordsInLine.isEmpty()) {
				continue;
			}

			final String token = wordsInLine.get(0);
			wordsInLine.remove(0);

			++lineInd;
			switch (token) {
				// Для структур типа вершин методы написаны так, чтобы ничего не знать о внешней среде.
				// Они принимают только то, что им нужно для работы, а возвращают только то, что могут создать.
				// Исключение - индекс строки. Он прокидывается, чтобы выводить сообщение об ошибке.
				// Могло быть иначе. Например, метод parseVertex мог вместо возвращения вершины принимать вектор вершин
				// модели или сам класс модели, работать с ним.
				// Но такой подход может привести к большему количеству ошибок в коде. Например, в нем что-то может
				// тайно сделаться с классом модели.
				// А еще это портит читаемость
				// И не стоит забывать про тесты. Чем проще вам задать данные для теста, проверить, что метод рабочий,
				// тем лучше.
				case OBJ_VERTEX_TOKEN -> result.vertices.add(parseVertex(wordsInLine, lineInd));
				case OBJ_TEXTURE_TOKEN -> result.textureVertices.add(parseTextureVertex(wordsInLine, lineInd));
				case OBJ_NORMAL_TOKEN -> result.normals.add(parseNormal(wordsInLine, lineInd));
				case OBJ_FACE_TOKEN -> result.polygons.add(parseFace(wordsInLine, lineInd));
				default -> {}
			}
		}
		return result;
	}

	// Всем методам кроме основного я поставил модификатор доступа protected, чтобы обращаться к ним в тестах
	protected static Vector3f parseVertex(final ArrayList<String> wordsInLineWithoutToken, int lineInd) {
		if (wordsInLineWithoutToken.size() > 3) {
			throw new ObjReaderException("Too many vertex arguments.", lineInd);
		}
		try {
			return new Vector3f(
					Float.parseFloat(wordsInLineWithoutToken.get(0)),
					Float.parseFloat(wordsInLineWithoutToken.get(1)),
					Float.parseFloat(wordsInLineWithoutToken.get(2)));

		} catch(NumberFormatException e) {
			throw new ObjReaderException("Failed to parse float value.", lineInd);

		} catch(IndexOutOfBoundsException e) {
			throw new ObjReaderException("Too few vertex arguments.", lineInd);
		}
	}

	protected static Vector2f parseTextureVertex(final ArrayList<String> wordsInLineWithoutToken, int lineInd) {
		if (wordsInLineWithoutToken.size() > 2) {
			throw new ObjReaderException("Too many texture vertex arguments.", lineInd);
		}
		try {
			return new Vector2f(
					Float.parseFloat(wordsInLineWithoutToken.get(0)),
					Float.parseFloat(wordsInLineWithoutToken.get(1)));

		} catch(NumberFormatException e) {
			throw new ObjReaderException("Failed to parse float value.", lineInd);

		} catch(IndexOutOfBoundsException e) {
			throw new ObjReaderException("Too few texture vertex arguments.", lineInd);
		}
	}


	protected static Vector3f parseNormal(final ArrayList<String> wordsInLineWithoutToken, int lineInd) {
		if (wordsInLineWithoutToken.size() > 3) {
			throw new ObjReaderException("Too many normal arguments.", lineInd);
		}
		try {
			return new Vector3f(
					Float.parseFloat(wordsInLineWithoutToken.get(0)),
					Float.parseFloat(wordsInLineWithoutToken.get(1)),
					Float.parseFloat(wordsInLineWithoutToken.get(2)));

		} catch(NumberFormatException e) {
			throw new ObjReaderException("Failed to parse float value.", lineInd);

		} catch(IndexOutOfBoundsException e) {
			throw new ObjReaderException("Too few normal arguments.", lineInd);
		}
	}

	protected static Polygon parseFace(final ArrayList<String> wordsInLineWithoutToken, int lineInd) {
		ArrayList<Integer> onePolygonVertexIndices = new ArrayList<Integer>();
		ArrayList<Integer> onePolygonTextureVertexIndices = new ArrayList<Integer>();
		ArrayList<Integer> onePolygonNormalIndices = new ArrayList<Integer>();

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

	// Обратите внимание, что для чтения полигонов я выделил еще один вспомогательный метод.
	// Это бывает очень полезно и с точки зрения структурирования алгоритма в голове, и с точки зрения тестирования.
	// В радикальных случаях не бойтесь выносить в отдельные методы и тестировать код из одной-двух строчек.
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
				default -> {
					throw new ObjReaderException("Invalid element size.", lineInd);
				}
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
		if (!onePolygonNormalIndices.isEmpty() && onePolygonNormalIndices.size() < 3) {
			return false;
		}
		return true;
	}
}
