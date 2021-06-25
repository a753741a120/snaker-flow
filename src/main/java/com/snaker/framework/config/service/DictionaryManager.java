package com.snaker.framework.config.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.snaker.framework.config.dao.DictionaryDao;
import com.snaker.framework.config.dto.DictionaryDTO;
import com.snaker.framework.config.entity.Dictionary;
import com.snaker.framework.config.entity.DictionaryItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 配置字典管理类
 *
 * @author yuqs
 * @since 0.1
 */
@Component
public class DictionaryManager extends ServiceImpl<DictionaryDao, Dictionary> {
    @Autowired
    private DictionaryDao dictionaryDao;


    /**
     * 保存配置字典实体，更新时先删除配置字典选项列表，再添加选项列表
     *
     * @param entity
     */
    public boolean save(Integer id, DictionaryDTO entity) {
        List<DictionaryItem> items = new ArrayList<DictionaryItem>();
        for (int i = 0; i < entity.getItemNames().size(); i++) {
            DictionaryItem ci = new DictionaryItem();
            ci.setDictionary(entity);
            ci.setName(entity.getItemNames().get(i));
            ci.setOrderby(entity.getOrderbys().get(i));
            ci.setCode(entity.getCodes().get(i));
            items.add(ci);
        }
        if (entity.getId() != null) {
            dictionaryDao.deleteById(id);
        }
        if (items.size() > 0) {
            entity.setDictionaryItems(items);
        }
        return this.save(entity);
    }


    /**
     * 根据分页对象、过滤集合参数，分页查询配置字典列表
     *
     * @return
     */
    public PageInfo<Dictionary> findPage(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return new PageInfo<Dictionary>(this.list());
    }

    /**
     * 根据字典名称，获取所有字典项，并以map类型返回
     *
     * @param name
     * @return
     */
    public List<DictionaryItem> getItemsByName(String name) {
//		StringBuffer sqlBuffer = new StringBuffer();
//		sqlBuffer.append("select ci.id,ci.name,ci.code,ci.orderby,ci.dictionary,ci.description from conf_dictitem ci ");
//		sqlBuffer.append(" left outer join conf_dictionary cd on cd.id = ci.dictionary ");
//		sqlBuffer.append(" where cd.name = ?");

//		SQLQuery query = dictionaryDao.createSQLQuery(sqlBuffer.toString(), name);
//		query.addEntity(DictionaryItem.class);
        return dictionaryDao.getItemsByName(name);
    }

    /**
     * 根据字典名称，获取配置字典数据对象
     *
     * @param name
     * @return Map<String, String> 选项主键ID、选项名称的字典映射集合
     */
    public Map<String, String> getByName(String name) {
        List<DictionaryItem> items = getItemsByName(name);
        if (items == null || items.isEmpty()) return Collections.emptyMap();
        Map<String, String> dicts = new TreeMap<String, String>();
        for (DictionaryItem item : items) {
            dicts.put(item.getCode(), item.getName());
        }
        return dicts;
    }
}
