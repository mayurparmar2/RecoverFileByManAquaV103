package com.demo.filerecovery.ui.activity;

import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.ContentLoadingProgressBar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.demo.filerecovery.App;
import com.demo.filerecovery.Constants;
import com.demo.filerecovery.R;
import com.demo.filerecovery.model.modul.recoveryaudio.Model.AudioModel;
import com.demo.filerecovery.model.modul.recoverydocument.Model.DocumentModel;
import com.demo.filerecovery.model.modul.recoveryphoto.Model.PhotoModel;
import com.demo.filerecovery.model.modul.recoveryvideo.Model.VideoModel;
import com.demo.filerecovery.ui.adapter.RestoreFileAdapter;
import com.demo.filerecovery.ui.adapter.ViewPagerAdapter;
import com.demo.filerecovery.ui.dialog.DeleteFileDialog;
import com.demo.filerecovery.ui.fragment.RestoredAudioFrg;
import com.demo.filerecovery.ui.fragment.RestoredDocumentsFrg;
import com.demo.filerecovery.ui.fragment.RestoredPhotoFrg;
import com.demo.filerecovery.ui.fragment.RestoredVideoFrg;
import com.demo.filerecovery.utilts.FileUtil;
import com.demo.filerecovery.utilts.Utils;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.tabs.TabLayout;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;


public class RestoredFileActivity extends AppCompatActivity implements DeleteFileDialog.DeleteFileDialogCallBack {
    private static final int TAB_AUDIO = 2;
    private static final int TAB_DOCUMENT = 3;
    private static final int TAB_PHOTO = 0;
    private static final int TAB_VIDEO = 1;
    private final String TAG = "RestoredFileActivity";
    public BottomSheetDialog bottomSheetDialogSortBy;
    public List<Fragment> fragmentList;
    public ArrayList<String> listFileDelete;
    public ContentLoadingProgressBar loadingDialog;
    public RestoredAudioFrg restoredAudioFrg;
    public RestoredDocumentsFrg restoredDocumentsFrg;
    public RestoredPhotoFrg restoredPhotoFrg;
    public RestoredVideoFrg restoredVideoFrg;
    public TabLayout tabLayout;
    public TextView tvTotalFileSelected;
    public ViewPager vpMedia;

    private DeleteFileDialog deleteFileDialog;
    private ImageView imgAtoZSelected;
    private ImageView imgLatestSelected;
    private ImageView imgMaxToMinSelected;
    private ImageView imgMinToMaxSelected;
    private ImageView imgNewestSelected;
    private ImageView imgZtoASelected;
    private Toolbar toolbar;
    private int type;
    private String currentSort = "";

