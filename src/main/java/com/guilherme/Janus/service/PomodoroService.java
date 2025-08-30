package com.guilherme.Janus.service;

public class PomodoroService {

    public void temporizador(int tempo, String texto){

        tempo = tempo*60;

        System.out.print("\n"+texto+"\n");

        while (tempo > -1) {
            int min = tempo/60;
            int sec = tempo%60;

            System.out.print("\r"+String.format("%02d:%02d", min, sec));

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            tempo--;
        }
    }

}
