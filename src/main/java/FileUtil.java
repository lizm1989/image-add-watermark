

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lizhiming  2017/8/22 13:37
 */
public class FileUtil {

    public static List<File> fileNameList(String fileDirectory) {
        List<File> filelist = new ArrayList<File>();
        File dir = new File(fileDirectory);
        File[] files = dir.listFiles(); // 该文件目录下文件全部放入数组
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) { // 判断是文件还是文件夹
                    fileNameList(files[i].getAbsolutePath()); // 获取文件绝对路径
                } else {
                    filelist.add(files[i]);
                }
            }
        }
        return filelist;
    }

    public static void main(String[] args) throws IOException {
        String fileDirectory = "D:\\照片\\打印的图片";

        List<File> filelist = fileNameList(fileDirectory);

        for (File file : filelist) {
            System.out.println(file.getName());
        }


        String qiniuFile = "http://image.xxx.cn/IMG_2667.JPG?watermark/2/text/MjAxNTowNDowNiAxMToxNDoyMQ==/font/5b6u6L2v6ZuF6buR/fontsize/1200";
        String filePath = "D:\\照片\\加水印图片";
        downFile(qiniuFile, "IMG_2667.JPG", filePath);

    }

    public static void downFile(String qiniuFileUrl, String fileName, String filePath) throws IOException {
        URL url = new URL(qiniuFileUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        //设置超时间为3秒
        conn.setConnectTimeout(5 * 1000);
        //防止屏蔽程序抓取而返回403错误
        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

        //得到输入流
        InputStream inputStream = conn.getInputStream();
        //获取自己数组
        byte[] getData = readInputStream(inputStream);

        //文件保存位置
        File saveDir = new File(filePath);
        if (!saveDir.exists()) {
            saveDir.mkdir();
        }
        File file = new File(saveDir + File.separator + fileName);
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(getData);
        if (fos != null) {
            fos.close();
        }
        if (inputStream != null) {
            inputStream.close();
        }
    }

    /**
     * 从输入流中获取字节数组
     *
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }
}
