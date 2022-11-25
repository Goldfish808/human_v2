package site.metacoding.humancloud.dto.recruit;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import site.metacoding.humancloud.domain.category.Category;
import site.metacoding.humancloud.domain.resume.Resume;
import site.metacoding.humancloud.dto.recruit.RecruitReqDto.RecruitSaveReqDto;
import site.metacoding.humancloud.dto.recruit.RecruitReqDto.RecruitUpdateReqDto;

@Getter
@Setter
@NoArgsConstructor
public class RecruitRespDto {
    private Integer recruitId;
    private String recruitTitle;
    private String recruitCareer;
    private Integer recruitSalary;
    private String recruitLocation;
    private Integer recruitReadCount;
    private String recruitContent;
    private Integer recruitCompanyId;
    private String recruitDeadline;

    private List<String> recruitCategoryList;

    public RecruitRespDto(RecruitSaveReqDto recruitSaveReqDto) {
        this.recruitId = recruitSaveReqDto.getRecruitId();
        this.recruitTitle = recruitSaveReqDto.getRecruitTitle();
        this.recruitCareer = recruitSaveReqDto.getRecruitCareer();
        this.recruitSalary = recruitSaveReqDto.getRecruitSalary();
        this.recruitLocation = recruitSaveReqDto.getRecruitLocation();
        this.recruitReadCount = recruitSaveReqDto.getRecruitReadCount();
        this.recruitContent = recruitSaveReqDto.getRecruitContent();
        this.recruitCompanyId = recruitSaveReqDto.getRecruitCompanyId();
        this.recruitDeadline = recruitSaveReqDto.getRecruitDeadline();
        this.recruitCategoryList = recruitSaveReqDto.getRecruitCategoryList();
    }

    public RecruitRespDto(RecruitUpdateReqDto recruitSaveReqDto) {
        this.recruitId = recruitSaveReqDto.getRecruitId();
        this.recruitTitle = recruitSaveReqDto.getRecruitTitle();
        this.recruitCareer = recruitSaveReqDto.getRecruitCareer();
        this.recruitSalary = recruitSaveReqDto.getRecruitSalary();
        this.recruitLocation = recruitSaveReqDto.getRecruitLocation();
        this.recruitReadCount = recruitSaveReqDto.getRecruitReadCount();
        this.recruitContent = recruitSaveReqDto.getRecruitContent();
        this.recruitCompanyId = recruitSaveReqDto.getRecruitCompanyId();
        this.recruitDeadline = recruitSaveReqDto.getRecruitDeadline();
        this.recruitCategoryList = recruitSaveReqDto.getRecruitCategoryList();
    }

    @Getter
    @Setter
    public static class RecruitDetailRespDto {
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

        private Integer companyId;
        private String companyName;
        private String companyEmail;
        private String companyPhoneNumber;
        private String companyAddress;
        private String companyLogo;

        private List<Resume> resume;
        private List<Category> category;
        private List<RecruitListByCompanyIdRespDto> recruitListByCompanyId;

        public void setRecruitCreatedAt(Timestamp recruitCreatedAt) {
            SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd");
            String t = form.format(recruitCreatedAt);
            this.recruitStartDay = t;
        }

    }

    @Getter
    @Setter
    public static class RecruitListByCompanyIdRespDto {
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

        public void setRecruitCreatedAt(Timestamp recruitCreatedAt) {
            SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd");
            String t = form.format(recruitCreatedAt);
            this.recruitStartDay = t;
        }

    }

    @Getter
    @Setter
    public static class CompanyRecruitDtoRespDto {
        private Integer companyId;
        private String companyUsername;
        private String companyPassword;
        private String companyName;
        private String companyEmail;
        private String companyPhoneNumber;
        private String companyAddress;
        private String companyLogo;
        private Timestamp companyCreatedAt;

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

        public void setRecruitCreatedAt(Timestamp recruitCreatedAt) {
            SimpleDateFormat form = new SimpleDateFormat("yyyy-MM-dd");
            String t = form.format(recruitCreatedAt);
            this.recruitStartDay = t;
        }

    }

}
