package com.lab5;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class TextManager{

    ArrayList<String> text = new ArrayList<>();

    CyclicBarrier barrier;


    public TextManager() {
        int n = 4;
        barrier = new CyclicBarrier(n-1, new Runnable() {
            @Override
            public void run() {
                System.out.println("Count A and B are the same in 3 lines");
            }
        });

        text.add("ABCDABCCDABCCDDAAAAABCABC");
        text.add("ABCDABCCBBBBBBBBBBAABCABC");
        text.add("ABCCCCCCCBBBBDDBBBBABCABC");
        text.add("ABCDABCCAAAAAAAAAAAABCABC");


        for (int i = 0; i < n; i++) {
            new Thread(new TextWorker(barrier, text.get(i), i)).start();
        }

    }


}

