package com.rsandoval.rxjavaexample.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import rx.Observable;
import rx.subjects.PublishSubject;

/**
 * Created by rsandoval on 06/04/2016.
 */
public class Download {

    public Download() {
    }

    public Observable<Boolean> obserbableDownload(String source, String destination, String nombreFile, PublishSubject<Integer> downloadProgress) {
        return Observable.create(subscriber -> {
            try {
                boolean result = downloadFile(source, destination, nombreFile, downloadProgress);
                if (result) {
                    subscriber.onNext(true);
                    subscriber.onCompleted();
                } else {
                    subscriber.onError(new Throwable("Download failed."));
                }
            } catch (Exception e) {
                subscriber.onError(e);
            }
        });

    }

    private boolean downloadFile(String source, String destination, String nombreFile, PublishSubject<Integer> downloadProgress) {
        boolean result = false;
        InputStream input = null;
        OutputStream output = null;
        HttpURLConnection connection = null;
        try {
            URL url = new URL(source);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return false;
            }

            int fileLength = connection.getContentLength();

            input = connection.getInputStream();
            output = new FileOutputStream(new File(String.format("%s%s", destination, nombreFile)));

            byte data[] = new byte[4096];
            long total = 0;
            int count;
            while ((count = input.read(data)) != -1) {
                total += count;

                if (fileLength > 0) {
                    int percentage = (int) (total * 100 / fileLength);
                    downloadProgress.onNext(percentage);
                }
                output.write(data, 0, count);
            }
            downloadProgress.onCompleted();
            result = true;
        } catch (Exception e) {
            downloadProgress.onError(e);
        } finally {
            try {
                if (output != null) {
                    output.close();
                }
                if (input != null) {
                    input.close();
                }
            } catch (IOException e) {
                downloadProgress.onError(e);
            }

            if (connection != null) {
                connection.disconnect();
                downloadProgress.onCompleted();
            }
        }
        return result;
    }


}
