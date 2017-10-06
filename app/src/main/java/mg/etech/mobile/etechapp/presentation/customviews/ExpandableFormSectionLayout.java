package mg.etech.mobile.etechapp.presentation.customviews;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mahery.haja on 06/10/2017.
 */

public class ExpandableFormSectionLayout extends LinearLayout {

    private ExpandableFormSection expandableFormSection;

    public ExpandableFormSectionLayout(Context context) {
        super(context);
        expandableFormSection = new ExpandableFormSection(context, null);


    }

//    public ExpandableFormSectionLayout(Context context, @Nullable AttributeSet attrs) {
//        super(context, attrs);
//        expandableFormSection = ExpandableFormSection_.build(context, attrs);
//
//
//    }
//
//    public ExpandableFormSectionLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//        expandableFormSection = ExpandableFormSection_.build(context, attrs);
//    }

    @Override
    protected void onFinishInflate() {

        List<View> childrenView = new ArrayList<>();

        for (int i = 0; i < getChildCount(); i++) {
            childrenView.add(getChildAt(i));
        }

        detachAllViewsFromParent();

        for (View v : childrenView) {
            expandableFormSection.addContent(v);
        }

        addView(expandableFormSection);
        super.onFinishInflate();
    }
}
