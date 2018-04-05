package com.nf28.td6;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.List;

public class TachesAdapter extends ArrayAdapter<Taches> {
    public TachesAdapter(Context context, int resource, List<Taches> objects) {
        super(context, resource, objects);
    }
}
