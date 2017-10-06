package mg.etech.mobile.etechapp.presentation.activities.test;

import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import mg.etech.mobile.etechapp.R;

@EActivity(R.layout.activity_test_animation)
public class TestAnimationActivity extends AppCompatActivity {

    @ViewById(R.id.testBtnToggle)
    Button testButton;

    @ViewById(R.id.testContent)
    LinearLayout testContent;

    @ViewById(R.id.testHeader)
    RelativeLayout testHeader;

    @ViewById(R.id.txtTestEssai)
    TextView testTextView;

    private boolean isDown = false;

    @Click(R.id.testBtnToggle)
    public void onToggleClicked() {
        testContent
                .animate()
                .scaleY(0)
                .withLayer()
                .setDuration(1000)
                .start();

        testTextView.setText("RANDRIANARISAONA Mahery Haja");
    }


}
