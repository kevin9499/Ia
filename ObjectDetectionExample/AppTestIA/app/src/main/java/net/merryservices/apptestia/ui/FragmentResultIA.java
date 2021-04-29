package net.merryservices.apptestia.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import net.merryservices.apptestia.services.IOnResultAPI;
import net.merryservices.apptestia.R;

public class FragmentResultIA extends Fragment implements IOnResultAPI {

    LinearLayout principaleLayout, firstButtonsLayout;
    TextView textView;
    Spinner spinnerSelect;
    Button buttonCorrection, buttonValidation, buttonCorrectionValidation;

    String classname[]= {"Avion", "Voiture", "Oiseau", "Chat", "Dain",
                            "Chien", "Grenouille", "Cheval", "Mouton", "Camion"};
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_resultia, null);
        principaleLayout= v.findViewById(R.id.linearLayoutResult);
        principaleLayout.setVisibility(View.INVISIBLE);
        textView= v.findViewById(R.id.textViewResult);
        spinnerSelect= v.findViewById(R.id.spinnerChoice);
        ArrayAdapter<String> arrayAdapter= new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, classname);
        spinnerSelect.setAdapter(arrayAdapter);
        spinnerSelect.setVisibility(View.INVISIBLE);
        firstButtonsLayout= v.findViewById(R.id.linearLayoutFirstButtons);
        buttonCorrection= v.findViewById(R.id.buttonCorriger);
        buttonCorrection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstButtonsLayout.setVisibility(View.GONE);
                spinnerSelect.setVisibility(View.VISIBLE);
                buttonCorrectionValidation.setVisibility(View.VISIBLE);
            }
        });
        buttonValidation= v.findViewById(R.id.buttonValider);
        buttonCorrectionValidation= v.findViewById(R.id.buttonValidationCorrection);
        buttonCorrectionValidation.setVisibility(View.INVISIBLE);
        return v;
    }


    @Override
    public void OnResultImageSend(int indice) {
        principaleLayout.setVisibility(View.VISIBLE);
        textView.setText(classname[indice]);
        firstButtonsLayout.setVisibility(View.VISIBLE);
        spinnerSelect.setVisibility(View.INVISIBLE);
        buttonCorrectionValidation.setVisibility(View.INVISIBLE);
    }

    @Override
    public void OnSelectImage() {
        principaleLayout.setVisibility(View.INVISIBLE);
    }
}
