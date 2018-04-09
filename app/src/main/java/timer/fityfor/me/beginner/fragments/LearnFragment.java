package timer.fityfor.me.beginner.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.RadioGroup;
import android.widget.VideoView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import timer.fityfor.me.beginner.R;
import timer.fityfor.me.beginner.activities.MainActivity;
import timer.fityfor.me.beginner.adapters.LearnAdapter;
import timer.fityfor.me.beginner.adapters.tools.SpacesItemDecoration;
import timer.fityfor.me.beginner.controllers.DataController;
import timer.fityfor.me.beginner.controllers.ViewController;
import timer.fityfor.me.beginner.converters.ConvertAnimalFromModelToEntity;
import timer.fityfor.me.beginner.entities.Animal;
import timer.fityfor.me.beginner.interfacies.Constants;
import timer.fityfor.me.beginner.interfacies.OnPlayCompliteListener;
import timer.fityfor.me.beginner.interfacies.RecyclerViewOnClickListener;
import timer.fityfor.me.beginner.utils.GeneralMetods;
import timer.fityfor.me.beginner.utils.SoundHelper;

import static timer.fityfor.me.beginner.interfacies.Constants.CATEGORY.AQUATIC;
import static timer.fityfor.me.beginner.interfacies.Constants.CATEGORY.BIRDS;
import static timer.fityfor.me.beginner.interfacies.Constants.CATEGORY.DOMESTIC;
import static timer.fityfor.me.beginner.interfacies.Constants.CATEGORY.MUSICAL;
import static timer.fityfor.me.beginner.interfacies.Constants.CATEGORY.TRANSPORTATIONS;
import static timer.fityfor.me.beginner.interfacies.Constants.CATEGORY.VERBS;
import static timer.fityfor.me.beginner.interfacies.Constants.CATEGORY.WHILD;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class LearnFragment extends Fragment implements RecyclerViewOnClickListener {

    @BindView(R.id.iv_background_learn)
    ImageView ivBackgroundLearn;
    Unbinder unbinder;
    @BindView(R.id.rv_learn)
    RecyclerView rvLearn;
    @BindView(R.id.rg_learn)
    RadioGroup rgLearn;
    private LearnAdapter adapter;
    Drawable backgroundDrawable = null;
    View view;
    ArrayAdapter<String> listVideoAdapter;
    public static boolean canClick = true;

    String TAG = "LearnEnglish";

    Activity mActivity;

    public static LearnFragment Instance = null;

    private AdView mAdView;

    public LearnFragment() {

    }

    @Override
    public void onResume() {
        super.onResume();
        ViewController.getViewController().setLearnFragment(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mActivity = this.getActivity();
        Instance = this;
        view = inflater.inflate(R.layout.fragment_learn, container, false);

        SoundHelper.getInstance().stopPlayer();
        unbinder = ButterKnife.bind(this, view);
        init();
        initListeners();
        final VideoView video = view.findViewById(R.id.videoView);
        final ImageView imageDefault = view.findViewById(R.id.video_default);
        final ListView videoImageList = view.findViewById(R.id.videoImage);

        mAdView = view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when when the user is about to return
                // to the app after tapping on an ad.

                mAdView.setVisibility(View.INVISIBLE);
            }
        });

        view.findViewById(R.id.iv_back_learn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MainActivity.wantToExit) return;
             //   mAdView.removeAllViews();
                getActivity().onBackPressed();
            }
        });
        view.findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MainActivity.wantToExit) return;
                MainActivity.wantToExit = true;
                SoundHelper.getInstance().playTrack(R.raw.action_sound_quit, new OnPlayCompliteListener() {
                    @Override
                    public void onComplite() {
                        getActivity().finish();
                        System.exit(0);
                    }
                });
            }
        });

        video.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra) {

                if (what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START) {
                    imageDefault.setVisibility(View.INVISIBLE);
                    return true;
                }
                return false;
            }
        });

        view.findViewById(R.id.rb_video).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isOnline()) {
                    rvLearn.setVisibility(View.INVISIBLE);

                    if (videoImageList.getVisibility() == View.VISIBLE) {
                        videoImageList.setVisibility(View.INVISIBLE);
                    } else {
                        videoImageList.setVisibility(View.VISIBLE);
                        String[] videos;
                        videos = getResources().getStringArray(R.array.school_drop_down_video);
                        listVideoAdapter = new ArrayAdapter<>(getContext(), R.layout.item_video, videos);
                        videoImageList.setAdapter(listVideoAdapter);
                    }

                    backgroundDrawable = GeneralMetods.getSingleImageFromDrawable(Constants.BACKGROUND_NEW_CATEGORY).get();
                    ivBackgroundLearn.setBackground(backgroundDrawable);
                } else {
                    showInternetAlert();
                }
            }
        });

        videoImageList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position,
                                    long arg3)
            {
                String value = (String)adapter.getItemAtPosition(position);
                ((MainActivity)getActivity()).hideStatusBar();
                String path = "http://promoletter.com/learnenglish/videos/";
                Uri uri = null;
                MediaController mediaController = new MediaController(getActivity());
                mediaController.setAnchorView(video);

                rvLearn.setVisibility(View.INVISIBLE);
                video.setVisibility(View.VISIBLE);
                imageDefault.setVisibility(View.VISIBLE);
                videoImageList.setVisibility(View.INVISIBLE);

//                uri = Uri.parse(path + "p_lesson_" + (position+1) + ".mp4");
                if (position >= 10) {
                    uri = Uri.parse(path + "p_1_lesson_" + (position-9) + ".mp4");
                } else {
                    uri = Uri.parse(path + "e_1_lesson_" + (position+1) + ".mp4");
                }

                video.setMediaController(mediaController);
                video.setVideoURI(uri);
                video.start();
            }
        });

        return view;
    }

    private void init() {
//        titles = new TextView[]{tvWildAnimals, tvDomestic, tvBirds, tvAquatics, tvTransportation, tvVerbs, tvMusical, tvVideo};
        setRecyclerAdapter();
    }

