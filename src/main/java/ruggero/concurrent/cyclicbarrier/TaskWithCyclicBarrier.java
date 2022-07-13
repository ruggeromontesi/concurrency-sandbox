package ruggero.concurrent.cyclicbarrier;

import ruggero.concurrent.newsolution.Bank;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TaskWithCyclicBarrier {

   Bank bank = new Bank(10);

   private void aStep(int n ) {
      System.out.println("a" + n );
      System.out.println("Money count at the beginning : " + bank.getCurrentCumulativeAmount() + "Eur");
   }

   private void bStep(int n ) {

      System.out.println("b" + n);
      System.out.println("Money transfer");
      //bank.performRandomTransfer();
   }

   private void cStep(int n ) {

      System.out.println("c" + n);
      System.out.println("Money count at the end : " + bank.getCurrentCumulativeAmount() + "Eur");
   }

   public void performTask(int n, CyclicBarrier c1, CyclicBarrier c2){
      try {
         aStep(n);
         c1.await();
         bStep(n);
         c2.await();
         cStep(n);
      } catch(InterruptedException | BrokenBarrierException ex) {

      }


   }

   public static void main(String[] args) {
      TaskWithCyclicBarrier task = new TaskWithCyclicBarrier();
      CyclicBarrier c1 = new CyclicBarrier(4);
      CyclicBarrier c2 = new CyclicBarrier(4, () -> System.out.println("Task is over"));
      ExecutorService service = null;
      try {
         service = Executors.newFixedThreadPool(4);
         for(int i = 0; i < 4 ; i++) {
            int finalI = i;
            service.submit(() -> task.performTask(finalI, c1,c2) );
         }
      } finally {
         service.shutdown();
      }
   }
}
