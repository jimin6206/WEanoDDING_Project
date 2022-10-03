package com.ssafy.api.service;

import com.ssafy.api.request.NoticeRegisterPostReq;
import com.ssafy.api.request.NoticeUpdatePostReq;
import com.ssafy.api.request.QuestionRegisterPostReq;
import com.ssafy.api.request.QuestionUpdatePostReq;
import com.ssafy.db.entity.Hall;
import com.ssafy.db.entity.Notice;
import com.ssafy.db.entity.Question;

import java.util.List;

/**
 *	유저 관련 비즈니스 로직 처리를 위한 서비스 인터페이스 정의.
 */
public interface QuestionService {

	Question createQuestion(QuestionRegisterPostReq questionRegisterInfo);

	void deleteQuestion(int qNum);
	Question getQuestionByQNum(int qNum);

	Question updateQuestion(int qNum, QuestionUpdatePostReq questionUpdateInfo);

	List<Question> getQuestionByQTitle(String qTitle);

	List<Question> getQuestionAll();

}
