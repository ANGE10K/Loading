import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) {
        ExecutorService exServ = Executors.newFixedThreadPool(3, new ThreadFactory() {
            @Override
            public Thread newThread(Runnable runnable) {
                Thread thread = new Thread(runnable);
                thread.setDaemon(true);
                return thread;
            }
        });
        exServ.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        Thread.sleep(300);
                        System.out.print(".");
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        Future<String> futureName = exServ.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                Thread.sleep(6000);
                return "John";
            }
        });
        Future<Integer> futureAge = exServ.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                Thread.sleep(5000);
                return 20;
            }
        });
        try {
            String name = futureName.get();
            int age = futureAge.get();
            System.out.println("\nИмя: " + name + ", " + "Возраст: " + age);
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }

    }
}

