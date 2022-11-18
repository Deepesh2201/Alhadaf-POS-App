package com.instanceit.alhadafpos.VolleyPlus.toolbox.multipart;

import com.instanceit.alhadafpos.VolleyPlus.Response.ProgressListener;

import java.io.IOException;
import java.io.OutputStream;

public class MultipartProgressEntity extends MultipartEntity {

    private ProgressListener listener;

    public void setListener(ProgressListener listener) {
        this.listener = listener;
    }

    @Override
    public void writeTo(OutputStream outstream) throws IOException {
        if (listener == null) {
            super.writeTo(outstream);
        } else {
            super.writeTo(new OutputStreamProgress(outstream, this.listener));
        }
    }
}