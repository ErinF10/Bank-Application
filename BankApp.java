import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class BankApp {
  /**
   * Creates an array of account numbers to read from an input file.
   */
  public static void main(String[] args) throws FileNotFoundException {
    final int MAX_ACCOUNTS = 10;
    String[] accountNumbers = new String[MAX_ACCOUNTS];
    int[] balances = new int[MAX_ACCOUNTS]; 
    Scanner sc = new Scanner(System.in);
    
    System.out.print("Name of input file: ");
    String filename = sc.next();
    Scanner fileScanner = new Scanner(new File(filename));

    int numAccounts = readAccounts(accountNumbers, balances, fileScanner);

    /*
    - The physical length of each array is 
      MAX_ACCOUNTS = accountNumbers.length = balances.length = 10. 
      In this program, the physical length never changes. 
    - The logical length of each array is numAccounts; 
      this can change over the course of this program. 
    - The logical length tells us which elements of the array 
      currently store meaningful information:
      the first numAccounts elements, that is, the elements in 
      indexes 0 through numAccounts - 1. 
      For example, if numAccounts is 3, then we are interested in 
      the elements at indexes 0, 1, and 2. 
    */

    char choice;
    do {
      printMenu();
      choice = sc.next().charAt(0);
      // Precedence: "sc.next().charAt(0)" is performed first, 
      // and then "choice =" is performed.
      // Associativity within "sc.next().charAt(0)" is left-to-right.
      // So Java does the following, in this order:
      // - reads in a String
      // - gets the 0th character of the String
      // - assigns that character to the "choice" variable
      
      switch (choice) {
        case 'b':
          getBalance(accountNumbers, balances, numAccounts, sc);
          break;
        case 'd':
          makeDeposit(accountNumbers, balances, numAccounts, sc);
          break;
        case 'w':
          makeWithdrawal(accountNumbers, balances, numAccounts, sc);
          break;
        case 'a':
          boolean added = addAccount(accountNumbers, balances, numAccounts, MAX_ACCOUNTS, sc);
          if (added) {
            numAccounts++;
          }
          break;
        case 'p':
          printAccounts(accountNumbers, balances, numAccounts);
          break;
        case 'q':
          break;
        default:
          System.out.println("Invalid choice; try again.");
      }
    } while (choice != 'q');

    System.out.print("Name of output file: ");
    filename = sc.next();    
    //File filepath = new File("/Users/erinforrest/documents/" + filename);
    printAccounts(accountNumbers, balances, numAccounts, filename);
  }

  /**
   * Reads the account numbers and balances from the file 
   * and stores the data in the arrays. Returns the number
   * of accounts that have been read in.
   */
  public static int readAccounts(String[] accountNumbers, 
                                 int[] balances, 
                                 Scanner sc) {
    int numAccounts = 0;

    while (sc.hasNext()) {
      accountNumbers[numAccounts] = sc.next();
      balances[numAccounts] = sc.nextInt();
      numAccounts++;
    }

    return numAccounts;
  }

  /** Prints the menu options. */
  public static void printMenu() {
    System.out.println("\nChoices:");
    System.out.println("b: get balance");
    System.out.println("d: make deposit");
    System.out.println("w: make withdrawal");
    System.out.println("a: add account");
    System.out.println("p: print accounts to screen");
    System.out.println("q: quit");
    System.out.print("Enter a letter choice: ");
  }

  /**
   * Returns the index at which the information about a 
   * particular account can be found. The account is specified 
   * by the account number. If the account cannot be found, 
   * the method returns -1.
   */
  // This method is invoked by some of the other methods in this program.
  public static int indexOfAccount(String[] accountNumbers, 
                                   String accountNumber, 
                                   int numAccounts) {
    for (int i = 0; i < numAccounts; i++) {
      if (accountNumbers[i].equals(accountNumber)) {
        return i;
      } 
    }
    
    return -1;
  }

  /**
   * Asks the user for an account number. Prints an error 
   * message if the account doesn't exist. Otherwise, 
   * prints the account's balance.
   */
  public static void getBalance(String[] accountNumbers, 
                                int[] balances, 
                                int numAccounts, 
                                Scanner sc) {
    System.out.print("account number: ");
    String accountNumber = sc.next();

    int index = indexOfAccount(accountNumbers, accountNumber, numAccounts);

    if (index < 0) {
      System.out.println("Sorry, that account doesn't exist.");
    } else {
      System.out.printf("The balance is $%,d%n", balances[index]);
    }
  }

  /**
   * Asks the user for an account number. Prints an error 
   * message if the account doesn't exist. Otherwise, asks 
   * the user for an amount of money to deposit, and deposits
   * the money by updating the appropriate balance.
   */
  public static void makeDeposit(String[] accountNumbers, 
                                 int[] balances, 
                                 int numAccounts, Scanner sc) {
    System.out.print("account number: ");
    String accountNumber = sc.next();

    int index = indexOfAccount(accountNumbers, accountNumber, numAccounts);

    if (index < 0) {
      System.out.println("Sorry, that account doesn't exist.");
    } else {
      System.out.print("amount to deposit: ");
      balances[index] += sc.nextInt();
      System.out.println("Deposit successful.");
    }
  }

  /**
   * Asks the user for an account number. Prints an error 
   * message if the account doesn't exist. Otherwise, asks the 
   * user for an amount of money to withdraw, and withdraws
   * the money by updating the appropriate balance.
   */
  public static void makeWithdrawal(String[] accountNumbers, 
                                    int[] balances, 
                                    int numAccounts, Scanner sc) {
    System.out.print("account number: ");
    String accountNumber = sc.next();

    int index = indexOfAccount(accountNumbers, accountNumber, numAccounts);

    if (index == -1) {
      System.out.println("Sorry, that account doesn't exist.");
    } else {
      System.out.print("amount to withdraw: ");
      balances[index] -= sc.nextInt();
      System.out.println("Withdrawal successful.");
    }
  }

  /**
   * If there is no more space in the arrays to add a new 
   * account, prints an error message and returns false. 
   * Otherwise, asks the user for a account number. If the 
   * account number already exists in the accountNumbers array, 
   * prints an error message and returns false. Otherwise, 
   * adds the new account to the arrays (with a balance of $0)
   * and returns true.
   */
  public static boolean addAccount(String[] accountNumbers, 
                                   int[] balances, 
                                   int numAccounts, int maxAccounts, Scanner sc) {
    if (numAccounts >= maxAccounts) {
      System.out.println("Sorry, the database is full.");
      return false;
    }

    System.out.print("new account number: ");
    String accountNumber = sc.next();

    int index = indexOfAccount(accountNumbers, accountNumber, numAccounts);
    
    if (index >= 0) {
      System.out.println("Sorry, that account number already exists.");
      return false;
    }

    accountNumbers[numAccounts] = accountNumber;
    balances[numAccounts] = 0;
    System.out.println("Account added.");
    return true;
  }

  /** Prints all information about all accounts to the screen. */
  public static void printAccounts(String[] accountNumbers, 
                                   int[] balances, 
                                   int numAccounts) {
    for (int i = 0; i < numAccounts; i++) {
      // print account number and balance with formatting
      System.out.printf("%5s  $%,12d%n", accountNumbers[i], balances[i]);
    }
  }

  /** Prints all information about all accounts to the file. */
  public static void printAccounts(String[] accountNumbers, 
                                   int[] balances, 
                                   int numAccounts, 
                                   String filename) 
                                      throws FileNotFoundException {
    PrintWriter pw = new PrintWriter(filename);
    for (int i = 0; i < numAccounts; i++) {
      // print account number and balance (without dollar sign)
      pw.println(accountNumbers[i] + " " + balances[i]);
    }
    pw.close();
  }
}
