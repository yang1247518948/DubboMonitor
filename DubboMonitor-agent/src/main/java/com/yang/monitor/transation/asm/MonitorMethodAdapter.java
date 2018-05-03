package com.yang.monitor.transation.asm;

import org.objectweb.asm.MethodAdapter;
import org.objectweb.asm.MethodVisitor;

import static org.objectweb.asm.Opcodes.*;

/**
 * @author yangping
 * @date  2017.11.18 17:20
 **/
public class MonitorMethodAdapter extends MethodAdapter {
	private String className;
	private String methodName;

	MonitorMethodAdapter(MethodVisitor methodVisitor, String className, String methodName) {
		super(methodVisitor);
		this.className = className;
		this.methodName = methodName;
	}


	@Override
	public void visitCode() {
		mv.visitCode();
		mv.visitLdcInsn(methodName);
		mv.visitLdcInsn(className);
		mv.visitMethodInsn(INVOKESTATIC, "com/danlu/dlmonitor/agent/Interceptor", "use", "(Ljava/lang/String;Ljava/lang/String;)V");
	}

	@Override
	public void visitInsn(int opcode) {
		if (opcode >= IRETURN && opcode <= RETURN) {// 在返回之前安插代码。
			mv.visitLdcInsn(methodName);
			mv.visitLdcInsn(className);
			mv.visitMethodInsn(INVOKESTATIC, "com/danlu/dlmonitor/agent/Interceptor", "beforeReturn", "(Ljava/lang/String;Ljava/lang/String;)V");
		}
		super.visitInsn(opcode);
	}
}

