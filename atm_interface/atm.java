import java.util.*;
public class atm{
    static HashMap<String,user> h=new HashMap<>();
    static int minimum_balance=1000;
    static class user{
        int bal;
        String userdid;
        String pass;
        String history;
        user(int bal,String userid,String pass){
            this.bal=bal;
            this.userdid=userid;
            this.pass=pass;
            history="        TRANSACTION HISTORY\n";
        }
    }
    public static user authenticate(String t1){
        Scanner sc=new Scanner(System.in);
        int ch=3;
        String t2;
        while(ch-->0){
            System.out.print("\nEnter the password:");
            t2=sc.nextLine();
            
            user u=h.get(t1);
            if(u.pass.equals(t2)){
                return u;
            }
            else{
                System.out.println("\t\t\t\tWrong Password left over attempts are: "+ch);    
            }
        }
        System.out.println("\t\t\t\t\tOut of attempts");
        return null;
    }
    public static void Mainframe(){
        System.out.println("\t\t\t\tWELCOME TO THE ATM\n\n");
        Scanner sc=new Scanner(System.in);
        System.out.println("Please enter your option among the following\n\t1.LOGIN\n\t2.Create Account\n\t3.Exit");
        System.out.print("Your Option :- ");
        int opt=sc.nextInt();
        if(opt==1){
            String t1,t2;
        System.out.print("Enter valid user id :- ");
        t1=sc.nextLine();
        t1=sc.nextLine();
        user u=null;
        if(h.containsKey(t1)){
            u=authenticate(t1);
        }
        else{
            System.out.println("\n\t\t\tThe user id doesn't exists");
            Mainframe();
            return;
        }
        if(u==null){
            Mainframe();
            return;
        }
        else{
            System.out.println("\n\t\t\t"+u.userdid+" Logged in Successfully\n");
            Operator(u);
        }    
        }
        else if(opt==2){
            createAccount();
        }
        else{
            System.out.println("\n\t\t\tThank You, and visit again\n\n");
            return;
        }
    }
    public static void Operator(user u){
        Scanner sc=new Scanner(System.in);
        while(true){
            System.out.println("Enter any one option among the following");
            System.out.println("\t1.Transaction history");
            System.out.println("\t2.Withdrawl");
            System.out.println("\t3.Deposit");
            System.out.println("\t4.Transfer");
            System.out.println("\t5.LogOut");
            int ch=0;
            System.out.print("Your Option :- ");
            try{
                
                ch=sc.nextInt();
            }
            catch (InputMismatchException e){
                System.out.println("\n\t\t\tPLEASE ENTER ONLY INTEGERS\n\n");
                System.out.println("1.Re-enter the Option\n2.Exit");
                int x=sc.nextInt();
                if(x==1){
                    Operator(u);
                }
                else{
                    break;
                }
            } 
            if (ch==1){
                tran_History(u);
            }
            else if(ch==2){
                Withdrawl(u);
            }
            else if(ch==3){
                deposit(u);
            }
            else if(ch==4){
                transfer(u);
            }
            else if(ch==5){
                break;
            }
            
            
        }
        Mainframe();
    }
    public static void deposit(user u){
        Scanner sc=new Scanner(System.in);
        System.out.print("Enter the Amount to be Deposited: ");
        try{
            int d=sc.nextInt();
        u.bal=u.bal+d;
        u.history=u.history+"Amount credited:- +"+d+"\n";
        System.out.println("\n\tAmount credited succesfully, Total available balance is :"+u.bal+"\n\n");

        h.put(u.userdid, u);
        }
        catch (InputMismatchException e){
            System.out.println("\n\n\t\t\tTHE AMOUNT MUST BE AN INTEGER\n\n");
            deposit(u);
        }
    }
    public static void Withdrawl(user u){
        int d;
        Scanner sc=new Scanner(System.in);
        System.out.print("\nEnter the amount to be withdrawn:- ");
        try{
            d=sc.nextInt();
        if(u.bal-d>=minimum_balance){
            u.bal=u.bal-d;
            u.history=u.history+"Amount debited by: -"+d+"\n";
            System.out.println("\n\tAmount withdrawn successfully, Total available balance is :"+u.bal+"\n");
            h.put(u.userdid, u);
        }
        else{
            System.out.println("\n\t\tInsufficient Funds. Maximum amount can be withdrawn is"+(u.bal-minimum_balance)+"\n");
        }
        }
        catch (InputMismatchException e){
            System.out.println("\t\tTHE AMOUNT MUST BE AN INTEGER");
            Withdrawl(u);
        }
        
    }
    public static void tran_History(user u){
        if(u.history.length()<=28){
            System.out.println("\n\t\t\tYou haven't made any transaction yet\n");
        }
        else{
            System.out.println(u.history);
        }
    }
    public static void transfer(user u){
        System.out.print("Enter the user id of the recepient:");
        Scanner sc=new Scanner(System.in);
        String id;
        try{
            id=sc.nextLine();
        if(h.containsKey(id)){
            user r=h.get(id);
            System.out.print("\nEnter the amount to be transfered");
            int am=sc.nextInt();
            if(u.bal-am>minimum_balance){
            u.bal=u.bal-am;
            System.out.println("\n\tAmount transfered successfully, Total available balance is :"+u.bal+"\n\n");
            
            r.bal=r.bal+am;
             u.history=u.history+"You to "+r.userdid+":- -"+am+"\n";
            h.put(u.userdid, u);
            r.history=r.history+""+u.userdid+" to you:- +"+am+"\n\n";
            h.put(r.userdid, r);
        }
        else{
            System.out.println("\t\t\tInsufficient funds, Total available balance is :"+u.bal+"\n\n");
        }
        }
        else{
            System.out.println("\n\t\t\t\tUser Id not found\n");
        }
        }
        catch(InputMismatchException e){
            System.out.println("\t\t\t\t\t\tThe Amount must be a Number \n");
            transfer(u);
            return;
        }


    }
    public static void createAccount(){
        Scanner sc=new Scanner(System.in);
        String t1,t2;
        int b;
        System.out.print("Enter the unique USER Id: ");
        t1=sc.nextLine();
        if(!h.containsKey(t1)){
            t2=passwordchecker();
            System.out.println("Minimum balance must be greater than or equals to :"+minimum_balance+" so please enter intial deposit");
            while(true){
                b=sc.nextInt();
                if(b>=minimum_balance){
                    user u=new user(b,t1,t2);
                    h.put(t1, u);
                    System.out.println("\n\tCongratulations! Account created successfully, HAPPY BANKING\n");
                    Mainframe();
                    return;
                }
                else{
                    System.out.print("Amount must be greater than or equal to "+minimum_balance+" \n\t\tEnter the minimum balance:");
                }
            }
               
        }
        else{
            System.out.println("Account id aldready existed Re-enter the user id");
            createAccount();
            return;
        }
    }
    public static String passwordchecker(){
                Scanner sc=new Scanner(System.in);
            String t2,t3;
            System.out.print("\nEnter the password: ");
            t2=sc.nextLine();
            System.out.print("\nRe-enter the password: ");
            t3=sc.nextLine();
            if(t2.equals(t3)){
                return t2;
            }
            else{
                System.out.println("Password Mismatched, Please enter the password again");
                passwordchecker();
            }
            return null;
    }
    public static void main(String[] args) {
        user a=new user(12500,"bharath","1234");
        user b=new user(11200,"suresh","1235");
        user c=new user(15200,"umesh","1256");
        user d=new user(11350,"tanwish","1230");
        user e=new user(12400,"sathwik","1455");
        h.put("bharath", a);
        h.put("suresh",b);
        h.put("umesh",c);
        h.put("tanwish",d);
        h.put("sathwik",e);
        Mainframe();
    }
}