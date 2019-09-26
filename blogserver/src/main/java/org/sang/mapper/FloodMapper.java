package org.sang.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.sang.bean.flood.BaseFlood;
import org.sang.bean.flood.BaseP;
import org.sang.bean.flood.FutureP;
import org.sang.bean.flood.P3;

/**
 * Created by sang on 2017/12/20.
 */
@Mapper
public interface FloodMapper {
	List<P3> getPByCs(@Param("cs") double cs);

	List<BaseP> getBaseP();

	List<FutureP> getFuturePByRcp(@Param("rcpId") String rcpId);

	List<BaseFlood> getBaseFlood();
}
