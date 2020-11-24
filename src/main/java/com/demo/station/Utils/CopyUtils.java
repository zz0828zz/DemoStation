package com.demo.station.Utils;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Data
@Component
public class CopyUtils {


    public static <T> T copyPojo(Object source, Class<T> clazz) {

        try {
            if (source != null) {
                Object obj = clazz.newInstance();
                BeanUtils.copyProperties(source, obj);
                return (T) obj;
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }


    public static IPage copyPage(IPage source, Class clazz) {

        List list = new ArrayList<>();
        for (Object obj : source.getRecords()) {
            list.add(copyPojo(obj, clazz));

        }
		source.setRecords(list);


        return source;
    }

    public static List copyList(List list, Class clazz) {

        List list2 = new ArrayList<>();
        list.stream().forEach(source -> {
            Object obj = null;
            try {
                obj = clazz.newInstance();
                if(null != source) {
                    BeanUtils.copyProperties(source, obj);
                }
                list2.add(obj);
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
        return list2;
    }




}
