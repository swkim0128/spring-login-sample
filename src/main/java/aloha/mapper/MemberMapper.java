package aloha.mapper;

import java.util.List;

import aloha.domain.Member;
import aloha.domain.MemberAuth;

public interface MemberMapper {
	public void create(Member member) throws Exception;
	public void createAuth(MemberAuth memberAuth) throws Exception;
	public Member readByUserId(String userId);
	public List<Member> list() throws Exception;
	public Member read(String userId) throws Exception;
}
