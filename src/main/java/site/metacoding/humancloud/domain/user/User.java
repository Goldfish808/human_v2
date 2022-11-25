package site.metacoding.humancloud.domain.user;

import java.sql.Timestamp;
import java.util.regex.Pattern;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class User {

	private Integer userId;
	private String username;
	private String password;
	private String name;
	private String email;
	private String phoneNumber;
	private Timestamp createdAt;

	public void updateToEntity(String password, String name, String email, String phoneNumber) {
		this.password = password;
		this.name = name;
		this.email = email;
		this.phoneNumber = phoneNumber;
	}

	public void toPhoneNumber(String num) {
		this.phoneNumber = num;
	}

	// 전화번호 포매팅
	public void formatPhoneNumber() {
		String fomat = "(\\d{3})(\\d{3,4})(\\d{4})";
		if (Pattern.matches(fomat, this.phoneNumber)) {
			this.phoneNumber = this.phoneNumber.replaceAll(fomat, "$1-$2-$3");
		}
	}
}