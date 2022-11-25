package site.metacoding.humancloud.domain.user;

import java.util.List;
import java.util.Optional;

import site.metacoding.humancloud.dto.CompanyRankingDto;
import site.metacoding.humancloud.dto.auth.UserFindByAllUsernameDto;
import site.metacoding.humancloud.dto.user.UserRespDto.UserFindById;
import site.metacoding.humancloud.dto.user.UserRespDto.UserFindByUsername;

public interface UserDao {
	public int save(User user);

	public Optional<UserFindById> findById(Integer id);

	public List<User> findAll();

	public int update(User user);

	public int deleteById(Integer userId);

	public Optional<UserFindByUsername> findByUsername(String username);

	public Optional<UserFindByAllUsernameDto> findAllUsername(String username);

	public List<CompanyRankingDto> findByRank();

}
