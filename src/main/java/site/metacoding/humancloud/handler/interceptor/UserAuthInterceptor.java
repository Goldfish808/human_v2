package site.metacoding.humancloud.handler.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import lombok.extern.slf4j.Slf4j;
import site.metacoding.humancloud.dto.SessionUser;

@Slf4j
@Component
public class UserAuthInterceptor implements HandlerInterceptor {

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    log.debug("디버그 :  유저 인터셉터");

    String uri = request.getRequestURI();
    String[] uriArray = uri.split("/");
    int reqId = Integer.parseInt(uriArray[uriArray.length - 1]);
    log.debug("디버그 : " + reqId);

    HttpSession session = request.getSession();
    SessionUser sessionUser = (SessionUser) session.getAttribute("sessionUser");
    int sessionUserId = sessionUser.getId();
    log.debug("디버그 : " + sessionUserId);

    String httpMethod = request.getMethod();
    if (httpMethod.equals("PUT") || httpMethod.equals("DELETE") || httpMethod.equals("GET")) {
      if (reqId == sessionUserId) {
        log.debug("디버그 : " + "유저 인터셉터 통과");
        return true;
      }
      response.sendError(HttpServletResponse.SC_BAD_REQUEST);

      throw new RuntimeException("권한이 없습니다.");
    }

    return true;
  }
}