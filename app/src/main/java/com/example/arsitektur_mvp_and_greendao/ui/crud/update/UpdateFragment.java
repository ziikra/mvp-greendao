package com.example.arsitektur_mvp_and_greendao.ui.crud.update;

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

public class UpdateFragment extends BaseFragment implements UpdateMvpView, UpdateAdapter.Callback {

    private static final String TAG = "UpdateFragment";

    @Inject //Penggunaan dependency injection untuk presenter update
    @Named("updatePresenter")
    UpdatePresenter<UpdateMvpView> mPresenter; //Deklarasi Presenter pada view Update

    @Inject //Penggunaan dependency injection untuk adapter update
    @Named("updateAdapter")
    UpdateAdapter mUpdateAdapter; //Deklarasi Adapter pada view Update

    @Inject
    LinearLayoutManager mLayoutManager;

    ContentLoadingProgressBar progressBar;

    RecyclerView mRecyclerView;

    ExecutionTimePreference executionTimePreference;

    TextView mNumOfRecord;

    TextView mUpdateDatabaseTime;

    TextView mAllUpdateTime;

    TextView mViewUpdateTime;

    EditText mEditTextNumData;

    Button btnExecute;

    public static UpdateFragment newInstance() {
        UpdateFragment fragment = new UpdateFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void setUp(View view) {
        this.mRecyclerView = view.findViewById(R.id.updateRecyclerView);
        this.mNumOfRecord = view.findViewById(R.id.textViewRecord);
        this.mUpdateDatabaseTime = view.findViewById(R.id.textViewTimeUpdateDB);
        this.mAllUpdateTime = view.findViewById(R.id.textViewTimeUpdateAll);
        this.mViewUpdateTime = view.findViewById(R.id.textViewTimeUpdateView);
        this.mEditTextNumData = view.findViewById(R.id.editTextNumData);
        this.btnExecute = view.findViewById(R.id.btnExecute);
        this.progressBar = view.findViewById(R.id.progressBar);

        this.btnExecute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mEditTextNumData.getText() != null) {
                    try {
                        Long numOfData = Long.valueOf(mEditTextNumData.getText().toString());
                        mPresenter.onUpdateExecuteClick(executionTimePreference, numOfData);
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
        this.mRecyclerView.setAdapter(mUpdateAdapter);

        if (!executionTimePreference.getExecutionTime().getDatabaseUpdateTime().isEmpty())
            this.mUpdateDatabaseTime
                    .setText("TIME DB (MS) : " +
                            executionTimePreference.getExecutionTime().getDatabaseUpdateTime());
        if (!executionTimePreference.getExecutionTime().getAllUpdateTime().isEmpty())
            this.mAllUpdateTime
                    .setText("TIME ALL (MS) : " +
                            executionTimePreference.getExecutionTime().getAllUpdateTime());
        if (!executionTimePreference.getExecutionTime().getViewUpdateTime().isEmpty())
            this.mViewUpdateTime
                    .setText("TIME VIEW (MS) : " +
                            executionTimePreference.getExecutionTime().getViewUpdateTime());
        if (!executionTimePreference.getExecutionTime().getNumOfRecordUpdate().isEmpty())
            this.mNumOfRecord
                    .setText("RECORDS : " +
                            executionTimePreference.getExecutionTime().getNumOfRecordUpdate());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_update, container, false);

        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            this.mPresenter.onAttach(this);
            this.mUpdateAdapter.setCallback(this);

            this.executionTimePreference = new ExecutionTimePreference(getBaseActivity());
        }
        return view;
    }

    @Override
    public void onEmptyViewRetryClick() {

    }

    @Override
    public void updateNumOfRecordUpdate(Long numOfRecord) {
        this.mNumOfRecord.setText("RECORDS : " + numOfRecord.toString());
    }

    @Override
    public void updateUpdateDatabaseTime(Long updateDatabaseTime) {
        this.mUpdateDatabaseTime.setText("TIME DB (MS) : " + updateDatabaseTime.toString());
    }

    @Override
    public void updateAllUpdateTime(Long allUpdateTime) {
        this.mAllUpdateTime.setText("TIME ALL (MS) : " + allUpdateTime.toString());
    }

    @Override
    public void updateViewUpdateTime(Long viewUpdateTime) {
        this.mViewUpdateTime.setText("TIME VIEW (MS) : " + viewUpdateTime.toString());
    }

    @Override
    public void updateMedicalData(List<Medical> medicalList) {
        this.mUpdateAdapter.clearItems();
        this.mUpdateAdapter.updateItems(medicalList);
    }

    @Override
    public void stateLoadingUpdate(boolean state) {
        if (state)
            this.progressBar.setVisibility(View.VISIBLE);
        else
            this.progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onDestroyView() {
        this.mPresenter.onDetach();
        super.onDestroyView();
    }
}
