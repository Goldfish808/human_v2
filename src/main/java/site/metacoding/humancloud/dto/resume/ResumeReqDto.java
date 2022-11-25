package site.metacoding.humancloud.dto.resume;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import site.metacoding.humancloud.dto.resume.ResumeRespDto.ResumeFindAllDto;

public class ResumeReqDto {

  @AllArgsConstructor
  @NoArgsConstructor
  @Getter
  @Setter
  public static class ResumeSaveReqDto {
    private Integer resumeId;
    private Integer resumeUserId;
    private String resumeTitle;
    private String resumeEducation;
    private String resumeCareer;
    private String resumePhoto;
    private String resumeLink;
    private List<String> categoryList;
    private MultipartFile file;

    @Builder
    public ResumeSaveReqDto(Integer resumeUserId, String resumeTitle, String resumeEducation,
        String resumeCareer, String resumePhoto, String resumeLink, List<String> categoryList) {
      this.resumeUserId = resumeUserId;
      this.resumeTitle = resumeTitle;
      this.resumeEducation = resumeEducation;
      this.resumeCareer = resumeCareer;
      this.resumePhoto = resumePhoto;
      this.resumeLink = resumeLink;
      this.categoryList = categoryList;
    }

    public void setFile(MultipartFile file) {
      this.file = file;
    }

  }

  @NoArgsConstructor
  @AllArgsConstructor
  @Getter
  @Setter
  public static class ResumeUpdateReqDto {
    private Integer resumeId;
    private Integer resumeUserId;
    private String resumeTitle;
    private String resumeEducation;
    private String resumeCareer;
    private String resumePhoto;
    private String resumeLink;
    private List<String> categoryList;
    private MultipartFile[] file;

    public ResumeUpdateReqDto(Integer resumeId, Integer resumeUserId, String resumeTitle, String resumeEducation,
        String resumeCareer, String resumePhoto, String resumeLink, List<String> categoryList) {
      this.resumeId = resumeId;
      this.resumeUserId = resumeUserId;
      this.resumeTitle = resumeTitle;
      this.resumeEducation = resumeEducation;
      this.resumeCareer = resumeCareer;
      this.resumePhoto = resumePhoto;
      this.resumeLink = resumeLink;
      this.categoryList = categoryList;
    }
  }

  @NoArgsConstructor
  @AllArgsConstructor
  @Getter
  @Setter
  public static class ResumeViewCategoryReqDto {
    private List<ResumeFindAllDto> resumeList;
    private String categoryName;
    private Integer page;
    private Integer startNum;

    public ResumeViewCategoryReqDto(String categoryName, Integer page) {
      this.categoryName = categoryName;
      this.page = page;
    }

  }

  @NoArgsConstructor
  @Getter
  @Setter
  public static class ResumeViewOrderListReqDto {
    private String order;
    private Integer companyId;
    private Integer page;

    public ResumeViewOrderListReqDto(String order, Integer companyId, Integer page) {
      this.order = order;
      this.companyId = companyId;
      this.page = page;
    }

  }
}
