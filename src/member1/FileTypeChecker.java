public class FileTypeChecker {

    // only these we read inside, rest just size
    private static final String[] TEXT_EXT = {"txt", "java", "csv", "md", "log", "xml", "json", "html", "css", "js", "py", "c", "cpp", "sql", "properties"};

    public static boolean isText(String ext) {
        for (int i = 0; i < TEXT_EXT.length; i++) {
            if (TEXT_EXT[i].equals(ext)) {
                return true;
            }
        }
        return false;
    }
}
