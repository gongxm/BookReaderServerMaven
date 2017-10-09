package com.gongxm.utils;

public class PrintTool {
	/**
	 * 打印信息工具
	 * @param param
	 */
	public static void println(Object...param){
		StringBuilder sb = new StringBuilder("[");
		for (int i=0;i<param.length;i++) {
			sb.append(param[i]);
			if(i!=param.length-1){
				sb.append(", ");
			}
		}
		
		sb.append("]");
		
		System.out.println(sb);
	}
}
