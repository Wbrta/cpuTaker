package com.wuxueyang;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Taker extends Thread {
    private ArrayList<Integer> arrays = new ArrayList<Integer>();

    public Taker() {
        Random random = new Random();
        for (int i = 0; i < 1000000; i++) {
            arrays.add(random.nextInt());
        }
    }

    @Override
    public void run() {
        while (true) {
            arrays.sort((Integer o1, Integer o2) -> {
                if (o1 >= o2) return 1;
                else return -1;
            });
            Collections.shuffle(arrays);
        }
    }
}