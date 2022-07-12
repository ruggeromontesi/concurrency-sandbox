package ruggero.concurrent.simpletask;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SimpleTaskWithBarrier {

    private void aTask(int n) {
        System.out.println("Executing task  "  + "a" + n);
    }

    private void bTask(int n) {
        System.out.println("Executing task  "  + "b" + n);
    }

    private void cTask(int n) {
        System.out.println("Executing task  "  + "c" + n);
    }

    public void performAllSteps(int n, CyclicBarrier c1, CyclicBarrier c2) {

        try {
            aTask(n);
            c1.await();
            bTask(n);
            c2.await();
            cTask(n);
        } catch (InterruptedException | BrokenBarrierException ex) {

        }


    }

    public static void main(String[] args)  {
        ExecutorService service = null;

        SimpleTaskWithBarrier simpleTaskWithBarrier = new SimpleTaskWithBarrier();
        CyclicBarrier c1 = new CyclicBarrier(4);
        CyclicBarrier c2 = new CyclicBarrier(4, () -> System.out.println("Main Task is over"));

        try{
            service = Executors.newFixedThreadPool(4);
            for(int i = 0; i< 4; i++) {
                int finalI = i;
                service.submit(() -> simpleTaskWithBarrier.performAllSteps(finalI,c1,c2));
            }

        } finally {
            if(service != null) {
                service.shutdown();
            }
        }
    }
}
