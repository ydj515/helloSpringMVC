package kr.ac.hansung.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.ac.hansung.dao.OfferDAO;
import kr.ac.hansung.model.Offer;

@Service //service layer는 dao를 이용
public class OfferService {
	
	@Autowired //의존성 주입
	private OfferDAO offerDao;

	public List<Offer> getCurrent() {
		
		return offerDao.getOffers(); //dao에 getOffers를 가져옴
								     //모든 record를 가져옴
	}

	public void insert(Offer offer) {
		offerDao.insert(offer);
	}

}
