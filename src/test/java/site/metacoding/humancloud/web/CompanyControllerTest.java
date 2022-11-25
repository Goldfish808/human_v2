package site.metacoding.humancloud.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import site.metacoding.humancloud.domain.company.CompanyDao;
import site.metacoding.humancloud.dto.SessionUser;
import site.metacoding.humancloud.dto.company.CompanyReqDto.CompanyJoinReqDto;
import site.metacoding.humancloud.dto.company.CompanyReqDto.CompanyUpdateReqDto;
import site.metacoding.humancloud.util.SHA256;

@Sql({ "classpath:ddl.sql", "classpath:dml.sql" })
@Slf4j
@ActiveProfiles("test")
@AutoConfigureMockMvc
@RequiredArgsConstructor
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
public class CompanyControllerTest {

        // header json
        private static final String APPLICATION_JSON = "application/json; charset=utf-8";

        @Autowired
        private ObjectMapper om;
        @Autowired
        private MockMvc mvc;
        private MockHttpSession session;
        @Autowired
        private CompanyDao companyDao;
        @Autowired
        private CompanyController companyController;
        @Autowired
        private SHA256 sha256;

        @BeforeEach
        public void sessionInit() {
                session = new MockHttpSession();
                session.setAttribute("sessionUser",
                                SessionUser.builder().id(1).username("adt").role(1).build());
        }

        @Test
        public void 기업회원가입테스트() throws Exception {
                // given
                String uploadFile = "현대.png";

                int pos = uploadFile.lastIndexOf(".");
                String extension = uploadFile.substring(pos + 1);
                String filePath = "C:\\temp\\img\\";
                String logoSaveName = UUID.randomUUID().toString();
                String logo = logoSaveName + "." + extension;

                // 요청 dto 객체 멀티파트로 변환
                CompanyJoinReqDto companyJoinReqDtoData = CompanyJoinReqDto.builder()
                                .companyId(1)
                                .companyUsername("test")
                                .companyPassword("1234")
                                .companyName("삼성")
                                .companyEmail("test@nate.com")
                                .companyPhoneNumber("01011113333")
                                .companyAddress("양정동")
                                .build();
                String body = om.writeValueAsString(companyJoinReqDtoData);
                MockMultipartFile companyJoinReqDto = new MockMultipartFile("companyJoinReqDto",
                                "companyJoinReqDtoData",
                                "application/json", body.getBytes(StandardCharsets.UTF_8));

                // 요청 파일 멀티파트로 변환
                MockMultipartFile file = new MockMultipartFile("file", "현대.png", "form-data",
                                filePath.getBytes(StandardCharsets.UTF_8));

                // when
                ResultActions resultActions = mvc.perform(
                                multipart("/company/join")
                                                .file(file)
                                                .file(companyJoinReqDto));

                // then
                resultActions.andExpect(MockMvcResultMatchers.status().isOk());
                resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(1));
        }

        @Test
        public void 기업회원수정테스트() throws Exception {
                // given
                Integer id = 1;

                String uploadFile = "삼성.png";

                int pos = uploadFile.lastIndexOf(".");
                String extension = uploadFile.substring(pos + 1);
                String filePath = "C:\\temp\\img\\";
                String logoSaveName = UUID.randomUUID().toString();
                String logo = logoSaveName + "." + extension;

                CompanyUpdateReqDto companyUpdateReqDtoData = CompanyUpdateReqDto.builder()
                                .companyPassword("1234")
                                .companyName("삼성")
                                .companyEmail("ssar@nate.com")
                                .companyPhoneNumber("0277778888")
                                .companyAddress("남천동")
                                .build();

                String body = om.writeValueAsString(companyUpdateReqDtoData);
                MockMultipartFile companyUpdateReqDto = new MockMultipartFile("companyUpdateReqDto",
                                "companyUpdateReqDtoData",
                                "application/json", body.getBytes(StandardCharsets.UTF_8));

                // 요청 파일 멀티파트로 변환
                MockMultipartFile file = new MockMultipartFile("file", "현대.png", "form-data",
                                filePath.getBytes(StandardCharsets.UTF_8));

                // when
                ResultActions resultActions = mvc.perform(
                                multipart("/s/company/" + id)
                                                .file(file)
                                                .file(companyUpdateReqDto)
                                                .with(req -> {
                                                        req.setMethod("PUT");
                                                        return req;
                                                })
                                                .session(session));
                // then
                MvcResult mvcResult = resultActions.andReturn();
                System.out.println("디버그 : " + mvcResult.getResponse().getContentAsString());
                resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(1));
                resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.data.companyName").value("삼성"));
        }

        @Test
        public void 기업회원삭제테스트() throws Exception {
                // given
                int companyId = 1;

                // when
                ResultActions resultActions = mvc.perform(
                                MockMvcRequestBuilders.delete("/s/company/" + companyId)
                                                .session(session));

                // then
                resultActions.andExpect(MockMvcResultMatchers.status().isOk());
                resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(1));
        }

        @Test
        public void 기업리스트보기테스트() throws Exception {
                // given
                Integer page = 0;

                // when
                ResultActions resultActions = mvc.perform(
                                MockMvcRequestBuilders.get("/company?page=" + page)
                                                .accept(APPLICATION_JSON));

                // then
                resultActions.andExpect(MockMvcResultMatchers.status().isOk());
                resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(1));
        }

        @Test
        public void 기업마이페이지테스트() throws Exception {
                // given
                Integer companyId = 1;

                // when
                ResultActions resultActions = mvc.perform(
                                MockMvcRequestBuilders.get("/s/company/mypage/" + companyId)
                                                .accept(APPLICATION_JSON)
                                                .session(session));

                // then
                resultActions.andExpect(MockMvcResultMatchers.status().isOk());
                resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(1));
                resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.data.companyEmail").value("adt@naver.com"));
        }

        @Test
        public void 기업정보상세보기테스트() throws Exception {
                // given
                Integer companyId = 1;
                // Integer userId = 0;

                // when
                ResultActions resultActions = mvc.perform(MockMvcRequestBuilders
                                .get("/company/" + companyId)
                                .accept(APPLICATION_JSON)
                                .session(session));

                // then
                resultActions.andExpect(MockMvcResultMatchers.status().isOk());
                resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(1));
                resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.data.companyName").value("ADT"));
        }

        @Test
        public void 지원이력서목록보기테스트() throws Exception {
                // given
                Integer companyId = 1;

                // when
                ResultActions resultActions = mvc.perform(MockMvcRequestBuilders
                                .get("/s/company/applyList/" + companyId)
                                .accept(APPLICATION_JSON)
                                .session(session));
                // then
                resultActions.andExpect(MockMvcResultMatchers.status().isOk());
                resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(1));

        }

}
