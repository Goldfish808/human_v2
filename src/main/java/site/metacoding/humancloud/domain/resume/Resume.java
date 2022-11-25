package site.metacoding.humancloud.domain.resume;

import java.sql.Timestamp;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class Resume {
	private Integer resumeId;
	private String resumeTitle;
	private String resumePhoto;
	private String resumeEducation;
	private String resumeCareer;
	private String resumeLink;
	private Integer resumeReadCount;
	private Integer resumeUserId;
	private Timestamp resumeCreatedAt;

	@Builder
	public Resume(Integer resumeId, String resumeTitle, String resumePhoto, String resumeEducation, String resumeCareer,
			String resumeLink, Integer resumeUserId) {
		this.resumeId = resumeId;
		this.resumeTitle = resumeTitle;
		this.resumePhoto = resumePhoto;
		this.resumeEducation = resumeEducation;
		this.resumeCareer = resumeCareer;
		this.resumeLink = resumeLink;
		this.resumeUserId = resumeUserId;
	}
}
