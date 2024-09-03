import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public static void main() {
  System.out.println("In unstructured concurrency, tasks and thread are not related to each other.");
  try {
    runSomeTasks();
  } catch (Exception e) {
    System.out.println("Failed with: " + e.getMessage());
  }
}

static void runSomeTasks() throws ExecutionException, InterruptedException {
  System.out.println("One thread can create an executor... " + Thread.currentThread()
      .getName());
  try (ExecutorService executor = Executors.newFixedThreadPool(5)) {
    var someTaskFuture = executor.submit(() -> submitToTheExecutor(executor));
    executor.submit(() -> readResults(someTaskFuture));
    Thread.sleep(5000);
  }
}

static Future<String> submitToTheExecutor(ExecutorService executor) {
  System.out.println("Another thread can submit work to the executor... " + Thread.currentThread()
      .getName());
  Future<String> someTask = executor.submit(() -> {
    System.out.println("Yet another thread does the work... " + Thread.currentThread()
        .getName());
    return "RESULT";
  });
  return someTask;
}

static void readResults(Future<Future<String>> someTaskFuture) {
  System.out.println("And a completely different thread can join (await) results... " + Thread.currentThread()
      .getName());
  try {
    someTaskFuture.get();
    System.out.println("someTask result: " + someTaskFuture.get().get());
  } catch (InterruptedException | ExecutionException e) {
    e.printStackTrace();
  }
}
