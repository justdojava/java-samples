package com.example.collection;

/**
 * @author: zll
 * @DATE: 2020/8/15
 **/
public class Test {
    public static void main(String[] args) {
        /**
         * 从集合头部位置新增元素
         */
        ArrayListTest.addFromHeaderTest(100000);
        LinkedListTest.addFromHeaderTest(100000);

        /**
         * 从集合中间位置新增元素
         */
        ArrayListTest.addFromMidTest(10000);
        LinkedListTest.addFromMidTest(10000);

        /**
         * 从集合尾部位置新增元素
         */
        ArrayListTest.addFromTailTest(1000000);
        LinkedListTest.addFromTailTest(1000000);

        /**
         * 从集合头部位置删除元素
         */
        ArrayListTest.deleteFromHeaderTest(100000);
        LinkedListTest.deleteFromHeaderTest(100000);

        /**
         * 从集合中间位置删除元素
         */
        ArrayListTest.deleteFromMidTest(100000);
        LinkedListTest.deleteFromMidTest(100000);

        /**
         * 从集合尾部位置删除元素
         */
        ArrayListTest.deleteFromTailTest(1000000);
        LinkedListTest.deleteFromTailTest(1000000);

        /**
         * ArrayList & LinkedList for(;;) 循环测试
         */
        ArrayListTest.getByForTest(10000);
        LinkedListTest.getByForTest(10000);

        /**
         * ArrayList & LinkedList 遍历元素操作测试
         */
        ArrayListTest.getByIteratorTest(100000);
        LinkedListTest.getByIteratorTest(100000);
    }
}
