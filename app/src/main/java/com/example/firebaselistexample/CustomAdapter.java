package com.example.firebaselistexample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.CustomViewHolder> {

    private ArrayList<User> arrayList;
    private Context context;    //선택한 엑티비티에 대한 context를 가져올 때 필요

    public CustomAdapter(ArrayList<User> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }


    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //실제 리스트 뷰가 어댑터에 연결된 다음에 이곳에서 뷰 홀더를 최초로 만들어 냄

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        //view 는 리사이클러뷰 한 컬럼을 만들 때에 대한 리스트 아이템을 이곳에서 선언함
        CustomViewHolder holder = new CustomViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        //각 아이템들에 대한 매칭을 실행 시켜줌. 텍스트 뷰는 문제가 없지만 이미지 뷰가 문제

        //ArrayList는 위에서 User 객체가 담긴 자바 파일이랑 연결해두었는데, 그 안의 getter setter를 통해
        //메인 엑티비티에서 파이어베이스를 통해 받아오면 User객체가 있는 ArrayList에 담아서 Adapter로 쏴준다
        //그러면 이 온바인드 뷰에서 받아서 글라이드로 로드를 하는 로직
        Glide.with(holder.itemView).load(arrayList.get(position).getProfile()).into(holder.iv_profile);

        //리스트 칼럼을 여러개 만들 것이기에 어레이 리스트를 다 담아서 객체 유저(위의 public CustomAdapter)에 담은 다음에 여기서 뿌려줌
        holder.tv_id.setText(arrayList.get(position).getId());
        holder.tv_pw.setText(String.valueOf(arrayList.get(position).getPw()));
        holder.tv_userName.setText(arrayList.get(position).getUserName());


    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size(): 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_profile;
        TextView tv_id, tv_pw, tv_userName;


        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.iv_profile = itemView.findViewById(R.id.iv_profile);
            this.tv_id = itemView.findViewById(R.id.tv_id);
            this.tv_pw = itemView.findViewById(R.id.tv_pw);
            this.tv_userName = itemView.findViewById(R.id.tv_userName);

        }
    }
}
