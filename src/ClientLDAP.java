import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.*;
import java.util.Properties;

public class ClientLDAP {
    DirContext connection;
    public void newConnection() {
        try {
            Properties env = new Properties();
            env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
            env.put(Context.PROVIDER_URL, "ldap://ldap-test.lacnic.net.uy:389");
            env.put(Context.SECURITY_PRINCIPAL, "cn=admin,dc=lacnic,dc=net");
            env.put(Context.SECURITY_CREDENTIALS,"aighu0Reeg7s");

            connection = new InitialDirContext(env);
            System.out.println("The connection to the Active DS has been successfully established");
            System.out.println("\n");

        } catch (NamingException e) {
            e.printStackTrace();
        }

    }

    /*public void addObjectClass() {

        // Create a new set of attributes
        BasicAttributes attrs = new BasicAttributes();

//The item is an organizationalPerson, which is a subclass of person.
//Person is a subclass of top. Store the class hierarchy in the
//objectClass attribute
        Attribute classes = new BasicAttribute("objectclass");
        classes.add("Top");

//Add the objectClass attribute to the attribute set
        attrs.put(classes);

//Store the other attributes in the attribute set
        attrs.put("vacationActive","TRUE");
        attrs.put("vacationStart", "01/12/20");
        attrs.put("vacationEnd", "01/12/21");
        attrs.put("vacationInfo", "Gracias por escribirme, temporalmente no voy a poder responder mensajes entre las fechas $vacationStart a $vacationEnd");

//Add the new entry to the directory server
        try {
            connection.("cn=Yixi Suarez,ou=people,dc=lacnic,dc=net", attrs);
        } catch (NamingException e) {
            System.out.println("No se pudo crear el subcontext con la entrada proporcionada");
            e.printStackTrace();
        }


    }*/

    public void addNewAttribute() throws NamingException {
        ModificationItem modificationItem = new ModificationItem(connection.ADD_ATTRIBUTE, new BasicAttribute("description","amA"));
        connection.modifyAttributes("cn=Yazmin Suarez,ou=people,dc=lacnic,dc=net", new ModificationItem[]{modificationItem});
        System.out.println("Add new attribute to the actual entry");
    }
    public void addObjectClass() throws NamingException {
        BasicAttributes attrs = new BasicAttributes();

        Attribute classes = new BasicAttribute("objectclass");
        classes.add("Vacation");

//Add the objectClass attribute to the attribute set
        attrs.put(classes);

//Store the other attributes in the attribute set
        attrs.put("vacationActive","TRUE");
        attrs.put("vacationStart", "01/12/20");
        attrs.put("vacationEnd", "01/12/21");
        attrs.put("vacationInfo", "Gracias por escribirme, temporalmente no voy a poder responder mensajes entre las fechas $vacationStart a $vacationEnd");
        connection.modifyAttributes("cn=Yazmin Suarez,ou=people,dc=lacnic,dc=net",DirContext.ADD_ATTRIBUTE, attrs);
        connection.close();
    }





    public static void main(String[] args) throws NamingException {

        ClientLDAP app = new ClientLDAP();
        app.newConnection();
        app.addObjectClass();
    }


}