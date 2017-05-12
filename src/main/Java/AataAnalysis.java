

import java.io.*;
import java.util.*;

/**
 * Created by ZhuShuai
 * 2017-05-11 13:25.
 */
public class AataAnalysis {
    private static int counter;


    public static void main(String[] args) {
        //读取文件名
        String path = "src/main/Java/data/bj/";
        File fileName = new File(path);
        File areaFilesNames[] = fileName.listFiles();
//        System.out.println(areaFilesNames.length);
        for (File areaFileName : areaFilesNames) {
//            System.out.print(areaFileName.getName());
            Set<String> set = new HashSet<>();

            try (
                    BufferedReader reader = new BufferedReader(new FileReader(path.concat(areaFileName.getName())))) {
                String line;
                counter++;
                while ((line = reader.readLine()) != null) {
                    if (line.contains("车库") || line.contains("车位")) {
                        continue;
//                        System.out.println(line);
                    }
                    set.add(line);
//                    System.out.println(set.size());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            List<Double> list = new ArrayList<>();
            for (String s : set) {
                String[] strings = s.split("α");
                list.add(Double.valueOf(strings[3]));
            }

//            System.out.print(Collections.min(list));
//            System.out.print(Collections.max(list));
            Double sum = 0d;
            for (Double aDouble : list) {
                sum += aDouble;
            }

            System.out.print(Math.round(sum/list.size()));
            System.out.print(",");

//            try {
//                Thread.sleep(2000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
        }
        System.out.println(counter);
    }
}

