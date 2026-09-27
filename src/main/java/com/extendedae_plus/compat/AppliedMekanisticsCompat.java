package com.extendedae_plus.compat;

// Applied Mekanistics 适配暂时禁用，待发布适配版本后恢复；build.gradle 已排除本文件。

import me.ramidzkh.mekae2.ae2.MekanismKey;

public final class AppliedMekanisticsCompat {
	private AppliedMekanisticsCompat() {
	}

	public static void addBookmark(Object key) {
		if (key instanceof MekanismKey mekanismKey) {
			JeiRuntimeCompat.addBookmark(mekanismKey.getStack());
		}
	}
}
