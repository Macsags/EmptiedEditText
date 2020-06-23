package com.lxl.emptiededittext;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.lxl.emptiededittext2.R;

/**
 * 带删除按钮的EditText 适配银行卡输入
 */

/**
 * 日期:2020/6/22
 * <p>
 * 作者:lxl
 * <p>
 * 描述: 可清空的EditText，可设置长度判断类型
 * 使用方式:
 * <p>
 * 设置长度：setMaxLength
 */
@SuppressLint("AppCompatCustomView")
public class EmptiedEditText extends EditText {

    /**
     * 清楚按钮的图标
     */
    private Drawable drawableEmptied;
    /**
     * 设置银行卡四位一空格
     */
    int beforeTextLength = 0;
    int onTextLength = 0;
    boolean isChanged = false;
    int location = 0;
    private char[] tempChar;
    private StringBuffer buffer = new StringBuffer();
    int spaceNumberB = 0;
    private OnTextLengthListener mOnTextLengthListener;
    private boolean isBankNoType;
    private int maxLength;
    /**
     * 外层布局
     */
    ViewGroup viewGroup;
    int viewGroupBackGround;

    /**
     * 最大长度
     *
     * @param maxLength
     */
    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
        invalidate();
    }

    /**
     * 银行卡类型
     *
     * @param bankNoType
     */
    public void setBankNoType(boolean bankNoType) {
        isBankNoType = bankNoType;
        invalidate();
    }

    public interface OnTextLengthListener {
        /**
         * 按钮可点击
         */
        void onButtonEnable();

        /**
         * 按钮不可点击
         */
        void onButtonUnEnable();
    }

    public void setOnTextLengthListener(OnTextLengthListener onTextLengthListener) {
        this.mOnTextLengthListener = onTextLengthListener;
    }


    public EmptiedEditText(Context context) {
        super(context);
        init(context, null);
    }

    public EmptiedEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public EmptiedEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        // 获取自定义属性
        drawableEmptied = getResources().getDrawable(R.mipmap.icon_delete);
        updateIconClear();

        // 设置TextWatcher用于更新清除按钮显示状态
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (isBankNoType) {
                    beforeTextLength = s.length();
                    if (buffer.length() > 0) {
                        buffer.delete(0, buffer.length());
                    }
                    spaceNumberB = 0;
                    for (int i = 0; i < s.length(); i++) {
                        if (s.charAt(i) == ' ') {
                            spaceNumberB++;
                        }
                    }
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (viewGroup != null) {
                    if (s.length() > 0) {
                        viewGroup.setBackgroundColor(0xffFFFFFF);
                    } else {
                        viewGroup.setBackgroundColor(viewGroupBackGround);
                    }
                }

                if (isBankNoType) {

                    onTextLength = s.length();
                    buffer.append(s.toString());
                    if (onTextLength == beforeTextLength || onTextLength <= 3 || isChanged) {
                        isChanged = false;
                        Log.e("~~~~~", "onTextChanged: " + "11111111111");
                        return;
                    }
                    isChanged = true;
                    Log.e("~~~~~", "onTextChanged: " + "222222222");

                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                updateIconClear();
                if (isChanged && isBankNoType) {
                    location = getSelectionEnd();
                    int index = 0;
                    while (index < buffer.length()) {
                        if (buffer.charAt(index) == ' ') {
                            buffer.deleteCharAt(index);
                        } else {
                            index++;
                        }
                    }

                    index = 0;
                    int spaceNumberC = 0;
                    while (index < buffer.length()) {
                        if ((index == 4 || index == 9 || index == 14 || index == 19)) {
                            buffer.insert(index, ' ');
                            spaceNumberC++;
                        }
                        index++;
                    }

                    if (spaceNumberC > spaceNumberB) {
                        location += (spaceNumberC - spaceNumberB);
                    }

                    tempChar = new char[buffer.length()];
                    buffer.getChars(0, buffer.length(), tempChar, 0);
                    String str = buffer.toString();
                    if (location > str.length()) {
                        location = str.length();
                    } else if (location < 0) {
                        location = 0;
                    }

                    setText(str);
                    Editable etable = getText();
                    Selection.setSelection(etable, location);
                    isChanged = false;
                }
                sendLengthState(s, maxLength);
            }
        });

        setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                updateIconClear();
            }
        });

    }

    /**
     * 发送editText的长度符合要求的状态
     *
     * @param s
     * @param maxLength
     */
    private void sendLengthState(Editable s, int maxLength) {
        int etLength = s.length();
        String etText = s.toString();
        if (maxLength == 23) {
            //银行卡
            if (etLength <= 23 && etLength >= 18) {
                etText.replaceAll(" ", "");
                dispatchEnable();
            } else {
                dispatchUnEnable();
            }
        } else if (maxLength == 18 && CheckUtils.checkIdNumber(etText)) {
            //身份证
            if (maxLength == etLength) {
                dispatchEnable();
            } else {
                dispatchUnEnable();
            }
        } else if (maxLength != 0) {
            //纯数字
            if (maxLength == etLength) {
                dispatchEnable();
            } else {
                dispatchUnEnable();
            }
        } else {
            //姓名类
            if (etLength != 0) {
                dispatchEnable();
            } else {
                dispatchUnEnable();
            }

        }
    }

    private void dispatchEnable() {
        if (mOnTextLengthListener != null) {
            mOnTextLengthListener.onButtonEnable();
        }
    }

    private void dispatchUnEnable() {
        if (mOnTextLengthListener != null) {
            mOnTextLengthListener.onButtonUnEnable();
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int xDown = (int) event.getX();
        if (event.getAction() == MotionEvent.ACTION_DOWN && xDown >= (getWidth() - getCompoundPaddingRight() * 2) && xDown < getWidth()) {
            // 清除按钮的点击范围 按钮自身大小 +-padding
            setText("");
            return false;
        }
        super.onTouchEvent(event);
        return true;
    }


    /**
     * 更新清除按钮图标显示
     */
    private void updateIconClear() {
        // 获取设置好的
        // drawableLeft、
        // drawableTop、
        // drawableRight、
        // drawableBottom
        Drawable[] drawables = getCompoundDrawables();
        if (length() > 0 && isFocused()) {
            showIcon(true, drawables);
        } else {
            showIcon(false, drawables);
        }
    }

    private void showIcon(boolean isShow, Drawable[] drawables) {
        if (isShow) {
            setCompoundDrawablesWithIntrinsicBounds(drawables[0], drawables[1], drawableEmptied,
                    drawables[3]);
        } else {
            setCompoundDrawablesWithIntrinsicBounds(drawables[0], drawables[1], null,
                    drawables[3]);
        }
    }

    /**
     * 清空文本的方法
     */
    public void clearText() {
        setText("");
    }

    /**
     * 外层布局必选项的操作，传进来外层布局，可设置颜色值
     */
    public void layout(ViewGroup viewGroup, int viewGroupBackGround) {
        this.viewGroup = viewGroup;
        this.viewGroupBackGround = viewGroupBackGround;
        viewGroup.setBackgroundColor(viewGroupBackGround);
    }
}