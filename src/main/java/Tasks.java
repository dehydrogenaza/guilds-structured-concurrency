import java.util.Random;

public class Tasks {
  String getFoo() throws InterruptedException {
    int delay = new Random().nextInt(1000) + 1000;
    System.out.println("getFoo delaying for: " + delay);
    Thread.sleep(delay);
//    throw new RuntimeException("getFoo failed");
    System.out.println("getFoo finished");
    return "foo";
  }

  String getBar() throws InterruptedException {
    int delay = new Random().nextInt(1000) + 2000;
    System.out.println("getBar delaying for: " + delay);
    Thread.sleep(delay);
    System.out.println("getBar finished");
    return "bar";
  }

  String fail() throws InterruptedException {
    Thread.sleep(1800);
    throw new RuntimeException("OH NO :-(");
  }
}
