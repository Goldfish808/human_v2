package site.metacoding.humancloud.web;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import site.metacoding.humancloud.dto.ResponseDto;
import site.metacoding.humancloud.dto.user.UserReqDto.JoinReqDto;
import site.metacoding.humancloud.dto.user.UserReqDto.UserUpdateReqDto;
import site.metacoding.humancloud.service.UserService;
import site.metacoding.humancloud.util.annotation.Auth;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @Auth(role = 0)
    @GetMapping("/test")
    public void TestInterceptor() {

    }

    @PostMapping("/join")
    public ResponseDto<?> joinUser(@RequestBody JoinReqDto joinReqDto) {
        return new ResponseDto<>(1, "ok", userService.회원가입(joinReqDto));
    }

    @Auth(role = 0)
    @PutMapping("/s/user/{id}")
    public ResponseDto<?> update(@PathVariable Integer id,
            @RequestBody UserUpdateReqDto userUpdateReqDto) {
        return new ResponseDto<>(1, "ok", userService.회원업데이트(id, userUpdateReqDto));
    }

    @Auth(role = 0)
    @DeleteMapping("/s/user/{id}")
    public ResponseDto<?> delete(@PathVariable Integer id) {
        userService.회원탈퇴(id);
        return new ResponseDto<>(1, "ok", null);
    }

    @Auth(role = 0)
    @GetMapping("/s/user/mypage/{id}")
    public ResponseDto<?> viewUserMypage(@PathVariable Integer id) {
        return new ResponseDto<>(1, "ok", userService.마이페이지보기(id));
    }

}
