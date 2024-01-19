package com.demo.filerecovery.model.modul.recoveryvideo;

import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.filerecovery.App;
import com.demo.filerecovery.Constants;



import com.demo.filerecovery.listener.OnItemSelected;
import com.demo.filerecovery.model.modul.recoveryvideo.Model.VideoModel;
import com.demo.filerecovery.model.modul.recoveryvideo.adapter.VideoAdapter;
import com.demo.filerecovery.model.modul.recoveryvideo.task.RecoverVideoAsyncTask;
import com.demo.filerecovery.ui.activity.RestoreResultActivity;
import com.demo.filerecovery.utilts.SharePreferenceUtils;
import com.demo.filerecovery.utilts.Utils;
import com.demo.filerecovery.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;


public class VideoActivity extends AppCompatActivity {
    VideoAdapter adapter;
    Button btnRestore;
     
    RecoverVideoAsyncTask mRecoverVideoAsyncTask;
    RecyclerView recyclerView;
    Toolbar toolbar;
    ArrayList<VideoModel> mList = new ArrayList<>();
    private BottomSheetDialog bottomSheetDialogSortBy;
    private ConstraintLayout btnDate;
    private ConstraintLayout btnSize;
    private ImageView imgCloseSort;
    private ImageView imgDate;
    private ImageView imgSize;
    private LinearLayout llSortBy;
    private TextView tvtDate;
    private TextView tvtSize;
    private TextView txtAdsReward;
    private TextView txtTypeSort;
    private boolean isAscendingDate = true;
    private boolean isAscendingSize = true;
    private boolean isSelectedDate = true;
    private boolean isSelectedSize = false;
    private ArrayList<VideoModel> originalList = new ArrayList<>();

    static int lambda$sortListByDate$5(boolean z, VideoModel videoModel, VideoModel videoModel2) {
        if (z) {
            return Long.valueOf(videoModel2.getLastModified()).compareTo(Long.valueOf(videoModel.getLastModified()));
        }
        return Long.valueOf(videoModel.getLastModified()).compareTo(Long.valueOf(videoModel2.getLastModified()));
    }

    static int lambda$sortListByName$6(boolean z, VideoModel videoModel, VideoModel videoModel2) {
        if (z) {
            return videoModel2.getPathPhoto().substring(videoModel2.getPathPhoto().lastIndexOf("/") + 1).compareTo(videoModel.getPathPhoto().substring(videoModel.getPathPhoto().lastIndexOf("/") + 1));
        }
        return videoModel.getPathPhoto().substring(videoModel.getPathPhoto().lastIndexOf("/") + 1).compareTo(videoModel2.getPathPhoto().substring(videoModel2.getPathPhoto().lastIndexOf("/") + 1));
    }

    static int lambda$sortListBySize$7(boolean z, VideoModel videoModel, VideoModel videoModel2) {
        if (z) {
            return Long.valueOf(videoModel2.getSizePhoto()).compareTo(Long.valueOf(videoModel.getSizePhoto()));
        }
        return Long.valueOf(videoModel.getSizePhoto()).compareTo(Long.valueOf(videoModel2.getSizePhoto()));
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Utils.setStatusBarGradiant(this);
        setContentView(R.layout.activity_photos);
        intView();
        intData();
      
        
    }

