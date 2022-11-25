package site.metacoding.humancloud.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import site.metacoding.humancloud.domain.company.Company;
import site.metacoding.humancloud.domain.company.CompanyDao;
import site.metacoding.humancloud.domain.recruit.RecruitDao;
import site.metacoding.humancloud.domain.resume.Resume;
import site.metacoding.humancloud.domain.resume.ResumeDao;
import site.metacoding.humancloud.domain.subscribe.SubscribeDao;
import site.metacoding.humancloud.domain.user.UserDao;
import site.metacoding.humancloud.dto.PagingDto;
import site.metacoding.humancloud.dto.auth.UserFindByAllUsernameDto;
import site.metacoding.humancloud.dto.company.CompanyReqDto.CompanyJoinReqDto;
import site.metacoding.humancloud.dto.company.CompanyReqDto.CompanyUpdateReqDto;
import site.metacoding.humancloud.dto.company.CompanyRespDto.CompanyDetailRespDto;
import site.metacoding.humancloud.dto.company.CompanyRespDto.CompanyFindAllRespDto;
import site.metacoding.humancloud.dto.company.CompanyRespDto.CompanyFindById;
import site.metacoding.humancloud.dto.company.CompanyRespDto.CompanyJoinRespDto;
import site.metacoding.humancloud.dto.company.CompanyRespDto.CompanyMypageRespDto;
import site.metacoding.humancloud.dto.company.CompanyRespDto.CompanyMypageRespDto.CompanyRecruitDto;
import site.metacoding.humancloud.dto.company.CompanyRespDto.CompanyUpdateRespDto;
import site.metacoding.humancloud.dto.recruit.RecruitRespDto.RecruitListByCompanyIdRespDto;
import site.metacoding.humancloud.util.SHA256;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompanyService {

	private final CompanyDao companyDao;
	private final SubscribeDao subscribeDao;
	private final RecruitDao recruitDao;
	private final ResumeDao resumeDao;
	private final SHA256 sha256;
	private final UserDao userDao;

	// 기업 회원 등록
	@Transactional
	public CompanyJoinRespDto 기업회원등록(MultipartFile file, CompanyJoinReqDto companyJoinReqDto) throws Exception {
		Optional<UserFindByAllUsernameDto> usernameDto = userDao
				.findAllUsername(companyJoinReqDto.getCompanyUsername());
		if (usernameDto.isPresent()) {
			throw new RuntimeException("중복된 아이디입니다.");
		}

		int pos = file.getOriginalFilename().lastIndexOf(".");
		String extension = file.getOriginalFilename().substring(pos + 1);
		String filePath = "C:\\temp\\img\\";
		String logoSaveName = UUID.randomUUID().toString();
		String logo = logoSaveName + "." + extension;

		File makeFileFolder = new File(filePath);
		if (!makeFileFolder.exists()) {
			if (!makeFileFolder.mkdir()) {
				throw new Exception("File.mkdir():Fail.");
			}
		}

		File dest = new File(filePath, logo);
		try {
			Files.copy(file.getInputStream(), dest.toPath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		String encPassword = sha256.encrypt(companyJoinReqDto.getCompanyPassword());
		companyJoinReqDto.setCompanyPassword(encPassword);
		Company company = companyJoinReqDto.toEntity(logo);
		companyDao.save(company);
		log.debug("디버그 : " + company.getCompanyId());
		Optional<CompanyFindById> companyOP = companyDao.findById(company.getCompanyId());
		if (companyOP.isEmpty()) {
			throw new RuntimeException("기업회원이 등록되지 않았습니다.");
		}
		CompanyJoinRespDto companyJoinRespDto = new CompanyJoinRespDto(companyOP.get());
		companyJoinRespDto.toPhoneNumber(companyJoinRespDto.getCompanyPhoneNumber());
		return companyJoinRespDto;
	}

	// 기업 정보 상세보기
	@Transactional(readOnly = true)
	public CompanyDetailRespDto 기업정보상세보기(Integer userId, Integer companyId) {
		Optional<CompanyFindById> companyOP = companyDao.findById(companyId);
		boolean isSub = false;
		if (subscribeDao.findById(userId, companyId) != null) {
			isSub = true;
		}
		CompanyDetailRespDto companyPS = new CompanyDetailRespDto(companyOP.get(), isSub);
		companyPS.toPhoneNumber(companyOP.get().getCompanyPhoneNumber());
		return companyPS;
	}

	// 기업 리스트 보기
	@Transactional(readOnly = true)
	public CompanyFindAllRespDto 기업리스트보기(Integer page) {
		if (page == null) {
			page = 0;
		}
		int startNum = page * 20;
		PagingDto paging = companyDao.paging(page);
		paging.dopaging();
		CompanyFindAllRespDto companyFindAllRespDto = new CompanyFindAllRespDto();
		companyFindAllRespDto.dopaging(paging);

		companyFindAllRespDto.setCompanyList(companyDao.findAll(startNum));

		return companyFindAllRespDto;
	}

	// 기업정보 수정
	@Transactional
	public CompanyUpdateRespDto 기업정보수정(Integer id, MultipartFile file, CompanyUpdateReqDto companyUpdateReqDto)
			throws Exception {
		// Optional로 영속화 및 null 체크
		Optional<CompanyFindById> companyOP = companyDao.findById(id);
		if (companyOP.isEmpty()) {
			throw new RuntimeException("기업정보가 없습니다.");
		}

		// 이미지 파일 작업
		int pos = file.getOriginalFilename().lastIndexOf(".");
		String extension = file.getOriginalFilename().substring(pos + 1);
		String filePath = "C:\\temp\\img\\";
		String logoSaveName = UUID.randomUUID().toString();
		String logo = logoSaveName + "." + extension;

		File makeFileFolder = new File(filePath);
		if (!makeFileFolder.exists()) {
			if (!makeFileFolder.mkdir()) {
				throw new Exception("File.mkdir():Fail.");
			}
		}

		File dest = new File(filePath, logo);
		try {
			Files.copy(file.getInputStream(), dest.toPath());
		} catch (IOException e) {
			e.printStackTrace();
		}

		companyUpdateReqDto.setCompanyLogo(logo);

		String encPassword = sha256.encrypt(companyUpdateReqDto.getCompanyPassword());
		companyUpdateReqDto.setCompanyPassword(encPassword);

		// 2. updateDto를 companyPS에 업데이트
		Company companyPS = companyOP.get().toEntity();
		companyPS.update(companyUpdateReqDto);

		// 3. update
		companyDao.update(companyPS);
		Optional<CompanyFindById> companyOP2 = companyDao.findById(id);
		return new CompanyUpdateRespDto(companyOP2.get());
	}

	// 기업정보 삭제
	@Transactional(rollbackFor = RuntimeException.class)
	public void 기업정보삭제(Integer id) {
		Optional<CompanyFindById> companyOP = companyDao.findById(id);
		companyOP.orElseThrow(() -> new RuntimeException("존재하지 않는 회원입니다."));

		// 해당 Company의 채용공고 삭제
		Optional<List<RecruitListByCompanyIdRespDto>> recruitListOP = recruitDao.findByCompanyId(id);
		if (recruitListOP.isPresent()) {
			companyDao.deleteById(id);
		}

	}

	@Transactional
	public CompanyMypageRespDto 마이페이지보기(Integer companyId) {

		// MyPageRespDto 생성 및 company정보 생성자 주입
		Optional<CompanyFindById> companyFindByIdOP = companyDao.findById(companyId);
		System.out.println(companyFindByIdOP.get().getCompanyName());

		CompanyMypageRespDto companyMypageRespDto = new CompanyMypageRespDto(companyFindByIdOP.get());

		try {
			List<CompanyRecruitDto> companyRecruitList = recruitDao.findByCompanyId2(companyId);
			companyMypageRespDto.setCompanyRecruitList(companyRecruitList);
		} catch (Exception e) {
			companyMypageRespDto.setCompanyRecruitList(null);
			throw new RuntimeException("채용공고가 없습니다.");
		}

		return companyMypageRespDto;
	}

	public List<Resume> 지원목록보기(Integer companyId) {
		return resumeDao.applyResumeList(companyId);
	}

}
