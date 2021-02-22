package fr.epsilonbzh.jmatrix;

/** User class get informations on a given user
 * @author epsilonbzh
 */
public class User {
	private MatrixClient client;
	private MatrixSDK api;
	private String userID;
	
	/** User class is useful to get information on a particular user
	 * @param client MatrixClient class
	 * @param userID User's ID, like \@exemple:matrix.org
	 */
	public User(MatrixClient client,String userID) {
		this.userID = userID;
		this.client = client;
		this.api = client.getAPI();
	}
	/** Get User's ID, like \@exemple:matrix.org
	 * @return UserID
	 */
	public String getUserID() {
		return userID;
	}
	/** Get User's public display name
	 * @return user's display name
	 * @throws MatrixException
	 */
	public String getDisplayName() throws MatrixException {
		return api.getDisplayNameById(userID);
	}

	/** Get User's Icon
	 * @return Media to user's icon
	 * @throws MatrixException
	 */
	public Media getAvatar() throws MatrixException {
		return new Media(api, api.getAvatarUrlById(userID));
	}
	/** Return true if the user is Online
	 * @return true or false
	 * @deprecated broken
	 * @throws MatrixException
	 */
	
	public boolean isOnline() throws MatrixException {
		if(client.isUserOnline(userID).contains("offline")){
			return false;
		}else {
			return true;
		}
		
		
	}
	
	
}
