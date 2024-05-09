package com.polipost.maker.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.text.LineBreaker;
import android.os.Build;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.method.TransformationMethod;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.AppCompatTextView;

public class ResizeTextView extends AppCompatTextView {
    public static float _minTextSize;
    private final RectF _availableSpaceRect;
    private final SizeTester _sizeTester;
    private boolean _initialized;
    private int _maxLines;
    private float _maxTextSize;
    private TextPaint _paint;
    private float _spacingAdd;
    private float _spacingMult;
    private int _widthLimit;
    private int outline_color = Color.TRANSPARENT;
    private float outline_width = 0.0f;


    public ResizeTextView(Context context) {
        this(context, null, 16842884);
        init(context);
    }

    public ResizeTextView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16842884);
        init(context);
    }

    public ResizeTextView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context);
        this._availableSpaceRect = new RectF();
        this._spacingMult = 1.0f;
        this._spacingAdd = 0.0f;
        this._initialized = false;
        outline_color = Color.parseColor("#000000");
        outline_width = 0.0f;
        _minTextSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12.0f, getResources().getDisplayMetrics());
        this._maxTextSize = getTextSize();
        this._paint = new TextPaint(getPaint());

        if (this._maxLines == 0) {
            this._maxLines = -1;
        }
        this._sizeTester = new TextSize();
        this._initialized = true;
    }

    public void init(Context context) {
    }


    public boolean isValidWordWrap(char c, char c2) {
        return c == ' ' || c == '-';
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setJustify() {
        setJustificationMode(LineBreaker.JUSTIFICATION_MODE_INTER_WORD);
    }

    public void setAllCaps(boolean z) {
        super.setAllCaps(z);
        adjustTextSize();
    }

    public void setTypeface(Typeface typeface) {
        super.setTypeface(typeface);
        adjustTextSize();
    }

    public void setTextSize(float f) {
        this._maxTextSize = f;
        adjustTextSize();
    }

    public int getMaxLines() {
        return this._maxLines;
    }

    public void setMaxLines(int i) {
        super.setMaxLines(i);
        this._maxLines = i;
        adjustTextSize();
    }

    public void setSingleLine() {
        super.setSingleLine();
        this._maxLines = 1;
        adjustTextSize();
    }

    public void setOutlineColor(int color) {
        outline_color = color;
        invalidate();
    }

    public void setOutlineWidth(float width) {
        outline_width = width;
        invalidate();
    }

    public void setSingleLine(boolean z) {
        super.setSingleLine(z);
        if (z) {
            this._maxLines = 1;
        } else {
            this._maxLines = -1;
        }
        adjustTextSize();
    }

    public void setLines(int i) {
        super.setLines(i);
        this._maxLines = i;
        adjustTextSize();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //draw standard text
        super.onDraw(canvas);
        //draw outline

        if (outline_width != 0) {
            Paint paint = getPaint();
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeJoin(Paint.Join.ROUND);
            paint.setStrokeCap(Paint.Cap.ROUND);
            paint.setAntiAlias(true);

            paint.setStrokeWidth(paint.getTextSize() * outline_width);
            int color_tmp = paint.getColor();
            setTextColor(outline_color);
            //restore
            drawStroke(this, canvas, paint);
            super.onDraw(canvas);

            setTextColor(color_tmp);
            paint.setStyle(Paint.Style.FILL);
        }

//        if (outline_width > 0) {
//            Paint paint = _paint;
//            paint.setStyle(Paint.Style.STROKE);
////            paint.setStrokeJoin(Paint.Join.ROUND);
////            paint.setStrokeCap(Paint.Cap.ROUND);
//            paint.setAntiAlias(true);
//
//            paint.setStrokeWidth(paint.getTextSize() * outline_width);
//            int color_tmp = paint.getColor();
//            setTextColor(outline_color);
//
//            drawStroke(this, canvas, paint);
//
//            //restore
//            setTextColor(color_tmp);
//            paint.setStyle(Paint.Style.FILL);
//
//        }
        super.onDraw(canvas);
    }

    @Override // androidx.appcompat.widget.AppCompatTextView
    public void setTextSize(int i, float f) {
        Resources resources;
        Context context = getContext();
        if (context == null) {
            resources = Resources.getSystem();
        } else {
            resources = context.getResources();
        }
        this._maxTextSize = TypedValue.applyDimension(i, f, resources.getDisplayMetrics());
        adjustTextSize();
    }

    public void setLineSpacing(float f, float f2) {
        super.setLineSpacing(f, f2);
        this._spacingMult = f2;
        this._spacingAdd = f;
    }

    public void setMinTextSize(float f) {
        _minTextSize = f;
        adjustTextSize();
    }

    private void adjustTextSize() {
        if (this._initialized) {
            int i = (int) _minTextSize;
            int measuredHeight = (getMeasuredHeight() - getCompoundPaddingBottom()) - getCompoundPaddingTop();
            int measuredWidth = (getMeasuredWidth() - getCompoundPaddingLeft()) - getCompoundPaddingRight();
            this._widthLimit = measuredWidth;
            if (measuredWidth > 0) {
                this._paint = new TextPaint(getPaint());
                this._availableSpaceRect.right = (float) this._widthLimit;
                this._availableSpaceRect.bottom = (float) measuredHeight;
                superSetTextSize(i);
            }
        }
    }

    private void superSetTextSize(int i) {
        super.setTextSize(0, (float) binarySearch(i, (int) this._maxTextSize, this._sizeTester, this._availableSpaceRect));
    }

    private int binarySearch(int i, int i2, SizeTester sizeTester, RectF rectF) {
        int i3 = i2 - 1;
        int i4 = i;
        while (i <= i3) {
            int i5 = (i + i3) >>> 1;
            int onTestSize = sizeTester.onTestSize(i5, rectF);
            if (onTestSize < 0) {
                int i6 = i5 + 1;
                i4 = i;
                i = i6;
            } else if (onTestSize <= 0) {
                return i5;
            } else {
                i4 = i5 - 1;
                i3 = i4;
            }
        }
        return i4;
    }

    @Override // androidx.appcompat.widget.AppCompatTextView
    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        super.onTextChanged(charSequence, i, i2, i3);
        adjustTextSize();
    }

    public void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        if (i != i3 || i2 != i4) {
            adjustTextSize();
        }
    }

    private void drawStroke(@NonNull final TextView textView, @NonNull final Canvas canvas, Paint paint) {

        paint.setTypeface(paint.getTypeface());

        final Layout layout = textView.getLayout();

        final int lineCount = textView.getLineCount();
        final int layoutHeight = layout.getHeight();
        final int viewWidth = textView.getWidth();
        final int viewHeight = textView.getHeight();
        final int paddingLeft = textView.getCompoundPaddingLeft();
        final int paddingRight = textView.getCompoundPaddingRight();
        final int paddingTop = textView.getCompoundPaddingTop();
        final int paddingBottom = textView.getCompoundPaddingBottom();

        final int spaceHeight = Math.max((viewHeight - paddingTop - paddingBottom) - layoutHeight, 0);
        final int verticalGravity = getVerticalGravity(textView);

        final int clipTop, clipBottom;

        if (verticalGravity == Gravity.BOTTOM) {
            clipBottom = (viewHeight > layoutHeight) ? viewHeight + paddingTop : layoutHeight + paddingTop;
            final int yLayoutStart = viewHeight - paddingBottom - layoutHeight;
            clipTop = (yLayoutStart > paddingTop) ? yLayoutStart : (paddingTop << 1) - yLayoutStart;
        } else {
            clipBottom = viewHeight - paddingBottom;
            clipTop = paddingTop;
        }

        if (clipTop > clipBottom) {
            return;
        }
        if (paddingLeft > viewWidth - paddingRight) {
            return;
        }

        canvas.save();
        canvas.clipRect(paddingLeft, clipTop, viewWidth - paddingRight, clipBottom);

        final String originalText = textView.getText().toString();
        final int start = (verticalGravity != Gravity.BOTTOM) ? 0 : lineCount - 1;
        final int add = (verticalGravity != Gravity.BOTTOM) ? 1 : -1;

        for (int i = start; getLoopCondition(i, lineCount, verticalGravity); i += add) {
            final String text = originalText.substring(
                    layout.getLineStart(i), layout.getLineEnd(i));

            final int x = (int) layout.getLineLeft(i) + paddingLeft;
            int y = layout.getLineBaseline(i) + paddingTop;

            switch (verticalGravity) {
                case Gravity.BOTTOM:
                    y += spaceHeight;
                    break;
                case Gravity.CENTER_VERTICAL:
                    y += (spaceHeight >> 1);
                    break;
                default:
                    break;
            }

            if (verticalGravity != Gravity.BOTTOM) {
                if (y - textView.getLineHeight() >= clipBottom) {
                    break;
                }
            } else {
                if (y + textView.getLineHeight() <= clipTop) {
                    break;
                }
            }

            canvas.drawText(text, x, y, paint);
        }
//        canvas.restore();
    }

    private int getVerticalGravity(@NonNull final TextView textView) {
        if ((textView.getGravity() & Gravity.TOP) == Gravity.TOP) {
            return Gravity.TOP;
        } else if ((textView.getGravity() & Gravity.BOTTOM) == Gravity.BOTTOM) {
            return Gravity.BOTTOM;
        } else {
            return Gravity.CENTER_VERTICAL;
        }
    }

    private boolean getLoopCondition(final int idx, final int count, final int verticalGravity) {
        if (verticalGravity == Gravity.BOTTOM) {
            return idx >= 0;
        } else {
            return idx < count;
        }
    }

    public interface SizeTester {
        int onTestSize(int i, RectF rectF);
    }

    class TextSize implements SizeTester {
        final RectF textRect = new RectF();

        TextSize() {
        }

        @Override // com.sk.businesscardmaker.view.ResizeTextView.SizeTester
        public int onTestSize(int i, RectF rectF) {

            String str;
            ResizeTextView.this._paint.setTextSize((float) i);
            TransformationMethod transformationMethod = ResizeTextView.this.getTransformationMethod();
            if (transformationMethod != null) {
                str = transformationMethod.getTransformation(ResizeTextView.this.getText(), ResizeTextView.this).toString();
            } else {
                str = ResizeTextView.this.getText().toString();
            }
            if (ResizeTextView.this.getMaxLines() == 1) {
                this.textRect.bottom = ResizeTextView.this._paint.getFontSpacing();
                this.textRect.right = ResizeTextView.this._paint.measureText(str);
            } else {
                StaticLayout staticLayout = new StaticLayout(str, ResizeTextView.this._paint, ResizeTextView.this._widthLimit, Layout.Alignment.ALIGN_NORMAL, ResizeTextView.this._spacingMult, ResizeTextView.this._spacingAdd, true);
                if (ResizeTextView.this.getMaxLines() != -1 && staticLayout.getLineCount() > ResizeTextView.this.getMaxLines()) {
                    return 1;
                }
                this.textRect.bottom = (float) staticLayout.getHeight();
                int lineCount = staticLayout.getLineCount();
                int i2 = -1;
                for (int i3 = 0; i3 < lineCount; i3++) {
                    int lineEnd = staticLayout.getLineEnd(i3);
                    if (i3 < lineCount - 1 && lineEnd > 0 && !ResizeTextView.this.isValidWordWrap(str.charAt(lineEnd - 1), str.charAt(lineEnd))) {
                        return 1;
                    }
                    if (((float) i2) < staticLayout.getLineRight(i3) - staticLayout.getLineLeft(i3)) {
                        i2 = ((int) staticLayout.getLineRight(i3)) - ((int) staticLayout.getLineLeft(i3));
                    }
                }
                this.textRect.right = (float) i2;
            }
            this.textRect.offsetTo(0.0f, 0.0f);
            return rectF.contains(this.textRect) ? -1 : 1;
        }
    }
}
