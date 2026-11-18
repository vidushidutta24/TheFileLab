public class ExtensionStat implements Comparable<ExtensionStat> {
    private String ext;
    private int count = 0;
    private long totalSize = 0;

    public ExtensionStat(String ext) {
        this.ext = ext;
    }

    public void addFile(long size) {
        count++;
        totalSize += size;
    }

    public String getExt() { return ext; }
    public int getCount() { return count; }
    public long getTotalSize() { return totalSize; }

    public int compareTo(ExtensionStat other) {
        return Integer.compare(other.count, this.count);
    }
}
