package com.demo.filerecovery.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.demo.filerecovery.Constants;



import com.demo.filerecovery.ui.adapter.ItemRestoredAdapter;
import com.demo.filerecovery.utilts.FileUtil;
import com.demo.filerecovery.utilts.SharePreferenceUtils;
import com.demo.filerecovery.utilts.Utils;
import com.demo.filerecovery.R;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;


public class RestoreResultActivity extends AppCompatActivity {
    private static final String TAG = "RestoreResultActivity";
    private final String APP_INSTALL_TYPE = "application/vnd.android.package-archive";
    private final String PROVIDER_PATH = ".provider";
    public MutableLiveData<Boolean> isLoadAdError = new MutableLiveData<>();
     
    String mName = "";
    int type = -1;

    private ItemRestoredAdapter adapter;
    private ImageView imgBack;
    private ImageView imgOpen;
    private ImageView imgShare;
    private LinearLayout llOpen;
    private LinearLayout llShare;
    private RecyclerView rvListFileRestored;
    private TextView txtOpen;
    private TextView txtShare;
    private ArrayList<String> listPath = new ArrayList<>();
    private ArrayList<String> listPathSelect = new ArrayList<>();
    private ArrayList<Integer> nativePositionList = new ArrayList<>();

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Utils.setStatusBarGradiant(this);
        setContentView(R.layout.activity_restore_result);
        this.isLoadAdError.setValue(false);
        intData();
        intView();
        initRvData();
        if (this.listPath.size() > 1) {
            disableButtonOpen();
            disableButtonShare();
            return;
        }
        this.listPathSelect.addAll(this.listPath);
      
        
    }

    private void initRvData() {
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        this.rvListFileRestored.setLayoutManager(linearLayoutManager);
        this.rvListFileRestored.setHasFixedSize(true);
        this.rvListFileRestored.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int i) {
                super.onScrollStateChanged(recyclerView, i);
//                RestoreResultActivity.this.loadMoreAdNative(linearLayoutManager.findLastVisibleItemPosition());
            }
        });
        this.adapter.setTypeMedia(this.type);
        ArrayList<Object> arrayList = new ArrayList<>();
        arrayList.addAll(this.listPath);
        this.adapter.setListFileAndAds(arrayList);


        this.rvListFileRestored.setAdapter(this.adapter);
