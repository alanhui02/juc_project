package com.y.juc.thredlocal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @Author yao
 * @Date 2021/10/12
 */
public class DateUtils {
	//1.有问题的
	public static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static Date parseDate(String date) throws ParseException {
		return sdf.parse(date);
	}


	//2.使用ThreadLocal创建SimpleDateFormat对象静态成员变量
	/**
	 * 这是创建一个带初始值的ThreadLocal变量
	 */
	/*public static final ThreadLocal<SimpleDateFormat> sdfThreadLocal = new ThreadLocal<SimpleDateFormat>() {
		@Override
		protected SimpleDateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		}
	};*/


	//2.1
	public static final ThreadLocal<SimpleDateFormat> sdfTL = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

	public static Date parseDateTL(String dateString) throws ParseException {
		return sdfTL.get().parse(dateString);
	}

	public static void removeDateTL() {
		sdfTL.remove();
	}

	//3.使用DateTimeFormat代替SimpleDateFormat
	public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	public static LocalDateTime parseDateDTF (String dateString) {
		return LocalDateTime.parse(dateString,DATE_TIME_FORMATTER);
	}

	public static String format (LocalDateTime localDateTime){
		return DATE_TIME_FORMATTER.format(localDateTime);
	}

	public static void main(String[] args) throws ParseException {

		//多线程先使用静态修饰的SimpleDateFormat会出现各种莫名其妙的异常错误谢谢
		//因为simpleDateFormat是线程不安全的
		/*for (int i = 0; i < 3; i++) {
			new Thread(() -> {
				try {
					System.out.println(parseDate("2021-10-12 12:23:34"));
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}, "t1").start();
		}*/

		//针对上述错误修改版本一如下
		//需要把SimpleDateFormat成员变量修改为局部变量，但这个会照成大量对象生成，从而照成内存溢出
		//避免此问题需要把显示把局部变量置为null
		/*for (int i = 0; i < 33; i++) {
			new Thread(()->{
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				try {
					System.out.println(sdf.parse("2021-10-12 12:32:34"));
					sdf = null;
				} catch (ParseException e) {
					e.printStackTrace();
				}
			},String.valueOf(i)).start();
		}*/

		//针对以上两种code任然有不足之处，再次修改
		//此次使用ThreadLocal创建SimpleDateFormat对象，保证每一个SimpleDateFormat对象都有自己的一个副本
		//因此各执不相干
		//首先创建一个成员变量，使用ThreadLocal，
		//然后在提供一个转换静态方法
		//最后使用ThreadLocal创建的副本一定要记得remove掉
		/*for (int i = 0; i < 1000; i++) {
			new Thread(() -> {
				try {
					System.out.println(parseDateTL("2020-12-23 12:42:12"));
				} catch (ParseException e) {
					e.printStackTrace();
				} finally {
					removeDateTL();
				}
			}, String.valueOf(i)).start();
		}*/

		for (int i = 0; i < 22; i++) {
			new Thread(() -> {
				System.out.println(parseDateDTF("2020-12-23 12:42:12"));
			}).start();
		}
		System.out.println(" =========== ");
		System.out.println(" =========== ");
		System.out.println(format(LocalDateTime.now()));
		System.out.println(format(parseDateDTF("2020-12-23 12:42:12")));

	}

}
