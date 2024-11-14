package kr.jinju.myshop.schedulers;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import kr.jinju.myshop.models.Member;
import kr.jinju.myshop.services.MemberService;
import lombok.extern.slf4j.Slf4j;

/**
 * 스케쥴러 샘플 클래스
 * 각 매서드가 정해진 스케쥴에 따라 자동 실행된다
 * 
 * 프로그램명 Application.java 파일의 상단에 "@EnableScheduling"이 추가되어야한다
 */
@Slf4j
@Component
@EnableAsync
public class AccountScheduler {

    /** 업로드 된 파일이 저장될 경로 (application.properties로부터 읽어옴) */
    @Value("${upload.dir}")
    private String uploadDir;

    @Autowired
    private MemberService memberService;
    
    //@Scheduled(cron= "0 0 4 * * ?") // 매일 오전 4시에 자동 실행
    @Scheduled(cron= "0 0 4 * * ?") 
    public void processOutMembers() throws InterruptedException {
        log.debug("탈퇴 회원 정리 시작");
        
        List<Member> outMembers = null;

        try {
            log.debug("탈퇴 회원 조회 및 삭제");
            outMembers = memberService.processOutMembers();
        } catch (Exception e) {
            log.error("탈퇴 회원 조회 및 삭제 실패");
            return;
        }

        if (outMembers == null || outMembers.size() == 0) {
            log.debug("탈퇴 대상 없음 :)");
            return;
        }

        for (int i = 0; i<outMembers.size(); i++) {
            Member m = outMembers.get(i);

            // 사용자가 업로드한 프로필 사진의 실제 경로
            File f = new File(uploadDir, m.getPhoto());
            log.debug("파일 삭제 >>> " + f.getAbsolutePath());

            if (f.exists()) {
                try {
                    f.delete();
                    log.debug("파일 삭제 성공");
                } catch (Exception e) {
                    log.debug("파일 삭제 실패");
                }
                
            }
        }

    }

}
