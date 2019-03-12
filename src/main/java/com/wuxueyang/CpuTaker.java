package com.wuxueyang;

import java.util.*;
import java.util.concurrent.*;

public class CpuTaker {
  public static void main(String argv[]) throws Exception {
    if (argv.length < 3) {
      System.err.println("java -jar Anonymous-jar-with-dependencies.jar [thread number] [memory limit] [cores]");
      System.exit(1);
    }

    int threadNumber = 0, memoryLimit = 0, cores = 0;
    try {
      threadNumber = Integer.valueOf(argv[0]);
      memoryLimit = Integer.valueOf(argv[1]);
      cores = Integer.valueOf(argv[2]);
    } catch (Exception e) {
      System.err.println("Specify parameters must be Integer! use default!");
      threadNumber = 8;
      memoryLimit = 2 * 1024 * 1024 * 1024; // 2GB
      cores = 8;
    }

    ConcurrentLinkedDeque<String> deque = new ConcurrentLinkedDeque<>();

    ExecutorService es = Executors.newFixedThreadPool(cores);

    for (int i = 0; i < threadNumber; i++) {
      es.execute(new Taker());
    }

    int limit = memoryLimit / 128;
    while (deque.size() <= limit) {
      deque.add(generateString(128));
      while (deque.size() >= limit) {
        deque.pollFirst();
      }
    }
  }

  private static String character = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

  public static String generateString(int size) {
    Random random = new Random();
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < size; i++) {
      sb.append(character.charAt(random.nextInt(26)));
    }
    return sb.toString();
  }
}
