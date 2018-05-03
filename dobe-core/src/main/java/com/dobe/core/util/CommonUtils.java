package com.dobe.core.util;

import java.util.List;

/**
 * @Description 		: 通用工具类
 * @Project				: dobe-core
 * @Program				: com.dobe.core.util
 * @Author 				: zc.ding@foxmail.com
 * @Date				: 2018年5月3日
 */
public class CommonUtils {

	/**
	 * 是否为空
	 * @method_name				: isEmpty
	 * @param list
	 * @return
	 * @Author					: zc.ding@foxmail.com
	 * @Date					: 2018年5月3日 上午7:44:56
	 */
	public static boolean isEmpty(List<?> list){
		if(list == null || list.isEmpty()){
			return true;
		}
		return false;
	}
	
	/**
	 *  判断list非空
	 *  @Method_Name    		: isNotEmpty
	 *  @param list	
	 *  @return         		: boolean true：非空， false：空
	 *  @Date  					: 2017年7月20日 上午10:47:06 
	 *  @Author         		: zhichaoding@hongkun.com zc.ding
	 */
	public static boolean isNotEmpty(List<?> list){
		return !isEmpty(list);
	}
}
