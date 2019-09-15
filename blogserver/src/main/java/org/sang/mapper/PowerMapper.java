package org.sang.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.sang.bean.hydro.Runoff;

/**
 * Created by sang on 2017/12/20.
 */
@Mapper
public interface PowerMapper {

	List<Runoff> getAllRunoffByPatAndSitu(@Param("rcp_id") String pattern, @Param("gcm_id") String situ);

	List<Runoff> getFRunoffByPatAndSitu(@Param("rcp_id") String pattern, @Param("gcm_id") String situ);

	List<Runoff> getPRunoffByPatAndSitu(@Param("rcp_id") String pattern, @Param("gcm_id") String situ);

	List<Runoff> getKRunoffByPatAndSitu(@Param("rcp_id") String pattern, @Param("gcm_id") String situ);

}
