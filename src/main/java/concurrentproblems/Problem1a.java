package concurrentproblems;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.locks.ReentrantLock;

class MyCounter implements Runnable {
  private Object rendezvous = new Object();
//  ReentrantLock lock = new ReentrantLock();
//  AtomicLong counter = new AtomicLong();
  LongAccumulator counter = new LongAccumulator((a, b) -> a + b, 1L);
//  private /*volatile*/ long counter = 0;
  @Override
  public void run() {
    for (int i = 0; i < 100_000_000; i++) {
//      counter.getAndIncrement();
      counter.accumulate(1);
//      synchronized (rendezvous) { // mutual exclusion when used correctly
//      lock.lock();
//      try {
//        counter++;
//      } finally {
//        lock.unlock();
//      }
    }
    System.out.println("finishing");
  }

  public long getCounter() {
//    return counter;
    return counter.get();
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
