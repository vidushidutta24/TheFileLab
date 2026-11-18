public class SearchHit {
    private String path;
    private int lineNo;
    private String line;

    public SearchHit(String path, int lineNo, String line) {
        this.path = path;
        this.lineNo = lineNo;
        this.line = line;
    }

    public String getPath() { return path; }
    public int getLineNo() { return lineNo; }
    public String getLine() { return line; }
}
