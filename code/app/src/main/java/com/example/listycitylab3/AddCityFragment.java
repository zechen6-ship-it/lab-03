package com.example.listycitylab3;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class AddCityFragment extends DialogFragment {

    private static final String ARG_CITY = "city";
    private static final String ARG_PROVINCE = "province";
    private static final String ARG_POSITION = "position";

    public static AddCityFragment newInstance(String city, String province, int position) {
        AddCityFragment fragment = new AddCityFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CITY, city);
        args.putString(ARG_PROVINCE, province);
        args.putInt(ARG_POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    interface AddCityDialogListener {
        void addCity(City city);
        void updateCity(City city, int position);
    }
    private AddCityDialogListener listener;

    public void onAttach(@NonNull Context context){
        super.onAttach(context);
        if (context instanceof AddCityDialogListener) {
            listener = (AddCityDialogListener) context;
        } else {
            throw new RuntimeException(context + " must implement AddCityDialogListener");
        }

    }
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_add_city, null);
        EditText editCityName = view.findViewById(R.id.edit_text_city_text);
        EditText editProvinceName = view.findViewById(R.id.edit_text_province_text);
        int pos = -1;

        if (getArguments() != null){
            editCityName.setText(getArguments().getString(ARG_CITY, ""));
            editProvinceName.setText(getArguments().getString(ARG_PROVINCE,""));
            pos = getArguments().getInt(ARG_POSITION, -1);
        }

        boolean isEdit = pos >= 0;
        final int finalPos = pos;

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        return builder
                .setView(view)
                .setTitle(isEdit ? "Edit City" : "Add a City")
                .setNegativeButton("Cancel", null)
                .setPositiveButton(isEdit ? "Save" : "Add", (dialog, which) -> {
                    String cityName = editCityName.getText().toString();
                    String provinceName = editProvinceName.getText().toString();
                    City city = new City(cityName, provinceName);

                    if (isEdit) {
                        listener.updateCity(city, finalPos);
                    } else {
                        listener.addCity(city);
                    }
                })
                .create();
    }


}
