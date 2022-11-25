package site.metacoding.humancloud.domain.company;

import java.util.List;
import java.util.Optional;

import site.metacoding.humancloud.dto.PagingDto;
import site.metacoding.humancloud.dto.company.CompanyRespDto.CompanyFindAllDto;
import site.metacoding.humancloud.dto.company.CompanyRespDto.CompanyFindById;

public interface CompanyDao {
	public void save(Company company);

	public Optional<CompanyFindById> findById(Integer id);

	public List<CompanyFindAllDto> findAll(int startNum);

	public void update(Company company);

	public void deleteById(Integer id);

	public Company findByUsername(String companyUsername);

	public Company findAllUsername(String companyUsername);

	public PagingDto paging(Integer page);
}
