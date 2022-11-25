package site.metacoding.humancloud.web;

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

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import site.metacoding.humancloud.dto.SessionUser;
import site.metacoding.humancloud.dto.apply.ApplyReqDto.ApplySaveReqDto;

@Sql({ "classpath:ddl.sql", "classpath:dml.sql" })
@Slf4j
@ActiveProfiles("test")
@AutoConfigureMockMvc
@RequiredArgsConstructor
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
public class ApplyControllerTest {

        // header json
        private static final String APPLICATION_JSON = "application/json; charset=utf-8";

        @Autowired
        private ObjectMapper om;
        @Autowired
        private MockMvc mvc;
        private MockHttpSession session;

        @BeforeEach
        public void sessionInit() {
                session = new MockHttpSession();
                session.setAttribute("sessionUser",
                                SessionUser.builder().id(1).username("ssar").role(0).build());
        }

        @Test
        public void 지원화면보기테스트() throws Exception {
                // given
                int userId = 1;

                // when
                ResultActions resultActions = mvc.perform(
                                MockMvcRequestBuilders.get("/s/apply/" + userId)
                                                .accept(APPLICATION_JSON)
                                                .session(session));

                // then
                resultActions.andExpect(MockMvcResultMatchers.status().isOk());
                resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(1));

        }

        @Test
        public void 지원하기테스트() throws Exception {
                // given
                ApplySaveReqDto applySaveReqDto = ApplySaveReqDto.builder()
                                .applyRecruitId(2)
                                .applyResumeId(1)
                                .build();
                String applyData = om.writeValueAsString(applySaveReqDto);

                // when
                ResultActions resultActions = mvc
                                .perform(MockMvcRequestBuilders.post("/s/apply/save").content(applyData)
                                                .contentType(APPLICATION_JSON)
                                                .accept(APPLICATION_JSON)
                                                .session(session));

                // then
                resultActions.andExpect(MockMvcResultMatchers.status().isOk());
                resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(1));
        }

        @Test
        public void 기업공고지원취소테스트() throws Exception {
                // given
                int recruitId = 1;
                int resumeId = 1;

                // when
                ResultActions resultActions = mvc.perform(
                                MockMvcRequestBuilders.delete("/s/apply/delete/" + recruitId + "/" + resumeId)
                                                .session(session));

                // then
                resultActions.andExpect(MockMvcResultMatchers.status().isOk());
                resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(1));

        }

}