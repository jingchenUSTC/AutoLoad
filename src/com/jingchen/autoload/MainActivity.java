package com.jingchen.autoload;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Toast;

import com.jingchen.autoload.PullableListView.OnLoadListener;

/**
 * 更多详解见博客http://blog.csdn.net/zhongkejingwang/article/details/38963177
 * @author chenjing
 *
 */
public class MainActivity extends Activity implements OnLoadListener
{

	private PullableListView listView;
	private MyAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listview);
		((PullToRefreshLayout) findViewById(R.id.refresh_view))
				.setOnRefreshListener(new MyListener());
		listView = (PullableListView) findViewById(R.id.content_view);
		initListView();
		listView.setOnLoadListener(this);
	}

	/**
	 * ListView初始化方法
	 */
	private void initListView()
	{
		List<String> items = new ArrayList<String>();
		for (int i = 0; i < 10; i++)
		{
			items.add("这里是item " + i);
		}
		adapter = new MyAdapter(this, items);
		listView.setAdapter(adapter);
		listView.setOnItemLongClickListener(new OnItemLongClickListener()
		{

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id)
			{
				Toast.makeText(
						MainActivity.this,
						"LongClick on "
								+ parent.getAdapter().getItemId(position),
						Toast.LENGTH_SHORT).show();
				return true;
			}
		});
		listView.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id)
			{
				Toast.makeText(MainActivity.this,
						" Click on " + parent.getAdapter().getItemId(position),
						Toast.LENGTH_SHORT).show();
			}
		});
	}

	@Override
	public void onLoad(final PullableListView pullableListView)
	{
		new Handler()
		{
			@Override
			public void handleMessage(Message msg)
			{
				for (int i = 0; i < 5; i++)
					adapter.addItem("这里是自动加载进来的item");
				// 千万别忘了告诉控件加载完毕了哦！
				pullableListView.finishLoading();
			}
		}.sendEmptyMessageDelayed(0, 5000);
	}

}
