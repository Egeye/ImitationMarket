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
	private Line currentLine;// 当前行
	private int useWidth = 0;// 当前行使用的宽度
	private int width;

	private class Line {
		int lineWidth = 0;
		private List<View> children = new ArrayList<View>();
		int height = 0;// 当前行的高度

		/** 添加一个孩子 */
		public void addChild(View child) {
			children.add(child);
			if (child.getMeasuredHeight() > height) {
				height = child.getMeasuredHeight();
			}
			lineWidth += child.getMeasuredWidth();
		}

		/** 返回孩子的数量 */
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

	/** 换行 */
	private void newLine() {
		mLines.add(currentLine);// 记录之前的行
		currentLine = new Line();// 创建新的一行
		useWidth = 0;
	}

	/** 分配每个子控件的位置 */
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		l += getPaddingLeft();
		t += getPaddingTop();
		for (int i = 0; i < mLines.size(); i++) {
			Line line = mLines.get(i);

			// 交给每一行去分配
			line.layout(l, t);
			t += line.getHeight() + verticalSpace;
		}
	}

	// 测量当前控件FlowLayout
	// 父亲是有义务测量每个孩子的,该方法可能会被多次执行，来回测试控件
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		mLines.clear();
		currentLine = null;
		useWidth = 0;

		// 获取父容器宽高模式
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

		currentLine = new Line();// 创建第一行
		for (int i = 0; i < getChildCount(); i++) {
			View child = getChildAt(i);

			// 测量每个孩子
			getChildAt(i).measure(childWidthMeasureSpec, childHeightMeasureSpec);

			int measureWidth = child.getMeasuredWidth();
			useWidth += measureWidth;// 让当前行加上使用的长度
			if (useWidth <= width) {
				// 此时证明当前的孩子是可以放进当前行里
				currentLine.addChild(child);
				useWidth += horizontolSpace;
				if (useWidth > width) {
					// 换行
					newLine();
				}
			} else {
				// 换行
				if (currentLine.getChildCount() < 1) {
					currentLine.addChild(child);
					// 保障当前行里最少有一个
				}
				newLine();
			}
		}
		if (mLines.contains(currentLine)) {
			mLines.add(currentLine);// 添加最后一行
		}

		// 为了测量每个孩子，需要知道孩子的测量规则

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
