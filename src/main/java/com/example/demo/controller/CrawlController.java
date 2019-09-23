package com.example.demo.controller;

import com.example.demo.util.FileUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;

//爬虫示例
@RestController
public class CrawlController {

    private String fileName = "D:\\fuliba.txt";
    private String gxImgFileName = "D:\\fulibaGxImg.txt";
    private String gxImgFile = "D:\\gaoxiao";

    //抓搞笑图
    @RequestMapping("/crawlGx")
    public String crawlGx() {
        System.setProperty("webdriver.chrome.driver", "D:\\chromedriver_win32\\chromedriver.exe");
        WebDriver webDriver = new ChromeDriver();

        File file = new File(fileName);
        InputStream is = null;
        Reader reader = null;
        BufferedReader bufferedReader = null;
        try {
            is = new FileInputStream(file);
            reader = new InputStreamReader(is);
            bufferedReader = new BufferedReader(reader);
            String line = null;
            while (!StringUtils.isEmpty(line = bufferedReader.readLine())) {
                String[] strings = line.split("@@");
                webDriver.get(strings[0]);
                Document document = Jsoup.parse(webDriver.getPageSource());
                Document parse = Jsoup.parse(document.html());
                System.out.println("===srart==="+line);
                Element main = parse.getElementsByClass("article-content").first();
                Elements imgs = main.getElementsByTag("img");
                for (Element img : imgs) {
                    String src = img.attr("src");
                    FileUtil.Write2FileByOutputStream(gxImgFileName, src);
                    System.out.println(src);
                }
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
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
        webDriver.quit();
        return "";
    }

    //抓福利图

    //抓汇总链接
    @RequestMapping("/crawl")
    public String crawl(int pageNum) {
        System.setProperty("webdriver.chrome.driver", "D:\\chromedriver_win32\\chromedriver.exe");
        WebDriver webDriver = new ChromeDriver();
        webDriver.get("https://fulibus.net/category/flhz/page/" + pageNum);
        Document document = Jsoup.parse(webDriver.getPageSource());
        Document parse = Jsoup.parse(document.html());

        Elements elements = parse.getElementsByClass("excerpt");
        for (Element element : elements) {
            Elements a = element.getElementsByTag("a").eq(1);
            String href = a.attr("href");
            String title = a.attr("title");
            FileUtil.Write2FileByOutputStream(fileName, href + "@@" + title);
            System.out.println(href + " " + title);
        }

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        webDriver.quit();
        return "";
    }

    //下载图片
    public void downPic(){
        File file = new File(gxImgFileName);
        InputStream is = null;
        Reader reader = null;
        BufferedReader bufferedReader = null;
        try {
            is = new FileInputStream(file);
            reader = new InputStreamReader(is);
            bufferedReader = new BufferedReader(reader);
            String line = null;
            while (!StringUtils.isEmpty(line = bufferedReader.readLine())) {
                String resString = FileUtil.downloadImg(gxImgFile, line);
                System.out.println("下载>>"+line +"<<==>>"+resString);
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

    public static void main(String[] args) {
        CrawlController crawlController = new CrawlController();
        crawlController.downPic();
    }

}
