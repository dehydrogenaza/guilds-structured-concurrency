import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.StructuredTaskScope;
import java.util.function.Supplier;

public class Structured {
  public static void main(String[] args) {
    System.out.println("Hello, Structured Concurrency!");

    try {
      new Structured().runSomeTasks();
    } catch (ExecutionException | InterruptedException e) {
      System.out.println("Failed with: " + e.getMessage());
    }
  }

  void runSomeTasks() throws ExecutionException, InterruptedException {
    try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
      Supplier<String> foo = scope.fork(this::getFoo);
      Supplier<String> bar = scope.fork(this::getBar);
      Supplier<String> fail = scope.fork(this::fail);

      scope.join()
          .throwIfFailed();

      System.out.println("ALL TASKS FINISHED WITH: " + foo.get() + bar.get());
    }
  }

  String getFoo() throws InterruptedException {
    int delay = new Random().nextInt(1000) + 2000;
    System.out.println("getFoo delaying for: " + delay);
    Thread.sleep(delay);
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
