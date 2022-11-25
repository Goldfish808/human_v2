package site.metacoding.humancloud.dto.subscribe;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import site.metacoding.humancloud.domain.subscribe.Subscribe;

public class SubscribeReqDto {

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Getter
    @Setter
    public static class SubscribeSaveReqDto {
        private Integer subscribeUserId;
        private Integer subscribeCompanyId;

        public Subscribe toEntity() {
            return Subscribe.builder()
                    .subscribeCompanyId(this.subscribeCompanyId)
                    .subscribeUserId(this.subscribeUserId)
                    .build();
        }
    }

}
