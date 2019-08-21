package com.vectorcoder.androidwoocommerce.fragments;


import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;

import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import com.vectorcoder.androidwoocommerce.activities.MainActivity;
import com.vectorcoder.androidwoocommerce.R;

import com.vectorcoder.androidwoocommerce.app.App;
import com.vectorcoder.androidwoocommerce.customs.CircularImageView;
import com.vectorcoder.androidwoocommerce.constant.ConstantValues;
import com.vectorcoder.androidwoocommerce.customs.DialogLoader;
import com.vectorcoder.androidwoocommerce.models.user_model.UpdateUser;
import com.vectorcoder.androidwoocommerce.models.user_model.UserData;
import com.vectorcoder.androidwoocommerce.models.user_model.UserDetails;
import com.vectorcoder.androidwoocommerce.network.APIClient;
import com.vectorcoder.androidwoocommerce.utils.CheckPermissions;
import com.vectorcoder.androidwoocommerce.utils.Utilities;
import com.vectorcoder.androidwoocommerce.utils.ImagePicker;
import com.vectorcoder.androidwoocommerce.utils.ValidateInputs;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;

import static android.content.Context.MODE_PRIVATE;


public class Update_Account extends Fragment implements TextWatcher{

    View rootView;
    String customers_id;
    String profileImageCurrent = "";
    String profileImageChanged = "";
    private static final int PICK_IMAGE_ID = 360;           // the number doesn't matter
    
    Button updateInfoBtn;
    CircularImageView user_photo;
    FloatingActionButton user_photo_edit_fab;
    EditText input_first_name, input_last_name, input_username, input_email, input_new_password;
    
