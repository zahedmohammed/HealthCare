package com.fxlabs.fxt.codegen.code;

public class CodegenThreadUtils {

    final static public ThreadLocal<BotLogger> taskLogger = new InheritableThreadLocal<>();
}
