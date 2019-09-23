package com.example.demo.util;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class FileUtil {

    /**
     * 按行读取文件
     */
    public static void ReadFileByLine(String filename) {
        File file = new File(filename);
        InputStream is = null;
        Reader reader = null;
        BufferedReader bufferedReader = null;
        try {
            is = new FileInputStream(file);
            reader = new InputStreamReader(is);
            bufferedReader = new BufferedReader(reader);
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != bufferedReader)
                    bufferedReader.close();
                if (null != reader)
                    reader.close();
                if (null != is)
                    is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * filename = "D:/abc.txt";
     * 追加写入文件
     */
    public static void Write2FileByOutputStream(String filename, String content) {
        File file = new File(filename);
        FileOutputStream fos = null;
        // BufferedOutputStream bos = null;
        OutputStreamWriter osw = null;
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            fos = new FileOutputStream(file, true);
            osw = new OutputStreamWriter(fos);
            osw.write(content);
            osw.write("\r\n");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != osw) {
                try {
                    osw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != fos) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 下载图片
     *
     * @param path    保存路径
     * @param destUrl 目标url
     */
    public static String downloadImg(String path, String destUrl) {
        FileOutputStream fos = null;
        BufferedInputStream bis = null;
        HttpURLConnection httpUrl = null;
        String fileEnd = destUrl.substring(destUrl.lastIndexOf(".") , destUrl.length());
        if (!fileEnd.contains(".") || fileEnd.length() > 6){
            return "下载失败:"+destUrl;
        }
        String fileName = System.currentTimeMillis() + fileEnd;

        if (!new File(path).exists()) {
            new File(path).mkdirs();
        }
        byte[] buf = new byte[1024];
        int size = 0;
        try {
            URL url = new URL(destUrl);

            httpUrl = (HttpURLConnection) url.openConnection();
            httpUrl.setRequestProperty("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
            httpUrl.setRequestProperty("accept-encoding", "gzip, deflate, br");
            httpUrl.setRequestProperty("accept-language", "zh-CN,zh;q=0.9");
            httpUrl.setRequestProperty("cache-control", "no-cache");
            httpUrl.setRequestProperty("cookie", "__cfduid=dc92bdf829fceb7b1f8838da2d5e20e3a1566386715");
            httpUrl.setRequestProperty("pragma", "no-cache");
            httpUrl.setRequestProperty("upgrade-insecure-requests", "1");
            httpUrl.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36");
            httpUrl.connect();

            bis = new BufferedInputStream(httpUrl.getInputStream());
            fos = new FileOutputStream(path + File.separator + fileName);
            while ((size = bis.read(buf)) != -1) {
                fos.write(buf, 0, size);
            }
            fos.flush();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (ClassCastException e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                fos.close();
                bis.close();
                httpUrl.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            } catch (NullPointerException e) {
                e.printStackTrace();
                return null;
            }
        }
        return fileName;
    }

    public static void main(String[] args) {
        String s = "https://img2048.xyz/images/2019/08/17/j2019-0819-17.jpg";
        int i = s.lastIndexOf(".");
        FileUtil.downloadImg("d:\\code", s);

    }
}
