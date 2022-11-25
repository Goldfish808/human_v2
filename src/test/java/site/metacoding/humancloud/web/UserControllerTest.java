package site.metacoding.humancloud.web;

import org.assertj.core.api.Assertions;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import site.metacoding.humancloud.domain.user.UserDao;
import site.metacoding.humancloud.dto.SessionUser;
import site.metacoding.humancloud.dto.user.UserReqDto.JoinReqDto;
import site.metacoding.humancloud.dto.user.UserReqDto.UserUpdateReqDto;

@Sql({ "classpath:ddl.sql", "classpath:dml.sql" })
@Slf4j
@ActiveProfiles("test")
@AutoConfigureMockMvc
@RequiredArgsConstructor
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
public class UserControllerTest {

        // header json
        private static final String APPLICATION_JSON = "application/json; charset=utf-8";

        @Autowired
        private ObjectMapper om;
        @Autowired
        private MockMvc mvc;
        private MockHttpSession session;
        @Autowired
        private UserDao userDao;
        @Autowired
        private UserController userController;

        @BeforeEach
        public void sessionInit() {
                session = new MockHttpSession();
                session.setAttribute("sessionUser",
                                SessionUser.builder().id(1).username("ssar").role(0).build());
        }

        @Test
        public void 회원가입dao테스트() throws Exception {
                // given
                JoinReqDto joinReqDto = JoinReqDto.builder().username("test").password("123").name("테스트")
                                .email("heo@test.c")
                                .phoneNumber("01023233434").build();

                // when
                int result = userDao.save(joinReqDto.toEntity());

                // then
                Assertions.assertThat(result).isEqualTo(1);

        }

        @Test
        public void 회원가입테스트() throws Exception {
                // given
                JoinReqDto joinReqDto = JoinReqDto.builder().username("test").password("123").name("테스트")
                                .email("heo@test.c")
                                .phoneNumber("01023233434").build();
                log.debug("디버그 : " + joinReqDto.getUsername());
                String joinData = om.writeValueAsString(joinReqDto);

                // when
                ResultActions resultActions = mvc
                                .perform(MockMvcRequestBuilders.post("/join").content(joinData)
                                                .contentType("application/json; charset=utf-8")
                                                .accept("application/json; charset=utf-8"));

                // then
                MvcResult mvcResult = resultActions.andReturn();

                log.debug("디버그 : " + mvcResult);

                resultActions.andExpect(MockMvcResultMatchers.status().isOk());
                resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.data.username").value("test"));
        }

        @Test
        public void 마이페이지보기() throws Exception {
                // given
                Integer userId = 1;

                // when
                ResultActions resultActions = mvc.perform(
                                MockMvcRequestBuilders.get("/s/user/mypage/" + userId)
                                                .accept(APPLICATION_JSON)
                                                .session(session));

                // then
                resultActions.andExpect(MockMvcResultMatchers.status().isOk());
                resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(1));
                resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.data.phoneNumber").value("01011112222"));

                log.debug("디버그 : " + resultActions);

        }

        @Test
        public void 회원업데이트() throws Exception {
                // given
                UserUpdateReqDto updateReqDto = UserUpdateReqDto.builder().userId(1).username("ssar").password("123")
                                .name("ssar").email("heo@name.com").phoneNumber("010999977777").build();

                String body = om.writeValueAsString(updateReqDto);

                int userId = 1;
                // when
                ResultActions resultActions = mvc.perform(
                                MockMvcRequestBuilders.put("/s/user/" + userId)
                                                .content(body).contentType(APPLICATION_JSON)
                                                .accept(APPLICATION_JSON).session(session));

                // then
                resultActions.andExpect(MockMvcResultMatchers.status().isOk());
                resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(1));
        }

        @Test
        public void 회원탈퇴() throws Exception {
                // given
                int userId = 1;

                // when
                ResultActions resultActions = mvc.perform(
                                MockMvcRequestBuilders.delete("/s/user/" + userId)
                                                .session(session));

                // then
                resultActions.andExpect(MockMvcResultMatchers.status().isOk());
                resultActions.andExpect(MockMvcResultMatchers.jsonPath("$.code").value(1));

        }

}