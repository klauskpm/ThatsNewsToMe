package br.com.klauskpm.thatsnewstome.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.klauskpm.thatsnewstome.News;
import br.com.klauskpm.thatsnewstome.R;

/**
 * Created by klaus on 26/10/16.
 */
public class NewsAdapter extends ArrayAdapter<News> {
    /**
     * Instantiates a new News adapter.
     *
     * @param context the context
     * @param objects the objects
     */
    public NewsAdapter(Context context, ArrayList<News> objects) {
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        final News news = getItem(position);
        assert news != null;

        if (convertView == null) {
            holder = new ViewHolder();

            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.news_item, parent, false);

            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.section = (TextView) convertView.findViewById(R.id.section);
            holder.item = (LinearLayout) convertView.findViewById(R.id.item);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.title.setText(news.getTitle());
        holder.section.setText(news.getSection());

        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(news.getURL()));
            getContext().startActivity(intent);
            }
        });

        return convertView;
    }

    private class ViewHolder {
        LinearLayout item;
        /**
         * The Title.
         */
        TextView title;
        /**
         * The Section.
         */
        TextView section;
    }
}
