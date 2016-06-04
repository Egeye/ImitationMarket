package com.or.googlemarket.utils;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;

public class DrawableTools {
	public static GradientDrawable createShape(int color) {
		GradientDrawable drawable = new GradientDrawable();

		// 设置4个角的弧度
		drawable.setCornerRadius(UiUtils.dip2px(5));
		drawable.setColor(color);

		return drawable;

	}

	public static StateListDrawable createSelectDrawable(Drawable press, Drawable normal) {
		StateListDrawable sld = new StateListDrawable();
		sld.addState(new int[] { android.R.attr.state_pressed }, press);
		sld.addState(new int[] {}, normal);

		return sld;

	}
}
