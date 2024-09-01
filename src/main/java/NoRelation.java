import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class NoRelation {
  public static void main(String[] args) {
    System.out.println("In unstructured concurrency, tasks and thread are not related to each other.");

    try {
      runSomeTasks();
    } catch (Exception e) {
      System.out.println("Failed with: " + e.getMessage());
    }
  }

  static void runSomeTasks() throws ExecutionException, InterruptedException {
    System.out.println("One thread can create an executor...");
    try (ExecutorService executor = Executors.newFixedThreadPool(3)) {
      var someTaskFuture = executor.submit(() -> submitToTheExecutor(executor));
      var someTask = someTaskFuture.get();
      executor.submit(() -> readResults(someTask));
    }

    System.out.println("Running some tasks...");
  }

  static Future<String> submitToTheExecutor(ExecutorService executor) {
    System.out.println("Another thread can submit work to the executor...");
    Future<String> someTask = executor.submit(() -> "ABC");
    return someTask;
  }

  static void readResults(Future<String> someTask) {
    System.out.println("And a completely different thread can join (await) results...");
    try {
      System.out.println("someTask result: " + someTask.get());
    } catch (InterruptedException | ExecutionException e) {
      e.printStackTrace();
    }
  }
}
