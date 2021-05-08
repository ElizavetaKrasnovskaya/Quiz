package com.bsuir.quiz.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bsuir.quiz.R;
import com.bsuir.quiz.adapter.TopicAdapter;
import com.bsuir.quiz.model.Answer;
import com.bsuir.quiz.model.Question;
import com.bsuir.quiz.model.Score;
import com.bsuir.quiz.model.Topic;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TopicActivity extends AppCompatActivity {

    private Topic topic;
    private RecyclerView recyclerView;
    private List<Topic> topics;
    private TopicAdapter adapter;
    private static List<Score> topUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        recyclerView = findViewById(R.id.recycler_view);
        topics = new ArrayList<>();
        topUsers = new ArrayList<>();

        MobileAds.initialize(getApplicationContext());
        AdView adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        adView.loadAd(adRequest);


        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    topics.clear();
                    for (DataSnapshot sn : snapshot.child("Score").child("user").getChildren()) {
                        Score score = sn.getValue(Score.class);
                        topUsers.add(score);
                    }
                    for (DataSnapshot sn : snapshot.getChildren()) {
                        String name = sn.child("name").getValue(String.class);
                        String url = sn.child("url").getValue(String.class);
                        List<Question> questions = new ArrayList<>();
                        Question question;
                        for (DataSnapshot dataSnapshot : sn.child("question").getChildren()) {
                            question = dataSnapshot.getValue(Question.class);
                            List<Answer> answers = new ArrayList<>();
                            Answer answer;
                            for (DataSnapshot dsn : dataSnapshot.child("answer").getChildren()) {
                                answer = dsn.getValue(Answer.class);
                                answers.add(answer);
                            }
                            question.setAnswers(answers);
                            questions.add(question);
                        }
                        topic = new Topic(name, url, questions);
                        if (topic.getName() != null) {
                            topics.add(topic);
                        }
                    }
                    adapter = new TopicAdapter(topics, getApplicationContext());
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    recyclerView.setAdapter(adapter);
                    runLayoutAnimation(recyclerView);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };
        reference.addValueEventListener(eventListener);

    }

    private void runLayoutAnimation(RecyclerView recyclerView) {
        Context context = recyclerView.getContext();
        LayoutAnimationController layoutAnimationController =
                AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_slide_right);
        recyclerView.setLayoutAnimation(layoutAnimationController);
        recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();
    }

    public static List<Score> getTopUsers() {
        return topUsers;
    }

    public static void setTopUsers(List<Score> topUsers) {
        TopicActivity.topUsers = topUsers;
    }
}