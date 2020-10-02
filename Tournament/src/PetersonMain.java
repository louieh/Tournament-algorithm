public class PetersonMain implements Runnable{
    Peterson lock = new Peterson();
    int NUMBER_OF_ITERATIONS = 10000;
    int counter = 0;

    @Override
    public void run() {
        Test test1 = new Test(this, 0);
        Test test2 = new Test(this, 1);
        Thread test1_thread = new Thread(test1);
        Thread test2_thread = new Thread(test2);
        test1_thread.start();
        test2_thread.start();
    }

    public static void main(String[] args) {
        System.out.println("hello world");
        PetersonMain petersonMain = new PetersonMain();
        Thread thread = new Thread(petersonMain);
        thread.start();
    }
}
