import java.util.Random;

public class Tasks {
  String longTask() throws InterruptedException {
    int delay = new Random().nextInt(1000) + 4000;
    System.out.println("longTask delaying for: " + delay);
    Thread.sleep(delay);
//    throw new RuntimeException("longTask failed");
    System.out.println("longTask finished");
    return "\n- long task result,";
  }

  String shortTask() throws InterruptedException {
    int delay = new Random().nextInt(1000) + 1000;
    System.out.println("shortTask delaying for: " + delay);
    Thread.sleep(delay);
//    throw new RuntimeException("shortTask failed");
    System.out.println("shortTask finished");
    return "\n- short task result,";
  }

  String fail() throws InterruptedException {
    Thread.sleep(2500);
    throw new RuntimeException("OH NO :-(");
  }
}
