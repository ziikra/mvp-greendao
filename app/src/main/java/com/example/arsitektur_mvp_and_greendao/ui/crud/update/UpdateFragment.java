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
import com.example.arsitektur_mvp_and_greendao.data.others.Medical;
import com.example.arsitektur_mvp_and_greendao.di.component.ActivityComponent;
import com.example.arsitektur_mvp_and_greendao.ui.base.BaseFragment;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

public class UpdateFragment extends BaseFragment implements UpdateMvpView, UpdateAdapter.Callback {

    private static final String TAG = "UpdateFragment";

    @Inject
    @Named("updatePresenter")
    UpdatePresenter<UpdateMvpView> mPresenter;

    @Inject
    @Named("updateAdapter")
    UpdateAdapter mUpdateAdapter;

    @Inject
    LinearLayoutManager mLayoutManager;

    ContentLoadingProgressBar progressBar;

    RecyclerView mRecyclerView;

    TextView mNumOfRecord;

    TextView mExecutionTime;

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
        this.mExecutionTime = view.findViewById(R.id.textViewTime);
        this.mEditTextNumData = view.findViewById(R.id.editTextNumData);
        this.btnExecute = view.findViewById(R.id.btnExecute);
        this.progressBar = view.findViewById(R.id.progressBar);

        this.btnExecute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mEditTextNumData.getText() != null) {
                    try {
                        Long numOfData = Long.valueOf(mEditTextNumData.getText().toString());
                        mPresenter.onUpdateExecuteClick(numOfData);
                    } catch (Exception e) {
                        Toast.makeText(getContext(), "Num Of Data is Not Valid", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.d(TAG, "onClick: ");
                    Toast.makeText(getContext(), "Num Of Data is Not Valid", Toast.LENGTH_SHORT).show();
                }
            }
        });

        this.mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        this.mRecyclerView.setLayoutManager(mLayoutManager);
        this.mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        this.mRecyclerView.setAdapter(mUpdateAdapter);
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
        }
        return view;
    }

    @Override
    public void onEmptyViewRetryClick() {

    }

    @Override
    public void updateNumOfRecordUpdate(Long numOfRecord) {
        this.mNumOfRecord.setText("RECORD : " + numOfRecord.toString());
    }

    @Override
    public void updateExecutionTimeUpdate(Long executionTime) {
        this.mExecutionTime.setText("TIME (MS) : " + executionTime.toString());
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
