package immutablestring;

public class Example {
  public static void main(String[] args) {
    String name = "fred";
    System.out.println(name);
    String upperName = name.toUpperCase();
    System.out.println(name);
    System.out.println(upperName);
    name = name.toUpperCase();
    System.out.println("now name refers to " + name);
  }
}
