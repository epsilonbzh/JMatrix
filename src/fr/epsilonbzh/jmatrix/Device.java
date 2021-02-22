package fr.epsilonbzh.jmatrix;

/** This class get User's device information.
 * @author epsilonbzh
 *
 */
public class Device {
	private String deviceID;
	private String displayName;
	private String lastSeenTS;
	private String lastSeenIP;
	private MatrixClient client;
	
	
	/** This class get User's device information.
	 * @param client MatrixClient class
	 * @param deviceID Device's ID
	 */
	public Device(MatrixClient client,String deviceID) {
		this.deviceID = deviceID;
		this.client = client;
	}
	protected Device(String deviceID, String displayName,String lastSeenTS,String lastSeenIP) {
			this.deviceID = deviceID;
			this.displayName = displayName;
			this.lastSeenTS = lastSeenTS;
			this.lastSeenIP = lastSeenIP;
		}

		/** Get Device's ID
		 * @return deviceID
		 */
		public String getDeviceID() {
			return deviceID;
		}

		/** Get the Device's display name
		 * @return device's display name
		 * @throws MatrixException
		 */
		public String getDisplayName() throws MatrixException {
			if(displayName == null) {
				displayName = client.getDevice(deviceID).getDisplayName();
			}
			return displayName;
		}
		
		/** Get the timestamp for the last time a device as be connected to the server
		 * @return timestamp
		 * @throws MatrixException
		 */
		public String getLastSeenTS() throws MatrixException {
			if(lastSeenTS == null) {
				lastSeenTS = client.getDevice(deviceID).getLastSeenTS();
			}
			return lastSeenTS;
		}

		/** Get the last IP address used by a device to connect to the server 
		 * @return
		 */
		public String getLastSeenIP() {
			return lastSeenIP;
		}
}
