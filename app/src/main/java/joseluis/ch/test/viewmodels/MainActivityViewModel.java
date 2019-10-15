package joseluis.ch.test.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;

import joseluis.ch.test.abstract_classes.DataResult;
import joseluis.ch.test.abstract_classes.ListResult;
import joseluis.ch.test.abstract_classes.Result;
import joseluis.ch.test.entities.Nota;
import joseluis.ch.test.repositories.NotaRepository;
import joseluis.ch.test.utilities.Status;

public class MainActivityViewModel extends SuperViewModel {


    private MutableLiveData<ListResult<Nota>> notas = new MutableLiveData<>();
    private MutableLiveData<Result<Nota>> nota = new MutableLiveData<>();

    //==============================================================================================

    public LiveData<ListResult<Nota>> getNotas() {
        if (this.notas.getValue() == null) {
            notas.setValue(new ListResult<>());
            notas.getValue().setResult(new DataResult<>());
            notas.getValue().getResult().setData(new ArrayList<>());
            getNotas(null);
        }
        return notas;
    }

    public MutableLiveData<Result<Nota>> getNota() {
        if (nota.getValue() == null) {
            nota.setValue(new Result<>());
            nota.getValue().setResult(new DataResult<>());
        }
        return nota;
    }

    //==============================================================================================

    public void getNotas(Nota nota) {
        setStatus(Status.STATUS_LOADING);
        NotaRepository.getInstance().subscribeList(this.notas);
    }

    public void addNota(Nota nota) {
        setStatus(Status.STATUS_LOADING);
        this.nota.getValue().getResult().setData(nota);
        NotaRepository.getInstance().add(this.nota);
    }

}
