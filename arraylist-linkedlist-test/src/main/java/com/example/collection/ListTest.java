package com.example.collection;

import java.util.*;

/**
 * @author: zll
 * @DATE: 2020/8/15
 **/
public class ListTest {
    public static void main(String[] args) {
        List<String> arrayList = new ArrayList<String>();
        arrayList.add("aaa");
        arrayList.add("bbb");
        isUseIterator(arrayList);

        List<String> linkedList = new LinkedList<String>();
        linkedList.add("ccc");
        linkedList.add("ddd");
        isUseIterator(linkedList);
    }

    public static void isUseIterator(List list){
        if (list instanceof RandomAccess){
            System.out.println("实现了 RandomAccess 接口,使用 for 循环遍历");

            for (int i = 0 ; i < list.size(); i++ ){
                System.out.println(list.get(i));
            }
        }else{
            System.out.println("没有实现 RandomAccess 接口,使用迭代器遍历");

            Iterator it = list.iterator();
            while (it.hasNext()){
                System.out.println(it.next());
            }
        }
    }
}
