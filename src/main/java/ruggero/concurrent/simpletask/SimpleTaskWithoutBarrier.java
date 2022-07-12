package ruggero.concurrent.simpletask;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SimpleTaskWithoutBarrier {

    private void aTask(int n) {
        System.out.println("Executing task  "  + "a" + n);
    }

    private void bTask(int n) {
        System.out.println("Executing task  "  + "b" + n);
    }

    private void cTask(int n) {
        System.out.println("Executing task  "  + "c" + n);
    }

    public void performAllSteps(int n) {
        aTask(n);
        bTask(n);
        cTask(n);

    }

    public static void main(String[] args) {
        ExecutorService service = null;

        SimpleTaskWithoutBarrier simpleTaskWithoutBarrier = new SimpleTaskWithoutBarrier();

        try{
            service = Executors.newFixedThreadPool(4);
            for(int i = 0; i< 4; i++) {
                int finalI = i;
                service.submit(() -> simpleTaskWithoutBarrier.performAllSteps(finalI));
            }
        } finally {
            if(service != null) {
                service.shutdown();
            }
        }
    }
}
