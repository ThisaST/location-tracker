package com.uok.se.thisara.smart.trackerapplication.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.uok.se.thisara.smart.trackerapplication.model.Bus;
import com.uok.se.thisara.smart.trackerapplication.repository.BusListRepository;

import java.util.List;

import javax.inject.Inject;

public class BusListViewModel extends ViewModel {

    private BusListRepository busListRepository;

    public BusListViewModel() {

    }
    @Inject
    public BusListViewModel(BusListRepository busListRepository) {
        this.busListRepository = busListRepository;
    }

    public LiveData<List<Bus>> getBusList() {


        LiveData<List<Bus>> busList;
        busList = busListRepository.getBusList();
        return busList;
    }

}
