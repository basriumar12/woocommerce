package com.vectorcoder.androidwoocommerce.adapters;

import android.Manifest;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.vectorcoder.androidwoocommerce.R;
import com.vectorcoder.androidwoocommerce.models.download.DownloadsModel;
import com.vectorcoder.androidwoocommerce.utils.CheckPermissions;
import com.vectorcoder.androidwoocommerce.utils.Utilities;

import java.util.List;

/**
 * Created by Muhammad Nabeel on 07/11/2018.
 */
public class DownloadAdapter extends RecyclerView.Adapter<DownloadAdapter.MyViewHolder> {
    
    Context context;
    List<DownloadsModel> ordersList;
    Activity activity;
    
    
    public DownloadAdapter(Context context,Activity activity, List<DownloadsModel> ordersList) {
        this.context = context;
        this.ordersList = ordersList;
        this.activity = activity;
    }
    
    
    
    //********** Called to Inflate a Layout from XML and then return the Holder *********//
    
    @Override
    public DownloadAdapter.MyViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        // Inflate the custom layout
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_items_download, parent, false);
        
        return new DownloadAdapter.MyViewHolder(itemView);
    }
    
    
    
    //********** Called by RecyclerView to display the Data at the specified Position *********//
    
    @Override
    public void onBindViewHolder(final DownloadAdapter.MyViewHolder holder, final int position) {
        
        // Get the data model based on Position
        final DownloadsModel downloadDetails = ordersList.get(position);
        
        holder.download_txt.setText(downloadDetails.getDownloadName());
        holder.product_name.setText(downloadDetails.getProductName());
        holder.expiry_txt.setText(downloadDetails.getAccessExpires());
        final String url = downloadDetails.getDownloadUrl();
        
        holder.download_div.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            
                if(Utilities.isDownloadManagerAvailable(context)){
    
                    if (CheckPermissions.is_STORAGE_PermissionGranted()) {
                        downloadItem(downloadDetails,url);
                    }
                    else {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            activity.requestPermissions
                                    (
                                            new String[]{ Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                            CheckPermissions.PERMISSIONS_REQUEST_STORAGE
                                    );
                        }
                    }
                    
                }
                else {
    
                    Toast.makeText(context,"Please update your android version",Toast.LENGTH_LONG).show();
                }
                
            }
        });
        
    }
    
    
    
    //********** Returns the total number of items in the data set *********//
    
    @Override
    public int getItemCount() {
        return ordersList.size();
    }
    
    
    
    /********** Custom ViewHolder provides a direct reference to each of the Views within a Data_Item *********/
    
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        
        private  RelativeLayout download_div;
        private TextView download_txt, expiry_txt, product_name;
        
        
        public MyViewHolder(final View itemView) {
            super(itemView);
    
            download_div = itemView.findViewById(R.id.download_div);
            download_txt = (TextView) itemView.findViewById(R.id.download_txt);
            expiry_txt = (TextView) itemView.findViewById(R.id.expiry_txt);
            product_name = (TextView) itemView.findViewById(R.id.product_name);
            
        }
    }
    
    private void downloadItem(DownloadsModel downloadDetails,String url){
        
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setDescription(downloadDetails.getDownloadName());
        request.setTitle(downloadDetails.getDownloadName());
        // in order for this if to run, you must use the android 3.2 to compile your app
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            request.allowScanningByMediaScanner();
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        }
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, downloadDetails.getProductName());
    
        // get download service and enqueue file
        DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);
    }
}
