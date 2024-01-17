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

public class Customer extends User implements ILogin, IButtonHandler, IFile {
    private double money;
    private double debt;
    private double creditLimit;

  
    public Customer(String id, String name, String lastName, String password, double money, double debt) {
        super(id, name, lastName, password);
        this.money = money;
        this.debt = debt;
    }
    
    public void welcomeUser(JFrame frame) {
    	JLabel welcomeLabel = new JLabel("Please Select a Bank Transaction");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 21));
        welcomeLabel.setBounds(90, 50, 400, 30);
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

        
        dialog.getContentPane().setBackground(new Color(190, 65, 65));
        
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
    	int	fontSize = 19;
    	    	
        JFrame customerFrame = new JFrame("Customer Panel");
        customerFrame.setBounds(BankaATM.frame.getBounds());
        customerFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        customerFrame.getContentPane().setLayout(null);
        customerFrame.getContentPane().setBackground(new Color(190, 65, 65));
        customerFrame.setResizable(false);
        
        welcomeUser(customerFrame);
        
        JButton balanceInquiryButton = newButtonMng(customerFrame, "Balance Inquiry", 150, 140, 200, 35, new Font("Arial", Font.PLAIN, fontSize));
        customerFrame.getContentPane().add(balanceInquiryButton);

        JButton debtInquiryButton = newButtonMng(customerFrame, "Debt Inquiry", 150, 200, 200, 35, new Font("Arial", Font.PLAIN, fontSize));
        customerFrame.getContentPane().add(debtInquiryButton);

        JButton withdrawButton = newButtonMng(customerFrame, "Withdraw", 150, 260, 200, 35, new Font("Arial", Font.PLAIN, fontSize));
        customerFrame.getContentPane().add(withdrawButton);

        JButton depositButton = newButtonMng(customerFrame, "Deposit", 150, 320, 200, 35, new Font("Arial", Font.PLAIN, fontSize));
        customerFrame.getContentPane().add(depositButton);
 
        JButton payCreditDebtButton = newButtonMng(customerFrame, "Pay Credit", 150, 380, 200, 35,new Font("Arial", Font.PLAIN, fontSize));
        customerFrame.getContentPane().add(payCreditDebtButton);

        
        withdrawButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String amount = JOptionPane.showInputDialog("Enter Amount to Withdraw:");                
                if(amount != null) {
                	double withdrawAmount = Double.parseDouble(amount);
                	if (withdrawAmount <= money) {
                    	money = Math.clamp(money - withdrawAmount, 0, Integer.MAX_VALUE);                    
                    	showMsg("Withdraw Successful.\nCurrent Balance: " + money, "Withdraw");
                    } 
                	else {
                    	showMsg("Insufficient Funds!", "Withdraw");
                    }
                	saveUserData();
                }                                
            }
        });

        depositButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String amount = JOptionPane.showInputDialog("Enter Amount to Deposit:");
                if(amount != null) {
                	double depositAmount = Double.parseDouble(amount);
                    money += depositAmount;                
                    showMsg("Deposit successful.\nCurrent Balance: " + money, "Deposit");
                    saveUserData();
                }                
            }
        });
        
        payCreditDebtButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		String amount = JOptionPane.showInputDialog("Enter Amount to Pay:");
                if(amount != null) {
                	double payAmount = Double.parseDouble(amount);
                    if(money - payAmount >= 0) {
                    	money = Math.clamp(money - payAmount, 0, Integer.MAX_VALUE);
                        debt = Math.clamp(debt - payAmount, 0, Integer.MAX_VALUE);                         
                        showMsg("Payment Successful.\nCurrent Credit Debt: " + debt, "Payment");
                        saveUserData();
                    } 
                    else {
                    	showMsg("Insufficient Funds!", "Payment");                    	
                    }
                }        		
        	}   	
        });
        
        balanceInquiryButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		showMsg("Welcome " + name+ " " + lastName + "\nYour Balance: " + money, "Balance Inquiry");
        	}        	
        });

        debtInquiryButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		showMsg("Welcome " + name+ " " + lastName + "\nYour Credit Debt: " + debt, "Debt Inquiry");
        	}        	
        });   
        customerFrame.setVisible(true);
    }
    

    public void writeToFile(BufferedWriter writer) throws IOException {
        writer.write(id + "," + name + "," + lastName + "," + password + "," + money + "," + debt);
        writer.newLine();
    }

    public static Customer fromString(String line) {
        String[] parts = line.split(",");
        return new Customer(parts[0], parts[1], parts[2], parts[3], Double.parseDouble(parts[4]), Double.parseDouble(parts[5]));
    }

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public double getDebt() {
		return debt;
	}

	public void setDebt(double debt) {
		this.debt = debt;		
	}
	
	public double getCreditLimit() {
		return creditLimit;
	}

	public void setCreditLimit(double creditLimit) {
		this.creditLimit = creditLimit;		
	}
}