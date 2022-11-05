package com.instanceit.alhadafpos.Helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.instanceit.alhadafpos.Entity.Model;
import com.instanceit.alhadafpos.R;

import java.util.ArrayList;

public class SearchableSpinnerAdapter extends RecyclerView.Adapter<SearchableSpinnerAdapter.DataObjectHolder> implements Filterable {

    Context context;
    private ArrayList<Model> itemArrayList;
    private ArrayList<Model> itemArrayList_filter;
    SearchableSpinnerDialog hsncodeSpinnerDialog;
    String str_edittextdata;

    public SearchableSpinnerAdapter(Context context, ArrayList<Model> itemArrayList, SearchableSpinnerDialog hsncodeSpinnerDialog) {
        this.itemArrayList = itemArrayList;
        this.itemArrayList_filter = itemArrayList;
        this.context = context;
        this.hsncodeSpinnerDialog = hsncodeSpinnerDialog;
        setHasStableIds(true);
    }


    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.spinner_items_view, parent, false);
        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }


    @Override
    public void onBindViewHolder(final DataObjectHolder holder, final int position) {

        holder.text1.setText(itemArrayList.get(holder.getAdapterPosition()).getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Model model = itemArrayList.get(holder.getAdapterPosition());
                hsncodeSpinnerDialog.onSpinerItemClick.onClick(model, holder.getAdapterPosition());
                hsncodeSpinnerDialog.dialogShownOnce = false;
                hsncodeSpinnerDialog.searchBox.setText("");
                hsncodeSpinnerDialog.alertDialog.dismiss();
            }
        });

    }


    public void clear() {
        itemArrayList.clear();
    }


    @Override
    public int getItemCount() {
        return itemArrayList.size();
    }


    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }


    public static class DataObjectHolder extends RecyclerView.ViewHolder {

        TextView text1;


        public DataObjectHolder(View itemView) {
            super(itemView);

            text1 = itemView.findViewById(R.id.text1);
        }

    }

    @Override
    public android.widget.Filter getFilter() {
        return new android.widget.Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                final String charString = charSequence.toString();

                if (charString.isEmpty()) {
                    itemArrayList = itemArrayList_filter;
                } else {
                    ArrayList<Model> filteredList = new ArrayList<>();

                    for (Model row : itemArrayList_filter) {
                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    itemArrayList = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = itemArrayList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                itemArrayList = (ArrayList<Model>) filterResults.values;
                if (itemArrayList.size() <= 0) {
                    Toast.makeText(context, "No data found", Toast.LENGTH_SHORT).show();
                }
                notifyDataSetChanged();
            }
        };
    }
}
