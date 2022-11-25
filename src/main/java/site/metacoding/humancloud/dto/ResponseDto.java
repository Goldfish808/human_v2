package site.metacoding.humancloud.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ResponseDto<T> {
	private Integer code; // 1 성공, -1 실패
	private String msg; // 각각의 상황에 따른 메세지
	private T data; // 데이터가 필요하면 돌려주고, 아니면 null
}
