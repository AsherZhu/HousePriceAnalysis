package estateDataAnalysis;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.SocketTimeoutException;

/**
 * Created by ZhuShuai
 * 2017-05-11 09:35.
 */
public class GetDataMultitherading implements Runnable{
    private static final String URL = "http://bj.lianjia.com/ershoufang/";
    private static final String UUID_KEY = "lianjia_uuid";
    private static final String UUID_VALUE = "5a03cab4-445c-4b5a-bb92-dfb73f76223f";
    private String areaName;
    private String areaNameUrl;

    public GetDataMultitherading(String areaName, String areaNameUrl) {
        this.areaName = areaName;
        this.areaNameUrl = areaNameUrl;
    }

    public static void main(String[] args) {
        try {
            Document documents = Jsoup.connect(URL).cookie(UUID_KEY,UUID_VALUE).get();
            Elements elements = documents.select("div[data-role=ershoufang]").first().select("a");
            for (Element element : elements) {
                String arerNameUrl = element.attr("href").replaceAll("(ershoufang|/)", "");
                String areaName = element.text();
                Thread thread = new Thread(new GetDataMultitherading(areaName,arerNameUrl));
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {


        System.out.println(areaNameUrl);
        try {
            Document areaDocumen= Jsoup.connect(URL.concat(areaNameUrl)).cookie(UUID_KEY, UUID_VALUE).get();
        int totalHouse = Integer.parseInt(areaDocumen.select("h2[class*=total]").first().child(0).text());
        int pages = (int) Math.ceil(totalHouse / 30d);

        for (int i = 0; i < pages; i++) {
            page(areaNameUrl, areaName, i+1);
            System.out.println("正在下载"+areaName+"第"+(i+1)+"页");
        }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private static void page(String areaNameUrl, String areaName, int page) {
        int cerent = 0;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("G:\\work\\Java_Study\\src\\main\\java\\estateDataAnalysis\\data\\bj\\" + areaName, true))) {
            Document document = Jsoup.connect(URL.concat(areaNameUrl) + "/pg" + page).cookie(UUID_KEY, UUID_VALUE).get();
            Elements elements = document.select("li[class=clear]");
            for (Element element : elements) {
                String imageUrl = element.childNode(0).attr("href");
                String id = imageUrl.substring(imageUrl.lastIndexOf("/") + 1, imageUrl.lastIndexOf("."));
                String region = element.select("a[data-el=region]").first().text();
                String totalPrice = element.select("div[class=totalPrice]").first().child(0).text();
                Element houseInfoElement = element.select("div[class=houseInfo]").first();
                String houseInfo = houseInfoElement.childNode(2).toString();
                String unitPrice = element.select("div[class=unitPrice]").first().attr("data-price");
                writer.write(id + "α" + region + "α" + houseInfo + "α" + totalPrice + "α" + unitPrice + "\n");
                System.out.println(++cerent);
            }
        } catch (SocketTimeoutException e) {
            System.out.println("---socket time out: " + areaName + ", page: " + page);
            page(areaNameUrl, areaName, page);
        } catch (HttpStatusException e) {
            System.out.println("---http status code: " + areaName + ", page: " + page);
            page(areaNameUrl, areaName, page);
        } catch (IOException e) {
            System.out.println("---io exception: " + areaName + "page: " + page);
            page(areaNameUrl, areaName, page);
        }
    }

}
