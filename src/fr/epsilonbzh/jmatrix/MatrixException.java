package fr.epsilonbzh.jmatrix;
import java.io.BufferedReader;
/**
 * @author epsilon
 */
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MatrixException extends IOException {

	public MatrixException(InputStream errorStream) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(errorStream));
		String line;
		StringBuffer content = new StringBuffer();
		
		try {
			while((line = reader.readLine()) != null) {
				content.append(line);
			}
			System.err.println(content.toString());
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public MatrixException() {
		
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
