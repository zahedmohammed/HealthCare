package com.fxlabs.fxt.sdk.services;

public class CredUtils {

    final static public ThreadLocal<String> url = new InheritableThreadLocal<>();
    final static public ThreadLocal<String> username = new InheritableThreadLocal<>();
    final static public ThreadLocal<String> password = new InheritableThreadLocal<>();
    final static public ThreadLocal<StringBuilder> taskLogger = new InheritableThreadLocal<>();
}