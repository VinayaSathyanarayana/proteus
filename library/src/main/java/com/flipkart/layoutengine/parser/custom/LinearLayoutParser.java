package com.flipkart.layoutengine.parser.custom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.widget.LinearLayout;

import com.flipkart.layoutengine.ParserContext;
import com.flipkart.layoutengine.parser.Attributes;
import com.flipkart.layoutengine.parser.ParseHelper;
import com.flipkart.layoutengine.parser.Parser;
import com.flipkart.layoutengine.parser.WrappableParser;
import com.flipkart.layoutengine.processor.DimensionAttributeProcessor;
import com.flipkart.layoutengine.processor.DrawableResourceProcessor;
import com.flipkart.layoutengine.processor.StringAttributeProcessor;
import com.flipkart.layoutengine.view.ProteusView;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * Created by kiran.kumar on 12/05/14.
 */
public class LinearLayoutParser<T extends LinearLayout> extends WrappableParser<T> {
    public LinearLayoutParser(Parser<T> wrappedParser) {
        super(LinearLayout.class, wrappedParser);
    }

    @Override
    protected void prepareHandlers(Context context) {
        super.prepareHandlers(context);
        addHandler(Attributes.LinearLayout.Orientation, new StringAttributeProcessor<T>() {
            @Override
            public void handle(ParserContext parserContext, String attributeKey, String attributeValue, T view, ProteusView proteusView, ProteusView parent, JsonObject layout, int index) {
                if ("horizontal".equals(attributeValue)) {
                    view.setOrientation(LinearLayout.HORIZONTAL);
                } else {
                    view.setOrientation(LinearLayout.VERTICAL);
                }
            }
        });

        addHandler(Attributes.View.Gravity, new StringAttributeProcessor<T>() {
            @Override
            public void handle(ParserContext parserContext, String attributeKey, String attributeValue, T view, ProteusView proteusView, ProteusView parent, JsonObject layout, int index) {

                view.setGravity(ParseHelper.parseGravity(attributeValue));

            }
        });

        addHandler(Attributes.LinearLayout.Divider, new DrawableResourceProcessor<T>(context) {
            @SuppressLint("NewApi")
            @Override
            public void setDrawable(T view, Drawable drawable) {

                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.GINGERBREAD_MR1) {
                    view.setDividerDrawable(drawable);
                }
            }
        });

        addHandler(Attributes.LinearLayout.DividerPadding, new DimensionAttributeProcessor<T>() {
            @SuppressLint("NewApi")
            @Override
            public void setDimension(ParserContext parserContext, float dimension, T view, String key, JsonElement value, ProteusView proteusView, JsonObject layout, int index) {
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.GINGERBREAD_MR1) {
                    view.setDividerPadding((int) dimension);
                }
            }
        });

        addHandler(Attributes.LinearLayout.ShowDividers, new StringAttributeProcessor<T>() {
            @SuppressLint("NewApi")
            @Override
            public void handle(ParserContext parserContext, String attributeKey, String attributeValue, T view, ProteusView proteusView, ProteusView parent, JsonObject layout, int index) {

                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.GINGERBREAD_MR1) {
                    int dividerMode = ParseHelper.parseDividerMode(attributeValue);
                    // noinspection ResourceType
                    view.setShowDividers(dividerMode);
                }
            }
        });

        addHandler(Attributes.LinearLayout.WeightSum, new StringAttributeProcessor<T>() {
            @SuppressLint("NewApi")
            @Override
            public void handle(ParserContext parserContext, String attributeKey, String attributeValue, T view, ProteusView proteusView, ProteusView parent, JsonObject layout, int index) {
                view.setWeightSum(ParseHelper.parseFloat(attributeValue));
            }
        });
    }
}
