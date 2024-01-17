package BankATM;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;


public class Personal extends User implements ILogin, IButtonHandler, IFile {
	private double salary;

    public Personal(String id, String name, String lastName, String password, double salary) {
        super(id, name, lastName, password);
        this.salary = salary;
    }

    public Personal(String id, String name, String lastName, String password) {
        super(id, name, lastName, password);
        this.salary = 15000 + (int) Math.random() * 10000;  
    }
    
    
    public void welcomeUser(JFrame frame) {
    	JLabel welcomeLabel = new JLabel("Wish You Good Work " + name);             
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 21));
        welcomeLabel.setBounds(130, 50, 400, 30);
        frame.getContentPane().add(welcomeLabel);
    }
    
    
    public void showMsg(String messageText, String messageTitle) {  
    	JDialog dialog = new JDialog();
        dialog.setTitle(messageTitle);
        
        String formattedMessage = messageText.replaceAll("\\\n", "\n<br>");
        JLabel label = new JLabel("<html><div style='margin: " + 30 + "px;'>" + formattedMessage + "</div></html>");
        label.setHorizontalAlignment(JLabel.CENTER);
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
        JFrame personalFrame = new JFrame("Personal Panel");
        personalFrame.setBounds(BankaATM.frame.getBounds());
        personalFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        personalFrame.getContentPane().setLayout(null);   
        personalFrame.getContentPane().setBackground(new Color(180, 65, 65));
        personalFrame.setResizable(false);
        
        welcomeUser(personalFrame);
        
        JButton grantCreditButton = newButtonMng(personalFrame, "Grant Credit", 150, 180, 200, 35, new Font("Arial", Font.PLAIN, 21));
        personalFrame.getContentPane().add(grantCreditButton);

        JButton checkSalaryButton = newButtonMng(personalFrame, "Check Salary", 150, 230, 200, 35, new Font("Arial", Font.PLAIN, 21));
        personalFrame.getContentPane().add(checkSalaryButton);
        
        JButton updateCustomerLimitButton = newButtonMng(personalFrame, "Update Customer's Credit Limit", 80, 280, 350, 35, new Font("Arial", Font.PLAIN, 21));
        personalFrame.getContentPane().add(updateCustomerLimitButton);
    
        grantCreditButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	String customerId = JOptionPane.showInputDialog("Enter customer ID:");
                Customer customer = findCustomer(customerId);
                if (customer != null) {
                    double amount = Double.parseDouble(JOptionPane.showInputDialog("Enter Credit Amount:"));
                    grantCredit(customer, amount);
                    customer.saveUserData();
                } 
                else {
                	showMsg("Customer With ID " + customerId + "\nNot Found!", "Grant Credit");
                }
            }
        });

        checkSalaryButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {      
            	String personalId = JOptionPane.showInputDialog("Enter Your Personal ID:");
            	if(!personalId.equals(id)) {
            		showMsg("This ID : " + personalId + "\nBelongs to Another Personal", "Check Salary");
            	}
            	else {
            		Personal personal = findPersonal(personalId);
                	
                    if (personal != null) {
                        checkSalary();
                    } 
                    else {
                    	showMsg("Personal With ID " + personalId + "\nNot Found!", "Check Salary");
                    }
            	}          	          	
            }
        });
        
        updateCustomerLimitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {      
            	String customerId = JOptionPane.showInputDialog("Enter Customer ID:");
                Customer customer = findCustomer(customerId);
                if (customer != null) {
                	updateCustomerLimit(customer); 
                	customer.saveUserData();
            	}
            }
        });
        
        personalFrame.setVisible(true);
    }
    
    
    
    public void grantCredit(Customer customer, double amount) {
    	customer.setCreditLimit(customer.getMoney() * 2);
    	if(customer.getDebt() + amount > customer.getCreditLimit()) {
    		showMsg(customer.name + " " + customer.lastName + "'s\nCredit Limit " + customer.getCreditLimit() + "|Limit Exceeded.", "Limit Exceeded");    		
    	}
    	else {
    		customer.setDebt(customer.getDebt() + amount);
            customer.setMoney(customer.getMoney() + amount);
            showMsg("Credit Granted to " + customer.getName() + 
            		" " + customer.getLastName() + "\nCurrent Credit Debt: " 
            		+ customer.getDebt(), "Grant Credit");
            customer.saveUserData();
    	}        
    }
    
    public void updateCustomerLimit(Customer customer) {
    	customer.setCreditLimit(customer.getMoney() * 2);
        showMsg(customer.getName() + " " + customer.getLastName() +
        		"'s Credit Limit Updated\nNew Limit : " + customer.getCreditLimit(), "Update Limit");        
        customer.saveUserData();
    }

    public void checkSalary() {
    	showMsg("Your Salary is: " + salary, "Check Salary");
    }

    public void setSalary(double newSalary) {
        this.salary = newSalary;
    }

    public double getSalary() {
        return salary;
    }

    public void writeToFile(BufferedWriter writer) throws IOException {
        writer.write(id + "," + name + "," + lastName + "," + password + "," + salary);
        writer.newLine();
    }

    public static Personal fromString(String line) {
        String[] parts = line.split(",");
        return new Personal(parts[0], parts[1], parts[2], parts[3], Double.parseDouble(parts[4]));
    }
}

