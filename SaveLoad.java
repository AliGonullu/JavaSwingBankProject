package BankATM;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public abstract class SaveLoad extends Search{

	protected void saveUserData() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Customer customer : customerList) {
                customer.writeToFile(writer);
            }
            for (Personal personal : personalList) {
                personal.writeToFile(writer);
            }
            for (BankManager manager : managerList) {
                manager.writeToFile(writer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


	protected void loadUserData() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 6) {
                    customerList.add(Customer.fromString(line));
                } else if (parts.length == 5) {
                    personalList.add(Personal.fromString(line));
                } else if (parts.length == 4) {
                    managerList.add(BankManager.fromString(line));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
