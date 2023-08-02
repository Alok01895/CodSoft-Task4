
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;

public class Interface implements Serializable{

    static ArrayList<AccountDetails> accounts;

    static class AccountDetails implements Serializable
    {
        private int pin;
        private int amount;
        private String name;
        private int age;
        private String gender;
        private int accountID;
        public AccountDetails(int pin,int amount,String name,String gender,int age,int accountID)
        {
            this.pin=pin;
            this.amount=amount;
            this.name=name;
            this.age=age;
            this.gender=gender;
            this.accountID=accountID;
        }
    }


    static class Transactions implements Serializable
    {
        Scanner sc=new Scanner(System.in);

        public void details()
        {
            while(true)
            {
                System.out.print("Enter Your accoundID:- ");
                int accoundID=sc.nextInt();
                System.out.println();
                System.out.println("Enter PIN:- ");
                AccountDetails account=searchAccount(accoundID);
                int pin=sc.nextInt();

                if(account==null || account.pin!=pin)
                {
                    System.out.println("Invalid ID or PIN!. Please try again");
                }
                else{
                    operations(account);
                    break;
                }
            }
        }



        public void create()
        {
            System.out.println();

            System.out.println("Please fill up the below details to get your account created");

            System.out.print("Please enter your name: ");
            String name=sc.nextLine();

            System.out.println();
            System.out.print("Please enter your age: ");
            int age=sc.nextInt();

            System.out.println();
            System.out.print("Please enter your gender: ");
            String gender=sc.next();

            System.out.println();
            System.out.println("Do you have any preferred pin for your ATM?");
            System.out.println("Press 1 for yess and 0 for no");
            Random rand=new Random();
            int option=sc.nextInt();
            int pin=0;
            if(option==0)
            {
                
                pin = rand.nextInt(9000) + 1000;
            }
            else{
                
                while(true)
                {
                    System.out.println("Enter a 4 digit pin");
                    int digit=0;
                    int pinTaken=sc.nextInt();
                    pin=pinTaken;
                    while(pinTaken!=0)
                    {
                        pinTaken/=10;
                        digit++;
                    }
                    if(digit!=4)
                    {
                        System.out.println("Pin out of range.");
                        System.out.println("Try again!..");
                    }
                    else{
                        break;
                    }
                }
                
            }

            int accountID=0;
            while(true)
            {
                accountID= rand.nextInt(9000000) + 1000000;
                if(searchAccount(accountID)!=null) break;
            }
            
            AccountDetails account=new AccountDetails(pin,0,name,gender,age,accountID);

            accounts.add(account);
            saveAccounts();

            System.out.println("Congratulation! Your account has been created");
            System.out.println("Your AccountID:- " + accountID);
            System.out.println("Your AccountPIN:- " + pin);

            System.out.println("Press 0 for various operations and 1 to exit");
            int x=sc.nextInt();
            if(x==0)
            {
                operations(account);
            }else{
                System.out.println("Thanks for visiting!..");
                System.exit(1);
            }
        }

        public AccountDetails searchAccount(int value)
        {
            for(AccountDetails account: accounts)
            {
                if(account.accountID==value)
                {
                    return account;
                }
            }
            return null;
        }

        public void operations(AccountDetails account)
        {
            while(true)
            {
                
                System.out.println("Press 1 to Deposit");
                System.out.println("Press 2 to Withdraw");
                System.out.println("Press 3 to Check Balance");
                System.out.println("Press 4 to Exit");

                int option=sc.nextInt();
                if(option==1)
                {
                    System.out.println("How much you wanna deposit?");
                    System.out.print("Enter Amount:- ");
                    int amount=sc.nextInt();
                    System.out.println();
                    account.amount+=amount;
                    System.out.println("Your money has been succesfully deposited");
                }
                if(option==2)
                {
                    System.out.println("How much you wanna withdraw?");
                    System.out.print("Enter Amount:- ");
                    int amount=sc.nextInt();
                    System.out.println();
                    if(account.amount-amount<0) 
                    {
                        System.out.println("No sufficient balance to withdraw");
                        continue;
                    }
                    account.amount-=amount;
                    System.out.println("Your money has been succesfully withdrawn");
                }
                if(option==3)
                {
                    System.out.println("Your current balance:- " + account.amount);
                }
                if(option==4)
                {
                    System.out.println("Thanks for visiting!..");
                    break;
                }
                saveAccounts();
            }

        }

}



    public static void saveAccounts() {
    try {
        FileOutputStream fileOut = new FileOutputStream("accounts.dat");
        ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
        objectOut.writeObject(accounts);
        objectOut.close();
        fileOut.close();
    } catch (IOException e) {
        e.printStackTrace();
    }
    }

    @SuppressWarnings("unchecked")
    public static ArrayList<AccountDetails> loadAccounts() {
        ArrayList<AccountDetails> loadedAccounts = new ArrayList<>();
        try {
            FileInputStream fileIn = new FileInputStream("accounts.dat");
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            loadedAccounts = (ArrayList<AccountDetails>) objectIn.readObject();
            objectIn.close();
            fileIn.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return loadedAccounts;
    }





    public static void main(String[] args) {
        accounts = loadAccounts();
        System.out.println();
        System.out.println("HELLO THERE!!..");
        System.out.println();

        System.out.println("Press 1 if you already have an account");
        System.out.println("Press 2 if you want to create an account");

        Scanner sc=new Scanner(System.in);

        int option=sc.nextInt();

        Transactions trans=new Transactions();

        while(true)
        {
            if(option==1)
            {
                trans.details();
                break;
            }
            else if(option==2)
            {
                trans.create();
                break;
            }
            else 
            {
                System.out.println("Invalid Choice!. Please try again");
                option=sc.nextInt();
            }
        }
    }
}
