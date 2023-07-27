package annotations;

// two "tokens" but conventionally run together @Name
//public @ interface RunMe {

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
//@Target({ElementType.METHOD, ElementType.CONSTRUCTOR})
@Target(ElementType.METHOD)
public @interface RunMe {
  Class<?> exception() default Object.class;
  String value();
  int count() default 10;
  String name() default "Unknown";
  // return types of these methods must be one of:
  // primitives (NOT wrappers)
  // String
  // Class
  // Annotation
  // Enums
  // Or array of the above (array is NOT "above")
}
