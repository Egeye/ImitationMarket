package com.or.googlemarket.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class RationLayout extends FrameLayout {
	// ����ֵ
	private float ratio = 2.22f;
	

	public void setRatio(float ratio) {
		this.ratio = ratio;
	}

	// ��װ��߱���ȥ��ʾ
	public RationLayout(Context context) {
		super(context);
	}

	public RationLayout(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		//����1 �����ռ䣬����2 �������֣� ���� 3Ĭ��ֵ
		float ratio =attrs.getAttributeFloatValue("http://schemas.android.com/apk/res/com.or.googlemarket", "ratio", 2.22f);
		setRatio(ratio);
	}

	public RationLayout(Context context, AttributeSet attrs) {
		this(context, attrs,0);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// widthMeasureSpec ��ȹ��򣬰����������֣�ģʽ��ֵ
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		// 3��ģʽ����󣬾�ȷ��δ����
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		
		// ȥ���������ߵ�padding
		int width = widthSize - getPaddingLeft() - getPaddingRight();

		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);

		// ȥ���������ߵ�padding
		int height = heightSize - getPaddingTop() - getPaddingBottom();

		if (widthMode == MeasureSpec.EXACTLY && heightMode != MeasureSpec.EXACTLY) {
			// ����һ�� �߶ȵ�ֵ �ø߶�=���/����
			height = (int) (width / ratio + 0.5f); // ��֤4������
		} else if (widthMode != MeasureSpec.EXACTLY && heightMode == MeasureSpec.EXACTLY) {
			// ���ڸ߶��Ǿ�ȷ��ֵ ,������Ÿ߶ȵı仯���仯
			width = (int) ((height * ratio) + 0.5f);
		}
		// �����������µĹ���
		widthMeasureSpec = MeasureSpec.makeMeasureSpec(MeasureSpec.EXACTLY,
				width + getPaddingLeft() + getPaddingRight());
		heightMeasureSpec = MeasureSpec.makeMeasureSpec(MeasureSpec.EXACTLY,
				height + getPaddingTop() + getPaddingBottom());

		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

}
