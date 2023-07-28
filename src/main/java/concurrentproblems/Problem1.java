package concurrentproblems;

class MyTask implements Runnable {

  public static void doStuff(int x) {
    if (x == 0) return;
    System.out.println("x is " + x);
    doStuff(x - 1);
    System.out.println("x is " + x);
  }

  int i = 0;

  @Override
  public void run() {
    System.out.println("Starting task thread is "
      + Thread.currentThread().getName());
    for (; i < 1_000; i++) {
      System.out.println("i is " + i + " " + Thread.currentThread().getName());
    }
    System.out.println("Ending task thread is "
      + Thread.currentThread().getName());
  }
}

//class RecurseCaller {
//  public static void main(String[] args) {
//    MyTask.doStuff(3);
//  }
//}

public class Problem1 {
  public static void main(String[] args) {
    MyTask mt = new MyTask();
    System.out.println("about to start task " + Thread.currentThread().getName());
    Thread t1 = new Thread(mt);
    Thread t2 = new Thread(mt);
    t1.start();
    t2.start();
//    mt.run();
    System.out.println("returned from starting tasks " + Thread.currentThread().getName());
  }
}
