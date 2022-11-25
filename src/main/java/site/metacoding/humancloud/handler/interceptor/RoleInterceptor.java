package site.metacoding.humancloud.handler.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import lombok.extern.slf4j.Slf4j;
import site.metacoding.humancloud.dto.SessionUser;
import site.metacoding.humancloud.util.annotation.Auth;

@Slf4j
@Component
public class RoleInterceptor implements HandlerInterceptor {

  // 핸들러(컨트룰러 메서드)가 실행되기 전 : 권한을 확인하기 위해

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    log.debug("디버그 : " + " ROLE 인터셉터 작동");

    HandlerMethod method = (HandlerMethod) handler; // 요청을 처리할 메서드
    Auth auth = method.getMethodAnnotation(Auth.class); // 어노테이션 값 받아오기
    log.debug("디버그 : " + auth);
    if (auth == null) {
      return true;
    } // 어노테이션이 없는 경우, 권한 필요 없는 메서드임 => 통과

    // 1. 세션 체크 => 근데 필터로 거르니 체크 안 해도 됨
    HttpSession session = request.getSession();

    // 2. 권한 체크
    int role = auth.role(); // 0 일반 1 기업 2 관리자?
    SessionUser test = (SessionUser) session.getAttribute("sessionUser");
    int sessionUserRole = test.getRole();

    if (role == sessionUserRole) {
      log.debug("디버그 : " + "role 통과");
      return true;
    }

    response.sendError(HttpServletResponse.SC_BAD_REQUEST);

    throw new RuntimeException("권한이 없습니다.");

  }
}
