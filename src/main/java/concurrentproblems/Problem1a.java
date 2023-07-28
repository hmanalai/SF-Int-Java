package concurrentproblems;

class MyCounter implements Runnable {
  private Object rendezvous = new Object();
  private /*volatile*/ long counter = 0;
  @Override
  public void run() {
    for (int i = 0; i < 1_000_000_000; i++) {
      synchronized (rendezvous) { // mutual exclusion when used correctly
        counter++;
      }
    }
    System.out.println("finishing");
  }

  public long getCounter() {
    return counter;
  }
}

public class Problem1a {
  public static void main(String[] args) throws Throwable {
    MyCounter mc = new MyCounter();
    Thread t1 = new Thread(mc);
    Thread t2 = new Thread(mc);
    long start = System.nanoTime();
    t1.start();
    t2.start();
// Timing!!!!
// Transactions :(
// Visibility!!??!!??

//    mc.run();
//    mc.run();
    t1.join();
    t2.join();
    long time = System.nanoTime() - start;
    System.out.println("count value is " + mc.getCounter());
    System.out.printf("time taken %7.3f\n", (time / 1_000_000_000.0));
  }
}
