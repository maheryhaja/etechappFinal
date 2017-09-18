package mg.etech.mobile.etechapp.presentation.fragments.employe.detail;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import eu.davidea.flexibleadapter.FlexibleAdapter;
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem;
import eu.davidea.viewholders.FlexibleViewHolder;
import mg.etech.mobile.etechapp.R;
import mg.etech.mobile.etechapp.commun.constante.SimpleDate;
import mg.etech.mobile.etechapp.commun.utils.date.SimpleDateUtils;
import mg.etech.mobile.etechapp.donnee.dto.HistoryPosteDto;

/**
 * Created by mahery.haja on 18/09/2017.
 */

public class PosteHistoryFlexibleItem extends AbstractFlexibleItem<PosteHistoryFlexibleItem.DetailHistoryPosteViewHolder> {

    private HistoryPosteDto historyPosteDto;


    public PosteHistoryFlexibleItem(HistoryPosteDto historyPosteDto) {
        this.historyPosteDto = historyPosteDto;
    }

    @Override

    public boolean equals(Object object) {

        if (object instanceof PosteHistoryFlexibleItem) {
            PosteHistoryFlexibleItem item = (PosteHistoryFlexibleItem) object;
            return item.getHistoryPosteDto().getId() == this.getHistoryPosteDto().getId();
        } else {

            return false;

        }

    }

    public HistoryPosteDto getHistoryPosteDto() {
        return historyPosteDto;
    }

    public void setHistoryPosteDto(HistoryPosteDto historyPosteDto) {
        this.historyPosteDto = historyPosteDto;
    }


    @Override
    public DetailHistoryPosteViewHolder createViewHolder(FlexibleAdapter adapter, LayoutInflater inflater, ViewGroup parent) {
        LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.viewholder_detail_history_poste, null);
        return new DetailHistoryPosteViewHolder(linearLayout, adapter);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.viewholder_detail_history_poste;
    }

    @Override
    public void bindViewHolder(FlexibleAdapter adapter, DetailHistoryPosteViewHolder holder, int position, List payloads) {
        holder.setFromHistoryPosteDto(this.historyPosteDto);
    }


    /*** inner class for view holder :)
     */

    public class DetailHistoryPosteViewHolder extends FlexibleViewHolder {

        private TextView txtHistoryId;
        private TextView txtHistoryPosteName;
        private TextView txtHistoryDatepromotion;

        public DetailHistoryPosteViewHolder(View view, FlexibleAdapter adapter) {
            super(view, adapter);
            txtHistoryId = (TextView) view.findViewById(R.id.txtHistoryId);
            txtHistoryDatepromotion = (TextView) view.findViewById(R.id.txtHistoryDatePromotion);
            txtHistoryPosteName = (TextView) view.findViewById(R.id.txtHistoryPosteName);
        }

        void setFromHistoryPosteDto(HistoryPosteDto historyPosteDto) {
            txtHistoryId.setText(historyPosteDto.getId() + "");
            txtHistoryDatepromotion.setText(SimpleDateUtils.format(historyPosteDto.getDatePromotion(), SimpleDate.GENERAL_DATE_PATTERN));
            txtHistoryPosteName.setText(historyPosteDto.getName());
        }
    }
}
