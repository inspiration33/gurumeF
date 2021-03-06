package com.security.gurume365.controller;

import java.security.Principal;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.security.gurume365.vo.Store;
import com.security.gurume365.vo.Users;

@Controller
public class StoreController {
	
	private static final Logger logger = LoggerFactory.getLogger(StoreController.class);
	
	// 지역정보 세션 값 zone;
	@RequestMapping(value = "/store/storeMain", method = RequestMethod.GET)
	public String storeMain(HttpSession session, Locale locale, Model model, String selector) {
		logger.info("상점 상세 페이지 이동 완료");
		logger.info("데이터1"+selector);
		session.setAttribute("zone", selector);
		return "store/storeMain";
	}
	// 노쇼, 음식 종류, 추천테마 정보를 갖고 있는 세션 값 thema
	@RequestMapping(value = "/store/thema", method = RequestMethod.GET)
	public String thema(HttpSession session, Locale locale, Model model, String selector) {
		session.setAttribute("thema", selector);
		logger.info("셀렉터 값 : "+selector);
		logger.info("상점 상세 페이지 이동 완료");
		//테마 리스트의 경우 스토어 리스트로 바로 가지 않고 하나를 더 거친다.
		if (selector.equals("thema")){
			logger.info("테마리스트로 갑니다");
			return "store/themaList";
		//노쇼리스트로 갈 경우 바로 스토어 리스트로 진입하게 된다.
		}else if (selector.equals("noshow")){
			logger.info("노쇼리스트로 갑니다");
			Store store=new Store("33","가게1","1","가게 주소1","가게 주소2","111-111",101,"중식","오전7시","가게1 정보","2",2500000,"블라블라","load.png",2,3);
			model.addAttribute("store", store);
			System.out.println(store);
			model.addAttribute("noshow", "noshow");
			return "store/storeList";
		//푸드리스트로 갈 경우 바로 스토어 리스트로 가지 않고 하나를 더 거친다.
		}else if (selector.equals("food")){
			logger.info("푸드리스트로 갑니다");
			return "store/foodList";
		}
		return "store/themaList";
	}
	//상품 정보 리스트에 가기전에 세션값을 지워주고, 모델에 담아 전달해준다.
	@RequestMapping(value= "/store/storeList", method = RequestMethod.GET)
	public String storeList(String food, HttpSession session, Model model){
		logger.info("상품 정보 리스트에 가기");
		//모델에 값 전달
		model.addAttribute("zone", session.getAttribute("zone"));
		model.addAttribute("thema", session.getAttribute("thema"));
		model.addAttribute("food", food);
			
		return "store/storeList";
	}
	//음식 종류별 레스토랑으로 들어왔을 경우 여기서 선택된 음식 정보를 받는다.
	@RequestMapping(value= "/store/foodList", method = RequestMethod.GET)
	public String foodList(String food){
		logger.info("[푸드리스트]셀렉터 값" + food);
		
		return "forward:storeList";
	}
	//샵 기본 정보 페이지로 이동
	@RequestMapping(value="/store/shopInfo", method = RequestMethod.GET)
	public String goShopInfo(Authentication auth, Principal principal, Model model){
		//깃
		logger.info("샵정보로 이동");
		logger.info(""+auth.getAuthorities());
		logger.info(""+principal.getName());
		logger.info(""+principal.toString());

		return "store/store";
	}
	
}
