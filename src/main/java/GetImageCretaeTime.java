import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

import java.io.File;
import java.io.IOException;

/**
 * @author lizhiming  2017/8/22 11:52
 * 获取图片的基本信息
 * 如创建时间
 */
public class GetImageCretaeTime {
    public static void main(String[] args) throws ImageProcessingException, IOException {
        String imagePath = "D:\\workspace\\private\\image-add-watermark\\src\\main\\resources\\QQ截图20170822115332.png";

        Metadata metadata = ImageMetadataReader.readMetadata(new File(imagePath));

        //打印图片各种属性
        print(metadata);
    }


    private static void print(Metadata metadata) {
        for (Directory directory : metadata.getDirectories()) {
            for (Tag tag : directory.getTags()) {
                System.out.println(tag);
            }
        }

    }
}
