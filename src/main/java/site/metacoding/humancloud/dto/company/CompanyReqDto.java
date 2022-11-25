package site.metacoding.humancloud.dto.company;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import site.metacoding.humancloud.domain.company.Company;

public class CompanyReqDto {
    @Builder
    @NoArgsConstructor
    @Setter
    @Getter
    @AllArgsConstructor
    public static class CompanyJoinReqDto {
        private Integer companyId;
        private String companyUsername;
        private String companyPassword;
        private String companyName;
        private String companyEmail;
        private String companyPhoneNumber;
        private String companyAddress;

        public Company toEntity(String companyLogo) {
            return Company.builder()
                    .companyId(companyId)
                    .companyUsername(companyUsername)
                    .companyPassword(companyPassword)
                    .companyName(companyName)
                    .companyEmail(companyEmail)
                    .companyPhoneNumber(companyPhoneNumber)
                    .companyAddress(companyAddress)
                    .companyLogo(companyLogo)
                    .build();
        }
    }

    @Setter
    @Getter
    public static class CompanyLoginReqDto {
        private String companyUsername;
        private String companyPassword;

        public Company toEntity() {
            return Company.builder()
                    .companyUsername(companyUsername)
                    .companyPassword(companyPassword)
                    .build();
        }
    }

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Setter
    @Getter
    public static class CompanyUpdateReqDto {
        private String companyPassword;
        private String companyName;
        private String companyEmail;
        private String companyPhoneNumber;
        private String companyAddress;
        private String companyLogo;

        public Company toEntity(String companyLogo) {
            return Company.builder()
                    .companyPassword(companyPassword)
                    .companyName(companyName)
                    .companyEmail(companyEmail)
                    .companyPhoneNumber(companyPhoneNumber)
                    .companyAddress(companyAddress)
                    .companyLogo(companyLogo)
                    .build();
        }
    }
}
