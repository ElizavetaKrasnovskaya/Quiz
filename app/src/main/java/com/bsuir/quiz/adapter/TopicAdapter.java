package com.bsuir.quiz.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bsuir.quiz.R;
import com.bsuir.quiz.model.Topic;
import com.bsuir.quiz.ui.QuestionActivity;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.TopicViewHolder> {

    Context context;
    private final List<Topic> topics;
    private static Topic selectedTopic;

    public TopicAdapter(List<Topic> topics, Context context) {
        this.topics = topics;
        this.context = context;
    }

    @NonNull
    @Override
    public TopicViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.layout_item, parent, false);
        return new TopicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopicViewHolder holder, int position) {
        Topic topic = topics.get(position);
        holder.topic.setText(topic.getName());
        new LoadImage(holder.image).execute(topic.getUrl());
        holder.cardView.setOnClickListener(v -> {
            selectedTopic = topic;
            showAlertDialog(v);
        });
    }

    private void showAlertDialog(View v) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(v.getContext());
        alertDialog.setTitle("Enter amount of questions:");
        String[] items = {"5", "10", "15"};
        int checkedItem = 1;
        final Intent[] intent = new Intent[1];
        alertDialog.setSingleChoiceItems(items, checkedItem, (dialog, which) -> {
            switch (which) {
                case 0:
                    QuestionActivity.setAmountOfQuestions(5);
                    intent[0] = new Intent(context, QuestionActivity.class);
                    intent[0].addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent[0]);
                    break;
                case 1:
                    QuestionActivity.setAmountOfQuestions(10);
                    intent[0] = new Intent(context, QuestionActivity.class);
                    intent[0].addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent[0]);
                    break;
                case 2:
                    QuestionActivity.setAmountOfQuestions(15);
                    intent[0] = new Intent(context, QuestionActivity.class);
                    intent[0].addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent[0]);
                    break;
            }
        });
        AlertDialog alert = alertDialog.create();
        alert.setCanceledOnTouchOutside(false);
        alert.show();
    }

    @Override
    public int getItemCount() {
        return topics.size();
    }

    static class TopicViewHolder extends RecyclerView.ViewHolder {
        public TextView topic;
        public ImageView image;
        public CardView cardView;

        public TopicViewHolder(@NonNull View view) {
            super(view);
            topic = view.findViewById(R.id.topic);
            image = view.findViewById(R.id.topic_image);
            cardView = view.findViewById(R.id.card_view);
        }
    }

    static class LoadImage extends AsyncTask<String, Void, Bitmap> {

        private final WeakReference<ImageView> imageViewReference;

        public LoadImage(ImageView imageView) {
            imageViewReference = new WeakReference<>(imageView);
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            try {
                return downloadBitmap(params[0]);
            } catch (Exception e) {
                Log.d("error", e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (isCancelled()) {
                bitmap = null;
            }

            if (imageViewReference != null) {
                ImageView imageView = imageViewReference.get();
                if (imageView != null) {
                    if (bitmap != null) {
                        imageView.setImageBitmap(bitmap);
                    }
                }
            }
        }

        private Bitmap downloadBitmap(String url) {
            HttpURLConnection urlConnection = null;
            try {
                URL uri = new URL(url);
                urlConnection = (HttpURLConnection) uri.openConnection();

                InputStream inputStream = urlConnection.getInputStream();
                if (inputStream != null) {
                    return BitmapFactory.decodeStream(inputStream);
                }
            } catch (Exception e) {
                urlConnection.disconnect();
                Log.w("ImageDownloader", "Error downloading image from " + url);
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
            return null;
        }
    }

    public static Topic getSelectedTopic() {
        return selectedTopic;
    }
}
