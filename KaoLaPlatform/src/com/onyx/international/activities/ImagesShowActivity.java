package com.onyx.international.activities;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

import com.example.android.bitmapfun.util.ImageFetcher;
import com.onyx.international.kaolaplatform.R;
import com.onyx.international.models.Gift;
import com.onyx.international.models.GiftModel;

public class ImagesShowActivity extends BaseActivity {

	private ImageFetcher mImageFetcher;
	private Gallery mGallery;
	private Gift mGift;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_images_show);
		
		mGift = GiftModel.sharedModel().getGift(GiftModel.sharedModel().mCurrentSelectGiftId);
		mGallery = (Gallery) findViewById(R.id.gallery_images_show);
		setTitle(mGift.name);
		
		setTopBarLeftButtonTypeBack();
		
		mGallery = (Gallery) findViewById(R.id.gallery_images_show);
		mGallery.setAdapter(new ImageAdapter());
		mImageFetcher = new ImageFetcher(this, 600);
	}

	class ImageAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mGift.imageArray.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int index, View containView, ViewGroup arg2) {
			// TODO Auto-generated method stub
			
			if (containView == null) {
				containView = new ImageView(ImagesShowActivity.this);
			} else {
				
			}
			
			mImageFetcher.loadImage(mGift.imageArray.get(index), (ImageView) containView);
			
			return containView;
		}
		
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
        mImageFetcher.setExitTasksEarly(false);
	}
}
