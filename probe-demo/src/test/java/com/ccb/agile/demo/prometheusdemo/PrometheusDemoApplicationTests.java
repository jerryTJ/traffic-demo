package com.ccb.agile.demo.prometheusdemo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.BlockingDeque;

class PrometheusDemoApplicationTests {
	public static void main(String aString) {
		Person lisi = new Person("李四", 24);
		Person wangwu = new Person("王五", 34);
		Person zhangsan = new Person("张三", 26);
		List<Person> personList = new ArrayList<Person>();
		personList.add(lisi);
		personList.add(wangwu);
		personList.add(zhangsan);
		String[] a = new String[] { "a", "b", "c" };
		Vector v;
		Hashtable t;
		BlockingDeque b;
		//personList.stream().sorted((p1, p2) -> p1.getAge() - p2.getAge()).filter(p -> p.getAge() > 30).map().toList();

		Person[] persons = { lisi, wangwu, zhangsan };
		lisi.equals(wangwu);
		lisi.toString();
		lisi.hashCode();
		Arrays.sort(persons);
	}

}

class Person implements Comparable<Person> {
	private String name;
	private int age;

	public Person(String name, int age) {
		this.name = name;
		this.age = age;
	}

	public String getName() {
		return name;
	}

	public int getAge() {
		return age;
	}

	@Override
	public int compareTo(Person o) {
		// TODO Auto-generated method stub
		return this.name.compareTo(o.getName());
	}
}