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
import site.metacoding.humancloud.dto.subscribe.SubscribeReqDto.SubscribeSaveReqDto;

@Sql({ "classpath:ddl.sql", "classpath:dml.sql" })
@Slf4j
@ActiveProfiles("test")
@AutoConfigureMockMvc
@RequiredArgsConstructor
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
public class SubscribeControllerTest {

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
        public void 구독하기테스트() throws Exception {
                // given

                SubscribeSaveReqDto subscribeSaveReqDto = SubscribeSaveReqDto.builder()
                                .subscribeCompanyId(2)
                                .subscribeUserId(1)
                                .build();
                String subscribeData = om.writeValueAsString(subscribeSaveReqDto);

                // when
                ResultActions resultActions = mvc
                                .perform(MockMvcRequestBuilders.post("/s/subscribe")
                                                .content(subscribeData)
                                                .contentType(APPLICATION_JSON)
                                                .accept(APPLICATION_JSON)
                                                .session(session));

                // then
                resultActions.andExpect(MockMvcResultMatchers.status().isOk());
                resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(1));

        }

        @Test
        public void 구독취소하기테스트() throws Exception {
                // given
                Integer userId = 1;
                Integer companyId = 1;

                // when
                ResultActions resultActions = mvc.perform(
                                MockMvcRequestBuilders.delete("/s/subscribe/" + userId + "/" + companyId)
                                                .session(session));

                // then
                resultActions.andExpect(MockMvcResultMatchers.status().isOk());
                resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(1));
        }

}