package com.example.androidteris;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.Bmob.PlayerData;
import com.example.Listenner.HttpListenner;
import com.example.androidteris.R;
import com.example.constant.Constant;
import com.example.httputil.CRecord;
import com.example.httputil.HttpUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.CountListener;
import cn.bmob.v3.listener.FindCallback;
import cn.bmob.v3.listener.FindListener;

public class CustomRecord extends BaseActivity{

	public ArrayList<PlayerData> cRecords = new ArrayList<PlayerData>();
	public ArrayList<PlayerData> cthreeRecords = new ArrayList<PlayerData>();
	public CRecord cRecordRank;
	
	public ListView recordList;
    public ViewPager viewpager;
	public View record;
	public View rank;
	public ArrayList<View> viewList; 
	
	public TextView userName;
	public TextView userRank;
	public TextView userScore;
	
	public TextView num1Name;
	public TextView num1Rank;
	public TextView num1Score;
	public TextView num2Name;
	public TextView num2Rank;
	public TextView num2Score;
	public TextView num3Name;
	public TextView num3Rank;
	public TextView num3Score;
	public Button cancel;
	public Button logout;
	
	Handler handle = new Handler(){
		
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			
			if(msg.what==1){
				recordList.setAdapter( new RecordList());
				
			}else if(msg.what==2){
				
				initRank();
			}else if(msg.what==3){
				
				initPersonRank();
			}
			
		}

		
	};
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.custom_info);
		init();
		
		setAdapter();
	
	}
	
	
	
	private void setAdapter() {
		// TODO Auto-generated method stub
		
		viewpager.setAdapter(new PagerAdapter() {
			
			 @Override
			public void destroyItem(ViewGroup container, int position,
					Object object) {
				// TODO Auto-generated method stub
					super.destroyItem(container, position, object);
					((ViewPager) container).removeView(viewList.get(position));
				
			}
			 
			 @Override
			public Object instantiateItem(ViewGroup container, int position) {
				// TODO Auto-generated method stub
				 
				 ((ViewPager) container).addView(viewList.get(position));
					return viewList.get(position);
			}
			 
			 
			 
			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				// TODO Auto-generated method stub
				return arg0==arg1;
			}
			
			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return viewList.size();
			}
		});
		
		
	}

	private void init() {
		// TODO Auto-generated method stub
		viewpager = (ViewPager) findViewById(R.id.viewpager);
		LayoutInflater LI = LayoutInflater.from(CustomRecord.this);
		record = LI.inflate(R.layout.custom_record, null);
		rank = LI.inflate(R.layout.custom_rank, null);
		
		recordList = (ListView) record.findViewById(R.id.customList);
		final String str = Constant.CustomName;
		BmobQuery<PlayerData> players = new BmobQuery<PlayerData>();
		players.addWhereEqualTo("name", str);
		players.order("-createdAt");
		players.findObjects(CustomRecord.this, new FindListener<PlayerData>() {
			
			@Override
			public void onSuccess(List<PlayerData> playerDatas) {
				
				cRecords = (ArrayList<PlayerData>) playerDatas;
				
				Message message = new Message();
				
				message.what =1;
				handle.sendMessage(message);
			}
			
			@Override
			public void onError(int arg0, String arg1) {
				// TODO Auto-generated method stub
				Toast.makeText(CustomRecord.this, "数据获取失败", Toast.LENGTH_SHORT).show();
			}
		});
		
		BmobQuery<PlayerData> players2 = new BmobQuery<PlayerData>();
		players2.addWhereEqualTo("name", str);
		players2.order("-score,-createdAt");
		players2.setLimit(1);
		players2.findObjects(CustomRecord.this, new FindListener<PlayerData>() {
			
			@Override
			public void onSuccess(List<PlayerData> arg0) {
				final Integer PMaxScore = arg0.get(0).getScore(); 
				final String PMaxTime = arg0.get(0).getCreatedAt();

				cRecordRank=new CRecord();
				cRecordRank.setCname(str);
				cRecordRank.setCscore(PMaxScore);
				cRecordRank.setDateTime(PMaxTime);
				
				BmobQuery<PlayerData> players3 = new BmobQuery<PlayerData>();
				players3.addWhereGreaterThanOrEqualTo("score", PMaxScore);
				players3.count(getApplicationContext(), PlayerData.class, new CountListener() {
					
					@Override
					public void onFailure(int arg0, String arg1) {
						// TODO Auto-generated method stub
						Toast.makeText(CustomRecord.this, "players3数据获取失败", Toast.LENGTH_SHORT).show();
					}
					
					@Override
					public void onSuccess(int count) {
						// TODO Auto-generated method stub
	
						
						cRecordRank.setRank(count);
						
						Message message = new Message();
						
						message.what =3;
						handle.sendMessage(message);

					}
				});
				
			}
			
			@Override
			public void onError(int arg0, String arg1) {
				Toast.makeText(CustomRecord.this, "数据获取失败1", Toast.LENGTH_SHORT).show();
				
			}
		});
		

		
		BmobQuery<PlayerData> playersRank = new BmobQuery<PlayerData>();
		playersRank.order("-score,createdAt");
		playersRank.setLimit(3);
		playersRank.findObjects(getApplicationContext(), new FindListener<PlayerData>() {
			
			@Override
			public void onSuccess(List<PlayerData> arg0) {
				// TODO Auto-generated method stub
				cthreeRecords = (ArrayList<PlayerData>) arg0;
				
				Message message = new Message();
				
				message.what =2;
				handle.sendMessage(message);
			}
			
			@Override
			public void onError(int arg0, String arg1) {
				// TODO Auto-generated method stub
				Toast.makeText(CustomRecord.this, "数据获取失败2", Toast.LENGTH_SHORT).show();
			}
		});
		
		
		
		viewList =new ArrayList<View>();
		viewList.add(rank);
		viewList.add(record);
	
		
		cancel  = (Button) rank.findViewById(R.id.cancel);
		logout  = (Button) rank.findViewById(R.id.logout);
		
		cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		logout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(CustomRecord.this,CustomLoginPage.class);
				startActivity(intent);
				finish();
			}
		});
		
		

	}

	

	class RecordList extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return cRecords.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return cRecords.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHandle	viewHandle;
			if(convertView==null){
				LayoutInflater inflate = LayoutInflater.from(CustomRecord.this);
				convertView = inflate.inflate(R.layout.record_part, null);
				viewHandle =new ViewHandle();
				viewHandle.time = (TextView) convertView.findViewById(R.id.rtime);
				viewHandle.score = (TextView) convertView.findViewById(R.id.rscore);
				convertView.setTag(viewHandle);
			}else{
				
				viewHandle=(ViewHandle) convertView.getTag();
			}
			
			viewHandle.time.setText(cRecords.get(position).getCreatedAt().toString());
			viewHandle.score.setText(cRecords.get(position).getScore().toString());
			
			
			
			return convertView;
		}
		
		
		
		
	}
	
	class ViewHandle{
		
	  public TextView time;
	  public TextView score;
		
	}
	
	
	private void initRank() {
		// TODO Auto-generated method stub
		
		
		num1Name = (TextView) rank.findViewById(R.id.num1name);
		num1Rank = (TextView) rank.findViewById(R.id.num1rank);
		num1Score = (TextView) rank.findViewById(R.id.num1score);
		
		num2Name = (TextView) rank.findViewById(R.id.num2name);
		num2Rank = (TextView) rank.findViewById(R.id.num2rank);
		num2Score = (TextView) rank.findViewById(R.id.num2score);
		
		num3Name = (TextView) rank.findViewById(R.id.num3name);
		num3Rank = (TextView) rank.findViewById(R.id.num3rank);
		num3Score = (TextView) rank.findViewById(R.id.num3score);
		
		
		
		
		num1Name.setText(cthreeRecords.get(0).getName());
		num1Rank.setText(1+"");
		num1Score.setText(cthreeRecords.get(0).getScore().toString());
		
		num2Name.setText(cthreeRecords.get(1).getName());
		num2Rank.setText(2+"");
		num2Score.setText(cthreeRecords.get(1).getScore().toString());
		
		num3Name.setText(cthreeRecords.get(2).getName());
		num3Rank.setText(3+"");
		num3Score.setText(cthreeRecords.get(2).getScore().toString());

	}
	
	public void initPersonRank(){
		userName = (TextView) rank.findViewById(R.id.username);
		userRank = (TextView) rank.findViewById(R.id.userrank);
		userScore = (TextView) rank.findViewById(R.id.userscore);
		
		userName.setText(cRecordRank.getCname().toString());
		userScore.setText(cRecordRank.getCscore().toString());
		userRank.setText(cRecordRank.getRank()+"");
	}
	
	
	
}
