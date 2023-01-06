package com.example.audiotest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class TabFragment2 extends Fragment {

    ListView listView;
    TextView textView;
    String[] listItem;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab_fragment_2, container, false);

        listView = rootView.findViewById(R.id.listView);
        //textView = rootView.findViewById(R.id.textView);
        listItem = getResources().getStringArray(R.array.array_technology);
        //ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, listItem);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.mylist, listItem);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // TODO Auto-generated method stub
                String value = adapter.getItem(position);
                Toast.makeText(getActivity().getApplicationContext(),value,Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }
}
