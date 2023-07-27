package com.example.selfmadepizza;

import android.content.Intent;
import android.os.Bundle;
import android.app.FragmentManager;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class OutputFragment extends Fragment {
    TextView res;
    Button but;
    String order;
//    public OutputFragment(){
//        super(R.layout.fragment_output);
//    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_output, container, false);
        ImageButton but = (ImageButton) view.findViewById(R.id.imageButton3);
        EditText phone = (EditText) view.findViewById(R.id.editTextPhone);
        ImageButton closeFr = (ImageButton) view.findViewById(R.id.closeFragment);
        res = (TextView) view.findViewById(R.id.resultInFrame);
        res.setText(order);
        OutputFragment outputFragment = new OutputFragment();
        closeFr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra("result", "Дякуємо за замовлення!!\nМи зберегли ваше замовлення, очікуйте на дзвінок))");
                getActivity().getFragmentManager().popBackStack();
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });

        but.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if(String.valueOf(phone.getText()).length() == 0){
                   Toast.makeText(getActivity(), "Спочатку введіть номер", Toast.LENGTH_LONG ).show();
                   return;
               }
               if(String.valueOf(phone.getText()).length() < 13){
                   Toast.makeText(getActivity(), "Введіть номер до кінця", Toast.LENGTH_LONG ).show();
                   return;
               }
               if(String.valueOf(phone.getText()).length() > 13){
                   Toast.makeText(getActivity(), "Занадто довгий номер", Toast.LENGTH_LONG ).show();
                   return;
               }
               if(String.valueOf(phone.getText()).length() != 13){
                   Toast.makeText(getActivity(), "Неправильно введений номер", Toast.LENGTH_LONG ).show();
                   return;
               }

               if (!String.valueOf(phone.getText()).substring(0, 4).equals("+380")){
                    Toast.makeText(getActivity(), "Неправильно введений номер", Toast.LENGTH_LONG ).show();
                   return;
               }
               if(order.contains("+")){
                   order = order.replaceAll("[\\+][0-9]{12}", String.valueOf(phone.getText()));
               }
               else {
                   order += "\nВаш номер: " + phone.getText();

                   String order_in_one_string = order.replace('\n', '|');
                   Database.save(getActivity(), order_in_one_string);
               }
               res.setText(order);
               Toast.makeText(getActivity(), "Ми вам зателефонуємо протягом 1 хв\n Дякуємо за замовлення", Toast.LENGTH_LONG ).show();
           }
       });


        // Inflate the layout for this fragment
        return view;
    }



    public void orderView(String text){
        res.setText(text);
    }
}