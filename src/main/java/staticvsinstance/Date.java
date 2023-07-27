package staticvsinstance;

public class Date {
  private int day;
  private int month;
  private int year;

  public Date(int day, int month, int year) {
    this.day = day;
    this.month = month;
    this.year = year;
  }

//  public boolean isLeap(/*@Deprecated*/ Date this) {
//  public boolean isLeap() { // makes little sense requiring an instance of Date
//  public boolean isLeap(int year) {
//    return year % 4 == 0 && year % 100 != 0 || year % 400 == 0;
//    return this.year % 4 == 0 && this.year % 100 != 0 || this.year % 400 == 0;
//  }

  public static boolean isLeap(int year) {
    return year % 4 == 0 && year % 100 != 0 || year % 400 == 0;
  }

  public static int daysInMonth(int month, int year) {
//    if (month < 1 || month > 12) throw new IllegalArgumentException("Bad month");
    return switch(month) {
      case 9, 4, 6, 11 -> { // arrow never falls through :)
        System.out.println("looks like a thirty to me");
        yield 30;
      }
      case 1, 3, 5, 7, 8, 10, 12 -> 31;
      case 2 -> isLeap(year) ? 29 : 28;
      default -> throw new IllegalArgumentException("Bad month");
    };
  }

  public /*static*/ String asText() {
//  public /*static*/ String asText(Date this) {
//  public static String asText(Date d) {
//    return "Date day=" + d.day + " month=" + d.month + " year=" + d.year;
    return "Date day=" + this.day + " month=" + this.month + " year=" + this.year;
  }
}

class UseDate {
  public static void main(String[] args) {
    Date d = new Date(1, 1, 2023);
//    d.isLeap(2023);
    d.isLeap(2023);
    System.out.println("date is " + d);

//    System.out.println(Date.asText(d));
    System.out.println(d.asText());
    d = null;


  }
}
