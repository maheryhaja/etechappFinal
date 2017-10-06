package mg.etech.mobile.etechapp.presentation.customviews;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.ArrayList;
import java.util.List;

import mg.etech.mobile.etechapp.R;

/**
 * Created by mahery.haja on 06/10/2017.
 */

public class ExpandableFormSection extends LinearLayout {

    private Button btnToggle;
    private LinearLayout contentLayout;
    private LinearLayout rootLayout;
    private ExpandableLayout expandableLayoutContent;

    public ExpandableFormSection(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        rootLayout = (LinearLayout) inflate(context, R.layout.viewgroup_expandable_form_section, null);
        contentLayout = (LinearLayout) rootLayout.findViewById(R.id.layoutFormSectionContent);
        btnToggle = (Button) rootLayout.findViewById(R.id.btnFormSectionToggle);
        expandableLayoutContent = (ExpandableLayout) rootLayout.findViewById(R.id.expandablelayoutContent);

        btnToggle.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                expandableLayoutContent
                        .toggle(true);
            }
        });


    }

    void addContent(View child) {
        if (contentLayout != null) {
            contentLayout.addView(child);
        }
    }

    @Override
    protected void onFinishInflate() {

        List<View> childrenView = new ArrayList<>();

        for (int i = 0; i < getChildCount(); i++) {
            childrenView.add(getChildAt(i));
        }

        detachAllViewsFromParent();

        for (View v : childrenView) {
            this.addContent(v);
        }

        addView(rootLayout);


        super.onFinishInflate();

    }


}
