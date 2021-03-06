package com.example.infectiontracker.viewmodel;

import android.app.Application;

import com.example.infectiontracker.database.Beacon;
import com.example.infectiontracker.repository.BroadcastRepository;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class ContactLoggerViewModel extends AndroidViewModel {

    private BroadcastRepository mBroadcastRepository;

    private LiveData<List<Beacon>> mAllBeacons;

    public ContactLoggerViewModel(Application application) {
        super(application);
        //TODO: are two instances of repository ok (in ViewModel and TracingService)?
        mBroadcastRepository = new BroadcastRepository(application);
        mAllBeacons = mBroadcastRepository.getAllBeacons();
    }

    public LiveData<List<Beacon>> getAllBeacons() {
        return mAllBeacons;
    }
}
