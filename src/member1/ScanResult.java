import java.util.ArrayList;

public class ScanResult {
    private String folder;
    private ArrayList<FileInfo> files = new ArrayList<FileInfo>();
    private FolderStats stats = new FolderStats();
    private long timeMs = 0;
    private String scanDate = "";

    public ScanResult(String folder) {
        this.folder = folder;
    }

    public synchronized void addFile(FileInfo f) {
        files.add(f);
        stats.addFile(f.getSize(), f.getWords(), f.getLines());
    }

    public synchronized int getDoneCount() {
        return files.size();
    }

    public String getFolder() { return folder; }
    public ArrayList<FileInfo> getFiles() { return files; }
    public FolderStats getStats() { return stats; }
    public long getTimeMs() { return timeMs; }
    public String getScanDate() { return scanDate; }
    public void setTimeMs(long timeMs) { this.timeMs = timeMs; }
    public void setScanDate(String scanDate) { this.scanDate = scanDate; }

    public String getSummary() {
        String s = "";
        s += "folder: " + folder + "\n";
        s += "files: " + stats.getFileCount() + "\n";
        s += "folders: " + stats.getFolderCount() + "\n";
        s += "total size: " + SizeFormatter.format(stats.getTotalSize()) + "\n";
        s += "total words: " + stats.getTotalWords() + "\n";
        s += "total lines: " + stats.getTotalLines() + "\n";
        s += "scan time: " + timeMs + " ms\n";
        return s;
    }
}
