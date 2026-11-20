public class ProgressPrinter implements ScanProgressListener {

    // \r moves cursor back so same line keeps updating
    public void onProgress(int done, int total) {
        System.out.print("\rscanned " + done + " / " + total);
    }
}
