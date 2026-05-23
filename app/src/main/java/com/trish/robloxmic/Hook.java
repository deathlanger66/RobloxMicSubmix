package com.trish.robloxmic;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class Hook implements IXposedHookLoadPackage {

    private static final int REMOTE_SUBMIX = 8;

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        if (!"com.roblox.client".equals(lpparam.packageName)) return;

        XposedBridge.log("Roblox Mic Submix carregado");

        try {
            XposedHelpers.findAndHookMethod(
                    "android.media.AudioRecord$Builder",
                    lpparam.classLoader,
                    "setAudioSource",
                    int.class,
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) {
                            param.args[0] = REMOTE_SUBMIX;
                            XposedBridge.log("AudioRecord Builder -> REMOTE_SUBMIX");
                        }
                    }
            );
        } catch (Throwable t) {
            XposedBridge.log("Erro hook Builder: " + t);
        }

        try {
            XposedHelpers.findAndHookConstructor(
                    "android.media.AudioRecord",
                    lpparam.classLoader,
                    int.class,
                    int.class,
                    int.class,
                    int.class,
                    int.class,
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) {
                            param.args[0] = REMOTE_SUBMIX;
                            XposedBridge.log("AudioRecord Constructor -> REMOTE_SUBMIX");
                        }
                    }
            );
        } catch (Throwable t) {
            XposedBridge.log("Erro hook Constructor: " + t);
        }
    }
}
