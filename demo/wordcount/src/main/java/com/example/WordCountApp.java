package com.example;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.broadcast.Broadcast;
import org.apache.spark.sql.SparkSession;

import scala.Tuple2;

public class WordCountApp {
    public static void main(String[] args) {
        String folderPath = "D:/projects/onlinebookstore/demo/wordcount/src/main/resources/input"; 
        String outputPath = "D:/projects/onlinebookstore/demo/wordcount/src/main/resources/output";

        List<String> keywords = Arrays.asList("爱", "死", "凶手", "高中", "想", "幸福");

        SparkSession spark = SparkSession.builder()
                .appName("KeywordCountApplication")
                .master("local") 
                .getOrCreate();

        JavaSparkContext sc = new JavaSparkContext(spark.sparkContext());

        File folder = new File(folderPath);
        File[] listOfFiles = folder.listFiles();

        if (listOfFiles == null || listOfFiles.length == 0) {
            System.out.println("文件夹为空或路径无效");
            spark.stop();
            return;
        }

        JavaRDD<String> allFileData = sc.emptyRDD();
        for (File file : listOfFiles) {
            if (file.isFile() && file.getName().endsWith(".txt")) {
                JavaRDD<String> fileData = sc.textFile(file.getAbsolutePath());
                allFileData = allFileData.union(fileData);
            }
        }

        Broadcast<List<String>> broadcastKeywords = sc.broadcast(keywords);

        JavaPairRDD<String, Integer> keywordCounts = allFileData.flatMapToPair(line -> {
            List<Tuple2<String, Integer>> counts = new ArrayList<>();
            for (String keyword : broadcastKeywords.value()) {
                if (line.contains(keyword)) {
                    counts.add(new Tuple2<>(keyword, 1));
                }
            }
            return counts.iterator();
        }).reduceByKey(Integer::sum);

        try {
            keywordCounts.saveAsTextFile(outputPath);
        } catch (Exception e) {
            System.out.println("写入输出文件失败：" + e.getMessage());
        }

        List<Tuple2<String, Integer>> result = keywordCounts.collect();
        for (Tuple2<String, Integer> entry : result) {
            System.out.println("关键词: " + entry._1() + "， 计数: " + entry._2());
        }

        spark.stop();
    }
}
