package annotations;

import java.io.FileReader;
import java.lang.reflect.Method;
import java.util.Properties;

public class TestFramework {
  public static void main(String[] args) throws Throwable {
    Properties props = new Properties();
    props.load(new FileReader("Test.properties"));
    String classToLoad = props.getProperty("class1");
    System.out.println("about to load class " + classToLoad);

    Class<?> clazz = Class.forName(classToLoad);
    System.out.println("loaded " + clazz.getName());

    Object obj = clazz.getDeclaredConstructor().newInstance();
    System.out.println("constructed an object " + obj);
//    Method[] methods = clazz.getMethods();
    Method[] methods = clazz.getDeclaredMethods();
    for (Method m : methods) {
      System.out.println("found method: " + m);
      RunMe rm = m.getAnnotation(RunMe.class);
      if (rm != null) {
        System.out.println("**** RunMe annotation found! value=" + rm.value()
          + " name=" + rm.name() + " count=" + rm.count()
          + " exception=" + rm.exception().getName()
        );
        Object exc = rm.exception();
        try {
          // doesn't set accessible "false"! That call enables access checks!
          // setAccessible(true) really merely DISABLES access checks
          // if using a SecurityManager (in "old" Java), or if using
          // the module system (JPMS) this can be prohibited
          m.setAccessible(true); // bypass private access control
          m.invoke(obj);
        } catch (Throwable t) {
          System.out.println("got exception: " + t);
          Throwable target = t.getCause();
          // check if t is a problem with invoke, versus a problem with
          // the method that was invoked
          if (target.getClass() == exc) {
            System.out.println("caught expected exception " + target.getMessage());
          } else {
            System.out.println("Caught unexpected exception");
            System.out.println("Caught is: " + target.getClass().getName());
            System.out.println("Expected is: " + exc.getClass().getName());
          }
        }
        System.out.println("method was invoked...");
      }
    }
  }
}
