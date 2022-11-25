package site.metacoding.humancloud.handler.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import site.metacoding.humancloud.domain.resume.ResumeDao;
import site.metacoding.humancloud.dto.SessionUser;

@Slf4j
@RequiredArgsConstructor
@Component
public class ApplyAuthInterceptor implements HandlerInterceptor {

    private final ResumeDao resumeDao;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        HttpSession session = request.getSession();
        SessionUser sessionUser = (SessionUser) session.getAttribute("sessionUser");
        int sessionUserId = sessionUser.getId();

        String uri = request.getRequestURI();
        String[] uriArray = uri.split("/");
        int reqId = Integer.parseInt(uriArray[uriArray.length - 1]);

        String httpMethod = request.getMethod();

        if (httpMethod.equals("GET")) {
            if (reqId != sessionUserId) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                throw new RuntimeException("권한이 없습니다.");
            }
        }
        if (httpMethod.equals("DELETE")) {
            int resumeId = reqId;
            int resumeUserId = resumeDao.findById(resumeId).get().getResumeUserId();
            if (sessionUserId != resumeUserId) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                throw new RuntimeException("권한이 없습니다.");
            }
        }
        return true;
    }

}