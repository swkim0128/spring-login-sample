package aloha.service;

import java.util.List;

import aloha.domain.Member;

public interface MemberService {
	public void register(Member member) throws Exception;
	
	public List<Member> list() throws Exception;
	
	public Member read(String userId) throws Exception;
}
