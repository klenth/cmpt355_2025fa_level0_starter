package edu.westminsteru.cmpt355.level1.lang;

public sealed interface ReturnType
    permits Type, ReturnType.Void {

    public static final ReturnType.Void VOID = Void.INSTANCE;

    enum Void implements ReturnType { INSTANCE }
}
