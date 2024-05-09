package com.polipost.maker.Utils.utility.textWork;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.text.Html;
import android.text.SpannableString;
import android.text.style.ScaleXSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.IntRange;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.ColorUtils;
import androidx.core.view.ViewCompat;

import com.polipost.maker.Models.ItemObjects;
import com.polipost.maker.R;
import com.polipost.maker.Utils.Globals;
import com.polipost.maker.Utils.Methods;
import com.polipost.maker.view.ResizeTextView;

import java.util.ArrayList;

public class AutoFitTextRel extends RelativeLayout implements MultiTouchListener.TouchCallbackListener {
    private static final String TAG = "AutofitTextRel";
    public boolean isMultiTouchEnabled = true;
    double angle = 0.0d;
    int baseH;
    int baseW;
    int baseX;
    int baseY;
    float cX = 0.0f;
    float cY = 0.0f;
    double dAngle = 0.0d;
    int height;
    int mergeL;
    int mergeT;
    float ratio;
    Animation scale;
    int sh = 1794;
    int sw = 1080;
    double tAngle = 0.0d;
    double vAngle = 0.0d;
    int width;
    Animation zoomInScale;
    Animation zoomOutScale;
    float widthMain = 0.0f;
    float heightMain = 0.0f;
    private Methods methods;
    private ImageView background_iv;
    private int bgAlpha = 255;
    private int bgColor = 0;
    private String bgDrawable = "0";
    private ImageView border_iv;
    private boolean isAllCaps = false;
    private Activity activity;
    private ImageView delete_iv;
    private int f27s;
    private String field_four = "";
    private int field_one = 0;
    private String field_three = "";
    private String field_two = "0,0";
    private String fontName = "";
    private GestureDetector gd = null;
    private int he = 1;
    private String type = "";
    private String originX = "";
    private String originY = "";
    private String stroke = "";
    private String strokeLineCap = "";
    private String strokeLineJoin = "";
    private String strokeMeterLimit = "";
    private String scaleX = "";
    private String scaleY = "";
    private String flipX = "";
    private String flipY = "";
    private String shadow = "";
    private String visible = "";
    private String clipTo = "";
    private String backgroundColor = "";
    private String fillRule = "";
    private String globleCompositeOperation = "";
    private String name = "";
    private String crossLine = "";
    private String alignX = "";
    private String alignY = "";
    private String meetOrSlice = "";
    private String lookPosition = "";
    private String fontSize = "";
    private String fontWeight = "";
    private String fontFamily = "";
    private String fontStyle = "";
    private String lineHeight = "";
    private String textDecoration = "";
    private String textAlign = "";
    private String textBackgroundColor = "";
    private boolean isBold = false;
    private boolean isItalic = false;
    private String rx = "";
    private String ry = "";
    private boolean isBorderVisible = false;
    private boolean isUnderLine = false;
    private boolean isOutLine = false;
    private int leftMargin = 0;
    private float leftRightShadow = 0.0f;
    private int outlineWidth = 0;
    private int outlineColor = 0;
    private String alignDirection = "center";
    private TouchEventListener listener = null;
    private OnTouchListener scaleTouchListener = new ScaleTouchListener();
    private int progress = 0;
    private OnTouchListener rotateTouchListener = new OnTouchListener() {
        public boolean onTouch(View view, MotionEvent motionEvent) {
            AutoFitTextRel autofitTextRel = (AutoFitTextRel) view.getParent();
            int action = motionEvent.getAction();
            if (action == 0) {
                if (autofitTextRel != null) {
                    autofitTextRel.requestDisallowInterceptTouchEvent(true);
                }
                if (AutoFitTextRel.this.listener != null) {
                    AutoFitTextRel.this.listener.onRotateDown(AutoFitTextRel.this);
                }
                Rect rect = new Rect();
                ((View) view.getParent()).getGlobalVisibleRect(rect);
                AutoFitTextRel.this.cX = rect.exactCenterX();
                AutoFitTextRel.this.cY = rect.exactCenterY();
                AutoFitTextRel.this.vAngle = (double) ((View) view.getParent()).getRotation();
                AutoFitTextRel autoFitTextRel2 = AutoFitTextRel.this;
                autoFitTextRel2.tAngle = (Math.atan2((double) (autoFitTextRel2.cY - motionEvent.getRawY()), (double) (AutoFitTextRel.this.cX - motionEvent.getRawX())) * 180.0d) / 3.141592653589793d;
                AutoFitTextRel autoFitTextRel3 = AutoFitTextRel.this;
                autoFitTextRel3.dAngle = autoFitTextRel3.vAngle - AutoFitTextRel.this.tAngle;
            } else if (action != 1) {
                if (action == 2) {
                    if (autofitTextRel != null) {
                        autofitTextRel.requestDisallowInterceptTouchEvent(true);
                    }
                    if (AutoFitTextRel.this.listener != null) {
                        AutoFitTextRel.this.listener.onRotateMove(AutoFitTextRel.this);
                    }
                    AutoFitTextRel autoFitTextRel4 = AutoFitTextRel.this;
                    autoFitTextRel4.angle = (Math.atan2((double) (autoFitTextRel4.cY - motionEvent.getRawY()), (double) (AutoFitTextRel.this.cX - motionEvent.getRawX())) * 180.0d) / 3.141592653589793d;
                    ((View) view.getParent()).setRotation((float) (AutoFitTextRel.this.angle + AutoFitTextRel.this.dAngle));
                    ((View) view.getParent()).invalidate();
                    ((View) view.getParent()).requestLayout();
                }
            } else if (AutoFitTextRel.this.listener != null) {
                AutoFitTextRel.this.listener.onRotateUp(AutoFitTextRel.this);
            }
            return true;
        }
    };
    private ImageView rotate_iv;
    private float rotation;
    private ImageView scale_iv;
    private int shadowColor = 0;
    private int shadowColorProgress = 255;
    private int shadowProg = 0;
    private int tAlpha = 100;
    private int tColor = ViewCompat.MEASURED_STATE_MASK;
    private String text = "";
    private String size = "";
    private Path textPath;
    private ResizeTextView text_iv;
    private float topBottomShadow = 0.0f;
    private int topMargin = 0;
    private int wi = 1;

