package com.ymx.test;

/**
 * 加载顺序
 */
public class SupperTest {
        //1.静态变量
        static String test01 = TestDemo01();

        //2.静态方法
        static  String TestDemo01() {
            System.out.println("父类静态变量");
            return "静态变量";
        }

        //3.静态代码块
        static {
            System.out.println("父类静态代码块one");
        }

        static {
            System.out.println("父类静态代码块second");
        }

        //4.非静态变量
        String test02 = TestDemo02();

        //5.非静态变量测试方法
        public String TestDemo02() {
            System.out.println("父类非静态变量");
            return "父类非静态变量";
        }

        //6.非静态语句块
        {
            System.out.println("父类非静态语句one");
        }
        {
            System.out.println("父类非静态语句second");
        }

        //7.非静态方法
        public void methodTest(){
            System.out.println("父类非静态方法");
        }
        //7.1静态方法
        public static void staticMethodTest(){
            System.out.println("父类静态方法");
        }

        //8.构造函数


        public SupperTest() {
            System.out.println("父类构造函数");
        }

        public static void main(String[] args) {
            System.out.println("main 开始");
            SupperTest exam02Demo01 = new SupperTest();


        }
    }
