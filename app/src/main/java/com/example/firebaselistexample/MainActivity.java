package com.example.firebaselistexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    //이제 어댑터를 여기랑 연결만 해주면 됨
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;   //리사이클러뷰는 layoutManager도 연결을 해주어야함

    private ArrayList<User> arrayList;  //CustomAdapter에도 이 작업을 해주었는데, 어댑터랑 메인에서 어레이 리스트로 데이터를 주고 받을 것이기 때문에 사용

    //파이어베이스와 db 관련 구문 전역변수로 설정
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView); //아이디 연결
        recyclerView.setHasFixedSize(true);     //리사이클러뷰 기존 성능을 강화하기 위한 구문
        layoutManager = new LinearLayoutManager(this);  //context를 자동으로 입력해줌
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>();  //User 객체를 담을 어레이 리스트(CustomerAdapter 쪽으로 데이터를 넘기고 받음)

        database = FirebaseDatabase.getInstance();  //파이어베이스 데이터베이스 연동
        databaseReference = database.getReference("User");  //DB 테이블을 연결하는 작업
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //실제적으로 파이어베이스 데이터베이스의 데이터를 받아오는 곳
                arrayList.clear();  //기존 배열 리스트가 존재할 수 있으므로 초기화 해주기
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    //반복문으로 데이터 리스트를 추출하기
                    User user = snapshot.getValue(User.class);  //User class 안에 파이어베이스 DB로 부터 받아온 것을 넣고 그것을 arrayList에 데이터를 담아서 adapter에 보내줌
                    arrayList.add(user);    //담은 데이터들을 배열 리스트에 넣고 리사이클러 뷰로 보낼 준비 끝
                }
                adapter.notifyDataSetChanged(); //리스트 저장 및 새로고침 (수정이 일어나게 되면 adapter쪽에 새로고침을 해주어야 반영이 됨)
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // 디비에서 데이터를 가져오던 중 에러 발생 시 띄워줄 에러들
                Log.e("MainActivity", String.valueOf(databaseError.toException())); //에러가 나면 이 구문 출력해줌.
            }
        });

        adapter = new CustomAdapter(arrayList, this);   //this 자체가 엑티비티 온 크레이트 에서는 context가 해당 됨.
        //arrayList와 this를 넘기는 이유는, customAdapter에 우리가 만들어 놓은 CustomAdapter는 두가지 인자를 받는다.
        recyclerView.setAdapter(adapter);   //리사이클러뷰에 어댑터 연결


    }
}
