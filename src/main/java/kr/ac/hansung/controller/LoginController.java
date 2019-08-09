package kr.ac.hansung.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

	@RequestMapping("/login")
	public String showLogin(@RequestParam(value = "error", required = false) String error,
							@RequestParam(value = "logout", required = false) String logout, Model model) {

		// 에러가 발생했다면 메시지 띄움
		// request에 parameter로 error값이 들어와서 null이 아니라면
		if (error != null) {
			model.addAttribute("errorMsg", "Invalid username and password");
		}

		// 로그아웃했다면 메시지 띄움
		// request에 parameter로 logout값이 들어와서 null이 아니라면
		if (logout != null) {
			model.addAttribute("logoutMsg", "You have been logged out successfully");
		}

		return "login";
	}
}
