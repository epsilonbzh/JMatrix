package fr.epsilonbzh.jmatrix;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;

/**
 * @author epsilonbzh
 *
 */
public class MatrixSDK {

private boolean verbose = false;
private String serverURL;
private String baseURL;

/**
 * @param serverURL full address of the homeserver
 */
public MatrixSDK(String serverURL) {
	this.serverURL = serverURL;
	this.baseURL = serverURL + "/_matrix/client/r0/";
}

public MatrixSDK() {
	String url = "https://matrix.org";
	this.serverURL = url;
	this.baseURL = url + "/_matrix/client/r0/";
}

public void activateVerboseMode(boolean verbose_state) {
	if(verbose_state == true) {
		this.verbose = true;
	}else {
		this.verbose = false;
	}
}


/**
 * @param username without the "@" and domain like ":matrix.org"
 * @return true or false
 */
public boolean isUsernameAvailable(String username) {
	try {
	RequestGET(baseURL + "register/available?username=" + username);
	return true;
	}catch (IOException e) {
		return false;
	}

}
//TODO WIP
@Deprecated
public void registerNewAccount(String username, String password) throws MatrixException {
	HashMap<String, String> args = new HashMap<String, String>();
	args.put("username", username);
	args.put("passwod", password);
	
	RequestPOST(baseURL + "register?kind=user",args);
}

/**
 * @param username without the "@" and domain like ":matrix.org"
 * @param password used for login
 * @return a new token
 * @throws MatrixException
 */
public String generateNewToken(String username, String password) throws MatrixException {
	HashMap<String, String> args = new HashMap<String, String>();
	args.put("type", "m.login.password");
	args.put("user", username);
	args.put("password", password);
	
	String response = RequestPOST(baseURL + "login", args);
	String[] content = response.split("\"");
	
	String token = null;
	String user_id = null;
	String device_id = null;
	int i = 0;
	for(String item : content) {
		switch(item) {
		case "access_token":
			token = content[i+2];
			i++;
			break;
		case "user_id":
			user_id = content[i+2];
			i++;
			break;
		case "device_id":
			device_id = content[i+2];
			i++;
			break;
		default:
			i++;
			break;
		}
	}
	System.out.println("[INFO] A new token has been generated for the user : " + user_id + " with the deviceID : " + device_id);
	return token;
	
}

protected String getDisplayNameById(String userID) throws MatrixException {
	String response = RequestGET(baseURL + "profile/" + userID + "/displayname");
	String[] content = response.split("\"");
	return content[3];
}


protected String getAvatarUrlById(String userID) throws MatrixException {
	
	String response = RequestGET(baseURL + "profile/" + userID + "/avatar_url");
	String[] content = response.split("\"");
	return content[3];
	
}

protected String RequestGET(String url) throws MatrixException {
	if(this.verbose == true) {
		System.err.println("Request --> " + url);
		}
	try {
	URL request = new URL(url);
	HttpURLConnection connexion = (HttpURLConnection) request.openConnection();
	int responsecode = connexion.getResponseCode();
	if(responsecode < 299) {
	BufferedReader reader = new BufferedReader(new InputStreamReader(request.openStream()));
	String line;
	StringBuffer content = new StringBuffer();
	
	while((line = reader.readLine()) != null) {
		content.append(line);
		}
	if(this.verbose == true) {
		System.err.println("GET <-- " + content.toString());
		}
	return content.toString();
	}else {
		throw new MatrixException(connexion.getErrorStream());
	}
	
	}catch (IOException e) {
		throw new MatrixException();
	}
}

protected String RequestPOST(String url,HashMap<String,String> args) throws MatrixException {
	if(this.verbose == true) {
		System.err.println("Request --> " + url);
		}
	try {
		URL request = new URL(url);
		HttpURLConnection connexion = (HttpURLConnection) request.openConnection();
		connexion.setRequestMethod("POST");
		connexion.setDoOutput(true);
		
		Iterator<String> itk = args.keySet().iterator();
		Iterator<String> itv = args.values().iterator();
		
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		while(itk.hasNext() && itv.hasNext()) {
			sb.append(",\"" + itk.next() + "\":\"" + itv.next() + "\"");
		}
		sb.append("}");
		String post = sb.toString().replace("{,", "{");
		if(this.verbose == true) {
			System.err.println("POST --> " + post);
		}
		byte[] arguments = post.getBytes(StandardCharsets.UTF_8);
		
		try(OutputStream os = connexion.getOutputStream()){
			os.write(arguments);
		}
		int responsecode = connexion.getResponseCode();
		if(responsecode < 299) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(connexion.getInputStream()));
		String line;
		StringBuffer content = new StringBuffer();
		
		while((line = reader.readLine()) != null) {
			content.append(line);
			}
		if(this.verbose == true) {
			System.err.println("GET <-- " + content.toString());
			}
		return content.toString();
		}else {
			throw new MatrixException(connexion.getErrorStream());
		}
		
		}catch (IOException e) {
			throw new MatrixException();
		}
	
}

protected String getMXCDownloadLink(String MediaID) throws MatrixException {
	String[] domaine = serverURL.split("/");
	return serverURL + "/_matrix/media/r0/download/" + domaine[2] + "/" + MediaID;
}

protected String getServerURL() {
	return this.serverURL;
}

}
