package site.metacoding.humancloud.domain.category;

import java.util.List;

import site.metacoding.humancloud.domain.resume.Resume;
import site.metacoding.humancloud.dto.category.CategoryRespDto.CategoryFindByName;
import site.metacoding.humancloud.dto.category.CategoryRespDto.CategoryFindByResumeId;

public interface CategoryDao {
	public void save(Category category);

	public Category findById(Integer id);

	public List<Category> findAll();

	public void update(Category category);

	public void deleteById(Integer id);

	public void deleteByResumeId(Integer resumeId);

	public void deleteByRecruitId(Integer recruitId);

	public List<String> distinctName();

	public List<CategoryFindByName> findByName(String name);

	public List<CategoryFindByResumeId> findByResumeId(Integer resumeId);

	public List<Category> findByRecruitId(Integer recruitId);

	public List<Resume> findByResumeCategory(String categories);

	public List<Category> joinRecruitCategory(Integer CompanyId);

}
