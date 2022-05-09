/*
MANAGER MENU STATE
*/
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import java.lang.*;


public class ManagerMenuState{

    private static Warehouse warehouse;
    private static ManagerMenuState ManagerMenuState;

    public static void performMenu(Warehouse w) {
       
        warehouse = w;
       
        //-----MANAGER FRAME-----------------
        JFrame managerFrame = new JFrame ("Manager Menu");
        JPanel managerPanel = new JPanel();
        managerPanel.setOpaque(true);
        managerPanel.setBackground(new Color(143, 183, 172));
        Cursor cur = new Cursor(Cursor.HAND_CURSOR);
        
        //----OPEN MANAGER MENU-----------------------
        managerPanel.setLayout(new BoxLayout(managerPanel, BoxLayout.PAGE_AXIS));
        managerFrame.add(managerPanel);
        managerFrame.setSize(300,300);
        managerFrame.setLocationRelativeTo(null);
        managerFrame.setVisible(true);

        //-----Add Product BUTTON---------------
        JButton addProduct = new JButton();
        addProduct.setText("  Add Product ");
        addProduct.setCursor(cur);
        addProduct.setForeground(new Color(79, 124, 114));
        addProduct.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        addProduct.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                addProduct();
            }
        });
        managerPanel.add(addProduct);
        
        //--------Add Shipment BUTTON------------------
        JButton addShipment = new JButton();
        addShipment.setText("Add Shipment");
        addShipment.setCursor(cur);
        addShipment.setForeground(new Color(79, 124, 114));
        addShipment.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        addShipment.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                addShipment();
            }
        });
        managerPanel.add(addShipment);
        
        //-------Login as Clerk BUTTON------------------
        JButton loginAsClerk = new JButton();
        loginAsClerk.setText("Login as Clerk");
        loginAsClerk.setCursor(cur);
        loginAsClerk.setForeground(new Color(79, 124, 114));
        loginAsClerk.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        loginAsClerk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                loginAsClerk();
            }
        });
        managerPanel.add(loginAsClerk);
        
        //--------Add Shipment BUTTON------------------
        JButton logout = new JButton();
        logout.setText("      Logout      ");
        logout.setCursor(cur);
        logout.setForeground(new Color(79, 124, 114));
        logout.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        logout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                System.out.println("Logging Out. Please choose from Interactive Menu");
                managerFrame.dispose();
            }
        });
        managerPanel.add(logout);
    }
    
//---------addProduct--------------------
// Adding product to Warehouse
//---------------------------------------

    private static void addProduct() {
        boolean adding = true;
        Scanner input;
        while(adding) {
            input = new Scanner(System.in);
            System.out.print("Enter name: ");
            String name = input.nextLine();
            System.out.print("Enter price: ");
            String salePrice = input.nextLine();
            System.out.print("Enter stock: ");
            int stock = input.nextInt();
            warehouse.addProduct(name,Double.valueOf(salePrice), stock);
            System.out.println("Product added.");
            System.out.print("Add another product? Y/N : ");
            input = new Scanner(System.in);
            adding = (input.next().charAt(0) == 'Y');
        }
    }
    
//--------AddShipment--------------------
// Adds shipment to warehouse
//------------------------------------
    private static void addShipment(){
    int productId, quantity, quantCount;
    Scanner scanner;
    boolean moreProducts = true;
    Iterator waitList;
    WaitListProduct currentItem;
    String choice="";
    
        while(moreProducts){
            
            System.out.print("Enter product id (0 to quit): ");
            scanner = new Scanner(System.in);
            productId = scanner.nextInt();
            if(productId == 0) return;
            
            if(!warehouse.verifyProduct(productId)) {
                System.out.println("Error. Invalid product Id.");
                return;
            } //endif
            
            System.out.print("Enter quantity: ");
            scanner = new Scanner(System.in);
            quantity = scanner.nextInt();
            
            waitList = warehouse.addShippedItem(productId, quantity);
            quantCount = 0; //reset quant count for new product
            while(waitList.hasNext()) {
                currentItem = (WaitListProduct)waitList.next();
                if((currentItem.getQuantity() + quantCount) <= warehouse.getStock(productId)) {
                    System.out.println("\nOrder for product " +
                                       currentItem.getOrder().getId() +
                                       " can be fulfilled with new stock.\n"  +
                                       "\nCurrent Stock: " +
                                       warehouse.getStock(productId) +
                                       "\nOrder Quantity Needed: " +
                                       currentItem.getQuantity() +
                                       "\nFulfill? Y/N ");
                    
                    scanner = new Scanner(System.in);
                    choice = scanner.next();
                    choice.toUpperCase();
                    if(choice.equals("Y")) {
                        System.out.println("Fulfilling Order");
                        warehouse.finishWaitList(productId, currentItem);
                        quantCount += currentItem.getQuantity();
                    }//endif
                }//end if
            
            }//endwhile
                  warehouse.doneFulfilling();
        }//endwhile
    }
    
    //------------------------
    private static void loginAsClerk(){
        ClerkMenuState.processInput(warehouse);
    }
    

    public static ManagerMenuState instance() {
        if(ManagerMenuState == null)
            return ManagerMenuState = new ManagerMenuState();
        else
            return ManagerMenuState;
    }
}










