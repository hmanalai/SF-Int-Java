package superiterable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class SuperIterable<E> implements Iterable<E> {
  private Iterable<E> self;

  public SuperIterable(Iterable<E> self) {
    this.self = self;
  }

  // forEvery illustrate forEach :) But forEach is already provided
  // by the Iterable behavior
  public void forEvery(SuperIterable<E> this, Consumer<? super E> op) {
    for (E e : this.self) {
      op.accept(e);
    }
  }

  public SuperIterable<E> filter(SuperIterable<E> this, Predicate<? super E> criterion) {
    List<E> results = new ArrayList<>();
    for (E s : this.self) {
      if (criterion.test(s)) {
        results.add(s);
      }
    }
    return new SuperIterable<>(results);
  }

  // Bucket o'items with a map method like this
  // it's called a "Functor"
  public <F> SuperIterable<F> map(
    //                         contravariance covariance
    SuperIterable<E> this, Function<? super E, ? extends F> op) {
    List<F> res = new ArrayList<>();
    for (E e : this.self) {
      res.add(op.apply(e));
    }
    return new SuperIterable<>(res);
  }

  // if you have a Bucket o'items which has a flatmap like this
  // it's called a "Monad"
  public <F> SuperIterable<F> flatMap(
    SuperIterable<E> this, Function<E, SuperIterable<F>> op) {
    List<F> res = new ArrayList<>();
    for (E e : this.self) {
      SuperIterable<F> fs = op.apply(e);
      for (F f : fs) {
        res.add(f);
      }
    }
    return new SuperIterable<>(res);
  }

  @Override
  public Iterator<E> iterator() {
    return self.iterator();
  }
}
