

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;
import javax.xml.parsers.ParserConfigurationException;

import java.net.HttpURLConnection;
import java.net.InetAddress;

import org.glassfish.jersey.jdkhttp.JdkHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import introsde.rest.adapters.Adapter;

public class App
{
	
    public static void main(String[] args) throws IllegalArgumentException, IOException, URISyntaxException, ParserConfigurationException, SAXException
    {
    	
        String protocol = "http://";
        String port_value = "5700";
        if (String.valueOf(System.getenv("PORT")) != "null"){
            port_value=String.valueOf(System.getenv("PORT"));
        }
        String port = ":"+port_value+"/";
        String hostname = InetAddress.getLocalHost().getHostAddress();
        if (hostname.equals("127.0.0.1"))
        {
            hostname = "localhost";
        }

        URI BASE_URI = new URI(protocol + hostname + port);
        

        System.out.println("Starting assignment02 standalone HTTP server...");
        JdkHttpServerFactory.createHttpServer(BASE_URI, createApp());
        
        String urlServer = "Server started on " + BASE_URI + "\n[kill the process to exit]";
        
        ////writer.println(urlServer);
        System.out.println(urlServer);
        
    }

    public static ResourceConfig createApp() {
        System.out.println("Starting sdelab REST services...");
        return new MyApplicationConfig();
    }
}