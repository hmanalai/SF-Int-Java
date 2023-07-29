package reduction;

import java.util.OptionalDouble;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.DoubleStream;

record Average(double sum, long count) {
  public Average merge(Average other) {
    return new Average(this.sum + other.sum, this.count + other.count);
  }

  public OptionalDouble get() {
    if (count > 0) {
      return OptionalDouble.of(sum / count);
    } else {
      return OptionalDouble.empty();
    }
  }
}

/*
see the activity of the JIT compiler:
-XX:+PrintCompilation
 */
// Scalability: "Amdahl's law"
// Java threads are given a "private" memory area, called a TLAB
// "Thread-local allocation buffer", so they can allocate objects
// without waiting for other threads ... until your allocate rate
// becomes so high that you use up your TLAB, then you must go back
// to the *shared* heap for more
public class Averages {
  public static void main(String[] args) {
    long limit = 3_000_000_000L;
    long start = System.nanoTime();
    DoubleStream.generate(() -> ThreadLocalRandom.current().nextDouble(-1, +1))
      .parallel()
      .limit(limit)
      .mapToObj(d -> new Average(d, 1))
//      .reduce(new Average(0, 0), (a1, a2) -> a1.merge(a2))
      .reduce(new Average(0, 0), Average::merge)
      .get()
      .ifPresentOrElse(m -> System.out.println("Mean is " + m),
        () -> System.out.println("No data!"));
    long time = System.nanoTime() - start;
    System.out.printf("Total time is %7.3f rate is %,9.3f per second\n",
      (time / 1_000_000_000.0), (1_000_000_000.0 * limit / time));
  }
}
