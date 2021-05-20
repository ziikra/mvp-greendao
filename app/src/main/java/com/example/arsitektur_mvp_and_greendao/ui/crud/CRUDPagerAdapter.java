package com.example.arsitektur_mvp_and_greendao.ui.crud;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.arsitektur_mvp_and_greendao.ui.crud.delete.DeleteFragment;
import com.example.arsitektur_mvp_and_greendao.ui.crud.insert.InsertFragment;
import com.example.arsitektur_mvp_and_greendao.ui.crud.select.SelectFragment;
import com.example.arsitektur_mvp_and_greendao.ui.crud.update.UpdateFragment;

public class CRUDPagerAdapter extends FragmentStatePagerAdapter {

    private int mTabCount;

    public CRUDPagerAdapter(FragmentManager fm){
        super(fm);
        this.mTabCount = 0;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return InsertFragment.newInstance();
            case 1:
                return SelectFragment.newInstance();
            case 2:
                return UpdateFragment.newInstance();
            case 3:
                return DeleteFragment.newInstance();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return this.mTabCount;
    }

    public void setCount(int mTabCount) {
        this.mTabCount = mTabCount;
    }

}
