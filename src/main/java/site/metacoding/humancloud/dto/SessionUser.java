package site.metacoding.humancloud.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import site.metacoding.humancloud.dto.auth.UserFindByAllUsernameDto;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class SessionUser {
    private Integer id;
    private String username;
    private Integer role; // 0 = user, 1 = company

    public SessionUser(UserFindByAllUsernameDto userFindByAllUsernameDto) {
        this.id = userFindByAllUsernameDto.getId();
        this.username = userFindByAllUsernameDto.getUsername();
        this.role = userFindByAllUsernameDto.getRole();
    }
}
