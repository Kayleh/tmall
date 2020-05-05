package com.kayleh.tmall.service.impl;

import com.kayleh.tmall.mapper.PropertyValueMapper;
import com.kayleh.tmall.pojo.Product;
import com.kayleh.tmall.pojo.Property;
import com.kayleh.tmall.pojo.PropertyValue;
import com.kayleh.tmall.pojo.PropertyValueExample;
import com.kayleh.tmall.service.PropertyService;
import com.kayleh.tmall.service.PropertyValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Wizard
 * @Date: 2020/5/5 16:35
 */
@Service
public class PropertyValueServiceImpl implements PropertyValueService {
    @Autowired
    PropertyValueMapper propertyValueMapper;
    @Autowired
    PropertyService propertyService;

    //初始化
    @Override
    public void init(Product product) {

        //根据产品获取分类，然后根据分类的id查询该分类下的属性的集合
        List<Property> propertyList = propertyService.list(product.getCid());
        for (Property property : propertyList) {
            //尝试根据产品id和产品属性id获取属性值
            PropertyValue propertyValue = get(property.getId(), product.getId());
            if (null==propertyValue){
                //没有属性值，就创建一个属性值
                propertyValue=new PropertyValue();
                //根据产品的id设置属性值的产品id
                propertyValue.setPid(product.getId());
                //根据属性的id设置属性值的产品属性id
                propertyValue.setPtid(property.getId());
                //存到数据库
                propertyValueMapper.insert(propertyValue);
            }
        }

    }

    @Override
    public void update(PropertyValue propertyValue) {
        propertyValueMapper.updateByPrimaryKeySelective(propertyValue);

    }

    //根据属性id和产品id获取PropertyValue对象
    @Override
    public PropertyValue get(int ptid, int pid) {
        PropertyValueExample propertyValueExample = new PropertyValueExample();
        propertyValueExample.createCriteria().andPidEqualTo(pid).andPtidEqualTo(ptid);
        List<PropertyValue> propertyValues = propertyValueMapper.selectByExample(propertyValueExample);
        if (propertyValues.isEmpty()){
            return null;
        }
        return propertyValues.get(0);
    }

    //根据产品id获取所有的属性值
    @Override
    public List<PropertyValue> list(int pid) {
        PropertyValueExample propertyValueExample = new PropertyValueExample();
        propertyValueExample.createCriteria().andPidEqualTo(pid);
        List<PropertyValue> propertyValues = propertyValueMapper.selectByExample(propertyValueExample);
        for (PropertyValue propertyValue : propertyValues) {
            Property property = propertyService.get(propertyValue.getPtid());
            propertyValue.setProperty(property);
        }return propertyValues;
    }
}
