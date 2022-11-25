package site.metacoding.humancloud.dto.recruit;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import site.metacoding.humancloud.domain.recruit.Recruit;

public class RecruitReqDto {

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RecruitSaveReqDto {
        private Integer recruitId; // 카테고리 INSERT 때문에 필요함, Mybatis 에서 INSERT 시 ID 값을 리턴해줌
        private String recruitTitle;
        private String recruitCareer;
        private Integer recruitSalary;
        private String recruitLocation;
        private Integer recruitReadCount;
        private String recruitContent;
        private Integer recruitCompanyId;
        private String recruitDeadline;

        private List<String> recruitCategoryList;

        public Recruit toEntity() {

            return Recruit.builder().recruitTitle(recruitTitle).recruitCareer(recruitCareer)
                    .recruitSalary(recruitSalary).recruitLocation(recruitLocation).recruitReadCount(recruitReadCount)
                    .recruitContent(recruitContent).recruitCompanyId(recruitCompanyId).recruitDeadline(recruitDeadline)
                    .build();
        }
    }

    @Getter
    @Setter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RecruitUpdateReqDto {
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

        public Recruit toEntity() {
            return Recruit.builder().recruitId(recruitId).recruitTitle(recruitTitle).recruitCareer(recruitCareer)
                    .recruitSalary(recruitSalary).recruitLocation(recruitLocation).recruitReadCount(recruitReadCount)
                    .recruitContent(recruitContent).recruitCompanyId(recruitCompanyId).recruitDeadline(recruitDeadline)
                    .build();
        }
    }

}
