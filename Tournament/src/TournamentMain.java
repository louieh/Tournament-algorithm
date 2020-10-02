// throughput: number of requests per unit time

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.time.Instant;

public class TournamentMain implements Runnable {
    int n; // process number
    int NUMBER_OF_ITERATIONS = 1;
    int counter = 0;
    int levels;
    Peterson[] LOCKS;

    public TournamentMain(int n) {
        this.n = n;
        this.levels = (int) Math.round(Math.log(n) / Math.log(2)); // 2
        this.LOCKS = new Peterson[n - 1];
        for (int i = 0; i < n - 1; i++) {
            Peterson lock = new Peterson();
            LOCKS[i] = lock;
        }
    }

    @Override
    public void run() {
        Thread[] threads = new Thread[n];
        for (int i = 0; i < n; i++) {
            Tournament tournament = new Tournament(this, i + 1);
            Thread thread = new Thread(tournament);
            threads[i] = thread;
            threads[i].start();
        }
        for (int i = 0; i < n; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            File file = new File("counter.out");
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(file.getName(), true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write("thread number: " + n + "; iterations number: " + NUMBER_OF_ITERATIONS + "; counter: " + counter);
            bufferedWriter.write("\n");
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("thread number: " + n + "; iterations number: " + NUMBER_OF_ITERATIONS + "; counter: " + counter);
    }

    public static void main(String[] args) {
        System.out.println("Hello, this is TournamentMain function");
        TournamentMain tournamentMain = new TournamentMain(2);
        Thread thread = new Thread(tournamentMain);
        thread.start();
    }

}
