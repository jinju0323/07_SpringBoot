package kr.jinju.mailer.helpers;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.springframework.stereotype.Component;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * 유틸리티 기능을 제공하는 클래스
 * 이전 수업에서 작성한 UtilHelper 클래스를 가져와서 사용
 */
@Component      // <- 스프링에게 이 클래스가 빈(Bean)임을 알려줌
public class UtilHelper {
    /** 싱글톤 객체 */
    // 의존성 주입 자동화를 통해 스프링이 싱글톤 객체 자동으로 처리해줌
    // private static UtilHelper current;

    // public static UtilHelper getInstance() {
    //     if (current == null) {
    //         current = new UtilHelper();
    //     }

    //     return current;
    // }

    // private UtilHelper() {
    //     super();
    // }

    /**
     * 랜덤 숫자를 생성하는 메서드
     * @param min   최솟값
     * @param max   최댓값
     * @return 생성된 랜덤 숫자
     */
    public int random(int min, int max) {
        int num = (int) ((Math.random() * (max - min + 1)) + min);
        return num;
    }

    /**
     * 클라이언트의 IP 주소를 가져오는 메서드
     * @param request HttpServletRequest 객체
     * @return IP 주소
     */
    public String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null) {
            ip = request.getHeader("WL-Proxy-Client-IP"); // 웹로직
        }
        if (ip == null) {
            ip = request.getHeader("HTTP_CLIENT_IP");
            }
        if (ip == null) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null) {
            ip = request.getRemoteAddr();
        }

        return ip;
    }

    /**
     * 쿠키값을 저장한다.
     * @param response - HttpServletResponse 객체
     * @param name - 쿠키 이름
     * @param value - 쿠키 값
     * @param maxAge - 쿠키 유효 시간 (0이면 지정 안함, 음수일 경우 즉시 삭제)
     * @param domain - 쿠키 도메인
     * @param path - 쿠키 경로
     */
    public void writeCookie(HttpServletResponse response, String name, String value, int maxAge, String domain, String path) {
        if (value != null && !value.equals("")) {
            try {
                // -> import java.net.URLEncoder;
                value = URLEncoder.encode(value, "utf-8");
            } catch (UnsupportedEncodingException e) {
                // -> import java.io.UnsupportedEncodingException;
                e.printStackTrace();
            }
        }

        Cookie cookie = new Cookie(name, value);
        cookie.setPath(path);

        if (domain != null) {
            cookie.setDomain(domain);            
        }

        if (maxAge != 0) {
            cookie.setMaxAge(maxAge);
        }

        response.addCookie(cookie);
    }

    /**
     * 쿠키 값을 저장한다. path값을 "/"로 강제 설정한다.
     * @param response - HttpServletResponse 객체
     * @param name - 쿠키 이름
     * @param value - 쿠키 값
     * @param maxAge - 쿠키 유효 시간 (0이면 지정 안함, 음수일 경우 즉시 삭제)
     * @param domain - 쿠키 도메인
     * 
     * @see #writeCookie(HttpServletResponse, String, String, int, String, String)
     */
    public void writeCookie(HttpServletResponse response, String name, String value, int maxAge, String domain) {
        this.writeCookie(response, name, value, maxAge, domain, "/");
    }

    /**
     * 쿠키 값을 저장한다. path값을 "/"로 domain을 null로 강제 설정한다.
     * @param response - HttpServletResponse 객체
     * @param name - 쿠키 이름
     * @param value - 쿠키 값
     * @param maxAge - 쿠키 유효 시간 (0이면 지정 안함, 음수일 경우 즉시 삭제)
     * @param domain - 쿠키 도메인
     * 
     * @see #writeCookie(HttpServletResponse, String, String, int, String, String)
     */
    public void writeCookie(HttpServletResponse response, String name, String value, int maxAge) {
        this.writeCookie(response, name, value, maxAge, null, "/");
    }

    /**
     * 쿠키 값을 저장한다. path값을 "/"로 domain을 null, maxAge를 0으로 강제 설정한다.
     * @param response - HttpServletResponse 객체
     * @param name - 쿠키 이름
     * @param value - 쿠키 값
     * @param maxAge - 쿠키 유효 시간 (0이면 지정 안함, 음수일 경우 즉시 삭제)
     * @param domain - 쿠키 도메인
     * 
     * @see #writeCookie(HttpServletResponse, String, String, int, String, String)
     */
    public void writeCookie(HttpServletResponse response, String name, String value) {
        this.writeCookie(response, name, value, 0, null, "/");
    }

    /**
     * 쿠키값을 삭제한다.
     */
    public void deleteCookie(HttpServletResponse response, String name) {
        this.writeCookie(response, name, null, -1, null, "/");
    }

    public void redirect(HttpServletResponse response, int statusCdoe, Object url, String message) {
        /** 알림 메시지 표시후 바로 이전 페이지로 이동 -> Helper에 이식 예정 */
        // HTTP 403 Forbidden 클라이언트 오류 상태 응답 코드는 서버에 요청이 전달되었지만,
        // 권한 때문에 거절되었다는 것을 의미
        response.setStatus(statusCdoe);
        response.setContentType("text/html; charset=UTF-8");
        
        PrintWriter out = null;

        try {
            out = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        if (message != null && !message.equals("")) {
            out.println("<meta http-equiv='refresh' content='0; url=" + url + "' />");
        }

        if (url != null && url.equals("")) {
            out.println("<script>");
            out.print("location.href = '" + url + "';");
            out.println("</script>");
        } else {
            out.println("<script>");
            out.print("history.back();");
            out.println("</script>");
        }

    }

}
