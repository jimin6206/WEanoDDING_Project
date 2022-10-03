package com.ssafy.api.service;

import com.ssafy.api.request.NoticeRegisterPostReq;
import com.ssafy.api.request.NoticeUpdatePostReq;
import com.ssafy.api.request.QuestionRegisterPostReq;
import com.ssafy.api.request.QuestionUpdatePostReq;
import com.ssafy.db.entity.Hall;
import com.ssafy.db.entity.Notice;
import com.ssafy.db.entity.Question;
import com.ssafy.db.repository.NoticeRepository;
import com.ssafy.db.repository.NoticeRepositorySupport;
import com.ssafy.db.repository.QuestionRepository;
import com.ssafy.db.repository.QuestionRepositorySupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 *	Hall 관련 비즈니스 로직 처리를 위한 서비스 구현 정의.
 */
@Service("QuestionService")
public class QuestionServiceImpl implements QuestionService {
	@Autowired
	QuestionRepository questionRepository;

	@Autowired
	QuestionRepositorySupport questionRepositorySupport;

	@Override
	public Question createQuestion(QuestionRegisterPostReq questionRegisterInfo) {
		Question question = new Question();

		question.setCompanyId(questionRegisterInfo.getCompanyId());
		question.setQTitle(questionRegisterInfo.getQTitle());
		question.setQContent(questionRegisterInfo.getQContent());
		question.setQDate(questionRegisterInfo.getQDate());

		return questionRepository.save(question);
	}

	@Override
	public void deleteQuestion(int qNum) {
		questionRepository.delete(getQuestionByQNum(qNum));
	}

	@Override
	public Question getQuestionByQNum(int qNum) {
		// 디비에 유저 정보 조회 (userId 를 통한 조회).
		Optional<Question> optQuestion = questionRepository.findByqNum(qNum);
		return optQuestion.isPresent() ? optQuestion.get() : null;
	}

	@Override
	public Question updateQuestion(int qNum, QuestionUpdatePostReq questionUpdateInfo) {
		Question question = getQuestionByQNum(qNum);
		question.setQTitle(questionUpdateInfo.getQTitle());
		question.setQContent(questionUpdateInfo.getQContent());
		questionRepository.save(question);

		return question;
	}

	@Override
	public List<Question> getQuestionByQTitle(String qTitle) {
		// 디비에 유저 정보 조회 (title 를 통한 조회).
		List<Question> questions = questionRepository.findByqTitleContains(qTitle);
		return questions;
	}

	@Override
	public List<Question> getQuestionAll() {
		List<Question> questions = questionRepository.findAll();

		return questions;
	}
}
