package ObjReaderTest;

import com.cgvsu.math.Vector2f;
import com.cgvsu.math.Vector3f;
import com.cgvsu.objreader.ObjReader;
import com.cgvsu.objreader.ObjReaderException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;

class ObjReaderTest extends ObjReader {
    @Test
    void read() {
        File file = new File("tests/ObjReaderTest/objectReaderTest.obj");
        Path fileName = Path.of(file.getAbsolutePath());
        String fileContent;
        try {
            fileContent = Files.readString(fileName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ObjReader.read(fileContent);
    }

    @Test
    void testParseVertex01() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.01", "1.02", "1.03"));
        Vector3f result = ObjReader.parseVertex(wordsInLineWithoutToken, 5);
        Vector3f expectedResult = new Vector3f(1.01f, 1.02f, 1.03f);
        Assertions.assertTrue(result.equals(expectedResult));
    }

    @Test
    void testParseVertex02() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.01", "1.02", "1"));
        Vector3f result = ObjReader.parseVertex(wordsInLineWithoutToken, 5);
        Vector3f expectedResult = new Vector3f(1.01f, 1.02f, 1.10f);
        Assertions.assertFalse(result.equals(expectedResult));
    }

    @Test
    void testParseVertex03() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.0", "2.0"));
        try {
            ObjReader.parseVertex(wordsInLineWithoutToken, 10);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. Too few vertex arguments.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    void testParseVertex04() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("ab", "o", "ba"));
        try {
            ObjReader.parseVertex(wordsInLineWithoutToken, 10);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. Failed to parse float value.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    void testParseVertex05() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.0", "2.0", "5.0l"));
        try {
            ObjReader.parseVertex(wordsInLineWithoutToken, 10);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. Failed to parse float value.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    void testParseVertex06() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.0", "2.0", "3.0", "4.0"));
        try {
            ObjReader.parseVertex(wordsInLineWithoutToken, 10);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. Too many vertex arguments.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    void testParseTextureVertex01() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.01", "1.02"));
        Vector2f result = ObjReader.parseTextureVertex(wordsInLineWithoutToken, 5);
        Vector2f expectedResult = new Vector2f(1.01f, 1.02f);
        Assertions.assertEquals(result, expectedResult);
    }

    @Test
    void testParseTextureVertex02() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.01", "1.02", "1.03"));
        try {
            ObjReader.parseTextureVertex(wordsInLineWithoutToken, 10);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. Too many texture vertex arguments.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    void testParseTextureVertex03() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.01", "1.02l"));
        try {
            ObjReader.parseTextureVertex(wordsInLineWithoutToken, 10);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 10. Failed to parse float value.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    void testParseNormal01() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.01", "1.02", "1.03", "1.04"));
        try {
            ObjReader.parseNormal(wordsInLineWithoutToken, 8);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 8. Too many normal arguments.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    void testParseNormal02() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.01", "1.02", "1.03"));
        Vector3f expectedResult = new Vector3f(1.01f, 1.02f, 1.03f);
        Vector3f result = ObjReader.parseNormal(wordsInLineWithoutToken, 8);
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void testParseNormal03() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.01", "1.02l"));
        try {
            ObjReader.parseNormal(wordsInLineWithoutToken, 8);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 8. Failed to parse float value.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    void testParseNormal04() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1.01", "1.02"));
        try {
            ObjReader.parseNormal(wordsInLineWithoutToken, 8);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 8. Too few normal arguments.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    void testParseFace01() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1//2", "3/2/1"));
        try {
            ObjReader.parseFace(wordsInLineWithoutToken, 8);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 8. Incorrect face format.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }
    @Test
    void testParseFace02() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1//2", "2//1"));
        try {
            ObjReader.parseFace(wordsInLineWithoutToken, 8);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 8. Incorrect face format.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    void testParseFace03() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1", "3"));
        try {
            ObjReader.parseFace(wordsInLineWithoutToken, 8);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 8. Incorrect face format.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    void testParseFace04() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1//2", "3"));
        try {
            ObjReader.parseFace(wordsInLineWithoutToken, 8);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 8. Incorrect face format.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }

    @Test
    void testParseFace05() {
        ArrayList<String> wordsInLineWithoutToken = new ArrayList<>(Arrays.asList("1//2", "3/2/1", "3/4/2"));
        try {
            ObjReader.parseFace(wordsInLineWithoutToken, 8);
        } catch (ObjReaderException exception) {
            String expectedError = "Error parsing OBJ file on line: 8. Incorrect face format.";
            Assertions.assertEquals(expectedError, exception.getMessage());
        }
    }

}