package fr.epsilonbzh.jmatrix;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * This class get errors messages about the API
 * @author epsilonbzh
 */
public class MatrixException extends IOException {

	/**
	 * @param errorStream error return by the server
	 */
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
			e.printStackTrace();
		}
	}
	
	public MatrixException() {
		
	}


	private static final long serialVersionUID = 1L;

}
