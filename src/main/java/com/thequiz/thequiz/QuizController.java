package com.thequiz.thequiz;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
	public static List<Boolean> CORRECT_ANS = Arrays.asList(false, false, false, true, true);
	
	//@CrossOrigin(origins = "*")
	@RequestMapping(value = "TheQuiz/qbd", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String getQuizQuestionsByDate(@RequestParam(value = "d", defaultValue = "") String date) {
		dataObj DO = new dataObj();
		if ("".equals(date.trim())) {
			date = df.format(new Date());
		}
		System.out.println("date:" + date);
		String p1 = "The objective is to apply the <span class='s-code'>static</span> keyword to methods and fields.Given the following classes:";
		String p2 = "public class Test {<BR>int result = -1;<BR>}<BR>public class CodeTest extends Test {<BR>public static void main(String[] args) {<BR>// line n1<BR>}<BR>}";
		String p3 = "What two lines added independently at line n1 will make the code compile successfully?<BR>Choose two.";
		List opt = Arrays.asList(
				"A.) result = 0;,B.) this.result = 0;,C.) super.result = 0;,D.) new Test().result = 0;,E.) new CodeTest().result = 0;"
						.split(","));

		DO.setQ(1);
		DO.setP1(p1);
		DO.setP2(p2);
		DO.setP3(p3);
		DO.setOpt(opt);
		DO.setQdate("01011988");

		return gson.toJson(DO);
	}

	//@CrossOrigin(origins = "*")
	@RequestMapping(value = "TheQuiz/cd", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String getQuizChartByDate(@RequestParam(value = "d", defaultValue = "") String date) {
		chartObj CO = new chartObj(1, 0.0, 0.0);
		return gson.toJson(CO);
	}

	//@CrossOrigin(origins = "*")
	@RequestMapping(value = "TheQuiz/sbd", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public String submitQuiz(@RequestBody String payload) {
		Constance.NO_SUBMITS++;
		System.out.println(payload);

		UserSubmitPayload plObj = gson.fromJson(payload, UserSubmitPayload.class);

		Resp resp = new Resp();
		resp.setQ(1);
		resp.setTitle("Answer for Question " + resp.getQ());
		resp.setAns(
				"options are D and E. This question investigates a small part of the rules by which names are resolved, the distinction between static and nonstatic variables, and access to variables from a static context. Here, we present a simplified description of some of the rules laid out in Java Language Specification sections 6 and 15. Our discussion will be nowhere near as complete as those sections, in either scope or detail, but we aim to present a perspective that has sufficient scope to answer this question and others like it. The approach presented is sound as far as it goes, but it does have limitations. Undoubtedly some readers will have knowledge way beyond the intended audience of this question, and they will likely be able to see exceptions to this description.");
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
