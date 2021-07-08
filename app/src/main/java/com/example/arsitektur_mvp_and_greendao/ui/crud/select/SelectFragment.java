package com.example.arsitektur_mvp_and_greendao.ui.crud.select;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.widget.ContentLoadingProgressBar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.arsitektur_mvp_and_greendao.R;
import com.example.arsitektur_mvp_and_greendao.data.others.ExecutionTimePreference;
import com.example.arsitektur_mvp_and_greendao.data.others.Medical;
import com.example.arsitektur_mvp_and_greendao.di.component.ActivityComponent;
import com.example.arsitektur_mvp_and_greendao.ui.base.BaseFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

public class SelectFragment extends BaseFragment implements SelectMvpView, SelectAdapter.Callback {

    @Inject //Penggunaan dependency injection untuk presenter select
    @Named("selectPresenter")
    SelectPresenter<SelectMvpView> mPresenter; //Deklarasi Presenter pada view Select

    @Inject //Penggunaan dependency injection untuk adapter select
    @Named("selectAdapter")
    SelectAdapter mSelectAdapter; //Deklarasi Adapter pada view Select

    @Inject
    LinearLayoutManager mLayoutManager;

    ContentLoadingProgressBar progressBar;

    FloatingActionButton floatingActionButton;

    RecyclerView mRecyclerView;

    ExecutionTimePreference executionTimePreference;

    TextView mNumOfRecord;

    TextView mSelectDatabaseTime;

    TextView mAllSelectTime;

    TextView mViewSelectTime;

    EditText mEditTextNumData;

    Button btnExecute;

    public static SelectFragment newInstance() {
        SelectFragment fragment = new SelectFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void setUp(View view) {
        this.mRecyclerView = view.findViewById(R.id.selectRecyclerView);
        this.mNumOfRecord = view.findViewById(R.id.textViewRecord);
        this.mSelectDatabaseTime = view.findViewById(R.id.textViewTimeSelectDB);
        this.mAllSelectTime = view.findViewById(R.id.textViewTimeSelectAll);
        this.mViewSelectTime = view.findViewById(R.id.textViewTimeSelectView);
        this.mEditTextNumData = view.findViewById(R.id.editTextNumData);
        this.btnExecute = view.findViewById(R.id.btnExecute);
        this.floatingActionButton = view.findViewById(R.id.fabDown);
        this.progressBar = view.findViewById(R.id.progressBar);

        this.btnExecute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mEditTextNumData.getText() != null) {
                    try {
                        Long numOfData = Long.valueOf(mEditTextNumData.getText().toString());
                        mPresenter.onSelectExecuteClick(executionTimePreference, numOfData);
                    } catch (Exception e) {
                        Toast.makeText(getContext(), "Amount Of Data is Not Valid", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.d("TAG", "onClick: ");
                    Toast.makeText(getContext(), "Amount Of Data is Not Valid", Toast.LENGTH_SHORT).show();
                }
            }
        });

        this.floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRecyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        mRecyclerView.scrollToPosition(mSelectAdapter.getItemCount() - 1);
                    }
                });
            }
        });

        this.mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        this.mRecyclerView.setLayoutManager(mLayoutManager);
        this.mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        this.mRecyclerView.setAdapter(mSelectAdapter);

        if (!executionTimePreference.getExecutionTime().getDatabaseSelectTime().isEmpty())
            this.mSelectDatabaseTime
                    .setText("TIME DB (MS) : " +
                            executionTimePreference.getExecutionTime().getDatabaseSelectTime());
        if (!executionTimePreference.getExecutionTime().getAllSelectTime().isEmpty())
            this.mAllSelectTime
                    .setText("TIME ALL (MS) : " +
                            executionTimePreference.getExecutionTime().getAllSelectTime());
        if (!executionTimePreference.getExecutionTime().getViewSelectTime().isEmpty())
            this.mViewSelectTime
                    .setText("TIME VIEW (MS) : " +
                            executionTimePreference.getExecutionTime().getViewSelectTime());
        if (!executionTimePreference.getExecutionTime().getNumOfRecordSelect().isEmpty())
            this.mNumOfRecord
                    .setText("RECORDS : " +
                            executionTimePreference.getExecutionTime().getNumOfRecordSelect());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_select, container, false);

        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            this.mPresenter.onAttach(this);
            this.mSelectAdapter.setCallback(this);

            this.executionTimePreference = new ExecutionTimePreference(getBaseActivity());
        }
        return view;
    }

    @Override
    public void onEmptyViewRetryClick(){

    }

    @Override
    public void updateNumOfRecordSelect(Long numOfRecord){
        this.mNumOfRecord.setText("RECORDS : " + numOfRecord.toString());
    }

    @Override
    public void updateSelectDatabaseTime(Long selectDatabaseTime) {
        this.mSelectDatabaseTime.setText("TIME DB (MS) : " + selectDatabaseTime.toString());
    }

    @Override
    public void updateAllSelectTime(Long allSelectTime) {
        this.mAllSelectTime.setText("TIME ALL (MS) : " + allSelectTime.toString());
    }

    @Override
    public void updateViewSelectTime(Long viewSelectTime) {
        this.mViewSelectTime.setText("TIME VIEW (MS) : " + viewSelectTime.toString());
    }

    @Override
    public void selectMedicalData(List<Medical> medicalList){
        this.mSelectAdapter.clearItems();
        this.mSelectAdapter.selectItems(medicalList);
    }

    @Override
    public void stateLoadingSelect(boolean state){
        if (state)
            this.progressBar.setVisibility(View.VISIBLE);
        else
            this.progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onDestroyView(){
        this.mPresenter.onDetach();
        super.onDestroyView();
    }

}
