import java.util.ArrayList;

public class DuplicateGroup {
    private long size;
    private ArrayList<FileInfo> files = new ArrayList<FileInfo>();

    public DuplicateGroup(long size) {
        this.size = size;
    }

    public void add(FileInfo f) {
        files.add(f);
    }

    public ArrayList<FileInfo> getFiles() { return files; }
    public long getSize() { return size; }

    public long getWastedSpace() {
        return size * (files.size() - 1);
    }
}
