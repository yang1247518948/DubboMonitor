package com.yang.monitor.utils;

import com.yang.monitor.traceClass.Trace;
import org.apache.commons.lang.StringUtils;

public class SpanUtils {

	/**
	 * 添加一个版本号
	 * @param trace 需要修改的追踪类
	 */
	public static void addOneSpan(Trace trace){
		if(StringUtils.isBlank(trace.span)){
			return;
		}
		StringBuilder sb = new StringBuilder(trace.span);
		sb.append(".");
		sb.append(trace.remainder+1);
		trace.remainder = 0;
		trace.span = sb.toString();
	}

	/**
	 * 减小一个版本号
	 * @param trace 需要修改的追踪类
	 */
	public static void reduceSpan(Trace trace){
		if(StringUtils.isBlank(trace.span)){
			return;
		}
		String[] words = trace.span.split("\\.");
		StringBuilder sb = new StringBuilder(trace.span);
		int x = sb.lastIndexOf(".");
		if(x>0) {
			sb.delete(x, sb.length());
		}
		trace.span = sb.toString();
		trace.remainder = Integer.valueOf(words[words.length-1]);
	}
}
