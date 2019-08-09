package kr.ac.hansung.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the application home page.
 */
@Controller //아래 class가 controller라는것을 의미 / component는 bean으로 등록하는작업
public class HomeController {
		
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	// logback.xml에서 logger에서 name=kr.ac.hansung 밑에있는 logger도 상속을 받는다.
	// => 따라서 현재 evel은 DEBUG, apender-ref도 동일
	//private static final Logger logger = LoggerFactory.getLogger("kr/ac/hansung/controller/HomeController.java"); 와 동일
	
	@RequestMapping(value = "/", method = RequestMethod.GET) // '/'로 value를 주면 context root가 helloSpringMVC니깐 위에 /붙는 view가 나올것이고 RequestMethod.GET(get방식을 쓰겠다.)
	public String showHome(HttpServletRequest request, Locale locale, Model model) {
		
		// c밑 tmp/minutes에 있다
		//logger.trace("trace level: Welcom home! The client locale is {}", locale); // 현재 debug level 이므로 출력 안됨
		//logger.debug("trace level: Welcom home! The client locale is {}", locale);
		logger.info("trace level: Welcom home! The client locale is {}", locale);
		//logger.warn("trace level: Welcom home! The client locale is {}", locale);
		//logger.error("trace level: Welcom home! The client locale is {}", locale);
		
		String url = request.getRequestURL().toString();
		String clientIPAddress = request.getRemoteAddr();
		
		logger.info("Request URL : " + url);
		logger.info("Client IP : " + clientIPAddress);
		
		return "home";
	}
	
}
