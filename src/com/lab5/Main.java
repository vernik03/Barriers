package com.lab5;

import java.util.ArrayList;

import static java.lang.Thread.sleep;

public class Main {
    public static int count_in_squad = 5;
    public static int squad_count = 4;
    public static ArrayList<Soldier> soldiers;
    public static void main(String[] args) throws InterruptedException {
        ArrayList<Squad> squads = new ArrayList<>();
        Barrier barrier = new Barrier(squad_count, squads);
        soldiers = new ArrayList<>();
        for (int i = 0; i < count_in_squad*squad_count; i++) {
                soldiers.add(Soldier.Front);
        }
        for (int i = 0; i < squad_count; i++) {
            squads.add(new Squad(count_in_squad, barrier, soldiers,
                    i*count_in_squad, i*count_in_squad + count_in_squad, i+1));
            sleep(100);
        }
        for (Squad squad : squads) {
            squad.start();
        }
    }
}

