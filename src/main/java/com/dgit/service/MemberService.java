package com.dgit.service;

import com.dgit.domain.MemberVO;

public interface MemberService {
	public void insertMember(MemberVO vo);
	public MemberVO selectById(String id);
}
