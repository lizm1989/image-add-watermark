import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.imaging.jpeg.JpegProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;

import java.io.IOException;
import java.io.InputStream;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lizhiming  2017/8/22 11:56
 * 处理七牛图片
 */
public class QiniuImageProccess {


    private static List<String> fileNameList = new ArrayList<String>();

    static {
        fileNameList.add("1");
    }

    public static void main(String[] args) throws JpegProcessingException, IOException, ImageProcessingException, URISyntaxException {
        String str = "http://image.xxx.cn/IMG_2667.JPG";
        Metadata metadata = ImageMetadataReader.readMetadata(getInputStream(str));

        String date = print(metadata);
        System.out.println(date);

        String urlStr = str + "?watermark/2/text/" + encode(date.getBytes()) + "/font/" + encode("微软雅黑".getBytes()) + "/fontsize/1200";

        System.out.println(urlStr);

        String urlStr2 = str + "?watermark/2/text/" + encode(date.getBytes()) + "/fontsize/1200";
        System.out.println(urlStr2);

    }


    private static InputStream getInputStream(String urlStr) {
        InputStream inStream = null;
        URL url = null;
        try {
            url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5 * 1000);
            inStream = conn.getInputStream();//通过输入流获取图片数据
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
//            try {
//                inStream.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }


        return inStream;
    }


    private static String print(Metadata metadata) {
        String date = "";
        for (Directory directory : metadata.getDirectories()) {
            for (Tag tag : directory.getTags()) {
                if (tag.getTagName().equals("Date/Time")) {
                    date = tag.getDescription();
                }
            }
        }
        return date;
    }

    private static String encode(byte[] bstr) {
        return new sun.misc.BASE64Encoder().encode(bstr);
    }
}
