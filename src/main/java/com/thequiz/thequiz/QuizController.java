package com.thequiz.thequiz;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.TextMessage;

import com.google.gson.Gson;

@RestController
public class QuizController {
	public static DateFormat df = new SimpleDateFormat("ddMMyyyy");
	public static Gson gson = new Gson();
	public static List<Boolean> CORRECT_ANS = Arrays.asList(false, true, false, false,false);
	
	@CrossOrigin(origins = "*")
	@RequestMapping(value = "TheQuiz/qbd", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String getQuizQuestionsByDate(@RequestParam(value = "d", defaultValue = "") String date) {
		dataObj DO = new dataObj();
		if ("".equals(date.trim())) {
			date = df.format(new Date());
		}
		System.out.println("date:" + date);
		String p1 = "Given the following code and assuming the numbers at the left are line numbers, not part of the source file:" ;
		String p2 = " 11: public class Ex2<T extends Runnable, String> { <BR> 12: String s = \"Hello\";<BR> 13: public void test(T t) {<BR> 14: t.run();<BR> 15: }<BR> 16: }<BR>";
		String p3 = "Which one of the following is true ?";
		List opt = new ArrayList<String>();
		opt.add(Arrays.asList( "A. Line 11 fails to compile.".split("<BR>")));
		opt.add(Arrays.asList( "B. Line 12 fails to compile".split("<BR>")));
	    opt.add(Arrays.asList( "C. Line 13 fails to compile.".split("<BR>")));;
        opt.add(Arrays.asList( "D. Line 14 fails to compile.".split("<BR>")));
        opt.add(Arrays.asList( "E. Compilation succeeds".split("<BR>")));

			 
		DO.setQ(1);
		DO.setP1(p1);
		DO.setP2(p2);
		DO.setP3(p3);
		DO.setOpt(opt);
		DO.setCodeBlock(Boolean.FALSE);
		DO.setQdate("01011988");

		return gson.toJson(DO);
	}

	@CrossOrigin(origins = "*")
	@RequestMapping(value = "TheQuiz/cd", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String getQuizChartByDate(@RequestParam(value = "d", defaultValue = "") String date) {
		chartObj CO = new chartObj(1, 0.0, 0.0);
		return gson.toJson(CO);
	}

	@CrossOrigin(origins = "*")
	@RequestMapping(value = "TheQuiz/sbd", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public String submitQuiz(@RequestBody String payload) {
		Constance.NO_SUBMITS++;
		System.out.println(payload);

		UserSubmitPayload plObj = gson.fromJson(payload, UserSubmitPayload.class);

		Resp resp = new Resp();
		resp.setQ(1);
		resp.setTitle("Answer for Question " + resp.getQ());
		resp.setAns("Option B is correct.");
		resp.setQdate("01011988");

		for (int i = 0; i < CORRECT_ANS.size(); i++) {
			if (CORRECT_ANS.get(i) == plObj.getCkBox().get(i)) {
				resp.setCorw(true);
			} else {
				resp.setCorw(false);
				break;
			}
		}
		if (resp.isCorw()) {
			Constance.NO_CORRECT++;
		}  
		try {
			 pushNewValueToAllSocketSession(resp.getQ(),Constance.getcalculatedChartData());
		} catch (Exception e) {
 		}
		return gson.toJson(resp);
	}

	public void pushNewValueToAllSocketSession(Integer q, Double[] cw) {
		System.out.println("pushNewValueToAllSocketSession...");
		TextMessage tm = new TextMessage(gson.toJson(new chartObj(q, cw)));

		for (String sessionKey : ChartWebSocketHandler.sessionMap.keySet()) {
			try {
				ChartWebSocketHandler.sessionMap.get(sessionKey).sendMessage(tm);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
