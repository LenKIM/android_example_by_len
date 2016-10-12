Android RecyclerView Tutorial

# RecyclerView
 튜트리얼 시작을 위한 기초 프로젝트
 -> https://koenig-media.raywenderlich.com/uploads/2016/05/Galacticon-Starter.zip

## 과정

1. NASA로 부터 Keys를 받아와서 RecyclerView로 표현하기.
 - [api.nasa.gov](api.nasa.gov) API Key 받아오기
 - 받아온 API Key를 String.xml file안에 있는 INSERT API KEY HERE에 삽입

2. RecyclerView란?
 A RecyclerView 는 ListView와 GridView의 조합이라고 생각 할수 있다.
그러나, memory-effcient design patters라고도 할 수 있을 만큼 추가적인 특징을 가지고 있다.
 자, 이제 좀더 상세하게 RecyclerView의 특징을 살펴보자.
만약, ListView와 많이 복잡한 custom Item을 만든다고 가정할때, getView() 메소드 안에 너의 새로운 아이템을 inflate할것이고, 다음에 너는 모든 View를 Unique ID(findViewID)를 통해 View logic를 완성 시킬꺼야. 일단 그게 끝난다면, 너는 그 View들을 Listview에 보낼테고, 이제 스크린으로 보낼준비가 다 된거지? 모든게 ok....?

 여기서 주의해야 될 것은 ListView and GridViews only do half the job of achiving true memory-effcient. ListView와 GridView들은 아이템 레이아웃을 재활용하지만 그걸 layout children에게 참고 시키지는 않아, 너는 분명 모든 findViewById()로 다시 호출해야 될거야 너가 getView()를 매시킬 호출 시킬 때마다.

 이런 절차는 매우 복잡한 레이아웃을 만들어야하고 더구나, 이런건 jerky 또는 non-resposive하게 만들꺼야,
 frantically - 미친 듯이, 극도로 흥분하다.

 이러한 복잡함을 해결하기 위해서 Android Developer는 ViewHolder라는 Pattern 통해 smooth scrolling로 문제를 해결했어.

 언제든 네가 ViewHolder패턴을 사용하면, you create a class that becomes an in-memory reference to all the views needed to fill your layout. The benefit is you set the references once and reuse them, effectively working around the performance hit that comes with repeatedly calling findViewById().

 또한 RecyclerView의 또다른 큰 특징은 내가 원하는 animation 과 적용을 필요에 따라 적용할수 있다는 라는 특징이 있다.

  RecyclerView의 흥미로운 component는 바로 LayoutManager라는 점이다.
이는 RecyclerView의 아이템에 위치해 있으며, 이는 items를 재활용할때 사용됨.

###LayoutManager 의 종류
 - LinearLayoutManager -> positions your items to look like a standard ListView

 - GridLayoutManager -> positions your items in a grid format similar to a GridView

 - StaggerGridLayoutManager -> positions your items in a staggered grid format

3. 사용법
 일단 RecyclerView를 이용하기 위해서는 dependency(build.gradle)에 라이브러리를 호출해야한다.

 그리고 XML에는
 	<android.support.v7.widget.RecyclerView
  android:id="@+id/recyclerView"
  android:layout_width="match_parent"
  android:layout_height="match_parent"
  android:scrollbars="vertical"/>
 이런 식으로 작성.

 3-1 선언
MainActivity.java에

private RecyclerView mRecyclerView;
private LinearLayoutManager mLinearLayoutManager;

 => variables 선언
In onCreate(), add the following lines

mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
mLinearLayoutManager = new LinearLayoutManager(this);
mRecyclerView.setLayoutManager(mLinearLayoutManager);

 3-2 ListView처럼 RecyclerView Items layout 만들기.

 recyclerview_item_row.xml

<ImageView
  android:id="@+id/item_image"
  android:layout_width="wrap_content"
  android:layout_height="wrap_content"
  android:layout_gravity="center"
  android:layout_marginTop="8dp"
  android:adjustViewBounds="true"
  android:layout_weight="3"/>
<TextView
  android:id="@+id/item_date"
  android:layout_width="wrap_content"
  android:layout_height="wrap_content"
  android:layout_marginTop="8dp"
  android:layout_gravity="top|start"
  android:layout_weight="1"
  android:text="Some date"/>
<TextView
  android:id="@+id/item_description"
  android:layout_width="wrap_content"
  android:layout_height="wrap_content"
  android:layout_gravity="center|start"
  android:layout_weight="1"
  android:ellipsize="end"
  android:maxLines="5"/>

 3-3 새로운 RecyclerView.Adapter class를 만들고.
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.PhotoHolder> {
}
 그 다음에 필요한 Implement methods를 만들어랏!

아래 3가지.
public RecyclerAdapter.PhotoHolder onCreateViewHolder(ViewGroup parent, int viewType)
public void onBindViewHolder(RecyclerAdapter.PhotoHolder holder, int position)
public int getItemCount()
호출.

다음에 사진 dataset을 위한 Private ArrayList<Photo> mPhotos; 선언하고
 - 여기서 Photo는 Model로 이미 튜트리얼에 만들어져 있음.
다음, 생성자(constructor) 만들고
	public RecyclerAdapter(ArrayList<Photo> photos) {
	  mPhotos = photos;
	}
다음, getItemCount() 에서 Return 값으로 mphotos.size()를 add.

