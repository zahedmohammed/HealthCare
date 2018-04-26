package com.fxlabs.fxt.sdk.services;

public class CredUtils {

    final static public InheritableThreadLocal<String> url = new InheritableThreadLocal<>();
    final static public InheritableThreadLocal<String> username = new InheritableThreadLocal<>();
    final static public InheritableThreadLocal<String> password = new InheritableThreadLocal<>();
    final static public InheritableThreadLocal<Boolean> errors = new InheritableThreadLocal<>();
    final static public InheritableThreadLocal<BotLogger> taskLogger = new InheritableThreadLocal<>();
}
