package joseluis.ch.test.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.snackbar.Snackbar;

import joseluis.ch.test.R;
import joseluis.ch.test.adapters.MainActivityAdapter;
import joseluis.ch.test.databinding.MainActivityBinding;
import joseluis.ch.test.entities.Nota;
import joseluis.ch.test.utilities.Status;
import joseluis.ch.test.viewmodels.MainActivityViewModel;

public class ActivityMain extends AppCompatActivity {

    private MainActivityBinding binding;
    private MainActivityAdapter adapter;
    private MainActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        binding = DataBindingUtil.setContentView(this, R.layout.main_activity);

        init();
        setListeners();
        setObservers();
    }

    private void init() {
        adapter = new MainActivityAdapter(getApplicationContext(), viewModel.getNotas().getValue().getResult().getData());
        binding.listView.setAdapter(adapter);
    }

    private void setListeners() {
        binding.floatingActionButtonAdd.setOnClickListener(v -> addNota());
        binding.floatingActionButtonChuck.setOnClickListener(v -> chuckActivity());
    }

    private void setObservers() {
        viewModel.getStatus().observe(this, integer -> {
            binding.status.empty.setVisibility(View.GONE);
            binding.status.error.setVisibility(View.GONE);
            binding.status.loading.setVisibility(View.GONE);

            if (integer != null) {
                switch (integer) {
                    case Status.STATUS_LOADING:
                        binding.status.loading.setVisibility(View.VISIBLE);
                        break;
                    case Status.STATUS_EMPTY:
                        binding.status.empty.setVisibility(View.VISIBLE);
                        break;
                    case Status.STATUS_ERROR:
                        binding.status.error.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });
        viewModel.getNotas().observe(this, notas -> {
            Exception exception = notas.getResult().getException();
            if (exception != null) {
                exception.printStackTrace();
                viewModel.setStatus(Status.STATUS_ERROR);
                Snackbar.make(getCurrentFocus(), exception.getMessage(), Snackbar.LENGTH_SHORT).show();
            } else if (notas.getResult().getData().isEmpty()) {
                viewModel.setStatus(Status.STATUS_EMPTY);
            } else {
                viewModel.setStatus(Status.STATUS_DONE);
            }
            adapter.notifyDataSetChanged();
        });
        viewModel.getNota().observe(this, nota -> {
            Exception exception = nota.getResult().getException();
            if (exception != null) {
                exception.printStackTrace();
                viewModel.setStatus(Status.STATUS_ERROR);
                Snackbar.make(getCurrentFocus(), exception.getMessage(), Snackbar.LENGTH_SHORT).show();
            } else if (nota.getResult().getData() != null) {
                Toast.makeText(getApplicationContext(), nota.getResult().getData().getTitulo() + "Fue agregado", Toast.LENGTH_SHORT).show();
                viewModel.setStatus(Status.STATUS_DONE);
            }
        });
    }

    //====================================================================

    private void addNota() {
        Nota nota = new Nota();
        nota.setTitulo("Titulo");
        nota.setDescripcion("Descripcion");
        nota.setFecha(System.currentTimeMillis());
        viewModel.addNota(nota);
    }


    private void chuckActivity() {
        Intent intent = new Intent(getApplicationContext(), ChuckNorrisActivity.class);
        startActivity(intent);
    }

}
