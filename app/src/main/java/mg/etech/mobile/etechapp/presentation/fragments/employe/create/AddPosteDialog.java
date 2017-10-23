package mg.etech.mobile.etechapp.presentation.fragments.employe.create;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Spinner;

import java.util.Date;
import java.util.List;

import io.reactivex.subjects.PublishSubject;
import mg.etech.mobile.etechapp.R;
import mg.etech.mobile.etechapp.commun.dialog.BaseDialog;
import mg.etech.mobile.etechapp.commun.utils.date.datepicker.SimpleDatePicker;
import mg.etech.mobile.etechapp.donnee.dto.HistoryPosteDto;
import mg.etech.mobile.etechapp.donnee.dto.PosteDto;

/**
 * Created by maheryHaja on 9/18/2017.
 */

public class AddPosteDialog extends BaseDialog {

    private List<PosteDto> posteDtos;

    private Spinner posteSpinner;

    private SimpleDatePicker datePromotionPicker;

    private HistoryPosteDto historyPosteDto;

    private LinearLayout contenu;

    private PublishSubject<HistoryPosteDto> publishSubject = PublishSubject.create();

    public AddPosteDialog(Context ctx, LayoutInflater inf) {
        super(ctx, inf);
    }

    public AddPosteDialog(Context ctx, LayoutInflater inf, List<PosteDto> posteDtos) {
        super(ctx, inf);
        this.posteDtos = posteDtos;
        contenu = (LinearLayout) inflater.inflate(R.layout.dialog_add_poste, null);
        posteSpinner = (Spinner) contenu.findViewById(R.id.spinner_add_poste);
        datePromotionPicker = (SimpleDatePicker) contenu.findViewById(R.id.edtAddPosteDatePromotion);
        init();
    }

    private void init() {
        posteSpinner.setAdapter(new PosteSpinnerAdapter(inflater.getContext(), android.R.layout.simple_dropdown_item_1line, posteDtos));
        datePromotionPicker.setDate(new Date());
        content.addView(contenu);
    }

    public HistoryPosteDto getHistoryPosteDto() {
        return historyPosteDto;
    }

    public void setHistoryPosteDto(HistoryPosteDto historyPosteDto) {
        this.historyPosteDto = historyPosteDto;
    }

    @Override
    protected boolean onValiderClicked() {
        historyPosteDto = new HistoryPosteDto();
        historyPosteDto.setName(posteDtos.get(posteSpinner.getSelectedItemPosition()).getName());
        historyPosteDto.setDatePromotion(datePromotionPicker.getDate());

        Log.d("mahery-haja", "publication");
        publishSubject.onNext(historyPosteDto);
        return true;
    }

    @Override
    protected View getView() {
        return contenu;
    }

    @Override
    protected String getTitle() {
        return "Ajout de Poste";
    }

    public PublishSubject<HistoryPosteDto> getHistoryPosteDtoObservable() {
        return publishSubject;
    }
}
