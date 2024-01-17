package BankATM;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class BankaATM extends SaveLoad{
	public static JFrame frame;
    private JButton loginButton;
    private JButton registerButton;
    private JTextField idField;
    private JTextField nameField;
    private JTextField lastNameField;
    private JTextField passwordField;
    private JComboBox<String> userTypeComboBox;

   
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new BankaATM().initialize();
        });
    }

    public void initialize() {
        loadUserData();

        frame = new JFrame("Banka ATM");
        frame.setBounds(850, 300, 500, 510);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        frame.getContentPane().setBackground(new Color(200, 70, 70));	
        frame.setResizable(false);
        
        int	fontSize = 19;
        
        JLabel titleLabel = new JLabel("Log In / Register");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 25));
        titleLabel.setBounds(145, 50, 200, 25);
        frame.getContentPane().add(titleLabel);
        
        JLabel userTypeLabel = new JLabel("User Type:");
        userTypeLabel.setFont(new Font("Arial", Font.BOLD, fontSize));
        userTypeLabel.setBounds(110, 140, 200, 25);
        frame.getContentPane().add(userTypeLabel);

        userTypeComboBox = new JComboBox<>(new String[]{"Customer", "Personal", "Manager"});       
        userTypeComboBox.setFont(new Font("Arial", Font.ITALIC, 15));
        userTypeComboBox.setBounds(240, 140, 150, 28);
        frame.getContentPane().add(userTypeComboBox);

        JLabel idLabel = new JLabel("ID:");
        idLabel.setFont(new Font("Arial", Font.BOLD, fontSize));
        idLabel.setBounds(110, 180, 200, 25);
        frame.getContentPane().add(idLabel);

        idField = new JTextField();
        idField.setBounds(240, 180, 150, 30);
        idField.setFont(new Font("Arial", Font.PLAIN, fontSize-2));
        frame.getContentPane().add(idField);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(new Font("Arial", Font.BOLD, fontSize));
        nameLabel.setBounds(110, 220, 200, 25);
        frame.getContentPane().add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(240, 220, 150, 30);
        nameField.setFont(new Font("Arial", Font.PLAIN, fontSize-2));
        frame.getContentPane().add(nameField);

        JLabel lastNameLabel = new JLabel("Last Name:");
        lastNameLabel.setFont(new Font("Arial", Font.BOLD, fontSize));
        lastNameLabel.setBounds(110, 260, 200, 25);
        frame.getContentPane().add(lastNameLabel);

        lastNameField = new JTextField();
        lastNameField.setBounds(240, 260, 150, 30);
        lastNameField.setFont(new Font("Arial", Font.PLAIN, fontSize-2));
        frame.getContentPane().add(lastNameField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, fontSize));
        passwordLabel.setBounds(110, 300, 200, 25);
        frame.getContentPane().add(passwordLabel);

        passwordField = new JTextField();
        passwordField.setBounds(240, 300, 150, 30);
        passwordField.setFont(new Font("Arial", Font.PLAIN, fontSize-2));
        frame.getContentPane().add(passwordField);

        loginButton = new JButton("Login");
        loginButton.setBounds(90, 360, 145, 40);
        loginButton.setFont(new Font("Arial", Font.PLAIN, fontSize));
        frame.getContentPane().add(loginButton);
        
        registerButton = new JButton("Register");
        registerButton.setFont(new Font("Arial", Font.PLAIN, fontSize));
        registerButton.setBounds(250, 360, 145, 40);
        frame.getContentPane().add(registerButton);

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String userType = (String) userTypeComboBox.getSelectedItem();
                if(userType.equals("Manager")) {userType = "BankManager";}             
                String id = idField.getText();
                String password = passwordField.getText();

                if (userType.equals("Customer")) {
                    Customer customer = findCustomer(id, password);
                    
                    if (customer != null) {
                        customer.login();
                    } 
                    
                    else{
                    	JOptionPane.showMessageDialog(null, "Invalid credentials!");
                    }             
                } 
                
                else if (userType.equals("Personal")) {
                    Personal personal = findPersonal(id, password);
                    
                    if (personal != null) {
                        personal.login();                        
                    }
                    
                    else {
                        JOptionPane.showMessageDialog(null, "Invalid credentials!");
                    }
                }
                
                else if (userType.equals("BankManager")) {
                    BankManager manager = findManager(id, password);
                    
                    if (manager != null) {
                        manager.login();
                    }
                    
                    else {
                        JOptionPane.showMessageDialog(null, "Invalid credentials!");
                    }
                }
            }
        });

        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String userType = (String) userTypeComboBox.getSelectedItem();
                String id = idField.getText();
                String name = nameField.getText();
                String lastName = lastNameField.getText();
                String password = passwordField.getText();

                if (userType.equals("Customer")) {
                    if (findCustomer(id) == null) {
                        Customer customer = new Customer(id, name, lastName, password, 0, 0);
                        customerList.add(customer);
                        JOptionPane.showMessageDialog(null, "Customer registered successfully!");
                        saveUserData();
                    } 
                    else {
                        JOptionPane.showMessageDialog(null, "Customer with the same ID already exists!");
                    }
                } 
                
                else if (userType.equals("Personal")) {
                    if (findPersonal(id) == null) {                        
                        Personal personal = new Personal(id, name, lastName, password);
                        personalList.add(personal);
                        JOptionPane.showMessageDialog(null, "Personal registered successfully!");
                        saveUserData();
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Personal with the same ID already exists!");
                    }
                } 
                
                else if (userType.equals("BankManager")) {
                    if (findManager(id) == null) {
                        BankManager manager = new BankManager(id, name, lastName, password);
                        managerList.add(manager);
                        JOptionPane.showMessageDialog(null, "Bank Manager registered successfully!");
                        saveUserData();
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Bank Manager with the same ID already exists!");
                    }
                }
            }
        });

        frame.setVisible(true);
    } 
}
