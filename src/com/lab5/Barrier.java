package com.lab5;

import java.util.ArrayList;

import static java.lang.Thread.sleep;

public class Barrier {
        private int value;
        private int max;
        ArrayList<Squad> squads;

        Barrier(int count, ArrayList<Squad> squads) {
            this.max = count;
            this.value = 0;
            this.squads = squads;
        }

        public boolean checkAllSquads() {
            for (Squad squad : squads) {
                if (!squad.isGoodDorection()) {
                    return false;
                }
            }
            return true;
        }

        public synchronized void await() throws InterruptedException {
            value++;
            if (value == max) {
                if (!checkAllSquads()) {
                    value = 0;
                    notifyAll();
                    System.out.println("No!");
                }
                else {
                    sleep(1000);
                    System.out.println("All squads are ready!");
                }
            } else {
                wait();
            }
        }


    }
