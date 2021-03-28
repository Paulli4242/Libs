import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Test {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = CompletableFuture.completedFuture("test");
        future.thenAccept(s-> System.out.println(s));
    }
}