//        this.listAdsNativeLiveData.observe(this, new Observer() {
//            @Override
//            public final void onChanged(Object obj) {
//                RestoreResultActivity.this.lambda$initRvData$0$RestoreResultActivity((ArrayList) obj);
//            }
//        });
        this.isLoadAdError.observe(this, new Observer() {
            @Override
            public final void onChanged(Object obj) {
                RestoreResultActivity.this.lambda$initRvData$1$RestoreResultActivity((Boolean) obj);
            }
        });
    }


    public void lambda$initRvData$1$RestoreResultActivity(Boolean bool) {
        if (bool.booleanValue()) {
            removeAdsLoadingFails(this.adapter.getListFileAndAds());
        }
    }



    private void removeAdsLoadingFails(ArrayList<Object> arrayList) {
        for (int i = 0; i < this.nativePositionList.size(); i++) {
            arrayList.remove(this.nativePositionList.get(i));
            ArrayList<Integer> arrayList2 = this.nativePositionList;
            arrayList2.remove(arrayList2.get(i));
            Log.e(TAG, "removeAdsLoadingFails: " + this.nativePositionList.get(i));
        }
        this.adapter.setNativePositionList(this.nativePositionList);
    }

    private ArrayList<Object> addNativeNullToList(ArrayList<String> arrayList) {
        ArrayList<Object> arrayList2 = new ArrayList<>();
        arrayList2.addAll(arrayList);
        this.nativePositionList.clear();
        this.adapter.setNativePositionList(this.nativePositionList);
        return arrayList2;
    }

    public void intView() {
        this.imgBack = (ImageView) findViewById(R.id.imgBack);
        this.imgShare = (ImageView) findViewById(R.id.imgShare);
        this.txtShare = (TextView) findViewById(R.id.txtShare);
        this.llShare = (LinearLayout) findViewById(R.id.llShare);
        this.imgOpen = (ImageView) findViewById(R.id.imgOpen);
        this.txtOpen = (TextView) findViewById(R.id.txtOpen);
        this.llOpen = (LinearLayout) findViewById(R.id.llOpen);
        this.rvListFileRestored = (RecyclerView) findViewById(R.id.rvListItem);
        int i = this.type;
        if (i == 0) {
            this.mName = getString(R.string.photo_recovery);
        } else if (i == 1) {
            this.mName = getString(R.string.video_recovery);
        } else if (i == 2) {
            this.mName = getString(R.string.audio_recovery);
        } else if (i == 3) {
            this.mName = getString(R.string.document_recovery);
        }
        new Bundle().putInt(Constants.FB_LOG_RESTORE_RESULT_TYPE, this.type);
        this.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                RestoreResultActivity.this.lambda$intView$2$RestoreResultActivity(view);
            }
        });
        this.llOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(final View view) {
                RestoreResultActivity.this.lambda$intView$3$RestoreResultActivity(view);

            }
        });
        this.llShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public final void onClick(View view) {
                RestoreResultActivity.this.lambda$intView$4$RestoreResultActivity(view);
            }
        });
    }

    public void lambda$intView$2$RestoreResultActivity(View view) {
        onBackPressed();
    }

    public void lambda$intView$3$RestoreResultActivity(View view) {
        int i = this.type;
        if (i == 0) {
            Intent intent = new Intent(this, ImageViewerActivity.class);
            intent.putExtra("data", "file://" + this.listPathSelect.get(0));
            startActivity(intent);
        } else if (i == 1) {
            FileUtil.openFileVideo(this.listPathSelect.get(0), this);
        } else if (i == 2) {
            try {
                FileUtil.openFileAudio(new File(this.listPathSelect.get(0)), this);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            File file = new File(this.listPathSelect.get(0));
            if (FilenameUtils.getExtension(file.getName()).equals("apk")) {
                openFileApk(file.getPath());
            } else {
                viewFileDocument(file.getPath());
            }
        }
    }

    public void lambda$intView$4$RestoreResultActivity(View view) {
        shareMultipleFile();
    }

    private void viewFileDocument(String str) {
        Intent intent = new Intent("android.intent.action.VIEW");
        File file = new File(str);
        if (file.exists()) {
            if (Build.VERSION.SDK_INT < 24) {
                intent.setDataAndType(Uri.fromFile(file), MimeTypeMap.getSingleton().getMimeTypeFromExtension(FilenameUtils.getExtension(str)));
            } else {
                Uri uriForFile = FileProvider.getUriForFile(this, getPackageName() + ".provider", file);
                grantUriPermission(getPackageName(), uriForFile, Intent.FLAG_GRANT_READ_URI_PERMISSION);
                intent.setDataAndType(uriForFile, MimeTypeMap.getSingleton().getMimeTypeFromExtension(FilenameUtils.getExtension(str)));
                intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }
            startActivity(Intent.createChooser(intent, "Complete action using"));
        }
    }

    private void openFileApk(String str) {
        if (Build.VERSION.SDK_INT > 24) {
            Uri uriForFile = FileProvider.getUriForFile(this, getPackageName() + ".provider", new File(str));
            Intent intent = new Intent("android.intent.action.VIEW", uriForFile);
            intent.putExtra("android.intent.extra.NOT_UNKNOWN_SOURCE", true);
            intent.setDataAndType(uriForFile, "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(intent);
            return;
        }
        Intent intent2 = new Intent("android.intent.action.VIEW");
        intent2.setDataAndType(Uri.parse(str), "application/vnd.android.package-archive");
        startActivity(intent2);
    }

    @Override
    public void onBackPressed() {
        SharePreferenceUtils.increaseRecoveryDone(this);
        setResult(102);
        finish();
    }

    public void intData() {
        this.type = getIntent().getIntExtra(Constants.FB_LOG_RESTORE_RESULT_TYPE, 0);
        long longExtra = getIntent().getLongExtra("value" , 0L);
        ArrayList<String> stringArrayListExtra = getIntent().getStringArrayListExtra("listPath");
        this.listPath = stringArrayListExtra;
        this.listPathSelect.addAll(stringArrayListExtra);
        this.adapter = new ItemRestoredAdapter(this, new ItemRestoredAdapter.OnListFileRestoredListener() {
            @Override
            public final void onListFileChange(ArrayList arrayList) {
                RestoreResultActivity.this.lambda$intData$5$RestoreResultActivity(arrayList);
            }
        });
        ((TextView) findViewById(R.id.tvtTotalSize)).setText(Formatter.formatFileSize(this, longExtra));
    }

    public void lambda$intData$5$RestoreResultActivity(ArrayList arrayList) {
        if (arrayList.size() == 1) {
            enableButtonOpen();
            enableButtonShare();
        } else if (arrayList.size() == 0) {
            disableButtonOpen();
            disableButtonShare();
        } else {
            disableButtonOpen();
            enableButtonShare();
        }
        this.listPathSelect.clear();
        this.listPathSelect.addAll(arrayList);
    }

    private void shareMultipleFile() {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.SEND_MULTIPLE");
        intent.putExtra("android.intent.extra.SUBJECT", "Here are some files.");
        intent.setType("*/*");
        ArrayList<Uri> arrayList = new ArrayList<>();
        Iterator<String> it = this.listPathSelect.iterator();
        while (it.hasNext()) {
            File file = new File(it.next());
            arrayList.add(FileProvider.getUriForFile(this, getPackageName() + ".provider", file));
        }
        intent.putParcelableArrayListExtra("android.intent.extra.STREAM", arrayList);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        SharePreferenceUtils.increaseCount(this);
    }

    @Override
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 101 && i2 == 102) {
            setResult(102);
            finish();
        }
    }

    private void disableButtonShare() {
        this.llShare.setEnabled(false);
    }

    private void enableButtonShare() {
        this.llShare.setEnabled(true);
    }

    private void disableButtonOpen() {
        this.llOpen.setEnabled(false);
    }

    private void enableButtonOpen() {
        this.llOpen.setEnabled(true);
    }
}
