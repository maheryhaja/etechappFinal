package mg.etech.mobile.etechapp.service.applicatif.synchro.central;

import mg.etech.mobile.etechapp.donnee.dto.EmployeDto;
import mg.etech.mobile.etechapp.presentation.fragments.employe.list.SuperListEmployeItem;

/**
 * Created by mahery.haja on 27/09/2017.
 */

public class ItemReplacement {
    private SuperListEmployeItem oldItem;
    private SuperListEmployeItem newItem;

    public enum ConcernedLevel {
        NotConcerned, NewConcerned, OldConcerned, TwoWayConcerned
    }

    public ItemReplacement(SuperListEmployeItem oldItem, SuperListEmployeItem newItem) {
        this.oldItem = oldItem;
        this.newItem = newItem;
    }

    public SuperListEmployeItem getOldItem() {
        return oldItem;
    }

    public void setOldItem(SuperListEmployeItem oldItem) {
        this.oldItem = oldItem;
    }

    public SuperListEmployeItem getNewItem() {
        return newItem;
    }

    public void setNewItem(SuperListEmployeItem newItem) {
        this.newItem = newItem;
    }

    public ConcernedLevel getConcernedLevel(Long poleId) {
        EmployeDto oldEmployeDto = oldItem.getEmployeDto();
        EmployeDto newEmployeDto = newItem.getEmployeDto();
        ConcernedLevel concernedLevel = ConcernedLevel.NotConcerned;

        if (oldEmployeDto.getPole() != null && newEmployeDto.getPole() != null) {
            if (poleId == oldEmployeDto.getPole().getId() && poleId == newEmployeDto.getPole().getId()) {
                concernedLevel = ConcernedLevel.TwoWayConcerned;
            } else {

                if (poleId == oldEmployeDto.getPole().getId())
                    concernedLevel = ConcernedLevel.OldConcerned;

                if (poleId == newEmployeDto.getPole().getId())
                    concernedLevel = ConcernedLevel.NewConcerned;
            }
        }

        return concernedLevel;
    }


}
