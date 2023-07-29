package collect;

import java.util.OptionalDouble;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

class Average {
  private double sum;
  private long count;

  public Average(double sum, long count) {
    this.sum = sum;
    this.count = count;
  }

  public void merge(Average other) {
    this.sum += other.sum;
    this.count += other.count;
  }

  public void include(double d) {
    this.sum += d;
    this.count++;
  }

  public OptionalDouble get() {
    if (count == 0) {
      return OptionalDouble.empty();
    } else {
      return OptionalDouble.of(sum / count);
    }
  }
}

public class Averages {
  public static void main(String[] args) {
    long limit = 8_000_000_000L;
    long start = System.nanoTime();
    DoubleStream.generate(() -> ThreadLocalRandom.current().nextDouble(-1, +1))
      .limit(limit)
      .parallel()
      .collect(
        () -> new Average(0, 0),
        (a, d) -> a.include(d),
        (af, ai) -> af.merge(ai))
      .get()
      .ifPresentOrElse(m -> System.out.println("Mean is " + m),
        () -> System.out.println("No data"));

    long time = System.nanoTime() - start;
    System.out.printf("Total time is %7.3f rate is %,9.3f per second\n",
      (time / 1_000_000_000.0), (1_000_000_000.0 * limit / time));
  }
}
