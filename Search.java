package BankATM;

import java.util.ArrayList;

abstract public class Search {
	
	protected static final String FILE_NAME = "users.txt";
	protected static ArrayList<Customer> customerList = new ArrayList<>();
	protected static ArrayList<Personal> personalList = new ArrayList<>();
	protected static ArrayList<BankManager> managerList = new ArrayList<>();
	
	protected Customer findCustomer(String id, String password) {
        for (Customer customer : customerList) {
            if (customer.getId().equals(id) && customer.getPassword().equals(password)) {
                return customer;
            }
        }
        return null;
    }

	protected Customer findCustomer(String id) {
        for (Customer customer : customerList) {
            if (customer.getId().equals(id)) {
                return customer;
            }
        }
        return null;
    }

	protected Personal findPersonal(String id, String password) {
        for (Personal personal : personalList) {
            if (personal.getId().equals(id) && personal.getPassword().equals(password)) {
                return personal;
            }
        }
        return null;
    }

	protected Personal findPersonal(String id) {
        for (Personal personal : personalList) {
            if (personal.getId().equals(id)) {
                return personal;
            }
        }
        return null;
    }

	protected BankManager findManager(String id, String password) {
        for (BankManager manager : managerList) {
            if (manager.getId().equals(id) && manager.getPassword().equals(password)) {
                return manager;
            }
        }
        return null;
    }

	protected BankManager findManager(String id) {
        for (BankManager manager : managerList) {
            if (manager.getId().equals(id)) {
                return manager;
            }
        }
        return null;
    }
}
