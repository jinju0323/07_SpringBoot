package kr.jinju.myshop.controllers.restcontrollers;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import kr.jinju.myshop.helpers.FileHelper;
import kr.jinju.myshop.helpers.RestHelper;
import kr.jinju.myshop.models.Member;
import kr.jinju.myshop.models.UploadItem;
import kr.jinju.myshop.services.MemberService;

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
    public Map<String, Object> join(
        @RequestParam("user_id") String userId,
        @RequestParam("user_pw") String userPw,
        @RequestParam("user_name") String userName,
        @RequestParam("email") String email,
        @RequestParam("phone") String phone,
        @RequestParam("birthday") String birthday,
        @RequestParam("gender") String gender,
        @RequestParam("postcode") String postcode,
        @RequestParam("addr1") String addr1,
        @RequestParam("addr2") String addr2,
        @RequestParam(value = "photo", required = false) MultipartFile photo
    ) {

        // 1) 유효성 검사 생략

        // 2) 아이디 중복 검사

        try {
            memberService.isUniqueUserId(userId);
        } catch (Exception e) {
            return restHelper.badRequest(e);
        }

        // 3) 이메일 중복 검사
        try {
            memberService.isUniqueEmail(email);
        } catch (Exception e) {
            return restHelper.badRequest(e);
        }

        // 4) 업로드 받기
        UploadItem uploadItem = null;
        
        try {
            uploadItem = fileHelper.saveMultipartFile(photo);
        } catch (NullPointerException e) {
            // 업로드 된 항목이 있는 경우는 에러가 아니므로 계속 진행
        } catch (Exception e) {
            return restHelper.serverError(e);
        }

        Member member = new Member();
        member.setUserId(userId);
        member.setUserPw(userPw);
        member.setUserName(userName);
        member.setEmail(email);
        member.setPhone(phone);
        member.setBirthday(birthday);
        member.setGender(gender);
        member.setPostcode(postcode);
        member.setAddr1(addr1);
        member.setAddr2(addr2);

        // 업로드 된 이미지의 이름을 표시할 필요가 없다면 저장된 경로만 DB에 저장하면 됨
        if (uploadItem != null) {
            member.setPhoto(uploadItem.getFilePath());
        }

        try {
            memberService.addItem(member);
        } catch (Exception e) {
            return restHelper.serverError(e);
        }

        return restHelper.sendJson();
    }

    @PostMapping("/api/account/find_id")
    public Map<String, Object> findId(
        @RequestParam("user_name") String userName,
        @RequestParam("email") String email) {
        
        Member input = new Member();
        input.setUserName(userName);
        input.setEmail(email);

        Member output = null;

        try {
            output = memberService.findId(input);
        } catch (Exception e) {
            return restHelper.serverError(e);
        }

        Map<String, Object> data = new LinkedHashMap<String, Object>();
        data.put("item", output.getUserId());

        return restHelper.sendJson(data);
    }
}
