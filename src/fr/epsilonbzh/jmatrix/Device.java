package fr.epsilonbzh.jmatrix;

/**
 * This class handle devices used to access the connected account.
 * @author epsilonbzh
 */
public class Device {
	
	/**
	 * ID used to be recognized by the API
	 * It's a String of 10 char long with only uppercase.
	 */
	private String deviceID;
	
	/**
	 * String used to be recognized by users. 
	 * It's set by default by the API with the browser name/app name...
	 * It can be edit by the user.
	 */
	private String displayName;
	
	/**
	 * TimeStamp for the last time the device has been seen by the API.
	 */
	private String lastSeenTS;
	
	/**
	 * Last IP Address used by the device.
	 * It can be either IPv4 or IPv6.
	 */
	private String lastSeenIP;
	
	/**
	 * Instance of MatrixClient.
     */
	private MatrixClient client;
	
	/** Device constructor, create Device object.
	 * @param client MatrixClient class
	 * @param deviceID Device's ID
	 * @see #deviceID 
	 */
	public Device(MatrixClient client,String deviceID) {
		this.deviceID = deviceID;
		this.client = client;
	}
	
	/**
	 * This constructor should be used only by the MatrixClient class.
	 * @param deviceID Device's ID.
	 * @param displayName Device's display name.
	 * @param lastSeenTS Last timestamp the device has been seen.
	 * @param lastSeenIP Last IP Address used by the device.
	 * @see #deviceID
	 * @see #displayName
	 * @see #lastSeenTS
	 * @see #lastSeenIP
	 */
	protected Device(String deviceID, String displayName,String lastSeenTS,String lastSeenIP) {
			this.deviceID = deviceID;
			this.displayName = displayName;
			this.lastSeenTS = lastSeenTS;
			this.lastSeenIP = lastSeenIP;
		}

	/** Get Device's ID
	 * @return deviceID
	 * @see #deviceID
	 */
	public String getDeviceID() {
		return deviceID;
	}
	/** Get the Device's display name
	 * @return device's display name
	 * @throws MatrixException MatrixException
	 * @see #displayName
	 */
	public String getDisplayName() throws MatrixException {
		if(displayName == null) {
			displayName = client.getDevice(deviceID).getDisplayName();
		}
		return displayName;
	}
	
	/** Get the timestamp for the last time a device as be connected to the server
	 * @return timestamp
	 * @throws MatrixException MatrixException
	 * @see #lastSeenTS
	 */
	public String getLastSeenTS() throws MatrixException {
		if(lastSeenTS == null) {
			lastSeenTS = client.getDevice(deviceID).getLastSeenTS();
		}
		return lastSeenTS;
	}
	/** Get the last IP address used by a device to connect to the server 
	 * @return last seen IP
	 * @see #lastSeenIP
	 */
	public String getLastSeenIP() {
		return lastSeenIP;
	}
}
