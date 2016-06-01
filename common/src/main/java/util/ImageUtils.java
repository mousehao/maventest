package util;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;

/**
 * 图片处理工具类.
 */
public class ImageUtils {
    private static Logger logger = LoggerFactory.getLogger(ImageUtils.class);

    /**
     * 图片缩略图裁剪
     * @param src 原始图片地址
     * @param dest 缩略图保存路径
     * @param w 缩略图宽度
     * @param h 缩略图高度
     */
    public static void centerThumbImage(String src, String dest, int w, int h) {
        try {
            BufferedImage image = ImageIO.read(new File(src));

            int imageWidth = image.getWidth();
            int imageHeight = image.getHeight();

            float wScale = (float) w / imageWidth;
            float hScale = (float) h / imageHeight;

            Thumbnails.Builder<BufferedImage> builder = null;
            if (new BigDecimal(wScale).compareTo(new BigDecimal(hScale)) == 0) {
                float scale = Math.max(wScale, hScale);
                image = Thumbnails.of(src).scale(scale).asBufferedImage();
                builder = Thumbnails.of(image).sourceRegion(Positions.CENTER, w, h).size(w, h);
            } else {
                builder = Thumbnails.of(image).size(w, h);
            }
            builder.toFile(dest);
        } catch (IOException e) {
            logger.error("CenterThumbImage Error:", e);
        }
    }

    /**
     * 图片缩略图生成(非裁剪)
     * @param src 原始图片地址
     * @param dest 缩略图保存路径
     * @param maxWidth 缩略图最大宽度
     * @param maxHeight 缩略图最大高度
     */
    public static void thumbImage(String src, String dest, int maxWidth, int maxHeight) {
        try {
            Thumbnails.of(src).size(maxWidth, maxHeight).toFile(dest);
        } catch (IOException e) {
            logger.error("ThumbImageError:", e);
        }
    }
}
