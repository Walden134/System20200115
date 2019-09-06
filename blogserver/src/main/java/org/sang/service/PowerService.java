package org.sang.service;

import org.sang.bean.hydro.CalculateBean;
import org.sang.bean.hydro.Hydrostation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PowerService {

	public int calc(Hydrostation hydrostation, CalculateBean calculateBean) {
		
		
		return 1;
	}

}
