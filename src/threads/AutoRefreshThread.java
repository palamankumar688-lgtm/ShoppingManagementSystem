package threads;

public class AutoRefreshThread extends Thread {

    private final Object lock;
    private final Runnable task;

    public AutoRefreshThread(Object lock, Runnable task) {
        this.lock = lock;
        this.task = task;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (lock) {
                task.run(); // thread-safe refresh
            }

            try {
                Thread.sleep(5000);
            } catch (Exception e) {}
        }
    }
}
