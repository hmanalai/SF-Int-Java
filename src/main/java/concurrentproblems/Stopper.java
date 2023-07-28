package concurrentproblems;

public class Stopper {
  // volatile is ONE way to create a visibility promise
  // (aka "happens-before" relationship)
  // NOTE: if something else changes the value between the write and the read
  // you will read the *latest* value (this is sometimes called a "race")
  // i.e. TIMING is still YOUR PROBLEM
  // also write operations that occur *without* the visibility promise
  // MIGHT BE SEEN anyway!!! But aren't guaranteed.
  // YOU CANNOT TEST A THREADED PROGRAM ENOUGH TO PROVE CORRECTNESS!!!!
  // YOU MUST PROVE CORRECTNESS BY UNDERSTANDING THE LANGUAGE CONCURRENCY RULES
  public static /*volatile*/ boolean stop = false;

  public static void main(String[] args) throws Throwable {
    Runnable stopperTask = () -> {
      System.out.println("Task starting in thread "
        + Thread.currentThread().getName());

      while (! stop)
        ;

      System.out.println("Task stopping in thread "
        + Thread.currentThread().getName());
    };

    System.out.println("main about to start task");
    new Thread(stopperTask).start();

    System.out.println("main pausing...");
    Thread.sleep(1_000);
    stop = true;
    System.out.println("main has set stop to: " + stop + " and is exiting");
  }
}
