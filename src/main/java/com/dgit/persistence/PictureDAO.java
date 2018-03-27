package com.dgit.persistence;

import java.util.List;

import com.dgit.domain.PictureVO;

public interface PictureDAO {
	public void upload(PictureVO vo);
	public List<PictureVO> selectById(String id);
	public void delete(String fullname);
}