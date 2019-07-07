package com.thequiz.thequiz;

public class Constance {

	public static Double NO_SUBMITS = 0.0;
	public static Double NO_CORRECT = 0.0;
 
	
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
}