    private String tempWidth = "0";
    private String tempHeight = "0";
    private String tempLeft = "0";
    private String tempTop = "0";

    private int xRotateProg = 0;
    private int yRotateProg = 0;
    private int zRotateProg = 0;

    public AutoFitTextRel(Activity activity) {
        super(activity);
        init(activity);
    }

    public AutoFitTextRel(Activity activity, AttributeSet attributeSet) {
        super(activity, attributeSet);
        init(activity);
    }

    public AutoFitTextRel(Activity activity, AttributeSet attributeSet, int i) {
        super(activity, attributeSet, i);
        init(activity);
    }

    public void deleteView() {
        if (delete_iv != null) {
            delete_iv.performClick();
        }
    }

    public void setMainLayoutWH(float f, float f2) {
        this.widthMain = f;
        this.heightMain = f2;
    }

    public void visibleContoll() {
    }

    public AutoFitTextRel setOnTouchCallbackListener(TouchEventListener touchEventListener) {
        this.listener = touchEventListener;
        return this;
    }

    public void setDrawParams() {
        invalidate();
    }

    public void init(Activity activity) {
        this.activity = activity;
        methods = new Methods(activity);
        Display defaultDisplay = ((Activity) activity).getWindowManager().getDefaultDisplay();
        Point point = new Point();
        defaultDisplay.getSize(point);
        this.width = point.x;
        int i = point.y;
        this.height = i;
        this.ratio = ((float) this.width) / ((float) i);
        this.text_iv = new ResizeTextView(this.activity);
        this.scale_iv = new ImageView(this.activity);
        this.border_iv = new ImageView(this.activity);
        this.background_iv = new ImageView(this.activity);
        this.delete_iv = new ImageView(this.activity);
        this.rotate_iv = new ImageView(this.activity);
        this.f27s = dpToPx(this.activity, 30);
        this.wi = dpToPx(this.activity, 200);
        this.he = dpToPx(this.activity, 200);
        this.scale_iv.setImageResource(R.drawable.sticker_scale);
        boolean z = false;
        this.background_iv.setImageResource(0);
        this.rotate_iv.setImageResource(R.drawable.sticker_rotate);
        this.delete_iv.setImageResource(R.drawable.sticker_delete1);
        LayoutParams layoutParams = new LayoutParams(this.wi, this.he);
        int i2 = this.f27s;
        LayoutParams layoutParams2 = new LayoutParams(i2, i2);
        layoutParams2.addRule(12);
        layoutParams2.addRule(11);
        int i3 = this.f27s;
        LayoutParams layoutParams3 = new LayoutParams(i3, i3);
        layoutParams3.addRule(12);
        layoutParams3.addRule(9);
        LayoutParams layoutParams4 = new LayoutParams(-1, -1);
        if (Build.VERSION.SDK_INT >= 17) {
            z = true;
        }
        if (z) {
            layoutParams4.addRule(17);
        } else {
            layoutParams4.addRule(1);
        }
        int i4 = this.f27s;
        LayoutParams layoutParams5 = new LayoutParams(i4, i4);
        layoutParams5.addRule(10);
        layoutParams5.addRule(9);
        LayoutParams layoutParams6 = new LayoutParams(-1, -1);
        LayoutParams layoutParams7 = new LayoutParams(-1, -1);
        setLayoutParams(layoutParams);
        setBackgroundResource(R.drawable.border_gray);
        addView(this.background_iv);
        this.background_iv.setLayoutParams(layoutParams7);
        this.background_iv.setScaleType(ImageView.ScaleType.FIT_XY);
        addView(this.border_iv);
        this.border_iv.setLayoutParams(layoutParams6);
        this.border_iv.setTag("border_iv");
        addView(this.text_iv);
        this.text_iv.setText(this.text);
        this.text_iv.setTextColor(this.tColor);
        this.text_iv.setTextSize(300.0f);
        this.text_iv.setLayoutParams(layoutParams4);
        this.text_iv.setGravity(17);
        this.text_iv.setMinTextSize(10.0f);
        addView(this.delete_iv);
        this.delete_iv.setLayoutParams(layoutParams5);
        this.delete_iv.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
                final ViewGroup viewGroup = (ViewGroup) AutoFitTextRel.this.getParent();
                AutoFitTextRel.this.zoomInScale.setAnimationListener(new Animation.AnimationListener() {
                    /* class com.sk.businesscardmaker.textWork.AutofitTextRel.AnonymousClass2.AnonymousClass1 */

                    public void onAnimationRepeat(Animation animation) {
                    }

                    public void onAnimationStart(Animation animation) {
                    }

                    public void onAnimationEnd(Animation animation) {
                        viewGroup.removeView(AutoFitTextRel.this);
                    }
                });
                AutoFitTextRel.this.text_iv.startAnimation(AutoFitTextRel.this.zoomInScale);
                AutoFitTextRel.this.background_iv.startAnimation(AutoFitTextRel.this.zoomInScale);
                AutoFitTextRel.this.setBorderVisibility(false);
                if (AutoFitTextRel.this.listener != null) {
                    AutoFitTextRel.this.listener.onDelete();
                }
            }
        });
        addView(this.rotate_iv);
        this.rotate_iv.setLayoutParams(layoutParams3);
        this.rotate_iv.setOnTouchListener(this.rotateTouchListener);
        addView(this.scale_iv);
        this.scale_iv.setLayoutParams(layoutParams2);
        this.scale_iv.setTag("scale_iv");
        this.scale_iv.setOnTouchListener(this.scaleTouchListener);
        this.rotation = getRotation();
        this.scale = AnimationUtils.loadAnimation(getContext(), R.anim.textlib_scale_anim_view);
        this.zoomOutScale = AnimationUtils.loadAnimation(getContext(), R.anim.textlib_scale_zoom_out_view);
        this.zoomInScale = AnimationUtils.loadAnimation(getContext(), R.anim.textlib_scale_zoom_in_view);
        initGD();
        this.isMultiTouchEnabled = setDefaultTouchListener(true);
    }

    public void goneContoller() {
        this.scale_iv.setVisibility(View.GONE);
        this.rotate_iv.setVisibility(View.GONE);
        this.delete_iv.setVisibility(View.GONE);
        invalidate();
    }

    public void applyLetterSpacing(float f) {
        if (this.text != null) {
            StringBuilder sb = new StringBuilder();
            int i = 0;
            while (i < this.text.length()) {
                sb.append("" + this.text.charAt(i));
                i++;
                if (i < this.text.length()) {
                    sb.append(" ");
                }
            }
            SpannableString spannableString = new SpannableString(sb.toString());
            if (sb.toString().length() > 1) {
                for (int i2 = 1; i2 < sb.toString().length(); i2 += 2) {
                    spannableString.setSpan(new ScaleXSpan((1.0f + f) / 10.0f), i2, i2 + 1, 33);
                }
            }
            this.text_iv.setText(spannableString, TextView.BufferType.SPANNABLE);
        }
    }

    public void applyLineSpacing(float f) {
        this.text_iv.setLineSpacing(f, 1.0f);
    }

    public void setBoldFont() {
        if (this.isBold) {
            this.isBold = false;
            this.text_iv.setTypeface(Typeface.DEFAULT);
            return;
        }
        this.isBold = true;
        this.text_iv.setTypeface(Typeface.DEFAULT_BOLD);
    }

    public void setTextSize(int i) {

        try {
            Log.e(TAG, "setTextSize: ");
//            this.text_iv.setTextSize((float) i);
            this.text_iv.setTextSize((float) 500.0f);
            LayoutParams layoutParams = (LayoutParams) getLayoutParams();
            int width2 = this.text_iv.getWidth();
            int height2 = this.text_iv.getHeight();
            layoutParams.width = width2;
            layoutParams.height = height2;
            setLayoutParams(layoutParams);
            invalidate();
            Log.e(TAG, "setTextSize: 2 ");
        } catch (Exception e) {
            Log.e(TAG, "instance initializer: " + e.getMessage());
        }
    }

    public void setCapitalFont() {
        if (!isAllCaps) {
            isAllCaps = true;
            this.text_iv.setText(text_iv.getText().toString().toUpperCase());
            return;
        }
        isAllCaps = false;
        this.text_iv.setText(text_iv.getText().toString().toLowerCase());
    }

    public void setUnderLineFont() {
        if (this.isUnderLine) {
            this.isUnderLine = false;
            this.text_iv.setText(Html.fromHtml(this.text.replace("<u>", "").replace("</u>", "")));
            return;
        }
        this.isUnderLine = true;
        ResizeTextView resizeTextView = this.text_iv;
        resizeTextView.setText(Html.fromHtml("<u>" + this.text + "</u>"));
    }

    public void setOutline(@IntRange(from = 0, to = 20) int progress) {
        if (progress > 0 && progress < 20) {
            outlineWidth = progress;
            float value = (float) outlineWidth / 100;
//            if (this.isOutLine) {
//                this.isOutLine = false;
//                this.text_iv.setOutlineWidth(0.0f);
//                return;
//            }
//            this.isOutLine = true;
            this.text_iv.setOutlineWidth(value);
        }
    }

    public void setOutlineColor(@ColorInt int color) {
        outlineColor = color;
        this.text_iv.setOutlineColor(outlineColor);
    }

    public void setItalicFont() {
        if (this.isItalic) {
            this.isItalic = false;
            TextView textView = new TextView(this.activity);
            textView.setText(this.text);
            if (this.isBold) {
                textView.setTypeface(textView.getTypeface(), Typeface.BOLD);
            } else {
                textView.setTypeface(textView.getTypeface(), Typeface.NORMAL);
            }
            this.text_iv.setTypeface(textView.getTypeface());
            return;
        }
        this.isItalic = true;
        TextView textView2 = new TextView(this.activity);
        textView2.setText(this.text);
        if (this.isBold) {
            textView2.setTypeface(textView2.getTypeface(), Typeface.BOLD_ITALIC);
        } else {
            textView2.setTypeface(textView2.getTypeface(), Typeface.ITALIC);
        }
        this.text_iv.setTypeface(textView2.getTypeface());
    }

    public void setAlignLeft() {
        alignDirection = "left";
        this.text_iv.setGravity(19);
    }

    public void setAlignCenter() {
        alignDirection = "center";
        this.text_iv.setGravity(17);
    }

    public void setAlignRight() {
        alignDirection = "right";
        this.text_iv.setGravity(21);
    }

    public boolean setDefaultTouchListener(boolean z) {
        if (z) {
            setOnTouchListener(new MultiTouchListener().enableRotation(true).setOnTouchCallbackListener(this).setGestureListener(this.gd));
            return true;
        }
        setOnTouchListener(null);
        return false;
    }

    public boolean getBorderVisibility() {
        return this.isBorderVisible;
    }

    public void setBorderVisibility(boolean z) {
        this.isBorderVisible = z;
        if (!z) {
            this.border_iv.setVisibility(View.GONE);
            this.scale_iv.setVisibility(View.GONE);
            this.delete_iv.setVisibility(View.GONE);
            this.rotate_iv.setVisibility(View.GONE);
            setBackgroundResource(0);
        } else if (this.border_iv.getVisibility() != VISIBLE) {
            this.border_iv.setVisibility(View.VISIBLE);
            this.scale_iv.setVisibility(View.VISIBLE);
            this.delete_iv.setVisibility(View.VISIBLE);
            this.rotate_iv.setVisibility(View.VISIBLE);
            setBackgroundResource(R.drawable.border_gray);
            this.text_iv.startAnimation(this.scale);
        }
    }

    public String getText() {
        return this.text_iv.getText().toString();
    }

    public void setText(String str) {
        this.text_iv.setText(str);
        this.text = str;
        this.text_iv.startAnimation(this.zoomOutScale);
    }

    public void setTextFont(String str) {
        try {
            str = str.replace(" ", "_").replace(".ttf", "");
            Typeface typeface = ResourcesCompat.getFont(activity, getResources().getIdentifier(str, "font", activity.getPackageName()));
            this.text_iv.setTypeface(typeface);
            this.fontName = str;
            this.text_iv.invalidate();

//            File file = new File(Configure.GetFontDir(this.activity), str);
//            if (file.exists()) {
//                try {
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
        } catch (Exception unused) {
            Log.e(TAG, "Exception: setTextFont : " + unused.getMessage());
        }
    }

    public String getFontName() {
        return this.fontName;
    }

    public int getTextColor() {
        return this.tColor;
    }

    public void setTextColor(int i) {
        this.text_iv.setTextColor(i);
        this.tColor = i;
    }

    public int getTextAlpha() {
        return this.tAlpha;
    }

    public void setTextAlpha(int i) {
        this.text_iv.setAlpha(((float) i) / 100.0f);
        this.tAlpha = i;
    }

    public int getTextShadowColor() {
        return this.shadowColor;
    }

    public void setTextShadowColor(int i) {
        this.shadowColor = i;
        int alphaComponent = ColorUtils.setAlphaComponent(i, this.shadowColorProgress);
        this.shadowColor = alphaComponent;
        this.text_iv.setShadowLayer((float) this.shadowProg, this.leftRightShadow, this.topBottomShadow, alphaComponent);
    }

    public void setTextShadowOpacity(int i) {
        this.shadowColorProgress = i;
        int alphaComponent = ColorUtils.setAlphaComponent(this.shadowColor, i);
        this.shadowColor = alphaComponent;
        this.text_iv.setShadowLayer((float) this.shadowProg, this.leftRightShadow, this.topBottomShadow, alphaComponent);
    }

    public void setLeftRightShadow(float f) {
        this.leftRightShadow = f;
        this.text_iv.setShadowLayer((float) this.shadowProg, f, this.topBottomShadow, this.shadowColor);
    }

    public void setTopBottomShadow(float f) {
        this.topBottomShadow = f;
        this.text_iv.setShadowLayer((float) this.shadowProg, this.leftRightShadow, f, this.shadowColor);
    }

    public int getTextShadowProg() {
        return this.shadowProg;
    }

    public void setTextShadowProg(int i) {
        this.shadowProg = i;
        this.text_iv.setShadowLayer((float) i, this.leftRightShadow, this.topBottomShadow, this.shadowColor);
    }

