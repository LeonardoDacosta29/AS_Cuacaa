package com.leonardoacosta202102275.cuacaa;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CuacaAdapter extends RecyclerView.Adapter<CuacaViewHolder> {

    private List<ListModel> listModelList;
    private RootModel namaVariabel_rootModel;

    public CuacaAdapter(RootModel namaVariabel_rootModel) {
        this.namaVariabel_rootModel = namaVariabel_rootModel;

        listModelList = namaVariabel_rootModel.getListModelList();
    }

    @NonNull
    @Override
    public CuacaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater namaVariabel_layoutInflater = LayoutInflater.from(parent.getContext());
        View namaVariabel_view = namaVariabel_layoutInflater.inflate(R.layout.list_cuaca, parent, false);
        return new CuacaViewHolder(namaVariabel_view);
    }

    private double toCelcius(double kelvin) {
        return kelvin = 272.15;
    }
    public String formatNumber(double number, String format) {
        DecimalFormat namaVariabel_decimalFormat = new DecimalFormat(format);
        return namaVariabel_decimalFormat.format(number);
    }

    @Override
    public void onBindViewHolder(@NonNull CuacaViewHolder holder, int position) {
        ListModel namaVariabel_listModel = listModelList.get(position);
        WeatherModel namaVariabel_weatherModel = namaVariabel_listModel.getWeatherModelList().get(0); // dibuat seperti ini karena berbentuk array dan knp get(0) karena data di weather nya dari link berisi 1 data.
        MainModel namaVariabel_mainModel = namaVariabel_listModel.getMainModel();

        //Simbol derajat ° bisa didapat dari app character map
        String suhu = formatNumber(toCelcius(namaVariabel_mainModel.getTemp_min()), "###.##") + "°C - " +
                formatNumber(toCelcius(namaVariabel_mainModel.getTemp_max()), "###.##") + "°C";

        switch (namaVariabel_weatherModel.getIcon()) {
            case "01d":
                holder.cuacaImageView.setImageResource(R.mipmap.ic_01d);
                break;
            case "01n":
                holder.cuacaImageView.setImageResource(R.mipmap.ic_01n);
                break;
            case "02d":
                holder.cuacaImageView.setImageResource(R.mipmap.ic_02d);
                break;
            case "02n":
                holder.cuacaImageView.setImageResource(R.mipmap.ic_02n);
                break;
            case "03d":
                holder.cuacaImageView.setImageResource(R.mipmap.ic_03d);
                break;
            case "03n":
                holder.cuacaImageView.setImageResource(R.mipmap.ic_03n);
                break;
            case "04d":
                holder.cuacaImageView.setImageResource(R.mipmap.ic_04d);
                break;
            case "04n":
                holder.cuacaImageView.setImageResource(R.mipmap.ic_04n);
                break;
            case "09d":
                holder.cuacaImageView.setImageResource(R.mipmap.ic_09d);
                break;
            case "09n":
                holder.cuacaImageView.setImageResource(R.mipmap.ic_09n);
                break;
            case "10d":
                holder.cuacaImageView.setImageResource(R.mipmap.ic_10d);
                break;
            case "10n":
                holder.cuacaImageView.setImageResource(R.mipmap.ic_10n);
                break;
            case "11d":
                holder.cuacaImageView.setImageResource(R.mipmap.ic_11d);
                break;
            case "11n":
                holder.cuacaImageView.setImageResource(R.mipmap.ic_11n);
                break;
        }
        String tanggalWaktuWib = formatWib(namaVariabel_listModel.getDt_txt());

        holder.namaTextView.setText(namaVariabel_weatherModel.getMain());
        holder.deskripsiTextView.setText(namaVariabel_weatherModel.getDescription());
        holder.tglWaktuTextView.setText(tanggalWaktuWib);
        holder.suhuTextView.setText(suhu);
    }

    private String formatWib(String tanggalWaktuGmt_string) {
        Log.d("*tw*", "Waktu GMT : " + tanggalWaktuGmt_string);

        Date tanggalWaktuGmt = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); //harus sesuai dengan data yang ada di link atau json

        try {
            tanggalWaktuGmt = sdf.parse(tanggalWaktuGmt_string);
        } catch (ParseException e) {
            Log.d("*tw*", e.getMessage());
        }

        // Calender diisini berguna untuk dari data Gmt terus ditambah 7 jam dengan fungsinya di baris ke 125 yaitu HOUR_OF_DAY
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(tanggalWaktuGmt);
        calendar.add(Calendar.HOUR_OF_DAY,7);

        Date tanggalWaktuWib = calendar.getTime();

        String tanggalWaktuWib_string = sdf.format(tanggalWaktuWib);
        tanggalWaktuWib_string = tanggalWaktuWib_string.replace("00.00", "00 WIB"); //Berguna untuk menhapus detik dari data nya

        //Fungsi *tw* berguna untuk memfilter data di Logcat setelah di log.d, *tw* juga bisa di ganti dengan inisial sendiri
        Log.d("*tw*", "Tanggal Waktu Indonesia Barat : " + tanggalWaktuWib_string);
        return tanggalWaktuWib_string;
    }

    @Override
    public int getItemCount() {
        return (listModelList != null) ? listModelList.size() : 0;
    }
}
