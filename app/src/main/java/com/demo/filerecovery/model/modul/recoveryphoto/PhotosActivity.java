package com.demo.filerecovery.model.modul.recoveryphoto;

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
import com.demo.filerecovery.model.modul.recoveryphoto.Model.PhotoModel;
import com.demo.filerecovery.model.modul.recoveryphoto.adapter.PhotoAdapter;
import com.demo.filerecovery.model.modul.recoveryphoto.task.RecoverPhotosAsyncTask;
import com.demo.filerecovery.ui.activity.RestoreResultActivity;
import com.demo.filerecovery.utilts.SharePreferenceUtils;
import com.demo.filerecovery.utilts.Utils;
import com.demo.filerecovery.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;


public class PhotosActivity extends AppCompatActivity {
    PhotoAdapter adapter;
    Button btnRestore;
     
    RecoverPhotosAsyncTask mRecoverPhotosAsyncTask;
    RecyclerView recyclerView;
    Toolbar toolbar;
    List<PhotoModel> mList = new ArrayList();
    List<PhotoModel> originalList = new ArrayList();
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

    static int lambda$sortListByDate$11(boolean z, PhotoModel photoModel, PhotoModel photoModel2) {
        if (z) {
            return Long.valueOf(photoModel2.getLastModified()).compareTo(Long.valueOf(photoModel.getLastModified()));
        }
        return Long.valueOf(photoModel.getLastModified()).compareTo(Long.valueOf(photoModel2.getLastModified()));
    }

    static int lambda$sortListBySize$12(boolean z, PhotoModel photoModel, PhotoModel photoModel2) {
        if (z) {
            return Long.valueOf(photoModel2.getSizePhoto()).compareTo(Long.valueOf(photoModel.getSizePhoto()));
        }
        return Long.valueOf(photoModel.getSizePhoto()).compareTo(Long.valueOf(photoModel2.getSizePhoto()));
    }

