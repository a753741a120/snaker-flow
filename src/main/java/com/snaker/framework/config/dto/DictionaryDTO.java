package com.snaker.framework.config.dto;

import com.snaker.framework.config.entity.Dictionary;
import lombok.Data;

import java.util.List;

/**
 * @基本功能:
 * @program:demo
 * @author:pm
 * @create:2021-06-21 15:24:09
 **/
@Data
public class DictionaryDTO extends Dictionary {

//    private Long id;
//    //名称
//    private String name;
//    //中文名称
//    private String cnName;
//    //描述
//    private String description;
//
//    //字典选项
//    private List<DictionaryItem> dictionaryItems = new ArrayList<DictionaryItem>();
    private List<String> itemNames;
    private List<Integer> orderbys;
    private List<String> codes;

}