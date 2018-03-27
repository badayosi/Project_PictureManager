package com.dgit.persistence;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dgit.domain.PictureVO;

@Repository
public class PictureDAOImpl implements PictureDAO{

	private static final String NAMESPACE = "com.dgit.mapper.PictureMapper.";
	@Autowired private SqlSession session;
	
	@Override
	public void upload(PictureVO vo) {
		session.insert(NAMESPACE + "upload", vo);
	}

	@Override
	public List<PictureVO> selectById(String id) {
		List<PictureVO> vo = session.selectList(NAMESPACE + "selectById", id);
		return vo;
	}

	@Override
	public void delete(String fullname) {
		session.delete(NAMESPACE + "delete", fullname);
	}
}
