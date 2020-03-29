package com.example.sovjanta.indiafrag;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager.widget.ViewPager;

import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.sovjanta.R;
import com.example.sovjanta.adapters.NewsFeedAdapter;
import com.example.sovjanta.adapters.ViewPagerAdapterIndia;
import com.example.sovjanta.customUi.CustomUISnackError;
import com.example.sovjanta.customUi.ProgressDialogCustom;
import com.example.sovjanta.fragments.NewsFragment;
import com.example.sovjanta.models.NewsFeedAbstractModel;
import com.example.sovjanta.prefUtils.PrefLogin;
import com.example.sovjanta.utilities.Base64EnDEUtility;
import com.example.sovjanta.utilities.NetworkUtility;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EntertainmentFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    SwipeRefreshLayout refreshNewsFeed;
    boolean isEverythingLoaded = false;
    ArrayList<NewsFeedAbstractModel> newsModelList = new ArrayList<>();
    LottieAnimationView loading, bottomLoadingBar;
    Context context;
    int PAGE = 1;
    ProgressDialogCustom progressDialogCustom;
    View v;
    Base64EnDEUtility base64EnDEUtility = new Base64EnDEUtility();
    RecyclerView recyclerView;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private NewsFeedAdapter newsFeedAdapter;
    private NewsFeedAbstractModel newsFeedAbstractModel;
    private String[] stories = {"Story1", "Story2"};

    public EntertainmentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewsFragment newInstance(String param1, String param2) {
        NewsFragment fragment = new NewsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_entertainment, container, false);
        initUI(v);
        clearList();

        //new fetchData(PAGE).execute();
        RequestQueue requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        if (NetworkUtility.getInstance(context).isOnline()) {
            getData();
        } else {
            new CustomUISnackError(context, v, "Network error");
        }
        return v;
    }

    private void clearList() {
        newsModelList.clear();
    }

    private void getData() {
        refreshNewsFeed.setRefreshing(true);

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        try {
            String url = "http://newsapi.org/v2/top-headlines?country=in&category=entertainment&apiKey=550f896c5aeb4200bc22f7d35c6c3abe";
            JSONObject object = new JSONObject();
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                    try {
                        JSONArray array = response.getJSONArray("articles");
                        for (int i = 0; i <= array.length(); i++) {
                            JSONObject item = array.getJSONObject(i);
                            newsFeedAbstractModel = new NewsFeedAbstractModel();

                            newsFeedAbstractModel.setUrl(item.getString("urlToImage"));
                            newsFeedAbstractModel.setStory(item.getString("title"));
                            newsFeedAbstractModel.setWebUrl(item.getString("url"));
                            newsFeedAbstractModel.setThumb(R.drawable.ic_ent);
                            newsFeedAbstractModel.setTv_status(item.getString("description"));


                            newsModelList.add(newsFeedAbstractModel);
                            newsFeedAdapter.notifyDataSetChanged();


                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    refreshNewsFeed.setRefreshing(false);
                    progressDialogCustom.dismiss();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    new CustomUISnackError(context, v, error.getMessage() + " " + "Caused by:" + error.getCause());
                    refreshNewsFeed.setRefreshing(false);
                    progressDialogCustom.dismiss();

                }
            });
            requestQueue.add(jsonObjectRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initUI(View v) {

        recyclerView = v.findViewById(R.id.rvNewsFeedTopHeadlinesIndia);
        newsFeedAdapter = new NewsFeedAdapter(context, newsModelList);
        loading = v.findViewById(R.id.loadingAnim);
        recyclerView.setAdapter(newsFeedAdapter);
        refreshNewsFeed = v.findViewById(R.id.refreshNewsFeed);
        progressDialogCustom = new ProgressDialogCustom(context, "Loading");

        setListeners();

    }

    private void setListeners() {
        refreshNewsFeed.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (NetworkUtility.getInstance(context).isOnline()) {
                    refreshNewsFeed.setRefreshing(true);
                    newsModelList.clear();

                    isEverythingLoaded = false;
                    getData();

                    recyclerView.setAdapter(newsFeedAdapter);
                } else {
                    new CustomUISnackError(context, v, "Network error");
                    refreshNewsFeed.setRefreshing(false);

                }
            }
        });
        newsFeedAdapter.setOnBottomReachedListener(new NewsFeedAdapter.OnBottomReachedListener() {
            @Override
            public void onBottomReached(int position) {
            }


        }) ;
        newsFeedAdapter.setOnItemClickListener(new NewsFeedAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, ArrayList<NewsFeedAbstractModel> menulist) {

            }
        });
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

}
