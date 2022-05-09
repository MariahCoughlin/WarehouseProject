/*
CLIENT MENU STATE
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
import java.util.*;


public class ClientMenuState{

    private static Warehouse warehouse;
    private static ClerkMenuState clerkMenuState;
    private static int clientID;
    private static WaitListProduct waitlistProduct;
    public static  int productId=0;
    public static int itemQuantity =0, newQuantity=0;
    
    
//-------getProductID----------------------
// Prompts user for product Id.
//------------------------------------------
    public static int getProductId(){
        System.out.println("Enter Product ID: ");
        Scanner s = new Scanner(System.in);
        return s.nextInt();
    } //end getProductID
    
    
//--------displayProductList----------------
// Displays list of products in warehouse
//------------------------------------------
    public static void displayProductList() {
            Iterator iterator = warehouse.getProducts();
            while(iterator.hasNext())
                System.out.println(iterator.next().toString());
    } //end displayProductList
    
//-------displayClientTransactions-------------------
    private static void displayClientTransactions(){
       
        Iterator invoiceIt;
        boolean choice;
        if(!warehouse.verifyClient(clientID)){
            System.out.println("ERROR");
            return;
        } //endif
        System.out.print("Display Detailed Transactions? Y/N ");
        choice = new Scanner(System.in).next().equals("Y");
        invoiceIt = warehouse.getInvoice(clientID) ;
        if(choice)
            while (invoiceIt.hasNext())
                System.out.println(((invoiceIt.next())).toString());
        else
            while (invoiceIt.hasNext())
                System.out.println(((invoiceIt.next())).toString());
                
        } //end displayInvoices
    
//-------ShowWaitList------------
// Product waitlist NOT CLIENT
// INCOMPLETE
//                 INCOMPLETE
// INCOMPLETE
//
//
//    INCOMPLETE
//
//-------------------------
    private static void showWaitList() {
        Iterator iterator;
        Product product = new Product();
        Order order;
        Client client;
        WaitListProduct currItem;
        int productId = 1;
        while (productId <5){

        iterator = warehouse.addItem(productId);
        if(iterator.hasNext()){
            currItem = (WaitListProduct)iterator.next();
            System.out.println(currItem);
           
        }
         productId++;
        }
    }
    
    
   /* private static void showWaitList() {
        try {
            int productID = getProductId();
            Iterator it;
            if(!warehouse.verifyProduct(productID)) {
                System.out.println("ERROR");
                return;
            } //end if
            
            it = warehouse.getWaitList(productID);
            System.out.println("Product: \n" + warehouse.findProduct(productID).toString());
            if(!it.hasNext())
                System.out.println("Product has no wait list currently");
            else {
                System.out.println("Wait list: \n" + "------------------");
                while(it.hasNext())
                    System.out.println(((WaitListProduct)it.next()).toString());
            } //end else
        }//end try
        catch(Exception e) { System.out.println("ERROR " + e ); }
    }//end showWaitList*/
    

//------EditShoppingCart------------
    private static void Shoppingcart(int cId) {
        Iterator it = warehouse.getCart(cId);
        while(it.hasNext())
            System.out.println(it.next().toString());
    }
    
 //------------------------------------------

    
    
    
    
    
//--------------displayCartOPTIONS-------------------------
// This is the shopping cart state.
//-----------------------------------------------------------
    private static void displayShoppingCart() {
        Scanner sc = new Scanner(System.in);
        ArrayList<ItemList> cart = new ArrayList <ItemList>();
        Product product;
        ItemList item;
       
        String itemName;
        double itemPrice;
        
        int ch=0;
        ProductList shoppingCart = new ProductList();
        
        JFrame shoppingCartFrame = new JFrame("Shopping Cart Menu");
        JPanel shoppingCartPanel = new JPanel();
        shoppingCartPanel.setOpaque(true);
        shoppingCartPanel.setBackground(new Color(152, 183, 214));
        Cursor cur = new Cursor(Cursor.HAND_CURSOR);

        shoppingCartPanel.setLayout(new BoxLayout(shoppingCartPanel, BoxLayout.PAGE_AXIS));
        shoppingCartFrame.add(shoppingCartPanel);
        shoppingCartFrame.setSize(300,300);
        shoppingCartFrame.setLocationRelativeTo(null);
        shoppingCartFrame.setVisible(true);
        
        //------View Cart BUTTON------------------------
        JButton viewCart  = new JButton();
        viewCart.setText("   View Shopping Cart   ");
        viewCart.setCursor(cur);
        viewCart.setForeground(new Color(89, 128, 168));
        viewCart.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        viewCart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                Shoppingcart(clientID);
            }
        });
        shoppingCartPanel.add(viewCart);
        
        //------Add Item BUTTON------------------------
        JButton addItem  = new JButton();
        addItem.setText("      Add Item to Cart     ");
        addItem.setCursor(cur);
        addItem.setForeground(new Color(89, 128, 168));
        addItem.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        addItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                System.out.println("Enter Item ID:");
                productId = sc.nextInt();
                System.out.println("Enter Quantity:");
                itemQuantity = sc.nextInt();
                warehouse.addToCart(clientID, productId, itemQuantity);
            }
        });
        shoppingCartPanel.add(addItem);
        
        //------Remove Item from cart BUTTON----------------
        JButton removeItem  = new JButton();
        removeItem.setText("Remove Item from Cart");
        removeItem.setCursor(cur);
        removeItem.setForeground(new Color(89, 128, 168));
        removeItem.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        removeItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                Shoppingcart(clientID);
                System.out.println("\n\nEnter ID to remove Item:");
                productId = sc.nextInt();
                warehouse.modifyCart(clientID, productId, 0);
            }
        });
        shoppingCartPanel.add(removeItem);
        
        //---------Change Quantity BUTTON----------------
        JButton quantity  = new JButton();
        quantity.setText("      Change Quantity    ");
        quantity.setCursor(cur);
        quantity.setForeground(new Color(89, 128, 168));
        quantity.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        quantity.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                System.out.println("Enter Item ID:");
                productId = sc.nextInt();
                System.out.println("Enter New Total Quantity:");
                newQuantity = sc.nextInt();
                warehouse.modifyCart(clientID, productId, newQuantity);
            }
        });
        shoppingCartPanel.add(quantity);

        //---------Exit BUTTON----------------
        JButton exitCart  = new JButton();
        exitCart.setText("    Exit Shopping Cart   ");
        exitCart.setCursor(cur);
        exitCart.setForeground(new Color(89, 128, 168));
        exitCart.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        exitCart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                System.out.println("Exiting Shopping Cart\n");
                shoppingCartFrame.dispose();
            }
        });
        shoppingCartPanel.add(exitCart);
        
    }
    

    
    

