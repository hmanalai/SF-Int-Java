package generics;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

interface MyList<E> {
  void add(E e);
//  static void addAnother(E e); // this can't work (like this)
}

public class Erasure {
//  public static <A extends B, B, E extends F, F> F[] asArray(List<E> input, Class<F> type) {
  public static <E extends F, F> F[] asArray(List<E> input, Class<F> type) {
//    E[] result = new E[input.size()]; // this doesn't work! What base type
//    E[] result = (E[])new Object[input.size()];
//    E[] result = (E[])Array.newInstance(type, input.size());
    F[] result = (F[])Array.newInstance(type, input.size());
    int idx = 0;
    for (E e : input) {
      result[idx++] = e;
    }
    return result;
  }
  public static void breakMyList(List l) {
    l.add(0, LocalDate.now());
  }

  public static void main(String[] args) {
//    List<String> names = new ArrayList(List.of(LocalDate.now()));
//    List<String> names = new ArrayList<String>(List.of(LocalDate.now()));
    List<String> names = new ArrayList<>(/*List.of(LocalDate.now())*/);
    names.add("Fred");
    names.add("Jim");
    names.add("Sheila");
    System.out.println(names);

//    breakMyList(names);
//    names.add(LocalDate.now());
    System.out.println(names);

//    String firstName = (String)names.get(0);
    String firstName = names.get(0);

    CharSequence[] sa = asArray(names, CharSequence.class);
    sa[2] = new StringBuilder("Alice");
    System.out.println(Arrays.toString(sa));
    System.out.println(sa.getClass().getName());
  }
}
