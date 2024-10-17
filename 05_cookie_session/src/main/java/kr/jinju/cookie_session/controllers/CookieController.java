package kr.jinju.cookie_session.controllers;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import kr.jinju.cookie_session.helpers.UtilHelper;



@Controller
public class CookieController {
    /**
     * 3) 쿠키 저장을 위한 작성 페이지
     */
    @GetMapping("/cookie/home")
    public String home(Model model,
            // -> import org.springframework.web.bind.annotation.CookieValue;
            @CookieValue(value = "name", defaultValue = "") String myCookieName,
            @CookieValue(value = "age", defaultValue = "0") int myCookieAge) {
        
        /** 컨트롤러에서 쿠키를 식별하기 위한 처리 */
        try {
            // 저장시에는 URLDecoding이 적용되었으므로 URLDecoding이 별도로 필요힘
            // -> import java.net.URLDecoder;
            myCookieName = URLDecoder.decode(myCookieName, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // 추출한 값을 View에게 전달
        model.addAttribute("myCookieName", myCookieName);
        model.addAttribute("myCookieAge", myCookieAge);

        return "/cookie/home";
    }

    /**
     * 쿠키를 저장하기 위한 action 페이지
     */
    @PostMapping("/cookie/save")
    public String save(HttpServletResponse response,
            @RequestParam(value = "cookie_name", defaultValue = "") String cookieName,
            @RequestParam(value = "cookie_time", defaultValue = "0") int cookieTime,
            @RequestParam(value = "cookie_var", defaultValue = "") String cookieVar) {
        
        /** 1) 파라미터를 쿠키에 저장하기 위한 URLEncoding 처리 */
        // 쿠키 저장을 위해서는 URLEncoding 처리가 필요하다.
        if(!cookieVar.equals("")) {
            try {
                // -> import java.net.URLEncoder;
                cookieVar = URLEncoder.encode(cookieVar, "utf-8");
            } catch (UnsupportedEncodingException e) {
                // -> import java.io.UnsupportedEncodingException;
                e.printStackTrace();
            }
        }

        /** 2) 쿠키 저장하기 */
        // 저장할 쿠키 객체 생성
        // -> import jakarta.servlet.http.Cookie;
        Cookie cookie = new Cookie(cookieName, cookieVar);

        // 유효 경로 --> 사이트 전역에 대한 설정 ("/")
        cookie.setPath("/");

        // 유효 도메인 (로컬 개발시에는 설정할 필요 없음)
        // -> "www.naver.com" 인 경우 ".naver.com"으로 설정
        //cookie.setDomain("localhost");

        // 유효시간 설정(0이하면 즉시 삭제, 초 단위)
        // 설정하지 않을 경우 브라우저를 닫기 전까지 유지
        cookie.setMaxAge(cookieTime);

        // 쿠키 저장
        response.addCookie(cookie);

        /** 강제 페이지 이동 */
        // 이 페이지에 머물렀다는 사실이 웹 브라우저의 history에 남지 않는다.
        return "redirect:/cookie/home";
    }
    

    /**
     * 팝업창 제어 페이지
     */
    @GetMapping("/cookie/popup")
    public String popup(Model model,
            // -> import org.springframework.web.bind.annotation.CookieValue;
            @CookieValue(value = "no-open", defaultValue = "") String noOpen) {

        // 쿠키값을 View에게 전달
        model.addAttribute("noOpen", noOpen);
        return "/cookie/popup";
    }

    @PostMapping("/cookie/popup_close")    
    public String popupClose(HttpServletResponse response,
            @RequestParam(value = "no-open", defaultValue = "") String noOpen) {
        
        /** 1) 쿠키 저장하기 */
        // 60초 간 유효한 쿠키 설정
        // 실제 상용화시에는 domain을 설정해야한다.
        UtilHelper.getInstance().writeCookie(response, "no-open", noOpen, 60);

        /** 2) 강제 페이지 이동 */
        return "redirect:/cookie/popup";
        }
}