//------------ProcessInput---------------------------------------
    public static void processInput(Warehouse w){
        

        
            warehouse = w;
            Client client;
        
            //----CLIENT FRAME------------------
            JFrame clientFrame = new JFrame ("Client Menu");
            JPanel clientPanel = new JPanel();
            clientPanel.setOpaque(true);
            clientPanel.setBackground(new Color(196, 209, 190));
            Cursor cur = new Cursor(Cursor.HAND_CURSOR);
            
            //----OPEN CLIENT MENU---------
        
            int clientId = clerkMenuState.getClientId();
            clientID = clientId;
            String name;
            clientPanel.setLayout(new BoxLayout(clientPanel, BoxLayout.PAGE_AXIS));
            clientFrame.add(clientPanel);
            clientFrame.setSize(300,300);
            clientFrame.setLocationRelativeTo(null);
            clientFrame.setVisible(true);
            //------------------------------
        
        //--------Client Information BUTTON------------------
            JButton clientInfo = new JButton();
            clientInfo.setText("  Client Information  ");
            clientInfo.setCursor(cur);
            clientInfo.setForeground(new Color(100, 122, 91));
            clientInfo.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
            clientInfo.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e){
                    Client c;
                    Iterator iterator = warehouse.getClients();
                    while(iterator.hasNext()){
                        c = (Client)iterator.next();
                        if (c.getId() == clientId)
                            System.out.println(c.toString());
                    }//endwhile
                }
            });
            clientPanel.add(clientInfo);
        
        //------Display Products BUTTON------------------------
            JButton displayProducts = new JButton();
            displayProducts.setText("    List of Products    ");
            displayProducts.setCursor(cur);
            displayProducts.setForeground(new Color(100, 122, 91));
            displayProducts.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
            displayProducts.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e){
                    displayProductList();
                }
            });
            clientPanel.add(displayProducts);
        
        //-----Display Shopping Cart BUTTON---------------------
        JButton shopCart = new JButton();
        shopCart.setText("Shopping Cart Menu");
        shopCart.setCursor(cur);
        shopCart.setForeground(new Color(100, 122, 91));
        shopCart.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        shopCart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                displayShoppingCart();
            }
        });
        clientPanel.add(shopCart);
        
        //--------Wait List BUTTON---------------------
        JButton waitList = new JButton();
        waitList.setText("      Client WaitList     ");
        waitList.setCursor(cur);
        waitList.setForeground(new Color(100, 122, 91));
        waitList.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        waitList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                System.out.println("Button Pressed");
                
            }
        });
        clientPanel.add(waitList);
        
        //-------place Order-------------------
        JButton placeOrder = new JButton();
        placeOrder.setText("        Place Order       ");
        placeOrder.setCursor(cur);
        placeOrder.setForeground(new Color(100, 122, 91));
        placeOrder.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        placeOrder.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                warehouse.placeOrder(clientID);
            }
        });
        clientPanel.add(placeOrder);
        
        //----Logout BUTTON---------------------
        JButton logout = new JButton();
        logout.setText("           Logout           ");
        logout.setCursor(cur);
        logout.setForeground(new Color(100, 122, 91));
        logout.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                System.out.println("Logging out. Please choose from Interactive Menu");
                clientFrame.dispose();
            }
        });
        clientPanel.add(logout);
        
        
        
          }//end processInput
    
    
    
} //end Client menu state



        
        
    

