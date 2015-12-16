package hcsdteam12;
/**
 * Create a ComboBox to select a specific partner before displaying the appointments for the selected partner.
 * 
 * 
 * @author Seng Kin(Terence), Kong 
 * **/

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
 
public class PartnerSelectionWindow {
    
   private JFrame primaryWindow;
   private JLabel headerLabel, statusLabel;
   private JPanel controlPanel;

   public PartnerSelectionWindow(){
      loadGUI();
   }

   public static void main(String[] args){
	   PartnerSelectionWindow swingcomponent = new PartnerSelectionWindow();      
	   swingcomponent.showComboboxDemo();
   }

   private void loadGUI(){
	  primaryWindow = new JFrame("Partner Selection Window");
	  primaryWindow.setSize(500,500);
      primaryWindow.setLayout(new GridLayout(3, 1));
      primaryWindow.addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent windowEvent){
            System.exit(0);
         }        
      });    
      headerLabel = new JLabel("", JLabel.CENTER);        
      statusLabel = new JLabel("",JLabel.CENTER);    
      statusLabel.setSize(500,100);

      controlPanel = new JPanel();
      controlPanel.setLayout(new FlowLayout());

      primaryWindow.add(headerLabel);
      primaryWindow.add(controlPanel);
      primaryWindow.add(statusLabel);
      primaryWindow.setVisible(true);  
   }

   private void showComboboxDemo(){                                    
      final DefaultComboBoxModel<String> PartnersName = new DefaultComboBoxModel<String>();

      PartnersName.addElement("Dentist");
      PartnersName.addElement("Hygienist");

      final JComboBox<String> partnerCombo = new JComboBox<String>(PartnersName);    
      partnerCombo.setSelectedIndex(0);

      JScrollPane partnerListScrollPane = new JScrollPane(partnerCombo);    

      JButton displayButton = new JButton("Show");

      displayButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) { 
            String data = "";
            if (partnerCombo.getSelectedIndex() != -1) {                     
               data = "Partner Selected: "  + partnerCombo.getItemAt(partnerCombo.getSelectedIndex());             
            }              
            statusLabel.setText(data);
         }
      }); 
      controlPanel.add(partnerListScrollPane);          
      controlPanel.add(displayButton);    
      primaryWindow.setVisible(true);             
   }
}