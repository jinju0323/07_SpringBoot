package kr.jinju.interceptor.interceptors;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.jinju.interceptor.helpers.UtilHelper;
import lombok.extern.slf4j.Slf4j;
import ua_parser.Client;
import ua_parser.Parser;

@Slf4j
@Component
@SuppressWarnings("null")
public class MyInterceptor implements HandlerInterceptor {
    /** 페이지 실행 시작 시각을 저장할 변수 */
    long startTime = 0;

    /** 페이지의 실행 완료 시각을 저장할 변수 */
    long endTime = 0;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // log.debug("MyInterceptor.preHandle 실행됨")

        log.info("-------------- new client connect --------------");
        return HandlerInterceptor.super.preHandle(request, response, handler);

        /** 1) 페이지의 실행 시작 시각을 구한다 */
        //startTime = System.currentTimeMillis();

        /** 2) 접속한 클라이언트 정보 확인하기*/
        //String ua = request.getHeader("user-agent");
        //Parser uaParser = new Parser();
        //Client c = uaParser.parse(ua);

        //String fmf = "[Client] %s, %s, %s %s, %s, %s";

        //String ipAddr = UtilHelper.getInstance().getClientIp(request);
        //String osVersion = c.os.major + (c.os.minor != null ? "." + c.os.minor : "");

    }

    
}
