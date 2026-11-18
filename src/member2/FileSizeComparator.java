import java.util.Comparator;

public class FileSizeComparator implements Comparator<FileInfo> {

    // small file = "smaller", used by the priority queue
    public int compare(FileInfo a, FileInfo b) {
        return Long.compare(a.getSize(), b.getSize());
    }
}
