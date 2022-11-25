package site.metacoding.humancloud.dto.resume;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import site.metacoding.humancloud.domain.resume.Resume;
import site.metacoding.humancloud.dto.PagingDto;
import site.metacoding.humancloud.dto.category.CategoryRespDto.CategoryFindByResumeId;
import site.metacoding.humancloud.dto.user.UserRespDto.UserFindById;

public class ResumeRespDto {

  @NoArgsConstructor
  @AllArgsConstructor
  @Setter
  @Getter
  public static class ResumeFindAllDto {
    private Integer resumeId;
    private String resumeTitle;
    private String resumePhoto;
    private String resumeEducation;
    private String resumeCareer;
    private String resumeLink;
    private Integer resumeReadCount;
    private Integer resumeUserId;

  }

  @Getter
  public static class ResumeSaveRespDto {
    private Integer resumeUserId;
    private String resumeTitle;
    private String resumeEducation;
    private String resumeCareer;
    private String resumePhoto;
    private String resumeLink;
    private List<String> categoryList;
  }

  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  @Getter
  @Setter
  public static class ResumeFindById {
    private Integer resumeId;
    private Integer resumeUserId;
    private String resumeTitle;
    private String resumeEducation;
    private String resumeCareer;
    private String resumePhoto;
    private String resumeLink;

    public Resume toEntity() {
      return Resume.builder()
          .resumeId(this.resumeId)
          .resumeUserId(this.resumeUserId)
          .resumeTitle(this.resumeTitle)
          .resumeEducation(this.resumeEducation)
          .resumeCareer(this.resumeCareer)
          .resumePhoto(this.resumePhoto)
          .resumeLink(this.resumeLink)
          .build();
    }
  }

  @Setter
  @Getter
  public static class ResumeDetailRespDto {
    private Integer resumeId;
    private Integer resumeUserId;
    private String resumeTitle;
    private String resumeEducation;
    private String resumeCareer;
    private String resumePhoto;
    private String resumeLink;
    private String name;
    private String email;
    private String phoneNumber;
    private List<ResumeCategoryDto> categoryList;

    public void setCategoryList(List<CategoryFindByResumeId> categoryList) {
      List<ResumeCategoryDto> resumeCategoryLists = new ArrayList<>();
      for (CategoryFindByResumeId category : categoryList) {
        resumeCategoryLists.add(new ResumeCategoryDto(category));
      }
      this.categoryList = resumeCategoryLists;
    }

    public UserFindById toUserEntity(UserFindById userFindById) {
      this.name = userFindById.getName();
      this.email = userFindById.getEmail();
      this.phoneNumber = userFindById.getPhoneNumber();
      return userFindById;
    }

    public ResumeFindById toResumeEntity(ResumeFindById resumeFindById) {
      this.resumeId = resumeFindById.getResumeId();
      this.resumeUserId = resumeFindById.getResumeUserId();
      this.resumeTitle = resumeFindById.getResumeTitle();
      this.resumeEducation = resumeFindById.getResumeEducation();
      this.resumeCareer = resumeFindById.getResumeCareer();
      this.resumePhoto = resumeFindById.getResumePhoto();
      this.resumeLink = resumeFindById.getResumeLink();
      return resumeFindById;
    }

  }

  @Setter
  @Getter
  public static class ResumeUpdateRespDto {
    private Integer resumeId;
    private Integer resumeUserId;
    private String resumeTitle;
    private String resumeEducation;
    private String resumeCareer;
    private String resumePhoto;
    private String resumeLink;
    private String name;
    private String email;
    private String phoneNumber;
    private List<ResumeCategoryDto> categoryList;

    public void setCategoryList(List<CategoryFindByResumeId> categoryList) {
      List<ResumeCategoryDto> resumeCategoryLists = new ArrayList<>();
      for (CategoryFindByResumeId category : categoryList) {
        resumeCategoryLists.add(new ResumeCategoryDto(category));
      }
      this.categoryList = resumeCategoryLists;
    }

    public UserFindById toUserEntity(UserFindById userFindById) {
      this.name = userFindById.getName();
      this.email = userFindById.getEmail();
      this.phoneNumber = userFindById.getPhoneNumber();
      return userFindById;
    }

    public ResumeFindById toResumeEntity(ResumeFindById resumeFindById) {
      this.resumeId = resumeFindById.getResumeId();
      this.resumeUserId = resumeFindById.getResumeUserId();
      this.resumeTitle = resumeFindById.getResumeTitle();
      this.resumeEducation = resumeFindById.getResumeEducation();
      this.resumeCareer = resumeFindById.getResumeCareer();
      this.resumePhoto = resumeFindById.getResumePhoto();
      this.resumeLink = resumeFindById.getResumeLink();
      return resumeFindById;
    }

  }

  @Getter
  @Setter
  public static class ResumeFindAllRespDto {
    private List<ResumeFindAllDto> resumeList;
    private List<String> categoryList;

    private Integer startPageNum;
    private Integer lastPageNum;
    private Integer blockPage;
    private Integer blockPageCount;

    public void setCategoryList(List<String> categoryList) {
      List<String> resumeCategoryLists = new ArrayList<>();
      for (String categoryName : categoryList) {
        resumeCategoryLists.add(categoryName);
      }
      this.categoryList = resumeCategoryLists;
    }

    public void dopaging(PagingDto paging) {
      this.startPageNum = paging.getStartPageNum();
      this.lastPageNum = paging.getLastPageNum();
      this.blockPage = paging.getBlockPage();
      this.blockPageCount = paging.getBlockPageCount();
    }
  }

  @Getter
  @Setter
  public static class ResumeCategoryDto {
    private Integer categoryId;
    private Integer categoryResumeId;
    private String categoryName;

    public ResumeCategoryDto(CategoryFindByResumeId category) {
      this.categoryId = category.getCategoryId();
      this.categoryResumeId = category.getCategoryResumeId();
      this.categoryName = category.getCategoryName();
    }
  }

  @Getter
  @Setter
  public static class ResumeOrderByOrderListDto {
    private List<ResumeFindAllDto> resumeList;

    private Integer startPageNum;
    private Integer lastPageNum;
    private Integer blockPage;
    private Integer blockPageCount;

    public void dopaging(PagingDto paging) {
      this.startPageNum = paging.getStartPageNum();
      this.lastPageNum = paging.getLastPageNum();
      this.blockPage = paging.getBlockPage();
      this.blockPageCount = paging.getBlockPageCount();
    }

  }

}
