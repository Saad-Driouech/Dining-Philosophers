import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.Random;
public class AvTable implements Table{
 
  private static Semaphore mutEx;
  private static Semaphore[] forks;
  private static int [][] status;

  public AvTable() {
    mutEx = new Semaphore(1, true);
    forks = new Semaphore [] {new Semaphore(1, true), new Semaphore(1, true), new Semaphore(1, true), new Semaphore(1, true), new Semaphore(1, true)};
     status = new int[][]{{0,0},{0,0},{0,0},{0,0},{0,0}};
    //status is  a table that keeps track of what forks each philosopher is holding, each row represents a philosoph, the first column refers to leftfork and the second to the right
    //for example, when philosipher 2 grabs a the left fork status[2][0] is set to  
    // when all philosophers are holding left forks deadlock is detected
  } 
 @Override
  public void acquireForks(int ID){
     int leftFork = ID;
    int rightFork= (ID + 1) % 5;
    try {
      // grab left fork 
      System.out.printf("--Philosopher id %d is hungry \n", ID);  
      //this function allows the philosopher top first try to pick up the left fork
      forks[leftFork].acquire(1);
      //status[ID][0] = 1 means that philosopher is using his left fork
      System.out.printf("--Philosopher %d grabs fork %d (left) \n", ID, leftFork); 
      status[ID][0] = 1;
      //then it checks for deadlock
      checkDeadlock();
      //after checking for deadlock, the philosopher tries to pick up the right fork
      forks[rightFork].acquire(1);
      //status[ID][1] = 1 means that philosopher is using his right fork
      status[ID][1] = 1;
      System.out.printf("--Philosopher %d grabs fork %d (right) \n", ID, rightFork);
      System.out.printf("--Philosopher %d is eating \n", ID); 
    } catch (Exception e) {
      e.printStackTrace();
    }               
  }
 @Override
  public void releaseForks(int ID){
    int leftFork = ID;
    int rightFork = (ID + 1) % 5;
    try{
      //philosopher releases the left fork
      forks[leftFork].release(1);
      //status[ID][0] = 0 means that philosopher is no longer using his left fork
      status[ID][0] = 0;
      //philosopher releases the right fork
      forks[rightFork].release(1);
      //status[ID][1] = 0 means that philosopher is no longer using his right fork
      status[ID][1] = 0;
      System.out.printf("--Philosopher %d is thinking \n", ID); 
  
    }catch(Exception e) {
      e.printStackTrace();
    }
  }

  public void checkDeadlock(){
    //deadlock will occur after each philosopher has picked his left fork
    try{
      int count=0, randomPhilo;
      Random rand = new Random();
      //this loop counts the number of philosophers that are holding their left fork
      for(int i=0; i<5; i++){
        if (status[i][0] == 1){
           count++;
        }
      }
      //if this counter is 5, then deadlock has happened. Pick up a random philosopher to release his fork so that it can be used by his left neighbour
      if(count==5){
      randomPhilo=rand.nextInt(5);
      System.out.printf("parent detected deadlock, philosipher %d was chosen randomly to release his left fork\n", randomPhilo);
      //philosopher releases the fork
      forks[randomPhilo].release(1);
      //status[ID][0] = 0 means that philosopher randomPhilo is no longer using his left fork
      status[randomPhilo][0] = 0;
      }
    
    }catch(Exception e) {
            e.printStackTrace();
            }
    }
}
