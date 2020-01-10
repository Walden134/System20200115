package org.hust.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.hust.bean.hydro.Runoff;
import java.util.List;

/**
 * Created by sang on 2017/12/20.
 */
@Mapper
public interface PowerMapper {

    List<Runoff> getAllRunoffByPatAndSitu(@Param("rcp_id") String pattern, @Param("gcm_id") String situ);

    List<Runoff> getFRunoffByPatAndSitu(@Param("rcp_id") String pattern, @Param("gcm_id") String situ);

    List<Runoff> getPRunoffByPatAndSitu(@Param("rcp_id") String pattern, @Param("gcm_id") String situ);

    List<Runoff> getKRunoffByPatAndSitu(@Param("rcp_id") String pattern, @Param("gcm_id") String situ);

    List<Runoff> getAllRunoffByPattern(@Param("rcp_id") String pattern);

    List<Runoff> getFRunoffByPattern(@Param("rcp_id") String pattern);

    List<Runoff> getPRunoffByPattern(@Param("rcp_id") String pattern);

    List<Runoff> getKRunoffByPattern(@Param("rcp_id") String pattern);

}
