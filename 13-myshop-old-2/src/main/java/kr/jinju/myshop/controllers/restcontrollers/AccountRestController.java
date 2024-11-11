package kr.jinju.myshop.controllers.restcontrollers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import kr.jinju.myshop.helpers.FileHelper;
import kr.jinju.myshop.helpers.RestHelper;
import kr.jinju.myshop.models.Member;
import kr.jinju.myshop.models.UploadItem;
import kr.jinju.myshop.services.MemberService;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
public class AccountRestController {
    @Autowired
    private RestHelper restHelper;

    @Autowired
    private FileHelper fileHelper;

    @Autowired
    private MemberService memberService;

    @GetMapping("/api/account/id_unique_check")
    public Map<String, Object> idUniqueCheck(@RequestParam("user_id") String userId) {
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

    @PostMapping("/api/account/join")
    public Map<String,Object> join(
        @RequestParam("name") String name,
        @RequestParam("user_id") String userId,
        @RequestParam("user_pw") String userPw,
        @RequestParam("email") String email,
        @RequestParam("phone") String phone,
        @RequestParam("birthday") String birthday,
        @RequestParam("gender") String gender,
        @RequestParam("postcode") String postcode,
        @RequestParam("addr1") String addr1,
        @RequestParam("addr2") String addr2,
        @RequestParam("isOut") String isOut,
        @RequestParam("isAdmin") String isAdmin,
        @RequestParam("loginDate") String loginDate,
        @RequestParam("regDate") String regDate,
        @RequestParam("editDate") String editDate,
        @RequestParam(value = "photo", required = false) MultipartFile photo) {
        
        /** 1) 입력값에 대한 유효성 검사 */
        // 여기서는 생략

        /** 2) 아이디 중복 검사 */
        try {
            memberService.isUniqueUserId(userId);
        } catch (Exception e) {
            return restHelper.badRequest(e);
        }
        
        /** 3) 이메일 중복 검사 */
        try {
            memberService.isUniqueEmail(email);
        } catch (Exception e) {
            return restHelper.badRequest(e);
        }

        /** 4) 업로드 받기 */
        UploadItem uploadItem = null;
        try {
            uploadItem = fileHelper.saveMultipartFile(photo);
        } catch (NullPointerException e) {
            // 업로드 된 항목이 있는 경우 에러가 아니므로 계속 진행
            e.printStackTrace();
        } catch (Exception e) {
            // 업로드 된 항목이 있으나, 이를 처리하다가 에러가 발생한 경우
            return restHelper.serverError(e);
        }

        /** 5) 정보를 Service에 전달하기 위한 객체 구성 */
        Member member = new Member();
        member.setUserId(userId);
        member.setUserPw(userPw);
        member.setName(name);
        member.setEmail(email);

        return null;
    }
    
}
