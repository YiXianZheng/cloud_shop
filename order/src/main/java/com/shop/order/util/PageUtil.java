package com.shop.order.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class PageUtil {

    public PageUtil() {
    }

    public static PageResult PageResult(Object page) {
        PageResult pageResult = new PageResult();
        Method method1;
        if (page.getClass().getName().equals("org.springframework.data.domain.PageImpl")) {
            try {
                method1 = page.getClass().getMethod("getTotalElements");
                long total = (Long)method1.invoke(page);
                Method method2 = page.getClass().getMethod("getContent");
                List list = (List)method2.invoke(page);
                Method method3 = page.getClass().getMethod("getNumber");
                int nowpage = (Integer)method3.invoke(page);
                Method method4 = page.getClass().getMethod("getSize");
                int pagesize = (Integer)method4.invoke(page);
                Long limmit = (long)((nowpage + 1) * pagesize) > total ? total : (long)((nowpage + 1) * pagesize);
                pageResult.setTotal(total);
                pageResult.setLimit(limmit.intValue());
                if (list == null) {
                    pageResult.setRows(new ArrayList());
                } else {
                    pageResult.setRows(list);
                }
            } catch (NoSuchMethodException var15) {
                var15.printStackTrace();
            } catch (IllegalAccessException var16) {
                var16.printStackTrace();
            } catch (InvocationTargetException var17) {
                var17.printStackTrace();
            }
        } else if (page.getClass().getName().equals("com.baomidou.mybatisplus.extension.plugins.pagination.Page")) {
            try {
                method1 = page.getClass().getMethod("getTotal");
                pageResult.setTotal((Long)method1.invoke(page));
                Method method2 = page.getClass().getMethod("getPages");
                pageResult.setLimit(new Integer((Long)method2.invoke(page) + ""));
                Method method3 = page.getClass().getMethod("getRecords");
                List list = (List)method3.invoke(page);
                if (list == null) {
                    pageResult.setRows(new ArrayList());
                } else {
                    pageResult.setRows(list);
                }
            } catch (NoSuchMethodException var12) {
                var12.printStackTrace();
            } catch (IllegalAccessException var13) {
                var13.printStackTrace();
            } catch (InvocationTargetException var14) {
                var14.printStackTrace();
            }
        }

        return pageResult;
    }
}
