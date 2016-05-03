package cn.ittiger.ucpage.inject;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * View中各个控件元素的注入，支持控件初始化，绑定单击、长按事件，对于列表数据中的item项也支持单击、长按事件绑定
 * 不支持静态变量控件的注入
 * Author: hyl
 * Time: 2015-8-24下午3:08:35
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface InjectView {
	/**
	 * View控件的资源id
	 */
	public int id()  default -1;
	/**
	 * View控件的单击事件，值为方法名
	 */
	public String onClick() default "";
	/**
	 * View控件的长按事件，值为方法名
	 */
	public String onLongClick() default "";
	/**
	 * AbsListView --> Item项的单击事件，值为方法名
	 */
	public String onItemClick() default "";
	/**
	 * AbsListView --> Item项的长按事件，值为方法名
	 */
	public String onItemLongClick() default "";
}
