package com.lab5;

import java.util.ArrayList;

public class Squad extends Thread {

    Barrier barrier;
    int id;
    int begin, end;
    public ArrayList<Soldier> soldiers;

    public Squad(int count, Barrier barrier, ArrayList<Soldier> soldiers, int begin, int end, int id) throws InterruptedException {
        this.soldiers = soldiers;
        this.barrier = barrier;
        this.begin = begin;
        this.end = end;
        this.id = id;
        firstTurn();
        System.out.println("Squad " + id + " from " + (begin + 1) + " to " + end + " is ready");
        printSoldiers();
        sleep(1000);
    }

    public void printSoldiers() {
        for (Soldier soldier : soldiers) {
            System.out.print(soldier + " ");
        }
        System.out.println();
    }

    public void turn() {
        for (int i = begin; i < end - 1; i++) {
            if (soldiers.get(i) != soldiers.get(i+1)) {
                if (soldiers.get(i) == Soldier.Left) {
                    soldiers.set(i, Soldier.Right);
                    if (i+1 < end-1) { soldiers.set(i+1, Soldier.Left);}
                } else {
                    soldiers.set(i, Soldier.Left);
                    if (i+1 < end-1) {soldiers.set(i+1, Soldier.Right);}
                }
            }
        }
    }

    public void run() {
        while (true) {
            try {
            while (true){
                if (isGoodDorection()){
                    System.out.printf("Squad %d is ready\n", id);
                    break;
                }
                else{
                    turn();
                    printSoldiers();
                        sleep(100);
                }
            }
            barrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public void firstTurn() {
        for (int i = begin; i < end; i++) {
            if (Math.random() > 0.5) {
                soldiers.set(i, Soldier.Left);
            } else {
                soldiers.set(i, Soldier.Right);
            }
        }
    }

    public boolean isGoodDorection() {
        for (int i = begin; i < end - 1; i++) {
            if (soldiers.get(i) != soldiers.get(i + 1)) {
                return false;
            }
        }
        return true;
    }
}

enum Soldier  {
    Front, Left, Right
}