//    private void setVisibilityOfTitleByCategory(int position) {
//        for (int i = 0; i < titles.length; i++) {
//            if (i == position) {
//                titles[i].setVisibility(View.VISIBLE);
//            } else {
//                titles[i].setVisibility(View.INVISIBLE);
//
//            }
//        }
//    }

    private void initListeners() {

        rgLearn.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                SoundHelper.getInstance().stopPlayer();
                canClick = true;
                VideoView video = view.findViewById(R.id.videoView);
                ImageView imageDefault = view.findViewById(R.id.video_default);
                ListView videoImage = view.findViewById(R.id.videoImage);

                rvLearn.setVisibility(View.VISIBLE);
                video.setVisibility(View.INVISIBLE);
                imageDefault.setVisibility(View.INVISIBLE);

                switch (i) {
                    case R.id.rb_whild:
                        video.setVisibility(View.INVISIBLE);
                        imageDefault.setVisibility(View.INVISIBLE);
                        rvLearn.setVisibility(View.VISIBLE);
                        videoImage.setVisibility(View.INVISIBLE);

                        setDataByCategory(WHILD);
                        DataController.getInstance().setCategory(WHILD);
                        backgroundDrawable = GeneralMetods.getSingleImageFromDrawable(Constants.BACKGROUND_LEARN_BASE + Constants.BACKGROUND_LEARN_NOT_AQUATICS).get();
                        ivBackgroundLearn.setBackground(backgroundDrawable);
                        break;
                    case R.id.rb_birds:
                        if (isOnline()) {
                            video.setVisibility(View.INVISIBLE);
                            imageDefault.setVisibility(View.INVISIBLE);
                            rvLearn.setVisibility(View.VISIBLE);
                            videoImage.setVisibility(View.INVISIBLE);

                            setDataByCategory(BIRDS);
                            DataController.getInstance().setCategory(BIRDS);
                            backgroundDrawable = GeneralMetods.getSingleImageFromDrawable(Constants.BACKGROUND_LEARN_BASE + Constants.BACKGROUND_LEARN_NOT_AQUATICS).get();
                            ivBackgroundLearn.setBackground(backgroundDrawable);
                        } else {
                            showInternetAlert();
                        }

                        break;
                    case R.id.rb_aquatic:
                        if (isOnline()) {
                            video.setVisibility(View.INVISIBLE);
                            imageDefault.setVisibility(View.INVISIBLE);
                            rvLearn.setVisibility(View.VISIBLE);
                            videoImage.setVisibility(View.INVISIBLE);

                            setDataByCategory(AQUATIC);
                            DataController.getInstance().setCategory(AQUATIC);
                            backgroundDrawable = GeneralMetods.getSingleImageFromDrawable(Constants.BACKGROUND_LEARN_BASE + Constants.BACKGROUND_LEARN_AQUATICS).get();
                            ivBackgroundLearn.setBackground(backgroundDrawable);
                        } else {
                            showInternetAlert();
                        }
                        break;
                    case R.id.rb_domestic:
                        if (isOnline()) {
                            video.setVisibility(View.INVISIBLE);
                            imageDefault.setVisibility(View.INVISIBLE);
                            rvLearn.setVisibility(View.VISIBLE);
                            videoImage.setVisibility(View.INVISIBLE);

                            setDataByCategory(DOMESTIC);
                            DataController.getInstance().setCategory(DOMESTIC);
                            backgroundDrawable = GeneralMetods.getSingleImageFromDrawable(Constants.BACKGROUND_LEARN_BASE + Constants.BACKGROUND_LEARN_NOT_AQUATICS).get();
                            ivBackgroundLearn.setBackground(backgroundDrawable);
                        } else {
                            showInternetAlert();
                        }
                        break;
                    case R.id.rb_transportation:
                        if (isOnline()) {
                            video.setVisibility(View.INVISIBLE);
                            imageDefault.setVisibility(View.INVISIBLE);
                            rvLearn.setVisibility(View.VISIBLE);
                            videoImage.setVisibility(View.INVISIBLE);

                            setDataByCategory(TRANSPORTATIONS);
                            DataController.getInstance().setCategory(TRANSPORTATIONS);
                            backgroundDrawable = GeneralMetods.getSingleImageFromDrawable(Constants.BACKGROUND_NEW_CATEGORY).get();
                            ivBackgroundLearn.setBackground(backgroundDrawable);
                        } else {
                            showInternetAlert();
                        }
                        break;
                    case R.id.rb_verbs:
                        if (isOnline()) {
                            video.setVisibility(View.INVISIBLE);
                            imageDefault.setVisibility(View.INVISIBLE);
                            rvLearn.setVisibility(View.VISIBLE);
                            videoImage.setVisibility(View.INVISIBLE);

                            setDataByCategory(VERBS);
                            DataController.getInstance().setCategory(VERBS);
                            backgroundDrawable = GeneralMetods.getSingleImageFromDrawable(Constants.BACKGROUND_NEW_CATEGORY).get();
                            ivBackgroundLearn.setBackground(backgroundDrawable);
                        } else {
                            showInternetAlert();
                        }
                        break;
                    case R.id.rb_musical:
                        if (isOnline()) {
                            video.setVisibility(View.INVISIBLE);
                            imageDefault.setVisibility(View.INVISIBLE);
                            rvLearn.setVisibility(View.VISIBLE);
                            videoImage.setVisibility(View.INVISIBLE);

                            setDataByCategory(MUSICAL);
                            DataController.getInstance().setCategory(MUSICAL);
                            backgroundDrawable = GeneralMetods.getSingleImageFromDrawable(Constants.BACKGROUND_NEW_CATEGORY).get();
                            ivBackgroundLearn.setBackground(backgroundDrawable);
                        } else {
                            showInternetAlert();
                        }
                        break;
                    case R.id.rb_video:
//
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    private void showInternetAlert() {
        ((MainActivity) getActivity()).hideStatusBar();
        Context context = ViewController.getViewController().getContex();
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.alert_internet_dialog);
        Button exit = dialog.findViewById(R.id.okInternetDialog);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void setRecyclerAdapter() {
        adapter = new LearnAdapter(this);
        rvLearn.setAdapter(adapter);
        rvLearn.setLayoutManager(new GridLayoutManager(ViewController.getViewController().getContex(), 2));
        rvLearn.addItemDecoration(new SpacesItemDecoration());
        DataController.getInstance().setCategory(WHILD);
        setDataByCategory(WHILD);
    }

    private void setDataByCategory(int category) {

        switch (category) {
            case BIRDS:
                adapter.setData(ConvertAnimalFromModelToEntity.converter(Constants.CATEGORY.BIRDS, getContext()));
                break;
            case AQUATIC:
                adapter.setData(ConvertAnimalFromModelToEntity.converter(Constants.CATEGORY.AQUATIC, getContext()));
                break;
            case WHILD:
                adapter.setData(ConvertAnimalFromModelToEntity.converter(Constants.CATEGORY.WHILD, getContext()));
                break;
            case DOMESTIC:
                adapter.setData(ConvertAnimalFromModelToEntity.converter(Constants.CATEGORY.DOMESTIC, getContext()));
                break;
            case TRANSPORTATIONS:
                adapter.setData(ConvertAnimalFromModelToEntity.converter(Constants.CATEGORY.TRANSPORTATIONS, getContext()));
                break;
            case VERBS:
                adapter.setData(ConvertAnimalFromModelToEntity.converter(Constants.CATEGORY.VERBS, getContext()));
                break;
            case MUSICAL:
                adapter.setData(ConvertAnimalFromModelToEntity.converter(Constants.CATEGORY.MUSICAL, getContext()));
                break;
            default:
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void itemClicked(View view, int position) {

        view.setBackground(GeneralMetods.makeSelector());
        Animal currentAnimal = null;
        int category = DataController.getInstance().getCategory();
        switch (category) {
            case BIRDS:
                currentAnimal = ConvertAnimalFromModelToEntity.converter(Constants.CATEGORY.BIRDS, getContext()).get(position);
                break;
            case AQUATIC:
                currentAnimal = ConvertAnimalFromModelToEntity.converter(Constants.CATEGORY.AQUATIC, getContext()).get(position);
                break;
            case WHILD:
                currentAnimal = ConvertAnimalFromModelToEntity.converter(Constants.CATEGORY.WHILD, getContext()).get(position);
                break;
            case DOMESTIC:
                currentAnimal = ConvertAnimalFromModelToEntity.converter(Constants.CATEGORY.DOMESTIC, getContext()).get(position);
                break;
            case TRANSPORTATIONS:
                currentAnimal = ConvertAnimalFromModelToEntity.converter(Constants.CATEGORY.TRANSPORTATIONS, getContext()).get(position);
                break;
            case VERBS:
                currentAnimal = ConvertAnimalFromModelToEntity.converter(Constants.CATEGORY.VERBS, getContext()).get(position);
                break;
            case MUSICAL:
                currentAnimal = ConvertAnimalFromModelToEntity.converter(Constants.CATEGORY.MUSICAL, getContext()).get(position);
                break;
            default:
                break;
        }

        switch (view.getId()) {
            case R.id.iv_learn_image:
                if (currentAnimal != null && currentAnimal.isSound()) {
                    SoundHelper.getInstance().playTrack(currentAnimal.getAnimalVoice(), new OnPlayCompliteListener() {
                        @Override
                        public void onComplite() {

                        }
                    });
                } else {
                    showInternetAlert();
                }
                break;
            case R.id.rl_eName:
                SoundHelper.getInstance().playTrack(currentAnimal.geteVoice(), new OnPlayCompliteListener() {
                    @Override
                    public void onComplite() {

                    }
                });
                break;
            case R.id.rl_pname:
                SoundHelper.getInstance().playTrack(currentAnimal.getpVoice(), new OnPlayCompliteListener() {
                    @Override
                    public void onComplite() {

                    }
                });
                break;
            default:
                break;
        }
    }

    void alert(String message) {
        AlertDialog.Builder bld = new AlertDialog.Builder(this.getActivity());
        bld.setMessage(message);
        bld.setNeutralButton("OK", null);
        Log.d(TAG, "Showing alert dialog: " + message);
        bld.create().show();
    }
}
