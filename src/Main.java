import gui.LoadingScreen;
import gui.LoginChoiceFrame;
import threads.LoadingThread;

public class Main {

    public static void main(String[] args) {

        LoadingScreen screen = new LoadingScreen();

        LoadingThread t = new LoadingThread() {
            @Override
            public void run() {
                try {
                    for (int i = 1; i <= 100; i++) {
                        screen.bar.setValue(i);
                        Thread.sleep(20);
                    }

                    synchronized (this) {
                        notify();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        t.start();

        synchronized (t) {
            try {
                t.wait();
            } catch (Exception e) {}
        }

        screen.dispose();
        new LoginChoiceFrame();
    }
}
