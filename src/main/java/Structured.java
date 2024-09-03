import java.util.concurrent.ExecutionException;
import java.util.concurrent.StructuredTaskScope;
import java.util.function.Supplier;

public class Structured {
  public static void main() {
    System.out.println("Hello, Structured Concurrency!");

    try {
      runSomeTasks();
    } catch (ExecutionException | InterruptedException e) {
      System.out.println("Failed with: " + e.getMessage());
    }
  }

  static void runSomeTasks() throws ExecutionException, InterruptedException {
    try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
      var tasks = new Tasks();
      Supplier<String> longTask = scope.fork(tasks::longTask);
      Supplier<String> shortTask = scope.fork(tasks::shortTask);

      Supplier<String> subTask = scope.fork(Structured::runSubTask);

      scope.join()
          .throwIfFailed();

      String shortResult = shortTask.get();
      System.out.println("Retrieved shortTask result");
      String longResult = longTask.get();
      System.out.println("Retrieved longTask result");

      System.out.println("ALL TASKS FINISHED WITH: " + longResult + shortResult);
    }
  }

  static String runSubTask() throws ExecutionException, InterruptedException {
    try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
      Supplier<Integer> subTask = scope.fork(() -> {
        Thread.sleep(5000);
        System.out.println("subTask finished");
        return 1;
      });
      throw new RuntimeException("actually, subTask failed");
//      return "abc";
    }
  }
}