public class Test extends Thread {
    PetersonMain petersonMain;
    int id;

    public Test(PetersonMain petersonMain, int id) {
        this.petersonMain = petersonMain;
        this.id = id;
    }

    @Override
    public void run() {
        for (int i = 0; i < petersonMain.NUMBER_OF_ITERATIONS; i++) {
            petersonMain.lock.lock(id);
            petersonMain.counter += 1;
            petersonMain.lock.unlock(id);
        }
        System.out.println("my id: " + id + " counter: " + petersonMain.counter);
    }
}
