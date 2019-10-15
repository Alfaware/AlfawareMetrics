package joseluis.ch.test.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;

import joseluis.ch.test.abstract_classes.DataResult;
import joseluis.ch.test.abstract_classes.ListResult;
import joseluis.ch.test.abstract_classes.Result;
import joseluis.ch.test.entities.Joke;
import joseluis.ch.test.entities.Nota;
import joseluis.ch.test.repositories.JokeRepository;
import joseluis.ch.test.repositories.NotaRepository;
import joseluis.ch.test.utilities.Status;

public class ChuckNorrisActivityViewModel extends SuperViewModel {

    private MutableLiveData<ListResult<Joke>> list = new MutableLiveData<>();
    private MutableLiveData<Result<Nota>> nota = new MutableLiveData<>();

    public LiveData<ListResult<Joke>> getList() {
        if (this.list.getValue() == null) {
            list.setValue(new ListResult<>());
            list.getValue().setResult(new DataResult<>());
            list.getValue().getResult().setData(new ArrayList<>());
        }
        return list;
    }

    public MutableLiveData<Result<Nota>> getNota() {
        if (nota.getValue() == null) {
            nota.setValue(new Result<>());
            nota.getValue().setResult(new DataResult<>());
        }
        return nota;
    }

    public void get() {
        JokeRepository.getInstance().get(list);
        setStatus(Status.STATUS_LOADING);
    }

    public void addNota(Nota nota) {
        setStatus(Status.STATUS_LOADING);
        this.nota.getValue().getResult().setData(nota);
        NotaRepository.getInstance().add(this.nota);
    }


}
