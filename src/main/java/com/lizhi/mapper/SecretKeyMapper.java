package com.lizhi.mapper;

import com.lizhi.model.SecretKey;
import com.lizhi.model.SecretKeyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;

public interface SecretKeyMapper {
    int countByExample(SecretKeyExample example);

    int deleteByExample(SecretKeyExample example);

    int deleteByPrimaryKey(Long id);

    int insert(SecretKey record);

    int insertSelective(SecretKey record);

    List<SecretKey> selectByExample(SecretKeyExample example);

    SecretKey selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") SecretKey record, @Param("example") SecretKeyExample example);

    int updateByExample(@Param("record") SecretKey record, @Param("example") SecretKeyExample example);

    int updateByPrimaryKeySelective(SecretKey record);

    int updateByPrimaryKey(SecretKey record);

    @ResultMap("BaseResultMap")
    @Select("select id,tenant_id,public_key,private_key,create_by_id,update_by_id from t_secret_key where tenant_id = #{tenantId} ")
    SecretKey selectByTenantId(Long id);
}