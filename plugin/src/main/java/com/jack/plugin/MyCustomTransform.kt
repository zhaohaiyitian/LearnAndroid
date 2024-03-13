package com.jack.plugin

import com.android.build.api.transform.Format
import com.android.build.api.transform.QualifiedContent
import com.android.build.api.transform.Transform
import com.android.build.api.transform.TransformInvocation
import com.android.build.gradle.internal.pipeline.TransformManager
import com.android.utils.FileUtils
import javassist.ClassPool
import javassist.CtClass
import javassist.CtMethod
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.Opcodes
import java.io.File
import java.io.FileDescriptor
import java.io.FileInputStream
import java.io.FileOutputStream


class MyCustomTransform: Transform() {

    val classPool = ClassPool.getDefault() // 内存在windows或者在Mac上

    override fun getName(): String {
        return "CustomTransform"
    }

    /**
     * 默认：指定Transform要处理的数据类型
     */
    override fun getInputTypes(): MutableSet<QualifiedContent.ContentType> {
        return TransformManager.CONTENT_CLASS
    }

    /**
     * 默认：用于指定Transform的作用域
     */
    override fun getScopes(): MutableSet<in QualifiedContent.Scope> {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    /**
     * 是否开启增量编译
     */
    override fun isIncremental(): Boolean {
        return false
    }

    /**
     * 用来处理中间转换过程
     */
    override fun transform(transformInvocation: TransformInvocation) {
        super.transform(transformInvocation)
        // 修改class 不修改jar包
        val inputProvider = transformInvocation.inputs
        val outputProvider = transformInvocation.outputProvider
        inputProvider.forEach {
            // 文件夹
            it.directoryInputs.forEach {directoryInput ->
                val path = directoryInput.file.absolutePath
                val dest = outputProvider.getContentLocation(directoryInput.name,directoryInput.contentTypes,directoryInput.scopes,Format.DIRECTORY)
                findTargetClass(directoryInput.file,path,dest)
                FileUtils.copyDirectory(directoryInput.file,dest)
            }
            //jar包
            it.jarInputs.forEach { jarInput ->
                val path = jarInput.file.absolutePath
                val dest = outputProvider.getContentLocation(jarInput.name,jarInput.contentTypes,jarInput.scopes,Format.JAR)
                FileUtils.copyFile(jarInput.file,dest)
            }
        }
        println("wangjie filePath: ")
    }

    private fun findTargetClass(dir: File, fileName: String,dest: File) {
        if (dir.isDirectory) {
            dir.listFiles().forEach {
                findTargetClass(it,fileName,dest)
            }
        } else {
            val path = dir.absolutePath
            if (path.endsWith(".class")) {
                println(dest)
                modify(path,fileName,dest)
            }
        }
    }

    private fun modify(filePath: String,fileName: String,dest: File) {
        if (filePath.contains("BuildConfig.class")) {
            return
        }
        println("filePath: "+filePath) // /Users/wangjie/AndroidStudioProjects/GradleDemo/app/build/tmp/kotlin-classes/debug/com/example/gradledemo/MainActivity.class
        println("fileName: "+fileName) // /Users/wangjie/AndroidStudioProjects/GradleDemo/app/build/tmp/kotlin-classes/debug
        val className = filePath.replace(fileName,"").replace("/",".").replace(".class","").substring(1)
        println("className: "+className)
        val fis = FileInputStream(filePath)
        val classReader = ClassReader(fis)
        val classWriter = ClassWriter(ClassWriter.COMPUTE_FRAMES)
        classReader.accept(MyClassVisitor(Opcodes.ASM9,classWriter),ClassReader.EXPAND_FRAMES)
        val bytes = classWriter.toByteArray()
        val fullClassPath = filePath.replace(fileName,"")
        println("fullClassPath: "+fullClassPath) // /com/example/gradledemo/MainActivity.class
        println("dest: "+dest) // /Users/wangjie/AndroidStudioProjects/GradleDemo/app/build/intermediates/transforms/CustomTransform/debug/47
        val outFile = File(dest,fullClassPath)

        val fos = FileOutputStream(outFile)
        fos.write(bytes)
        fos.close()
        fis.close()
    }
}