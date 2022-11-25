package site.metacoding.humancloud.service;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import site.metacoding.humancloud.domain.apply.ApplyDao;
import site.metacoding.humancloud.domain.resume.Resume;
import site.metacoding.humancloud.domain.resume.ResumeDao;
import site.metacoding.humancloud.dto.apply.ApplyReqDto.ApplySaveReqDto;
import site.metacoding.humancloud.dto.apply.ApplyRespDto.ApplyListRespDto;
import site.metacoding.humancloud.dto.apply.ApplyRespDto.ApplySaveRespDto;

@RequiredArgsConstructor
@Service
public class ApplyService {

  private final ApplyDao applyDao;
  private final ResumeDao resumeDao;

  public List<ApplyListRespDto> 이력서목록보기(Integer userId) {
    List<Resume> resumes = resumeDao.findByUserId(userId);
    return new ApplyListRespDto().toList(resumes);
  }

  public ApplySaveRespDto 기업공고에지원하기(ApplySaveReqDto applySaveReqDto) {
    applyDao.save(applySaveReqDto);
    return new ApplySaveRespDto(applySaveReqDto);
  }

  public void 기업공고지원취소(Integer recruitId, Integer resumeId) {
    applyDao.deleteById(recruitId, resumeId);
  }

}
