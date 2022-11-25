package site.metacoding.humancloud.web;

import java.util.Arrays;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import site.metacoding.humancloud.dto.ResponseDto;
import site.metacoding.humancloud.dto.resume.ResumeReqDto.ResumeSaveReqDto;
import site.metacoding.humancloud.dto.resume.ResumeReqDto.ResumeUpdateReqDto;
import site.metacoding.humancloud.dto.resume.ResumeReqDto.ResumeViewCategoryReqDto;
import site.metacoding.humancloud.dto.resume.ResumeReqDto.ResumeViewOrderListReqDto;
import site.metacoding.humancloud.dto.resume.ResumeRespDto.ResumeDetailRespDto;
import site.metacoding.humancloud.dto.resume.ResumeRespDto.ResumeFindAllRespDto;
import site.metacoding.humancloud.dto.resume.ResumeRespDto.ResumeOrderByOrderListDto;
import site.metacoding.humancloud.service.ResumeService;
import site.metacoding.humancloud.service.UserService;
import site.metacoding.humancloud.util.annotation.Auth;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ResumeController {

  private final ResumeService resumeService;
  private final UserService userService;

  // http://localhost:8000/resume?page=0
  @Auth(role = 1)
  @GetMapping("/s/resume")
  public ResponseDto<?> viewList(@Param("page") Integer page) {

    ResumeFindAllRespDto resumeFindAllRespDto = resumeService.이력서목록보기(page);

    return new ResponseDto<>(1, "OK", resumeFindAllRespDto);
  }

  // http://localhost:8000/resume?page=0&category=Java
  @Auth(role = 1)
  @PostMapping("/s/resume")
  public ResponseDto<?> viewCategory(@RequestBody ResumeViewCategoryReqDto resumeViewCategoryReqDto) {
    ResumeOrderByOrderListDto resumeOrderByOrderListDto = resumeService.분류별이력서목록보기(resumeViewCategoryReqDto);
    return new ResponseDto<>(1, "OK", resumeOrderByOrderListDto);
  }

  // http://localhost:8000/resume/list?page=0&order=education
  @Auth(role = 1)
  @PostMapping("/s/resume/list")
  public ResponseDto<?> orderList(@RequestBody ResumeViewOrderListReqDto resumeViewOrderListReqDto) {
    ResumeFindAllRespDto resumeFindAllRespDto = resumeService.정렬하기(resumeViewOrderListReqDto);
    return new ResponseDto<>(1, "ok", resumeFindAllRespDto);
  }

  @Auth(role = 0)
  @DeleteMapping("/s/resume/{resumeId}")
  public ResponseDto<?> deleteResume(@PathVariable Integer resumeId) {
    resumeService.이력서삭제(resumeId);
    return new ResponseDto<>(1, "이력서 삭제 성공", null);
  }

  @Auth(role = 0)
  @PutMapping(value = "/s/resume/update/{resumeId}", consumes = { MediaType.APPLICATION_JSON_VALUE,
      MediaType.MULTIPART_FORM_DATA_VALUE })
  public ResponseDto<?> updateResume(@PathVariable Integer resumeId,
      @RequestPart("file") MultipartFile file,
      @RequestPart("resumeUpdateReqDto") ResumeUpdateReqDto resumeUpdateReqDto) throws Exception {

    resumeService.이력서수정(resumeId, file, resumeUpdateReqDto);

    ResumeDetailRespDto resumeDetailRespDto = resumeService.이력서상세보기(resumeId, resumeUpdateReqDto.getResumeUserId());

    return new ResponseDto<>(1, "이력서 수정 성공", resumeDetailRespDto);
  }

  @GetMapping("/s/resume/detail/{resumeId}/{userId}")
  public ResponseDto<?> detailResume(@PathVariable("resumeId") Integer resumeId,
      @PathVariable("userId") Integer userId) {

    resumeService.열람횟수증가(resumeId);
    ResumeDetailRespDto resumeDetailRespDto = resumeService.이력서상세보기(resumeId, userId);

    return new ResponseDto<>(1, "이력서 상세보기 성공", resumeDetailRespDto);
  }

  @GetMapping("/resume/saveForm/{userId}")
  public String saveResumeForm(@PathVariable Integer userId, Model model) {
    model.addAttribute("user", userService.유저정보보기(userId));
    return "page/resume/saveForm";
  }

  @Auth(role = 0)
  @PostMapping(value = "/s/resume/save", consumes = { MediaType.APPLICATION_JSON_VALUE,
      MediaType.MULTIPART_FORM_DATA_VALUE })
  public ResponseDto<?> create(@RequestPart("file") MultipartFile file,
      @RequestPart("ResumeSaveReqDto") ResumeSaveReqDto resumeSaveReqDto) throws Exception {

    resumeService.이력서저장(file, resumeSaveReqDto);

    List<String> test = Arrays.asList("생성");
    log.debug("리스트 테스트 : " + test.get(0));

    return new ResponseDto<>(1, "업로드 성공", null);
  }

}
