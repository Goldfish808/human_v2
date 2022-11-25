package site.metacoding.humancloud.web;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import site.metacoding.humancloud.domain.category.Category;
import site.metacoding.humancloud.domain.company.CompanyDao;
import site.metacoding.humancloud.domain.resume.ResumeDao;
import site.metacoding.humancloud.domain.user.User;
import site.metacoding.humancloud.domain.user.UserDao;
import site.metacoding.humancloud.dto.SessionUser;
import site.metacoding.humancloud.dto.recruit.RecruitReqDto.RecruitSaveReqDto;
import site.metacoding.humancloud.dto.recruit.RecruitReqDto.RecruitUpdateReqDto;

@Sql({ "classpath:ddl.sql", "classpath:dml.sql" })
@Slf4j
@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
public class RecruitControllerTest {

        // header json
        private static final String APPLICATION_JSON = "application/json; charset=utf-8";

        @Autowired
        private ObjectMapper om;
        @Autowired
        private MockMvc mvc;
        @Autowired
        private UserDao userDao;

        @Autowired
        private CompanyDao companyDao;

        @Autowired
        private MockHttpSession userSession;

        @Autowired
        private MockHttpSession companySession;

        @Autowired
        private ResumeDao resumeDao;
        @Autowired
        private ResumeController resumeController;

        @BeforeEach
        public void userSessionInit() {
                userSession.setAttribute("sessionUser",
                                SessionUser.builder().id(1).username("ssar").role(1).build());
        }

        @BeforeEach
        public void companySessionInit() {
                companySession.setAttribute("sessionUser",
                                SessionUser.builder().id(1).username("adt").role(1).build());
        }

        @Test
        public void 채용공고작성테스트() throws Exception {
                // given
                RecruitSaveReqDto recruitSaveReqDto = RecruitSaveReqDto.builder()
                                .recruitTitle("제제제제제제목")
                                .recruitContent("내내내낸내용")
                                .recruitCareer("1년")
                                .recruitCompanyId(1)
                                .recruitLocation("서울")
                                .recruitReadCount(1)
                                .recruitDeadline("ddd")
                                .recruitSalary(5000)
                                .build();

                List<String> recruitCategoryList = new ArrayList<>();
                recruitCategoryList.add(0, "머머");
                recruitCategoryList.add(1, "공주");

                recruitSaveReqDto.setRecruitCategoryList(recruitCategoryList);
                String body = om.writeValueAsString(recruitSaveReqDto);

                // then
                ResultActions resultActions = mvc.perform(
                                MockMvcRequestBuilders.post("/s/recruit/save").content(body)
                                                .contentType(APPLICATION_JSON)
                                                .accept(APPLICATION_JSON).session(companySession));

                // then
                resultActions.andExpect(MockMvcResultMatchers.status().isOk());
                resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(1));
        }

        @Test
        public void 채용공고수정테스트() throws Exception {
                // given
                Integer recruitId = 1;

                RecruitUpdateReqDto recruitUpdateReqDto = RecruitUpdateReqDto.builder()
                                .recruitTitle("제제제제제제목")
                                .recruitContent("내내내낸내용")
                                .recruitCareer("1년")
                                .recruitCompanyId(1)
                                .recruitLocation("서울")
                                .recruitSalary(5000)
                                .recruitCategoryList(null).build();
                List<String> recruitCategoryList = new ArrayList<>();
                recruitCategoryList.add(0, "머머");
                recruitCategoryList.add(1, "공주");

                recruitUpdateReqDto.setRecruitCategoryList(recruitCategoryList);

                String body = om.writeValueAsString(recruitUpdateReqDto);

                // when
                ResultActions resultActions = mvc.perform(
                                MockMvcRequestBuilders.put("/s/recruit/" + recruitId).content(body)
                                                .contentType(APPLICATION_JSON)
                                                .accept(APPLICATION_JSON).session(companySession));

                // then
                resultActions.andExpect(MockMvcResultMatchers.status().isOk());
                resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(1));
        }

        @Test
        public void 메인페이지테스트() throws Exception {
                // given , 한 페이지에서 볼 목록 개수 10 = 0 개 / 0 = 10 개

                // when
                ResultActions resultActions = mvc.perform(
                                MockMvcRequestBuilders.get("/main")
                                                .accept(APPLICATION_JSON));
                // then
                resultActions.andExpect(MockMvcResultMatchers.status().isOk());
                resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(1));

        }

        @Test
        public void 채용공고상세보기테스트() throws Exception {
                // given
                Integer recruitId = 1;
                Integer userId = 1;

                // when
                ResultActions resultActions = mvc.perform(
                                MockMvcRequestBuilders.get("/recruit/detail/" + recruitId + "/" + userId)
                                                .accept(APPLICATION_JSON));

                // then
                resultActions.andExpect(MockMvcResultMatchers.status().isOk());
                resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(1));
        }

        @Test
        public void 공고삭제하기테스트() throws Exception {
                // given
                Integer recruitId = 1;

                // when
                ResultActions resultActions = mvc.perform(
                                MockMvcRequestBuilders.delete("/s/recruit/delete/" + recruitId)
                                                .session(companySession));

                // then
                resultActions.andExpect(MockMvcResultMatchers.status().isOk());
                resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(1));
                // then
        }

        @Test
        public void 분류별채용공고정렬테스트() throws Exception {
                // given
                Category category = new Category(1, "Java");

                Integer page = 0;

                String body = om.writeValueAsString(category);

                // when
                ResultActions resultActions = mvc.perform(
                                MockMvcRequestBuilders.post(
                                                "/s/resume?page=" + page + "&category=" + category.getCategoryName())
                                                .content(body).contentType(APPLICATION_JSON)
                                                .accept(APPLICATION_JSON).session(companySession));

                // then
                resultActions.andExpect(MockMvcResultMatchers.status().isOk());
                resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(1));
        }

        @Test
        public void 항목별채용공고정렬테스트() throws Exception {
                // given
                String order = "recent";
                User user = User.builder().userId(1).build();

                String body = om.writeValueAsString(user);

                // when
                ResultActions resultActions = mvc.perform(
                                MockMvcRequestBuilders.post(
                                                "/recruit/list?order=" + order)
                                                .content(body).contentType(APPLICATION_JSON)
                                                .accept(APPLICATION_JSON));

                // then
                resultActions.andExpect(MockMvcResultMatchers.status().isOk());
                resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(1));
        }
}