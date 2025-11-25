package threads;

public class LoadingThread extends Thread {

    @Override
    public void run() {
        try {
            // Simulate loading work
            for (int i = 1; i <= 100; i++) {
                System.out.println("Loading: " + i + "%");
                Thread.sleep(20); // slow down so loading looks real
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
