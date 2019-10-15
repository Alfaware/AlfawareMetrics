package joseluis.ch.test.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SuperViewModel extends ViewModel {

    private MutableLiveData<Integer> status = new MutableLiveData<>();

    //==============================================================================================

    public LiveData<Integer> getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status.postValue(status);
    }

    //==============================================================================================

}
