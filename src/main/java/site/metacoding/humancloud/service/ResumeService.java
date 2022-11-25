package site.metacoding.humancloud.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import site.metacoding.humancloud.domain.category.Category;
import site.metacoding.humancloud.domain.category.CategoryDao;
import site.metacoding.humancloud.domain.resume.ResumeDao;
import site.metacoding.humancloud.domain.user.UserDao;
import site.metacoding.humancloud.dto.PagingDto;
import site.metacoding.humancloud.dto.category.CategoryRespDto.CategoryFindByResumeId;
import site.metacoding.humancloud.dto.resume.ResumeReqDto.ResumeSaveReqDto;
import site.metacoding.humancloud.dto.resume.ResumeReqDto.ResumeUpdateReqDto;
import site.metacoding.humancloud.dto.resume.ResumeReqDto.ResumeViewCategoryReqDto;
import site.metacoding.humancloud.dto.resume.ResumeReqDto.ResumeViewOrderListReqDto;
import site.metacoding.humancloud.dto.resume.ResumeRespDto.ResumeDetailRespDto;
import site.metacoding.humancloud.dto.resume.ResumeRespDto.ResumeFindAllDto;
import site.metacoding.humancloud.dto.resume.ResumeRespDto.ResumeFindAllRespDto;
import site.metacoding.humancloud.dto.resume.ResumeRespDto.ResumeFindById;
import site.metacoding.humancloud.dto.resume.ResumeRespDto.ResumeOrderByOrderListDto;
import site.metacoding.humancloud.dto.user.UserRespDto.UserFindById;

@Slf4j
@RequiredArgsConstructor
@Service
public class ResumeService {

    private final ResumeDao resumeDao;
    private final CategoryDao categoryDao;
    private final UserDao userDao;

