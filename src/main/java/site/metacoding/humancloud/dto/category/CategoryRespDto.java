package site.metacoding.humancloud.dto.category;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class CategoryRespDto {

  @Getter
  @Setter
  public static class CategoryFindByResumeId {
    private Integer categoryId;
    private Integer categoryResumeId;
    private String categoryName;
  }

  @Getter
  @Setter
  public static class CategoryDistinctNameDto {
    private String CategoryName;
  }

  @NoArgsConstructor
  @Getter
  @Setter
  public static class CategoryFindByName {
    private Integer categoryId;
    private Integer categoryResumeId;
    private String categoryName;
  }
}
