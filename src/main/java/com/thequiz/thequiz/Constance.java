package com.thequiz.thequiz;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.Charsets;
import org.apache.commons.io.FileUtils;

public class Constance {

	public static Double NO_SUBMITS = 0.0;
	public static Double NO_CORRECT = 0.0;
	public static String PATH_QUIZ_FILE =  System.getProperty("the.quiz.full_path_filename");
	public static  List<String> dataList;
 
	public static Double[] getcalculatedChartData () {
		 Double res[] = {0.0d,0.0d} ;
		try {
 			res[0] = ((Constance.NO_CORRECT/Constance.NO_SUBMITS) * 100);
			
			res[1] = 100-res[0];
			
		} catch (Exception e) {
			res[0] = 0.0d; res[1] = 0.0d;
 		}
		
		if(Double.isNaN(res[0]) || Double.isNaN(res[0])) {res[0] = 0.0d; res[1] = 0.0d;}
		return res;
	}
	
	public  static String getQuestionfromFile (String date) {
		String data = "";
		if(dataList == null ) {
			try {
				dataList = FileUtils.readLines(new File(PATH_QUIZ_FILE),Charsets.UTF_8);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		for (String datafromfile : dataList) {
			if (datafromfile.indexOf(date+":")!=-1) {
				System.out.println("date:"+date);
				System.out.println("datafromfile:"+datafromfile);
				data = datafromfile.replace(date+":","");break;
			}
		}
		return data.trim();
	}
}