    UserDetails userInfo;
    DialogLoader dialogLoader;
    SharedPreferences sharedPreferences;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.update_account, container, false);

        // Enable Drawer Indicator with static variable actionBarDrawerToggle of MainActivity
        MainActivity.actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(getString(R.string.actionAccount));
    
        sharedPreferences = getContext().getSharedPreferences("UserInfo", MODE_PRIVATE);
        
        // Get the CustomerID from SharedPreferences
        customers_id = sharedPreferences.getString("userID", "");
    
        // Get the User's Info from the AppContext
        userInfo = ((App) getContext().getApplicationContext()).getUserDetails();
        

        // Binding Layout Views
        user_photo = (CircularImageView) rootView.findViewById(R.id.user_photo);
        input_first_name = (EditText) rootView.findViewById(R.id.first_name);
        input_last_name = (EditText) rootView.findViewById(R.id.last_name);
        input_username = (EditText) rootView.findViewById(R.id.username);
        input_email = (EditText) rootView.findViewById(R.id.email);
        input_new_password = (EditText) rootView.findViewById(R.id.new_password);
        updateInfoBtn = (Button) rootView.findViewById(R.id.updateInfoBtn);
        user_photo_edit_fab = (FloatingActionButton) rootView.findViewById(R.id.user_photo_edit_fab);
    
    
        input_username.setKeyListener(null);

        user_photo_edit_fab.setVisibility(View.GONE);
        
        dialogLoader = new DialogLoader(getContext());
        

        // Get User's Details
        RequestUserDetails(customers_id);


        // Set User's Photo
        if (!TextUtils.isEmpty(userInfo.getAvatarUrl())) {
            profileImageCurrent = userInfo.getAvatarUrl();
            Glide.with(this)
                    .load(ConstantValues.WOOCOMMERCE_URL +profileImageCurrent).asBitmap()
                    .placeholder(R.drawable.profile)
                    .error(R.drawable.profile)
                    .into(user_photo);
            
        }
        else if (!TextUtils.isEmpty(sharedPreferences.getString("userPicture", ""))) {
            profileImageCurrent = sharedPreferences.getString("userPicture", "");
            Glide.with(this)
                    .load(profileImageCurrent)
                    .placeholder(R.drawable.profile)
                    .error(R.drawable.profile)
                    .into(user_photo);
            
        }
        else {
            profileImageCurrent = "";
            Glide.with(this)
                    .load(R.drawable.profile).asBitmap()
                    .placeholder(R.drawable.profile)
                    .error(R.drawable.profile)
                    .into(user_photo);
        }



        // Handle Click event of user_photo_edit_fab FAB
        user_photo_edit_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
                if (CheckPermissions.is_CAMERA_PermissionGranted()  &&  CheckPermissions.is_STORAGE_PermissionGranted()) {
                    pickImage();
                }
                else {
                    requestPermissions
                        (
                            new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            CheckPermissions.PERMISSIONS_REQUEST_CAMERA
                            
                        );
                }
                
            }
        });


        // Handle Click event of updateInfoBtn Button
        updateInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Validate User's Info Form Inputs
                boolean isValidData = validateForm();

                if (isValidData) {
                    processUpdateUser();
                }
            }
        });
    
    
        if (validateFormHasInput()) {
            // Enable Update Button
            updateInfoBtn.setEnabled(true);
            updateInfoBtn.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.rounded_corners_button_accent));
        }
        else {
            // Disable Update Button
            updateInfoBtn.setEnabled(false);
            updateInfoBtn.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.rounded_corners_button_gray));
        }
    
        
        input_first_name.addTextChangedListener(this);
        input_last_name.addTextChangedListener(this);
        input_email.addTextChangedListener(this);
        input_new_password.addTextChangedListener(this);


        return rootView;

    }
    
    
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
    
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (validateFormHasInput()) {
            // Enable Update Button
            updateInfoBtn.setEnabled(true);
            updateInfoBtn.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.rounded_corners_button_accent));
        }
        else {
            // Disable Update Button
            updateInfoBtn.setEnabled(false);
            updateInfoBtn.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.rounded_corners_button_gray));
        }
    }
    
    @Override
    public void afterTextChanged(Editable s) {}
    
    
    
    //*********** Picks User Profile Image from Gallery or Camera ********//

    private void pickImage() {
        // Intent with Image Picker Apps from the static method of ImagePicker class
        Intent chooseImageIntent = ImagePicker.getImagePickerIntent(getContext());
        chooseImageIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        chooseImageIntent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
    
        // Start Activity with Image Picker Intent
        startActivityForResult(chooseImageIntent, PICK_IMAGE_ID);
    }
    
    
    
    //*********** Receives the result from a previous call of startActivityForResult(Intent, int) ********//
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == PICK_IMAGE_ID) {

                // Get the User Selected Image as Bitmap from the static method of ImagePicker class
                Bitmap bitmap = ImagePicker.getImageFromResult(this.getActivity(), resultCode, data);

                // Upload the Bitmap to ImageView
                user_photo.setImageBitmap(bitmap);

                // Get the converted Bitmap as Base64ImageString from the static method of Helper class
                profileImageChanged = Utilities.getBase64ImageStringFromBitmap(bitmap);
            }
        }
    }
    
    
    
    //*********** This method is invoked for every call on requestPermissions(Activity, String[], int) ********//
    
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        
        if (requestCode == CheckPermissions.PERMISSIONS_REQUEST_CAMERA) {
            if (grantResults.length > 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                // The Camera and Storage Permission is granted
                pickImage();
            }
            else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.CAMERA)) {
                    // Show Information about why you need the permission
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle(getString(R.string.permission_camera_storage));
                    builder.setMessage(getString(R.string.permission_camera_storage_needed));
                    builder.setPositiveButton(getString(R.string.grant), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            requestPermissions
                                (
                                    new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                    CheckPermissions.PERMISSIONS_REQUEST_CAMERA
                                );
                        }
                    });
                    builder.setNegativeButton(getString(R.string.not_now), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.show();
                }
                else {
                    Toast.makeText(getContext(),getString(R.string.permission_rejected), Toast.LENGTH_LONG).show();
                }
            }
        }
    }
    
    
    
    //*********** Proceed Update of User's Details ********//
    
    private void processUpdateUser() {
        UpdateUser user = new UpdateUser();
        
        if (!TextUtils.isEmpty(input_first_name.getText().toString()))
            user.setFirstName(input_first_name.getText().toString());
    
        if (!TextUtils.isEmpty(input_last_name.getText().toString()))
            user.setLastName(input_last_name.getText().toString());
    
        if (!TextUtils.isEmpty(input_email.getText().toString()))
            user.setEmail(input_email.getText().toString());
    
        if (!TextUtils.isEmpty(input_new_password.getText().toString()))
            user.setPassword(input_new_password.getText().toString());
        
        updateCustomerInfo(user);
    }
    
    
    
    //*********** Updates User's Personal Information ********//

    private void updateCustomerInfo(UpdateUser userDetails) {

        dialogLoader.showProgressDialog();
    
        Map<String, String> params = new LinkedHashMap<>();
        params.put("insecure", "cool");
        params.put("user_id", customers_id);
       
        
        if (userDetails.getFirstName() != null)
            params.put("first_name", userDetails.getFirstName());
    
        if (userDetails.getLastName() != null)
            params.put("last_name", userDetails.getLastName());
    
        if (userDetails.getEmail() != null)
            params.put("email", userDetails.getEmail());
    
        if (userDetails.getPassword() != null)
            params.put("password", userDetails.getPassword());
        

        Call<UpdateUser> call = APIClient.getInstance()
                .updateCustomerInfo
                        (
                                params
                        );

        call.enqueue(new Callback<UpdateUser>() {
            @Override
            public void onResponse(Call<UpdateUser> call, retrofit2.Response<UpdateUser> response) {

                dialogLoader.hideProgressDialog();
    
                // Check if the Response is successful
                if (response.isSuccessful()) {
                    if ("ok".equalsIgnoreCase(response.body().getStatus())) {
    
                        if (response.body().getUserLogin() != null) {
    
                            // Update User Details
                            UserDetails userDetails = ((App) getContext().getApplicationContext()).getUserDetails();
                            
                            userDetails.setId(response.body().getID());
                            userDetails.setUsername(response.body().getUserLogin());
                            userDetails.setEmail(response.body().getUserEmail());
                            userDetails.setFirstName(response.body().getFirstName());
                            userDetails.setLastName(response.body().getLastName());
                            userDetails.setDisplay_name(response.body().getDisplayName());
    
                            ((App) getContext().getApplicationContext()).setUserDetails(userDetails);
    
    
                            // Save necessary details in SharedPrefs
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("userID", userDetails.getId());
                            editor.putString("userCookie", userDetails.getCookie());
                            editor.putString("userEmail", userDetails.getEmail());
                            editor.putString("userName", userDetails.getUsername());
                            editor.putString("userDisplayName", userDetails.getDisplay_name());
                            editor.putString("userPicture", "");
    
                            if (userDetails.getPicture() != null  &&  userDetails.getPicture().getData() != null)
                                if (!TextUtils.isEmpty(userDetails.getPicture().getData().getUrl()))
                                    editor.putString("userPicture", userDetails.getPicture().getData().getUrl());
    
                            editor.apply();
    
                            ((MainActivity) getContext()).setupExpandableDrawerList();
                            ((MainActivity) getContext()).setupExpandableDrawerHeader();
                            
                            getFragmentManager().popBackStack();
                        }
                        
                        
                        if (response.body().getMessage() != null) {
                            Snackbar.make(rootView, response.body().getMessage(), Snackbar.LENGTH_SHORT).show();
                        }
                        else {
                            Snackbar.make(rootView, getString(R.string.account_updated), Snackbar.LENGTH_SHORT).show();
                        }
                        
                    }
                    else {
                        if (response.body().getError() != null)
                            Snackbar.make(rootView, response.body().getError(), Snackbar.LENGTH_SHORT).show();
                    }
                }
                else {
                    Converter<ResponseBody, UserData> converter = APIClient.retrofit.responseBodyConverter(UserData.class, new java.lang.annotation.Annotation[0]);
                    UserData userData;
                    try {
                        userData = converter.convert(response.errorBody());
                    } catch (IOException e) {
                        userData = new UserData();
                    }
    
                    Toast.makeText(App.getContext(), "Error : "+userData.getError(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UpdateUser> call, Throwable t) {
                dialogLoader.hideProgressDialog();
                Toast.makeText(getContext(), "NetworkCallFailure : "+t, Toast.LENGTH_LONG).show();
            }
        });
    }
    
    
    
    //*********** Get User Details based on User's Email ********//
    
    public void RequestUserDetails(String customerID) {
    
        dialogLoader.showProgressDialog();
    
        Call<UserDetails> call = APIClient.getInstance()
                .getUserInfo
                        (
                                customerID
                        );
    
        call.enqueue(new Callback<UserDetails>() {
            @Override
            public void onResponse(Call<UserDetails> call, retrofit2.Response<UserDetails> response) {
            
                dialogLoader.hideProgressDialog();
            
                // Check if the Response is successful
                if (response.isSuccessful()) {
                    // Set User's Info to Form Inputs
                    if (response.body().getUsername() != null) {
                        input_first_name.setText(response.body().getFirstName());
                        input_last_name.setText(response.body().getLastName());
                        input_username.setText(response.body().getUsername());
                        input_email.setText(response.body().getEmail());
                    }
                }
            }
        
            @Override
            public void onFailure(Call<UserDetails> call, Throwable t) {
                dialogLoader.hideProgressDialog();
//                Toast.makeText(getContext(), "NetworkCallFailure : "+t, Toast.LENGTH_LONG).show();
            }
        });
    }
    
    
    
    //*********** Validate User Info Form Inputs ********//
    
    private boolean validateFormHasInput() {
        
        boolean hasInput = false;
        
        if (ValidateInputs.isValidInput(input_first_name.getText().toString())) {
            hasInput = true;
        }
        else if (ValidateInputs.isValidInput(input_last_name.getText().toString())) {
            hasInput = true;
        }
        else if (ValidateInputs.isValidInput(input_email.getText().toString())) {
            hasInput = true;
        }
        else if (ValidateInputs.isValidInput(input_new_password.getText().toString())) {
            hasInput = true;
        }
        else {
            hasInput = false;
        }
        
        return hasInput;
    }
    
    
    //*********** Validate User Info Form Inputs ********//
    
    private boolean validateForm() {

        if (!ValidateInputs.isIfValidInput(input_first_name.getText().toString())) {
            input_first_name.setError(getString(R.string.invalid_first_name));
            return false;
        }
        else if (!ValidateInputs.isIfValidInput(input_last_name.getText().toString())) {
            input_last_name.setError(getString(R.string.invalid_last_name));
            return false;
        }
        else if (!ValidateInputs.isIfValidInput(input_email.getText().toString())) {
            input_email.setError(getString(R.string.invalid_email));
            return false;
        }
        else if (!TextUtils.isEmpty(input_new_password.getText())) {
            if (!ValidateInputs.isValidPassword(input_new_password.getText().toString())) {
                input_new_password.setError(getString(R.string.invalid_password));
                return false;
            } else {
                return true;
            }
        }
        else {
            return true;
        }
    }

}