    @Override
    public void onDeleteFileCallbackInDialogWithPosition(int i) {
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        RestoreFileAdapter.OnItemStateChange onItemStateChange = new RestoreFileAdapter.OnItemStateChange() {
            @Override
            public void onCheckBoxStateChange(String str, boolean z, int i) {
                if (z) {
                    RestoredFileActivity.this.listFileDelete.add(str);
                } else {
                    RestoredFileActivity.this.listFileDelete.remove(str);
                }
                ((TextView) RestoredFileActivity.this.findViewById(R.id.tvTotalFileSelected)).setText(RestoredFileActivity.this.listFileDelete.size() + "");
            }

            @Override
            public void onLongClick() {
                RestoredFileActivity.this.findViewById(R.id.editFileToolbar).setVisibility(View.VISIBLE);
                RestoredFileActivity.this.findViewById(R.id.tabLayout).setVisibility(View.GONE);
            }
        };
        this.restoredDocumentsFrg = new RestoredDocumentsFrg(onItemStateChange);
        this.restoredAudioFrg = new RestoredAudioFrg(onItemStateChange);
        this.restoredVideoFrg = new RestoredVideoFrg(onItemStateChange);
        this.restoredPhotoFrg = new RestoredPhotoFrg(onItemStateChange);
        ArrayList arrayList = new ArrayList();
        this.fragmentList = arrayList;
        arrayList.addAll(Arrays.asList(this.restoredPhotoFrg, this.restoredVideoFrg, this.restoredAudioFrg, this.restoredDocumentsFrg));
        Utils.setStatusBarGradiant(this);
        setContentView(R.layout.activity_restored_file);


        this.listFileDelete = new ArrayList<>();
        this.type = getIntent().getIntExtra(Constants.FB_LOG_RESTORE_RESULT_TYPE, 0);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.toolbar = toolbar;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        this.vpMedia = (ViewPager) findViewById(R.id.vpMedia);
        this.tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        this.loadingDialog = (ContentLoadingProgressBar) findViewById(R.id.loadingDialog);
        this.tvTotalFileSelected = (TextView) findViewById(R.id.tvTotalFileSelected);
        findViewById(R.id.imgDeleteFile).setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                RestoredFileActivity.this.lambda$onCreate$0$RestoredFileActivity(view);
            }
        });
        this.tabLayout.setupWithViewPager(this.vpMedia);
        this.vpMedia.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrollStateChanged(int i) {
            }

            @Override
            public void onPageScrolled(int i, float f, int i2) {
            }

            @Override
            public void onPageSelected(int i) {
                RestoredFileActivity.this.findViewById(R.id.editFileToolbar).setVisibility(View.GONE);
                RestoredFileActivity.this.tabLayout.setVisibility(View.VISIBLE);
                RestoredFileActivity.this.tvTotalFileSelected.setText(RestoredFileActivity.this.getString(R.string.size_of_list_delete_when_clear));
                RestoredFileActivity.this.listFileDelete.clear();
                RestoredFileActivity.this.bottomSheetDialogSortBy = null;
                int currentItem = RestoredFileActivity.this.vpMedia.getCurrentItem();
                if (currentItem == 0) {
                    RestoredFileActivity.this.restoredPhotoFrg.resetAdapter();
                } else if (currentItem == 1) {
                    RestoredFileActivity.this.restoredVideoFrg.resetAdapter();
                } else if (currentItem == 2) {
                    RestoredFileActivity.this.restoredAudioFrg.resetAdapter();
                } else if (currentItem == 3) {
                    RestoredFileActivity.this.restoredDocumentsFrg.resetAdapter();
                }
            }
        });
        new RestoredFileAsyncTask().execute(new Void[0]);
    }

    public void lambda$onCreate$0$RestoredFileActivity(View view) {
        if (this.listFileDelete.size() == 0) {
            Toast.makeText(this, (int) R.string.warning_empty_file_selected, Toast.LENGTH_SHORT).show();
            return;
        }
        ArrayList arrayList = new ArrayList();
        Iterator<String> it = this.listFileDelete.iterator();
        while (it.hasNext()) {
            arrayList.add(new File(it.next()));
        }
        DeleteFileDialog deleteFileDialog = new DeleteFileDialog(this, arrayList, null);
        this.deleteFileDialog = deleteFileDialog;
        deleteFileDialog.setDeleteFileDialogCallBack(this);
        this.deleteFileDialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            onBackPressed();
        } else if (menuItem.getItemId() == R.id.btnSortList) {
            showMenuSortFile();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        setResult(102);
        finish();
    }

    @Override
    public void onDeleteFileCallbackInDialog(List<? extends File> list) {
        ArrayList<PhotoModel> arrayList = new ArrayList<>();
        ArrayList<VideoModel> arrayList2 = new ArrayList<>();
        ArrayList<AudioModel> arrayList3 = new ArrayList<>();
        ArrayList<DocumentModel> arrayList4 = new ArrayList<>();
        int currentItem = this.vpMedia.getCurrentItem();
        if (currentItem == 0) {
            arrayList = App.getInstance().getStorageCommon().getListPhoto();
        } else if (currentItem == 1) {
            arrayList2 = App.getInstance().getStorageCommon().getListVideo();
        } else if (currentItem == 2) {
            arrayList3 = App.getInstance().getStorageCommon().getListAudio();
        } else if (currentItem == 3) {
            arrayList4 = App.getInstance().getStorageCommon().getListDocument();
        }
        for (File file : list) {
            FileUtil.copyFileToInternalStorage(this, file.getPath());
            int currentItem2 = this.vpMedia.getCurrentItem();
            if (currentItem2 == 0) {
                Iterator<PhotoModel> it = arrayList.iterator();
                while (it.hasNext()) {
                    if (it.next().getPathPhoto().equals(file.getPath())) {
                        it.remove();
                    }
                }
            } else if (currentItem2 == 1) {
                Iterator<VideoModel> it2 = arrayList2.iterator();
                while (it2.hasNext()) {
                    if (it2.next().getPathPhoto().equals(file.getPath())) {
                        it2.remove();
                    }
                }
            } else if (currentItem2 == 2) {
                Iterator<AudioModel> it3 = arrayList3.iterator();
                while (it3.hasNext()) {
                    if (it3.next().getPathPhoto().equals(file.getPath())) {
                        it3.remove();
                    }
                }
            } else if (currentItem2 == 3) {
                Iterator<DocumentModel> it4 = arrayList4.iterator();
                while (it4.hasNext()) {
                    if (it4.next().getPathDocument().equals(file.getPath())) {
                        it4.remove();
                    }
                }
            }
        }
        int currentItem3 = this.vpMedia.getCurrentItem();
        if (currentItem3 == 0) {
            this.restoredPhotoFrg.setDataAdapter(arrayList);
            if (arrayList.size() == 0) {
                this.restoredPhotoFrg.displayEmptyText();
            }
        } else if (currentItem3 == 1) {
            this.restoredVideoFrg.setDataAdapter(arrayList2);
            if (arrayList2.size() == 0) {
                this.restoredVideoFrg.displayEmptyText();
            }
        } else if (currentItem3 == 2) {
            this.restoredAudioFrg.setDataAdapter(arrayList3);
            if (arrayList3.size() == 0) {
                this.restoredAudioFrg.displayEmptyText();
            }
        } else if (currentItem3 == 3) {
            this.restoredDocumentsFrg.setDataAdapter(arrayList4);
            if (arrayList4.size() == 0) {
                this.restoredDocumentsFrg.displayEmptyText();
            }
        }
        this.listFileDelete.clear();
        this.tvTotalFileSelected.setText(getString(R.string.size_of_list_delete_when_clear));
        findViewById(R.id.editFileToolbar).setVisibility(View.GONE);
        this.tabLayout.setVisibility(View.VISIBLE);
    }

    private void showMenuSortFile() {
        if (this.bottomSheetDialogSortBy == null) {
            BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this, R.style.CustomBottomSheetDialogTheme);
            this.bottomSheetDialogSortBy = bottomSheetDialog;
            bottomSheetDialog.setContentView(R.layout.layout_bottom_sheet_sort_file);
            this.imgNewestSelected = (ImageView) this.bottomSheetDialogSortBy.findViewById(R.id.imgNewestSelected);
            this.imgLatestSelected = (ImageView) this.bottomSheetDialogSortBy.findViewById(R.id.imgLatestSelected);
            this.imgAtoZSelected = (ImageView) this.bottomSheetDialogSortBy.findViewById(R.id.imgAtoZSelected);
            this.imgZtoASelected = (ImageView) this.bottomSheetDialogSortBy.findViewById(R.id.imgZtoASelected);
            this.imgMinToMaxSelected = (ImageView) this.bottomSheetDialogSortBy.findViewById(R.id.imgMinToMaxSelected);
            this.imgMaxToMinSelected = (ImageView) this.bottomSheetDialogSortBy.findViewById(R.id.imgMaxToMinSelected);
        }
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
                    RestoredFileActivity.this.lambda$showMenuSortFile$1$RestoredFileActivity(view);
                }
            });
        }
        if (textView2 != null) {
            textView2.setOnClickListener(new View.OnClickListener() {
                @Override
                public final void onClick(View view) {
                    RestoredFileActivity.this.lambda$showMenuSortFile$2$RestoredFileActivity(view);
                }
            });
        }
        if (textView3 != null) {
            textView3.setOnClickListener(new View.OnClickListener() {
                @Override
                public final void onClick(View view) {
                    RestoredFileActivity.this.lambda$showMenuSortFile$3$RestoredFileActivity(view);
                }
            });
        }
        if (textView4 != null) {
            textView4.setOnClickListener(new View.OnClickListener() {
                @Override
                public final void onClick(View view) {
                    RestoredFileActivity.this.lambda$showMenuSortFile$4$RestoredFileActivity(view);
                }
            });
        }
        if (textView5 != null) {
            textView5.setOnClickListener(new View.OnClickListener() {
                @Override
                public final void onClick(View view) {
                    RestoredFileActivity.this.lambda$showMenuSortFile$5$RestoredFileActivity(view);
                }
            });
        }
        if (textView6 != null) {
            textView6.setOnClickListener(new View.OnClickListener() {
                @Override
                public final void onClick(View view) {
                    RestoredFileActivity.this.lambda$showMenuSortFile$6$RestoredFileActivity(view);
                }
            });
        }
        this.bottomSheetDialogSortBy.setDismissWithAnimation(true);
        this.bottomSheetDialogSortBy.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        this.bottomSheetDialogSortBy.show();
    }

    public void lambda$showMenuSortFile$1$RestoredFileActivity(View view) {
        sortFileForFragment(Constants.SORT_BY_TIME_OLDEST);
        this.currentSort = Constants.SORT_BY_TIME_OLDEST;
        setStatusSelectedSort();
    }

    public void lambda$showMenuSortFile$2$RestoredFileActivity(View view) {
        sortFileForFragment(Constants.SORT_BY_TIME_NEWEST);
        this.currentSort = Constants.SORT_BY_TIME_NEWEST;
        setStatusSelectedSort();
    }

    public void lambda$showMenuSortFile$3$RestoredFileActivity(View view) {
        sortFileForFragment(Constants.SORT_BY_NAME_A_TO_Z);
        this.currentSort = Constants.SORT_BY_NAME_A_TO_Z;
        setStatusSelectedSort();
    }

    public void lambda$showMenuSortFile$4$RestoredFileActivity(View view) {
        sortFileForFragment(Constants.SORT_BY_NAME_Z_TO_A);
        this.currentSort = Constants.SORT_BY_NAME_Z_TO_A;
        setStatusSelectedSort();
    }

    public void lambda$showMenuSortFile$5$RestoredFileActivity(View view) {
        sortFileForFragment(Constants.SORT_BY_SIZE_MIN_TO_MAX);
        this.currentSort = Constants.SORT_BY_SIZE_MIN_TO_MAX;
        setStatusSelectedSort();
    }

    public void lambda$showMenuSortFile$6$RestoredFileActivity(View view) {
        sortFileForFragment(Constants.SORT_BY_SIZE_MAX_TO_MIN);
        this.currentSort = Constants.SORT_BY_SIZE_MAX_TO_MIN;
        setStatusSelectedSort();
    }

    private void sortFileForFragment(String str) {
        int currentItem = this.vpMedia.getCurrentItem();
        if (currentItem == 0) {
            this.restoredPhotoFrg.sortList(str);
        } else if (currentItem == 1) {
            this.restoredVideoFrg.sortList(str);
        } else if (currentItem == 2) {
            this.restoredAudioFrg.sortList(str);
        } else if (currentItem == 3) {
            this.restoredDocumentsFrg.sortList(str);
        }
        this.bottomSheetDialogSortBy.dismiss();
    }

    private void setStatusSelectedSort() {
        char c;
        String str = this.currentSort;
        switch (str.hashCode()) {
            case -1699274633:
                if (str.equals(Constants.SORT_BY_TIME_NEWEST)) {
                    c = 4;
                    break;
                }
            case -1664746864:
                if (str.equals(Constants.SORT_BY_TIME_OLDEST)) {
                    c = 5;
                    break;
                }
            case -1644940831:
                if (str.equals(Constants.SORT_BY_NAME_A_TO_Z)) {
                    c = 0;
                    break;
                }
            case -1322418300:
                if (str.equals(Constants.SORT_BY_SIZE_MIN_TO_MAX)) {
                    c = 3;
                    break;
                }
            case -929212081:
                if (str.equals(Constants.SORT_BY_NAME_Z_TO_A)) {
                    c = 1;
                    break;
                }
            case 500549920:
                if (str.equals(Constants.SORT_BY_SIZE_MAX_TO_MIN)) {
                    c = 2;
                    break;
                }
            default:
                c = 65535;
                break;
        }
        if (c == 0) {
            this.imgNewestSelected.setVisibility(View.GONE);
            this.imgLatestSelected.setVisibility(View.GONE);
            this.imgAtoZSelected.setVisibility(View.VISIBLE);
            this.imgZtoASelected.setVisibility(View.GONE);
            this.imgMinToMaxSelected.setVisibility(View.GONE);
            this.imgMaxToMinSelected.setVisibility(View.GONE);
        } else if (c == 1) {
            this.imgNewestSelected.setVisibility(View.GONE);
            this.imgLatestSelected.setVisibility(View.GONE);
            this.imgAtoZSelected.setVisibility(View.GONE);
            this.imgZtoASelected.setVisibility(View.VISIBLE);
            this.imgMinToMaxSelected.setVisibility(View.GONE);
            this.imgMaxToMinSelected.setVisibility(View.GONE);
        } else if (c == 2) {
            this.imgNewestSelected.setVisibility(View.GONE);
            this.imgLatestSelected.setVisibility(View.GONE);
            this.imgAtoZSelected.setVisibility(View.GONE);
            this.imgZtoASelected.setVisibility(View.GONE);
            this.imgMinToMaxSelected.setVisibility(View.GONE);
            this.imgMaxToMinSelected.setVisibility(View.VISIBLE);
        } else if (c == 3) {
            this.imgNewestSelected.setVisibility(View.GONE);
            this.imgLatestSelected.setVisibility(View.GONE);
            this.imgAtoZSelected.setVisibility(View.GONE);
            this.imgZtoASelected.setVisibility(View.GONE);
            this.imgMinToMaxSelected.setVisibility(View.VISIBLE);
            this.imgMaxToMinSelected.setVisibility(View.GONE);
        } else if (c == 4) {
            this.imgNewestSelected.setVisibility(View.VISIBLE);
            this.imgLatestSelected.setVisibility(View.GONE);
            this.imgAtoZSelected.setVisibility(View.GONE);
            this.imgZtoASelected.setVisibility(View.GONE);
            this.imgMinToMaxSelected.setVisibility(View.GONE);
            this.imgMaxToMinSelected.setVisibility(View.GONE);
        } else if (c == 5) {
            this.imgNewestSelected.setVisibility(View.GONE);
            this.imgLatestSelected.setVisibility(View.VISIBLE);
            this.imgAtoZSelected.setVisibility(View.GONE);
            this.imgZtoASelected.setVisibility(View.GONE);
            this.imgMinToMaxSelected.setVisibility(View.GONE);
            this.imgMaxToMinSelected.setVisibility(View.GONE);
        }
    }

    public class RestoredFileAsyncTask extends AsyncTask<Void, Integer, Void> {
        ArrayList<AudioModel> listAudio = new ArrayList<>();
        ArrayList<DocumentModel> listDocument = new ArrayList<>();
        ArrayList<PhotoModel> listPhoto = new ArrayList<>();
        ArrayList<VideoModel> listVideo = new ArrayList<>();

        public RestoredFileAsyncTask() {
        }

        @Override
        public void onPostExecute(Void r5) {
            super.onPostExecute(r5);
            if (RestoredFileActivity.this.isDestroyed()) {
                return;
            }
            FragmentManager supportFragmentManager = RestoredFileActivity.this.getSupportFragmentManager();
            RestoredFileActivity restoredFileActivity = RestoredFileActivity.this;
            restoredFileActivity.vpMedia.setAdapter(new ViewPagerAdapter(supportFragmentManager, restoredFileActivity, restoredFileActivity.fragmentList));
            RestoredFileActivity.this.loadingDialog.setVisibility(View.GONE);
            RestoredFileActivity.this.vpMedia.setVisibility(View.VISIBLE);
            RestoredFileActivity.this.findViewById(R.id.btnShowTab).setOnClickListener(new View.OnClickListener() {
                @Override
                public final void onClick(View view) {
                    RestoredFileAsyncTask.this.lambda$onPostExecute$0$RestoredFileActivity$RestoredFileAsyncTask(view);
                }
            });
        }

        public void lambda$onPostExecute$0$RestoredFileActivity$RestoredFileAsyncTask(View view) {
            RestoredFileActivity.this.findViewById(R.id.editFileToolbar).setVisibility(View.GONE);
            RestoredFileActivity.this.tabLayout.setVisibility(View.VISIBLE);
            int currentItem = RestoredFileActivity.this.vpMedia.getCurrentItem();
            if (currentItem == 0) {
                clearListFileDelete();
                RestoredFileActivity.this.restoredPhotoFrg.resetAdapter();
            } else if (currentItem == 1) {
                clearListFileDelete();
                RestoredFileActivity.this.restoredVideoFrg.resetAdapter();
            } else if (currentItem == 2) {
                clearListFileDelete();
                RestoredFileActivity.this.restoredAudioFrg.resetAdapter();
            } else if (currentItem == 3) {
                clearListFileDelete();
                RestoredFileActivity.this.restoredDocumentsFrg.resetAdapter();
            }
        }

        private void clearListFileDelete() {
            RestoredFileActivity.this.listFileDelete.clear();
            RestoredFileActivity.this.tvTotalFileSelected.setText(RestoredFileActivity.this.getString(R.string.size_of_list_delete_when_clear));
        }

        @Override
        public void onProgressUpdate(Integer... numArr) {
            super.onProgressUpdate(numArr);
        }

        @Override
        public Void doInBackground(Void... voidArr) {
            int i;
            Log.d("RestoredFileAsyncTask", "doInBackground: ");
            File[] listFiles = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + RestoredFileActivity.this.getString(R.string.restore_folder_path_video)).listFiles();
            if (listFiles != null) {
                i = 0;
                for (File file : listFiles) {
                    this.listVideo.add(new VideoModel(file.getAbsolutePath(), file.lastModified(), file.length(), "mp4", ""));
                    i++;
                    publishProgress(Integer.valueOf(i));
                }
                Log.d("RestoredFileAsyncTask", "doInBackground: videos " + this.listVideo.size());
                App.getInstance().getStorageCommon().setListVideo(this.listVideo);
            } else {
                i = 0;
            }
            File[] listFiles2 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + RestoredFileActivity.this.getString(R.string.restore_folder_path_photo)).listFiles();
            if (listFiles2 != null) {
                for (File file2 : listFiles2) {
                    this.listPhoto.add(new PhotoModel(file2.getAbsolutePath(), file2.lastModified(), file2.length()));
                    i++;
                    publishProgress(Integer.valueOf(i));
                }
                Log.d("RestoredFileAsyncTask", "doInBackground: photo " + this.listPhoto.size());
                App.getInstance().getStorageCommon().setListPhoto(this.listPhoto);
            }
            File[] listFiles3 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + RestoredFileActivity.this.getString(R.string.restore_folder_path_audio)).listFiles();
            if (listFiles3 != null) {
                for (File file3 : listFiles3) {
                    this.listAudio.add(new AudioModel(file3.getAbsolutePath(), file3.lastModified(), file3.length()));
                    i++;
                    publishProgress(Integer.valueOf(i));
                }
                Log.d("RestoredFileAsyncTask", "doInBackground: audio " + this.listAudio.size());
                App.getInstance().getStorageCommon().setListAudio(this.listAudio);
            }
            File[] listFiles4 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/" + RestoredFileActivity.this.getString(R.string.restore_folder_path_document)).listFiles();
            if (listFiles4 != null) {
                for (File file4 : listFiles4) {
                    this.listDocument.add(new DocumentModel(file4.getAbsolutePath(), file4.lastModified(), file4.length()));
                    i++;
                    publishProgress(Integer.valueOf(i));
                }
                Log.d("RestoredFileAsyncTask", "doInBackground: document " + this.listDocument.size());
                App.getInstance().getStorageCommon().setListDocument(this.listDocument);
                return null;
            }
            return null;
        }
    }
}
