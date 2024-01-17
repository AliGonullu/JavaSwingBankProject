package BankATM;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class BankManager extends User implements ILogin, IButtonHandler, IFile {
    public BankManager(String id, String name, String lastName, String password) {
        super(id, name, lastName, password);
    }
    
    public void welcomeUser(JFrame frame) {
    	JLabel welcomeLabel = new JLabel("Welcome Mngr. " + name + " " + lastName);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeLabel.setBounds(120, 50, 400, 30);
        frame.getContentPane().add(welcomeLabel);
    }
    
    
    public void showMsg(String messageText, String messageTitle) {  
    	JDialog dialog = new JDialog();
        dialog.setTitle(messageTitle);
        
        String formattedMessage = messageText.replaceAll("\\\n", "\n<br>");
        JLabel label = new JLabel("<html><div style='margin: " + 30 + "px;'>" + formattedMessage + "</div></html>");
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.TOP);
        label.setFont(new Font("Arial", Font.PLAIN, 23));
        dialog.add(label);

        
        dialog.getContentPane().setBackground(new Color(180, 65, 65));
        
        dialog.setBounds(850, 300, 500, 510);
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.setVisible(true);
    	//JOptionPane.showMessageDialog(null, messageText);
    }
    
    public JButton newButtonMng(JFrame frame, String buttonMsg, int x, int y, int z, int t, Font _font) {
        JButton button = new JButton(buttonMsg);
        button.setBounds(x, y, z, t);
        button.setFont(_font);        
        return button;
    }
    
    public void login() {
        JFrame managerFrame = new JFrame("Manager Panel");
        managerFrame.setBounds(BankaATM.frame.getBounds());
        managerFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        managerFrame.getContentPane().setLayout(null);
        managerFrame.getContentPane().setBackground(new Color(170, 65, 65));
        managerFrame.setResizable(false);
        
        welcomeUser(managerFrame);
        
        JButton checkAllSalaryButton = newButtonMng(managerFrame, "Check All Personals Salary", 100, 200, 300, 40, new Font("Arial", Font.PLAIN, 19));
        managerFrame.getContentPane().add(checkAllSalaryButton);

        JButton changePersonalSalaryButton = newButtonMng(managerFrame, "Change Personal's Salary", 100, 270, 300, 40, new Font("Arial", Font.PLAIN, 19));
        managerFrame.getContentPane().add(changePersonalSalaryButton);


        checkAllSalaryButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	checkAllSalaries(personalList);
            }
        });

        changePersonalSalaryButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	String personalId = JOptionPane.showInputDialog("Enter Personal ID:");
                Personal personal = findPersonal(personalId);
                if (personal != null) {
                    double newSalary = Double.parseDouble(JOptionPane.showInputDialog("Enter New Salary:"));
                    setPersonalSalary(personal, newSalary);
                    saveUserData();
                } else {
                	showMsg("Personal With ID " + personalId + "\nNot Found!", "Personal Salary");
                }
            }
        });
                    
        managerFrame.setVisible(true);
    }
    

    public void checkSalary(Personal personal) {
    	showMsg("Salary of " + personal.name + " " + personal.lastName + " : " + personal.getSalary(), "Check Salary");
    }
    
    public void setPersonalSalary(Personal personal, double newSalary) {
        personal.setSalary(newSalary);
        showMsg("Salary of " + personal.name + " " + personal.lastName + "\nSet to : " + newSalary, "Change Personal Salary");
    }

    public void checkAllSalaries(ArrayList<Personal> personalList) {
    	StringBuilder message = new StringBuilder();        	
        for (Personal personal : personalList) {        	
            message.append(personal.getName()).append(" ").append(personal.getLastName()).append(": ").append(personal.getSalary()).append("\n\n");
        }
        showMsg(message.toString(), "Check All Salaries");
    }

    public void writeToFile(BufferedWriter writer) throws IOException {
        writer.write(id + "," + name + "," + lastName + "," + password);
        writer.newLine();
    }

    public static BankManager fromString(String line) {
        String[] parts = line.split(",");
        return new BankManager(parts[0], parts[1], parts[2], parts[3]);
    }
}

