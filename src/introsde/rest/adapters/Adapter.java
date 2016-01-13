package introsde.rest.adapters;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.ejb.*;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import org.json.*;



@Stateless // will work only inside a Java EE application
@LocalBean // will work only inside a Java EE application
@Path("/adapter")
public class Adapter {

	//Getting a picture from instagram
    @GET
    @Path("/getPictureUrl")
    public Response getPicture() throws ClientProtocolException, IOException {
    	
    	String[] tags = {"happiness", "happy", "smile", "motivation", "nevergiveup"};
    	int randomNum = 0 + (int)(Math.random()*(tags.length-1)); 
    	
    	final String ACCESS_TOKEN = "2329724457.1677ed0.eaf30452a29b44ba9b1c400948282914";
    	String ENDPOINT = "https://api.instagram.com/v1/tags/"+tags[randomNum]+"/media/recent?random=true&access_token="+ACCESS_TOKEN;

    	String jsonResponse;
    	
    	DefaultHttpClient client = new DefaultHttpClient();
    	HttpGet request = new HttpGet(ENDPOINT);
    	HttpResponse response = client.execute(request);
    	
    	BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

    	StringBuffer result = new StringBuffer();
    	String line = "";
    	while ((line = rd.readLine()) != null) {
    	    result.append(line);
    	}

    	JSONObject o = new JSONObject(result.toString());
    	
    	if(response.getStatusLine().getStatusCode() == 200){

        	JSONArray arr = o.getJSONArray("data");
        	
        	for (int i = 0; i < arr.length(); i++)
        	{
        		String type = arr.getJSONObject(i).getString("type");
        		
        		if(type.equals("image")){
        			String low_resolution_url = "\"low_resolution_url\":\"" +arr.getJSONObject(i).getJSONObject("images").getJSONObject("low_resolution").getString("url")+"\"";
        			String standard_resolution_url = ", "+"\"standard_resolution_url\":\"" +arr.getJSONObject(i).getJSONObject("images").getJSONObject("standard_resolution").getString("url")+"\"";
        			String link_instagram = ", "+"\"link_instagram\":\"" +arr.getJSONObject(i).getString("link")+"\"";
        			String random_tag = ", "+"\"random_tag\":\""+tags[randomNum]+"\"";
        			jsonResponse = "{"+low_resolution_url+standard_resolution_url+link_instagram+random_tag+"}";
        			//return arr.getJSONObject(i).toString();
        			return Response.ok(jsonResponse).build();
        		}
        	}
    	}
        	
    	return Response.status(204).build();
    }
    
    //Getting a motivation quote from quotesondesign
    @GET
    @Path("/getQuote")
    public Response getQuote() throws ClientProtocolException, IOException {
    	
    	String ENDPOINT = "http://quotesondesign.com/api/3.0/api-3.0.json";

    	DefaultHttpClient client = new DefaultHttpClient();
    	HttpGet request = new HttpGet(ENDPOINT);
    	HttpResponse response = client.execute(request);
    	
    	BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

    	StringBuffer result = new StringBuffer();
    	String line = "";
    	while ((line = rd.readLine()) != null) {
    	    result.append(line);
    	}
    	
    	JSONObject o = new JSONObject(result.toString());
    	
    	if(response.getStatusLine().getStatusCode() == 200){
    		return Response.ok(o.toString()).build();
        }
    	
    	return Response.status(204).build();
    	
    }
}