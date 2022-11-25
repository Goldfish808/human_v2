package site.metacoding.humancloud.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import site.metacoding.humancloud.domain.resume.Resume;
import site.metacoding.humancloud.domain.resume.ResumeDao;
import site.metacoding.humancloud.domain.subscribe.SubscribeDao;
import site.metacoding.humancloud.domain.user.User;
import site.metacoding.humancloud.domain.user.UserDao;
import site.metacoding.humancloud.dto.auth.UserFindByAllUsernameDto;
import site.metacoding.humancloud.dto.user.UserReqDto.JoinReqDto;
import site.metacoding.humancloud.dto.user.UserReqDto.UserUpdateReqDto;
import site.metacoding.humancloud.dto.user.UserRespDto.JoinRespDto;
import site.metacoding.humancloud.dto.user.UserRespDto.UserFindById;
import site.metacoding.humancloud.dto.user.UserRespDto.UserMypageRespDto;
import site.metacoding.humancloud.dto.user.UserRespDto.UserUpdateRespDto;
import site.metacoding.humancloud.util.SHA256;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserDao userDao;
    private final ResumeDao resumeDao;
    private final SubscribeDao subscribeDao;
    private final SHA256 sha256;

    public JoinRespDto 회원가입(JoinReqDto joinReqDto) {
        Optional<UserFindByAllUsernameDto> userPS = userDao.findAllUsername(joinReqDto.getUsername());
        if (userPS.isPresent()) {
            throw new RuntimeException("중복된 아이디 입니다");
        }

        String encPassword = sha256.encrypt(joinReqDto.getPassword());
        joinReqDto.setPassword(encPassword);

        User user = joinReqDto.toEntity();
        userDao.save(user);
        return new JoinRespDto(user);
    }

    public UserUpdateRespDto 회원업데이트(Integer id, UserUpdateReqDto userUpdateReqDto) {
        Optional<UserFindById> userPS = userDao.findById(id);
        userPS.orElseThrow(() -> new RuntimeException("잘못된 아이디값입니다."));

        User user = userUpdateReqDto.toEntity(userPS.get());
        userDao.update(user);

        return new UserUpdateRespDto(user);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void 회원탈퇴(Integer id) {
        Optional<UserFindById> userPS = userDao.findById(id);
        userPS.orElseThrow(() -> new RuntimeException("잘못된 아이디값입니다."));

        // 해당 유저의 이력서 삭제
        List<Resume> resumes = resumeDao.findByUserId(id);
        if (resumes != null) {
            resumeDao.deleteByUserId(id);
        }

        userDao.deleteById(id);
    }

    @Transactional
    public UserMypageRespDto 마이페이지보기(Integer id) {
        // 리스폰스 생성 및 user정보
        UserMypageRespDto userMypageRespDto = new UserMypageRespDto(유저정보보기(id));
        // 구독 기업 목록
        userMypageRespDto.setCompanyList(subscribeDao.findCompanyByUserId(id));
        // 작성한 이력서 목록, 이력서 열람 횟수
        try {
            userMypageRespDto.setUserResume(resumeDao.findByUserId(id));
        } catch (NullPointerException e) {
            throw new NullPointerException("작성한 이력서가 없습니다.");
        }
        // 추천 기업 목록
        userMypageRespDto.setCompanyRankingDtoList(userDao.findByRank());

        return userMypageRespDto;
    }

    // 서비스 내에서 사용하는 메서드
    public UserFindById 유저정보보기(Integer userId) {
        Optional<UserFindById> userPS = userDao.findById(userId);
        userPS.orElseThrow(() -> new RuntimeException("잘못된 아이디값입니다."));
        return userPS.get();
    }

}
