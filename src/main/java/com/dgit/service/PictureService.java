package com.dgit.service;

import java.util.List;

import com.dgit.domain.PictureVO;

public interface PictureService {
	public void upload(PictureVO vo);
	public List<PictureVO> selectById(String id);
	public void delete(String fullname);
}
