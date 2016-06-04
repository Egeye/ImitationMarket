package com.or.googlemarket.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class RationLayout extends FrameLayout {
	// 比例值
	private float ratio = 2.22f;
	

	public void setRatio(float ratio) {
		this.ratio = ratio;
	}

	// 安装宽高比例去显示
	public RationLayout(Context context) {
		super(context);
	}

	public RationLayout(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		//参数1 命名空间，参数2 属性名字， 参数 3默认值
		float ratio =attrs.getAttributeFloatValue("http://schemas.android.com/apk/res/com.or.googlemarket", "ratio", 2.22f);
		setRatio(ratio);
	}

	public RationLayout(Context context, AttributeSet attrs) {
		this(context, attrs,0);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// widthMeasureSpec 宽度规则，包含了两部分，模式与值
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		// 3种模式，最大，精确，未定义
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		
		// 去掉左右两边的padding
		int width = widthSize - getPaddingLeft() - getPaddingRight();

		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);

		// 去掉上下两边的padding
		int height = heightSize - getPaddingTop() - getPaddingBottom();

		if (widthMode == MeasureSpec.EXACTLY && heightMode != MeasureSpec.EXACTLY) {
			// 修正一下 高度的值 让高度=宽度/比例
			height = (int) (width / ratio + 0.5f); // 保证4舍五入
		} else if (widthMode != MeasureSpec.EXACTLY && heightMode == MeasureSpec.EXACTLY) {
			// 由于高度是精确的值 ,宽度随着高度的变化而变化
			width = (int) ((height * ratio) + 0.5f);
		}
		// 重新制作了新的规则
		widthMeasureSpec = MeasureSpec.makeMeasureSpec(MeasureSpec.EXACTLY,
				width + getPaddingLeft() + getPaddingRight());
		heightMeasureSpec = MeasureSpec.makeMeasureSpec(MeasureSpec.EXACTLY,
				height + getPaddingTop() + getPaddingBottom());

		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

}
