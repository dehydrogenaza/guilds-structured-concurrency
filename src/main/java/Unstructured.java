import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Unstructured {
  public static void main(String[] args) {
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

//      Future<String> fail = executor.submit(tasks::fail);
//      String failResult = fail.get();

//      Future<String> subTask = executor.submit(Unstructured::runSubTask);

      System.out.println("ALL TASKS FINISHED WITH: " + longTask.get() + shortTask.get());
    }
  }

  static String runSubTask() throws ExecutionException, InterruptedException {
    try (ExecutorService executor = Executors.newFixedThreadPool(3)) {
      Future<Integer> subTask = executor.submit(() -> {
        Thread.sleep(5000);
        System.out.println("subTask finished");
        return 1;
      });
      throw new RuntimeException("subTask failed");
    }
  }
}