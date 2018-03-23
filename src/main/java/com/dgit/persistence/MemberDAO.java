package com.dgit.persistence;

import com.dgit.domain.MemberVO;

public interface MemberDAO {
	public void insertMember(MemberVO vo);
	public MemberVO selectById(String id);
}