    static int lambda$sortListByName$13(boolean z, PhotoModel photoModel, PhotoModel photoModel2) {
        if (z) {
            return photoModel2.getPathPhoto().substring(photoModel2.getPathPhoto().lastIndexOf("/") + 1).compareTo(photoModel.getPathPhoto().substring(photoModel.getPathPhoto().lastIndexOf("/") + 1));
        }
        return photoModel.getPathPhoto().substring(photoModel.getPathPhoto().lastIndexOf("/") + 1).compareTo(photoModel2.getPathPhoto().substring(photoModel2.getPathPhoto().lastIndexOf("/") + 1));
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Utils.setStatusBarGradiant(this);
        setContentView(R.layout.activity_photos);
        intView();
        intData();
      
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_tool_bar_photo, menu);
        return true;
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
                    PhotosActivity.this.lambda$showMenuSortFile$0$PhotosActivity(view);
                }
            });
        }
        if (textView2 != null) {
            textView2.setOnClickListener(new View.OnClickListener() {
                @Override
                public final void onClick(View view) {
                    PhotosActivity.this.lambda$showMenuSortFile$1$PhotosActivity(view);
                }
            });
        }
        if (textView3 != null) {
            textView3.setOnClickListener(new View.OnClickListener() {
                @Override
                public final void onClick(View view) {
                    PhotosActivity.this.lambda$showMenuSortFile$2$PhotosActivity(view);
                }
            });
        }
        if (textView4 != null) {
            textView4.setOnClickListener(new View.OnClickListener() {
                @Override
                public final void onClick(View view) {
                    PhotosActivity.this.lambda$showMenuSortFile$3$PhotosActivity(view);
                }
            });
        }
        if (textView5 != null) {
            textView5.setOnClickListener(new View.OnClickListener() {
                @Override
                public final void onClick(View view) {
                    PhotosActivity.this.lambda$showMenuSortFile$4$PhotosActivity(view);
                }
            });
        }
        if (textView6 != null) {
            textView6.setOnClickListener(new View.OnClickListener() {
                @Override
                public final void onClick(View view) {
                    PhotosActivity.this.lambda$showMenuSortFile$5$PhotosActivity(view);
                }
            });
        }
        this.bottomSheetDialogSortBy.setDismissWithAnimation(true);
        this.bottomSheetDialogSortBy.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        this.bottomSheetDialogSortBy.show();
    }

    public void lambda$showMenuSortFile$0$PhotosActivity(View view) {
        this.llSortBy.setVisibility(View.VISIBLE);
        this.txtTypeSort.setText(getString(R.string.sort_by_latest));
        sortByTime(false);
        this.bottomSheetDialogSortBy.dismiss();
    }

    public void lambda$showMenuSortFile$1$PhotosActivity(View view) {
        this.llSortBy.setVisibility(View.VISIBLE);
        this.txtTypeSort.setText(getString(R.string.sort_by_newest));
        sortByTime(true);
        this.bottomSheetDialogSortBy.dismiss();
    }

    public void lambda$showMenuSortFile$2$PhotosActivity(View view) {
        this.llSortBy.setVisibility(View.VISIBLE);
        this.txtTypeSort.setText(getString(R.string.sort_by_name_a_to_z));
        sortByName(false);
        this.bottomSheetDialogSortBy.dismiss();
    }

    public void lambda$showMenuSortFile$3$PhotosActivity(View view) {
        this.llSortBy.setVisibility(View.VISIBLE);
        this.txtTypeSort.setText(getString(R.string.sort_by_name_z_to_a));
        sortByName(true);
        this.bottomSheetDialogSortBy.dismiss();
    }

    public void lambda$showMenuSortFile$4$PhotosActivity(View view) {
        this.llSortBy.setVisibility(View.VISIBLE);
        this.txtTypeSort.setText(getString(R.string.sort_by_storage_min_to_max));
        sortBySize(false);
        this.bottomSheetDialogSortBy.dismiss();
    }

    public void lambda$showMenuSortFile$5$PhotosActivity(View view) {
        this.llSortBy.setVisibility(View.VISIBLE);
        this.txtTypeSort.setText(getString(R.string.sort_by_storage_max_to_min));
        sortBySize(true);
        this.bottomSheetDialogSortBy.dismiss();
    }

    public void intView() {
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.btnDate = (ConstraintLayout) findViewById(R.id.btnDate);
        this.tvtDate = (TextView) findViewById(R.id.tvtDate);
        this.imgDate = (ImageView) findViewById(R.id.imgDate);
        this.btnSize = (ConstraintLayout) findViewById(R.id.btnSize);
        this.tvtSize = (TextView) findViewById(R.id.tvtSize);
        this.txtAdsReward = (TextView) findViewById(R.id.txtAdsReward);
        this.imgSize = (ImageView) findViewById(R.id.imgSize);
        this.btnRestore = (Button) findViewById(R.id.btnRestore);
        this.recyclerView = (RecyclerView) findViewById(R.id.gv_folder);
        this.llSortBy = (LinearLayout) findViewById(R.id.llSortBy);
        this.txtTypeSort = (TextView) findViewById(R.id.txtTypeSort);
        this.imgCloseSort = (ImageView) findViewById(R.id.imgCloseSort);
        this.recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        this.recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        this.recyclerView.setItemAnimator(new DefaultItemAnimator());
        switchBtnSort();
    }

    private void switchBtnSort() {
        this.btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                PhotosActivity.this.lambda$switchBtnSort$6$PhotosActivity(view);
            }
        });
        this.btnSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                PhotosActivity.this.lambda$switchBtnSort$7$PhotosActivity(view);
            }
        });
    }

    public void lambda$switchBtnSort$6$PhotosActivity(View view) {
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

    public void lambda$switchBtnSort$7$PhotosActivity(View view) {
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

    public void intData() {
        this.toolbar.setTitle(getIntent().getStringExtra("FOLDER"));
        List<PhotoModel> listPhotoScanSelect = App.getInstance().getStorageCommon().getListPhotoScanSelect();
        this.mList.clear();
        this.mList.addAll(listPhotoScanSelect);
        this.originalList.clear();
        this.originalList.addAll(listPhotoScanSelect);
        setSupportActionBar(this.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        this.adapter = new PhotoAdapter(this, new OnItemSelected() {
            @Override
            public final void onItemSelect(int i) {
                PhotosActivity.this.lambda$intData$8$PhotosActivity(i);
            }
        });
        sortListByDate(this.mList, false);
        this.recyclerView.setAdapter(this.adapter);
        this.btnRestore.setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                PhotosActivity.this.lambda$intData$9$PhotosActivity(view);
            }
        });
        this.imgCloseSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                PhotosActivity.this.lambda$intData$10$PhotosActivity(view);
            }
        });
    }

    public void lambda$intData$8$PhotosActivity(int i) {
        if (i > 0) {
            this.btnRestore.setEnabled(true);
            this.btnRestore.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.light_rad));
            Button button = this.btnRestore;
            button.setText(getString(R.string.restore) + " (" + i + ")");
            return;
        }
        this.btnRestore.setEnabled(false);
        this.btnRestore.setBackgroundTintList(ContextCompat.getColorStateList(this, R.color.text_color));
        this.btnRestore.setText(getString(R.string.restore));
    }

    public void lambda$intData$9$PhotosActivity(View view) {
        if (this.adapter.getSelectedItem().size() == 0) {
            Toast.makeText(this, getText(R.string.cannot_restore), Toast.LENGTH_LONG).show();
        } else {
            startRestoreFile();
        }
    }

    public void lambda$intData$10$PhotosActivity(View view) {
        this.llSortBy.setVisibility(View.GONE);
        this.adapter.setData(this.originalList);
    }

    private void sortListByDate(List<PhotoModel> list, final boolean z) {
        Collections.sort(list, new Comparator() {
            @Override
            public final int compare(Object obj, Object obj2) {
                return PhotosActivity.lambda$sortListByDate$11(z, (PhotoModel) obj, (PhotoModel) obj2);
            }
        });
        this.adapter.setData(list);
    }

    private void sortListBySize(List<PhotoModel> list, final boolean z) {
        Collections.sort(list, new Comparator() {
            @Override
            public final int compare(Object obj, Object obj2) {
                return PhotosActivity.lambda$sortListBySize$12(z, (PhotoModel) obj, (PhotoModel) obj2);
            }
        });
        this.adapter.setData(list);
    }

    private void sortListByName(List<PhotoModel> list, final boolean z) {
        Collections.sort(list, new Comparator() {
            @Override
            public final int compare(Object obj, Object obj2) {
                return PhotosActivity.lambda$sortListByName$13(z, (PhotoModel) obj, (PhotoModel) obj2);
            }
        });
        this.adapter.setData(list);
    }

    public void openRestoreActivity() {
        ArrayList<String> arrayList = new ArrayList<>();
        Iterator<PhotoModel> it = this.adapter.getSelectedItem().iterator();
        long j = 0;
        while (it.hasNext()) {
            PhotoModel next = it.next();
            j += next.getSizePhoto();
            arrayList.add(next.getPathPhoto());
        }
        final Intent intent = new Intent(getApplicationContext(), RestoreResultActivity.class);
        intent.putExtra("value" , j);
        intent.putExtra(Constants.FB_LOG_RESTORE_RESULT_TYPE, 0);
        intent.putStringArrayListExtra("listPath", arrayList);
        PhotosActivity.this.startActivityForResult(intent, 101);

        SharePreferenceUtils.restoreFeatureUsed(this);
    }

    private void startRestoreFile() {
        RecoverPhotosAsyncTask recoverPhotosAsyncTask = new RecoverPhotosAsyncTask(this, this.adapter.getSelectedItem(), new RecoverPhotosAsyncTask.OnRestoreListener() {
            @Override
            public final void onComplete() {
                PhotosActivity.this.openRestoreActivity();
            }
        });
        this.mRecoverPhotosAsyncTask = recoverPhotosAsyncTask;
        recoverPhotosAsyncTask.execute(new String[0]);
    }

    public boolean SDCardCheck() {
        File[] externalFilesDirs = ContextCompat.getExternalFilesDirs(this, null);
        return (externalFilesDirs.length <= 1 || externalFilesDirs[0] == null || externalFilesDirs[1] == null) ? false : true;
    }

    private int dpToPx(int i) {
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, i, getResources().getDisplayMetrics()));
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
