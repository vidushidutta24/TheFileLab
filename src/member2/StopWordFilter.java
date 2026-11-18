public class StopWordFilter {

    private static final String[] STOP = {"the", "and", "for", "that", "with", "this", "from", "are", "not", "you", "was", "but", "have", "has", "all", "can", "will", "his", "her"};

    public static boolean isStopWord(String w) {
        for (int i = 0; i < STOP.length; i++) {
            if (STOP[i].equals(w)) {
                return true;
            }
        }
        return false;
    }
}
