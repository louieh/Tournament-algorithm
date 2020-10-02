import java.util.concurrent.atomic.AtomicIntegerArray;

public class Peterson {
    private final AtomicIntegerArray flag = new AtomicIntegerArray(2);
    private volatile int turn;
    int temp;

    public void lock(int id) {
        flag.set(id, 1);
        turn = 1 - id;
        while (flag.get(1 - id) == 1 && turn != id) {
            temp++;
        }
    }

    public void unlock(int id) {
        flag.set(id, 0);
    }
}
