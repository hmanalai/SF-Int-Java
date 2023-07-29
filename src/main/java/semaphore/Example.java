package semaphore;

import java.util.concurrent.Semaphore;

public class Example {
  public static void main(String[] args) {
    Semaphore sem = new Semaphore(0);
    new Thread(() -> {
      System.out.println("waiter starting, attempting to acquire 3 permits");
      try {
        sem.acquire(3);
      } catch (InterruptedException e) {
        System.out.println("Huh, interrupted? That's a surprise");
      }
      System.out.println("waiter now continuing...");
    }).start();

    new Thread(() -> {
      System.out.println("releaser started...");
      for (int i = 0; i < 3; i++) {
        try {
          Thread.sleep((int) (Math.random() * 2000) + 1000);
          sem.release(); // one release
          System.out.println("releaser released one permit!");
        } catch (InterruptedException e) {
          System.out.println("Huh, interrupted? That's a surprise");
        }
      }
      System.out.println("releaser finished");
    }).start();
    System.out.println("main exiting");
  }
}
