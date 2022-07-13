package ruggero.concurrent;

import ruggero.concurrent.newsolution.Bank;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TransferTask {

    private Bank bank = new Bank(10);

    private void printCumulativeAmount(String amountAttribute) {
        System.out.println(amountAttribute + " cumulative amount is " + bank.getCurrentCumulativeAmount());
    }




    public void performTask(int n, CyclicBarrier c1,CyclicBarrier c2 ) {

        try {
            printCumulativeAmount("initial "  + n );
            c1.await();
            bank.performRandomTransfer(n);
            c2.await();
            printCumulativeAmount("final"  + n);

        } catch(InterruptedException | BrokenBarrierException ex) {
            System.out.println("Exception occurred!");

        }

    }


    public static void main(String[] args) {
        ExecutorService service = null;
        TransferTask transferTask = new TransferTask();
        CyclicBarrier c1 = new CyclicBarrier(4 );
        CyclicBarrier c2 = new CyclicBarrier(1,() -> System.out.println("Transfer Task is over") );

        try {
            service = Executors.newFixedThreadPool(4);
            for (int i = 0; i < 4; i++) {
                int finalI = i;
                service.submit(() -> transferTask.performTask(finalI,c1,c2));

            }

        } finally {
            if (service != null) {
                service.shutdown();
            }
        }
    }

}
