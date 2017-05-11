

import java.io.*;
import java.util.*;

/**
 * Created by ZhuShuai
 * 2017-05-11 13:25.
 */
public class AataAnalysis {
    private static int counter;

    private static Set<String> set = new HashSet<>();
    public static void main(String[] args) {
        String path = "G:\\work\\Java_Study\\src\\main\\java\\estateDataAnalysis\\data\\bj\\";
        File fileName = new File(path);
        File areaFilesNames[] = fileName.listFiles();
//        System.out.println(areaFilesNames.length);
        for (File areaFileName : areaFilesNames) {
//            System.out.println(areaFileName.getName());
            Set<String> set = new HashSet<>();
            try (
                    BufferedReader reader = new BufferedReader(new FileReader(path.concat(areaFileName.getName())))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    set.add(line);
                }
//                System.out.println(set.size());
            } catch (IOException e) {
                e.printStackTrace();
            }
            List<Double> list = new ArrayList<>();
            for (String s : set) {
                String[] strings = s.split("α");
                list.add(Double.parseDouble(strings[strings.length - 1]));
            }
            Collections.sort(list);
            double sum = 0d;
            for (Double aDouble : list) {
                sum += aDouble;
            }
        }
    }

}


