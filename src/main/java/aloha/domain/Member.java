package aloha.domain;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class Member {
	private int userNo;
	private String userId;
	private String userPw;
	private String userName;
	private Boolean enable;
	private Date regDate;
	private Date updDate;
	
	private List<MemberAuth> authList;
}
