# Overview
JMatrix is a Java SDK for Matrix.org API with easy to use syntax.

# Features
* Connect
  * - [x] Login
  * - [ ] Register 
* Room interaction 
  * - [x] Send messages
  * - [x] Send images
  * - [ ] Send files
  * - [x] Retrive messages
* Room management
  * - [x] Create and delete room 
  * - [x] Invite, kick and ban
* User information
  * - [x] Get username and avatar 

# Code Exemple 
```java
  //Import MatrixSDK class and insert your matrix synapse address
  //by default, it retrive 'https://matrix.org'
  MatrixSDK sdk = new MatrixSDK("http://127.0.0.1:8008");
  
  //MatrixClient allows you to use all parts of the API that require authentication
  //you can generate a new token with 'System.out.println(sdk.generateNewToken(username, password))'
  String token = "blablabla";
  MatrixClient client = new MatrixClient(sdk,token);
  
  //Send a message in a room
  try {
    client.getRoomByID("blablabla").sendMessage("Hello World !");
  } catch (MatrixException e) {
    e.printStackTrace();
  }
```

