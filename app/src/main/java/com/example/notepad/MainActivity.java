package com.example.notepad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ImageView profileImageId;
    private RecyclerView recyclerViewId;
    private FloatingActionButton addNoteFloatActionBtn;
    private FirebaseDatabase firebaseDatabase;
    private List<User> noteList;
    private CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialization();
        onClick();
        showToDB();
        initRecyclerView();

    }

    private void initialization() {
        noteList = new ArrayList<>();
        firebaseDatabase = FirebaseDatabase.getInstance();
        profileImageId = findViewById(R.id.profileImageId);
        recyclerViewId= findViewById(R.id.recyclerViewId);
        addNoteFloatActionBtn = findViewById(R.id.addNoteFloatActionBtn);
    }

    private void onClick() {
        addNoteFloatActionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(MainActivity.this);
                bottomSheetDialog.setContentView(R.layout.notes);
                bottomSheetDialog.setCanceledOnTouchOutside(false);
                final EditText et_noteTitle,et_noteDetails;
                Button save;

                et_noteTitle = bottomSheetDialog.findViewById(R.id.et_noteTitle);
                et_noteDetails = bottomSheetDialog.findViewById(R.id.et_noteDetails);
                save = bottomSheetDialog.findViewById(R.id.saveBtn);
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String title = et_noteTitle.getText().toString();
                        String details = et_noteDetails.getText().toString();
                        saveToDB(new User(title,details));
                    }
                });
                bottomSheetDialog.show();
            }
        });
    }

    private void saveToDB(User user) {
        DatabaseReference userDB = firebaseDatabase.getReference().child("noteList");
        String userid = userDB.push().getKey();
        user.setUserId(userid);
        userDB.child(userid).setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(MainActivity.this, "Successfully saved", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initRecyclerView() {
        recyclerViewId.setLayoutManager(new LinearLayoutManager(this));
        customAdapter = new CustomAdapter(noteList,this);
        recyclerViewId.setAdapter(customAdapter);
    }

    private void showToDB() {
        DatabaseReference userDB = firebaseDatabase.getReference().child("noteList");
        userDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    noteList.clear();
                    for (DataSnapshot data: dataSnapshot.getChildren()){
                        User user = data.getValue(User.class);
                        noteList.add(user);
                        customAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
