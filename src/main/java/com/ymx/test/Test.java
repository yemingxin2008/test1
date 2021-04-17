package com.ymx.test;

public class Test extends SupperTest {

        //1.静态变量
        static String test01 = SunTestDemo01();

        public static String SunTestDemo01() {
            System.out.println("子类静态变量");
            return "子类静态变量";
        }

        //2.静态代码块
        static {
            System.out.println("子类静态代码块one");
        }

        static {
            System.out.println("子类静态代码块second");
        }

        //3.非静态变量
        String test02 = SunTEstDemo02();

        public String SunTEstDemo02() {
            System.out.println("子类非静态变量");
            return "子类非静态变脸";
        }

        //4.非静态代码块
        {
            System.out.println("子类非静态代码块one");
        }

        {
            System.out.println("子类非静态代码块second");
        }

        //5.构造函数

        public Test() {
            System.out.println("子类构造函数");
        }

        //6.静态方法
        public static void StaticMethod() {
            System.out.println("子类静态方法");
        }

        //7.非静态方法
        public void Method() {
            System.out.println("子类非静态方法");
        }

        public static void main(String[] args) {
            System.out.println("main方法");
            Test exam02Demo02 = new Test();
            System.out.println("main1方法");
            System.out.println("=============");
            Test exam02Demo023 = new Test();
            System.out.println("main2方法");



        }

    /**
     * 插叙
     * @param
     * @return
     */
    public void   chaxu( ){
        int[] ins = {2,3,5,1,23,6,78,34};
        int[] ins2 = sort(ins);
        for(int in: ins2){
            System.out.println(in);
        }
        sort2();sort3();
    }

        public static  int[] sort(int[] ins){

            for (int i=1;i<ins.length;i++){
                int temp = ins[i];
                int j;
                for (  j=i;j>0&&ins[j-1]>temp ;j--){
                        ins[j] = ins[j-1];
                }
                ins[j]=temp;
            }

            return ins;
        }

        public static  void sort2( ){

            int i=1;
            int j=i++;
            //System.out.println("============"+j+"============"+i);
            if(j>++j&&(i++==++j)){
                j+=i;
            }

            int ii=i++;
            i=i++;



            System.out.println("="+j+"===="+i+"====");
        }

    public static  void operrat(StringBuffer x,StringBuffer y){
            x.append(y);
            y=x;
        System.out.println(x+""+y);

    }
    public static  void sort3( ){
        StringBuffer a = new StringBuffer("A");
        StringBuffer b = new StringBuffer("B");
        operrat(a,b);
        System.out.println("a="+a+",b="+b);
    }


    }