package site.metacoding.humancloud.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PagingDto {
	private Integer totalCount;
	private Integer totalPage;
	private Integer currentPage;
	private boolean isFirst;
	private boolean isLast;

	private Integer startPageNum;
	private Integer lastPageNum;
	private Integer blockPage;
	private Integer blockPageCount;

	public void dopaging() {
		blockPageCount = 5;
		startPageNum = ((currentPage / blockPageCount) * blockPageCount) + 1;
		blockPage = (currentPage / blockPageCount) + 1;
		lastPageNum = ((currentPage / blockPageCount) + 1) * blockPageCount;

		if (totalPage < lastPageNum) {
			lastPageNum = totalPage;
		}
	}

}