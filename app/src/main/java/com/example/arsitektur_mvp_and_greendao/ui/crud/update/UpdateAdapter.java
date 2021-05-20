package com.example.arsitektur_mvp_and_greendao.ui.crud.update;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.arsitektur_mvp_and_greendao.R;
import com.example.arsitektur_mvp_and_greendao.data.others.Medical;
import com.example.arsitektur_mvp_and_greendao.ui.base.BaseViewHolder;

import java.util.List;

public class UpdateAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    public static final int VIEW_TYPE_EMPTY = 0;
    public static final int VIEW_TYPE_NORMAL = 1;

    private Callback mCallback;
    private List<Medical> medicalList;

    public UpdateAdapter(List<Medical> medicalList) {
        this.medicalList = medicalList;
        Log.d("UA", "UpdateAdapter: " + medicalList.size());
    }

    public void setMedicalList(List<Medical> medicalList) {
        this.medicalList.addAll(medicalList);
    }

    public void setCallback(Callback mCallback) {
        this.mCallback = mCallback;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_NORMAL:
                return new UpdateViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_update_view, parent, false));
            case VIEW_TYPE_EMPTY:
            default:
                return new UpdateEmptyViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_update_empty_view, parent, false));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (this.medicalList != null && this.medicalList.size() > 0) {
            return VIEW_TYPE_NORMAL;
        } else {
            return VIEW_TYPE_EMPTY;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        if (this.medicalList != null && this.medicalList.size() > 0) {
            return this.medicalList.size();
        } else {
            return 1;
        }
    }

    public void updateItems(List<Medical> medicalList) {
        this.medicalList.addAll(medicalList);
        notifyDataSetChanged();
        Log.d("UA", "updateItems: " + medicalList.size());
    }

    public void clearItems() {
        this.medicalList.clear();
    }

    public interface Callback {
        void onEmptyViewRetryClick();
    }

    public class UpdateViewHolder extends BaseViewHolder {

        private TextView id;

        private TextView hospitalName;

        private TextView medicineName;

        public UpdateViewHolder(View itemView) {
            super(itemView);
            this.id = itemView.findViewById(R.id.medicalIdTextView);
            this.hospitalName = itemView.findViewById(R.id.hospitalNameTextView);
            this.medicineName = itemView.findViewById(R.id.medicineNameTextView);
        }

        protected void clear() {
            this.id.setText("");
            this.hospitalName.setText("");
            this.medicineName.setText("");
        }

        public void onBind(int position) {
            super.onBind(position);

            final Medical medical = medicalList.get(position);

            if (position >= 0) {
                this.id.setText(String.valueOf(position));
            }

            if (medical.getHospitalName() != null) {
                this.hospitalName.setText(medical.getHospitalName());
            }

            if (medical.getMedicineName() != null) {
                this.medicineName.setText(medical.getMedicineName());
            }
        }
    }

    public class UpdateEmptyViewHolder extends BaseViewHolder {

        Button retryButton;

        TextView messageTextView;

        public UpdateEmptyViewHolder(View itemView) {
            super(itemView);
            retryButton = itemView.findViewById(R.id.btnRetry);
            messageTextView = itemView.findViewById(R.id.tvMessage);

            retryButton.setOnClickListener(view -> onRetryClick());
        }

        @Override
        protected void clear() {
            messageTextView.setText("");
        }

        void onRetryClick() {
            if (mCallback != null)
                mCallback.onEmptyViewRetryClick();
        }
    }
}
