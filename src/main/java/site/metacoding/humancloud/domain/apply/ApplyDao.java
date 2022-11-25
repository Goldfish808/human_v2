package site.metacoding.humancloud.domain.apply;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import site.metacoding.humancloud.dto.apply.ApplyReqDto.ApplySaveReqDto;

public interface ApplyDao {
	public int save(ApplySaveReqDto applySaveReqDto);

	public Apply findById(Integer id);

	public List<Apply> findAll();

	public void update(Apply apply);

	public void deleteById(@Param("recruitId") Integer recruitId, @Param("resumeId") Integer resumeId);
}
