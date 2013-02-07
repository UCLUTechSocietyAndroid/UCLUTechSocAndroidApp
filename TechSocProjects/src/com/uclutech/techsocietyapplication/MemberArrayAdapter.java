package com.uclutech.techsocietyapplication;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.uclutech.techsocietyapplication.models.society.Member;

public class MemberArrayAdapter extends ArrayAdapter<Member> {

	private List<Member> mMembers;
	private Context mContext;
	private LayoutInflater inflater;

	public MemberArrayAdapter(Context context, int textViewResourceId,
			List<Member> members) {
		super(context, textViewResourceId);
		mMembers = members;
		mContext = context;
		
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		Member positionMember = mMembers.get(position);
		View itemView = inflater.inflate(R.layout.member_item, parent, false);
		
		TextView name = (TextView) itemView.findViewById(R.id.memberlist_text_name);
		name.setText(positionMember.getName());
		
		TextView joinDate = (TextView) itemView.findViewById(R.id.memberlist_text_joindate);
		return itemView;
	}

}
