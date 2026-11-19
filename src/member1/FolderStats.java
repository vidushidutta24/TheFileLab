public class FolderStats {
    private int fileCount = 0;
    private int folderCount = 0;
    private long totalSize = 0;
    private long totalWords = 0;
    private long totalLines = 0;

    // synchronized bcz many threads call this
    public synchronized void addFile(long size, long words, long lines) {
        fileCount++;
        totalSize += size;
        totalWords += words;
        totalLines += lines;
    }

    public void setFolderCount(int folderCount) { this.folderCount = folderCount; }
    public int getFileCount() { return fileCount; }
    public int getFolderCount() { return folderCount; }
    public long getTotalSize() { return totalSize; }
    public long getTotalWords() { return totalWords; }
    public long getTotalLines() { return totalLines; }
}
