package site.metacoding.humancloud.domain.subscribe;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import site.metacoding.humancloud.dto.user.UserRespDto.UserMypageRespDto.SubscribeCompanyDto;

public interface SubscribeDao {
	public void save(Subscribe subscribe);

	public Subscribe findById(@Param("userId") Integer userId, @Param("companyId") Integer companyId);

	public void deleteByUserCompany(@Param("userId") Integer userId, @Param("companyId") Integer companyId);

	// public List<Subscribe> findAll();

	// public void update(Subscribe subscribe);

	// public void deleteById(Integer id);

	public List<SubscribeCompanyDto> findCompanyByUserId(Integer userId);

	public List<Subscribe> findByCompanyId(Integer companyId);

}