package com.snaker.framework.config.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.snaker.framework.config.entity.Dictionary;
import com.snaker.framework.config.entity.DictionaryItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 配置字典持久化类
 * @author yuqs
 * @since 0.1
 */
@Mapper
public interface DictionaryDao extends BaseMapper<Dictionary> {

    @Select(value = {
            "select ci.id,ci.name,ci.code,ci.orderby,ci.dictionary,ci.description from conf_dictitem ci " +
                    " left outer join conf_dictionary cd on cd.id = ci.dictionary " +
                    " where cd.name = #{name}"
    })
    List<DictionaryItem> getItemsByName(@Param("name") String name);
}
