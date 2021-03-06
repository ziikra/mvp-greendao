package com.example.arsitektur_mvp_and_greendao.ui.crud.delete;

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

public class DeleteFragment extends BaseFragment implements DeleteMvpView, DeleteAdapter.Callback {

    private static final String TAG = "DeleteFragment";

    @Inject //Penggunaan dependency injection untuk presenter delete
    @Named("deletePresenter")
    DeletePresenter<DeleteMvpView> mPresenter; //Deklarasi Presenter pada view Delete

    @Inject //Penggunaan dependency injection untuk adapter delete
    @Named("deleteAdapter")
    DeleteAdapter mDeleteAdapter; //Deklarasi Adapter pada view Delete

    @Inject
    LinearLayoutManager mLayoutManager;

    ContentLoadingProgressBar progressBar;

    RecyclerView mRecyclerView;

    ExecutionTimePreference executionTimePreference;

    TextView mNumOfRecord;

    TextView mDeleteDatabaseTime;

    TextView mAllDeleteTime;

    TextView mViewDeleteTime;

    EditText mEditTextNumData;

    Button btnExecute;

    public static DeleteFragment newInstance() {
        DeleteFragment fragment = new DeleteFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void setUp(View view) {
        this.mRecyclerView = view.findViewById(R.id.deleteRecyclerView);
        this.mNumOfRecord = view.findViewById(R.id.textViewRecord);
        this.mDeleteDatabaseTime = view.findViewById(R.id.textViewTimeDeleteDB);
        this.mAllDeleteTime = view.findViewById(R.id.textViewTimeDeleteAll);
        this.mViewDeleteTime = view.findViewById(R.id.textViewTimeDeleteView);
        this.mEditTextNumData = view.findViewById(R.id.editTextNumData);
        this.btnExecute = view.findViewById(R.id.btnExecute);
        this.progressBar = view.findViewById(R.id.progressBar);

        this.btnExecute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mEditTextNumData.getText() != null) {
                    try {
                        Long numOfData = Long.valueOf(mEditTextNumData.getText().toString());
                        mPresenter.onDeleteExecuteClick(executionTimePreference, numOfData);
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
        this.mRecyclerView.setAdapter(mDeleteAdapter);

        if (!executionTimePreference.getExecutionTime().getDatabaseDeleteTime().isEmpty())
            this.mDeleteDatabaseTime
                    .setText("TIME DB (MS) : " +
                            executionTimePreference.getExecutionTime().getDatabaseDeleteTime());
        if (!executionTimePreference.getExecutionTime().getAllDeleteTime().isEmpty())
            this.mAllDeleteTime
                    .setText("TIME ALL (MS) : " +
                            executionTimePreference.getExecutionTime().getAllDeleteTime());
        if (!executionTimePreference.getExecutionTime().getViewDeleteTime().isEmpty())
            this.mViewDeleteTime
                    .setText("TIME VIEW (MS) : " +
                            executionTimePreference.getExecutionTime().getViewDeleteTime());
        if (!executionTimePreference.getExecutionTime().getNumOfRecordDelete().isEmpty())
            this.mNumOfRecord
                    .setText("RECORDS : " +
                            executionTimePreference.getExecutionTime().getNumOfRecordDelete());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_delete, container, false);

        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            this.mPresenter.onAttach(this);
            this.mDeleteAdapter.setCallback(this);

            this.executionTimePreference = new ExecutionTimePreference(getBaseActivity());
        }
        return view;
    }

    @Override
    public void onEmptyViewRetryClick() {

    }

    @Override
    public void updateNumOfRecordDelete(Long numOfRecord) {
        this.mNumOfRecord.setText("RECORDS : " + numOfRecord.toString());
    }

    @Override
    public void updateDeleteDatabaseTime(Long deleteDatabaseTime) {
        this.mDeleteDatabaseTime.setText("TIME DB (MS) : " + deleteDatabaseTime.toString());
    }

    @Override
    public void updateAllDeleteTime(Long allDeleteTime) {
        this.mAllDeleteTime.setText("TIME ALL (MS) : " + allDeleteTime.toString());
    }

    @Override
    public void updateViewDeleteTime(Long viewDeleteTime) {
        this.mViewDeleteTime.setText("TIME VIEW (MS) : " + viewDeleteTime.toString());
    }

    @Override
    public void deleteMedicalData(List<Medical> medicalList) {
        this.mDeleteAdapter.clearItems();
        this.mDeleteAdapter.deleteItems(medicalList);
    }

    @Override
    public void stateLoadingDelete(boolean state) {
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
