package com.or.googlemarket.view;

import java.util.ArrayList;
import java.util.List;

import com.or.googlemarket.utils.UiUtils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class FlowLayout extends ViewGroup {

	public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public FlowLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public FlowLayout(Context context) {
		super(context);
	}

	private int horizontolSpace = UiUtils.dip2px(13);
	private int verticalSpace = UiUtils.dip2px(13);
	private List<Line> mLines = new ArrayList<FlowLayout.Line>();
	private Line currentLine;// ��ǰ��
	private int useWidth = 0;// ��ǰ��ʹ�õĿ��
	private int width;

	private class Line {
		int lineWidth = 0;
		private List<View> children = new ArrayList<View>();
		int height = 0;// ��ǰ�еĸ߶�

		/** ���һ������ */
		public void addChild(View child) {
			children.add(child);
			if (child.getMeasuredHeight() > height) {
				height = child.getMeasuredHeight();
			}
			lineWidth += child.getMeasuredWidth();
		}

		/** ���غ��ӵ����� */
		public int getChildCount() {
			return children.size();
		}

		public int getHeight() {
			return height;
		}

		public void layout(int l, int t) {
			lineWidth += horizontolSpace * (children.size() - 1);

			int surplusCild = 0;

			// int surplus = getMeasuredWidth() - lineWidth;
			int surplus = width - lineWidth;
			
			if (surplus > 0) {
				surplusCild = surplus / children.size();
			}
			for (int i = 0; i < children.size(); i++) {
				View child = children.get(i);
				child.layout(l, t, l + child.getMeasuredWidth() + surplusCild, t + child.getMeasuredHeight());
				l += child.getMeasuredWidth() + surplusCild;
				l += horizontolSpace;
			}
		}
	}

	/** ���� */
	private void newLine() {
		mLines.add(currentLine);// ��¼֮ǰ����
		currentLine = new Line();// �����µ�һ��
		useWidth = 0;
	}

	/** ����ÿ���ӿؼ���λ�� */
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		l += getPaddingLeft();
		t += getPaddingTop();
		for (int i = 0; i < mLines.size(); i++) {
			Line line = mLines.get(i);

			// ����ÿһ��ȥ����
			line.layout(l, t);
			t += line.getHeight() + verticalSpace;
		}
	}

	// ������ǰ�ؼ�FlowLayout
	// ���������������ÿ�����ӵ�,�÷������ܻᱻ���ִ�У����ز��Կؼ�
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		mLines.clear();
		currentLine = null;
		useWidth = 0;

		// ��ȡ���������ģʽ
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		width = MeasureSpec.getSize(widthMeasureSpec) - getPaddingRight() - getPaddingLeft();
		int height = MeasureSpec.getSize(heightMeasureSpec) - getPaddingBottom() - getPaddingTop();

		int childWidthMode;
		int childHeightMode;
		childWidthMode = widthMode == MeasureSpec.EXACTLY ? MeasureSpec.AT_MOST : widthMode;
		childHeightMode = heightMode == MeasureSpec.EXACTLY ? MeasureSpec.AT_MOST : heightMode;

		int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidthMode, width);
		int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(childHeightMode, height);

		currentLine = new Line();// ������һ��
		for (int i = 0; i < getChildCount(); i++) {
			View child = getChildAt(i);

			// ����ÿ������
			getChildAt(i).measure(childWidthMeasureSpec, childHeightMeasureSpec);

			int measureWidth = child.getMeasuredWidth();
			useWidth += measureWidth;// �õ�ǰ�м���ʹ�õĳ���
			if (useWidth <= width) {
				// ��ʱ֤����ǰ�ĺ����ǿ��ԷŽ���ǰ����
				currentLine.addChild(child);
				useWidth += horizontolSpace;
				if (useWidth > width) {
					// ����
					newLine();
				}
			} else {
				// ����
				if (currentLine.getChildCount() < 1) {
					currentLine.addChild(child);
					// ���ϵ�ǰ����������һ��
				}
				newLine();
			}
		}
		if (mLines.contains(currentLine)) {
			mLines.add(currentLine);// ������һ��
		}

		// Ϊ�˲���ÿ�����ӣ���Ҫ֪�����ӵĲ�������

		int totalheight = 0;
		for (Line line : mLines) {
			totalheight += line.getHeight();
		}
		totalheight += verticalSpace * (mLines.size() - 1) + getPaddingTop() + getPaddingBottom();

		// setMeasuredDimension(width, resolveSize(totalheight,
		// heightMeasureSpec));
		setMeasuredDimension(width + getPaddingLeft()+getPaddingRight(), resolveSize(totalheight, heightMeasureSpec));
	}

}
