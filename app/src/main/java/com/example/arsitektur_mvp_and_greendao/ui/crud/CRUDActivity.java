package com.example.arsitektur_mvp_and_greendao.ui.crud;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.example.arsitektur_mvp_and_greendao.R;
import com.example.arsitektur_mvp_and_greendao.data.others.ExecutionTimePreference;
import com.example.arsitektur_mvp_and_greendao.ui.base.BaseActivity;
import com.example.arsitektur_mvp_and_greendao.utils.InformationDialogFragment;
import com.google.android.material.tabs.TabLayout;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

public class CRUDActivity extends BaseActivity {

    @Inject
    CRUDPagerAdapter mPagerAdapter;

    Toolbar mToolBar;

    ExecutionTimePreference executionTimePreference;
    String fileName = "/Execution_Time.xls";
    File filePath = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + fileName);

    ViewPager mViewPager;

    TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud);

        this.mToolBar = findViewById(R.id.toolbar);
        this.mViewPager = findViewById(R.id.crudViewPager);
        this.mTabLayout = findViewById(R.id.tabLayout);

        getActivityComponent().inject(this);
        setUp();
        showInformationDialog();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.right_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Drawable drawable = item.getIcon();
        if (drawable instanceof Animatable)
            ((Animatable) drawable).start();
        switch (item.getItemId()) {
            case R.id.action_info:
                showInformationDialog();
                return true;
            case R.id.action_export:
                HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yy HH.mm.ss");
                Date date = new Date();
                HSSFSheet hssfSheet = hssfWorkbook.createSheet(formatter.format(date));

                List<List<String>> listExecutionTime = new ArrayList<>();
                List<String> dataExecutionTime = new ArrayList<>();
                dataExecutionTime.add("TYPE CRUD");
                dataExecutionTime.add("NUMBER OF RECORD");
                dataExecutionTime.add("TIME DB (MS)");
                dataExecutionTime.add("TIME VIEW (MS)");
                dataExecutionTime.add("TIME ALL (MS)");
                listExecutionTime.add(dataExecutionTime);
                dataExecutionTime = new ArrayList<>();
                dataExecutionTime.add("INSERT");
                dataExecutionTime.add(executionTimePreference.getExecutionTime().getNumOfRecordInsert());
                dataExecutionTime.add(executionTimePreference.getExecutionTime().getDatabaseInsertTime());
                dataExecutionTime.add(executionTimePreference.getExecutionTime().getViewInsertTime());
                dataExecutionTime.add(executionTimePreference.getExecutionTime().getAllInsertTime());
                listExecutionTime.add(dataExecutionTime);
                dataExecutionTime = new ArrayList<>();
                dataExecutionTime.add("SELECT");
                dataExecutionTime.add(executionTimePreference.getExecutionTime().getNumOfRecordSelect());
                dataExecutionTime.add(executionTimePreference.getExecutionTime().getDatabaseSelectTime());
                dataExecutionTime.add(executionTimePreference.getExecutionTime().getViewSelectTime());
                dataExecutionTime.add(executionTimePreference.getExecutionTime().getAllSelectTime());
                listExecutionTime.add(dataExecutionTime);
                dataExecutionTime = new ArrayList<>();
                dataExecutionTime.add("UPDATE");
                dataExecutionTime.add(executionTimePreference.getExecutionTime().getNumOfRecordUpdate());
                dataExecutionTime.add(executionTimePreference.getExecutionTime().getDatabaseUpdateTime());
                dataExecutionTime.add(executionTimePreference.getExecutionTime().getViewUpdateTime());
                dataExecutionTime.add(executionTimePreference.getExecutionTime().getAllUpdateTime());
                listExecutionTime.add(dataExecutionTime);
                dataExecutionTime = new ArrayList<>();
                dataExecutionTime.add("DELETE");
                dataExecutionTime.add(executionTimePreference.getExecutionTime().getNumOfRecordDelete());
                dataExecutionTime.add(executionTimePreference.getExecutionTime().getDatabaseDeleteTime());
                dataExecutionTime.add(executionTimePreference.getExecutionTime().getViewDeleteTime());
                dataExecutionTime.add(executionTimePreference.getExecutionTime().getAllDeleteTime());
                listExecutionTime.add(dataExecutionTime);
                int rowNum = 0;
                for (int i = 0; i < listExecutionTime.size(); i++) {
                    List<String> objArr = listExecutionTime.get(i);
                    HSSFRow row = hssfSheet.createRow(rowNum++);

                    int cellNum = 0;
                    for (String obj : objArr) {
                        Cell cell = row.createCell(cellNum++);
                        cell.setCellValue(obj);
                    }
                }

                try {
                    if (!filePath.exists()){
                        filePath.createNewFile();
                    }

                    FileOutputStream fileOutputStream= new FileOutputStream(filePath);
                    hssfWorkbook.write(fileOutputStream);

                    if (fileOutputStream!=null){
                        fileOutputStream.flush();
                        fileOutputStream.close();
                    }
                    Toast.makeText(this, "SAVE FILE SUCCESS", Toast.LENGTH_SHORT).show();
                    Log.d("TAG", "onOptionsItemSelected: SAVE FILE SUCCESS");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showInformationDialog() {
        FragmentManager fragmentManager = getSupportFragmentManager();

        InformationDialogFragment dialogFragment = InformationDialogFragment.newInstance();
        dialogFragment.show(fragmentManager, "information_dialog");
    }


    @Override
    protected void setUp() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                PackageManager.PERMISSION_GRANTED);

        executionTimePreference = new ExecutionTimePreference(this);
        mToolBar.getOverflowIcon().setColorFilter(
                ContextCompat.getColor(this, R.color.white), PorterDuff.Mode.SRC_ATOP);
        setSupportActionBar(mToolBar);
        mPagerAdapter.setCount(4);

        mViewPager.setAdapter(mPagerAdapter);

        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.tab_text_1));
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.tab_text_2));
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.tab_text_3));
        mTabLayout.addTab(mTabLayout.newTab().setText(R.string.tab_text_4));

        mViewPager.setOffscreenPageLimit(mTabLayout.getTabCount());

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

}