public class WordCount implements Comparable<WordCount> {
    private String word;
    private int count;

    public WordCount(String word, int count) {
        this.word = word;
        this.count = count;
    }

    public String getWord() { return word; }
    public int getCount() { return count; }

    // bigger count comes first
    public int compareTo(WordCount other) {
        return Integer.compare(other.count, this.count);
    }
}
