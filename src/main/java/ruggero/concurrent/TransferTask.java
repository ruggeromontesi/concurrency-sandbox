package ruggero.concurrent;

import ruggero.concurrent.domain.Bank;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

public class TransferTask {
    static Bank bank = new Bank(10);


    public static void main(String[] args) throws Exception {
        int totalInitialAmount = bank.getCurrentCumulativeAmount();
        CyclicBarrier cb = new CyclicBarrier(4);
        System.out.println("Total initial amount = " + totalInitialAmount );
        performRandomTransfers(cb);



    }

    public static void performRandomTransfers(CyclicBarrier cb) throws Exception {

        ExecutorService service1 = null;
        ExecutorService service2 = null;

        try {
            service1 = Executors.newFixedThreadPool(5);

            for (int i = 0; i < 5; i++) {
                service1.submit(bank::performRandomTransfer);

            }


            System.out.println("Total final amount = " + bank.getCurrentCumulativeAmount());


       } finally {
            service1.shutdown();
            service2.shutdown();
        }

    }

}