//    public void setTextOpacity(int i) {
//        this.textAlpha = i;
//        this.text_iv.setAlpha((float) i);
//    }

    public String getBgDrawable() {
        return this.bgDrawable;
    }

    public void setBgDrawable(String str) {
        this.bgDrawable = str;
        this.bgColor = 0;
        this.background_iv.setImageBitmap(getTiledBitmap(this.activity, getResources().getIdentifier(str, "drawable", this.activity.getPackageName()), this.wi, this.he));
        this.background_iv.setBackgroundColor(this.bgColor);
    }

    public int getBgColor() {
        return this.bgColor;
    }

    public void setBgColor(int i) {
        this.bgDrawable = "0";
        this.bgColor = i;
        this.background_iv.setImageBitmap(null);
        this.background_iv.setBackgroundColor(i);
    }

    public int getBgAlpha() {
        return this.bgAlpha;
    }

    public void setBgAlpha(int i) {
        this.background_iv.setAlpha(((float) i) / 255.0f);
        this.bgAlpha = i;
    }

    private String getStr(Object obj) {
        return obj != null ? String.valueOf(obj) : "";
    }

    // baki

    public ItemObjects getTextInfo() {
        ItemObjects objects = new ItemObjects(type == null || type.isEmpty() ? "i-text" : type, originX, originY, String.valueOf(getX()),
                String.valueOf(getY()), getStr(tempWidth), getStr(tempHeight), getStr(tColor), stroke, strokeLineCap, strokeLineJoin
                , strokeMeterLimit, scaleX, scaleY, getStr(getRotation()), flipX, flipY, getStr(tAlpha), getStr(outlineWidth), getStr(outlineColor)
                , getStr(shadowProg), getStr(shadowColor), visible, clipTo, backgroundColor, fillRule, globleCompositeOperation
                , true, name, "", crossLine, alignX, alignY, meetOrSlice
                , new ArrayList<>(), "0", "0", "0", "0", lookPosition, "", text, size, fontWeight, fontName, fontStyle
                , lineHeight, textDecoration, alignDirection, getStr(topBottomShadow), getStr(leftRightShadow), getStr(bgColor), isBold, isItalic, isUnderLine, isAllCaps, ItemObjects.REGULAR, rx, ry);

        return objects;
    }

    public ItemObjects getTextInfo(String newText) {
        ItemObjects objects = new ItemObjects(type == null || type.isEmpty() ? "i-text" : type, originX, originY, String.valueOf(getX()),
                String.valueOf(getY()), getStr(tempWidth), getStr(tempHeight), getStr(tColor), stroke, strokeLineCap, strokeLineJoin
                , strokeMeterLimit, scaleX, scaleY, getStr(getRotation()), flipX, flipY, getStr(tAlpha), getStr(outlineWidth), getStr(outlineColor)
                , getStr(shadowProg), getStr(shadowColor), visible, clipTo, backgroundColor, fillRule, globleCompositeOperation
                , true, name, "", crossLine, alignX, alignY, meetOrSlice
                , new ArrayList<>(), "0", "0", "0", "0", lookPosition, "", text, size, fontWeight, fontName, fontStyle
                , lineHeight, textDecoration, alignDirection, getStr(topBottomShadow), getStr(leftRightShadow), getStr(bgColor), isBold, isItalic, isUnderLine, isAllCaps, ItemObjects.REGULAR, rx, ry);

        objects.setText(newText);

        return objects;
    }

    public void setTextInfo(ItemObjects objects, boolean z) {

        try {
            tempWidth = objects.getWidth();
            tempHeight = objects.getHeight();
            tempLeft = objects.getLeft();
            tempTop = objects.getTop();
            float heMaker;
            float wiMaker;

            if (z) {
                wiMaker = Float.parseFloat(tempWidth);
                heMaker = Float.parseFloat(tempHeight);
            } else {
                wiMaker = Float.parseFloat(tempWidth) * Float.parseFloat(objects.getScaleX());
                heMaker = Float.parseFloat(tempHeight) * Float.parseFloat(objects.getScaleY());
            }


//            if (Globals.isIsFromDatabase()) {
//                this.wi = (int) wiMaker;
//                this.he = (int) heMaker;
//            } else {
            if (!z) {
                this.wi = (int) methods.getRatio(wiMaker);
                this.he = (int) methods.getRatio(heMaker);
            }
//            }

            this.text = objects.getText();
            this.fontName = objects.getFontFamily().toLowerCase();
            this.tColor = Color.parseColor(objects.getFill());
            this.tAlpha = Integer.parseInt(objects.getOpacity());
            this.shadowColor = Color.parseColor(objects.getShadowColor().equals("0") ? "#000000" : objects.getShadowColor());
            this.shadowProg = Integer.parseInt(objects.getShadow());
//            this.shadowProg = 0;
            this.isAllCaps = false;
            Log.e(TAG, "setTextInfo getTextBackgroundColor: " + objects.getTextBackgroundColor());
            String tempBgColor = objects.getTextBackgroundColor();
            this.bgColor = tempBgColor.trim().equals("0") ? Color.parseColor("#00000000") : Color.parseColor(tempBgColor);

//        this.bgDrawable = objects.getBG_DRAWABLE();
            this.bgAlpha = 0;
            this.rotation = Float.parseFloat(objects.getAngle());
            this.field_two = "";
            setText(this.text);
            //setTextSizes(Integer.parseInt(objects.getFontSize()));
            setTextFont(this.fontName);
            setTextColor(this.tColor);
            setTextAlpha(this.tAlpha);
            setTextShadowColor(this.shadowColor);
            setTextShadowProg(this.shadowProg);

            this.type = objects.getType();
            this.originX = objects.getOriginX();
            this.originY = objects.getOriginY();
            this.stroke = objects.getStroke();
            this.strokeLineCap = objects.getStrokeLineCap();
            this.strokeLineJoin = objects.getStrokeLineJoin();
            this.strokeMeterLimit = objects.getStrokeMiterLimit();
            this.scaleX = objects.getScaleX();
            this.scaleY = objects.getScaleY();
            this.flipX = objects.getFlipX();
            this.flipY = objects.getFlipY();
            this.shadow = objects.getShadow();
            this.visible = objects.getVisible();
            this.clipTo = objects.getClipTo();
            this.backgroundColor = objects.getBackgroundColor().equals("0") ? "#00000000" : objects.getBackgroundColor();
            this.fillRule = objects.getFillRule();
            this.globleCompositeOperation = objects.getGlobalCompositeOperation();
            this.name = objects.getName();
            this.crossLine = objects.getCrossOrigin();
            this.alignX = objects.getAlignX();
            this.alignY = objects.getAlignY();
            this.meetOrSlice = objects.getMeetOrSlice();
            this.lookPosition = objects.getLookPosition();
            this.fontSize = objects.getFontSize();
            this.fontWeight = objects.getFontWeight();
            this.fontFamily = objects.getFontFamily();
            this.fontStyle = objects.getFontStyle();
            this.lineHeight = objects.getLineHeight();
            this.textDecoration = objects.getTextDecoration();
            this.textAlign = objects.getTextAlign();
            this.textBackgroundColor = objects.getTextBackgroundColor();
            this.rx = objects.getRx();
            this.ry = objects.getRy();

            int i = this.bgColor;

            if (i != 0) {
                setBgColor(i);
            } else {
                this.background_iv.setBackgroundColor(0);
            }
            if (this.bgDrawable.equals("0")) {
                this.background_iv.setImageBitmap(null);
            } else {
                setBgDrawable(this.bgDrawable);
            }
            setBgAlpha(this.bgAlpha);
            setRotation(Float.parseFloat(objects.getAngle()));
            if (this.field_two.equals("")) {
                getLayoutParams().width = this.wi;
                getLayoutParams().height = this.he;

                if (Globals.isIsFromDatabase()) {
                    setX(Float.parseFloat(tempLeft));
                    setY(Float.parseFloat(tempTop));
                } else {
                    setX(methods.getRatio(tempLeft));
                    setY(methods.getRatio(tempTop));
                }

            } else {
                String[] split = this.field_two.split(",");
                int parseInt = Integer.parseInt(split[0]);
                int parseInt2 = Integer.parseInt(split[1]);
                ((LayoutParams) getLayoutParams()).leftMargin = parseInt;
                ((LayoutParams) getLayoutParams()).topMargin = parseInt2;
                getLayoutParams().width = this.wi;
                getLayoutParams().height = this.he;

                if (Globals.isIsFromDatabase()) {
                    setX((float) Float.parseFloat(tempLeft) + ((float) (parseInt * -1)));
                    setY((float) Float.parseFloat(tempTop) + ((float) (parseInt2 * -1)));
                } else {
                    setX((float) methods.getRatio(tempLeft) + ((float) (parseInt * -1)));
                    setY((float) methods.getRatio(tempTop) + ((float) (parseInt2 * -1)));
                }
            }

//            if (((float) this.wi) > this.widthMain) {
//                ((LayoutParams) getLayoutParams()).leftMargin = (int) (this.widthMain - ((float) this.wi));
//                setX((Float.parseFloat(objects.getLeft()) - ((float) 0.0f) + ((this.widthMain - ((float) this.wi)) * -1.0f)));
//            } else {
//                setX(Float.parseFloat(objects.getLeft()) - ((float) 0.0f));
//            }
//            if (((float) this.he) > this.heightMain) {
//                ((LayoutParams) getLayoutParams()).topMargin = (int) (this.heightMain - ((float) this.he));
//                setY((Float.parseFloat(objects.getTop()) - ((float) 0.0f) + ((this.heightMain - ((float) this.he)) * -1.0f)));
//            } else {
//                setY((Float.parseFloat(objects.getTop()) - ((float) 0.0f)));
//            }
            getLayoutParams().width = this.wi;
            getLayoutParams().height = this.he;
        } catch (Exception e) {
            Log.e(TAG, "Exception: setTextInfo: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public void setTextInfoWithoutFilter(ItemObjects objects, boolean z) {

        try {
            tempWidth = objects.getWidth();
            tempHeight = objects.getHeight();
            tempLeft = objects.getLeft();
            tempTop = objects.getTop();
            float heMaker;
            float wiMaker;

            if (z) {
                wiMaker = Float.parseFloat(tempWidth);
                heMaker = Float.parseFloat(tempHeight);
            } else {
                wiMaker = Float.parseFloat(tempWidth) * Float.parseFloat(objects.getScaleX());
                heMaker = Float.parseFloat(tempHeight) * Float.parseFloat(objects.getScaleY());
            }


//            if (Globals.isIsFromDatabase()) {
//                this.wi = (int) wiMaker;
//                this.he = (int) heMaker;
//            } else {
            if (!z) {
                this.wi = (int) methods.getRatio(wiMaker);
                this.he = (int) methods.getRatio(heMaker);
            }
//            }

            this.text = objects.getText();
            this.fontName = objects.getFontFamily().toLowerCase();
            this.tColor = Color.parseColor(objects.getFill());
            this.tAlpha = Integer.parseInt(objects.getOpacity());
            this.shadowColor = Color.parseColor(objects.getShadowColor().equals("0") ? "#000000" : objects.getShadowColor());
            this.shadowProg = Integer.parseInt(objects.getShadow());
//            this.shadowProg = 0;
            this.isAllCaps = false;
            Log.e(TAG, "setTextInfo getTextBackgroundColor: " + objects.getTextBackgroundColor());
            String tempBgColor = objects.getTextBackgroundColor();
            this.bgColor = tempBgColor.trim().equals("0") ? Color.parseColor("#00000000") : Color.parseColor(tempBgColor);

//        this.bgDrawable = objects.getBG_DRAWABLE();
            this.bgAlpha = 0;
            this.rotation = Float.parseFloat(objects.getAngle());
            this.field_two = "";
            setText(this.text);
            //setTextSizes(Integer.parseInt(objects.getFontSize()));
            setTextFont(this.fontName);
            setTextColor(this.tColor);
            setTextAlpha(this.tAlpha);
            setTextShadowColor(this.shadowColor);
            setTextShadowProg(this.shadowProg);

            this.type = objects.getType();
            this.originX = objects.getOriginX();
            this.originY = objects.getOriginY();
            this.stroke = objects.getStroke();
            this.strokeLineCap = objects.getStrokeLineCap();
            this.strokeLineJoin = objects.getStrokeLineJoin();
            this.strokeMeterLimit = objects.getStrokeMiterLimit();
            this.scaleX = objects.getScaleX();
            this.scaleY = objects.getScaleY();
            this.flipX = objects.getFlipX();
            this.flipY = objects.getFlipY();
            this.shadow = objects.getShadow();
            this.visible = objects.getVisible();
            this.clipTo = objects.getClipTo();
            this.backgroundColor = objects.getBackgroundColor().equals("0") ? "#00000000" : objects.getBackgroundColor();
            this.fillRule = objects.getFillRule();
            this.globleCompositeOperation = objects.getGlobalCompositeOperation();
            this.name = objects.getName();
            this.crossLine = objects.getCrossOrigin();
            this.alignX = objects.getAlignX();
            this.alignY = objects.getAlignY();
            this.meetOrSlice = objects.getMeetOrSlice();
            this.lookPosition = objects.getLookPosition();
            this.fontSize = objects.getFontSize();
            this.fontWeight = objects.getFontWeight();
            this.fontFamily = objects.getFontFamily();
            this.fontStyle = objects.getFontStyle();
            this.lineHeight = objects.getLineHeight();
            this.textDecoration = objects.getTextDecoration();
            this.textAlign = objects.getTextAlign();
            this.textBackgroundColor = objects.getTextBackgroundColor();
            this.rx = objects.getRx();
            this.ry = objects.getRy();

            int i = this.bgColor;

            if (i != 0) {
                setBgColor(i);
            } else {
                this.background_iv.setBackgroundColor(0);
            }
            if (this.bgDrawable.equals("0")) {
                this.background_iv.setImageBitmap(null);
            } else {
                setBgDrawable(this.bgDrawable);
            }
            setBgAlpha(this.bgAlpha);
            setRotation(Float.parseFloat(objects.getAngle()));
            if (this.field_two.equals("")) {
                getLayoutParams().width = this.wi;
                getLayoutParams().height = this.he;

                setX(Float.parseFloat(tempLeft));
                setY(Float.parseFloat(tempTop));

            } else {
                String[] split = this.field_two.split(",");
                int parseInt = Integer.parseInt(split[0]);
                int parseInt2 = Integer.parseInt(split[1]);
                ((LayoutParams) getLayoutParams()).leftMargin = parseInt;
                ((LayoutParams) getLayoutParams()).topMargin = parseInt2;
                getLayoutParams().width = this.wi;
                getLayoutParams().height = this.he;

                setX(Float.parseFloat(tempLeft));
                setY(Float.parseFloat(tempTop));
            }

//            if (((float) this.wi) > this.widthMain) {
//                ((LayoutParams) getLayoutParams()).leftMargin = (int) (this.widthMain - ((float) this.wi));
//                setX((Float.parseFloat(objects.getLeft()) - ((float) 0.0f) + ((this.widthMain - ((float) this.wi)) * -1.0f)));
//            } else {
//                setX(Float.parseFloat(objects.getLeft()) - ((float) 0.0f));
//            }
//            if (((float) this.he) > this.heightMain) {
//                ((LayoutParams) getLayoutParams()).topMargin = (int) (this.heightMain - ((float) this.he));
//                setY((Float.parseFloat(objects.getTop()) - ((float) 0.0f) + ((this.heightMain - ((float) this.he)) * -1.0f)));
//            } else {
//                setY((Float.parseFloat(objects.getTop()) - ((float) 0.0f)));
//            }
            getLayoutParams().width = this.wi;
            getLayoutParams().height = this.he;
        } catch (Exception e) {
            Log.e(TAG, "Exception: setTextInfo: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void optimize(float f, float f2) {
        setX(getX() * f);
        setY(getY() * f2);
        getLayoutParams().width = (int) (((float) this.wi) * f);
        getLayoutParams().height = (int) (((float) this.he) * f2);
    }

    public void incrX() {
        setX(getX() + 2.0f);
    }

    public void decX() {
        setX(getX() - 2.0f);
    }

    public void incrY() {
        setY(getY() + 2.0f);
    }

    public void decY() {
        setY(getY() - 2.0f);
    }

    public int dpToPx(Activity activity, int i) {
        activity.getResources();
        return (int) (Resources.getSystem().getDisplayMetrics().density * ((float) i));
    }

    private Bitmap getTiledBitmap(Activity activity, int i, int i2, int i3) {
        Rect rect = new Rect(0, 0, i2, i3);
        Paint paint = new Paint();
        paint.setShader(new BitmapShader(BitmapFactory.decodeResource(activity.getResources(), i, new BitmapFactory.Options()), Shader.TileMode.REPEAT, Shader.TileMode.REPEAT));
        Bitmap createBitmap = Bitmap.createBitmap(i2, i3, Bitmap.Config.ARGB_8888);
        new Canvas(createBitmap).drawRect(rect, paint);
        return createBitmap;
    }

    private void initGD() {
        this.gd = new GestureDetector(this.activity, new SimpleListner());
    }

    @Override
    public void onTouchCallback(View view) {
        TouchEventListener touchEventListener = this.listener;
        if (touchEventListener != null) {
            touchEventListener.onFirstTouch();
            touchEventListener.onTouchDown(view);
            setBorderVisibility(true);
        }
    }

    @Override
    public void onTouchUpCallback(View view) {
        TouchEventListener touchEventListener = this.listener;
        if (touchEventListener != null) {
            touchEventListener.onTouchUp(view);
        }
    }

    @Override
    public void onTouchMoveCallback(View view) {
        TouchEventListener touchEventListener = this.listener;
        if (touchEventListener != null) {
            touchEventListener.onTouchMove(view);
        }
    }

    public float getNewX(float f) {
        return ((float) this.width) * (f / ((float) this.sw));
    }

    public float getNewY(float f) {
        return ((float) this.height) * (f / ((float) this.sh));
    }

    public void setTextSizes(int i) {

        try {
            Log.e(TAG, "setTextSizes: " + i);

            this.size = String.valueOf(i);
            this.text_iv.setTextSize((float) i * 2);
            this.wi = this.text_iv.getWidth();
            this.he = this.text_iv.getHeight();
            getLayoutParams().width = this.wi;
            getLayoutParams().height = this.he;
        } catch (Exception e) {
            Log.e(TAG, "setTextSizes: " + e.getMessage());
        }


    }

    public interface TouchEventListener {
        void onDelete();

        void onDoubleTap();

        void onFirstTouch();

        void onEdit(View view, Uri uri);

        void onRotateDown(View view);

        void onRotateMove(View view);

        void onRotateUp(View view);

        void onScaleDown(View view);

        void onScaleMove(View view);

        void onScaleUp(View view);

        void onTouchDown(View view);

        void onTouchMove(View view);

        void onTouchUp(View view);
    }

    class ScaleTouchListener implements OnTouchListener {
        ScaleTouchListener() {
        }

        public boolean onTouch(View view, MotionEvent motionEvent) {
            AutoFitTextRel autofitTextRel = (AutoFitTextRel) view.getParent();
            int rawX = (int) motionEvent.getRawX();
            int rawY = (int) motionEvent.getRawY();
            LayoutParams layoutParams = (LayoutParams) AutoFitTextRel.this.getLayoutParams();
            int action = motionEvent.getAction();
            if (action == MotionEvent.ACTION_DOWN) {
                if (autofitTextRel != null) {
                    autofitTextRel.requestDisallowInterceptTouchEvent(true);
                }
                if (AutoFitTextRel.this.listener != null) {
                    AutoFitTextRel.this.listener.onScaleDown(AutoFitTextRel.this);
                }
                AutoFitTextRel.this.invalidate();
                AutoFitTextRel.this.baseX = rawX;
                AutoFitTextRel.this.baseY = rawY;
                AutoFitTextRel autoFitTextRel2 = AutoFitTextRel.this;
                autoFitTextRel2.baseW = autoFitTextRel2.getWidth();
                AutoFitTextRel autoFitTextRel3 = AutoFitTextRel.this;
                autoFitTextRel3.baseH = autoFitTextRel3.getHeight();
                AutoFitTextRel.this.getLocationOnScreen(new int[2]);
                AutoFitTextRel.this.mergeL = layoutParams.leftMargin;
                AutoFitTextRel.this.mergeT = layoutParams.topMargin;
            } else if (action == MotionEvent.ACTION_UP) {
                AutoFitTextRel autoFitTextRel4 = AutoFitTextRel.this;
                autoFitTextRel4.wi = autoFitTextRel4.getLayoutParams().width;
                AutoFitTextRel autoFitTextRel5 = AutoFitTextRel.this;
                autoFitTextRel5.he = autoFitTextRel5.getLayoutParams().height;
                AutoFitTextRel autoFitTextRel6 = AutoFitTextRel.this;
                autoFitTextRel6.leftMargin = ((LayoutParams) autoFitTextRel6.getLayoutParams()).leftMargin;
                AutoFitTextRel autoFitTextRel7 = AutoFitTextRel.this;
                autoFitTextRel7.topMargin = ((LayoutParams) autoFitTextRel7.getLayoutParams()).topMargin;
                AutoFitTextRel autoFitTextRel8 = AutoFitTextRel.this;
                autoFitTextRel8.field_two = String.valueOf(AutoFitTextRel.this.leftMargin) + "," + String.valueOf(AutoFitTextRel.this.topMargin);
                if (AutoFitTextRel.this.listener != null) {
                    AutoFitTextRel.this.listener.onScaleUp(AutoFitTextRel.this);
                }

                tempWidth = String.valueOf(getLayoutParams().width);
                tempHeight = String.valueOf(getLayoutParams().height);
            } else if (action == MotionEvent.ACTION_MOVE) {
                if (autofitTextRel != null) {
                    autofitTextRel.requestDisallowInterceptTouchEvent(true);
                }
                if (AutoFitTextRel.this.listener != null) {
                    AutoFitTextRel.this.listener.onScaleMove(AutoFitTextRel.this);
                }
                float degrees = (float) Math.toDegrees(Math.atan2((double) (rawY - AutoFitTextRel.this.baseY), (double) (rawX - AutoFitTextRel.this.baseX)));
                if (degrees < 0.0f) {
                    degrees += 360.0f;
                }
                int i = rawX - AutoFitTextRel.this.baseX;
                int i2 = rawY - AutoFitTextRel.this.baseY;
                int i3 = i2 * i2;
                int sqrt = (int) (Math.sqrt((double) ((i * i) + i3)) * Math.cos(Math.toRadians((double) (degrees - AutoFitTextRel.this.getRotation()))));
                int sqrt2 = (int) (Math.sqrt((double) ((sqrt * sqrt) + i3)) * Math.sin(Math.toRadians((double) (degrees - AutoFitTextRel.this.getRotation()))));
                int i4 = (sqrt * 2) + AutoFitTextRel.this.baseW;
                int i5 = (sqrt2 * 2) + AutoFitTextRel.this.baseH;
                if (i4 > AutoFitTextRel.this.f27s) {
                    layoutParams.width = i4;
                    layoutParams.leftMargin = AutoFitTextRel.this.mergeL - sqrt;
                }
                if (i5 > AutoFitTextRel.this.f27s) {
                    layoutParams.height = i5;
                    layoutParams.topMargin = AutoFitTextRel.this.mergeT - sqrt2;
                }
                AutoFitTextRel.this.setLayoutParams(layoutParams);
                if (!AutoFitTextRel.this.bgDrawable.equals("0")) {
                    AutoFitTextRel autoFitTextRel9 = AutoFitTextRel.this;
                    autoFitTextRel9.wi = autoFitTextRel9.getLayoutParams().width;
                    AutoFitTextRel autoFitTextRel10 = AutoFitTextRel.this;
                    autoFitTextRel10.he = autoFitTextRel10.getLayoutParams().height;
                    AutoFitTextRel autoFitTextRel11 = AutoFitTextRel.this;
                    autoFitTextRel11.setBgDrawable(autoFitTextRel11.bgDrawable);
                }
            }
            return true;
        }
    }

    /* access modifiers changed from: package-private */
    public class SimpleListner extends GestureDetector.SimpleOnGestureListener {
        SimpleListner() {
        }

        public boolean onDoubleTapEvent(MotionEvent motionEvent) {
            return true;
        }

        public boolean onDown(MotionEvent motionEvent) {
            return true;
        }

        public boolean onDoubleTap(MotionEvent motionEvent) {
            if (AutoFitTextRel.this.listener == null) {
                return true;
            }
            AutoFitTextRel.this.listener.onDoubleTap();
            return true;
        }

        public void onLongPress(MotionEvent motionEvent) {
            super.onLongPress(motionEvent);
        }
    }
}
