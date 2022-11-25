package site.metacoding.humancloud.web;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import site.metacoding.humancloud.dto.ResponseDto;
import site.metacoding.humancloud.dto.apply.ApplyReqDto.ApplySaveReqDto;
import site.metacoding.humancloud.service.ApplyService;
import site.metacoding.humancloud.util.annotation.Auth;

@RequiredArgsConstructor
@RestController
public class ApplyController {
  private final ApplyService applyService;

  @Auth(role = 0)
  @GetMapping("/s/apply/{userId}")
  public ResponseDto<?> getResumeList(@PathVariable Integer userId) {
    return new ResponseDto<>(1, "ok", applyService.이력서목록보기(userId));
  }

  @Auth(role = 0)
  @PostMapping("/s/apply/save")
  public ResponseDto<?> applyByResume(@RequestBody ApplySaveReqDto applySaveReqDto) {
    return new ResponseDto<>(1, "ok", applyService.기업공고에지원하기(applySaveReqDto));
  }

  @Auth(role = 0)
  @DeleteMapping("/s/apply/delete/{recruitId}/{reusmeId}")
  public @ResponseBody ResponseDto<?> applyDeleteById(@PathVariable("recruitId") Integer recruitId,
      @PathVariable("reusmeId") Integer reusmeId) {
    applyService.기업공고지원취소(recruitId, reusmeId);
    return new ResponseDto<>(1, "지원취소완료", null);
  }
}
