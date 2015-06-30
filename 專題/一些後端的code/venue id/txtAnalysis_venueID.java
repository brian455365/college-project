import java.io.*;
import java.util.*;

/*-----------------------------前提 : 參數固定-----------------------------*/ 
/*-----------------------------前提 : 格式固定-----------------------------*/ 
public class txtAnalysis { 
	public static void main(String[] args) throws IOException {
		FileReader fr = new FileReader("input.txt");
		FileWriter fw = new FileWriter("txtAnalysis_venueID.txt");
		BufferedReader br = new BufferedReader(fr);
		String line="",tempstr="";
		String [] tempArray;
		while((line = br.readLine())!=null){
			String[] arr = line.split(" ");
			// arr[6] => GeoData[longitude=121.69114 
			// arr[7] => latitude=25.207552
			// arr[9] => Direction:
			// arr[10] => " " 或 195.4166667
			// arr[11] => Date:
			// arr[12] => " " 或 2014:06:14
			if(arr.length<4);
			else{
				/* 檢查 arr[10] */
				//if(arr[10].isEmpty());                // String.isEmpty()
				/* 檢查 arr[12] */
				if(arr[12].isEmpty());
				else{
					tempstr = arr[12] + ":" + arr[13];				
					tempArray = tempstr.split(":");
					if(tempArray.length==6){
						/* 檢查 "月" */
						if( Integer.parseInt(tempArray[1])>0 && Integer.parseInt(tempArray[1])<13 ){
							/* 檢查 "日" */
							if( Integer.parseInt(tempArray[2])>0 && Integer.parseInt(tempArray[2])<32 ){
								/* 檢查 "時" */
								if( Integer.parseInt(tempArray[3])>=0 && Integer.parseInt(tempArray[3])<24 ){
									/* 檢查 "分" */
									if( Integer.parseInt(tempArray[4])>=0 && Integer.parseInt(tempArray[4])<60 ){
										/* 檢查 "秒" */
										if( Integer.parseInt(tempArray[5])>=0 && Integer.parseInt(tempArray[5])<60 ){
											/* 檢查是否有venue id */ 
											tempArray = line.split("venue=");
											if(tempArray.length==2){
												fw.write(line + "\n");
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		fw.close();
        fr.close();
	}	
}