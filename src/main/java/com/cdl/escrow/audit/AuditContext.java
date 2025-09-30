package com.cdl.escrow.audit;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public final class AuditContext {
    private static final ThreadLocal<String> USER = new ThreadLocal<>();
    private static final ThreadLocal<String> IP = new ThreadLocal<>();
    private static final ThreadLocal<String> UA = new ThreadLocal<>();
    private static final ThreadLocal<String> REQ = new ThreadLocal<>();

    private AuditContext() {}

    public static void set(String user, String ip, String ua, String req) {
        USER.set(user); IP.set(ip); UA.set(ua); REQ.set(req);
    }
    public static String user() { return USER.get(); }
    public static String ip() { return IP.get(); }
    public static String ua() { return UA.get(); }
    public static String req() { return REQ.get(); }
    public static void clear() { USER.remove(); IP.remove(); UA.remove(); REQ.remove(); }
}