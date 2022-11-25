package site.metacoding.humancloud.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import site.metacoding.humancloud.domain.user.User;
import site.metacoding.humancloud.dto.user.UserRespDto.UserFindById;

public class UserReqDto {

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @Setter
    public static class JoinReqDto {
        private String username;
        private String password;
        private String name;
        private String email;
        private String phoneNumber;

        public User toEntity() {
            return User.builder()
                    .username(this.username)
                    .password(this.password)
                    .name(this.name)
                    .email(this.email)
                    .phoneNumber(this.phoneNumber)
                    .build();
        }
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Getter
    @Setter
    public static class UserUpdateReqDto {
        private Integer userId;
        private String username;
        private String password;
        private String name;
        private String email;
        private String phoneNumber;

        public User toEntity(UserFindById userFindById) {
            return User.builder()
                    .userId(userFindById.getUserId())
                    .username(userFindById.getUsername())
                    .password(this.password)
                    .name(this.name)
                    .email(this.email)
                    .phoneNumber(this.phoneNumber)
                    .build();
        }
    }
}
