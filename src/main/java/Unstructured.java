import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Unstructured {
  public static void main() {
    System.out.println("Goodbye, Unstructured Concurrency!");

    try {
      runSomeTasks();
    } catch (ExecutionException | InterruptedException e) {
      System.out.println("Failed with: " + e.getMessage());
    }
  }

  static void runSomeTasks() throws ExecutionException, InterruptedException {
    try (ExecutorService executor = Executors.newFixedThreadPool(3)) {
      var tasks = new Tasks();
      Future<String> longTask = executor.submit(tasks::longTask);
      Future<String> shortTask = executor.submit(tasks::shortTask);

//      Future<String> subTask = executor.submit(Unstructured::runSubTask);

      String shortResult = shortTask.get();
      System.out.println("Retrieved shortTask result");
      String longResult = longTask.get();
      System.out.println("Retrieved longTask result");

      System.out.println("ALL TASKS FINISHED WITH: " + longResult + shortResult);
    }
  }

  static String runSubTask() throws ExecutionException, InterruptedException {
    try (ExecutorService executor = Executors.newFixedThreadPool(3)) {
      Future<Integer> subTask = executor.submit(() -> {
        Thread.sleep(5000);
        System.out.println("subTask finished");
        return 1;
      });
      throw new RuntimeException("actually, subTask failed");
    }
  }
}