다음, 뷰를 참조하기 위한 PhotoHolder를 static inner class로 adapter안에 create 한다. 왜 static inner class 하는 이유는 adapter와 긴밀한 연결을 하기 때문이다.

 아래 inner class 의 내용을 아래와 같다.

//1
public static class PhotoHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
  //2
  private ImageView mItemImage;
  private TextView mItemDate;
  private TextView mItemDescription;
  private Photo mPhoto;

  //3
  private static final String PHOTO_KEY = "PHOTO";

  //4
  public PhotoHolder(View v) {
    super(v);

    mItemImage = (ImageView) v.findViewById(R.id.item_image);
    mItemDate = (TextView) v.findViewById(R.id.item_date);
    mItemDescription = (TextView) v.findViewById(R.id.item_description);
    v.setOnClickListener(this);
  }

  //5
  @Override
  public void onClick(View v) {
    Log.d("RecyclerView", "CLICK!");
  }
}

 1. 첫번째로 RecyclerView.ViewHolder를 상속받고, 이는 Adapter를 위한 ViewHolder 사용이 허락되는 것이다.

 2. 참조하는 리스트를 더한다.

 3. 너의 RecyclerView사용에 쓰이기 위한 특별한 아이템의 쉬운 참조를 위한 키를 더한다.

 4.  그리고 생성자를 set up

 5. View.OnClickListener를 위한 메소드가 실행되었다. 이것은 ViewHolder가 책임지고 handling합니다.


4. onCreateViewHolder()  리턴값으로 아래와 같이 적는다.
	View inflatedView = LayoutInflater.from(parent.getContext())
	    .inflate(R.layout.recyclerview_item_row, parent, false);
	return new PhotoHolder(inflatedView);

5. ViewHolder's onClick에 이러한 코드 작성

Context context = itemView.getContext();
Intent showPhotoIntent = new Intent(context, PhotoActivity.class);
showPhotoIntent.putExtra(PHOTO_KEY, mPhoto);
context.startActivity(showPhotoIntent);

 여기서 주의할점은 어떤 Context를 Grabs하고 있는가? 봐야한다.

 6. PhotoHolder()안에다가

public void bindPhoto(Photo photo) {
  mPhoto = photo;
  Picasso.with(mItemImage.getContext()).load(photo.getUrl()).into(mItemImage);
  mItemDate.setText(photo.getHumanDate());
  mItemDescription.setText(photo.getExplanation());
}

 이는 Picasso 라이버리를 활용하여 간단하게 URL를통해 이미지를 받아오는 것이다.

 7. onBindViewHolder() 에

	Photo itemPhoto = mPhotos.get(position);
	holder.bindPhoto(itemPhoto);

 작성.

 8. 다시 MainActivity.java로 돌아와서

 private RecyclerAdapter mAdapter;

adapter를 추가하고.

mAdapter = new RecyclerAdapter(mPhotosList);
mRecyclerView.setAdapter(mAdapter);

 onCreate()안에 어탭터를 껴주고.

 onStart()안에 사진을 불러옴 아래는 onStart()안에 들어갈 코드

		if (mPhotosList.size() == 0) {
		  requestPhoto();
		}

 URL를 통해 사진을 받아와서 list를 추가하는 것은 receivedNewphoto()를 통해 받아온다.

@Override
public void receivedNewPhoto(final Photo newPhoto) {

  runOnUiThread(new Runnable() {
    @Override
    public void run() {
      mPhotosList.add(newPhoto);
      mAdapter.notifyItemInserted(mPhotosList.size());
    }
  });
}
 여기서 runOnUiThread를 주의 깊게 살펴볼 필요가 있다.
 이는 여기서 참고하자.
[https://developer.android.com/reference/android/app/Activity.html](https://developer.android.com/reference/android/app/Activity.html)
[http://itmining.tistory.com/6](http://itmining.tistory.com/6)

 9. smooth scrolling를 통해 계속해서 URL를 통해 NASA의 contents를 받아오는 작업을 해보자.

MainActivity.java안에

	private int getLastVisibleItemPosition() {
  return mLinearLayoutManager.findLastVisibleItemPosition();
}

 위는 RecyclerView's LinearLayoutManager가 마지막 아이템의 index를 얻기 위해 사용되었고.
이는

private void setRecyclerViewScrollListener() {
  mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
      super.onScrollStateChanged(recyclerView, newState);
      int totalItemCount = mRecyclerView.getLayoutManager().getItemCount();
      if (!mImageRequester.isLoadingData() && totalItemCount == getLastVisibleItemPosition() + 1) {
        requestPhoto();
      }
    }
  });
}

 위 메소드를 onCreate안에 setRecyclerViewScrollListener()를 넣어야함.


 10. LinearLayoutManager 에서 GridLayoutManager로 변경하는 방법은 그냥 따라감.

 11. ItemTouchHelper를 활용하면 onSwiped 와 onMove 등을 활용할 수있다.

private void setRecyclerViewItemTouchListener() {

  //1
  ItemTouchHelper.SimpleCallback itemTouchCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder viewHolder1) {
      //2
      return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
      //3
      int position = viewHolder.getAdapterPosition();
      mPhotosList.remove(position);
      mRecyclerView.getAdapter().notifyItemRemoved(position);
    }
  };

  //4
  ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemTouchCallback);
  itemTouchHelper.attachToRecyclerView(mRecyclerView);
