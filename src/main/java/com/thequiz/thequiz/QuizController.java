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
	public static List<Boolean> CORRECT_ANS = Arrays.asList(true, false, false, false);
	
	//@CrossOrigin(origins = "*")
	@RequestMapping(value = "TheQuiz/qbd", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public String getQuizQuestionsByDate(@RequestParam(value = "d", defaultValue = "") String date) {
		dataObj DO = new dataObj();
		if ("".equals(date.trim())) {
			date = df.format(new Date());
		}
		System.out.println("date:" + date);
		String p1 = "The objective is to use java.util.concurrent collections and classes, including <span class='s-code'>CyclicBarrier</span> and <span class='s-code'>CopyOnWriteArrayList</span>. Given the following <span class='s-code'>CBTest</span> class:";
		String p2 = "import static java.lang.System.out;<BR> public class CBTest {<BR> private List<Integer> results =<BR> Collections.synchronizedList(new ArrayList<>());<BR> class Calculator extends Thread {<BR> CyclicBarrier cb;<BR> int param;<BR> Calculator(CyclicBarrier cb, int param) {<BR> this.cb = cb;<BR> this.param = param;<BR> }<BR> public void run() {<BR> try {<BR> results.add(param * param);<BR> cb.await();<BR> } catch (Exception e) {<BR> }<BR> }<BR> }<BR> void doCalculation() {<BR> // add your code here<BR> }<BR> public static void main(String[] args) {<BR> new CBTest().doCalculation();<BR> }<BR>}<BR>";
		String p3 = "Which code fragment when added to the doCalculation method independently will make the code reliably print 13 to the console?<BR>Choose one.";
		List opt = new ArrayList<String>();
		opt.add(Arrays.asList( "A.) CyclicBarrier cb = new CyclicBarrier(2, () -> {# out.print(results.stream().mapToInt(v -> v.intValue()).sum());# });# new Calculator(cb, 2).start();# new Calculator(cb, 3).start();#".split("#")));
		opt.add(Arrays.asList( "B.) CyclicBarrier cb = new CyclicBarrier(2);<BR> out.print(results.stream().mapToInt(v -> v.intValue()).sum());<BR> new Calculator(cb, 2).start();<BR> new Calculator(cb, 3).start();<BR>".split("<BR>")));
	    opt.add(Arrays.asList( "C.) CyclicBarrier cb = new CyclicBarrier(3);<BR> new Calculator(cb, 2).start();<BR> new Calculator(cb, 3).start();<BR> cb.await();<BR> out.print(results.stream().mapToInt(v -> v.intValue()).sum());<BR>".split("<BR>")));;
        opt.add(Arrays.asList( "D.) CyclicBarrier cb = new CyclicBarrier(2); <BR> new Calculator(cb, 2).start();<BR> new Calculator(cb, 3).start();<BR> out.print(results.stream().mapToInt(v -> v.intValue()).sum());<BR>".split("<BR>")));
			 
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
		resp.setAns("The CyclicBarrier class is a feature of the java.util.concurrent package and it provides timing synchronization among threads, while also ensuring that data written by those threads prior to the synchronization is visible among those threads (this is the so-called “happensbefore” relationship). These problems might otherwise have been addressed using the synchronized, wait, and notify mechanisms, but they are generally considered low-level and harder to use correctly.");
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
