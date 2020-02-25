package demo;

public class Demo1 {//只能public、默认、abstract、final修饰
	byte a=10,b=20;
//	byte c=a+b;//报错
	/**
	 * 那么为什么不会编译出错呢，这里（ 我认为 ）这个整数如果没有超出范围（比如 byte b = 4;在范围之内），
	 * 编译系统有个自动转换。 那么b= b1 + b2;怎么又会编译出错呢，而且 b1,b2都是byte型的，这里java又有
	 * 自己的一个机制，就是防止两个小数相加超出自己所表示的范围，把b1 ,b2变成 int 在相加。相加后也是int 型
	 * （注意这里的int 和 上面说的没有指出类型的整数默认是int 有区别，这里相当于你自己实际定义的了 例 int c=0; 
	 * 系统不会在将这种明确规定数据类型的数做出自动转换的。转换也要自己强制类型转换b=byte(b1+b2);
	 *  这样看来好像更符合防止两个数相加超出自己表示范围的机制）。b=b1+7;和b= b1 + b2;情况差不多。
	 */
	byte c=++b;
	byte e =127;
	byte d = e++;
	byte f = ++e;
	//=======
	byte g= (byte)128;
	//====
	static String str1;
	String str2;
	public static void main(String[] args) {
		new B().call();//50,69
		new C().call();//10,10
		System.out.println(new Demo1().d);//127
		System.out.println(new Demo1().f);//-127
		System.out.println("g="+new Demo1().g);//-128
		int j=0;
		for(int i=0;i<100;i++) {
			j=j++;
		}
		System.out.println("j="+j);//j=0
		//=====
		String str3;
		System.out.println("str1="+str1);
//		System.out.println("str2="+str2);//报错，不能引用非静态变量
//		System.out.println("str3="+str3);//报错，未初始化
		System.out.println("带参数构造"+new B("40").num);//50
		System.out.println("带参数构造"+new C("400").num);//400
		
		String name="zheng";
//		new Demo1().new InnerClass().get();
		Demo1.InnerClass.get();
		new InnerClass().get();
	}
	 public  static class InnerClass{
		 static final String string="abcd";
		public static void get() {
//			System.out.println("a="+a);
			System.out.println("string="+string);
		}
	}

}
class A{
	String num="10";
}
class B extends A{
	String num = "50";
	B(){
		super.num = "69";
	}
	B(String num){
		num=num;
	}
	void call() {
		System.out.println(num);
		System.out.println(super.num);
	}
}
class C extends A{
	String num ="100";
	C(){}
	C(String num){this.num=num;}
	void call() {
		System.out.println(num);
		System.out.println(super.num);
	}
}