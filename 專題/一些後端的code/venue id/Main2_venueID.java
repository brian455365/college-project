package com.javacodegeeks.javabasics.jsonparsertest;

import java.net.*;
import java.util.ArrayList;
import java.io.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

//   according to txtAnalysis_venueID.txt , and get every-line's corresponding venueID and category
public class Main2 {
	public static void main(String[] args) throws IOException {
		//http:stackoverflow.com/questions/7189474/foursquare-java-api-v2-checkin-example
		//https:developer.foursquare.com/overview/auth#userless
		//https:developer.foursquare.com/overview/responses

		FileReader fr = new FileReader("txtAnalysis_venueID.txt");
		FileWriter writer = new FileWriter("category_venueID.txt");  
		FileWriter writer2 = new FileWriter("venueID_venueID.txt");  

		ArrayList <String> venue = new ArrayList<String>();
		
		int i=0;
		double M2Degree=0.00000900900901;	
			 
		BufferedReader br = new BufferedReader(fr);
		while (br.ready()) {
			String buf=br.readLine();		
			String[] tmp=buf.split("venue="); 
			if(tmp.length==2){
				tmp=tmp[1].split(",");
				venue.add(tmp[0]);	
				
			}				
		}
		fr.close();
			
//		System.out.println(venue.size());	
		String temp="";	
		for(String v:venue){	
			try{
				if(temp.equals(v)){
					System.out.println( "the same venue id: " + v);
				//	type.add("aaabbb");
				}
			//	else{
				temp = v;
			//	System.out.println( "this is : " + v);

				String data =  URLEncoder.encode(v, "UTF-8");
				data += "?" + URLEncoder.encode("client_id", "UTF-8") + "=" + URLEncoder.encode("P5U2CXX1J2OMF4DWDE05S5JBXYZMRR4OODPQT4BUNRNSDRE1", "UTF-8");
				data += "&" + URLEncoder.encode("client_secret", "UTF-8") + "=" + URLEncoder.encode("RJQIU4H13SZQ3H41RMMF25WQL1F0COD2JBUVDGFKETELJEVY", "UTF-8");
				data += "&v=" +URLEncoder.encode("20141108", "UTF-8");
		
				// Send data
				URL url = new URL("https://api.foursquare.com/v2/venues/"+data);
				URLConnection conn = url.openConnection();	   
		    
				System.out.println(url);
		    
				// Get the response
				BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				String response = rd.readLine();
		    
		    	if (response != null) {
		    		JSONParser jsonParser = new JSONParser();
		    		JSONObject jsonObject = (JSONObject) jsonParser.parse(response);
		    		JSONObject a = (JSONObject) jsonObject.get("response");
		    		JSONObject b = (JSONObject) a.get("venue");
		    		JSONArray c = (JSONArray) b.get("categories");
		    		if(c.size()==0){
		    			writer.write("aaabbb\n");
						writer2.write("aaabbb\n");
		    		}
		    		else{
		    			JSONObject d = (JSONObject) c.get(0);
		    			String e = (String) d.get("id");
		    			writer.write(e + "\n");
		    			writer2.write(v + "\n");
		    		}
		    	}
		    	else{
		    		writer.write("aaabbb\n");
					writer2.write("aaabbb\n");
		    	}
			//	}
			}
			catch (Exception e) {
				System.out.println(e);
				//type.add("aaabbb");
				writer.write("aaabbb\n");
				writer2.write("aaabbb\n");
			}
		}
		
		writer.close();
		writer2.close();
		System.out.println("aaabbb");
	}
}
