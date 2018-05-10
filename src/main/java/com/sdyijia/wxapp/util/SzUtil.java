package com.sdyijia.wxapp.util;

import com.sdyijia.wxapp.modules.sys.bean.Base;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class SzUtil {
    /**
     * 写一个方法
     * 第一步实现判断该类中的所有属性是否为空的情况
     * 第二步实现忽略某些属性的情况
     * TODO 第三步实现向上或向下递归查找实现判断是否为空的情况（默认向上），主要是判断id目前该方法不判断id
     * 调用实例详见  test ： com.sdyijia.wxapp.util.SzUtilTest
     *
     * @param b      想要验证的具体类
     * @param c      对应的class
     * @param fields 想要忽略的属性
     * @return
     */
    public static boolean nonNull(Base b, Class c, String... fields) {
        if (b == null)
            return false;
        Field[] arr = c.getDeclaredFields();
        List<Field> arrList = Arrays.asList(arr);
        List<String> fieldlist = Arrays.asList(fields);
        arrList = arrList.stream().filter(field -> {
            return !fieldlist.contains(field.getName());
        }).collect(Collectors.toList());

        //获取get方法数据
        try {
            for (Field f : arrList) {
                //处理jiava bean的一个类能够获取私有属性的get，set方法
                PropertyDescriptor pd = new PropertyDescriptor(f.getName(), c);
                //readMethod就是读取方法
                Method readMethod = pd.getReadMethod();//获得读方法
                if (Objects.isNull(readMethod.invoke(b)))
                    return false;
            }
        } catch (IntrospectionException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        //获取set方法
//        for(Field f : fields) {
//            PropertyDescriptor pd = new PropertyDescriptor(f.getName(), c);
//            Method wM = pd.getWriteMethod();//获得WriteMethod也就是写（set）的方法
//            wM.invoke(b, 2);//因为知道是int类型的属性，所以传个int过去就是了。。实际情况中需要判断下他的参数类型
//        }
        return true;
    }


}
