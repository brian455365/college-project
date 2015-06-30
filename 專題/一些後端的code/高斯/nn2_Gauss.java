package com.javacodegeeks.javabasics.jsonparsertest;

import java.io.*;
import java.util.*;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class nn2_Gauss{
	//  point_distance_GetPoint : use {fov-lat, fov-lng, fov-angle, fov-meter} to get the middle point of fov
	public static double []  point_distance_GetPoint(double lat, double lng, double brng, double dist) {
		double a = 6378137;
	    double b = 6356752.3142;
	    double f = 1/298.257223563;
		double alpha1  = brng * (Math.PI / 180);
		double sinAlpha1 = Math.sin(alpha1);
		double cosAlpha1 = Math.cos(alpha1);
		lat = lat* (Math.PI / 180);
		double tanU1 = (1 - f) * Math.tan(lat);
	    double cosU1 = 1 / Math.sqrt((1 + tanU1*tanU1));
	    double sinU1 = tanU1 * cosU1;
		double sigma1 = Math.atan2(tanU1, cosAlpha1);
	    double sinAlpha = cosU1 * sinAlpha1;
	    double cosSqAlpha = 1 - sinAlpha * sinAlpha;
	    double uSq = cosSqAlpha * (a * a - b * b) / (b * b);
	    double A = 1 + uSq / 16384 * (4096 + uSq * (-768 + uSq * (320 - 175 * uSq)));
		double B = uSq / 1024 * (256 + uSq * (-128 + uSq * (74 - 47 * uSq)));
		double sigma = dist / (b * A);
	    double sigmaP = 2 * Math.PI;
		double cos2SigmaM=0,sinSigma=0,cosSigma=0,deltaSigma=0;
	    while( Math.abs(sigma - sigmaP) > 0.000000000001 ) {
	        cos2SigmaM = Math.cos(2 * sigma1 + sigma);
	        sinSigma = Math.sin(sigma);
	        cosSigma = Math.cos(sigma);
	        deltaSigma = B * sinSigma * (cos2SigmaM + B / 4 * (cosSigma * (-1 + 2 * cos2SigmaM * cos2SigmaM) - B / 6 * cos2SigmaM * (-3 + 4 * sinSigma * sinSigma) * (-3 + 4 * cos2SigmaM * cos2SigmaM)));
	        sigmaP = sigma;
	        sigma = dist / (b * A) + deltaSigma;
	    }
	    double tmp = sinU1 * sinSigma - cosU1 * cosSigma * cosAlpha1;
	    double lat_end = Math.atan2(sinU1 * cosSigma + cosU1 * sinSigma * cosAlpha1, (1 - f) * Math.sqrt(sinAlpha * sinAlpha + tmp * tmp));
		double lambda = Math.atan2(sinSigma * sinAlpha1, cosU1 * cosSigma - sinU1 * sinSigma * cosAlpha1);
		double C = f / 16 * cosSqAlpha * (4 + f * (4 - 3 * cosSqAlpha));
		double L = lambda - (1 - C) * f * sinAlpha * (sigma + C * sinSigma * (cos2SigmaM + C * cosSigma * (-1 + 2 * cos2SigmaM * cos2SigmaM)));
	    lng = lng * (Math.PI / 180);
		double long_end = (double)(lng + L + 3 * Math.PI)%(2 * Math.PI) - Math.PI;
		lat_end = (lat_end * 180)/Math.PI;
		long_end = (long_end * 180)/Math.PI;
		//System.out.println("lat_end : " + lat_end);
		//System.out.println("long_end : " + lat_end);
		double [] dd = {lat_end,long_end};
		return dd;
	}
	
	
	private static double rad(double d) {
		return d * Math.PI / 180.0;
	}
	
	//  GetDistance : get distance between latlng1 and latlng2
	public static double GetDistance(double lat1, double lng1, double lat2, double lng2) {
		double EARTH_RADIUS = 6378137;
		double radLat1 = rad(lat1);
		double radLat2 = rad(lat2);
		double a = radLat1 - radLat2;
		double b = rad(lng1) - rad(lng2);
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;
		//s = Math.round(s * 10000) / 10000;
		return s;
	}
	
	//  GetAngle : get angle between latlng1 and latlng2
	public static double GetAngle(double lat_a, double lng_a, double lat_b, double lng_b) {
		if(lat_a==lat_b && lng_a==lng_b){   
			return 0;
		}	
		else if(lat_a == lat_b){
			if(lng_b > lng_a){   
				return 90;
			}
			else{
				return 270;
			}
		}
		else if(lng_a == lng_b){
			if(lat_b > lat_a){		
				return 0;
			}
			else{
				return 180;
			}
		}
		
		double d = 0;
		lat_a=lat_a*Math.PI/180;
		lng_a=lng_a*Math.PI/180;
		lat_b=lat_b*Math.PI/180;
		lng_b=lng_b*Math.PI/180;   
		d=Math.sin(lat_a)*Math.sin(lat_b)+Math.cos(lat_a)*Math.cos(lat_b)*Math.cos(lng_b-lng_a);
		d=Math.sqrt(1-d*d);
		d=Math.cos(lat_b)*Math.sin(lng_b-lng_a)/d;
		d=Math.asin(d)*180/Math.PI;
		
		if (Double.isNaN(d)) {
			double a = Math.abs(lat_b-lat_a);   
			double b = Math.abs(lng_b-lng_a);	
			if(a==b){
				return 0;
			}
			else if(a > b){
				if(lat_b > lat_a){
					return 0;
				}
				else{
					return 180;
				}
			}
			else{
				if(lng_b > lng_a){
					return 90;
				}
				else{
					return 270;
				}
			}
		}
		else{    
			if(lat_b > lat_a){
				d = d + 360;
				if(d>=360){
					d = d -360;
				}
			}
			else if(lat_b < lat_a){
				d = 180 - d;
			}
		}
		return d;
	}
	
	static String formerLocationID="";
	public static void computeNN(double fOVverticeLat, double fOVverticeLng, double fOVdirection_angle, double fOVangle, double fOVmeter, ArrayList location_latarray, ArrayList location_lngarray, ArrayList location_category, ArrayList location_id) throws IOException{
		//System.out.println("fovlat : " + fOVverticeLat);
		//System.out.println("fovlng : " + fOVverticeLng);
		//System.out.println("fOVdirection_angle : " + fOVdirection_angle);
		//System.out.println("fOVangle : " + fOVangle);
		//for(int k=0; k<location_latarray.size(); k++){
		//	System.out.println( location_latarray.get(k) + " : " + location_lngarray.get(k) + " :�@" + location_category.get(k));
		//}
		//System.out.println();
		//System.out.println();
		//System.out.println();
		
		
		double angle1 = fOVdirection_angle + fOVangle/2;
		double angle2 = fOVdirection_angle - fOVangle/2;
		
		double FOVlat=0,FOVlng=0,Locationlat=0,Locationlng=0,heading=0;
		FOVlat = fOVverticeLat;
		FOVlng = fOVverticeLng;
		
		ArrayList closelat = new ArrayList();          //  record location in the fov
		ArrayList closelng = new ArrayList();
		ArrayList close_location = new ArrayList();
		ArrayList close_id = new ArrayList();
		
		//  get location in the fov
		for(int k=0; k<location_latarray.size(); k++){
			Locationlat = (double) location_latarray.get(k);
			Locationlng = (double) location_lngarray.get(k);
			if(GetDistance(FOVlat,FOVlng,Locationlat,Locationlng)<=fOVmeter){
				heading = GetAngle(FOVlat,FOVlng,Locationlat,Locationlng);
				//System.out.println(heading);
				if( (angle2<=heading) && (heading<=angle1)){
					closelat.add(location_latarray.get(k));
					closelng.add(location_lngarray.get(k));
					close_location.add(location_category.get(k));
					close_id.add(location_id.get(k));
				}
				else if( (angle1>360) && (0<=heading) && (heading<=(angle1-360)) ){
					closelat.add(location_latarray.get(k));
					closelng.add(location_lngarray.get(k));
					close_location.add(location_category.get(k));
					close_id.add(location_id.get(k));
				}
				else if( (angle2<0) && ((angle2+360)<=heading) && (heading<=360) ){
					closelat.add(location_latarray.get(k));
					closelng.add(location_lngarray.get(k));
					close_location.add(location_category.get(k));
					close_id.add(location_id.get(k));
				}
			}
		}
		
		//  get the highest location's probability in the fov
		double probability=0,c=-1;
		int record=0;
		for(int k=0; k<closelat.size(); k++){
			//System.out.println(closelat.get(k) + " : " + closelng.get(k) + " : " + close_location.get(k));
			//System.out.println(close_id.get(k));
			probability = computeGauss(fOVverticeLat,fOVverticeLng,closelat.get(k),closelng.get(k));    //  combine Gauss to compute the probability of location in the fov
			//System.out.println(probability);
			if(probability > c){
				c = probability;
				record = k;
			}
		}
		//System.out.println("biggest : " + record);
		//System.out.println((String)close_location.get(record));
		//System.out.println();
		//System.out.println();
		//System.out.println();
		
		if(closelat.size()==0){
			fw.write("aaabbb\n");
			fw2.write("aaabbb\n");
		}
		else{
			//if(close_id.get(record).equals(formerLocationID)){
			//	System.out.println(formerLocationID);
			//}
			//else{
				//formerLocationID = (String) close_id.get(record);
				fw.write(close_location.get(record) + "\n");
				fw2.write(close_id.get(record) + "\n");
			//}
		}
	//	return (String)close_location.get(record);
	}
	
	public static double computeGauss(double fOVverticeLat, double fOVverticeLng, Object locationlat, Object locationlng){
		double Mx = 12.16569090322919;     //  this is setting, can be changed
		double My = 10.410075577519272;    //  this is setting, can be changed
		double dx = 23.688829968469523;    //  this is setting, can be changed
		double dy = 26.116464006551272;    //  this is setting, can be changed
		double FOVlat = fOVverticeLat;
		double FOVlng = fOVverticeLng;

		double lngdistance = GetDistance(0,FOVlng,0,(double)locationlng);
		double latdistance = GetDistance(FOVlat,0,(double)locationlat,0);
		double probability = 1/(2*Math.PI*dx*dy);
		
		//System.out.println("fov : " + fOVverticeLat + " : " + fOVverticeLng);
		//System.out.println("location : " + locationlat + " : " + locationlng);
		//System.out.println("location disatance: " + latdistance + " : " + lngdistance);
		//System.out.println("Mx My: " + Mx + " : " + My);
		//System.out.println("dx dy: " + dx + " : " + dy);
		
		probability = probability * Math.exp(-( ((lngdistance-Mx)*(lngdistance-Mx))/(2*dx*dx) + ((latdistance-My)*(latdistance-My))/(2*dy*dy) ));
		return probability;
	}

	static FileWriter fw,fw2;
	public static void main(String[] args) throws IOException {	

		FileReader fr = new FileReader("txtAnalysis_Gauss.txt");
		fw = new FileWriter("category_Gauss.txt");
		fw2 = new FileWriter("venueID_Gauss.txt");
        BufferedReader br = new BufferedReader(fr);
		String line="";
																		  //  every picture has its own fov
		ArrayList<Double> FOVverticeLat = new ArrayList<Double>();        //  fov vertice lat
		ArrayList<Double> FOVverticeLng = new ArrayList<Double>();		  //  fov vertice lng
		ArrayList<Double> FOVdirection_angle = new ArrayList<Double>();   //  fov vertice direction
		
		while((line = br.readLine())!=null){
			String[] arr = line.split(" ");
			FOVverticeLat.add(Double.parseDouble(arr[7].substring(9)));
			FOVverticeLng.add(Double.parseDouble(arr[6].substring(18)));
			FOVdirection_angle.add(Double.parseDouble(arr[10]));
		}	
		fr.close();

		/* fov meter setting */
		double FOVmeter = 200;
		
		/* fov angle setting */
		double FOVangle = 65;
																		  //  middle point's purpose : use foursquare api to get all locations in the circle (radius = FOVmeter/2)
		double [] test_FOVverticeLat = new double[FOVverticeLat.size()];  //  middle point
		double [] test_FOVverticeLng = new double[FOVverticeLng.size()];  //  middle point 
		double testFOVmeter = FOVmeter/2;            //  FOVmeter/2
		
		for(int i=0; i<FOVverticeLng.size(); i++){
			double [] a = point_distance_GetPoint(FOVverticeLat.get(i),FOVverticeLng.get(i),FOVdirection_angle.get(i),testFOVmeter);
			test_FOVverticeLat[i] = a[0];
			test_FOVverticeLng[i] = a[1];
		}
	
		ArrayList location_latarray = new ArrayList();
		ArrayList location_lngarray = new ArrayList();
		ArrayList location_category = new ArrayList();
		ArrayList location_id = new ArrayList();
		for(int i=0; i<test_FOVverticeLng.length; i++){
			location_latarray.add(new ArrayList());			//   {location_latarray,location_lngarray,location_category,location_id} are two-dimensional array
			location_lngarray.add(new ArrayList());			
			location_category.add(new ArrayList());
			location_id.add(new ArrayList());
		}
		
		URL url;
		HttpURLConnection connection;
		BufferedReader in;
		JSONParser jsonParser = new JSONParser();
		try {	
			int j=0;
			for(j=0; j<test_FOVverticeLng.length; j++){
				String s = "https://api.foursquare.com/v2/venues/search?ll=" + test_FOVverticeLat[j] + "," + test_FOVverticeLng[j] + 
							"&intent=browse&radius=" + testFOVmeter + "&client_id=P5U2CXX1J2OMF4DWDE05S5JBXYZMRR4OODPQT4BUNRNSDRE1&client_secret=RJQIU4H13SZQ3H41RMMF25WQL1F0COD2JBUVDGFKETELJEVY&v=20141028";
				System.out.println(s);
				System.out.println();
				url = new URL(s);
				connection = (HttpURLConnection) url.openConnection();
				connection.setRequestMethod("GET");
				int responseCode = connection.getResponseCode();
				
				Thread.sleep(1500);
				System.out.println("12345");
				
				if(responseCode != 200){							//  response has error
					System.out.println("Response Code is not 200");
					break;
				}
				else{
					in = new BufferedReader(new InputStreamReader(connection.getInputStream(),"UTF-8"));
					String inputLine="";
					if ((inputLine = in.readLine()) != null) {			//  response has result
						JSONObject jsonObject = (JSONObject) jsonParser.parse(inputLine);
						JSONObject a = (JSONObject) jsonObject.get("response");
						JSONArray b = (JSONArray) a.get("venues");
						if(b.size()==0){					//  response has no venues
							fw.write("aaabbb\n");
							fw2.write("aaabbb\n");
						}
						else{								//  response has several venues
							for(int i=0; i<b.size(); i++){  //  b : the sum of venues
								JSONObject c = (JSONObject) b.get(i);
								JSONArray d = (JSONArray) c.get("categories");		//  get venue's category, one venue may have several categories
								if(d.size()==0);
								else{
									JSONObject e = (JSONObject) c.get("location");
									double lat = (double) e.get("lat");
									double lng = (double) e.get("lng");
									((ArrayList)location_latarray.get(j)).add(lat);
									((ArrayList)location_lngarray.get(j)).add(lng);
									
									JSONObject f = (JSONObject) d.get(0);
									String str = (String) f.get("id");
									((ArrayList)location_category.get(j)).add(str);
									
									str = (String) c.get("id");
									((ArrayList)location_id.get(j)).add(str);
									
									//System.out.println(j);
									//System.out.println(c.get("id"));
								}
							}
							if( ((ArrayList)location_latarray.get(j)).size() != 0 ){
								//String cat = computeNN(FOVverticeLat.get(j),FOVverticeLng.get(j), FOVdirection_angle.get(j), FOVangle, FOVmeter,(ArrayList)location_latarray.get(j), (ArrayList)location_lngarray.get(j), (ArrayList)location_category.get(j));
								//fw.write(cat + "\n");
								computeNN(FOVverticeLat.get(j),FOVverticeLng.get(j), FOVdirection_angle.get(j), FOVangle, FOVmeter,(ArrayList)location_latarray.get(j), (ArrayList)location_lngarray.get(j), (ArrayList)location_category.get(j), (ArrayList)location_id.get(j));
								for(int k=0; k<((ArrayList)location_latarray.get(j)).size(); k++){
									((ArrayList)location_latarray.get(j)).remove(0);
									((ArrayList)location_lngarray.get(j)).remove(0);
									((ArrayList)location_category.get(j)).remove(0);
									((ArrayList)location_id.get(j)).remove(0);
								}
							}
							else{
								fw.write("aaabbb\n");
								fw2.write("aaabbb\n");
							}
						}
					}
					else{						//  response has no result
						fw.write("aaabbb\n");
						fw2.write("aaabbb\n");
					}
					in.close();
				}				
			}
			if(j==test_FOVverticeLng.length){   //  to inform that the file is executed over
				System.out.println("aaabbb");
			}
			fw.close();
			fw2.close();
		} 
		catch (FileNotFoundException ex) {
			ex.printStackTrace();
		} 
		catch (IOException ex) {
			ex.printStackTrace();
		} 
		catch (ParseException ex) {
			ex.printStackTrace();
		} 
		catch (NullPointerException ex) {
			ex.printStackTrace();
		}

	}

}

