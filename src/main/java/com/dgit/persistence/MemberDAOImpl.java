package com.dgit.persistence;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dgit.domain.MemberVO;

@Repository
public class MemberDAOImpl implements MemberDAO{

	private static final String NAMESPACE = "com.dgit.mapper.MemberMapper.";
	@Autowired private SqlSession session;
	
	@Override
	public void insertMember(MemberVO vo) {
		session.insert(NAMESPACE + "insertMember", vo);
	}

	@Override
	public MemberVO selectById(String id) {
		MemberVO vo = session.selectOne(NAMESPACE + "selectById", id);
		return vo;
	}

}
