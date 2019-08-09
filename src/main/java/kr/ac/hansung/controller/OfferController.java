package kr.ac.hansung.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.ac.hansung.model.Offer;
import kr.ac.hansung.service.OfferService;

@Controller
public class OfferController {

	@Autowired // 의존 주입
	private OfferService offerService;

	@RequestMapping("/offers")
	public String showOffers(Model model) {
		List<Offer> offers = offerService.getCurrent(); // offers에 저장
		model.addAttribute("offers", offers); // model에 저장 // 이부분을 생략하면 offer객체를 view가 못 받아와서 오류가 뜬다

		return "offers";

	}

	@RequestMapping("/createoffer")
	public String createOffers(Model model) {

		model.addAttribute("offer", new Offer()); // offer를 생성 각필드 값은 초기화가 되있음

		return "createoffer";

	}

	@RequestMapping("/docreate")
	public String doCreate(Model model, @Valid Offer offer, BindingResult result) { // 데이터 binding한 내용을 검증 검증결과가 result에
																					// 들어간다

		if (result.hasErrors()) { // error가 있는지 check
			System.out.println("======form data does not validated");
			List<ObjectError> errors = result.getAllErrors(); // 모든에러
			for (ObjectError error : errors) { //
				System.out.println(error.getDefaultMessage());
			}
			return "createoffer"; // createoffer jsp로 넘어가짐
		}
		// System.out.println(offer); //offer에 tostring내용이넘어옴
		offerService.insert(offer);
		return "offercreated";

	}

}
