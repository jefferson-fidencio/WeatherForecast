package fidencio.jefferson.hbsisteste.enums;

import android.content.Context;

import fidencio.jefferson.hbsisteste.R;

public enum ListSortOption {

    nome(R.string.sort_option_nome, 0),
    temp_min(R.string.sort_option_temp_min,1),
    temp_max(R.string.sort_option_temp_max,2);

    private int sortOptionTitle;
    private int sortOptionPosition;

    ListSortOption(int sortOptionTitle, int sortOptionPosition) {
        this.sortOptionTitle = sortOptionTitle;
        this.sortOptionPosition = sortOptionPosition;
    }

    public String toString(Context context) {
        return context.getResources().getString(sortOptionTitle);
    }
}
