package ruggero.concurrent.cyclicbarrier;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TaskWithoutCyclicBarrier {

   private void aStep(int n ) {
      System.out.println("a" + n );
   }

   private void bStep(int n ) {
      System.out.println("b" + n);
   }

   private void cStep(int n ) {
      System.out.println("c" + n);
   }

   public void performTask(int n){
      aStep(n);
      bStep(n);
      cStep(n);

   }

   public static void main(String[] args) {
      TaskWithoutCyclicBarrier task = new TaskWithoutCyclicBarrier();
      ExecutorService service = null;
      try {
         service = Executors.newFixedThreadPool(4);
         for(int i = 0; i < 4 ; i++) {
            int finalI = i;
            service.submit(() -> task.performTask(finalI) );
         }
      } finally {
         service.shutdown();
      }
   }
}
