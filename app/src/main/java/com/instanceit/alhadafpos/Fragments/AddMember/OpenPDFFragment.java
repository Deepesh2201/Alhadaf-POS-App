package com.instanceit.alhadafpos.Fragments.AddMember;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.barteksc.pdfviewer.PDFView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.instanceit.alhadafpos.Activities.MainActivity;

import com.instanceit.alhadafpos.Fragments.ServiceOrder.ServiceOrderListingFragment;
import com.instanceit.alhadafpos.R;
import com.instanceit.alhadafpos.Utility.AppConstant;
import com.instanceit.alhadafpos.Utility.SessionManagement;
import com.instanceit.alhadafpos.VolleyPlus.misc.AsyncTask;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class OpenPDFFragment extends Fragment {

    MainActivity mainActivity;
    //Views
    PDFView idPDFView;
    FloatingActionButton btn_printer;

    //variables
    String PDF_URL = "";
    String action_str = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = (MainActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_open_p_d_f, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Declaration(view);
        Initialization();
        onBackPress(view);
    }

    private void Declaration(View view) {
        idPDFView = view.findViewById(R.id.idPDFView);
        btn_printer = view.findViewById(R.id.btn_printer);
    }

    private void Initialization() {
        idPDFView.zoomTo(2f);
        Bundle bundle = getArguments();
        if (bundle != null) {
            action_str = bundle.getString(AppConstant.ACTION);
            PDF_URL = bundle.getString("URL");
        }

        new RetrivePDFfromUrl().execute(PDF_URL);

        btn_printer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(PDF_URL));
                startActivity(browserIntent);
            }
        });
    }


    class RetrivePDFfromUrl extends AsyncTask<String, Void, InputStream> {
        @Override
        protected InputStream doInBackground(String... strings) {
            // we are using inputstream
            // for getting out PDF.
            InputStream inputStream = null;
            try {
                URL url = new URL(strings[0]);
                // below is the step where we are
                // creating our connection.
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                if (urlConnection.getResponseCode() == 200) {
                    // response is success.
                    // we are getting input stream from url
                    // and storing it in our variable.
                    inputStream = new BufferedInputStream(urlConnection.getInputStream());
                }

            } catch (IOException e) {
                // this is the method
                // to handle errors.
                e.printStackTrace();
                return null;
            }
            return inputStream;
        }

        @Override
        protected void onPostExecute(InputStream inputStream) {
            // after the execution of our async
            // task we are loading our pdf in our pdf view.
            idPDFView.fromStream(inputStream).load();
            idPDFView.zoomTo(1.5f);
        }
    }


    private void onBackPress(View view) {
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                    if (action_str.equals(AppConstant.ACTION_LISTING)) {
                        Bundle bundle = new Bundle();
                        bundle.putString(AppConstant.ACTION, AppConstant.ACTION_LISTING);
                        Fragment fragment = new ServiceOrderListingFragment();
                        fragment.setArguments(bundle);
                        mainActivity.addFragment(fragment);
                    } else if (action_str.equals(AppConstant.ACTION_LISTING_RIGHTS)) {
                        Bundle bundle = new Bundle();
                        bundle.putString(AppConstant.ACTION, AppConstant.ACTION_LISTING_RIGHTS);
                        Fragment fragment = new ServiceOrderListingFragment();
                        fragment.setArguments(bundle);
                        mainActivity.addFragment(fragment);
                    } else {
                        mainActivity.addFragment(new MembershipListingFragment());
                    }
                    return true;
                }
                return false;
            }
        });
    }

}