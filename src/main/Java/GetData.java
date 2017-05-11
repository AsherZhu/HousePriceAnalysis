package estateDataAnalysis;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by ZhuShuai
 * 2017-05-09 14:18.
 */
public class GetData {

    private static final String ERSHOUFANG = "http://bj.lianjia.com/ershoufang";
    private static final String URL = "http://bj.lianjia.com";

    public static void main(String[] args) {
        try {
            //利用Jsoup 读取网页源代码 Document引用jsoup包
            Document documents = Jsoup.connect(ERSHOUFANG).cookie("lianjia_uuid", "3a4a801b-dbf8-4c15-b5a5-5599f2e77145").get();

            //获取地区
            Elements elements = documents.select("div[data-role=ershoufang]").first().select("a[href^=/ershoufang]");
            for (Element element : elements) {

                //迭代获取href标签内的内容
                String areaURL = element.attr("href");

                //迭代获取文本内容
                String areaCH = element.text();

                Document document = Jsoup.connect(URL.concat(areaURL)).cookie("lianjia_uuid", "3a4a801b-dbf8-4c15-b5a5-5599f2e77145").get();
                //获取地区销售数量
                int total = Integer.parseInt(document.select("h2[class=total fl]").first().child(0).text());

                //除以每页数量向上取整
                int pages = (int) Math.ceil(total / 30d);

                System.out.println(areaCH + "地区有：" + total + " 套房出售，合计：" + pages + " 页");
                for (int i = 0; i < pages; i++) {
                    System.out.println(areaCH + "第：" + (i + 1) + "页");
                    page(i + 1, areaURL, areaCH);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void page(int page, String areaURL, String areaCH) {
        try (
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("G:\\work\\Java_Study\\src\\main\\java\\estateDataAnalysis\\data\\".concat(areaCH), true))
        ) {
            //利用Jsoup 读取网页源代码 Document引用jsoup包
            //cookie 增加链家网的lianjia_uuid,fe547e4f-b83f-49be-9708-5af2d41ebef4
            Document document = Jsoup.connect(URL.concat(areaURL) + "pg" + page).cookie("lianjia_uuid", "3a4a801b-dbf8-4c15-b5a5-5599f2e77145").get();

            //获取li数量 进行进行迭代输出
            Elements elements = document.select("li[class=clear]");

            int conter = 0;
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
