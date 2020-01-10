package org.hust.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.hust.bean.flood.BaseFlood;
import org.hust.bean.flood.BaseP;
import org.hust.bean.flood.FutureP;
import org.hust.bean.flood.P3;

import java.util.List;

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
