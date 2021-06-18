import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class PrevTable implements Table{
  public  final static int THINKING=0;
  public final static int HUNGRY=1;
  public final static int EATING=2;
  private static Semaphore mutEx;
  private static Semaphore[] philosophers;
  private static int [] status;

  public PrevTable() {
    //mutex ensures mutual exclusion
    mutEx = new Semaphore(1, true);
    //philosophers is used to decide when a philosopher can eat based on the status of his neighbours
    philosophers = new Semaphore [] {new Semaphore(1, true), new Semaphore(1, true), new Semaphore(1, true), new Semaphore(1, true), new Semaphore(1, true)};
    //status represents the status of philosopher
    status = new int[]{0,0,0,0,0};
  } 
 @Override
  public void acquireForks(int ID){
    //this function first changes the status of philosopher to hungry, checks if the philosopher can eat and then tries to block the philospher if checkphilosopher did not allow it to eat
    try {
      mutEx.acquire(1);
      status[ID] = HUNGRY; 
      System.out.printf("Philosopher %d is hungry. \n", ID);
      checkPhilosophers(ID);                    
      mutEx.release(1);
      philosophers[ID].acquire(1);  
    } catch (Exception e) {
      e.printStackTrace();
    }               
  }
 @Override
  public void releaseForks(int ID){
    //this functionstarts by computing the IDs of the neighbours of the philosopher
    int leftPhi = (ID + 4) % 5;
    int rightPhi = (ID + 1) % 5;
    try {
    mutEx.acquire(1);
    //after requiring mutex, it changes the status of the philosopher to thinking
    status[ID] = THINKING;
    System.out.printf("Philosopher %d is thinking. \n", ID); 
    //then it checks if the neighbours of the philosopher can eat by calling checkPhilosophers on both the neighbours
    checkPhilosophers(leftPhi);
    checkPhilosophers(rightPhi);
    mutEx.release(1);
    }catch(Exception e) {
      e.printStackTrace();
    }
  }

  public void checkPhilosophers(int ID){
    //this function makes sure that the status of the philosopher is hungry and that the status of its neighbours is different from eating, which means that they are not using the forks that the philosopher needs to eat
    int leftPhi = (ID + 4) % 5;
    int rightPhi = (ID + 1) % 5;
    try{
    // if at least one of the philosophers neighbours is eating, the philosopher ID cannot eat because he is missing at least one fork, so he won't grab any fork
    if(status[ID] == HUNGRY && status[leftPhi] != EATING && status[rightPhi] != EATING) {
      //if the philosopher is allowed to eat change its status to eating
      status[ID] = EATING;
      System.out.printf("Philosophe %d grabs left and right and  starts eating. \n", ID); 
      //the release is used to ensure that the philosopher will not be blocked in the acquireForks function (where checkPhilosophers is called)
      philosophers[ID].release(1);
    }
    }catch(Exception e) {
            e.printStackTrace();
            }
    }
}
