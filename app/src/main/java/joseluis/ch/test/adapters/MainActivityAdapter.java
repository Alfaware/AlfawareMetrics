package joseluis.ch.test.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.databinding.DataBindingUtil;

import java.util.List;

import joseluis.ch.test.R;
import joseluis.ch.test.databinding.MainActivityAdapterBinding;
import joseluis.ch.test.entities.Nota;

public class MainActivityAdapter extends ArrayAdapter<Nota> {

    public MainActivityAdapter(Context context, List<Nota> notas) {
        super(context, 0, notas);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        MainActivityAdapterBinding binding = DataBindingUtil.inflate(inflater, R.layout.main_activity_adapter, null, false);
        binding.setNota(getItem(position));
        return binding.getRoot();
    }


}