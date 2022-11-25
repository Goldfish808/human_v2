package site.metacoding.humancloud.dto.user;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import site.metacoding.humancloud.domain.resume.Resume;
import site.metacoding.humancloud.domain.user.User;
import site.metacoding.humancloud.dto.CompanyRankingDto;

public class UserRespDto {
    @Builder
    @AllArgsConstructor
    @Getter
    @Setter
    @NoArgsConstructor
    public static class UserFindById {
        private Integer userId;
        private String username;
        private String password;
        private String name;
        private String email;
        private String phoneNumber;
        private Timestamp createdAt;

        public User toEntity() {
            return User.builder()
                    .password(this.password)
                    .name(this.name)
                    .email(this.email)
                    .phoneNumber(this.phoneNumber)
                    .build();
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class UserFindByUsername {
        private Integer Id;
        private String username;
        private String password;
    }

    @Getter
    @Setter
    public static class JoinRespDto {
        private Integer userId;
        private String username;
        private String password;
        private String name;
        private String email;
        private String phoneNumber;
        private Timestamp createdAt;

        public JoinRespDto(User user) {
            this.userId = user.getUserId();
            this.username = user.getUsername();
            this.password = user.getPassword();
            this.name = user.getName();
            this.email = user.getEmail();
            this.phoneNumber = user.getPhoneNumber();
            this.createdAt = new Timestamp(System.currentTimeMillis());
        }
    }

    @Getter
    @Setter
    public static class UserUpdateRespDto {
        private Integer userId;
        private String username;
        private String password;
        private String name;
        private String email;
        private String phoneNumber;

        public UserUpdateRespDto(User user) {
            this.userId = user.getUserId();
            this.username = user.getUsername();
            this.password = user.getPassword();
            this.name = user.getName();
            this.email = user.getEmail();
            this.phoneNumber = user.getPhoneNumber();
        }
    }

    @NoArgsConstructor
    @Getter
    @Setter
    public static class UserMypageRespDto {
        private String email;
        private String phoneNumber;
        private int resumeReadCount;
        private List<SubscribeCompanyDto> companyList;
        private List<UserResumeDto> resumeList;
        private List<CompanyRankingDto> companyRankingDtoList;

        public UserMypageRespDto(UserFindById user) {
            this.email = user.getEmail();
            this.phoneNumber = user.getPhoneNumber();
        }

        public void setUserResume(List<Resume> resumeList) {
            // 형 변환 후 리스트화
            List<UserResumeDto> userResumeDtoList = new ArrayList();
            for (Resume resume : resumeList) {
                userResumeDtoList.add(new UserResumeDto(resume));
            }
            this.resumeList = userResumeDtoList;

            // 이력서 열람횟수
            if (resumeList.size() >= 1)
                this.resumeReadCount = resumeList.size();
        }

        @Getter
        @Setter
        @NoArgsConstructor
        public static class SubscribeCompanyDto {
            private String companyName;
            private String companyLogo;
        }

        @Getter
        @Setter
        public static class UserResumeDto {
            private String resumeTitle;
            private int resumeReadCount;
            private Timestamp resumeCreatedAt;

            public UserResumeDto(Resume resume) {
                this.resumeTitle = resume.getResumeTitle();
                this.resumeReadCount = resume.getResumeReadCount();
                this.resumeCreatedAt = resume.getResumeCreatedAt();
            }
        }
    }
}
