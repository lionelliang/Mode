package com.voltaire.mode;

import java.util.LinkedList;
import java.util.List;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.voltaire.mode.dummy.DummyContent;
import com.voltaire.mode.dummy.MusicManager;

/**
 * A fragment representing a single Frustrated detail screen. This fragment is
 * either contained in a {@link FrustratedListActivity} in two-pane mode (on
 * tablets) or a {@link FrustratedDetailActivity} on handsets.
 */
public class FrustratedDetailFragment extends Fragment {
	/**
	 * The fragment argument representing the item ID that this fragment
	 * represents.
	 */
	public static final String ARG_ITEM_ID = "item_id";

	/**
	 * The dummy content this fragment is presenting.
	 */
	private DummyContent.DummyItem mItem;

	private View rootView;
	private TextView tv;
	private ImageView imgv;
	private List<Integer> imgList = new LinkedList<Integer>();
	private int i = 0;
	private boolean continueMusic;
	private MediaPlayer player;
	
	private OnTouchListener imgvTouchListener = new OnTouchListener() {

		@Override
		public boolean onTouch(View arg0, MotionEvent event) {
			// TODO Auto-generated method stub
			Log.i(getTag(), "onTouchListener works");
			return gdImgv.onTouchEvent(event);
		}
	};

	@SuppressWarnings("deprecation")
	GestureDetector gdImgv = new GestureDetector(new OnGestureListener() {

		@Override
		public boolean onDown(MotionEvent arg0) {
			// TODO Auto-generated method stub
			return true;
		}

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			// TODO Auto-generated method stub
			Log.i(getTag(), "chang photos");
			if (e1.getX() - e2.getX() > 50) {
				if (i < imgList.size() - 1) {
					i++;
					if (imgv != null) {
						if (imgv.getDrawable() != null) {
							((BitmapDrawable) imgv.getDrawable()).getBitmap()
									.recycle();
						}
						imgv.setImageBitmap(decodeSampledBitmapFromResource(
								getResources(), imgList.get(i), 480, 800));
					}
				}
				return true;
			} else if (e1.getX() - e2.getX() < -50) {
				if (i > 0) {
					i--;
					if (imgv != null) {
						if (imgv.getDrawable() != null) {
							((BitmapDrawable) imgv.getDrawable()).getBitmap()
									.recycle();
						}
						imgv.setImageBitmap(decodeSampledBitmapFromResource(
								getResources(), imgList.get(i), 480, 800));
					}
					return true;
				}
			}
			return false;
		}

		@Override
		public void onLongPress(MotionEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {
			// TODO Auto-generated method stub
			return true;
		}

		@Override
		public void onShowPress(MotionEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public boolean onSingleTapUp(MotionEvent e) {
			// TODO Auto-generated method stub
			return true;
		}

	});

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public FrustratedDetailFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getArguments().containsKey(ARG_ITEM_ID)) {
			// Load the dummy content specified by the fragment
			// arguments. In a real-world scenario, use a Loader
			// to load content from a content provider.
			mItem = DummyContent.ITEM_MAP.get(getArguments().getString(
					ARG_ITEM_ID));
			player = MediaPlayer.create(getActivity(), R.raw.smalltimes);
			Log.i(getTag(), "creat music player");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragment_frustrated_detail,
				container, false);
		// rootView.setBackgroundColor(getResources().getColor(R.color.corBackgroud));

		// Show the dummy content as text in a TextView.
		if (mItem != null) {
			tv = (TextView) rootView.findViewById(R.id.frustrated_detail);
			imgv = (ImageView) rootView.findViewById(R.id.frustrated_image);
			if (imgv.getDrawable() != null) {
				((BitmapDrawable) imgv.getDrawable()).getBitmap().recycle();
			}
			imgList.clear();

			tv.setText(mItem.content);
			if (mItem.content == "Introduction") {
				tv.setText("This is a 17, rue Voltaire application, best wishes to them!");
				imgList.add(R.drawable.allofus);
			} else if (mItem.content == "GUO Fuliang") {
				tv.setText(mItem.content);
				imgList.add(R.drawable.flguostandup);
				imgList.add(R.drawable.flguo2);
			} else if (mItem.content == "ZHANG Yan") {
				tv.setText(mItem.content);
				imgList.add(R.drawable.yzhan1);
				imgList.add(R.drawable.yzhang2);
				imgList.add(R.drawable.yzhang3);
			} else if (mItem.content == "Autres") {
				tv.setText(mItem.content);
			}
			if (!imgList.isEmpty()) {
				if (imgv.getDrawable() != null) {
					((BitmapDrawable) imgv.getDrawable()).getBitmap().recycle();
				}
				Log.i(getTag(), "Load picture");
				imgv.setImageBitmap(decodeSampledBitmapFromResource(
						getResources(), imgList.get(0), 480, 800));
				// imgv.setImageResource(imgList.get(0));
			}
			// imgv.setOnClickListener(imgvTouchListener);
			imgv.setOnTouchListener(this.imgvTouchListener);
		}
		 
		return rootView;
	}

	@Override
	public void onPause() {
		super.onPause();
		if (!continueMusic) {
			MusicManager.pause();
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		continueMusic = false;
		player.start();
		Log.i(getTag(), "start to play music");
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		player.stop();
		player.release();
	}
	/**
	 * mImageView.setImageBitmap(
	 * decodeSampledBitmapFromResource(getResources(), R.id.myimage, 100, 100));
	 * 
	 * @param res
	 * @param resId
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 */
	public static Bitmap decodeSampledBitmapFromResource(Resources res,
			int resId, int reqWidth, int reqHeight) {

		// First decode with inJustDecodeBounds=true to check dimensions
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeResource(res, resId, options);

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, reqWidth,
				reqHeight);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeResource(res, resId, options);
	}

	/**
	 * get smaller scanling bitmap
	 * 
	 * @param options
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 */
	public static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {

			final int halfHeight = height / 2;
			final int halfWidth = width / 2;

			// Calculate the largest inSampleSize value that is a power of 2 and
			// keeps both
			// height and width larger than the requested height and width.
			while ((halfHeight / inSampleSize) > reqHeight
					&& (halfWidth / inSampleSize) > reqWidth) {
				inSampleSize *= 2;
			}
		}

		return inSampleSize;
	}
}
