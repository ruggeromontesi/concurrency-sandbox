package ruggero.concurrent.newsolution;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MoneyTransferTask {

    private static int TRANSACTION_NUMBER = 4;
    Bank bank ;

    public MoneyTransferTask(Bank bank) {
        this.bank = bank;
    }

    private void preTask(){
        System.out.println("Money count at the beginning : " + bank.getCurrentCumulativeAmount() + "Eur");
    }

    private void task() {

        bank.performRandomTransfer();
    }

    private void postTask() {
        System.out.println("Money count at the end : " + bank.getCurrentCumulativeAmount() + "Eur");
    }

    public void performTask( CyclicBarrier c1,CyclicBarrier c2 ){

        try {
            preTask();
            c1.await();
            System.out.println("after wait1");
            bank.performRandomTransfer();
            System.out.println("out of perform random transfer");
            c2.await();
            System.out.println("after wait2");
            postTask();
        } catch(InterruptedException | BrokenBarrierException ex) {

        }


    }

    public static void main(String[] args) throws BrokenBarrierException, InterruptedException {
        MoneyTransferTask task = new MoneyTransferTask(new Bank(6));
        CyclicBarrier c1 = new CyclicBarrier(TRANSACTION_NUMBER);
        CyclicBarrier c2 = new CyclicBarrier(TRANSACTION_NUMBER);
        ExecutorService service = null;

        try {
            service = Executors.newFixedThreadPool(TRANSACTION_NUMBER);
            for(int i = 0; i < TRANSACTION_NUMBER; i++ ) {
                service.submit(() -> task.performTask(c1,c2));
            }
        } finally {
            if(service != null) {
                service.shutdown();
            }
        }




    }



}