    public void intView() {
        this.llSortBy = (LinearLayout) findViewById(R.id.llSortBy);
        this.txtTypeSort = (TextView) findViewById(R.id.txtTypeSort);
        this.imgCloseSort = (ImageView) findViewById(R.id.imgCloseSort);
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.btnDate = (ConstraintLayout) findViewById(R.id.btnDate);
        this.tvtDate = (TextView) findViewById(R.id.tvtDate);
        this.txtAdsReward = (TextView) findViewById(R.id.txtAdsReward);
        this.imgDate = (ImageView) findViewById(R.id.imgDate);
        this.btnSize = (ConstraintLayout) findViewById(R.id.btnSize);
        this.tvtSize = (TextView) findViewById(R.id.tvtSize);
        this.imgSize = (ImageView) findViewById(R.id.imgSize);
        this.btnRestore = (Button) findViewById(R.id.btnRestore);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.gv_folder);
        this.recyclerView = recyclerView;
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        this.recyclerView.addItemDecoration(new GridSpacingItemDecoration(1, dpToPx(5), true));
        this.recyclerView.setItemAnimator(new DefaultItemAnimator());
        switchBtnSort();
    }

    public void intData() {
        this.toolbar.setTitle(getIntent().getStringExtra("FOLDER"));
        List<VideoModel> listVideoScanSelect = App.getInstance().getStorageCommon().getListVideoScanSelect();
        this.mList.clear();
        this.mList.addAll(listVideoScanSelect);
        this.originalList.clear();
        this.originalList.addAll(listVideoScanSelect);
        setSupportActionBar(this.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        this.adapter = new VideoAdapter(this, this, new OnItemSelected() {
            @Override
            public final void onItemSelect(int i) {
                VideoActivity.this.lambda$intData$0$VideoActivity(i);
            }
        });
        sortListByDate(this.mList, false);
        this.recyclerView.setAdapter(this.adapter);
        this.btnRestore.setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                VideoActivity.this.lambda$intData$1$VideoActivity(view);
            }
        });
        this.imgCloseSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                VideoActivity.this.lambda$intData$2$VideoActivity(view);
            }
        });
    }

    public void lambda$intData$0$VideoActivity(int i) {
        if (i > 0) {
            this.btnRestore.setEnabled(true);
            this.btnRestore.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.light_rad));
            Button button = this.btnRestore;
            button.setText(getString(R.string.restore) + " (" + i + ")");
            return;
        }
        this.btnRestore.setEnabled(false);
        this.txtAdsReward.setVisibility(View.GONE);
        this.btnRestore.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.color_media_restored_disable));
        this.btnRestore.setText(getString(R.string.restore));
    }

    public void lambda$intData$1$VideoActivity(View view) {
        if (this.adapter.getSelectedItem().size() == 0) {
            Toast.makeText(this, "Cannot restore, all items are unchecked!", Toast.LENGTH_LONG).show();
        } else {
            executeRestore();
        }
    }

    public void lambda$intData$2$VideoActivity(View view) {
        this.llSortBy.setVisibility(View.GONE);
        this.adapter.setData(this.originalList);
    }

    public void executeRestore() {
        startRestoreFile();
    }

    public void openRestoreActivity() {
        ArrayList<String> arrayList = new ArrayList<>();
        Iterator<VideoModel> it = this.adapter.getSelectedItem().iterator();
        long j = 0;
        while (it.hasNext()) {
            VideoModel next = it.next();
            j += next.getSizePhoto();
            arrayList.add(next.getPathPhoto());
        }
        final Intent intent = new Intent(getApplicationContext(), RestoreResultActivity.class);
        intent.putExtra("value" , j);
        intent.putExtra(Constants.FB_LOG_RESTORE_RESULT_TYPE, 1);
        intent.putStringArrayListExtra("listPath", arrayList);
        VideoActivity.this.startActivityForResult(intent, 101);

        SharePreferenceUtils.restoreFeatureUsed(this);
    }

    private void startRestoreFile() {
        RecoverVideoAsyncTask recoverVideoAsyncTask = new RecoverVideoAsyncTask(this, this.adapter.getSelectedItem(), new RecoverVideoAsyncTask.OnRestoreListener() {
            @Override
            public final void onComplete() {
                VideoActivity.this.openRestoreActivity();
            }
        });
        this.mRecoverVideoAsyncTask = recoverVideoAsyncTask;
        recoverVideoAsyncTask.execute(new String[0]);
    }

    private void switchBtnSort() {
        this.btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                VideoActivity.this.lambda$switchBtnSort$3$VideoActivity(view);
            }
        });
        this.btnSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                VideoActivity.this.lambda$switchBtnSort$4$VideoActivity(view);
            }
        });
    }

    public void lambda$switchBtnSort$3$VideoActivity(View view) {
        if (this.isSelectedDate) {
            if (this.isAscendingDate) {
                this.imgDate.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_sort_decrease));
                sortListByDate(this.mList, true);
            } else {
                this.imgDate.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_sort_ascending));
                sortListByDate(this.mList, false);
            }
            this.isAscendingDate = !this.isAscendingDate;
            return;
        }
        sortListByDate(this.mList, false);
        selectedBtn(0);
        unSelectedBtn(1);
        this.isSelectedDate = !this.isSelectedDate;
        this.isSelectedSize = !this.isSelectedSize;
    }

    public void lambda$switchBtnSort$4$VideoActivity(View view) {
        if (this.isSelectedSize) {
            if (this.isAscendingSize) {
                this.imgSize.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_sort_decrease));
                sortListBySize(this.mList, true);
            } else {
                this.imgSize.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_sort_ascending));
                sortListBySize(this.mList, false);
            }
            this.isAscendingSize = !this.isAscendingSize;
            return;
        }
        sortListBySize(this.mList, false);
        selectedBtn(1);
        unSelectedBtn(0);
        this.isSelectedDate = !this.isSelectedDate;
        this.isSelectedSize = !this.isSelectedSize;
    }

    private void selectedBtn(int i) {
        if (i == 0) {
            this.btnDate.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_selected_sort));
            this.tvtDate.setTextColor(getResources().getColor(R.color.colorBlue));
            this.imgDate.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_sort_ascending));
            return;
        }
        this.btnSize.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_selected_sort));
        this.tvtSize.setTextColor(getResources().getColor(R.color.colorBlue));
        this.imgSize.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_sort_ascending));
    }

    private void unSelectedBtn(int i) {
        if (i == 0) {
            this.btnDate.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_unseleced_sort));
            this.tvtDate.setTextColor(getResources().getColor(R.color.color990));
            this.imgDate.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_sort_noselected));
            return;
        }
        this.btnSize.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_unseleced_sort));
        this.tvtSize.setTextColor(getResources().getColor(R.color.color990));
        this.imgSize.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_sort_noselected));
    }

    private void sortListByDate(ArrayList<VideoModel> arrayList, final boolean z) {
        Collections.sort(arrayList, new Comparator() {
            @Override
            public final int compare(Object obj, Object obj2) {
                return VideoActivity.lambda$sortListByDate$5(z, (VideoModel) obj, (VideoModel) obj2);
            }
        });
        this.adapter.setData(arrayList);
    }

    private void sortListByName(ArrayList<VideoModel> arrayList, final boolean z) {
        Collections.sort(arrayList, new Comparator() {
            @Override
            public final int compare(Object obj, Object obj2) {
                return VideoActivity.lambda$sortListByName$6(z, (VideoModel) obj, (VideoModel) obj2);
            }
        });
        this.adapter.setData(arrayList);
    }

    private void sortListBySize(ArrayList<VideoModel> arrayList, final boolean z) {
        Collections.sort(arrayList, new Comparator() {
            @Override
            public final int compare(Object obj, Object obj2) {
                return VideoActivity.lambda$sortListBySize$7(z, (VideoModel) obj, (VideoModel) obj2);
            }
        });
        this.adapter.setData(arrayList);
    }

    private int dpToPx(int i) {
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, i, getResources().getDisplayMetrics()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_tool_bar_photo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            onBackPressed();
        } else if (menuItem.getItemId() == R.id.sortFile) {
            showMenuSortFile();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 101 && i2 == 102) {
            setResult(102);
            finish();
        }
    }

    private void showMenuSortFile() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this, R.style.CustomBottomSheetDialogTheme);
        this.bottomSheetDialogSortBy = bottomSheetDialog;
        bottomSheetDialog.setContentView(R.layout.layout_bottom_sheet_sort_file);
        TextView textView = (TextView) this.bottomSheetDialogSortBy.findViewById(R.id.txtLatest);
        TextView textView2 = (TextView) this.bottomSheetDialogSortBy.findViewById(R.id.txtNewest);
        TextView textView3 = (TextView) this.bottomSheetDialogSortBy.findViewById(R.id.txtAtoZ);
        TextView textView4 = (TextView) this.bottomSheetDialogSortBy.findViewById(R.id.txtZtoA);
        TextView textView5 = (TextView) this.bottomSheetDialogSortBy.findViewById(R.id.txtMinToMax);
        TextView textView6 = (TextView) this.bottomSheetDialogSortBy.findViewById(R.id.txtMaxToMin);
        if (textView != null) {
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public final void onClick(View view) {
                    VideoActivity.this.lambda$showMenuSortFile$8$VideoActivity(view);
                }
            });
        }
        if (textView2 != null) {
            textView2.setOnClickListener(new View.OnClickListener() {
                @Override
                public final void onClick(View view) {
                    VideoActivity.this.lambda$showMenuSortFile$9$VideoActivity(view);
                }
            });
        }
        if (textView3 != null) {
            textView3.setOnClickListener(new View.OnClickListener() {
                @Override
                public final void onClick(View view) {
                    VideoActivity.this.lambda$showMenuSortFile$10$VideoActivity(view);
                }
            });
        }
        if (textView4 != null) {
            textView4.setOnClickListener(new View.OnClickListener() {
                @Override
                public final void onClick(View view) {
                    VideoActivity.this.lambda$showMenuSortFile$11$VideoActivity(view);
                }
            });
        }
        if (textView5 != null) {
            textView5.setOnClickListener(new View.OnClickListener() {
                @Override
                public final void onClick(View view) {
                    VideoActivity.this.lambda$showMenuSortFile$12$VideoActivity(view);
                }
            });
        }
        if (textView6 != null) {
            textView6.setOnClickListener(new View.OnClickListener() {
                @Override
                public final void onClick(View view) {
                    VideoActivity.this.lambda$showMenuSortFile$13$VideoActivity(view);
                }
            });
        }
        this.bottomSheetDialogSortBy.setDismissWithAnimation(true);
        this.bottomSheetDialogSortBy.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        this.bottomSheetDialogSortBy.show();
    }

    public void lambda$showMenuSortFile$8$VideoActivity(View view) {
        this.llSortBy.setVisibility(View.VISIBLE);
        this.txtTypeSort.setText(getString(R.string.sort_by_latest));
        sortByTime(false);
        this.bottomSheetDialogSortBy.dismiss();
    }

    public void lambda$showMenuSortFile$9$VideoActivity(View view) {
        this.llSortBy.setVisibility(View.VISIBLE);
        this.txtTypeSort.setText(getString(R.string.sort_by_newest));
        sortByTime(true);
        this.bottomSheetDialogSortBy.dismiss();
    }

    public void lambda$showMenuSortFile$10$VideoActivity(View view) {
        this.llSortBy.setVisibility(View.VISIBLE);
        this.txtTypeSort.setText(getString(R.string.sort_by_name_a_to_z));
        sortByName(false);
        this.bottomSheetDialogSortBy.dismiss();
    }

    public void lambda$showMenuSortFile$11$VideoActivity(View view) {
        this.llSortBy.setVisibility(View.VISIBLE);
        this.txtTypeSort.setText(getString(R.string.sort_by_name_z_to_a));
        sortByName(true);
        this.bottomSheetDialogSortBy.dismiss();
    }

    public void lambda$showMenuSortFile$12$VideoActivity(View view) {
        this.llSortBy.setVisibility(View.VISIBLE);
        this.txtTypeSort.setText(getString(R.string.sort_by_storage_min_to_max));
        sortBySize(false);
        this.bottomSheetDialogSortBy.dismiss();
    }

    public void lambda$showMenuSortFile$13$VideoActivity(View view) {
        this.llSortBy.setVisibility(View.VISIBLE);
        this.txtTypeSort.setText(getString(R.string.sort_by_storage_max_to_min));
        sortBySize(true);
        this.bottomSheetDialogSortBy.dismiss();
    }

    private void sortByTime(boolean z) {
        sortListByDate(this.mList, z);
    }

    private void sortByName(boolean z) {
        sortListByName(this.mList, z);
    }

    private void sortBySize(boolean z) {
        sortListBySize(this.mList, z);
    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {
        private boolean includeEdge;
        private int spacing;
        private int spanCount;

        public GridSpacingItemDecoration(int i, int i2, boolean z) {
            this.spanCount = i;
            this.spacing = i2;
            this.includeEdge = z;
        }

        @Override
        public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
            int childAdapterPosition = recyclerView.getChildAdapterPosition(view);
            int i = this.spanCount;
            int i2 = childAdapterPosition % i;
            if (this.includeEdge) {
                int i3 = this.spacing;
                rect.left = i3 - ((i2 * i3) / i);
                rect.right = ((i2 + 1) * this.spacing) / this.spanCount;
                if (childAdapterPosition < this.spanCount) {
                    rect.top = this.spacing;
                }
                rect.bottom = this.spacing;
                return;
            }
            rect.left = (this.spacing * i2) / i;
            int i4 = this.spacing;
            rect.right = i4 - (((i2 + 1) * i4) / this.spanCount);
            if (childAdapterPosition >= this.spanCount) {
                rect.top = this.spacing;
            }
        }
    }
}
