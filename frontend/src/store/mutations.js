export function setIsLoggedIn (state) {
	if (localStorage.getItem('token')) {
		state.IsLoggedIn = true
	} else {
		state.IsLoggedIn = false
	}
}

export function setIsAdmin (state) {
	if ( localStorage.getItem('companyId') === 'admin') {
		state.IsAdmin = true
	} else {
		state.IsAdmin = false
	}
}

export function setHallsInfo (state, HallsInfo) {
	state.HallsInfo = HallsInfo
}

export function setCompanyInfo (state, CompanyInfo) {
	state.CompanyInfo = CompanyInfo
}

export function setWeddingInfo (state, WeddingInfo) {
	state.WeddingInfo = WeddingInfo
}

export function setWeddingDateInfo (state, WeddingDateInfo) {
	state.WeddingDateInfo = WeddingDateInfo
}

export function setWeddingDetailInfo (state, WeddingDetailInfo) {
	state.WeddingDetailInfo = WeddingDetailInfo
}

export function setNoticeListInfo(state, NoticeListInfo) {
	state.NoticeListInfo = NoticeListInfo
}
export function setNoticeDetailInfo(state, NoticeDetailInfo) {
	state.NoticeDetailInfo = NoticeDetailInfo
}

export function setQuestionListInfo(state, QuestionListInfo) {
	state.QuestionListInfo = QuestionListInfo
}
export function setQuestionDetailInfo(state, QuestionDetailInfo) {
	state.QuestionDetailInfo = QuestionDetailInfo
}

export function setNowWeddingInfo (state, NowWeddingInfo) {
	state.NowWeddingInfo = NowWeddingInfo
}

export function setIsGuest (state) {
	if (localStorage.getItem('token')) {
		state.IsGuest = false
	} else {
		state.IsGuest = true
	}
}

export function setNowGuestInfo (state, NowGuestInfo) {
	state.NowGuestInfo = NowGuestInfo
}
