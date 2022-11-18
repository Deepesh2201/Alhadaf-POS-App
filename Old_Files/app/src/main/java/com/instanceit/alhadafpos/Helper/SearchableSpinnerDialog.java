package com.instanceit.alhadafpos.Helper;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.instanceit.alhadafpos.Entity.Model;
import com.instanceit.alhadafpos.R;

import java.util.ArrayList;

import io.michaelrocks.paranoid.Obfuscate;

@Obfuscate
@SuppressWarnings("unchecked")

public class SearchableSpinnerDialog {
    ArrayList<Model> SubcategoryArrayList;
    Activity context;
    String dTitle;
    public OnSpinerItemClickSpinnerDialog onSpinerItemClick;
    public AlertDialog alertDialog;
    int pos;
    public EditText searchBox;
    int style;
    public boolean dialogShownOnce = false;
    AlertDialog.Builder adb;
    View v;


    public SearchableSpinnerDialog(Activity activity, ArrayList<Model> items, String dialogTitle) {
        this.SubcategoryArrayList = items;
        this.context = activity;
        this.dTitle = dialogTitle;
    }

    public SearchableSpinnerDialog(Activity activity, ArrayList<Model> items, String dialogTitle, int style) {
        this.SubcategoryArrayList = items;
        this.context = activity;
        this.dTitle = dialogTitle;
        this.style = style;
        dialogShownOnce = false;
        adb = new AlertDialog.Builder(this.context);
        v = this.context.getLayoutInflater().inflate(R.layout.dialog_recyclerspinnerlayout, null);
        adb.setView(v);
        this.alertDialog = adb.create();
    }


    public void bindOnSpinerListener(OnSpinerItemClickSpinnerDialog onSpinerItemClick1) {
        this.onSpinerItemClick = onSpinerItemClick1;
    }


    public void showSpinerDialog() {
        if (!alertDialog.isShowing() && !dialogShownOnce) {
            v.setFocusableInTouchMode(true);

            TextView rippleViewClose = v.findViewById(R.id.close);
            TextView title = v.findViewById(R.id.spinerTitle);
            title.setText(this.dTitle);
            title.setTextColor(Color.parseColor("#000000"));
            RecyclerView listView = v.findViewById(R.id.list);
            searchBox = v.findViewById(R.id.searchBox);
            searchBox.setFocusableInTouchMode(true);

            listView.setLayoutManager(new LinearLayoutManager(this.context));

            final SearchableSpinnerAdapter adapter = new SearchableSpinnerAdapter(this.context, this.SubcategoryArrayList, SearchableSpinnerDialog.this);
            listView.setAdapter(adapter);

            this.alertDialog.getWindow().getAttributes().windowAnimations = this.style;
            this.alertDialog.setCancelable(false);


            searchBox.addTextChangedListener(new TextWatcher() {
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                public void afterTextChanged(Editable editable) {
                    adapter.getFilter().filter(searchBox.getText().toString());
                }
            });
            rippleViewClose.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    dialogShownOnce = false;
                    searchBox.setText("");
                    alertDialog.dismiss();
                }
            });

            if (!alertDialog.isShowing() && !dialogShownOnce) {
                alertDialog.show();
                dialogShownOnce = true;
            }
        }
    }

}

