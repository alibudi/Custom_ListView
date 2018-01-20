package com.ali.custom_listview;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    ArrayList<HashMap<String, Object>> searchResult;

    ArrayList<HashMap<String, Object>>nama_makanan;

    LayoutInflater inflater;
    CustomAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText kotakpencarian = (EditText) findViewById(R.id.sbmakanan);
        ListView list_makanan = (ListView) findViewById(R.id.listmakanan);
        inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        String namamakanan[] = {"Sate Ayam","Bakso","Nasi Goreng","Risol"};
        String detailmakanan[] = {"Harga : 26.000","Harga : 10.000","Harga : 11.000","Harga : 2.000"};
        Integer[] gambar = {R.drawable.sate,R.drawable.pisa,R.drawable.nasgor,R.drawable.mie};

        nama_makanan = new ArrayList<HashMap<String, Object>>();

        HashMap<String, Object> temp;

        int nomor_makanan = namamakanan.length;

        for (int b=0;b<nomor_makanan;b++){
            temp = new HashMap<String, Object>();
                    temp.put("namamakanan",namamakanan[b]);
                    temp.put("detailmakanan",detailmakanan[b]);
                    temp.put("gambar",gambar[b]);

                    nama_makanan.add(temp);

        }
        searchResult = new ArrayList<HashMap<String, Object>>(nama_makanan);

        adapter= new CustomAdapter(MainActivity.this, R.layout.activity_daftar_makanan, searchResult);

        list_makanan.setAdapter(adapter);
        list_makanan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String str = searchResult.get(i).get("namamakanan").toString();
                switch (str){
                    case "Sate Ayam" :
                    Intent Sate = new Intent(MainActivity.this, Sate.class);
                    startActivity(Sate);
                    break;
                    case "Bakso" :
                        Intent Bakso = new Intent(MainActivity.this, Bakso.class);
                        startActivity(Bakso);
                        break;
                    case "Risol" :
                        Intent Risol = new Intent(MainActivity.this, Risol.class);
                        startActivity(Risol);
                        break;
                }
            }
        });
        kotakpencarian.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String searchString = kotakpencarian.getText().toString();
                int textLength = searchString.length();
                searchResult.clear();

                for (int b=0;b<nama_makanan.size();b++){
                    String hasil_namamakanan = nama_makanan.get(b).get("namamakanan").toString();

                    if (textLength<=hasil_namamakanan.length()){
                        if (searchString.equalsIgnoreCase(hasil_namamakanan.substring(0,textLength)))
                            searchResult.add(nama_makanan.get(b));
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable editable) {


            }
        });

    }

    private class CustomAdapter extends ArrayAdapter<HashMap<String, Object>> {

        public CustomAdapter(Context context, int textViewResource, ArrayList<HashMap<String, Object>> String) {
            super(context, textViewResource, String);
        }
        private class ViewHolder{
            ImageView gambar;
            TextView namamakanan, detailmakanan;
        }
        ViewHolder viewHolder;


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView==null){
                convertView = inflater.inflate(R.layout.activity_daftar_makanan,null);
                viewHolder = new ViewHolder();

                viewHolder.gambar = (ImageView) convertView.findViewById(R.id.gambar);
                viewHolder.namamakanan = (TextView) convertView.findViewById(R.id.namamakanan);
                viewHolder.detailmakanan = (TextView) convertView.findViewById(R.id.detailmakanan);

                convertView.setTag(viewHolder);

            }else

                viewHolder = (ViewHolder) convertView.getTag();
            int gambarId = (Integer) searchResult.get(position).get("gambar");

            viewHolder.gambar.setImageResource(gambarId);
            viewHolder.namamakanan.setText(searchResult.get(position).get("namamakanan").toString());
            viewHolder.detailmakanan.setText(searchResult.get(position).get("detailmakanan").toString());
            return convertView;
        }
    }

}