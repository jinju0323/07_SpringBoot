package kr.jinju.myshop.controllers.apis;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import kr.jinju.myshop.helpers.RestHelper;
import kr.jinju.myshop.services.MemberService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class AccountRestController {
    @Autowired
    private RestHelper restHelper;

    @Autowired
    private MemberService memberService;

    @GetMapping("/api/account/id_unique_check")
    public Map<String, Object> IdUniqueCheck(@RequestParam("user_id") String userId) {
        try {
            memberService.isUniqueUserId(userId);
        } catch (Exception e) {
            return restHelper.badRequest(e);
        }
        return restHelper.sendJson();
    }
    
    @GetMapping("/api/account/email_unique_check")
    public Map<String, Object> EmailUniqueCheck(@RequestParam("email") String email) {
        try {
            memberService.isUniqueEmail(email);
        } catch (Exception e) {
            return restHelper.badRequest(e);
        }
        return restHelper.sendJson();
    }
}
