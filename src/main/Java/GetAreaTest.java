import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


import java.io.IOException;

/**
 * Created by ZhuShuai
 * 2017-05-09 20:06.
 */
public class GetAreaTest {
    public static final String ERSHOUFANG = "http://bj.lianjia.com/ershoufang";

    public static void main(String[] args) {
        try {
            Document document = Jsoup.connect(ERSHOUFANG).cookie("lianjia_uuid", "3a4a801b-dbf8-4c15-b5a5-5599f2e77145").get();
            Elements elements = document.select("div[data-role=ershoufang]").first().select("a[href^=/ershoufang]");
            for (Element element : elements) {
                System.out.println(element.attr("href"));
                System.out.println(element.text());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
