
public class Tournament implements Runnable {
    int id; // process id
    private int[] level_id; // to record the current id at each level of tree
    TournamentMain tournamentMain; // main thread
    int levels; // number of level

    public Tournament(TournamentMain tournamentMain, int id) {
        this.id = id;
        this.tournamentMain = tournamentMain;
        this.levels = tournamentMain.levels;
        this.level_id = new int[levels];
    }

    public void lock(int i, int n) {
        int nodeID = i + (n - 1);
        for (int level = 0; level < tournamentMain.levels; level++) {
            level_id[level] = nodeID % 2;
            nodeID /= 2;
            tournamentMain.LOCKS[nodeID - 1].lock(level_id[level]);
        }
    }

    public void unlock() {
        int nodeID = 1;
        for (int level = levels - 1; level >= 0; level--) {
            tournamentMain.LOCKS[nodeID - 1].unlock(level_id[level]);
            nodeID = 2 * nodeID + level_id[level];
        }
    }


    @Override
    public void run() {
        // long theStartRequestTime = System.currentTimeMillis();
        for (int i = 0; i < tournamentMain.NUMBER_OF_ITERATIONS; i++) {
            long theStartRequestOneTime = System.nanoTime();
            lock(id, tournamentMain.n);
            tournamentMain.counter += 1;
            unlock();
            long theEndReuqestOneTime = System.nanoTime();
            System.out.println("process id: " + id + "; start: " + theStartRequestOneTime + "; end: " + theEndReuqestOneTime + "time interval: " + (theEndReuqestOneTime - theStartRequestOneTime));
        }
        long theEndRequestTime = System.currentTimeMillis();
        // float throughput = tournamentMain.NUMBER_OF_ITERATIONS / (theEndRequestTime - theStartRequestTime);
        // System.out.println("process id: " + id + "counter: " + tournamentMain.counter + "; start: " + theStartRequestTime + "; end: " + theEndRequestTime + "time interval: " + (theEndRequestTime - theStartRequestTime) + "; throughput: " + throughput);
    }
}