package com.example.arsitektur_mvp_and_greendao.ui.crud.insert;

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

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

public class InsertFragment extends BaseFragment implements InsertMvpView, InsertAdapter.Callback{

    private static final String TAG = "InsertFragment";

    @Inject
    @Named("insertPresenter")
    InsertPresenter<InsertMvpView> mPresenter;

    @Inject
    @Named("insertAdapter")
    InsertAdapter mInsertAdapter;

    @Inject
    LinearLayoutManager mLayoutManager;

    ContentLoadingProgressBar progressBar;

    RecyclerView mRecyclerView;

    ExecutionTimePreference executionTimePreference;

    TextView mNumOfRecord;

    TextView mInsertDatabaseTime;

    TextView mAllInsertTime;

    TextView mViewInsertTime;

    EditText mEditTextNumData;

    Button btnExecute;

    public static InsertFragment newInstance(){
        InsertFragment fragment = new InsertFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void setUp(View view) {
        this.mRecyclerView = view.findViewById(R.id.insertRecyclerView);
        this.mNumOfRecord = view.findViewById(R.id.textViewRecord);
        this.mInsertDatabaseTime = view.findViewById(R.id.textViewTimeInsertDB);
        this.mAllInsertTime = view.findViewById(R.id.textViewTimeInsertAll);
        this.mViewInsertTime = view.findViewById(R.id.textViewTimeInsertView);
        this.mEditTextNumData = view.findViewById(R.id.editTextNumData);
        this.btnExecute = view.findViewById(R.id.btnExecute);
        this.progressBar = view.findViewById(R.id.progressBar);

        this.btnExecute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mEditTextNumData.getText() != null) {
                    try {
                        Long numOfData = Long.valueOf(mEditTextNumData.getText().toString());
                        mPresenter.onInsertExecuteClick(executionTimePreference, numOfData);
                    } catch (Exception e) {
                        Toast.makeText(getContext(), "Amount Of Data is Not Valid", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.d(TAG, "onClick: ");
                    Toast.makeText(getContext(), "Amount Of Data is Not Valid", Toast.LENGTH_SHORT).show();
                }
            }
        });

        this.mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        this.mRecyclerView.setLayoutManager(mLayoutManager);
        this.mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        this.mRecyclerView.setAdapter(mInsertAdapter);

        if (!executionTimePreference.getExecutionTime().getDatabaseInsertTime().isEmpty())
            this.mInsertDatabaseTime
                    .setText("TIME DB (MS) : " +
                            executionTimePreference.getExecutionTime().getDatabaseInsertTime());
        if (!executionTimePreference.getExecutionTime().getAllInsertTime().isEmpty())
            this.mAllInsertTime
                    .setText("TIME ALL (MS) : " +
                            executionTimePreference.getExecutionTime().getAllInsertTime());
        if (!executionTimePreference.getExecutionTime().getViewInsertTime().isEmpty())
            this.mViewInsertTime
                    .setText("TIME VIEW (MS) : " +
                            executionTimePreference.getExecutionTime().getViewInsertTime());
        if (!executionTimePreference.getExecutionTime().getNumOfRecordInsert().isEmpty())
            this.mNumOfRecord
                    .setText("RECORDS : " +
                            executionTimePreference.getExecutionTime().getNumOfRecordInsert());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_insert, container, false);

        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            this.mPresenter.onAttach(this);
            this.mInsertAdapter.setCallback(this);

            this.executionTimePreference = new ExecutionTimePreference(getBaseActivity());
        }
        return view;
    }

    @Override
    public void onEmptyViewRetryClick(){

    }

    @Override
    public void updateNumOfRecordInsert(Long numOfRecord) {
        this.mNumOfRecord.setText("RECORDS : " + numOfRecord.toString());
    }

    @Override
    public void updateInsertDatabaseTime(Long insertDatabaseTime) {
        this.mInsertDatabaseTime.setText("TIME DB (MS) : " + insertDatabaseTime.toString());
    }

    @Override
    public void updateAllInsertTime(Long allInsertTime) {
        this.mAllInsertTime.setText("TIME ALL (MS) : " + allInsertTime.toString());
    }

    @Override
    public void updateViewInsertTime(Long viewInsertTime) {
        this.mViewInsertTime.setText("TIME VIEW (MS) : " + viewInsertTime.toString());
    }

    @Override
    public void insertMedicalData(List<Medical> medicalList) {
        this.mInsertAdapter.clearItems();
        this.mInsertAdapter.insertItems(medicalList);
    }

    @Override
    public void stateLoadingInsert(boolean state) {
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
