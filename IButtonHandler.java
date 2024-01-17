package BankATM;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;

public interface IButtonHandler {
	
	JButton newButtonMng(JFrame frame, String buttonMsg, int x, int y, int z, int t, Font _font);
	void welcomeUser(JFrame frame);
	
}
