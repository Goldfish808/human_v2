package site.metacoding.humancloud.domain.resume;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Param;

import site.metacoding.humancloud.dto.PagingDto;
import site.metacoding.humancloud.dto.resume.ResumeReqDto.ResumeSaveReqDto;
import site.metacoding.humancloud.dto.resume.ResumeReqDto.ResumeUpdateReqDto;
import site.metacoding.humancloud.dto.resume.ResumeReqDto.ResumeViewCategoryReqDto;
import site.metacoding.humancloud.dto.resume.ResumeRespDto.ResumeFindAllDto;
import site.metacoding.humancloud.dto.resume.ResumeRespDto.ResumeFindById;

public interface ResumeDao {

	public void save(ResumeSaveReqDto ResumeSaveReqDto);

	public List<ResumeFindAllDto> findByCategoryName(ResumeViewCategoryReqDto resumeViewCategoryReqDto);

	public Optional<ResumeFindById> findById(Integer id);

	public List<ResumeFindAllDto> findAll(int startNum);

	public void update(ResumeUpdateReqDto resumeUpdateReqDto);

	public void deleteById(Integer id);

	public List<Resume> findByUserId(Integer userId);

	public void deleteByUserId(Integer userId);

	public Resume sumReadCount(Integer userId);

	public List<ResumeFindAllDto> orderByCareer(int startNum);

	public List<ResumeFindAllDto> orderByEducation(int startNum);

	public List<ResumeFindAllDto> orderByCreatedAt(int startNum);

	public List<ResumeFindAllDto> orderByRecommend(@Param("companyId") Integer companyId,
			@Param("startNum") Integer startNum);

	public PagingDto paging(@Param("page") Integer page);

	public void updateReadCount(Integer resumeId);

	public List<Resume> applyResumeList(Integer companyId);

}
