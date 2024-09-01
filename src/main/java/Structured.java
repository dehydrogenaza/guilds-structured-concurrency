import java.util.concurrent.ExecutionException;
import java.util.concurrent.StructuredTaskScope;
import java.util.function.Supplier;

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
    Supplier<String> foo = scope.fork(tasks::getFoo);
    Supplier<String> bar = scope.fork(tasks::getBar);
//    Supplier<String> fail = scope.fork(tasks::fail);

    scope.join()
        .throwIfFailed();

    System.out.println("ALL TASKS FINISHED WITH: " + foo.get() + bar.get());
  }
}
