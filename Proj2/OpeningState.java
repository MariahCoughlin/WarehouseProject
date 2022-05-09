/*
 OPENING STATE
 
 
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import java.lang.*;

public class OpeningState extends JFrame{
    

        final static String FILENAME = "WarehouseData";
    
//------------saveChanges---------------------
// Saves changes made to warehosue
//-------------------------------------------
    public static void saveChanges(String file, Warehouse warehouse) {
        if(warehouse.saveData(file))
            System.out.println("Saved Successfully");
        else
            System.out.println("Save Failed. ERROR");
    } //end saveChanges
    
//-----------openWarehouse-------------------
    private static Warehouse openWarehouse(String file){
        Warehouse w;
       
            w = Warehouse.retrieveData(file);
            if(w == null){
                System.out.println("Empty File. Creating New Warehouse");
                w = Warehouse.instance();
            } else
                System.out.println("Warehouse opened Successfully.");

                return w;

    }//end openWarehouse
  
//-----------------------------
    public static void createGui(JFrame mainFrame, JPanel panel) {
       
        mainFrame.add(panel);
        mainFrame.setSize(300,300);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);
        
    }
    
    
    
//-------------MAIN-----------------------------
    public static void main (String args[]){
       
        Warehouse warehouse = openWarehouse(FILENAME);
        JFrame mainFrame = new JFrame("Warehouse System");
        JPanel panel = new JPanel();
        panel.setOpaque(true);
        panel.setBackground(new Color(219, 193, 179));
        panel.setLayout(new BoxLayout(panel,BoxLayout.PAGE_AXIS));
        JLabel label = new JLabel("\nPlease select an Option: \n");
        label.setFont(new Font(Font.DIALOG, Font.BOLD, 10));
        label.setForeground(new Color(140, 100, 88));
        Cursor cur = new Cursor(Cursor.HAND_CURSOR);
        JButton clientButton = new JButton();
        JButton clerkButton = new JButton();
        JButton managerButton = new JButton();
        JButton quitButton = new JButton();
        
        clientButton.setText("  Client Menu   ");
        clerkButton.setText ("   Clerk Menu   ");
        managerButton.setText("Manager Menu");
        quitButton.setText("\t       Quit          ");
        
    
        clientButton.setForeground(new Color(113, 153, 108));
        clerkButton.setForeground(new Color(129, 106, 147));
        managerButton.setForeground(new Color(81, 137, 136));
        quitButton.setForeground(new Color(135, 100, 93));
        
        clientButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        clerkButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        managerButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        quitButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 16));
        
        clientButton.setCursor(cur);
        clerkButton.setCursor(cur);
        managerButton.setCursor(cur);
        quitButton.setCursor(cur);
        
        quitButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                saveChanges(FILENAME, warehouse);
                System.exit(0);
            }
        });//QUIT BUTTON
        
        clientButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                ClientMenuState.processInput(warehouse);
                
            }
        });//CLIENT MENU BUTTON
        
        managerButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                ManagerMenuState.performMenu(warehouse);
            }
        }); //MANAGER MENU BUTTON
        
        clerkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClerkMenuState.processInput(warehouse);
            }
        }); //CLERK MENU BUTTON
        
        
        
        panel.add(label);
        panel.add(clientButton);
        panel.add(clerkButton);
        panel.add(managerButton);
        panel.add(quitButton);
        
        
        createGui(mainFrame, panel);
        
        
            }//end main
    
    
    
    }//end Opening State
