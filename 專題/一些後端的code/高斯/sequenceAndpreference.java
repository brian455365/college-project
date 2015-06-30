import java.io.*;
import java.util.*;

//  calculate user preference and everyday category's sequence of fov
public class sequenceAndpreference {
    public static void main(String [] argv) throws IOException {
        FileWriter fw = new FileWriter("output.txt");
		FileWriter fw2 = new FileWriter("preference.txt");
		FileReader fr = new FileReader("txtAnalysis.txt");
		//FileReader fr = new FileReader("repair2.txt");
		FileReader fr2 = new FileReader("categoryAnalysis.txt");
		FileReader fr3 = new FileReader("venueID.txt");
        BufferedReader br = new BufferedReader(fr);
		BufferedReader br2 = new BufferedReader(fr2);
		BufferedReader br3 = new BufferedReader(fr3);
		String line="",tempstr="",line2="",line3="";
		String[] temparr;
		int i=0,j=0,k=0,tempInt1=0,tempInt2=0;
		int pictureNum=0;
		
		ArrayList<Integer> year = new ArrayList<Integer>();
		ArrayList<Integer> month = new ArrayList<Integer>();
		ArrayList<Integer> day = new ArrayList<Integer>();
		ArrayList<Integer> hour = new ArrayList<Integer>();
		ArrayList<Integer> minute = new ArrayList<Integer>();
		ArrayList<Integer> second = new ArrayList<Integer>();
		ArrayList<String> type = new ArrayList<String>();
		ArrayList<String> venueID = new ArrayList<String>();
		//String [] temp_category ={ "A","C","E","F","N","O","P","R","S","T"};
		String [] temp_category ={ "0", "1","2","3","4","5","6","7","8","9" };
		
		while((line = br.readLine())!=null && (line2 = br2.readLine())!=null){
			line3=br3.readLine();
			temparr = line.split(" ");
			/* ex : tempstr="2014:06:12:23:07:56" OR tempstr=zzzzz.... */
			tempstr = temparr[12] + ":" + temparr[13];
			temparr = tempstr.split(":");
			year.add(Integer.parseInt(temparr[0]));
			month.add(Integer.parseInt(temparr[1]));
			day.add(Integer.parseInt(temparr[2]));
			hour.add(Integer.parseInt(temparr[3]));
			minute.add(Integer.parseInt(temparr[4]));
			second.add(Integer.parseInt(temparr[5]));
			type.add(line2);
			venueID.add(line3);
			pictureNum++;
		}
		
		/* record_year 紀錄有哪些年 */
		ArrayList<Integer> record_year = new ArrayList<Integer>();
		record_year.add(year.get(0));
		for(i=1; i<pictureNum; i++){
			for(j=0; j<record_year.size(); j++){
				if(record_year.get(j).equals(year.get(i))){
					break;
				}
			}
			if(j == record_year.size()){
				record_year.add(year.get(i));
			}
		}
		
		/* record_year[] 作 sorting */	
		for(i=1; i<record_year.size(); i++){
			for(j=0; j<(record_year.size()-i); j++){
				if(record_year.get(j) > record_year.get(j+1)){
					tempInt1 = record_year.get(j);
					tempInt2 = record_year.get(j+1);
					record_year.set(j,tempInt2);
					record_year.set((j+1),tempInt1);
				}
			}
		}
		
		/* record[][] = [n年*12個月*31天][每天的相片量] */	
		ArrayList record = new ArrayList();	
		for(i=0; i<(record_year.size()*12*31); i++){
			record.add(new ArrayList());
		}		
		int [] count = new int [(record_year.size()*12*31)];
		for(i=0; i<count.length; i++){
			count[i] = 0;
		}
		
		/* 將相片依照天數分類 */
		int couting=0;
		for(i=0; i<pictureNum; i++){
			for(j=0; j<record_year.size(); j++){
				if(year.get(i).equals(record_year.get(j))){
					couting = j * 31 * 12;
					break;
				}
			}
			couting = couting + (month.get(i)-1)*31 + (day.get(i)-1);
			((ArrayList)record.get(couting)).add(i);
			count[couting]++;			
		}
		
		/* 將每天的相片依照時間作sorting */
		int judge=0;
		for(j=0; j<count.length; j++){
			for(i=1; i<count[j]; i++){
				for(k=0; k<(count[j]-i); k++){
					judge = 0;
					tempstr = ((ArrayList)record.get(j)).get(k).toString();
					tempInt1 = Integer.parseInt(tempstr);
					tempstr = ((ArrayList)record.get(j)).get((k+1)).toString();
					tempInt2 = Integer.parseInt(tempstr);
					
					if(hour.get(tempInt1) > hour.get(tempInt2)){
						judge=1;
					}
					else if(hour.get(tempInt1) < hour.get(tempInt2)){
						judge=0;
					}
					else{
						if(minute.get(tempInt1) > minute.get(tempInt2)){
							judge = 1;
						}
						else if(minute.get(tempInt1) < minute.get(tempInt2)){
							judge = 0;
						}
						else{
							if(second.get(tempInt1) > second.get(tempInt2)){
								judge = 1;
							}
							else if(second.get(tempInt1) < second.get(tempInt2)){
								judge = 0;
							}
						}
					}
					
					if(judge==1){
						((ArrayList)record.get(j)).set(k,tempInt2);
						((ArrayList)record.get(j)).set((k+1),tempInt1);
					}
				}
			}
		}
		
		//int sum=0,sum2=0;	
		int d=0;
		String s = "aaabbb";
		int [] preferenceCount = new int[temp_category.length];
		for(j=0; j<count.length; j++){
			if(count[j] != 0){
				//fw.write("day " + j + "\n");
				//fw.write("the sum of picture in this day " + count[j] + "\n");
				for(i=0; i<count[j]; i++){
					tempstr = ((ArrayList)record.get(j)).get(i).toString();
					tempInt1 = Integer.parseInt(tempstr);
					//fw.write("original picture location : " + tempInt1 + "  year: " + year.get(tempInt1) + " month: " + month.get(tempInt1) + " day: " + day.get(tempInt1) + " hour: " + hour.get(tempInt1) + " minute: " + minute.get(tempInt1) + " second: " + second.get(tempInt1) + " type: " + type.get(tempInt1) + "\n");
					System.out.println(venueID.get(tempInt1));
					/*if(type.get(tempInt1).equals("aaabbb"));
					else{
						d=1;
						fw.write(type.get(tempInt1) + " ");
					}
					for(k=0; k<temp_category.length; k++){
						if(type.get(tempInt1).equals(temp_category[k])){
							preferenceCount[k]++;
							break;
						}
					}
					*/
					if(s.equals(venueID.get(tempInt1)));
					else{
						s = venueID.get(tempInt1);	
						if(type.get(tempInt1).equals("aaabbb"));
						else{
							d=1;
							fw.write(type.get(tempInt1) + " ");
							for(k=0; k<temp_category.length; k++){
								if(type.get(tempInt1).equals(temp_category[k])){
									preferenceCount[k]++;
									break;
								}
							}
						}
					}			
				}
				if(d==1){
					fw.write('\n');
					d=0;
				}
			}
			//sum = sum + count[j];
			//sum2 = sum2 + ((ArrayList)record.get(j)).size();
		}
		//System.out.println("sum: " + sum);
		//System.out.println("sum2: " + sum2);
		
		int preferenceSum=0;
		for(i=0; i<preferenceCount.length; i++){
			System.out.println("preference" + i + " count : " + preferenceCount[i]);
			preferenceSum = preferenceSum + preferenceCount[i];
		}
		double [] preferenceRatio = new double [temp_category.length];
		for(i=0; i<preferenceCount.length; i++){
			preferenceRatio[i] = (double)preferenceCount[i]/preferenceSum;
			System.out.println("preferenceRatio" + i + " : " + preferenceRatio[i]);
			fw2.write(preferenceRatio[i] + "\n");
		}
		
        fw.close();
		fw2.close();
		fr.close();
		fr2.close();
		fr3.close();
    }
}
