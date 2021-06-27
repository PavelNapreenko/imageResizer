import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Method;
import org.imgscalr.Scalr.Mode;

public class ImageResizer implements Runnable {

  private final File[] files;
  private final int newWidth;
  private final String dstFolder;
  private final long start;

  public ImageResizer(File[] files, int newWidth, String dstFolder, long start) {
    this.files = files;
    this.newWidth = newWidth;
    this.dstFolder = dstFolder;
    this.start = start;
  }

  @Override
  public void run() {
    try {
      for (File file : files) {
        BufferedImage image = ImageIO.read(file);
        if (image == null) {
          continue;
        }

        int newHeight = (int) Math.round(
            image.getHeight() / (image.getWidth() / (double) newWidth)
        );

        BufferedImage newImage = Scalr.resize(
            image,
            Method.ULTRA_QUALITY,
            Mode.AUTOMATIC,
            newWidth,newHeight);

        File newFile = new File(dstFolder + "/" + file.getName());
        ImageIO.write(newImage, "jpg", newFile);
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    System.out.println("Duration: " + (System.currentTimeMillis() - start));
  }
}
