package site.metacoding.humancloud.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import site.metacoding.humancloud.domain.subscribe.SubscribeDao;
import site.metacoding.humancloud.dto.subscribe.SubscribeReqDto.SubscribeSaveReqDto;
import site.metacoding.humancloud.dto.subscribe.SubscribeRespDto.SubscribeSaveRespDto;

@RequiredArgsConstructor
@Service
public class SubscribeService {

    private final SubscribeDao subscribeDao;

    public void 구독취소(Integer userId, Integer companyId) {
        subscribeDao.deleteByUserCompany(userId, companyId);
    }

    public boolean 구독확인(Integer userId, Integer companyId) {
        if (subscribeDao.findById(userId, companyId) == null) {
            return false;
        }
        return true;
    }

    public SubscribeSaveRespDto 구독하기(SubscribeSaveReqDto subscribeSaveReqDto) {
        if (구독확인(subscribeSaveReqDto.getSubscribeUserId(), subscribeSaveReqDto.getSubscribeCompanyId()) == false) {
            subscribeDao.save(subscribeSaveReqDto.toEntity());
            return new SubscribeSaveRespDto(subscribeSaveReqDto);
        }
        return null;
    }
}
