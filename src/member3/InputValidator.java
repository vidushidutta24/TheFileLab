import java.io.File;

public class InputValidator {

    public static boolean isValidFolder(String path) {
        if (path == null || path.trim().length() == 0) {
            return false;
        }
        return new File(path.trim()).isDirectory();
    }

    // if its not a number or less than 1 we just give default
    public static int toInt(String text, int defaultValue) {
        try {
            int n = Integer.parseInt(text.trim());
            if (n < 1) {
                return defaultValue;
            }
            return n;
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }
}
