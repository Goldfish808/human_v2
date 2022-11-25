package site.metacoding.humancloud.handler.interceptor;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import site.metacoding.humancloud.domain.resume.ResumeDao;
import site.metacoding.humancloud.dto.SessionUser;
import site.metacoding.humancloud.dto.resume.ResumeRespDto.ResumeFindById;

@Slf4j
@RequiredArgsConstructor
public class ResumeInterceptor implements HandlerInterceptor {

    private final ResumeDao resumeDao;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String httpMethod = request.getMethod();
        log.debug("디버그 :  이력서 인터셉터");
        if (!(httpMethod.equals("PUT") || httpMethod.equals("DELETE"))) {
            return true;
        }

        // url요청의 {id}
        String uri = request.getRequestURI();
        String[] uriArray = uri.split("/");
        int reqResumeId = Integer.parseInt(uriArray[uriArray.length - 1]);

        Optional<ResumeFindById> resumePS = resumeDao.findById(reqResumeId);
        Integer resumeUserId = resumePS.get().getResumeUserId();

        // 세션의 id
        HttpSession session = request.getSession();
        SessionUser sessionUser = (SessionUser) session.getAttribute("sessionUser");
        int sessionUserId = sessionUser.getId();

        // 업데이트 딜리트

        if (httpMethod.equals("PUT") || httpMethod.equals("DELETE")) {
            if (resumeUserId == sessionUserId) {
                return true;
            }
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            throw new RuntimeException("권한이 없습니다.");
        }

        return true;
    }
}