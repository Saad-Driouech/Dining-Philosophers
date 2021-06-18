import java.util.concurrent.TimeUnit;
import java.util.concurrent.Semaphore;
import java.util.Random;
class Philosopher extends Thread{

  
  int philosopherId;
  int thinkingTime;
  Table table;
  int eatingTime;

  public Philosopher(int philosopherId,int thinkingTime, int eatingTime, Table table){
    this.philosopherId = philosopherId;
    this.thinkingTime = thinkingTime;
    this.eatingTime = eatingTime;
    this.table=table;
  }

  @Override
  public void run() {
    //this function represents the cycle of eating and thinking
      try {
        System.out.printf("Philosopher with id  %d joined the table and is thinking \n",philosopherId ); 
        //each philosopher starts with thinking, then tries to acquire forks, then eats, and then release forks, then repeats the process
        while(true){            
          this.think();                
          table.acquireForks(philosopherId);      
          this.eat( );                  
          table.releaseForks(philosopherId);            
        }
      }catch(Exception e){
        e.printStackTrace();
      }
    }
    

  public void think(){
    //think is implemented as a delay
    try{
      TimeUnit.SECONDS.sleep(thinkingTime);}
    catch(Exception e) {
      e.printStackTrace();
    }
  }
  public void eat(){
    //eat is implemented as a delay
    try{
      TimeUnit.SECONDS.sleep(eatingTime);
    }catch(Exception e){
      e.printStackTrace();
    }
  }

}
