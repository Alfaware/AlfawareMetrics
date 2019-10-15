package joseluis.ch.test.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.snackbar.Snackbar;

import joseluis.ch.test.R;
import joseluis.ch.test.databinding.ChuckNorrisActivityBinding;
import joseluis.ch.test.entities.Joke;
import joseluis.ch.test.entities.Nota;
import joseluis.ch.test.utilities.Status;
import joseluis.ch.test.viewmodels.ChuckNorrisActivityViewModel;

public class ChuckNorrisActivity extends AppCompatActivity {

    private ChuckNorrisActivityBinding binding;
    private ChuckNorrisActivityViewModel viewModel;
    private ArrayAdapter adapter;

    private int cont;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.chuck_norris_activity);
        viewModel = ViewModelProviders.of(this).get(ChuckNorrisActivityViewModel.class);

        init();
        setListeners();
        setObservers();
    }

    private void init() {
        adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, viewModel.getList().getValue().getResult().getData());
        binding.listView.setAdapter(adapter);

        cont = 0;
    }

    private void setListeners() {
        binding.floatingActionButton.setOnClickListener(v -> viewModel.get());
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
        viewModel.getList().observe(this, jokeListResult -> {
            Exception exception = jokeListResult.getResult().getException();
            if (exception != null) {
                exception.printStackTrace();
                viewModel.setStatus(Status.STATUS_ERROR);
                Snackbar.make(getCurrentFocus(), exception.getMessage(), Snackbar.LENGTH_SHORT).show();
            } else if (jokeListResult.getResult().getData().isEmpty()) {
                viewModel.setStatus(Status.STATUS_EMPTY);
            } else if (jokeListResult.getResult().getData().isEmpty() && cont == 0) {
                cont = jokeListResult.getResult().getData().size();
            } else {
                addNota(jokeListResult.getResult().getData().get(jokeListResult.getResult().getData().size() - 1));
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
                setResult(Activity.RESULT_OK);
                finish();
            }
        });
    }

    private void addNota(Joke joke) {
        Nota nota = new Nota();
        nota.setTitulo(joke.getId());
        nota.setDescripcion(joke.getValue());
        nota.setFecha(System.currentTimeMillis());
        viewModel.addNota(nota);
    }

}
