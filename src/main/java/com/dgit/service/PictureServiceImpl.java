package com.dgit.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.dgit.domain.PictureVO;
import com.dgit.persistence.PictureDAO;

@Repository
public class PictureServiceImpl implements PictureService{

	@Autowired private PictureDAO dao;
	
	@Override
	public void upload(PictureVO vo) {
		dao.upload(vo);
	}

	@Override
	public List<PictureVO> selectById(String id) {
		List<PictureVO> vo = dao.selectById(id);
		return vo;
	}

	@Override
	public void delete(String fullname) {
		dao.delete(fullname);
	}
	
}
