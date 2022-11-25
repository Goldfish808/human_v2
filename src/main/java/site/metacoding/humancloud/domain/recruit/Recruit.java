package site.metacoding.humancloud.domain.recruit;

import java.sql.Timestamp;
import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import site.metacoding.humancloud.domain.category.Category;
import site.metacoding.humancloud.domain.company.Company;

@NoArgsConstructor
@Getter
@Setter
public class Recruit {
	private Integer recruitId;
	private String recruitTitle;
	private String recruitCareer;
	private Integer recruitSalary;
	private String recruitLocation;
	private String recruitContent;
	private Integer recruitReadCount;
	private Integer recruitCompanyId;
	private String recruitDeadline;
	private Timestamp recruitCreatedAt;
	private String recruitStartDay;

	@Builder
	public Recruit(Integer recruitId, String recruitTitle, String recruitCareer, Integer recruitSalary,
			String recruitLocation, String recruitContent, Integer recruitReadCount, Integer recruitCompanyId,
			String recruitDeadline, Timestamp recruitCreatedAt, String recruitStartDay, Company company,
			List<Category> category,
			List<Recruit> recruitListByCompanyId) {
		this.recruitId = recruitId;
		this.recruitTitle = recruitTitle;
		this.recruitCareer = recruitCareer;
		this.recruitSalary = recruitSalary;
		this.recruitLocation = recruitLocation;
		this.recruitContent = recruitContent;
		this.recruitReadCount = recruitReadCount;
		this.recruitCompanyId = recruitCompanyId;
		this.recruitDeadline = recruitDeadline;
		this.recruitCreatedAt = recruitCreatedAt;
		this.recruitStartDay = recruitStartDay;
		this.company = company;
		this.category = category;
		this.recruitListByCompanyId = recruitListByCompanyId;
	}

	private Company company;
	private List<Category> category;
	private List<Recruit> recruitListByCompanyId;

	public Recruit(String recruitTitle) {
		this.recruitTitle = recruitTitle;
	}
}