    @Transactional(rollbackFor = RuntimeException.class)
    public void 이력서삭제(Integer resumeId) {
        resumeDao.deleteById(resumeId);
        categoryDao.deleteByResumeId(resumeId);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void 이력서수정(Integer resumeId, MultipartFile file, ResumeUpdateReqDto resumeUpdateReqDto) throws Exception {
        Optional<ResumeFindById> resumeOP = resumeDao.findById(resumeId);

        if (!(resumeOP.isPresent())) {
            throw new RuntimeException("해당 하는 이력서가 없습니다. 이력서 Id : " + resumeId);
        }

        if (resumeOP.get().getResumeUserId() != resumeUpdateReqDto.getResumeUserId()) {
            throw new RuntimeException("해당 유저는 이력서 Id가 " + resumeId + "인 이력서를 수정할 권한이 없습니다.");
        }

        String imgName = insertImg(file);
        resumeUpdateReqDto.setResumePhoto(imgName);

        resumeDao.update(resumeUpdateReqDto);
        categoryDao.deleteByResumeId(resumeId);
        for (String category : resumeUpdateReqDto.getCategoryList()) {
            Category categoryElement = new Category(resumeId, category);
            categoryDao.save(categoryElement);
        }
    }

    @Transactional(rollbackFor = RuntimeException.class)
    public void 이력서저장(MultipartFile file, ResumeSaveReqDto resumeSaveReqDto) throws Exception {

        String imgName = insertImg(file);
        resumeSaveReqDto.setResumePhoto(imgName);

        resumeDao.save(resumeSaveReqDto);
        for (String category : resumeSaveReqDto.getCategoryList()) {
            Category categoryElement = new Category(resumeSaveReqDto.getResumeId(), category);
            categoryDao.save(categoryElement);
        }
    }

    public ResumeDetailRespDto 이력서상세보기(@Param("resumeId") Integer resumeId, @Param("userId") Integer userId) {
        ResumeDetailRespDto resumeDetailRespDto = new ResumeDetailRespDto();
        Optional<UserFindById> userOP = userDao.findById(userId);
        Optional<ResumeFindById> resumeOP = resumeDao.findById(resumeId);
        List<CategoryFindByResumeId> categories = categoryDao.findByResumeId(resumeId);

        if (userOP.isPresent()) {
            resumeDetailRespDto.toUserEntity(userOP.get());
        } else {
            throw new RuntimeException("해당하는 Id를 가진 유저가 없습니다. 유저 ID : " + userId);
        }

        if (resumeOP.isPresent()) {
            resumeDetailRespDto.toResumeEntity(resumeOP.get());
        } else {
            throw new RuntimeException("해당하는 Id를 가진 이력서가 없습니다. 이력서 ID : " + resumeId);
        }

        resumeDetailRespDto.setCategoryList(categories);

        return resumeDetailRespDto;

    }

    // 이력서 목록
    public ResumeFindAllRespDto 이력서목록보기(Integer page) {
        if (page == null) {
            page = 0;
        }
        int startNum = page * 20;
        PagingDto paging = resumeDao.paging(page);
        paging.dopaging();
        ResumeFindAllRespDto resumeFindAllRespDto = new ResumeFindAllRespDto();
        resumeFindAllRespDto.dopaging(paging);

        resumeFindAllRespDto.setResumeList(resumeDao.findAll(startNum));
        resumeFindAllRespDto.setCategoryList(categoryDao.distinctName());

        return resumeFindAllRespDto;
    }

    public ResumeOrderByOrderListDto 분류별이력서목록보기(ResumeViewCategoryReqDto resumeViewCategoryReqDto) {
        Integer page = resumeViewCategoryReqDto.getPage();
        log.debug("디버그 : 카테고리 " + resumeViewCategoryReqDto.getCategoryName());
        log.debug("디버그 : page " + resumeViewCategoryReqDto.getPage());

        if (resumeViewCategoryReqDto.getPage() == null) {
            page = 0;
        }
        int startNum = page * 20;
        PagingDto paging = resumeDao.paging(page);
        paging.dopaging();
        resumeViewCategoryReqDto.setStartNum(startNum);
        ResumeOrderByOrderListDto resumeOrderByOrderListDto = new ResumeOrderByOrderListDto();
        resumeOrderByOrderListDto.dopaging(paging);
        resumeOrderByOrderListDto.setResumeList(resumeDao.findByCategoryName(resumeViewCategoryReqDto));

        return resumeOrderByOrderListDto;
    }

    public ResumeFindAllRespDto 정렬하기(ResumeViewOrderListReqDto resumeViewOrderListReqDto) {
        Integer page = resumeViewOrderListReqDto.getPage();
        if (page == null) {
            page = 0;
        }
        int startNum = page * 20;
        PagingDto paging = resumeDao.paging(page);
        paging.dopaging();

        ResumeFindAllRespDto resumeFindAllRespDto = new ResumeFindAllRespDto();
        resumeFindAllRespDto.dopaging(paging);
        resumeFindAllRespDto.setCategoryList(categoryDao.distinctName());

        if (resumeViewOrderListReqDto.getOrder().equals("recent")) {
            resumeFindAllRespDto.setResumeList(최신순보기(startNum));
        } else if (resumeViewOrderListReqDto.getOrder().equals("career")) {
            resumeFindAllRespDto.setResumeList(경력순보기(startNum));
        } else if (resumeViewOrderListReqDto.getOrder().equals("education")) {
            resumeFindAllRespDto.setResumeList(학력순보기(startNum));
        } else {
            resumeFindAllRespDto.setResumeList(추천순보기(resumeViewOrderListReqDto.getCompanyId(), startNum));
        }
        return resumeFindAllRespDto;
    }

    public List<ResumeFindAllDto> 최신순보기(int startNum) {
        return resumeDao.orderByCreatedAt(startNum);
    }

    public List<ResumeFindAllDto> 경력순보기(int startNum) {
        return resumeDao.orderByCareer(startNum);
    }

    public List<ResumeFindAllDto> 학력순보기(int startNum) {
        return resumeDao.orderByEducation(startNum);
    }

    public List<ResumeFindAllDto> 추천순보기(Integer companyId, Integer startNum) {
        return resumeDao.orderByRecommend(companyId, startNum);
    }

    public void 열람횟수증가(Integer resumeId) {
        resumeDao.updateReadCount(resumeId);
    }

    private String insertImg(MultipartFile file) throws Exception {
        int pos = file.getOriginalFilename().lastIndexOf(".");
        String extension = file.getOriginalFilename().substring(pos + 1);
        String filePath = "C:\\temp\\img\\";
        String imgSaveName = UUID.randomUUID().toString();
        String imgName = imgSaveName + "." + extension;

        File makeFileFolder = new File(filePath);
        if (!makeFileFolder.exists()) {
            if (!makeFileFolder.mkdir()) {
                throw new Exception("File.mkdir():Fail.");
            }
        }

        File dest = new File(filePath, imgName);
        try {
            Files.copy(file.getInputStream(), dest.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imgName;
    }
}
