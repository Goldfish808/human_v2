package site.metacoding.humancloud.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyRankingDto {
    private Integer ranking;
    private Integer likes;
    private String name;
    private String logo;
}
