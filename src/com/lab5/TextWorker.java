package com.lab5;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class TextWorker implements Runnable {

    CyclicBarrier barrier;
    int str_number;
    String thread_text;

    public TextWorker(CyclicBarrier barrier, String text, int i) {
        this.barrier = barrier;
        this.thread_text = text;
        this.str_number = i;
    }

    public Integer[] CountAandB() {
        Integer a = 0;
        Integer b = 0;
        for (int i = 0; i < thread_text.length(); i++) {
            if (thread_text.charAt(i) == 'A') {
                a++;
            }
            if (thread_text.charAt(i) == 'B') {
                b++;
            }
        }
        return new Integer[]{a, b};
    }

    @Override
    public void run() {
        Integer[] count = CountAandB();
        for (int i = 0; i < thread_text.length(); i++) {
            if (count[0] == count[1]) {
                try {
                    barrier.await();
                    System.out.println("Thread " + str_number + " !!");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
            if (count[0] < count[1]) {
                if (count[0] < thread_text.length() / 4) {
                    if (thread_text.charAt(i) == 'C') {
                        thread_text = thread_text.replace('C', 'A');
                    }
                } else {
                    if (thread_text.charAt(i) == 'B') {
                        thread_text = thread_text.replace('B', 'D');
                    }
                }
            } else {
                if (count[1] < thread_text.length() / 4) {
                    if (thread_text.charAt(i) == 'D') {
                        thread_text = thread_text.replace('D', 'B');
                    }
                } else {
                    if (thread_text.charAt(i) == 'A') {
                        thread_text = thread_text.replace('A', 'C');
                    }
                }
            }
            System.out.println(thread_text);
            count = CountAandB();
        }
    }
}
