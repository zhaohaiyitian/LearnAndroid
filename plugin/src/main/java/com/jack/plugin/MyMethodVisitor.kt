package com.jack.plugin

import org.objectweb.asm.AnnotationVisitor
import org.objectweb.asm.MethodVisitor
import org.objectweb.asm.Type
import org.objectweb.asm.commons.AdviceAdapter
import org.objectweb.asm.commons.Method

class MyMethodVisitor(
    api: Int,
    methodVisitor: MethodVisitor?,
    access: Int,
    name: String?,
    descriptor: String?
) : AdviceAdapter(api, methodVisitor, access, name, descriptor) {

    var s = 0
    override fun onMethodEnter() {
        super.onMethodEnter()
//        if (!inject) {
//            return
//        }
        //  long startTime= System.currentTimeMillis();
//        invokeStatic(Type.getType("Ljava/lang/System;"),Method("currentTimeMillis","()J"))
//        s = newLocal(Type.LONG_TYPE)
//        storeLocal(s)
    }

    var z = 0
    override fun onMethodExit(opcode: Int) {
        super.onMethodExit(opcode)
//        if (!inject) {
//            return
//        }
        //        long endTime= System.currentTimeMillis();
        //        System.out.println("execute time="+(endTime-startTime)+"ms");
//        invokeStatic(Type.getType("Ljava/lang/System;"),Method("currentTimeMillis","()J"))
//        z = newLocal(Type.LONG_TYPE)
//        storeLocal(z)
//        getStatic(Type.getType("Ljava/lang/System;"),"out",Type.getType("Ljava/io/PrintStream;"))
//        newInstance(Type.getType("Ljava/lang/StringBuilder;"))
//        dup()
//        invokeConstructor(Type.getType("Ljava/lang/StringBuilder;"),Method("<init>","()V"))
//        visitLdcInsn("execute time=") // 申请字符串
//        invokeVirtual(Type.getType("Ljava/lang/StringBuilder;"),Method("append","(Ljava/lang/String;)Ljava/lang/StringBuilder;"))
//        loadLocal(z)
//        loadLocal(s)
//        math(SUB,Type.LONG_TYPE)
//        invokeVirtual(Type.getType("Ljava/lang/StringBuilder;"),Method("append","(J)Ljava/lang/StringBuilder;"))
//        visitLdcInsn(" ms")
//        invokeVirtual(Type.getType("Ljava/lang/StringBuilder;"),Method("append","(Ljava/lang/String;)Ljava/lang/StringBuilder;"))
//        invokeVirtual(Type.getType("Ljava/lang/StringBuilder;"),Method("toString","()Ljava/lang/String;"))
//        invokeVirtual(Type.getType("Ljava/io/PrintStream;"),Method("println","(Ljava/lang/String;)V;"))
    }

    /**
     * visityTypeInsn() 表示执行 NEW, ANEWARRAY, CHECKCAST, INSTANCEOF 指令
     */
    override fun visitTypeInsn(opcode: Int, type: String?) {
        super.visitTypeInsn(opcode, type)
    }

    /**
     * visitFieldInsn() 表示执行 GETSTATIC, PUTSTATIC, GETFIELD, PUTFIELD 指令
     */
    override fun visitFieldInsn(opcode: Int, owner: String?, name: String?, descriptor: String?) {
        super.visitFieldInsn(opcode, owner, name, descriptor)
    }

    /**
     * visitMethodInsn() 表示执行 INVOKEVIRTUAL, INVOKESPECIAL, INVOKESTATIC, INVOKEINTERFACE 指令
     * INVOKEVIRTUAL：调用对象的实例方法
     * INVOKESPECIAL：调用private方法、构造函数、父类方法
     * INVOKESTATIC： 调用静态方法,根据方法签名进行分派
     * INVOKEINTERFACE：调用接口方法,会在运行时搜索该接口的实现类,找到合适的方法进行调用
     */
    override fun visitMethodInsn(
        opcodeAndSource: Int,
        owner: String?,
        name: String?,
        descriptor: String?,
        isInterface: Boolean
    ) {
        super.visitMethodInsn(opcodeAndSource, owner, name, descriptor, isInterface)
    }

    var inject = false
    override fun visitAnnotation(descriptor: String?, visible: Boolean): AnnotationVisitor {
//        println("visitAnnotation: "+descriptor)
//        if ("Lcom/example/gradledemo/Test;".equals(descriptor)) {
//            inject = true
//        }
        return super.visitAnnotation(descriptor, visible)
    }
}