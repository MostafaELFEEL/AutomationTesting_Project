package Utils.HelperPack;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.FileReader;
import java.lang.reflect.Type;
import java.util.List;

public class HelperClass {
    private static final String BASE_PATH = "src/main/java/TestingData/";

    // For single object
//    public static <T> T readObjectFromFile(String fileName, Class<T> classOfT) {
//        try (FileReader reader = new FileReader(BASE_PATH + fileName)) {
//            return new Gson().fromJson(reader, classOfT);
//        } catch (Exception e) {
//            throw new RuntimeException("Failed to load JSON object: " + e.getMessage(), e);
//        }
//    }

    // For array of objects
    public static <T> List<T> readArrayFromFile(String fileName, Class<T> classOfT) {
        try (FileReader reader = new FileReader(BASE_PATH + fileName)) {
            Type listType = TypeToken.getParameterized(List.class, classOfT).getType();
            return new Gson().fromJson(reader, listType);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load JSON array: " + e.getMessage(), e);
        }
    }
}
