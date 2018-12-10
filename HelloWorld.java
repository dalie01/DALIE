
public class HelloWorld {

  private static final String s1;

  static {
    s1 = "HelloWorld";
  }

  public static void main(String[] args) {
      System.out.println("Hello");
      System.out.println(s1);
      System.out.println("Hello" + s1);
  }

}
