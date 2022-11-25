package site.metacoding.humancloud.dto.subscribe;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import site.metacoding.humancloud.dto.subscribe.SubscribeReqDto.SubscribeSaveReqDto;

@NoArgsConstructor
public class SubscribeRespDto {

    @Getter
    @Setter
    public static class SubscribeSaveRespDto {
        private Integer subscribeUserId;
        private Integer subscribeCompanyId;
        private Timestamp subscribeCreatedAt;

        public SubscribeSaveRespDto(SubscribeSaveReqDto subscribeSaveReqDto) {
            this.subscribeUserId = subscribeSaveReqDto.getSubscribeUserId();
            this.subscribeCompanyId = subscribeSaveReqDto.getSubscribeCompanyId();
            this.subscribeCreatedAt = new Timestamp(System.currentTimeMillis());
        }

    }

}
