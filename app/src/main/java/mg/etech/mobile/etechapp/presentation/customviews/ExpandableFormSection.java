package mg.etech.mobile.etechapp.presentation.customviews;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.ArrayList;
import java.util.List;

import mg.etech.mobile.etechapp.R;

/**
 * Created by mahery.haja on 06/10/2017.
 */

public class ExpandableFormSection extends LinearLayout {

    private final int EXPAND_DURATION = 500;
    private ImageView btnToggle;
    private LinearLayout contentLayout;
    private LinearLayout rootLayout;
    private ExpandableLayout expandableLayoutContent;
    private TextView txtTitle;
    private boolean isExpanded;
    private int angle = 0;

    public ExpandableFormSection(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        rootLayout = (LinearLayout) inflate(context, R.layout.viewgroup_expandable_form_section, null);
        contentLayout = (LinearLayout) rootLayout.findViewById(R.id.layoutFormSectionContent);
        btnToggle = (ImageView) rootLayout.findViewById(R.id.btnFormSectionToggle);
        txtTitle = (TextView) rootLayout.findViewById(R.id.txtFormSectionTitle);
        expandableLayoutContent = (ExpandableLayout) rootLayout.findViewById(R.id.expandablelayoutContent);
        expandableLayoutContent.setDuration(EXPAND_DURATION);
        btnToggle.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                expandableLayoutContent
                        .toggle(true);
                toggleIcon();
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
        toggleIcon();

    }

    private void toggleIcon() {
        angle += 180;
        btnToggle
                .animate()
                .rotation(angle)
                .setDuration(EXPAND_DURATION);
    }

    public void setTitle(String titre) {
        txtTitle.setText(titre);
    }


}
