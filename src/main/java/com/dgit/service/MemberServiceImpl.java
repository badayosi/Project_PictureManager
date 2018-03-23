package com.dgit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dgit.domain.MemberVO;
import com.dgit.persistence.MemberDAO;

@Repository
public class MemberServiceImpl implements MemberService{

	@Autowired private MemberDAO dao;
	
	@Override
	public void insertMember(MemberVO vo) {
		dao.insertMember(vo);
	}

	@Override
	public MemberVO selectById(String id) {
		MemberVO vo = dao.selectById(id);
		return vo;
	}
	
}
