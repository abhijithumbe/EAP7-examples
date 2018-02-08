package com.redhat.dmr;

import java.io.IOException;
import java.net.InetAddress;

import org.jboss.as.cli.CommandLineException;
import org.jboss.as.controller.client.ModelControllerClient;
 import org.jboss.dmr.ModelNode;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.sasl.RealmCallback;

public class CheckActiveSessions {

    static String host = "localhost"; // replace it with ip address
    static int port = 9990; // Make sure that port offset is added in managemt port
    static String user = "admin"; // replace it with management username created with the help of add-user.sh
    static String password = "admin12@"; // respective password
    static String application_name="sessiondemo.war";
    
    public static void main(String[] args) throws CommandLineException, IOException, Exception {

	ModelControllerClient client = createClient(InetAddress.getByName(host),port,user,password);

	// /subsystem=web/connector=http:read-resource(recursive=true,include-runtime=true)

	ModelNode activeSession = new ModelNode();
	activeSession.get("operation").set("read-attribute");
	activeSession.get("name").set("active-sessions");

	ModelNode address = activeSession.get("address");
	address.add("deployment", application_name);
	address.add("subsystem", "undertow");

	
	ModelNode returnHttp = client.execute(activeSession);
	System.out.println("Total Active sessions for "+application_name+": "+returnHttp.get("result").toString());

	client.close();
    }

    private static final ModelControllerClient createClient(final InetAddress host, final int port,
	    final String username, final String password) {
	final CallbackHandler callackHandler = new CallbackHandler() {
	    public void handle(Callback[] callback) throws IOException, UnsupportedCallbackException {
		for (Callback current : callback) {
		    if (current instanceof NameCallback) {
			NameCallback ncb = (NameCallback) current;
			ncb.setName(username);
		    } else if (current instanceof PasswordCallback) {
			PasswordCallback pcb = (PasswordCallback) current;
			pcb.setPassword(password.toCharArray());
		    } else if (current instanceof RealmCallback) {
			RealmCallback rcb = (RealmCallback) current;
			rcb.setText(rcb.getDefaultText());
		    } else {
			throw new UnsupportedCallbackException(current);
		    }
		}
	    }
	};
	return ModelControllerClient.Factory.create(host, port, callackHandler);
    }

     

}
