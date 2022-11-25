package site.metacoding.humancloud.domain.category;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class Category {
	private Integer categoryId;
	private Integer categoryResumeId;
	private Integer categoryRecruitId;
	private String categoryName;

	public Category(Integer categoryRecruitId, String categoryName, Integer categoryResumeId) {
		this.categoryRecruitId = categoryRecruitId;
		this.categoryName = categoryName;
		this.categoryResumeId = categoryResumeId;
	}

	public Category(Integer categoryResumeId, String category) {
		this.categoryResumeId = categoryResumeId;
		this.categoryName = category;
	}
}
