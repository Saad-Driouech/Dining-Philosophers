
/* ***************************************************
CSC3351         ASSIGNMENT2: Deadlocks
Group members:  
LAMIRI Fatima Zahrae
DRIOUECH Saad
IRAOUI Imane
ZAIZ Hajar 
******************************************************
*/
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ThreadLocalRandom;

class Main {
  
  public static int choice; //1 or 2 enetered by the user

  public static void main(String[] args)throws InterruptedException  {
    // create a table that works with prevenetion 
    final PrevTable table = new PrevTable(); 
    // create a table that works with detection
    final AvTable AvTable = new AvTable();
    int thinkingTime;
    int eatingTime;
    int choice;
    Random rand = new Random();
    Philosopher[] philosophs = new Philosopher[5];
    Scanner scanner = new Scanner(System.in);
    // menu to choose between detection or prevenetion 
    System.out.printf("\n\n****************************************\n");
    System.out.printf("Pick one of the scenarios:\n");
    System.out.printf("1=> Deadlocks with prevention\n");
    System.out.printf("2=> Deadlocks with Detection \n");
    System.out.printf("****************************************\n\n");
    System.out.printf("Enter choice (1 or 2 :)");
    choice = scanner.nextInt();
    //  passing table with prevention to philosophers in case choice 1 was selected
if (choice==1){
    for (int i = 0; i < 5; i++) {
       thinkingTime=rand.nextInt(10);
       eatingTime=rand.nextInt(10);
       philosophs[i] = new Philosopher(i,thinkingTime,eatingTime,table);
    }}
else{ //  passing table with detection to philosophers in case choice 2 was selected
    for (int i = 0; i < 5; i++) {
      thinkingTime=rand.nextInt(5);
      eatingTime=rand.nextInt(5);
      philosophs[i] = new Philosopher(i,thinkingTime,eatingTime,AvTable);
   }
}
// starting the five philosophers threads
    for ( Philosopher newphilosoph: philosophs) {
      if ( newphilosoph != null) {
        newphilosoph.start();
      }
    }

      for ( Philosopher newphilosoph: philosophs) {
        if ( newphilosoph != null && newphilosoph.isAlive()) {
          newphilosoph.join();
        }
      } 
  }
}