import java.io.*;
import java.util.*;

//  use to combine two files into one file
public class combine {
    public static void main(String [] argv) throws IOException {
		FileWriter fw = new FileWriter("categoryAnalysis.txt");
        FileReader fr = new FileReader("categoryAnalysis_Gauss.txt");
		BufferedReader br = new BufferedReader(fr);
		int count=0,count2=0,count3=0;
		String line="";
		while((line = br.readLine())!=null){
			fw.write(line + "\n");
			count++;
		}
		fr.close();
		fr = new FileReader("categoryAnalysis_venueID.txt");
		br = new BufferedReader(fr);
		while((line = br.readLine())!=null){
			fw.write(line + "\n");
			count++;
		}
		fw.close();
        fr.close();
		
		fw = new FileWriter("txtAnalysis.txt");
		fr = new FileReader("txtAnalysis_Gauss.txt");
		br = new BufferedReader(fr);
		while((line = br.readLine())!=null){
			fw.write(line + "\n");
			count2++;
		}
		fr.close();
		fr = new FileReader("txtAnalysis_venueID.txt");
		br = new BufferedReader(fr);
		while((line = br.readLine())!=null){
			fw.write(line + "\n");
			count2++;
		}
		fw.close();
        fr.close();
		
		fw = new FileWriter("venueID.txt");
		fr = new FileReader("venueID_Gauss.txt");
		br = new BufferedReader(fr);
		while((line = br.readLine())!=null){
			fw.write(line + "\n");
			count3++;
		}
		fr.close();
		fr = new FileReader("venueID_venueID.txt");
		br = new BufferedReader(fr);
		while((line = br.readLine())!=null){
			fw.write(line + "\n");
			count3++;
		}
		fw.close();
        fr.close();
		
		if(count != count2 || count != count3){
			System.out.println("File error");
		}
    }
}
