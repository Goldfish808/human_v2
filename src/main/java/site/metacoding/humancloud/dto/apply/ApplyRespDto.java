package site.metacoding.humancloud.dto.apply;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import site.metacoding.humancloud.domain.resume.Resume;
import site.metacoding.humancloud.dto.apply.ApplyReqDto.ApplySaveReqDto;

public class ApplyRespDto {
    @Getter
    @Setter
    @NoArgsConstructor
    public static class ApplyListRespDto {
        private String resumeTitle;
        private Timestamp resumeCreateAt;

        public ApplyListRespDto(Resume resume) {
            this.resumeTitle = resume.getResumeTitle();
            this.resumeCreateAt = resume.getResumeCreatedAt();
        }

        public List<ApplyListRespDto> toList(List<Resume> resumes) {
            List<ApplyListRespDto> list = new ArrayList<>();
            for (Resume data : resumes) {
                list.add(new ApplyListRespDto(data));
            }
            return list;
        }
    }

    @Getter
    @Setter
    public static class ApplySaveRespDto {
        private Integer applyId;
        private Integer applyRecruitId;
        private Integer applyResumeId;
        private Timestamp applyCreatedAt;

        public ApplySaveRespDto(ApplySaveReqDto applySaveReqDto) {
            this.applyId = applySaveReqDto.getApplyId();
            this.applyRecruitId = applySaveReqDto.getApplyRecruitId();
            this.applyResumeId = applySaveReqDto.getApplyResumeId();
            this.applyCreatedAt = new Timestamp(System.currentTimeMillis());
        }
    }
}
