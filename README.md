# P2P Instant Messaging Application

<b>Languages:</b> Java, XML<br>
<b>Libraries/Frameworks:</b> Swing, Java Socket API, dom4J, Jaxen

### Purpose

This IM application replaces the classical client-server network architecture with a direct peer-to-peer network communication. Instead of using a dedicated server which regulates incoming client connection requests and data sent by client output streams, the application lets each user to connect to other clients directly. This approach provides security in terms of circulation of data; nobody can access to conversation history of two clients in any way. However, the application only works in local area networks due to limitations caused by port forwarding and each user has the full responsibility to keep track of his own and his contacts' IP-addresses. My recommendation is using this application after fastening IP-addresses in a local area network. If a LAN is established using a router, the router can set different IP-addresses each time a computer connects to the router. 


### Implementation Note

If a contact is offline; either the contact is, in fact, offline or the IP-address of the contact has been changed. As I said, users should keep track of their contacts' IP-addresses. The second thing is, display pictures are not visible to contacts, I will fix it in v1.1 later.




