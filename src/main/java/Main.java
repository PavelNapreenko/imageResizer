import java.io.File;
import java.util.Arrays;

public class Main {
    private static final int newWidth = 300;
    private static final String srcFolder = "ImageResizer/images/";
    private static final String dstFolder = "ImageResizer/resizedImages/";


    public static void main(String[] args) {
        getThreads();
    }

    private static void getThreads () {

        File srcDir = new File(srcFolder);

        long start = System.currentTimeMillis();

        File[] files = srcDir.listFiles();

        if (files.length == 0) {
            System.out.println("Folder is empty.");
        } else {
            final int threadCount = Runtime.getRuntime().availableProcessors();

            int from = 0;
            for (int i =0; i < threadCount; i++) {
                final int to = (from + (int) Math.ceil((files.length - from) / (double) (threadCount - i)));
                File[] f = Arrays.copyOfRange(files, from, to);
                ImageResizer r = new ImageResizer(f, newWidth, dstFolder, start);
                new Thread(r).start();
                //System.out.println("Thread # " + i + " will use " + f.length + " files");
                from = to;
            }
        }
    }
}
