package BankATM;

import java.io.BufferedWriter;
import java.io.IOException;

public interface IFile {
	void writeToFile(BufferedWriter writer) throws IOException;
}
