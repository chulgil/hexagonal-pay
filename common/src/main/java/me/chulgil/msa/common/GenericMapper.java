package me.chulgil.msa.common;

public interface GenericMapper<F, T> {

    F to(T to);
    T from(F from);
}
