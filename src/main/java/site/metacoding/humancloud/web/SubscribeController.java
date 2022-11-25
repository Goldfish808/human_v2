package site.metacoding.humancloud.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import site.metacoding.humancloud.dto.ResponseDto;
import site.metacoding.humancloud.dto.subscribe.SubscribeReqDto.SubscribeSaveReqDto;
import site.metacoding.humancloud.service.SubscribeService;
import site.metacoding.humancloud.util.annotation.Auth;

@RequiredArgsConstructor
@Controller
public class SubscribeController {

    private final SubscribeService subscribeService;

    @Auth(role = 0)
    @DeleteMapping("/s/subscribe/{userId}/{companyId}")
    public @ResponseBody ResponseDto<?> deleteSubscribe(@PathVariable("userId") Integer userId,
            @PathVariable("companyId") Integer companyId) {
        subscribeService.구독취소(userId, companyId);
        return new ResponseDto<>(1, "OK", null);
    }

    @Auth(role = 0)
    @PostMapping("/s/subscribe")
    public @ResponseBody ResponseDto<?> subscribeCompany(@RequestBody SubscribeSaveReqDto subscribeSaveReqDto) {
        return new ResponseDto<>(1, "ok", subscribeService.구독하기(subscribeSaveReqDto));
    }
}
