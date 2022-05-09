/*
CLERK MENU STATE
*/


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import java.lang.*;

public class ClerkMenuState{

    private static Warehouse warehouse;
    private static ClerkMenuState ClerkMenuState;
    final static String FILENAME = "WarehouseData";
    final static double outstandingBalance = 100.00;


//--------getClientId-------------------------
// Prompts user for Client ID
//--------------------------------------------
    public static int getClientId(){
        System.out.print("Enter Client id: ");
        Scanner s = new Scanner(System.in);
        return s.nextInt();
    } //end getClientId()
    

//--------getProductId------------------------
// Prompts user for Product ID
//--------------------------------------------
    public static int getProductId() {
        System.out.print("Enter Product Id: ");
        Scanner s = new Scanner(System.in);
        return s.nextInt();
    } //end getProuctId
    
//-------openWarehouse--------------------------------
// Opens the Warehouse Data File
//     if no file exists one is created
//----------------------------------------------------
    public static void openWarehouse() {
        Warehouse w = Warehouse.retrieveData(FILENAME);
        if(w==null){
            System.out.println("Creating new warehouse");
            warehouse = Warehouse.instance();
        } else {
            System.out.println("Warehouse read from file");
            warehouse = w;
        } //end else
    } //end open warehouse
    
//----------saveChanges---------------------
// Saves data to the warehouse file
//------------------------------------------
    public static void saveChanges() {
        if(warehouse.saveData(FILENAME))
            System.out.println("Saved");
        else
            System.out.println("Failed");
    }//end saveChanges

//------------logout()-----------------------
// Prompts the system to save
// ----------------------------------------
    public static void logout() {
        saveChanges();
    }//endLogout
    
//-------instance()-----------------------------
    public static ClerkMenuState instance() {
        if(ClerkMenuState == null)
            return ClerkMenuState = new ClerkMenuState();
        else
            return ClerkMenuState;
    }//end Instance
    
//--------addClient------------------------
// Prompts user for name and address and
// generates ID. Creates client object.
// Adds client object to client list.
//----------------------------------------
    private static void addClient() {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter name for client:");
        String name = input.nextLine();
        System.out.print("Enter address for client:");
        String address = input.nextLine();
        warehouse.addClient(name, address);
        System.out.println("Client added successfully");
    }//addClient
    
//---------displayProducts---------------------------
// Displays a list of all the products in the
// warehouse system.
//---------------------------------------------------
    private static void displayAllProducts() {
        Iterator iterator = warehouse.getProducts();
        while(iterator.hasNext())
            System.out.println(iterator.next().toString());
    }//end DisplayProducts
    
//---------displayClients-------------------------
// Displays a list of all the clients in the
// warehouse system.
//------------------------------------------------
    private static void displayAllClients() {
        Iterator it = warehouse.getClients();
        while(it.hasNext())
            System.out.println(it.next().toString()) ;
    }//end displayClients
    
//-----------------------------------------
// INCOMPLETE
  /* private static void displayInvoices() {
       int clientId = getClientId();
       Iterator invoiceIt;
        boolean choice;
       
       if(!warehouse.verifyClient(clientId)){
           System.out.println("Error. Invalid client ID");
           return } //endif
       
        System.out.print("Display detailed transaction? Y/N ");
        choice = new Scanner(System.in).next().equals("Y");
       invoiceIt = warehouse.getInvoiceIt(clientId);
       
       
        if(choice)
            while(invoiceIt.hasNext())
                System.out.println(((Invoice)(invoiceIt.next())).itemListString());
        else
            while(invoiceIt.hasNext())
                System.out.println(((Invoice)(invoiceIt.next())).toString());
        System.out.println("Display invoice in clerk menu testing incomplete" );
        }//endwhile
    }*/
    
//-----------outstandingBalances()--------------
//
//-----------------------------------------------
    public static void displayOutstandingBalances(){
        Iterator it = warehouse.getClients();
        Client currClient = null;
        boolean found = false;
        while(it.hasNext()){
            currClient = (Client)it.next();
            if(currClient.getClientBalance() >= outstandingBalance){
                System.out.println(currClient.toString());
                found = true;
            }//end if
        }//end while()
        if(!found)
            System.out.println("No clients with an outstanding balance found");
        if(currClient == null)
            System.out.println("No Outstanding Balances currently");
    }//end displayOutstandingBalances

//----------makePayment------------------------
// User provides client ID and payment amount
// Payment gets made to clients account
//---------------------------------------------
    public static void makePayment(){
        //Get client id
        int clientId = getClientId();
        if(!warehouse.verifyClient(clientId)){
            System.out.println("Error, invalid client id.");
            return;
        }//end if
        
        System.out.print("Enter a payment amount: ");
        double amount = Double.valueOf(new Scanner(System.in).nextLine());
        if(amount <= 0.0){
            System.out.println("ERROR, payment must be a positive value. ");
            return;
        }//end if
        System.out.println("Please enter a description for this transaction (ie payment method)");
        String description = (new Scanner(System.in).nextLine());
        warehouse.makePayment(clientId, amount, description);
        System.out.println("Payment received successfully");
    }//end makePayment
    
//-------showWaitList------------------------------
// Displays waitlist for a specific product
//--------------------------------------------------
    private static void showWaitList() {
        int productID = getProductId();
        Iterator it;
        if(!warehouse.verifyProduct(productID)){
            System.out.println("ERORR-- cannot find product\n");
            return;
        } //endif
        it = warehouse.getWaitList(productID);
        System.out.println("Product: \n" + warehouse.findProduct(productID).toString());
        if(!it.hasNext())
            System.out.println("Product has no wait list currently");
        else {
            System.out.println("Wait list: \n" + "--------------");
            while(it.hasNext())
                System.out.println(((WaitListProduct)it.next()).toString());
        }
    }


//------callClient-----------------------
// Become a client but return back to
// Clerk menu after processing client
// menu.
//--------------------------------------
    private static void callClient() {
        ClientMenuState.processInput(warehouse);
        
    }
    
//------------ClientInformation()------------
// Client information menu state
//--------------------------------------------
    public static void clientInformation() {
        Scanner scanner = new Scanner(System.in);
        int ch = 0;
        while(ch !=4) {
            System.out.println("\n   Client Information Menu: " +
                               "\n---------------------------------------");
            System.out.println("\t1. Display All Clients\n" +
                               "\t2. Display Clients w Outstanding Balance\n" +
                               "\t3. Display Clients w no transactions in last 6 months\n" +
                               "\t4. Exit Client Information Menu\n");
            
            ch = scanner.nextInt();
            switch(ch) {
                case 1:
                    displayAllClients();
                    break;
                case 2:
                    displayOutstandingBalances();
                    break;
                case 3:
                    break;
                    
                case 4:
                    System.out.println("Exiting Client Information Menu\n");
                    
            
            }//endswitch
        }//endwhile
    }
    
//------processClerkMenuInput---------------------------
//
//-------------------------------------------------------
    public static void processInput(Warehouse w){

        warehouse = w;
        Client client;
        
        JFrame clerkFrame = new JFrame ("Clerk Menu");
        JPanel clerkPanel = new JPanel();
        Cursor cur = new Cursor(Cursor.HAND_CURSOR);
        
        clerkPanel.setOpaque(true);
        clerkPanel.setBackground(new Color(172, 161, 181));
        clerkPanel.setLayout(new BoxLayout(clerkPanel, BoxLayout.PAGE_AXIS));
        clerkFrame.add(clerkPanel);
        clerkFrame.setSize(300,300);
        clerkFrame.setLocationRelativeTo(null);
        clerkFrame.setVisible(true);
        
        //------Add Client BUTTON------------------------
        JButton addClient = new JButton();
        addClient.setText("    Add New Client   ");
        addClient.setCursor(cur);
        addClient.setForeground(new Color(108, 100, 132));
        addClient.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        addClient.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                addClient();
            }
        });
        clerkPanel.add(addClient);
       
        //------Display Products BUTTON------------------------
        JButton displayProducts = new JButton();
        displayProducts.setText("Display all Products");
        displayProducts.setCursor(cur);
        displayProducts.setForeground(new Color(108, 100, 132));
        displayProducts.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        displayProducts.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                displayAllProducts();
            }
        });
        clerkPanel.add(displayProducts);

        //------Client Info BUTTON------------------------
        JButton clientInfo = new JButton();
        clientInfo.setText("  Client Information ");
        clientInfo.setCursor(cur);
        clientInfo.setForeground(new Color(108, 100, 132));
        clientInfo.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        clientInfo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                clientInformation();
            }
        });
        clerkPanel.add(clientInfo);
        
        //------Make Payment BUTTON------------------------
        JButton payment = new JButton();
        payment.setText("     Make Payment    ");
        payment.setCursor(cur);
        payment.setForeground(new Color(108, 100, 132));
        payment.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        payment.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                makePayment();
            }
        });
        clerkPanel.add(payment);

        
        //------Become Client BUTTON------------------------
        JButton callClient = new JButton();
        callClient.setText("     Become Client     ");
        callClient.setCursor(cur);
        callClient.setForeground(new Color(108, 100, 132));
        callClient.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        callClient.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                callClient();
            }
        });
        clerkPanel.add(callClient);

        //------WaitList BUTTON------------------------
        JButton waitList = new JButton();
        waitList.setText("    Product Waitlist    ");
        waitList.setCursor(cur);
        waitList.setForeground(new Color(108, 100, 132));
        waitList.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        waitList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                showWaitList();
            }
        });
        clerkPanel.add(waitList);
        
        //----Logout BUTTON---------------------
        JButton logout = new JButton();
        logout.setText("   \t       Logout        \t  ");
        logout.setCursor(cur);
        logout.setForeground(new Color(108, 100, 132));
        logout.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                System.out.println("Logging out. Please choose from Interactive Menu");
                clerkFrame.dispose();
            }
        });
        clerkPanel.add(logout);
      
    }
}//end class




