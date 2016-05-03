package cn.ittiger.ucpage.inject;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;

/**
 * View控件注入帮助类
 * Author: hyl
 * Time: 2015-8-24下午4:04:15
 */
public class InjectHelper {
	/**
	 * 为Activity中声明的控件进行注入初始化(必须在setContextView之后执行)
	 * Author: hyl
	 * Time: 2015-8-24下午4:55:37
	 * @param activity	对该Activity对象的相关view控件属性进行注入初始化
	 */
	public static void inject(Activity activity) {
		InjectHelper.inject(activity, activity.getWindow().getDecorView());
	}
	
	/**
	 * 为android.app.Fragment中声明的控件进行注入初始化(必须在Activity关联且onCreateView之后执行)
	 * Author: hyl
	 * Time: 2015-8-24下午4:55:37
	 * @param fragment	对该Fragment对象的相关view控件属性进行注入初始化
	 */
	@SuppressLint("NewApi")
	public static void inject(Fragment fragment) {
		InjectHelper.inject(fragment, fragment.getActivity().getWindow().getDecorView());
	}
	
	/**
	 * 为android.support.v4.app.Fragment中声明的控件进行注入初始化(必须在Activity关联且onCreateView之后执行)
	 * Author: hyl
	 * Time: 2015-8-24下午4:55:37
	 * @param fragment		对该android.support.v4.app.Fragment对象的相关view控件属性进行注入初始化
	 */
	public static void inject(android.support.v4.app.Fragment fragment) {
		InjectHelper.inject(fragment, fragment.getActivity().getWindow().getDecorView());
	}
	
	/**
	 * 对控件进行注入操作
	 * Author: hyl
	 * Time: 2015-8-24下午4:21:06
	 * @param curInjectInstance		要进行view控件属性注入初始化的对象
	 * @param view					注入控件所在的父View
	 */
	public static void inject(Object curInjectInstance, View view) {
		Class<?> claxx = curInjectInstance.getClass();
		while(claxx != Activity.class && claxx != Object.class) {
			//绑定初始化所有的字段信息和方法
			bindViewAndListener(curInjectInstance, claxx, view);
			//得到直接父类的Class对象
			claxx = claxx.getSuperclass();
		}
	}
	
	private static void bindViewAndListener(Object curInjectInstance, Class<?> claxx, View view) {
		//得到当前Class类声明的所有字段
		Field[] fields = claxx.getDeclaredFields();
		for(Field field : fields) {
			if (Modifier.isStatic(field.getModifiers())) {//过滤掉static静态字段
				continue;
			}
			InjectView injectView = field.getAnnotation(InjectView.class);
			if(injectView == null) {
				continue;
			}
			if(injectView.id() == -1) {//不允许InjectView注解不给定id值
				throw new IllegalArgumentException(claxx.getName() + "中的字段" + field.getName() + "上的注解InjectView必须设置id值");
			}
			try {
				View curView = view.findViewById(injectView.id());
				
				InjectHelper.injectOnClickListener(curInjectInstance, claxx, injectView.onClick(), curView);
				InjectHelper.injectOnLongClickListener(curInjectInstance, claxx, injectView.onLongClick(), curView);
				InjectHelper.injectAbsListViewOnItemClickListener(curInjectInstance, claxx, injectView.onItemClick(), curView);
				InjectHelper.injectAbsListViewOnLongClickListener(curInjectInstance, claxx, injectView.onItemLongClick(), curView);
				
				field.setAccessible(true); 
				field.set(curInjectInstance, curView);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 为View设置单击响应监听
	 * @param curObj		当前监听响应方法所属的对象
	 * @param claxx			当前监听函数所声明的类Class
	 * @param methodName	单击响应监听函数名
	 * @param view			设置监听的控件
	 */
	private static void injectOnClickListener(final Object curObj, final Class<?> claxx, String methodName, final View view) {
		if(methodName == null || "".equals(methodName)) {
			return;
		}
		try {
			final Method method = claxx.getDeclaredMethod(methodName, View.class);
			if(method == null) {
				return;
			}
			view.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					try {
						method.invoke(curObj, v);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 为View设置长按响应监听
	 * @param curObj		当前监听响应方法所属的对象
	 * @param claxx			当前监听函数所声明的类Class
	 * @param methodName	单击响应监听函数名
	 * @param view			设置监听的控件
	 */
	private static void injectOnLongClickListener(final Object curObj, final Class<?> claxx, String methodName, final View view) {
		if(methodName == null || "".equals(methodName)) {
			return;
		}
		try {
			final Method method = claxx.getDeclaredMethod(methodName, View.class);
			if(method == null) {
				return;
			}
			view.setOnLongClickListener(new View.OnLongClickListener() {
				@Override
				public boolean onLongClick(View v) {
					try {
						method.invoke(curObj, v);
					} catch (Exception e) {
						e.printStackTrace();
					}
					return false;
				}
			});
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 为View的子项设置单击响应监听(必须为AbsListView的子类)
	 * @param curObj		当前监听响应方法所属的对象
	 * @param claxx			当前监听函数所声明的类Class
	 * @param methodName	单击响应监听函数名
	 * @param view			设置监听的控件
	 */
	private static void injectAbsListViewOnItemClickListener(final Object curObj, final Class<?> claxx, String methodName, final View view) {
		if(methodName == null || "".equals(methodName)) {
			return;
		}
		if(!(view instanceof AbsListView)) {
			return;
		}
		try {
			final AbsListView listView = (AbsListView) view;
			final Method method = claxx.getDeclaredMethod(methodName, AdapterView.class, View.class, int.class, long.class);
			if(method == null) {
				return;
			}
			listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					try {
						method.invoke(curObj, parent, view, position, id);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 为View子项设置长按响应监听(必须为ABSListView的子类)
	 * @param curObj		当前监听响应方法所属的对象
	 * @param claxx			当前监听函数所声明的类Class
	 * @param methodName	单击响应监听函数名
	 * @param view			设置监听的控件
	 */
	private static void injectAbsListViewOnLongClickListener(final Object curObj, final Class<?> claxx, String methodName, final View view) {
		if(methodName == null || "".equals(methodName)) {
			return;
		}
		if(!(view instanceof AbsListView)) {
			return;
		}
		try {
			final AbsListView listView = (AbsListView) view;
			final Method method = claxx.getDeclaredMethod(methodName, AdapterView.class, View.class, int.class, long.class);
			if(method == null) {
				return;
			}
			listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
				@Override
				public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
					try {
						method.invoke(curObj, parent, view, position, id);
					} catch (Exception e) {
						e.printStackTrace();
					}
					return false;
				}
			});
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
	}
}
