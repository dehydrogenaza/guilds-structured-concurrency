import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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
    Future<String> foo = executor.submit(tasks::getFoo);
    Future<String> bar = executor.submit(tasks::getBar);

//    Future<String> fail = executor.submit(tasks::fail);
//    String failResult = fail.get();

    System.out.println("ALL TASKS FINISHED WITH: " + foo.get() + bar.get());
  }
}