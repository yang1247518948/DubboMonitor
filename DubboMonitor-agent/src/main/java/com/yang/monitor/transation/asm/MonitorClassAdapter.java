package com.yang.monitor.transation.asm;

import org.objectweb.asm.ClassAdapter;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * @author yangping
 * @date  2017.11.18 17:15
 **/
public class MonitorClassAdapter extends ClassAdapter {
	private String className;
	private String methodName;
	private boolean isInterface;

	public MonitorClassAdapter(ClassVisitor classVisitor) {
		super(classVisitor);
	}

	@Override
	public MethodVisitor visitMethod(int i, String s, String s1, String s2, String[] strings) {
		MethodVisitor mv = cv.visitMethod(i, s, s1, s2,strings);
		if(!s.equals("<init>") && !isInterface && mv!=null&& !s.equals("main")){
			methodName = s;
			mv = new MonitorMethodAdapter(mv,className,methodName);
		}
		return mv;
	}

	@Override
	public void visit(int i, int i1, String s, String s1, String s2, String[] strings) {
		cv.visit(i, i1, s, s1, s2, strings);
		className = s;
		isInterface = (i1 & Opcodes.ACC_INTERFACE) != 0;
	}
}
