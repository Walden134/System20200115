package org.sang.service;

import java.util.ArrayList;
import java.util.Map;

import org.sang.bean.hydro.CalculateBean;
import org.sang.bean.hydro.Hydrostation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class PowerService {

	public int calc(Hydrostation hydrostation, CalculateBean calculateBean) {
		Map<String, ArrayList<Double>> map_0_GFDL;
		System.out.println(hydrostation);
		System.out.println(calculateBean);

		return 1;
	}

}
