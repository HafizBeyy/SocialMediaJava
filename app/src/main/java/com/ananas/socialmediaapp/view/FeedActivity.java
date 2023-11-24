package com.ananas.socialmediaapp.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.ananas.socialmediaapp.R;
import com.ananas.socialmediaapp.adapter.PostAdapter;
import com.ananas.socialmediaapp.databinding.ActivityFeedBinding;
import com.ananas.socialmediaapp.values.PostValues;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;

public class FeedActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private FirebaseFirestore firebaseFirestore;
    ArrayList<PostValues> postValuesArrayList;
    ActivityFeedBinding binding;
    PostAdapter postAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFeedBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view );
        auth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        postValuesArrayList = new ArrayList<>();
        getData();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        postAdapter=new PostAdapter(postValuesArrayList);
        binding.recyclerView.setAdapter (postAdapter);
    }

    public void getData() {
        // alternatif  DocumentReference documentReference = firebaseFirestore.collection("rendom") gereksiz eğer döküman adı otomatik ise .document("sadasdas");
        // alternatif  CollectionReference = firebaseFirestore.collection("rendom");
        firebaseFirestore.collection("Gönderiler").orderBy("date", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Toast.makeText(FeedActivity.this, error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
                if (value != null) {
                    for (DocumentSnapshot snapshot : value.getDocuments()) {
                        HashMap<String, Object> data = (HashMap<String, Object>) snapshot.getData();
                        String email =(String) data.get("email");
                        String comment= (String) data.get("comment");
                        String downloadURL= (String) data.get("downloadurl");
                        PostValues postValues = new PostValues(email,comment,downloadURL);
                        postValuesArrayList.add(postValues);

                    }
                    postAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.addnew) {
            // upload activity
            Intent upload = new Intent(FeedActivity.this, UploadActivity.class);
            startActivity(upload);
        } else if (item.getItemId() == R.id.logout) {
            auth.signOut();
            Intent mainactivity = new Intent(FeedActivity.this, MainActivity.class);
            startActivity(mainactivity);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}