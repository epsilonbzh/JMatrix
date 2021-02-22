package fr.epsilonbzh.jmatrix;

/** Media class is used for images,videos, and other files uploaded on the Matrix Server.
 * @author epsilonbzh
 */
public class Media {
	private String mediaLink;
	private MatrixSDK sdk;
	/** Media class is used for images,videos, and other files uploaded on the Matrix Server.
	 * @param sdk MatrixAPI Class
	 * @param mediaLink MXC link, like mxc://matrix.org/bla bla...
	 */
	public Media(MatrixSDK sdk,String mediaLink) {
		this.mediaLink = mediaLink;
		this.sdk = sdk;
	}
	/** get a MXC link, like mxc://matrix.org/bla bla...
	 * @return MXC link
	 */
	public String getMediaLink() {
		return mediaLink;
	}
	
	/** get the Media ID
	 * @return MediaID
	 */
	public String getMediaID() {
		String[] media =  mediaLink.split("/");
		return media[3];
	}
	/** Show a download link to a media
	 * @return a http link to the media
	 * @throws MatrixException
	 */
	public String getDownloadLink() throws MatrixException {
		return sdk.getMXCDownloadLink(getMediaID());
	}
}
