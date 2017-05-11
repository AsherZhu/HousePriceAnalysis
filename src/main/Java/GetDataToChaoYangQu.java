package estateDataAnalysis;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by ZhuShuai
 * 2017-05-10 10:26.
 */
public class GetDataToChaoYangQu {
    private static final String CHAOYANG_URL = "http://bj.lianjia.com/ershoufang/chaoyang/pg";

    public static void main(String[] args) {
        try {
            Document document = Jsoup.connect(CHAOYANG_URL).cookie("lianjia_uuid", "7f1b6221-8f63-42d4-967b-bfb02a555970").get();
//            <h2 class="total fl">共找到<span> 7337 </span>套北京二手房</h2>
            int total = Integer.parseInt(document.select("h2[class=total fl] span").text());
            int totalPage = (int) Math.ceil(total / 30d);
            for (int i = 0; i < totalPage; i++) {
                GetChaoYangData(CHAOYANG_URL, i + 1);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void GetChaoYangData(String CHAOYANG_URL,int page) {

        try (
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("G:\\work\\Java_Study\\src\\main\\java\\estateDataAnalysis\\data\\朝阳", true))
        ) {
            Document document = Jsoup.connect(CHAOYANG_URL + page).cookie("lianjia_uuid", "7f1b6221-8f63-42d4-967b-bfb02a555970").get();
            int conter = 0;
            Elements elements = document.select("li[class=clear]");
            for (Element element : elements) {
                //迭代小区
                String region = element.select("a[data-el=region]").first().text();

                //迭代房屋总价
                String totalPrice = element.select("div[class=totalPrice]").first().child(0).text();

                //迭代房屋信息
                Element houseInfoElement = element.select("div[class=houseInfo]").first();
                String houseInfo = houseInfoElement.childNode(2).toString();

                //迭代平米单价
                String unitPrice = element.select("div[class=unitPrice]").first().attr("data-price");

                bufferedWriter.write(region + "@" + houseInfo + "@" + totalPrice + "@" + unitPrice + "\n");
                System.out.println(++conter);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
