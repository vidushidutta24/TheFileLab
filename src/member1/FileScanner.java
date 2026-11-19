import java.io.File;
import java.util.ArrayList;

public class FileScanner {
    private int folderCount = 0;

    public ArrayList<File> findFiles(String path) throws InvalidFolderException {
        File root = new File(path);
        if (!root.exists() || !root.isDirectory()) {
            throw new InvalidFolderException("not a valid folder: " + path);
        }
        ArrayList<File> list = new ArrayList<File>();
        folderCount = 0;
        walk(root, list);
        return list;
    }

    // goes inside every folder, recursion yaaay
    private void walk(File folder, ArrayList<File> list) {
        File[] items = folder.listFiles();
        if (items == null) {
            return; // no permission prob
        }
        for (File f : items) {
            if (f.isDirectory()) {
                folderCount++;
                walk(f, list);
            } else {
                list.add(f);
            }
        }
    }

    public int getFolderCount() {
        return folderCount;
    }
}
