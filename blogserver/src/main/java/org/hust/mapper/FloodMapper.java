package org.hust.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.hust.bean.flood.BaseFlood;
import org.hust.bean.flood.BaseP;
import org.hust.bean.flood.FutureP;
import org.hust.bean.flood.P3;

import java.util.List;

@Mapper
public interface FloodMapper {
    List<P3> getPByCs(@Param("cs") double cs);

    List<Double> getPaiByCsAndP(@Param("cs") double cs, @Param("P") double[] P);

    List<BaseP> getBaseP();

    List<FutureP> getFuturePByRcp(@Param("rcpId") String rcpId);

    List<FutureP> getFuturePByRcp2(@Param("rcpId") String rcpId);

    double[] getRunoffByRcpAndGcm(@Param("rcpId") String rcpId, @Param("gcmId") String gcmId);

    List<BaseFlood> getBaseFlood();

    List<BaseP> getBase1();

}
