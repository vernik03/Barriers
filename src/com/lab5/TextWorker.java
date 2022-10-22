package com.lab5;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class TextWorker implements Runnable {

    CyclicBarrier barrier;
    int str_number;
    String thread_text;
    Boolean end;
    Integer global_end;


    public TextWorker(CyclicBarrier barrier, String text, int i, Integer global_end) {
        this.barrier = barrier;
        this.thread_text = text;
        this.str_number = i;
        this.end = false;
        this.global_end = global_end;
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
        while  (!end){

            for (int i = 0; i < thread_text.length(); i++) {
                //System.out.println(global_end);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (global_end >= 3) {
                    end = true;
                    break;
                }
                if (count[0] == count[1]) {
                    try {
                        System.out.println(thread_text);
                        System.out.println("Thread " + str_number + " done");
                        global_end++;
                        end = true;
                        barrier.await();
                        break;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                }
                char old_char = thread_text.charAt(i);
                int random = (int) (Math.random() * 2);
                if (random == 0) {
                    switch (old_char) {
                        case 'A':
                            thread_text = thread_text.replace('A', 'C');
                            break;
                        case 'C':
                            thread_text = thread_text.replace('C', 'A');
                            break;
                        case 'B':
                            thread_text = thread_text.replace('B', 'D');
                            break;
                        case 'D':
                            thread_text = thread_text.replace('D', 'B');
                            break;
                    }
                }
                /*

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
                }*/
                //System.out.println(thread_text);
                count = CountAandB();
            }
        }
    }
}
