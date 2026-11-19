public class FileInfo {
    private String path;
    private String name;
    private String extension;
    private long size;
    private long words;
    private long lines;

    public FileInfo(String path, String name, String extension, long size, long words, long lines) {
        this.path = path;
        this.name = name;
        this.extension = extension;
        this.size = size;
        this.words = words;
        this.lines = lines;
    }

    public String getPath() { return path; }
    public String getName() { return name; }
    public String getExtension() { return extension; }
    public long getSize() { return size; }
    public long getWords() { return words; }
    public long getLines() { return lines; }
    public void setWords(long words) { this.words = words; }
    public void setLines(long lines) { this.lines = lines; }

    public boolean isTextFile() {
        return FileTypeChecker.isText(extension);
    }
}
